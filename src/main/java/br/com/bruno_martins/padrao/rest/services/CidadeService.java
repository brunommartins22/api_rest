/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.services;

import br.com.bruno_martins.erplibrary.Utils;
import br.com.bruno_martins.padrao.rest.models.Cidade;
import br.com.bruno_martins.padrao.rest.util.PadraoService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class CidadeService extends PadraoService<Cidade> {

    //************************ create business rules ***************************
    public List<Cidade> findByUf(Long idUf) {
        return this.em.createQuery("SELECT o from Cidade o "
                + "where o.cuF.id = " + idUf + " order by o.xmun").getResultList();

    }

    @Override
    public String getWhere(String complementoConsulta) {

        String consultaSQL = "";

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {

            if (Utils.somenteNumeros(complementoConsulta)) {

                consultaSQL = "o.id = '" + complementoConsulta;

            } else {

                consultaSQL = "UPPER (o.xmun) LIKE '%" + complementoConsulta + "%' ";

            }

        }

        return consultaSQL;
    }

}
