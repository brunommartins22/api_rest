/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.services;

import br.com.bruno_martins.padrao.rest.dto.PermissaoDto;
import br.com.bruno_martins.padrao.rest.models.Permissao;
import br.com.bruno_martins.padrao.rest.util.PadraoService;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class PermissaoService extends PadraoService<Permissao> {

    @Autowired
    private PermissaoService permissaoService;

    public List<PermissaoDto> buscaPermissaoSistema(Long idEmpresa, Long idUsuario, String idsSistemas) throws Exception {
        Query query = em.createNativeQuery("SELECT DISTINCT "
                + "  p.id,  p.descricao_permissao, p.dominio_tipo_permissao "
                + "  FROM seguranca.permissao_sistema ps"
                + "  INNER JOIN seguranca.permissao p ON (ps.permissao_id = p.id)"
                + "  where ps.sistema_id in (" + idsSistemas + ") order by p.id");
        List<Object[]> lista = query.getResultList();
        //List<PermissaoDto> result = new TransformNativeQuery(PermissaoDto.class).execute(lista);
        List<PermissaoDto> result2 = new ArrayList<>();
        for (Object[] permissaoDto : lista) {
            PermissaoDto p = new PermissaoDto();

            p.setId((String) permissaoDto[0]);
            p.setDescricao((String) permissaoDto[1]);
            p.setDominioTipoPermissao((String) permissaoDto[2]);
            p.setPossui(usuarioPossuiPermissao(idEmpresa, p.getId(), idUsuario));
            p.setIdEmpresa(idEmpresa);
            result2.add(p);
        }
        return result2;

    }

    public boolean usuarioPossuiPermissao(Long empresaId, String permissaoId, Long usuarioId) {
        Query query = em.createNativeQuery("select 1 from seguranca.usuario_permissao up "
                + "WHERE up.empresa_id  = " + empresaId + " and up.permissao_id = '" + permissaoId + "' and up.usuario_id = " + usuarioId);
        List lista = query.getResultList();
        return !lista.isEmpty();
    }

}
