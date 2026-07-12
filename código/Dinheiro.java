public class Dinheiro implements FormaPagamento {
    private double troco;

    public Dinheiro() {
        this.troco = 0.0;
    }

    @Override
    public boolean realizarPagamento(Pagamento pagamento) {
        return realizarPagamento(pagamento, pagamento.getValorTotal());
    }

    /** Sobrecarga para informar o valor recebido em espécie */
    public boolean realizarPagamento(Pagamento pagamento, double valorRecebido) {
        if (valorRecebido < pagamento.getValorTotal()) {
            System.out.println("[ERRO] Valor recebido insuficiente.");
            return false;
        }
        this.troco = valorRecebido - pagamento.getValorTotal();
        pagamento.setStatus(true);
        System.out.printf("[DINHEIRO] Pagamento recebido. Troco: R$ %.2f%n", troco);
        return true;
    }
}
