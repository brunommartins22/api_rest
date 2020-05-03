package br.com.bruno_martins.padrao.rest.dto;

/**
 *
 * @author Bruno Martins
 */
public class UserAcessoLiberacao {

    private String login;
    private String senha;
    private String documento;

    public UserAcessoLiberacao(String login, String senha, String documento) {

        super();
        this.login = login;
        this.senha = senha;
        this.documento = documento;

    }

    //**************************** get && sets *********************************    
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

}
