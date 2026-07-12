import java.util.ArrayList;
import java.util.List;

public class Carrinho {
    private static int proximoId = 1;

    private int id;
    private List<ItemCarrinho> listaItens;

    public Carrinho() {
        this.id = proximoId++;
        this.listaItens = new ArrayList<>();
    }

    public List<ItemCarrinho> getListaItens() {
        return listaItens;
    }

    /** + adicionarItem(Produto, quantidade): void */
    public void adicionarItem(Produto produto, int quantidade) {
        for (ItemCarrinho item : listaItens) {
            if (item.getProduto() == produto) {
                item.incrementarQuantidade(quantidade);
                System.out.println("[OK] Quantidade de '" + produto.getNome() + "' atualizada no carrinho.");
                return;
            }
        }
        listaItens.add(new ItemCarrinho(produto, quantidade));
        System.out.println("[OK] '" + produto.getNome() + "' adicionado ao carrinho (x" + quantidade + ").");
    }

    /** + removerItem(Produto): void */
    public void removerItem(Produto produto) {
        for (ItemCarrinho item : new ArrayList<>(listaItens)) {
            if (item.getProduto() == produto) {
                listaItens.remove(item);
                System.out.println("[OK] '" + produto.getNome() + "' removido do carrinho.");
                return;
            }
        }
        System.out.println("[ERRO] '" + produto.getNome() + "' não está no carrinho.");
    }

    /** + calcularSubtotal(): double */
    public double calcularSubtotal() {
        double total = 0.0;
        for (ItemCarrinho item : listaItens) {
            total += item.calcularSubtotal();
        }
        return total;
    }

    /** + esvaziarCarrinho(): void */
    public void esvaziarCarrinho() {
        listaItens.clear();
        System.out.println("[OK] Carrinho esvaziado.");
    }
}
