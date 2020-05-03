/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.models;

import br.com.bruno_martins.padrao.rest.domains.DominioTipoPermissao;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(schema = "seguranca", name = "permissao")
public class Permissao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;

    @Column(nullable = false, length = 80)
    private String descricaoPermissao;

    @Column(nullable = true, length = 10)
    @Enumerated(EnumType.STRING)
    private DominioTipoPermissao dominioTipoPermissao;

    //*************************** equals && hashCode ***************************
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Permissao)) {
            return false;
        }
        Permissao other = (Permissao) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.padrao.rest.models.Permissao[ id=" + id + " ]";
    }

    //**************************** get && sets *********************************
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricaoPermissao() {
        return descricaoPermissao;
    }

    public void setDescricaoPermissao(String descricaoPermissao) {
        this.descricaoPermissao = descricaoPermissao;
    }

    public DominioTipoPermissao getDominioTipoPermissao() {
        return dominioTipoPermissao;
    }

    public void setDominioTipoPermissao(DominioTipoPermissao dominioTipoPermissao) {
        this.dominioTipoPermissao = dominioTipoPermissao;
    }

}
