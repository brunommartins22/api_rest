/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.services;

import br.com.bruno_martins.padrao.rest.dto.PermissaoDto;
import br.com.bruno_martins.padrao.rest.models.Usuario;
import br.com.bruno_martins.padrao.rest.models.UsuarioPermissao;
import br.com.bruno_martins.padrao.rest.util.PadraoService;
import javax.persistence.Query;

import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class UsuarioPermissaoService extends PadraoService<UsuarioPermissao> {

    public void create(Usuario usuario) throws Exception {

        if (usuario.getListaPermissao() == null) {
            return;
        }

        Query queryInsert = em.createNativeQuery("INSERT INTO seguranca.usuario_permissao(id, empresa_id, permissao_id, usuario_id) "
                + " VALUES(nextval('seguranca.seq_usuario_permissao'),:empresa_id, :permissao_id, :usuario_id )");

        Query queryDelete = em.createNativeQuery("DELETE FROM seguranca.usuario_permissao "
                + "where empresa_id = :empresa_id and  "
                + "permissao_id = :permissao_id and "
                + "usuario_id = :usuario_id");

        for (PermissaoDto permissaoDto : usuario.getListaPermissao()) {
            if ((permissaoDto.isPossui())) { //marcou 
                boolean existePermissao = existePermissao(permissaoDto.getIdEmpresa(), permissaoDto.getId(), usuario.getId());

                if (!existePermissao) {

                    queryInsert.setParameter("empresa_id", permissaoDto.getIdEmpresa());
                    queryInsert.setParameter("permissao_id", permissaoDto.getId());
                    queryInsert.setParameter("usuario_id", usuario.getId());
                    queryInsert.executeUpdate();
                }

            } else {//desmarcou

                queryDelete.setParameter("empresa_id", permissaoDto.getIdEmpresa());
                queryDelete.setParameter("permissao_id", permissaoDto.getId());
                queryDelete.setParameter("usuario_id", usuario.getId());
                queryDelete.executeUpdate();

            }
        }
    }

    public boolean existePermissao(Long empresa_id, String permissao_id, Long usuario_id) {

        Query query = em.createNativeQuery("SELECT 1 "
                + "from seguranca.usuario_permissao "
                + "where empresa_id = :empresa_id and  "
                + "permissao_id     = :permissao_id and "
                + "usuario_id       = :usuario_id ");

        query.setParameter("empresa_id", empresa_id);
        query.setParameter("permissao_id", permissao_id);
        query.setParameter("usuario_id", usuario_id);

        return !query.getResultList().isEmpty();
        /* Number max = (Number) query.getSingleResult();
        return max.intValue() > 0;*/
    }
}
