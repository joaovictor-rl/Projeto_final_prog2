public class Cartao implements FormaPagamento {
    private boolean creditoDebito; // true = crédito, false = débito
    private String bandeira;

    public Cartao(boolean creditoDebito, String bandeira) {
        this.creditoDebito = creditoDebito;
        this.bandeira = bandeira;
    }

    @Override
    public boolean realizarPagamento(Pagamento pagamento) {
        String tipo = creditoDebito ? "crédito" : "débito";
        System.out.printf("[CARTÃO] Processando pagamento de R$ %.2f no cartão de %s (%s)...%n",
                pagamento.getValorTotal(), tipo, bandeira);
        pagamento.setStatus(true);
        System.out.println("[OK] Pagamento no cartão aprovado.");
        return true;
    }
}
