package br.com.bruno_martins.padrao.rest.models;

import java.util.Date;

/**
 *
 * @author Bruno Martins
 */
public class UsuarioLogado {

    private Long usuarioId;
    private String sessionId;
    private Date dataValidade;
    private Integer tempoSessao;
    private String nome;
    private String login;

    public UsuarioLogado(String sessionId, Usuario usuario) {

        this.usuarioId = usuario.getId();
        this.sessionId = sessionId;
        this.tempoSessao = usuario.getTempoSessao() == null ? 30 : usuario.getTempoSessao();
        this.dataValidade = new Date(System.currentTimeMillis() + (getTempoSessao() * 60000));
        this.nome = usuario.getNome();
        this.login = usuario.getLogin();

    }

    //**************************** get && sets *********************************
    public Long getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public boolean isValido() {
        return getDataValidade() != null && getDataValidade().after(new Date());
    }

    public boolean atualizarValidade() {
        if (isValido()) {
            setDataValidade(new Date(System.currentTimeMillis() + (getTempoSessao() * 60000)));
            return true;
        }

        return false;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the tempoSessao
     */
    public Integer getTempoSessao() {
        return tempoSessao;
    }

    /**
     * @param tempoSessao the tempoSessao to set
     */
    public void setTempoSessao(Integer tempoSessao) {
        this.tempoSessao = tempoSessao;
    }

}
