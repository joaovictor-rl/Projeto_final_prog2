public class Pix implements FormaPagamento {
    private String pixCobranca;

    public Pix() {
        this.pixCobranca = "";
    }

    /** - gerarPixCobranca(pagamento): boolean */
    private boolean gerarPixCobranca(Pagamento pagamento) {
        this.pixCobranca = "PIX-" + System.nanoTime();
        System.out.println("[PIX] Cobrança gerada: " + pixCobranca);
        return true;
    }

    @Override
    public boolean realizarPagamento(Pagamento pagamento) {
        gerarPixCobranca(pagamento);
        System.out.printf("[PIX] Aguardando confirmação de R$ %.2f...%n", pagamento.getValorTotal());
        pagamento.setStatus(true);
        System.out.println("[OK] Pagamento via Pix confirmado.");
        return true;
    }
}
