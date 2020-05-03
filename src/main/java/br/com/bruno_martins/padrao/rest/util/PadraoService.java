package br.com.bruno_martins.padrao.rest.util;

import br.com.bruno_martins.erplibrary.AtributoPadrao;
import br.com.bruno_martins.erplibrary.Utils;
import br.com.bruno_martins.erplibrary.WebService;
import br.com.bruno_martins.padrao.rest.services.SequenciaService;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 * @author Bruno Martins
 */
@Transactional
public class PadraoService<T> extends WebService {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private SequenciaService sequenciaService;

    @PersistenceContext(name = "default")
    protected EntityManager em;

    protected String order = "";

    private final Class<T> entityClass;

    public PadraoService() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    public List<T> findAll() {

        boolean existeAtributoPadrao = Utils.existeAtributoPadraoByObjectClass(entityClass);

        String sqlComplementar = "";
        if (existeAtributoPadrao) {
            sqlComplementar = " where o.atributoPadrao.dominioEvento <> 3";
        }

        List<T> listaResult = em.createQuery("select o from " + entityClass.getSimpleName()
                + " o " + sqlComplementar + " " + order).getResultList();
        return listaResult;

    }

    public String getWhere(String complementoConsulta) {
        return "";
    }

    public String getWhere(FiltroParametro filtroParametro) {
        return "";
    }

    public String getWhere(Map filtros) {
        return "";
    }

    public List<T> findRange(FiltroParametro filtro) {
        return findRange(null, filtro, filtro.getPosicaoInicial(), filtro.getQuantidadeRegistros());
    }

    public List<T> findRange(String complementoConsulta, int apartirDe, int quantidade, String ordernacao) {
        setOrder(" order by o." + ordernacao);
        return findRange(complementoConsulta, null, apartirDe, quantidade);
    }

    private List<T> findRange(String complementoConsulta, FiltroParametro filtroParametro, int apartirDe, int quantidade) {

        String consulta = "SELECT o from " + entityClass.getSimpleName() + " o ";

        String consultaSQL;
        if (filtroParametro == null) {
            consultaSQL = getWhere(complementoConsulta);
        } else {
            consultaSQL = getWhere(filtroParametro);
        }

        if (!StringUtils.isEmpty(consultaSQL)) {
            consulta += " where " + consultaSQL;
        }

        boolean existeAtributoPadrao = Utils.existeAtributoPadraoByObjectClass(entityClass);

        if (existeAtributoPadrao) {
            consulta += (consulta.contains("where") ? " and " : " where ") + " o.atributoPadrao.dominioEvento <>  3";
        }

        List<T> listaResult = em.createQuery(consulta + " " + order)
                .setMaxResults(quantidade).
                setFirstResult(apartirDe).
                getResultList();

        return listaResult;
    }

    public List<T> search(Map filtros, int apartirDe, int quantidade) {

        String consulta = "SELECT o from " + entityClass.getSimpleName() + " o ";
        String consultaSQL = "";
        consultaSQL = getWhere(filtros);

        if (filtros != null && !filtros.isEmpty()) {
            consulta += " where " + consultaSQL + " " + order;
        }

        List<T> listaResult = em.createQuery(consulta)
                .setMaxResults(quantidade).
                setFirstResult(apartirDe).
                getResultList();

        return listaResult;
    }

    public String count(String complementoConsulta) {
        return count(complementoConsulta, null);
    }

    public String count(FiltroParametro filtroParametro) {
        return count(null, filtroParametro);
    }

