import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * Sistema de Mercado - Menu principal (console).
 *
 * Demonstra os pilares de POO usados no diagrama:
 *  - Herança: Usuario (abstrata) -> Gerente / OperadorCaixa
 *  - Interface / Polimorfismo: FormaPagamento -> Cartao / Pix / Dinheiro
 *  - Encapsulamento: atributos privados com getters/setters em todas as classes
 *  - Composição: Venda possui um Carrinho; Carrinho possui ItemCarrinho(s)
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final Empresa empresa = new Empresa(100);
    private static final List<Usuario> usuarios = new ArrayList<>();
    private static final List<Venda> vendas = new ArrayList<>();

    private static Usuario usuarioLogado = null;
    private static Venda vendaAtual = null;

    public static void main(String[] args) {
        inicializarDadosDemo();

        System.out.println("=====================================");
        System.out.println("      SISTEMA DE MERCADO - MENU");
        System.out.println("=====================================");
        System.out.println("Login GERENTE  -> CPF: 00000000000 | Senha: admin");
        System.out.println("Login OPERADOR -> CPF: 22222222222 | Senha: senha123\n");

        boolean executando = true;
        while (executando) {
            if (usuarioLogado == null) {
                executando = telaLogin();
            } else if (usuarioLogado instanceof Gerente) {
                menuGerente((Gerente) usuarioLogado);
            } else if (usuarioLogado instanceof OperadorCaixa) {
                menuOperador((OperadorCaixa) usuarioLogado);
            }
        }

        System.out.println("\nSistema encerrado. Até logo!");
    }

    // =========================================================
    // DADOS INICIAIS (usuários e produtos de demonstração)
    // =========================================================
    private static void inicializarDadosDemo() {
        // Usuários
        Gerente gerenteInicial = new Gerente("Administrador", "00000000000", "admin", 1, true);
        OperadorCaixa operadorInicial = new OperadorCaixa("Carlos Lima", "22222222222", "senha123", 1);
        usuarios.add(gerenteInicial);
        usuarios.add(operadorInicial);

        // Produtos já cadastrados no estoque
        Produto arroz = new Produto("Arroz 5kg", 25.90, "Arroz tipo 1", 0);
        Produto feijao = new Produto("Feijão 1kg", 8.50, "Feijão carioca", 0);
        Produto oleo = new Produto("Óleo de Soja 900ml", 6.79, "Óleo de soja refinado", 0);
        Produto acucar = new Produto("Açúcar 1kg", 4.50, "Açúcar refinado", 0);
        Produto leite = new Produto("Leite 1L", 5.29, "Leite integral", 0);

        empresa.adicionarProduto(arroz);
        empresa.adicionarProduto(feijao);
        empresa.adicionarProduto(oleo);
        empresa.adicionarProduto(acucar);
        empresa.adicionarProduto(leite);

        arroz.adicionarEstoque(50);
        feijao.adicionarEstoque(80);
        oleo.adicionarEstoque(40);
        acucar.adicionarEstoque(60);
        leite.adicionarEstoque(100);
    }

    // =========================================================
    // TELA DE LOGIN
    // =========================================================
    private static boolean telaLogin() {
        System.out.println("----- LOGIN -----");
        System.out.println("1 - Entrar");
        System.out.println("0 - Sair do sistema");
        int opcao = lerInt("Escolha: ");

        if (opcao == 0) {
            return false;
        }
        if (opcao != 1) {
            System.out.println("Opção inválida.\n");
            return true;
        }

        String cpf = lerTexto("CPF: ");
        String senha = lerTexto("Senha: ");

        // Localiza primeiro o usuário pelo CPF, para chamar fazerLogin() só nele
        // (evita imprimir "credenciais inválidas" para todos os outros usuários da lista)
        Usuario candidato = null;
        for (Usuario usuario : usuarios) {
            if (usuario.getCpf().equals(cpf)) {
                candidato = usuario;
                break;
            }
        }

        if (candidato != null && candidato.fazerLogin(cpf, senha)) {
            usuarioLogado = candidato;
        } else {
            System.out.println("[ERRO] CPF ou senha inválidos.");
        }
        System.out.println();
        return true;
    }

    // =========================================================
    // MENU DO GERENTE
    // =========================================================
    private static void menuGerente(Gerente gerente) {
        System.out.println("----- MENU GERENTE (" + gerente.getNome() + ") -----");
        System.out.println("1 - Cadastrar funcionário (Operador de caixa)");
        System.out.println("2 - Cadastrar produto");
        System.out.println("3 - Adicionar estoque a um produto");
        System.out.println("4 - Baixar estoque de um produto");
        System.out.println("5 - Listar produtos");
        System.out.println("6 - Verificar estoque total");
        System.out.println("7 - Emitir relatório de vendas");
        System.out.println("8 - Alterar minha senha");
        System.out.println("9 - Logout");
        System.out.println("0 - Sair do sistema");
        int opcao = lerInt("Escolha: ");
        System.out.println();

        switch (opcao) {
            case 1:
                cadastrarFuncionario(gerente);
                break;
            case 2:
                cadastrarProduto();
                break;
            case 3:
                alterarEstoque(true);
                break;
            case 4:
                alterarEstoque(false);
                break;
            case 5:
                empresa.listarProdutos();
                break;
            case 6:
                empresa.verificarEstoque(empresa.getListaProdutos());
                break;
            case 7:
                gerente.emitirRelatorioVendas(vendas);
                break;
            case 8:
                String novaSenha = lerTexto("Nova senha: ");
                gerente.alterarSenha(novaSenha);
                break;
            case 9:
                usuarioLogado = null;
                System.out.println("[OK] Logout realizado.\n");
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida.\n");
        }
    }

    private static void cadastrarFuncionario(Gerente gerente) {
        String nome = lerTexto("Nome do operador: ");
        String cpf = lerTexto("CPF: ");
        String senha = lerTexto("Senha: ");
        int idOperador = usuarios.size() + 1;
        OperadorCaixa operador = new OperadorCaixa(nome, cpf, senha, idOperador);
        usuarios.add(operador);
        gerente.cadastrarFuncionario(operador);
        System.out.println();
    }

    private static void cadastrarProduto() {
        String nome = lerTexto("Nome do produto: ");
        double preco = lerDouble("Preço base: ");
        String descricao = lerTexto("Descrição: ");
        int estoqueInicial = lerInt("Quantidade inicial em estoque: ");
        Produto produto = new Produto(nome, preco, descricao, 0);
        empresa.adicionarProduto(produto);
        if (estoqueInicial > 0) {
            produto.adicionarEstoque(estoqueInicial);
        }
        System.out.println();
    }

    private static void alterarEstoque(boolean adicionar) {
        Produto produto = selecionarProduto();
        if (produto == null) return;
        int quantidade = lerInt("Quantidade: ");
        try {
            if (adicionar) {
                produto.adicionarEstoque(quantidade);
            } else {
                produto.baixarEstoque(quantidade);
            }
        } catch (RuntimeException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
        System.out.println();
    }

    // =========================================================
    // MENU DO OPERADOR DE CAIXA
    // =========================================================
    private static void menuOperador(OperadorCaixa operador) {
        System.out.println("----- MENU OPERADOR DE CAIXA (" + operador.getNome() + ") -----");
        if (vendaAtual == null) {
            System.out.println("(nenhuma venda em aberto)");
        } else {
            System.out.printf("Venda #%d em aberto | Subtotal: R$ %.2f%n",
                    vendaAtual.getId(), vendaAtual.getCarrinho().calcularSubtotal());
        }
        System.out.println("1 - Iniciar nova venda");
        System.out.println("2 - Registrar item na venda");
        System.out.println("3 - Remover item da venda");
        System.out.println("4 - Aplicar desconto");
        System.out.println("5 - Registrar pagamento");
        System.out.println("6 - Emitir recibo");
        System.out.println("7 - Finalizar venda");
        System.out.println("8 - Logout");
        System.out.println("0 - Sair do sistema");
        int opcao = lerInt("Escolha: ");
        System.out.println();

        switch (opcao) {
            case 1:
                vendaAtual = operador.iniciarVenda();
                vendas.add(vendaAtual);
                System.out.println();
                break;
            case 2:
                registrarItemNaVenda(operador);
                break;
            case 3:
                removerItemDaVenda();
                break;
            case 4:
                aplicarDesconto();
                break;
            case 5:
                registrarPagamento();
                break;
            case 6:
                if (vendaAtual != null) vendaAtual.emitirRecibo();
                else System.out.println("[ERRO] Nenhuma venda em aberto.\n");
                break;
            case 7:
                if (vendaAtual != null) {
                    operador.finalizarVenda(vendaAtual);
                    vendaAtual = null;
                } else {
                    System.out.println("[ERRO] Nenhuma venda em aberto.\n");
                }
                System.out.println();
                break;
            case 8:
                usuarioLogado = null;
                vendaAtual = null;
                System.out.println("[OK] Logout realizado.\n");
                break;
            case 0:
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida.\n");
        }
    }

    private static void registrarItemNaVenda(OperadorCaixa operador) {
        if (vendaAtual == null) {
            System.out.println("[ERRO] Inicie uma venda primeiro.\n");
            return;
        }
        Produto produto = selecionarProduto();
        if (produto == null) return;
        int quantidade = lerInt("Quantidade: ");
        operador.registrarItem(vendaAtual, produto, quantidade);
        try {
            produto.baixarEstoque(quantidade);
        } catch (RuntimeException e) {
            System.out.println("[AVISO] " + e.getMessage());
        }
        System.out.println();
    }

    private static void removerItemDaVenda() {
        if (vendaAtual == null) {
            System.out.println("[ERRO] Nenhuma venda em aberto.\n");
            return;
        }
        Produto produto = selecionarProduto();
        if (produto == null) return;
        vendaAtual.getCarrinho().removerItem(produto);
        System.out.println();
    }

    private static void aplicarDesconto() {
        if (vendaAtual == null) {
            System.out.println("[ERRO] Nenhuma venda em aberto.\n");
            return;
        }
        double valor = lerDouble("Valor do desconto: ");
        vendaAtual.aplicarDesconto(valor);
        System.out.println();
    }

    private static void registrarPagamento() {
        if (vendaAtual == null) {
            System.out.println("[ERRO] Nenhuma venda em aberto.\n");
            return;
        }
        double totalVenda = vendaAtual.getTotal() > 0
                ? vendaAtual.getTotal()
                : vendaAtual.getCarrinho().calcularSubtotal();

        System.out.println("Formas de pagamento:");
        System.out.println("1 - Cartão");
        System.out.println("2 - Pix");
        System.out.println("3 - Dinheiro");
        int opcao = lerInt("Escolha: ");

        // Polimorfismo: cada implementação de FormaPagamento trata o pagamento à sua maneira
        FormaPagamento forma;
        Pagamento pagamento;
        boolean sucesso;

        switch (opcao) {
            case 1:
                boolean credito = lerTexto("Crédito ou débito? (c/d): ").equalsIgnoreCase("c");
                String bandeira = lerTexto("Bandeira: ");
                pagamento = new Pagamento(credito ? "Cartão de Crédito" : "Cartão de Débito", totalVenda);
                forma = new Cartao(credito, bandeira);
                sucesso = forma.realizarPagamento(pagamento);
                break;
            case 2:
                pagamento = new Pagamento("Pix", totalVenda);
                forma = new Pix();
                sucesso = forma.realizarPagamento(pagamento);
                break;
            case 3:
                double valorRecebido = lerDouble("Valor recebido em dinheiro: ");
                pagamento = new Pagamento("Dinheiro", totalVenda);
                Dinheiro dinheiro = new Dinheiro();
                sucesso = dinheiro.realizarPagamento(pagamento, valorRecebido);
                break;
            default:
                System.out.println("Opção inválida.\n");
                return;
        }

        if (sucesso) {
            vendaAtual.registrarPagamento(pagamento);
        }
        System.out.println();
    }

    // =========================================================
    // UTILITÁRIOS
    // =========================================================
    private static Produto selecionarProduto() {
        if (empresa.getListaProdutos().isEmpty()) {
            System.out.println("[ERRO] Não há produtos cadastrados.\n");
            return null;
        }
        empresa.listarProdutos();
        int id = lerInt("Informe o ID do produto: ");
        for (Produto p : empresa.getListaProdutos()) {
            if (p.getId() == id) return p;
        }
        System.out.println("[ERRO] Produto não encontrado.\n");
        return null;
    }

    private static int lerInt(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = Integer.parseInt(scanner.nextLine().trim());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    private static double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    private static String lerTexto(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }
}
