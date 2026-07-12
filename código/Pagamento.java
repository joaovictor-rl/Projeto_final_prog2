public class Pagamento {
    private String formaPagamento;
    private boolean status;
    private double valorTotal;

    public Pagamento(String formaPagamento, double valorTotal) {
        this.formaPagamento = formaPagamento;
        this.valorTotal = valorTotal;
        this.status = false;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    @Override
    public String toString() {
        String situacao = status ? "aprovado" : "pendente";
        return String.format("Pagamento(%s, R$ %.2f, %s)", formaPagamento, valorTotal, situacao);
    }
}
