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
public enum DominioAtivo implements DominioPadrao {

    S("SIM"), N("NÃO");

    private final String descricao;

    private DominioAtivo(String descricao) {

        this.descricao = descricao;
    }

    @Override
    public String getDescricao() {

        return this.descricao;

    }

}
