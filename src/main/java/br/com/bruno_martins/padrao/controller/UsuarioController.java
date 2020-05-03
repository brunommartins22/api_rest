/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.controller;

import br.com.bruno_martins.padrao.rest.models.Usuario;
import br.com.bruno_martins.padrao.rest.services.UsuarioService;
import br.com.bruno_martins.padrao.rest.util.IsServiceDefault;
import br.com.bruno_martins.padrao.rest.util.PadraoController;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Bruno Martins
 */
@RestController
@RequestMapping(path = "api/usuarios")
public class UsuarioController extends PadraoController<Usuario> {

    @IsServiceDefault
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping(path = "/perfil/{idUsuario}", method = RequestMethod.GET)
    public String getPerfilDoUsuario(@PathVariable("idUsuario") Long idUsuario) {

        return this.serializar(this.usuarioService.getPerfilDoUsuario(idUsuario));

    }

    @GetMapping(path = "/informarTipoMenu/{idUsuario}/{dominioTipoMenu}")
    public void informarTipoMenu(@PathVariable Long idUsuario, @PathVariable String dominioTipoMenu) {

        this.usuarioService.informarTipoMenu(idUsuario, dominioTipoMenu);

    }

    @GetMapping(path = "/informarTipoLayout/{idUsuario}/{dominioTipoLayou}")
    public void informarTipoLayout(@PathVariable Long idUsuario, @PathVariable String dominioTipoLayou) {

        this.usuarioService.informarTipoLayout(idUsuario, dominioTipoLayou);

    }

    @GetMapping(path = "/getPerfilUsuario/{idUsuario}")
    public List<Map> getPerfilUsuario(@PathVariable Long idUsuario) {

        return this.usuarioService.getPerfilUsuario(idUsuario);

    }

    @GetMapping(path = "/alterPasswordUser/{idUsuario}/{senha}")
    public String getAlterPasswordUser(@PathVariable Long idUsuario, @PathVariable String senha) {
        try {
            return this.serializar(this.usuarioService.alterPasswordUser(idUsuario, senha));

        } catch (Exception ex) {

            return this.returnException(ex);

        }
    }

    @GetMapping(path = "/sincronizar/{dataUltimaSincronizacao}/{idEmpresa}")
    public String sincronizar(@PathVariable String dataUltimaSincronizacao, @PathVariable Long idEmpresa) {

        Date dataUltima = null;

        try {

            dataUltima = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(dataUltimaSincronizacao);

        } catch (ParseException ex) {

            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);

        }

        List<Usuario> result = usuarioService.sincronizar(dataUltima, idEmpresa);

        return this.serializar(result);

    }

}
