
package br.com.bruno_martins.padrao.rest.services;

import br.com.bruno_martins.padrao.rest.domains.DominioGrupoSistema;
import br.com.bruno_martins.padrao.rest.models.Empresa;
import br.com.bruno_martins.padrao.rest.models.Sistema;
import br.com.bruno_martins.padrao.rest.util.PadraoService;
import java.util.List;
import javax.persistence.Query;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class SistemaService extends PadraoService<Sistema> {

    //************************ create business rules ***************************
    public List<Empresa> findBySistema(Long id) {
        return em.createQuery("SELECT o from sistema o "
                + "where o.id = " + id + " order by o.nome_sistema").getResultList();
    }
    
    
     public List<Sistema> buscaSistemaContrato(Long idEmpresa, String grupoSistema){        
        Query query = em.createQuery("SELECT s " +
                                     "FROM Sistema s " +
                                     "where s.id in(SELECT sistema.id FROM Contrato where empresa.id = "+idEmpresa+" ) and s.dominioGrupoSistema = '"+DominioGrupoSistema.valueOf(grupoSistema)+"' order by nomeSistema");         
        List<Sistema> lista = query.getResultList();
        return lista;   
    }
     
     

}
