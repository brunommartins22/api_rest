/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(schema = "seguranca", name = "sequencia")
public class Sequencia implements Serializable {

    @Id
    private String entidade;
    @NotNull
    private Long sequencia;

    //*************************** equals && hashCode ***************************
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entidade != null ? entidade.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sequencia)) {
            return false;
        }
        Sequencia other = (Sequencia) object;
        if ((this.entidade == null && other.entidade != null) || (this.entidade != null && !this.entidade.equals(other.entidade))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.eurus.erp.pdv.models.PdvSequencia[ id=" + entidade + " ]";
    }

    //**************************** get && sets *********************************
    /**
     * @return the entidade
     */
    public String getEntidade() {
        return entidade;
    }

    /**
     * @param entidade the entidade to set
     */
    public void setEntidade(String entidade) {
        this.entidade = entidade;
    }

    /**
     * @return the sequencia
     */
    public Long getSequencia() {
        return sequencia;
    }

    /**
     * @param sequencia the sequencia to set
     */
    public void setSequencia(Long sequencia) {
        this.sequencia = sequencia;
    }

}
