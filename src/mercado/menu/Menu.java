package mercado.menu;

import mercado.modelo.Empresa;
import mercado.modelo.ItemCarrinho;
import mercado.modelo.Produto;
import mercado.modelo.Venda;
import mercado.pagamento.*;
import mercado.usuario.Gerente;
import mercado.usuario.OperadorCaixa;
import mercado.usuario.Usuario;

import java.util.List;

public class Menu {
    private final Empresa empresa;
    private final List<Usuario> usuarios;
    private final List<Venda> vendas;

    private Usuario usuarioLogado;
    private Venda vendaAtual;

    public Menu(Empresa empresa, List<Usuario> usuarios, List<Venda> vendas) {
        this.empresa = empresa;
        this.usuarios = usuarios;
        this.vendas = vendas;
        this.usuarioLogado = null;
        this.vendaAtual = null;
    }

    // =========================================================
    // LOOP PRINCIPAL
    // =========================================================
    public void iniciar() {
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
    // TELA DE LOGIN
    // =========================================================
    private boolean telaLogin() {
        System.out.println("----- LOGIN -----");
        System.out.println("1 - Entrar");
        System.out.println("0 - Sair do sistema");
        int opcao = Utilitarios.lerInt("Escolha: ");

        if (opcao == 0) {
            return false;
        }
        if (opcao != 1) {
            System.out.println("Opção inválida.\n");
            return true;
        }

        String cpf = Utilitarios.lerTexto("CPF: ");
        String senha = Utilitarios.lerTexto("Senha: ");

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
    private void menuGerente(Gerente gerente) {
        System.out.println("----- MENU GERENTE (" + gerente.getNome() + ") -----");
        System.out.println("1  - Cadastrar funcionário (Operador de caixa)");
        System.out.println("2  - Cadastrar produto");
        System.out.println("3  - Adicionar estoque a um produto");
        System.out.println("4  - Baixar estoque de um produto");
        System.out.println("5  - Editar preço de um produto");
        System.out.println("6  - Remover produto");
        System.out.println("7  - Ativar/Desativar produto");
        System.out.println("8  - Listar produtos");
        System.out.println("9  - Verificar estoque total");
        System.out.println("10 - Emitir relatório de vendas");
        System.out.println("11 - Alterar minha senha");
        System.out.println("12 - Logout");
        System.out.println("0  - Sair do sistema");
        int opcao = Utilitarios.lerInt("Escolha: ");
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
                editarPrecoProduto();
                break;
            case 6:
                removerProduto();
                break;
            case 7:
                ativarDesativarProduto();
                break;
            case 8:
                empresa.listarProdutos();
                break;
            case 9:
                empresa.verificarEstoque(empresa.getListaProdutos());
                break;
            case 10:
                gerente.emitirRelatorioVendas(vendas);
                break;
            case 11:
                String novaSenha = Utilitarios.lerTexto("Nova senha: ");
                gerente.alterarSenha(novaSenha);
                break;
            case 12:
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

    private void cadastrarFuncionario(Gerente gerente) {
        String nome = Utilitarios.lerTexto("Nome do operador: ");
        String cpf = Utilitarios.lerTexto("CPF: ");
        String senha = Utilitarios.lerTexto("Senha: ");
        int idOperador = usuarios.size() + 1;
        OperadorCaixa operador = new OperadorCaixa(nome, cpf, senha, idOperador);
        usuarios.add(operador);
        gerente.cadastrarFuncionario(operador);
        System.out.println();
    }

    private void cadastrarProduto() {
        String nome = Utilitarios.lerTexto("Nome do produto: ");
        double preco = Utilitarios.lerDouble("Preço base: ");
        String descricao = Utilitarios.lerTexto("Descrição: ");
        int estoqueInicial = Utilitarios.lerInt("Quantidade inicial em estoque: ");
        Produto produto = new Produto(nome, preco, descricao, 0);
        empresa.adicionarProduto(produto);
        if (estoqueInicial > 0) {
            produto.adicionarEstoque(estoqueInicial);
        }
        System.out.println();
    }

    private void alterarEstoque(boolean adicionar) {
        Produto produto = Utilitarios.selecionarProduto(empresa.getListaProdutos(), false);
        if (produto == null) return;
        int quantidade = Utilitarios.lerInt("Quantidade: ");
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

    private void editarPrecoProduto() {
        Produto produto = Utilitarios.selecionarProduto(empresa.getListaProdutos(), false);
        if (produto == null) return;
        double novoPreco = Utilitarios.lerDouble("Novo preço: ");
        try {
            produto.atualizarPreco(novoPreco);
        } catch (RuntimeException e) {
            System.out.println("[ERRO] " + e.getMessage());
        }
        System.out.println();
    }

    private void removerProduto() {
        Produto produto = Utilitarios.selecionarProduto(empresa.getListaProdutos(), false);
        if (produto == null) return;
        empresa.deletarProduto(produto);
        System.out.println();
    }

    private void ativarDesativarProduto() {
        Produto produto = Utilitarios.selecionarProduto(empresa.getListaProdutos(), false);
        if (produto == null) return;
        if (produto.getStatus()) {
            produto.desativar();
        } else {
            produto.ativar();
        }
        System.out.println();
    }

    // =========================================================
    // MENU DO OPERADOR DE CAIXA
    // =========================================================
    private void menuOperador(OperadorCaixa operador) {
        System.out.println("----- MENU OPERADOR DE CAIXA (" + operador.getNome() + ") -----");
        if (vendaAtual == null) {
            System.out.println("(Nenhuma venda em aberto! Selecione 1)");
        } else {
            boolean pagamentoConcluido = vendaAtual.getPagamento() != null && vendaAtual.getPagamento().getStatus();
            String statusPagamento = pagamentoConcluido ? "Concluído" : "Aguardando";
            System.out.printf("Venda #%d em aberto | Total: R$ %.2f | Desconto aplicado: R$ %.2f | Pagamento: %s%n",
                    vendaAtual.getId(), vendaAtual.getTotal(), vendaAtual.getDesconto(), statusPagamento);
        }
        System.out.println("1 - Iniciar nova venda");
        System.out.println("2 - Registrar item na venda");
        System.out.println("3 - Remover item da venda");
        System.out.println("4 - Alterar quantidade de um item");
        System.out.println("5 - Aplicar desconto");
        System.out.println("6 - Registrar pagamento");
        System.out.println("7 - Emitir recibo");
        System.out.println("8 - Finalizar venda");
        System.out.println("9 - Logout");
        System.out.println("0 - Sair do sistema");
        int opcao = Utilitarios.lerInt("Escolha: ");
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
                alterarQuantidadeItemNaVenda();
                break;
            case 5:
                aplicarDesconto();
                break;
            case 6:
                registrarPagamento();
                break;
            case 7:
                if (vendaAtual != null) vendaAtual.emitirRecibo();
                else System.out.println("[ERRO] Nenhuma venda em aberto.\n");
                break;
            case 8:
                if (vendaAtual == null) {
                    System.out.println("[ERRO] Nenhuma venda em aberto.\n");
                } else if (vendaAtual.getPagamento() == null || !vendaAtual.getPagamento().getStatus()) {
                    System.out.println("[ERRO] Pagamento ainda não realizado.\n");
                } else {
                    for (ItemCarrinho item : vendaAtual.getCarrinho().getListaItens()) {
                        Produto p = item.getProduto();
                        int qtdVendida = item.getQuantidade();
                        p.baixarEstoque(qtdVendida);
                    }
                    operador.finalizarVenda(vendaAtual);
                    vendaAtual = null;
                    System.out.println("[OK] Venda finalizada e estoque atualizado com sucesso!");
                }
                System.out.println();
                break;
            case 9:
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

    private void registrarItemNaVenda(OperadorCaixa operador) {
        if (vendaAtual == null) {
            System.out.println("[ERRO] Inicie uma venda primeiro.\n");
            return;
        }

        Produto produto = Utilitarios.selecionarProduto(empresa.getListaProdutos(), true);
        if (produto == null) return;
        int quantidadeDesejada = Utilitarios.lerInt("Quantidade: ");
        int qtdJaNoCarrinho = 0;
        for (ItemCarrinho item : vendaAtual.getCarrinho().getListaItens()) {
            if (item.getProduto() == produto) {
                qtdJaNoCarrinho = item.getQuantidade();
                break;
            }
        }
        int totalNecessario = quantidadeDesejada + qtdJaNoCarrinho;
        if (produto.getQuantidadeEstoque() < totalNecessario) {
            System.out.println("[ERRO] Estoque insuficiente! Você tem " + produto.getQuantidadeEstoque() +
                    " em estoque, mas a venda exige " + totalNecessario + ".\n");
            return;
        }
        operador.registrarItem(vendaAtual, produto, quantidadeDesejada);
        System.out.println();
    }

    private void removerItemDaVenda() {
        if (vendaAtual == null) {
            System.out.println("[ERRO] Nenhuma venda em aberto.\n");
            return;
        }
        Produto produto = Utilitarios.selecionarProduto(empresa.getListaProdutos(), false);
        if (produto == null) return;
        vendaAtual.removerItem(produto);
        System.out.println();
    }

    private void alterarQuantidadeItemNaVenda() {
        if (vendaAtual == null) {
            System.out.println("[ERRO] Nenhuma venda em aberto.\n");
            return;
        }
        Produto produto = Utilitarios.selecionarProduto(empresa.getListaProdutos(), false);
        if (produto == null) return;
        int novaQuantidade = Utilitarios.lerInt("Nova quantidade (0 remove o item): ");
        if (novaQuantidade > produto.getQuantidadeEstoque()) {
            System.out.println("[ERRO] Estoque insuficiente! Disponível: " + produto.getQuantidadeEstoque() + ".\n");
            return;
        }
        vendaAtual.atualizarQuantidadeItem(produto, novaQuantidade);
        System.out.println();
    }

    private void aplicarDesconto() {
        if (vendaAtual == null) {
            System.out.println("[ERRO] Nenhuma venda em aberto.\n");
            return;
        }
        double valor = Utilitarios.lerDouble("Valor do desconto: ");
        vendaAtual.aplicarDesconto(valor);
        System.out.println();
    }

    private void registrarPagamento() {
        if (vendaAtual == null) {
            System.out.println("[ERRO] Nenhuma venda em aberto.\n");
            return;
        }

        double totalVenda = vendaAtual.getTotal();

        System.out.println("Formas de pagamento:");
        System.out.println("1 - Cartão");
        System.out.println("2 - Pix");
        System.out.println("3 - Dinheiro");
        int opcao = Utilitarios.lerInt("Escolha: ");

        FormaPagamento forma;
        Pagamento pagamento;
        boolean sucesso;

        switch (opcao) {
            case 1:
                boolean credito = Utilitarios.lerTexto("Crédito ou débito? (c/d): ").equalsIgnoreCase("c");
                String bandeira = Utilitarios.lerTexto("Bandeira: ");
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
                double valorRecebido = Utilitarios.lerDouble("Valor recebido em dinheiro: ");
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
}