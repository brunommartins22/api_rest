/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.controller;

import br.com.bruno_martins.padrao.rest.models.Contrato;
import br.com.bruno_martins.padrao.rest.services.ContratoService;
import br.com.bruno_martins.padrao.rest.util.IsServiceDefault;
import br.com.bruno_martins.padrao.rest.util.PadraoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bruno Martins
 */
@RestController
@RequestMapping(path = "/api/contratos")
public class ContratoController extends PadraoController<Contrato> {

    @IsServiceDefault
    @Autowired
    private ContratoService service;

    @GetMapping(path = "/buscaEmpresaContrato/{grupoSistema}")
    public String buscaEmpresaContrato(@PathVariable("grupoSistema") String grupoSistema) {

        try {

            return this.serializar(this.service.buscaEmpresaContrato(grupoSistema));

        } catch (Exception ex) {

            return returnException(ex);

        }

    }

    @GetMapping(path = "/isPrimeiroAcesso")
    public boolean isPrimeiroAcesso() {

        return this.service.isPrimeiroAcesso();

    }

    @PostMapping(path = "/l")
    public String liberacao(@RequestBody String documento) {

        try {

            return this.serializar(this.service.checkLiberacaoOnline(documento));

        } catch (Exception ex) {

            ex.printStackTrace();
            //FIXME - tem que gerar log de erro, mas não erro de validação
            //Logger.getLogger(PadraoController.class.getName()).log(Level.SEVERE, null, ex);
            return returnException(ex);

        }
    }

}
