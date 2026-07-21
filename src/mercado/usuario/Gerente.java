package mercado.usuario;

import mercado.modelo.Venda;

import java.util.List;

public class Gerente extends Usuario {
    private int idGerente;
    private boolean administrador;

    public Gerente(String nome, String cpf, String senha, int idGerente, boolean administrador) {
        super(nome, cpf, senha);
        this.idGerente = idGerente;
        this.administrador = administrador;
    }

    public void emitirRelatorioVendas(List<Venda> vendas) {
        System.out.println("\n============== Relatório de Vendas (Gerente: " + nome + ") ==============");
        double totalGeral = 0.0;
        for (Venda venda : vendas) {
            System.out.printf("Venda #%d | Data: %s | Total: R$ %.2f | Status: %s | Operador: %s%n",
                    venda.getId(), venda.getData(), venda.getTotal(), venda.getStatus(), venda.getNomeOperador());
            totalGeral += venda.getTotal();
        }
        System.out.printf("Total geral: R$ %.2f%n", totalGeral);
        System.out.println("==================================================================================\n");
    }

    public void cadastrarFuncionario(OperadorCaixa funcionario) {
        System.out.println("[OK] Funcionário " + funcionario.getNome() + " cadastrado por " + nome + ".");
    }
}