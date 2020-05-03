/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.services;

import br.com.bruno_martins.padrao.rest.models.Estado;
import br.com.bruno_martins.padrao.rest.util.PadraoService;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class EstadoService extends PadraoService<Estado> {

    public EstadoService() {

        order = "order by o.uf";

    }

}
