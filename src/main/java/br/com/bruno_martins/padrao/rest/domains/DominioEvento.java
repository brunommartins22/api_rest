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
public enum DominioEvento implements DominioPadrao {

    I("Inserido", 1), A("Alterado", 2), E("Excluido", 3);

    private String descricao;

    private int codigo;

    private DominioEvento(String descricao, int codigo) {

        this.descricao = descricao;
        this.codigo = codigo;

    }

    @Override
    public String getDescricao() {

        return descricao;

    }

    public static DominioEvento getEventoByCodigo(int codigo) {

        DominioEvento[] eventos = DominioEvento.values();

        for (DominioEvento evento : eventos) {

            if (evento.getCodigo() == codigo) {

                return evento;

            }

        }

        return null;

    }

    /**
     * @return the codigo
     */
    public int getCodigo() {

        return codigo;

    }

}
