package mercado.modelo;

public class Produto {
    private static int proximoId = 1;

    private int id;
    private boolean status;
    private String nome;
    private double precoBase;
    private String descricao;
    private int quantidadeEstoque;

    public Produto(String nome, double precoBase, String descricao, int quantidadeEstoque) {
        this.id = proximoId++;
        this.status = true;
        this.nome = nome;
        this.precoBase = precoBase;
        this.descricao = descricao;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getPrecoBase() {
        return precoBase;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public boolean getStatus() {
        return status;
    }

    public void ativar() {
        this.status = true;
        System.out.println("[OK] Produto '" + nome + "' ativado.");
    }

    public void desativar() {
        this.status = false;
        System.out.println("[OK] Produto '" + nome + "' desativado.");
    }

    public void atualizarPreco(double novoPreco) {
        if (novoPreco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo.");
        }
        this.precoBase = novoPreco;
        System.out.printf("[OK] Preço de '%s' atualizado para R$ %.2f.%n", nome, novoPreco);
    }

    public void adicionarEstoque(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("Quantidade inválida.");
        }
        this.quantidadeEstoque += quantidade;
        System.out.printf("[OK] Estoque de '%s' incrementado em %d (total: %d).%n",
                nome, quantidade, quantidadeEstoque);
    }

    public void baixarEstoque(int quantidade) {
        if (quantidade > this.quantidadeEstoque) {
            throw new IllegalStateException("Estoque insuficiente de '" + nome + "'.");
        }
        this.quantidadeEstoque -= quantidade;
        System.out.printf("[OK] Estoque de '%s' reduzido em %d (total: %d).%n",
                nome, quantidade, quantidadeEstoque);
    }

    @Override
    public String toString() {
        return String.format("Produto(id=%d, nome=%s, precoBase=%.2f, estoque=%d, status=%s)",
                id, nome, precoBase, quantidadeEstoque, status ? "ativo" : "inativo");
    }
}