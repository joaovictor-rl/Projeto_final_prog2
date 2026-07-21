package mercado.usuario;

public abstract class Usuario {
    protected String nome;
    protected String cpf;
    private String senha;

    public Usuario(String nome, String cpf, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public boolean fazerLogin(String login, String senha) {
        boolean autenticado = this.cpf.equals(login) && this.senha.equals(senha);
        if (autenticado) {
            System.out.println("[OK] Login bem-sucedido para " + nome + ".");
        } else {
            System.out.println("[ERRO] CPF ou senha inválidos.");
        }
        return autenticado;
    }

    public void alterarSenha(String novaSenha) {
        this.senha = novaSenha;
        System.out.println("[OK] Senha de " + nome + " alterada com sucesso.");
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(nome=" + nome + ")";
    }
}