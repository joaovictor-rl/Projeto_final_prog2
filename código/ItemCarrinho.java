public class ItemCarrinho {
    private static int proximoId = 1;

    private int id;
    private Produto produto;
    private int quantidade;
    private double precoUnitario;

    public ItemCarrinho(Produto produto, int quantidade) {
        this.id = proximoId++;
        this.produto = produto;
        this.quantidade = quantidade;
        this.precoUnitario = produto.getPrecoBase();
    }

    public Produto getProduto() {
        return produto;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public double calcularSubtotal() {
        return precoUnitario * quantidade;
    }

    public void atualizarQuantidade(int novaQuantidade) {
        if (novaQuantidade < 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        this.quantidade = novaQuantidade;
    }

    public void incrementarQuantidade(int incremento) {
        this.quantidade += incremento;
    }


    public void decrementarQuantidade(int decremento) {
        if (this.quantidade - decremento < 0) {
            throw new IllegalArgumentException("Quantidade não pode ficar negativa.");
        }
        this.quantidade -= decremento;
    }

    @Override
    public String toString() {
        return String.format("ItemCarrinho(%s x%d = R$ %.2f)",
                produto.getNome(), quantidade, calcularSubtotal());
    }
}