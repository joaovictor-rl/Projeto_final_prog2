import java.time.LocalDate;

public class Venda {
    private static int proximoId = 1;

    private int id;
    private LocalDate data;
    private double total;
    private double desconto;
    private String status;
    private String recibo;
    private Pagamento pagamento;
    private Carrinho carrinho;

    public Venda(Carrinho carrinho) {
        this.id = proximoId++;
        this.data = LocalDate.now();
        this.total = 0.0;
        this.desconto = 0.0;
        this.carrinho = carrinho;
        this.status = "aberta";
        this.recibo = null;
        this.pagamento = null;
        recalcularTotal();
    }

    public int getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public double getTotal() {
        recalcularTotal();
        return this.total;
    }

    public void recalcularTotal() {
        double subtotal = carrinho.calcularSubtotal();
        this.total = Math.max(subtotal - desconto, 0.0);
    }

    public double getDesconto(){
        return desconto;
    }

    public String getStatus() {
        return status;
    }

    public Pagamento getPagamento(){
        return pagamento;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public void aplicarDesconto(double valor) {
        if (valor < 0) {
            System.out.println("[ERRO] O desconto não pode ser negativo.");
            return;
        }
        this.desconto = valor;
        recalcularTotal();
        System.out.printf("[OK] Desconto de R$ %.2f aplicado. Novo total: R$ %.2f%n", valor, this.total);
    }

    public void registrarPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
        System.out.println("[OK] Pagamento registrado na venda #" + id + ".");
    }

    public void emitirRecibo() {
        if (pagamento == null || !pagamento.getStatus()) {
            System.out.println("[ERRO] Não é possível emitir recibo sem pagamento aprovado.");
            return;
        }
        this.recibo = "RECIBO-" + id + "-" + data;
        System.out.println("\n----- " + recibo + " -----");
        for (ItemCarrinho item : carrinho.getListaItens()) {
            System.out.printf("%s x%d = R$ %.2f%n",
                    item.getProduto().getNome(), item.getQuantidade(), item.calcularSubtotal());
        }
        System.out.printf("TOTAL: R$ %.2f%n", getTotal());
        System.out.println("Forma de pagamento: " + pagamento.getFormaPagamento());
        System.out.println("---------------------------\n");
    }

    public void finalizarVenda() {
        if (this.total == 0.0) {
            this.total = carrinho.calcularSubtotal();
        }
        if (pagamento == null || !pagamento.getStatus()) {
            System.out.println("[ERRO] Não é possível finalizar a venda sem pagamento aprovado.");
            return;
        }
        this.status = "finalizada";
        System.out.printf("[OK] Venda #%d finalizada com sucesso. Total: R$ %.2f%n", id, total);
    }

    @Override
    public String toString() {
        return String.format("Venda(id=%d, total=%.2f, status=%s)", id, total, status);
    }
}