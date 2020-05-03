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
public enum DominioTipoDesconto implements DominioPadrao {

    VALOR("Valor"), PERCENTUAL("Percental");

    private final String descricao;

    private DominioTipoDesconto(String descricao) {

        this.descricao = descricao;

    }

    /**
     * @return the descricao
     */
    public String getDescricao() {

        return descricao;

    }

}
