/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.controller;

import br.com.bruno_martins.padrao.rest.models.Sistema;
import br.com.bruno_martins.padrao.rest.services.SistemaService;
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
@RequestMapping(path = "api/sistemas")
public class SistemaController extends PadraoController<Sistema> {

    //********************** inject service in controller **********************
    @IsServiceDefault
    @Autowired
    private SistemaService service;

    //*************************** endpoint *************************************
    @GetMapping(path = "buscaSistemaContrato/{idEmpresa}/{grupoSistema}")
    public List<Sistema> buscaSistemaContrato(@PathVariable Long idEmpresa, @PathVariable String grupoSistema) {

        List<Sistema> result = this.service.buscaSistemaContrato(idEmpresa, grupoSistema);

        return result;

    }

}
