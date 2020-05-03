/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.services;

import br.com.bruno_martins.erplibrary.Utils;
import br.com.bruno_martins.padrao.rest.domains.DominioEvento;
import br.com.bruno_martins.padrao.rest.dto.EmpresaPermissaoDto;
import br.com.bruno_martins.padrao.rest.models.Contrato;
import br.com.bruno_martins.padrao.rest.models.Empresa;
import br.com.bruno_martins.padrao.rest.models.Usuario;
import br.com.bruno_martins.padrao.rest.models.UsuarioPermissao;
import br.com.bruno_martins.padrao.rest.util.FiltroParametro;
import br.com.bruno_martins.padrao.rest.util.PadraoService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class UsuarioService extends PadraoService<Usuario> implements UserDetailsService {

    @Autowired
    private UsuarioPermissaoService usuarioPermissaoService;

    @Autowired
    private ContratoService contratoService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = searchUserByName(username);

        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não localizado");
        }

        return usuario;

    }

    private Usuario searchUserByName(String login) {

        //busca pelo campop novo (login)
        String hql = "SELECT u FROM Usuario u WHERE u.login = :usuario and u.atributoPadrao.dominioEvento <> '3'";
        TypedQuery<Usuario> query = em.createQuery(hql, Usuario.class);
        query.setParameter("usuario", login.trim().toUpperCase());

        try {
            return query.getSingleResult();
        } catch (NonUniqueResultException | NoResultException e) {
            return null;
        }

    }

    public List<UsuarioPermissao> getPermissoesDoUsuario(Long idUsuario) {
        String hql = "SELECT "
                + "u "
                + "FROM "
                + "UsuarioPermissao u "
                + "WHERE "
                + "u.usuario.id = :id ";

        TypedQuery<UsuarioPermissao> query = em.createQuery(hql, UsuarioPermissao.class);
        query.setParameter("id", idUsuario);
        return query.getResultList();
    }

    public List< EmpresaPermissaoDto> getPerfilDoUsuario(Long idUsuario) {

        List<UsuarioPermissao> permissoesDoUsuario = getPermissoesDoUsuario(idUsuario);

        List<Empresa> empresas = distinctEmpresas(permissoesDoUsuario);

        List<Contrato> contratos = (empresas == null || empresas.isEmpty()) ? new ArrayList<>() : contratoService.getContratoByEmpresas(empresas);

        List<EmpresaPermissaoDto> result = new ArrayList();

        List<Long> empresasId = mapEmpresasId(empresas);

        for (Long empresaId : empresasId) {
            EmpresaPermissaoDto empresaPermissaoDto = new EmpresaPermissaoDto(mapPermissoesByEmpresa(permissoesDoUsuario, empresaId),
                    mapIdSistemasByEmpresa(contratos, empresaId));

            empresaPermissaoDto.setIdEmpresa(empresaId);
            result.add(empresaPermissaoDto);
        }

        return result;
    }

    private List<Empresa> distinctEmpresas(List<UsuarioPermissao> usuarioPermissoes) {
        return usuarioPermissoes.stream()
                .map(UsuarioPermissao::getEmpresa)
                .distinct()
                .collect(Collectors.toList());
    }

    private List<Long> mapEmpresasId(List<Empresa> empresas) {
        return empresas.stream()
                .map((e) -> {
                    return e.getId();
                }).collect(Collectors.toList());
    }

    private List<Long> mapIdSistemasByEmpresa(List<Contrato> contratos, Long idEmpresa) {
        return contratos.stream()
                .filter((c) -> {
                    return c.getEmpresa().getId().equals(idEmpresa);
                })
                .map((c) -> {
                    return c.getSistema().getId();
                })
                .collect(Collectors.toList());
    }

    private List<String> mapPermissoesByEmpresa(List<UsuarioPermissao> permissoes, Long idEmpresa) {

        return permissoes.stream()
                .filter((u) -> {
                    return u.getEmpresa().getId().equals(idEmpresa);
                })
                .map((u) -> {
                    return u.getPermissao().getId();
                })
                .collect(Collectors.toList());
    }

    @Override
    public String getWhere(String complementoConsulta) {

        String consultaSQL = "";

        if (complementoConsulta != null && !complementoConsulta.trim().equals("")) {
            if (Utils.somenteNumeros(complementoConsulta)) {
                consultaSQL = " o.id = '" + complementoConsulta;
            } else {
                consultaSQL = " o.nome  LIKE '%" + complementoConsulta + "%' or "
                        + " o.login LIKE '%" + complementoConsulta + "%' ";
            }
        }

        return consultaSQL;
    }

    public boolean existeNome(Usuario usuario) {

        String sqlComplementar = "";
        if (usuario.getId() != null) {
            sqlComplementar = " and o.id <> :id ";
        }

        Query query = em.createQuery("SELECT o from Usuario o where o.nome = :nome " + sqlComplementar + " and o.atributoPadrao.dominioEvento <> '3'");

        query.setParameter("nome", usuario.getNome());

        if (usuario.getId() != null) {
            query.setParameter("id", usuario.getId());
        }

        List<Usuario> lista = query.getResultList();

        return !lista.isEmpty();

    }

    public boolean existeLogin(Usuario usuario) {

        String sqlComplementar = "";
        if (usuario.getId() != null) {
            sqlComplementar = " and o.id <> :id ";
        }

        Query query = em.createQuery("SELECT o from Usuario o where o.login = :login " + sqlComplementar + " and o.atributoPadrao.dominioEvento <> '3'");

        query.setParameter("login", usuario.getLogin());

        if (usuario.getId() != null) {
            query.setParameter("id", usuario.getId());
        }

        List<Usuario> lista = query.getResultList();

        return !lista.isEmpty();

    }

    @Override
    public Usuario create(Usuario obj) throws Exception {

        if (existeNome(obj)) {
            addErro("Nome do usuário cadastrado anteriomente!");
        }

        if (obj.getLogin() != null && existeLogin(obj)) {
            addErro("Login cadastrado anteriomente!");
        }

        if (obj.getSenha() != null) {
            obj.setSenha(Utils.Encriptar(obj.getSenha()));
        }

        if (obj.getSenhaInformada()!= null) {
            obj.setSenha(Utils.Encriptar(obj.getSenhaInformada()));
        }
        
        if(obj.getCodigoAcesso() != null && obj.getCodigoAcesso().length() < 32){
            obj.setCodigoAcesso(Utils.Encriptar(obj.getCodigoAcesso()));
        }
        
        Usuario usuario = super.create(obj);

        usuarioPermissaoService.create(obj);

        return usuario;
    }

    @Override
    public Usuario update(Usuario obj) throws Exception {

        if (!obj.getAtributoPadrao().getDominioEvento().equals(DominioEvento.E) && existeNome(obj)) {
            addErro("Nome do usuário cadastrado anteriomente!");
        }

        if (obj.getLogin() != null && existeLogin(obj)) {
            addErro("Login cadastrado anteriomente!");
        }
        
        if (obj.getSenha() != null && obj.getSenhaInformada() != null 
                && !obj.getSenha().equals(obj.getSenhaInformada())) {
            
            obj.setSenha(Utils.Encriptar(obj.getSenhaInformada()));
        }
        
        if(obj.getCodigoAcesso() != null && obj.getCodigoAcesso().length() < 32){
            obj.setCodigoAcesso(Utils.Encriptar(obj.getCodigoAcesso()));
        }
        
        Usuario usuario = super.update(obj);

        usuarioPermissaoService.create(obj);

        return usuario;
    }

    public void informarTipoMenu(Long idUsuario, String dominioTipoMenu) {
        String sql = " update seguranca.usuario set"
                + " tipo_menu = '" + dominioTipoMenu + "' "
                + " where id = " + idUsuario;

        em.createNativeQuery(sql).executeUpdate();
    }

    public void informarTipoLayout(Long idUsuario, String dominioTipoLayou) {
        String sql = " update seguranca.usuario set"
                + " tipo_layout = '" + dominioTipoLayou + "' "
                + " where id = " + idUsuario;

        em.createNativeQuery(sql).executeUpdate();
    }

    public List<Map> getPerfilUsuario(Long idUsuario) {
        return em.createQuery("select new Map(o.tipoLayout as tipoLayout ,o.tipoMenu as tipoMenu) from Usuario o where o.id = :id")
                .setParameter("id", idUsuario).getResultList();
    }

    @Override
    public List<Usuario> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        List<Usuario> result = super.findRange(complementoConsulta, apartirDe, quantidade, ordernacao);
        result.forEach((u) -> {
            u.setSenhaInformada(u.getSenha());
        });
        return result;
    }

    public String alterPasswordUser(Long idUsuario, String senha) throws Exception {
        String senhaEncriptada = Utils.Encriptar(senha);
        em.createQuery("UPDATE Usuario u set u.senha =" + (senha != null && !senha.isEmpty() ? "'" + senhaEncriptada + "'" : null) + " where u.id=" + idUsuario).executeUpdate();
        return senhaEncriptada;
    }

    public int existeSincronizacao(Date dataUltima, Long idEmpresa) {
        return sincronizar(dataUltima, idEmpresa).size();
    }

    public List<Usuario> sincronizar(Date dataUltima, Long idEmpresa) {

        List<UsuarioPermissao> lista = em.createQuery("select o from UsuarioPermissao o where "
                + "o.usuario.atributoPadrao.dataRegistro > :dataUltimaSincronizacao and "
                + "o.empresa.id = :idEmpresa and o.permissao.id = '3' and o.usuario.codigoAcesso is not null ")
                .setParameter("dataUltimaSincronizacao", dataUltima)
                .setParameter("idEmpresa", idEmpresa).getResultList();

        List<Usuario> result = new ArrayList<>();

        for (UsuarioPermissao item : lista) {
            result.add(item.getUsuario());
        }

        return result;

    }
    
    @Override
    public String getWhere(FiltroParametro filtroParametro) {
        
        String consultaSQL = "";

        if (filtroParametro != null) {
            
            if (filtroParametro.get("campoFiltro") != null){
                if(Utils.somenteNumeros(filtroParametro.get("campoFiltro").toString())){
                    consultaSQL = " o.id = '" + Long.parseLong(filtroParametro.get("campoFiltro").toString()) + "' ";
                } else {
                    consultaSQL = " (o.nome  LIKE '%" + filtroParametro.get("campoFiltro").toString() + "%' or "
                            + " o.login LIKE '%" + filtroParametro.get("campoFiltro").toString() + "%') ";
                }
            }
            
            if(filtroParametro.get("empresaId") != null){
                
                String collect = em.createQuery("SELECT DISTINCT(o.usuario.id) FROM UsuarioPermissao o "
                        + "where o.empresa.id = :empresaId", Long.class)
                        .setParameter("empresaId", Long.parseLong(filtroParametro.get("empresaId").toString()))
                        .getResultList().stream().map(String::valueOf)
                        .collect(Collectors.joining("', '"));
                
                consultaSQL += (consultaSQL.length() > 0 ? " and " : "") + " o.id in ('" + collect + "') ";
                
            }
            
            if(filtroParametro.get("ordenacao") != null){
                setOrder(filtroParametro.get("ordenacao").toString());
            }
            
        }

        return consultaSQL;
        
    }
    
    @Override
    public List<Usuario> findByEmpresaId(Long empresaId) {

        boolean existeAtributoPadrao = Utils.existeAtributoPadraoByObjectClass(Usuario.class);

        String collect = em.createQuery("SELECT DISTINCT(o.usuario.id) FROM UsuarioPermissao o "
                + "where o.empresa.id = :empresaId", Long.class)
                .setParameter("empresaId", empresaId)
                .getResultList().stream().map(String::valueOf)
                .collect(Collectors.joining("', '"));

        String sql = " where o.id in ('" + collect + "') ";
        
        if (existeAtributoPadrao) {
            sql += " and o.atributoPadrao.dominioEvento <> 3";
        }

        List<Usuario> listaResult = em.createQuery("select o from Usuario o " 
                + sql + " " + order).getResultList();
        return listaResult;

    }

}
