/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.controller;

import br.com.bruno_martins.padrao.rest.models.Empresa;
import br.com.bruno_martins.padrao.rest.services.EmpresaService;
import br.com.bruno_martins.padrao.rest.util.IsServiceDefault;
import br.com.bruno_martins.padrao.rest.util.PadraoController;

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
@RequestMapping(path = "api/empresas")
public class EmpresaController extends PadraoController<Empresa> {

    //********************** inject service in controller **********************
    @IsServiceDefault
    @Autowired
    private EmpresaService empresaService;

    //*************************** endpoint *************************************
    @GetMapping(path = "/id/{idEmpresa}")
    public String getEmpresa(@PathVariable("idEmpresa") Long idEmpresa) {

        return this.serializar(this.empresaService.findByEmpresa(idEmpresa));

    }

}
