/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
@Table(schema = "seguranca", name = "permissao_sistema")
public class PermissaoSistema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_permissao_sistema")
    @SequenceGenerator(name = "gen_permissao_sistema", sequenceName = "seguranca.seq_permissao_sistema", initialValue = 0, allocationSize = 1)
    private Long id;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "permissaoSistema_fk_permissao"))
    private Permissao permissao;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "permissaoSistema_fk_sistema"))
    private Sistema sistema;

    //*************************** equals && hashCode ***************************
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
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
        final PermissaoSistema other = (PermissaoSistema) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PermissaoSistema{" + "id=" + id + ", permissao=" + permissao + ", sistema=" + sistema + '}';
    }

    //**************************** get && sets *********************************
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Permissao getPermissao() {
        return permissao;
    }

    public void setPermissao(Permissao permissao) {
        this.permissao = permissao;
    }

    public Sistema getSistema() {
        return sistema;
    }

    public void setSistema(Sistema sistema) {
        this.sistema = sistema;
    }

}
