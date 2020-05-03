/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.domains;

import br.com.bruno_martins.padrao.rest.util.DominioPadrao;

/**
 *
 * @author Bruno Martins
 */
public enum DominioTipoDocumento implements DominioPadrao {

    RG("RG"), CPF("CPF"), CNH("CNH");

    private final String descricao;

    private DominioTipoDocumento(String descricao) {

        this.descricao = descricao;

    }

    /**
     * @return the descricao
     */
    public String getDescricao() {

        return descricao;

    }

}
