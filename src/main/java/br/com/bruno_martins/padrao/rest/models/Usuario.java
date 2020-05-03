package br.com.bruno_martins.padrao.rest.models;

import br.com.bruno_martins.erplibrary.AtributoPadrao;
import br.com.bruno_martins.padrao.rest.domains.DominioTipoLayout;
import br.com.bruno_martins.padrao.rest.domains.DominioTipoMenu;
import br.com.bruno_martins.padrao.rest.dto.PermissaoDto;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(schema = "seguranca", name = "usuario")
public class Usuario implements Serializable, UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_usuario")
    @SequenceGenerator(name = "gen_usuario", sequenceName = "seguranca.seq_usuario", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nome;

    @Column(nullable = false, length = 50)
    private String login;

    @Column(length = 55)
    private String senha;

    @Column(length = 55)
    private String codigoAcesso;

    @Transient
    private String senhaInformada;

    private Integer tempoSessao;

    @Embedded
    private AtributoPadrao atributoPadrao;

    @Enumerated(EnumType.STRING)
    private DominioTipoMenu tipoMenu;

    @Enumerated(EnumType.STRING)
    private DominioTipoLayout tipoLayout;

    @Transient
    private List<PermissaoDto> listaPermissao;

    //*************************** equals && hashCode ***************************
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Usuario other = (Usuario) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    //**************************** get && sets *********************************
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

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

    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    // ************************************************************************
    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public String getPassword() {
        return this.getSenha();
    }

    @Override
    public String getUsername() {
        return this.getNome();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Integer getTempoSessao() {
        return tempoSessao;
    }

    public void setTempoSessao(Integer tempoSessao) {
        this.tempoSessao = tempoSessao;
    }

    public List<PermissaoDto> getListaPermissao() {
        return listaPermissao;
    }

    public void setListaPermissao(List<PermissaoDto> listaPermissao) {
        this.listaPermissao = listaPermissao;
    }

    /**
     * @return the tipoMenu
     */
    public DominioTipoMenu getTipoMenu() {
        return tipoMenu;
    }

    /**
     * @param tipoMenu the tipoMenu to set
     */
    public void setTipoMenu(DominioTipoMenu tipoMenu) {
        this.tipoMenu = tipoMenu;
    }

    /**
     * @return the tipoLayout
     */
    public DominioTipoLayout getTipoLayout() {
        return tipoLayout;
    }

    /**
     * @param tipoLayout the tipoLayout to set
     */
    public void setTipoLayout(DominioTipoLayout tipoLayout) {
        this.tipoLayout = tipoLayout;
    }

    /**
     * @return the senhaInformada
     */
    public String getSenhaInformada() {
        return senhaInformada;
    }

    /**
     * @param senhaInformada the senhaInformada to set
     */
    public void setSenhaInformada(String senhaInformada) {
        this.senhaInformada = senhaInformada;
    }

    /**
     * @return the codigoAcesso
     */
    public String getCodigoAcesso() {
        return codigoAcesso;
    }

    /**
     * @param codigoAcesso the codigoAcesso to set
     */
    public void setCodigoAcesso(String codigoAcesso) {
        this.codigoAcesso = codigoAcesso;
    }

}
