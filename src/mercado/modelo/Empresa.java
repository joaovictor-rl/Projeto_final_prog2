package mercado.modelo;

import java.util.ArrayList;
import java.util.List;

public class Empresa {
    private int capacidade;
    private List<Produto> listaProdutos;

    public Empresa(int capacidade) {
        this.capacidade = capacidade;
        this.listaProdutos = new ArrayList<>();
    }

    public List<Produto> getListaProdutos() {
        return listaProdutos;
    }

    public int verificarEstoque(List<Produto> produtos) {
        int total = 0;
        for (Produto p : produtos) {
            total += p.getQuantidadeEstoque();
        }
        System.out.println("[INFO] Estoque total verificado: " + total + " unidades.");
        return total;
    }


    public void listarProdutos() {
        System.out.println("\n----- Produtos cadastrados -----");
        if (listaProdutos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
        }
        for (Produto p : listaProdutos) {
            System.out.printf("#%d %s - R$ %.2f (estoque: %d)%n",
                    p.getId(), p.getNome(), p.getPrecoBase(), p.getQuantidadeEstoque());
        }
        System.out.println("---------------------------------\n");
    }

    public void adicionarProduto(Produto produto) {
        if (listaProdutos.size() >= capacidade) {
            throw new IllegalStateException("Capacidade máxima de produtos atingida.");
        }
        listaProdutos.add(produto);
        System.out.println("[OK] Produto '" + produto.getNome() + "' cadastrado.");
    }

    public void deletarProduto(Produto produto) {
        if (listaProdutos.remove(produto)) {
            System.out.println("[OK] Produto '" + produto.getNome() + "' removido.");
        } else {
            System.out.println("[ERRO] Produto '" + produto.getNome() + "' não encontrado.");
        }
    }
}