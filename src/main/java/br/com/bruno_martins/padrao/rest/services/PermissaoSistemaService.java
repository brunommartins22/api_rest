/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.services;

import br.com.bruno_martins.padrao.rest.models.PermissaoSistema;
import br.com.bruno_martins.padrao.rest.util.PadraoService;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class PermissaoSistemaService extends PadraoService<PermissaoSistema> {
    
     public List<PermissaoSistema> loadPermissaoSistema(Long idSistema){
        return em.createQuery("SELECT o FROM PermissaoSistema o WHERE o.sistema.id").getResultList();
    }
    
    

}
