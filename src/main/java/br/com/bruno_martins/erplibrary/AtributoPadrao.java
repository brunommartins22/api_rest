/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.erplibrary;

import br.com.bruno_martins.padrao.rest.domains.DominioEvento;
import br.com.bruno_martins.padrao.rest.util.Conversor;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Bruno Martins
 */
@Embeddable
public class AtributoPadrao implements Serializable {

    @Column(name = "rgcodusu", nullable = false)
    @Conversor(atributoDeOrigem = "rgcodusu")
    private Long idUsuario;

    @Conversor(atributoDeOrigem = "rgusuario")
    @Column(length = 50, name = "rgusuario", nullable = false)
    private String nomeUsuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rgdata", nullable = false)
    @Conversor(atributoDeOrigem = "rgdata")
    private Date dataRegistro;

    @Column(name = "rgevento", nullable = false)
    @Conversor(atributoDeOrigem = "rgevento")
    private int dominioEvento;

    //**************************************************************************
    /**
     * @return the dataAlteracao
     */
    public Date getDataAlteracao() {
        return dataRegistro;
    }

    /**
     * @param dataAlteracao the dataAlteracao to set
     */
    public void setDataAlteracao(Date dataAlteracao) {
        this.dataRegistro = dataAlteracao;
    }

    /**
     * @return the dominioEvento
     */
    public DominioEvento getDominioEvento() {
        return DominioEvento.getEventoByCodigo(dominioEvento);
    }

    /**
     * @param dominioEvento the dominioEvento to set
     */
    public void setDominioEvento(DominioEvento dominioEvento) {
        this.dominioEvento = dominioEvento.getCodigo();
    }

    /**
     * @return the idUsuario
     */
    public Long getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the nomeUsuario
     */
    public String getNomeUsuario() {
        return nomeUsuario;
    }

    /**
     * @param nomeUsuario the nomeUsuario to set
     */
    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

}
