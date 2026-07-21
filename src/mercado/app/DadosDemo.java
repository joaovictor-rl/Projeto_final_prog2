package mercado.app;

import mercado.modelo.Empresa;
import mercado.modelo.Produto;
import mercado.modelo.Venda;
import mercado.pagamento.Pagamento;
import mercado.usuario.Gerente;
import mercado.usuario.OperadorCaixa;
import mercado.usuario.Usuario;

import java.util.List;

public class DadosDemo {

    private DadosDemo() {
    }

    public static void inicializar(Empresa empresa, List<Usuario> usuarios, List<Venda> vendas) {

        Gerente gerenteInicial = new Gerente("Gerente Teste Oliveira", "00000000000", "admin", 1, true);
        OperadorCaixa operadorInicial = new OperadorCaixa("Teste da Silva", "22222222222", "senha123", 1);
        usuarios.add(gerenteInicial);
        usuarios.add(operadorInicial);

        Produto arroz = new Produto("Arroz 5kg", 25.90, "Arroz tipo 1", 0);
        Produto feijao = new Produto("Feijão 1kg", 8.50, "Feijão carioca", 0);
        Produto oleo = new Produto("Óleo de Soja 900ml", 6.50, "Óleo de soja refinado", 0);
        Produto acucar = new Produto("Açúcar 1kg", 4.50, "Açúcar refinado", 0);
        Produto leite = new Produto("Leite 1L", 7.90, "Leite integral", 0);

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

        criarVendaDemo(operadorInicial, vendas, "Dinheiro", new Produto[]{arroz, feijao}, new int[]{2, 3});
        criarVendaDemo(operadorInicial, vendas, "Pix", new Produto[]{leite, acucar}, new int[]{4, 1});
    }

    private static void criarVendaDemo(OperadorCaixa operador, List<Venda> vendas, String formaPagamento,
                                       Produto[] produtos, int[] quantidades) {
        Venda venda = operador.iniciarVenda();
        for (int i = 0; i < produtos.length; i++) {
            operador.registrarItem(venda, produtos[i], quantidades[i]);
        }

        Pagamento pagamento = new Pagamento(formaPagamento, venda.getTotal());
        pagamento.setStatus(true);
        venda.registrarPagamento(pagamento);

        for (int i = 0; i < produtos.length; i++) {
            produtos[i].baixarEstoque(quantidades[i]);
        }

        operador.finalizarVenda(venda);
        vendas.add(venda);
    }
}