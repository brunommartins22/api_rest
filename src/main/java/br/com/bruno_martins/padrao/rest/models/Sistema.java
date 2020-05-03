/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.models;

import br.com.bruno_martins.padrao.rest.domains.DominioGrupoSistema;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(schema = "seguranca", name = "sistema")
public class Sistema implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_sistema")
    @SequenceGenerator(name = "gen_sistema", sequenceName = "seguranca.seq_sistema", initialValue = 0, allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 50)
    private String nomeSistema;

    @Enumerated(EnumType.STRING)
    private DominioGrupoSistema dominioGrupoSistema;

    //*************************** equals && hashCode ***************************
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sistema)) {
            return false;
        }
        Sistema other = (Sistema) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.interagese.padrao.rest.models.Sistema[ id=" + getId() + " ]";
    }

    //**************************** get && sets *********************************
    /**
     * @return the nomeSistema
     */
    public String getNomeSistema() {
        return nomeSistema;
    }

    /**
     * @param nomeSistema the nomeSistema to set
     */
    public void setNomeSistema(String nomeSistema) {
        this.nomeSistema = nomeSistema;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DominioGrupoSistema getDominioGrupoSistema() {
        return dominioGrupoSistema;
    }

    public void setDominioGrupoSistema(DominioGrupoSistema dominioGrupoSistema) {
        this.dominioGrupoSistema = dominioGrupoSistema;
    }

}
