/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.services;

import br.com.bruno_martins.padrao.rest.models.Empresa;
import br.com.bruno_martins.padrao.rest.util.FiltroParametro;
import br.com.bruno_martins.padrao.rest.util.PadraoService;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class EmpresaService extends PadraoService<Empresa> {

    //************************ create business rules ***************************
    public List<Empresa> findByEmpresa(Long id) {

        return em.createQuery("SELECT o from empresa o "
                + "where o.id = " + id
                + " order by o.nome").getResultList();

    }

    public String getWhere(FiltroParametro filtroParametro) {

        String consultaSQL = "";

        consultaSQL = " o.id = " + filtroParametro.getFilter("empresa").getValue();

        return consultaSQL;
    }

}
