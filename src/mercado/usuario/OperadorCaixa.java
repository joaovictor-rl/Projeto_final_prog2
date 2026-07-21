package mercado.usuario;

import mercado.modelo.Carrinho;
import mercado.modelo.Produto;
import mercado.modelo.Venda;

public class OperadorCaixa extends Usuario {
    private int idOperador;

    public OperadorCaixa(String nome, String cpf, String senha, int idOperador) {
        super(nome, cpf, senha);
        this.idOperador = idOperador;
    }

    public Venda iniciarVenda() {
        Venda venda = new Venda(new Carrinho(), this.nome);
        System.out.println("[OK] Venda iniciada pelo operador " + nome + ".");
        return venda;
    }

    public void registrarItem(Venda venda, Produto produto, int quantidade) {
        venda.adicionarItem(produto, quantidade);
    }

    public void finalizarVenda(Venda venda) {
        venda.finalizarVenda();
    }
}