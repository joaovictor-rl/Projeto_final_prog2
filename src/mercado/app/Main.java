package mercado.app;

import mercado.menu.Menu;
import mercado.modelo.Empresa;
import mercado.modelo.Venda;
import mercado.usuario.Usuario;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Empresa empresa = new Empresa(100);
        List<Usuario> usuarios = new ArrayList<>();
        List<Venda> vendas = new ArrayList<>();

        DadosDemo.inicializar(empresa, usuarios, vendas);

        System.out.println("=====================================");
        System.out.println("      SISTEMA DE MERCADO - MENU");
        System.out.println("=====================================");
        System.out.println("Login GERENTE  -> CPF: 00000000000 | Senha: admin");
        System.out.println("Login OPERADOR -> CPF: 22222222222 | Senha: senha123\n");

        Menu menu = new Menu(empresa, usuarios, vendas);
        menu.iniciar();
    }
}