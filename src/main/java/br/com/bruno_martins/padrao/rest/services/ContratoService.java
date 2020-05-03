package br.com.bruno_martins.padrao.rest.services;

import br.com.bruno_martins.erplibrary.AtributoPadrao;
import br.com.bruno_martins.erplibrary.EnderecoPadrao;
import br.com.bruno_martins.erplibrary.Utils;
import br.com.bruno_martins.padrao.rest.domains.DominioEvento;
import br.com.bruno_martins.padrao.rest.dto.EmpresaDto;
import br.com.bruno_martins.padrao.rest.dto.UserAcessoLiberacao;
import br.com.bruno_martins.padrao.rest.models.Contrato;
import br.com.bruno_martins.padrao.rest.models.Empresa;
import br.com.bruno_martins.padrao.rest.models.Sistema;
import br.com.bruno_martins.padrao.rest.util.PadraoService;
import br.com.bruno_martins.padrao.rest.util.TransformNativeQuery;
import br.com.bruno_martins.padrao.rest.domains.DominioTipoPessoa;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class ContratoService extends PadraoService<Contrato> {

    @Autowired
    private EmpresaService empresaService;

    public List<Contrato> getContratoByEmpresas(List<Empresa> empresas) {

        String hql = "SELECT "
                + "c FROM "
                + "Contrato c "
                + "WHERE "
                + "c.empresa.id IN :idEmpresas ";

        List<Long> idsEmpresas = empresas.stream()
                .map(Empresa::getId)
                .collect(Collectors.toList());

        TypedQuery<Contrato> query = em.createQuery(hql, Contrato.class);
        query.setParameter("idEmpresas", idsEmpresas);
        return query.getResultList();

    }

    public List<EmpresaDto> buscaEmpresaContrato(String grupoSistema) throws Exception {

        Query query = em.createNativeQuery("SELECT e.id, e.razao_social, "
                + " e.fantasia, e.cnpj_cpf, e.ie_rg "
                + "FROM seguranca.empresa e "
                + "where e.id in(SELECT empresa_id FROM seguranca.contrato c "
                + "JOIN seguranca.sistema s on (s.id = c.sistema_id) "
                + "where s.dominio_grupo_sistema = '" + grupoSistema + "') ");

        List lista = query.getResultList();

        try {
            List<EmpresaDto> result = new TransformNativeQuery(EmpresaDto.class).execute(lista);
            return result;
        } catch (InstantiationException | IllegalAccessException ex) {
            addErro(ex.toString());
            return null;
        }

    }

    public boolean isPrimeiroAcesso() {
        return (((Number) em.createNativeQuery("SELECT count(*) from seguranca.contrato  where sistema_id = 5  and empresa_id is not null").getSingleResult()).intValue() < 1);
    }

    public Boolean checkLiberacaoOnline(String documento) throws Exception {

        String baseUrl = "http://www.interagese.com.br/validacao/rest.php";

        String response = this.getClientService(baseUrl).request(MediaType.APPLICATION_JSON).post(Entity.json(new UserAcessoLiberacao("WEBSERV", "webservice", documento))).readEntity(String.class);

        response = Utils.liberacaoDecode(7, response.substring(3));

        Map<String, String> arquivo = Utils.getDecodificacaoLiberacao(response);

        if (arquivo.get("LIBPROMO") != null && arquivo.get("LIBPROMO").equals("S")) {

            AtributoPadrao rg = new AtributoPadrao();
            rg.setIdUsuario(1L);
            rg.setNomeUsuario("LIBERACAO");
            rg.setDominioEvento(DominioEvento.I);
            rg.setDataAlteracao(new Date());

            // Criar empresa
            Empresa empresa = new Empresa();

            empresa.setRazaoSocial(arquivo.get("EMPRESA"));
            empresa.setCnpjCpf(arquivo.get("CNPJEMP"));
            empresa.setIeRg(arquivo.get("IEEMP"));

            if (empresa.getCnpjCpf().length() > 14) {
                empresa.setTipoPessoa(DominioTipoPessoa.JURIDICA);
            } else {
                empresa.setTipoPessoa(DominioTipoPessoa.FISICA);
            }

            empresa.setEndereco(new EnderecoPadrao());
            empresa.getEndereco().setEndereco(arquivo.get("ENDEMP"));
            empresa.getEndereco().setNumero(arquivo.get("NUMEROEMP"));
            empresa.getEndereco().setBairro(arquivo.get("BAIRROEMP"));
            empresa.getEndereco().setCep(arquivo.get("CEPEMP"));
            empresa.getEndereco().setFone1(arquivo.get("FONEEMP"));
            empresa.getEndereco().setEmail(arquivo.get("EMAILEMP"));

            empresa.setAtributoPadrao(rg);

            empresa = this.empresaService.create(empresa);
            Sistema sistema = em.find(Sistema.class, 5L);

            Contrato contrato = new Contrato();
            contrato.setEmpresa(empresa);
            contrato.setSistema(sistema);

            super.create(contrato);

            return true;
        }

//        }
        return false;
    }

}