    protected String getQueryTransmissao(FiltroParametro filtroParametro, boolean isCount) {

        StringBuilder hql = new StringBuilder();
        if (isCount) {
            hql.append("SELECT COUNT(o) ");
        } else {
            hql.append("SELECT o ");
        }

        hql.append(" FROM " + entityClass.getSimpleName() + " o ");

        String where = getWhereTransmissao(filtroParametro);

        if (!StringUtils.isEmpty(where)) {
            hql.append(" WHERE ").append(where);
        } else {
            boolean existeAtributoPadrao = Utils.existeAtributoPadraoByObjectClass(entityClass);
            if (existeAtributoPadrao) {
                hql.append(" WHERE o.atributoPadrao.dataRegistro BETWEEN '")
                        .append(filtroParametro.get("dataInicio"))
                        .append("' AND '")
                        .append(filtroParametro.get("dataFim"))
                        .append("' ");

                if (hasFilial()) {
                    hql.append(" AND o.filial.id = ").append(filtroParametro.get("codigoFilial"));
                } else if (hasFilialAsId()) {
                    hql.append(" AND o.id.filial.id = ").append(filtroParametro.get("codigoFilial"));
                }

            } else {
                throw new RuntimeException("Erro ao consultar quantidade para transmissão: A condição da consulta não foi implementada e o objeto não possui um AtributoPadrao");
            }
        }

        return hql.toString();
    }

    public String countTransmissao(FiltroParametro filtroParametro) {

        String hql = getQueryTransmissao(filtroParametro, true);

        TypedQuery<Number> query = em.createQuery(hql, Number.class);
        List<Number> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return "0";
        }

