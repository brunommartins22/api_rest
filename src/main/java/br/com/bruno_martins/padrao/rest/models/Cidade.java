package br.com.bruno_martins.padrao.rest.models;

import br.com.bruno_martins.erplibrary.AtributoPadrao;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(schema = "seguranca", name = "cidade")
public class Cidade implements Serializable {

    @Id   
    @Column(name = "cMun")
    private Long id;
    
    @NotNull
    private String xmun;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Estado cuF;
    
    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();
    
    

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
        if (!(object instanceof Cidade)) {
            return false;
        }
        Cidade other = (Cidade) object;
        if ((this.getId() == null && other.getId() != null) || (this.getId() != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "br.com.eurus.erp.padrao.models.Cidade[ id=" + getId() + " ]";
    }
    
    

    //**************************** get && sets *********************************
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
     * @return the xmun
     */
    public String getXmun() {
        return xmun;
    }

    /**
     * @param xmun the xmun to set
     */
    public void setXmun(String xmun) {
        this.xmun = xmun;
    }

    /**
     * @return the cuF
     */
    public Estado getCuF() {
        return cuF;
    }

    /**
     * @param cuF the cuF to set
     */
    public void setCuF(Estado cuF) {
        this.cuF = cuF;
    }

    /**
     * @return the atributoPadrao
     */
    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    /**
     * @param atributoPadrao the atributoPadrao to set
     */
    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

}
