/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.controller;

import br.com.bruno_martins.padrao.rest.models.Cidade;
import br.com.bruno_martins.padrao.rest.services.CidadeService;
import br.com.bruno_martins.padrao.rest.util.IsServiceDefault;
import br.com.bruno_martins.padrao.rest.util.PadraoController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bruno Martins
 */
@RestController
@RequestMapping(path = "/api/cidades")
public class CidadeController extends PadraoController<Cidade> {

    @IsServiceDefault
    @Autowired
    private CidadeService service;

    @GetMapping(path = "findByUf/{idUf}")
    public String findByUf(@PathVariable Long idUf) {
      
        List<Cidade> result = service.findByUf(idUf);

        return this.serializar(result);
    }

}