        return String.valueOf(resultList.get(0).longValue());

    }

    public List<T> findRangeTransmissao(FiltroParametro filtroParametro) {
        String hql = getQueryTransmissao(filtroParametro, false);

        Query query = em.createQuery(hql);
        query.setMaxResults(filtroParametro.getQuantidadeRegistros());
        query.setFirstResult(filtroParametro.getPosicaoInicial());

        return query.getResultList();
    }

    protected String getWhereTransmissao(FiltroParametro filtroParametro) {
        return "";
    }

    private String count(String complementoConsulta, FiltroParametro filtroParametro) {
        String consultaSQL;

        if (filtroParametro != null) {
            consultaSQL = getWhere(filtroParametro);
        } else {
            consultaSQL = getWhere(complementoConsulta);
        }

        String consulta = "SELECT count(o) from " + entityClass.getSimpleName() + " o ";

        if (!StringUtils.isEmpty(consultaSQL)) {
            consulta += " where " + consultaSQL;
        }

        boolean existeAtributoPadrao = Utils.existeAtributoPadraoByObjectClass(entityClass);

        if (existeAtributoPadrao) {
            consulta += (consulta.contains("where") ? " and " : " where ") + " o.atributoPadrao.dominioEvento <>  3";
        }

        Long l = (Long) em.createQuery(consulta).getSingleResult();
        if (l == null) {
            l = 0L;
        }

        return l.toString();
    }

    public T findById(Object id) {
        return em.find(entityClass, id);
    }

    public List<T> findByEmpresaId(Long empresaId) {

        boolean existeAtributoPadrao = Utils.existeAtributoPadraoByObjectClass(entityClass);

        String sql = " where o.empresa.id = :empresaId ";
        if (existeAtributoPadrao) {
            sql += " and o.atributoPadrao.dominioEvento <> 3";
        }

        List<T> listaResult = em.createQuery("select o from " + entityClass.getSimpleName()
                + " o " + sql + " " + order).setParameter("empresaId", empresaId).getResultList();
        return listaResult;

    }

    public void setID(Object obj) {
        try {
            Object id = Utils.getIDByEntity(obj);
            Type t = Utils.getTypeIDByEntity(obj);
            AtributoPadrao atributoPadrao = Utils.getAtributoPadraoByObjectClass(obj);
            boolean existeSequence = Utils.possuiSequence(obj);
            if (!existeSequence && id == null && t == Long.class && atributoPadrao != null) {
                Long idN = sequenciaService.getProximaSequencia(obj.getClass());
                Utils.setIDIntoEntity(obj, idN);
            }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(PadraoService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public T create(T obj) throws Exception {
        try {
            setID(obj);

            AtributoPadrao atributoPadrao = Utils.getAtributoPadraoByObject(obj);
            if (atributoPadrao != null) {
                atributoPadrao.setDataAlteracao(new Date());
            };

            em.persist(obj);

            return obj;
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public T update(T obj) throws Exception {

        AtributoPadrao atributoPadrao = Utils.getAtributoPadraoByObject(obj);
        if (atributoPadrao != null) {
            atributoPadrao.setDataAlteracao(new Date());
        }

        em.merge(obj);
        return obj;
    }

    public void delete(T obj) throws Exception {
        obj = em.merge(obj);
        em.remove(obj);
    }

    public void mergeList(List<T> lista) throws Exception {
        for (T obj : lista) {
            em.merge(obj);
        }
    }

    @Transactional
    public void executeUpdate(List<String> sqls) throws Exception {
        try {

            for (String sql : sqls) {

                em.createNativeQuery(sql).executeUpdate();
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new Exception(e.fillInStackTrace());
        }

    }

    public String getPathApp() {
        /*URL url = getClass().getResource("/");
        String path = url.toString();
        path = path.replace("file:", "");
        path = path.replace("jar:", "");*/
        URL url = getClass().getClassLoader().getResource("/");
        if (url == null) {
            url = getClass().getResource("/");
        }
        String path = url.getPath();

        return path;
    }

    public void addErro(String mensagem) throws Exception {
        throw new Exception(mensagem);
    }

    public boolean existeCampoRepetido(Object entidade, String... campos) {

        try {
            Integer id = (Integer) Utils.getIDByEntity(entidade);

            String sqlComplementar = "";
            if (id != null) {
                sqlComplementar = " and o.id <> " + id;
            }

            String where = null;
            for (String campo : campos) {

                Object valorCampo = Utils.getValueFromObject(entidade, campo);

                if (campo.equals(campos[0])) {//primeiro elemento
                    where = " where o." + campo + " = '" + valorCampo + "'";
                } else {
                    where += " and o." + campo + " = '" + valorCampo + "'";
                }
            }

            return !em.createQuery("SELECT o from " + entidade.getClass().getSimpleName() + " o " + where + sqlComplementar).getResultList().isEmpty();

        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * @return the order
     */
    public String getOrder() {
        return order;
    }

    /**
     * @param order the order to set
     */
    public void setOrder(String order) {
        this.order = order;
    }

    //Método para injetar o entityManager programaticamente
    //E evitar de abrir conexões desnecessário a cada requisição
    private EntityManager getQualifiedEntityManager(String name) {
        AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
        return BeanFactoryAnnotationUtils.qualifiedBeanOfType(
                beanFactory,
                EntityManager.class,
                name
        );
    }

    public EntityManager getEmIntegrado() {
        return getQualifiedEntityManager("integradoEntityManager");
    }

    public EntityManager getEmInterserv() {
        return getQualifiedEntityManager("interservEntityManager");
    }

    protected <Z> List<Z> dataReportPagination(int reportCount, TypedQuery<Z> query) {
        List<Z> list = new ArrayList<>();
        int pageSize = 1000;

        int pageCount = reportCount / pageSize;

        if (reportCount % pageSize != 0) {
            pageCount++;
        }

        for (int page = 0; page < pageCount; page++) {
            int index = 0;
            if (page > 1) {
                index = (page * pageSize) - pageSize;
            }
            query.setFirstResult(index);
            query.setMaxResults(pageSize);
            list.addAll(query.getResultList());
        }

        return list;
    }

    public BigInteger getUltimoId(String sequence) throws Exception {
        return (BigInteger) em.createNativeQuery("SELECT nextval('" + sequence + "') as id").getSingleResult();
    }

    public void alterValueSeq(String sequence, Long id) throws Exception {
        em.createNativeQuery("ALTER SEQUENCE " + sequence + " INCREMENT 1 MINVALUE 1 MAXVALUE 9223372036854775807 START 1 RESTART " + id + " CACHE 1 NO CYCLE;").executeUpdate();
    }

    public boolean hasFilial() {

        try {
            return entityClass.getDeclaredField("filial") != null;
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    public boolean hasFilialAsId() {

        try {
            Field id = entityClass.getDeclaredField("id");
            return id.getType().getDeclaredField("filial") != null;
        } catch (NoSuchFieldException e1) {
            return false;
        }
    }

    protected boolean isValid(Object o) {
        return o != null && o.toString().trim().length() > 0;
    }
}
