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
public enum DominioTipoPessoa implements DominioPadrao {

    FISICA("Pessoa Física") {

        @Override
        public String getMascara() {

            return "###.###.###-##";

        }

        @Override
        public DominioTipoPessoa getTipo() {

            return DominioTipoPessoa.FISICA;

        }

    }, JURIDICA("Pessoa Jurídica") {

        @Override
        public String getMascara() {

            return "##.###.###/####-##";

        }

        @Override
        public DominioTipoPessoa getTipo() {

            return DominioTipoPessoa.JURIDICA;

        }

    }, PUBLICA("Pessoa Pública") {

        @Override
        public String getMascara() {

            return "";

        }

        @Override
        public DominioTipoPessoa getTipo() {

            return DominioTipoPessoa.PUBLICA;

        }

    }, ESTRANGEIRA("Estrangeira") {

        @Override
        public String getMascara() {

            return "";

        }

        @Override
        public DominioTipoPessoa getTipo() {

            return DominioTipoPessoa.ESTRANGEIRA;

        }

    };

    private final String descricao;

    private DominioTipoPessoa(String descricao) {

        this.descricao = descricao;

    }

    /**
     * @return the descricao
     */
    @Override
    public String getDescricao() {

        return descricao;

    }

    public abstract String getMascara();

    public abstract DominioTipoPessoa getTipo();

}
