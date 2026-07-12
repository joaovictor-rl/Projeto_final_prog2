public class OperadorCaixa extends Usuario {
    private int idOperador;

    public OperadorCaixa(String nome, String cpf, String senha, int idOperador) {
        super(nome, cpf, senha);
        this.idOperador = idOperador;
    }

    /** + iniciarVenda(): Venda */
    public Venda iniciarVenda() {
        Venda venda = new Venda(new Carrinho());
        System.out.println("[OK] Venda iniciada pelo operador " + nome + ".");
        return venda;
    }

    /** + registrarItem(Produto, quantidade): void */
    public void registrarItem(Venda venda, Produto produto, int quantidade) {
        venda.getCarrinho().adicionarItem(produto, quantidade);
    }

    /** + finalizarVenda(Venda): void */
    public void finalizarVenda(Venda venda) {
        venda.finalizarVenda();
    }
}
