/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.controller;

import br.com.bruno_martins.padrao.rest.dto.PermissaoDto;
import br.com.bruno_martins.padrao.rest.models.Permissao;
import br.com.bruno_martins.padrao.rest.services.PermissaoService;
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
@RequestMapping(path = "/api/permissoes")
public class PermissaoController extends PadraoController<Permissao> {

    @IsServiceDefault
    @Autowired
    private PermissaoService service;

    @GetMapping(path = "/buscaPermissaoSistema/{idEmpresa}/{idUsuario}/{idsSistemas}")
    public String buscaPermissaoSistema(@PathVariable Long idEmpresa,
            @PathVariable String idUsuario,
            @PathVariable String idsSistemas) {

        Long idUsu = null;

        if (!idUsuario.equals("null") && !idUsuario.equals("undefined") && !idUsuario.equals("")) {

            idUsu = Long.parseLong(idUsuario);

        }

        try {

            List<PermissaoDto> result = this.service.buscaPermissaoSistema(idEmpresa, idUsu, idsSistemas);

            return this.serializar(result);

        } catch (Exception ex) {

            return returnException(ex);

        }
    }

}
