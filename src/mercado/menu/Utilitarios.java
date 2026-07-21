package mercado.menu;

import mercado.modelo.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utilitarios {
    private static final Scanner scanner = new Scanner(System.in);

    private Utilitarios() {
    }

    public static int lerInt(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    public static double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    public static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public static Produto selecionarProduto(List<Produto> produtos, boolean apenasAtivos) {
        List<Produto> disponiveis = new ArrayList<>();
        for (Produto p : produtos) {
            if (!apenasAtivos || p.getStatus()) {
                disponiveis.add(p);
            }
        }

        if (disponiveis.isEmpty()) {
            System.out.println("[ERRO] Não há produtos disponíveis.\n");
            return null;
        }

        System.out.println("\n----- Produtos -----");
        for (Produto p : disponiveis) {
            String tag = p.getStatus() ? "" : " [INATIVO]";
            System.out.printf("#%d %s - R$ %.2f (estoque: %d)%s%n",
                    p.getId(), p.getNome(), p.getPrecoBase(), p.getQuantidadeEstoque(), tag);
        }
        System.out.println("---------------------\n");

        int id = lerInt("Informe o ID do produto: ");
        for (Produto p : disponiveis) {
            if (p.getId() == id) return p;
        }
        System.out.println("[ERRO] Produto não encontrado.\n");
        return null;
    }
}