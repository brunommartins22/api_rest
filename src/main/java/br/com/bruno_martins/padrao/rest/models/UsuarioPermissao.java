package br.com.bruno_martins.padrao.rest.models;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(schema = "seguranca", name = "usuario_permissao")
public class UsuarioPermissao implements Serializable {

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_usuario_permissao")
    @SequenceGenerator(name = "gen_usuario_permissao", sequenceName = "seguranca.seq_usuario_permissao", initialValue = 0, allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "usuarioPermissao_fk_usuario"))
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "usuarioPermissao_fk_permissao"))
    private Permissao permissao;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "usuarioPermissao_fk_empresa"))
    private Empresa empresa;

    //*************************** equals && hashCode ***************************
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.getId());
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
        final UsuarioPermissao other = (UsuarioPermissao) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    //**************************** get && sets *********************************
    /**
     * @return the serialVersionUID
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * @param aSerialVersionUID the serialVersionUID to set
     */
    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * @return the permissao
     */
    public Permissao getPermissao() {
        return permissao;
    }

    /**
     * @param permissao the permissao to set
     */
    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    /**
     * @return the empresa
     */
    public Empresa getEmpresa() {
        return empresa;
    }

    /**
     * @param empresa the empresa to set
     */
    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

}
