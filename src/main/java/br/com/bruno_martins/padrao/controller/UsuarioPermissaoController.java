/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.controller;

import br.com.bruno_martins.padrao.rest.models.UsuarioPermissao;
import br.com.bruno_martins.padrao.rest.services.UsuarioPermissaoService;
import br.com.bruno_martins.padrao.rest.util.IsServiceDefault;
import br.com.bruno_martins.padrao.rest.util.PadraoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bruno Martins
 */
@RestController
@RequestMapping(path = "api/usuariopermissoes")
public class UsuarioPermissaoController extends PadraoController<UsuarioPermissao> {

    @IsServiceDefault
    @Autowired
    private UsuarioPermissaoService usuarioPermissaoService;

}
