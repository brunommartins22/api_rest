package br.com.bruno_martins.padrao.rest.util;

import br.com.bruno_martins.erplibrary.AtributoPadrao;
import br.com.bruno_martins.erplibrary.Utils;
import br.com.bruno_martins.padrao.rest.domains.DominioEvento;
import java.lang.reflect.Field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.google.gson.ExclusionStrategy;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;

/**
 *
 * @author Bruno Martins
 */
public class PadraoController<T> {

    private PadraoService<T> padraoService;

    private final Class<T> entityClass;

    public PadraoController() {
        ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
        this.entityClass = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
    }

    @GetMapping
    public String findAll() {
        return serializar(getPadraoService().findAll());
    }

    @GetMapping(path = "/{apartirDe}/{quantidade}/{ordenacao}/{filtro:.+}")
    public String findRange(@PathVariable("apartirDe") int apartirDe,
            @PathVariable("quantidade") int quantidade,
            @PathVariable("ordenacao") String ordenacao,
            @PathVariable("filtro") String filtro) {

        ordenacao = new String(Base64.getDecoder().decode(ordenacao));
        filtro = new String(Base64.getDecoder().decode(filtro));
        String json = serializar(getPadraoService().findRange(filtro.equals("*") ? null : filtro, apartirDe, quantidade, ordenacao));

        return json;
    }

    /**
     * metodo utilizado para navegação de filtro nao padrao
     *
     * @param json
     * @return
     */
    @PostMapping(path = "/findRange")
    public String findRangePost(@RequestBody String json) {
        FiltroParametro filtroParametro = (FiltroParametro) deserializar(json, FiltroParametro.class);

        String result = serializar(getPadraoService().findRange(filtroParametro));

        return result;
    }

    @PostMapping(path = "/{apartirDe}/{quantidade}")
    public String findRange(@PathVariable("apartirDe") int apartirDe, @PathVariable("quantidade") int quantidade,
            @RequestBody Map filtros) {

        return serializar(getPadraoService().search(filtros, apartirDe, quantidade));
    }

    /**
     * metodo utilizado para navegação de filtro nao padrao
     *
     * @param json
     * @return
     */
    @PostMapping(path = "/countPost")
    public String countPost(@RequestBody String json) {
        FiltroParametro filtroParametro = (FiltroParametro) deserializar(json, FiltroParametro.class);
        return getPadraoService().count(filtroParametro);
    }

    @PostMapping(path = "/countTransmissao")
    public String countTransmissao(@RequestBody String json) {
        FiltroParametro filtroParametro = (FiltroParametro) deserializar(json, FiltroParametro.class);
        return getPadraoService().countTransmissao(filtroParametro);
    }

    @PostMapping(path = "/findRangeTransmissao")
    public String findRangeTransmissao(@RequestBody String json) {
        FiltroParametro filtroParametro = (FiltroParametro) deserializar(json, FiltroParametro.class);
        List<T> lista = getPadraoService().findRangeTransmissao(filtroParametro);
        return serializar(lista);
    }

    @GetMapping(path = "count/{filtro:.+}")
    public String count(@PathVariable("filtro") String filtro) {

        if (filtro != null && filtro.equals("*")) {
            filtro = null;
        }

        return getPadraoService().count(filtro);
    }

    @GetMapping(path = "/empresaId/{id:.+}")
    public String findByEmpresaId(@PathVariable(name = "id") String id) {
        try {
            return serializar(getPadraoService().findByEmpresaId(Long.parseLong(id)));
        } catch (Exception ex) {
            Logger.getLogger(PadraoController.class.getName()).log(Level.SEVERE, null, ex);
            return returnException(ex);
        }
    }

    @GetMapping(path = "/{id:.+}")
    public String findById(@PathVariable(name = "id") String id) {
        try {
            Class clazz = Utils.getIDTypeByClass(entityClass);

            if (clazz == String.class) {
                return serializar(getPadraoService().findById(id));
            } else {
                return serializar(getPadraoService().findById(Long.parseLong(id)));
            }

        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(PadraoController.class.getName()).log(Level.SEVERE, null, ex);
            return returnException(ex);
        }
    }

    @PostMapping()
    public String create(@RequestBody String json) {

        try {
            T entity = (T) deserializar(json, entityClass);

            return serializar(getPadraoService().create(entity));
        } catch (Exception ex) {
            ex.printStackTrace();
            //FIXME - tem que gerar log de erro, mas não erro de validação
            //Logger.getLogger(PadraoController.class.getName()).log(Level.SEVERE, null, ex);
            return returnException(ex);
        }

    }

    @PostMapping(path = "/mergeList")
    public String mergeList(@RequestBody List<T> lista) {

        //List<T> lista = (List<T>) deserializar(json, new TypeToken<List<T>>(){}.getType());
        try {
            getPadraoService().mergeList(lista);
            return "OK";
        } catch (Exception ex) {
            ex.printStackTrace();
            //FIXME - tem que gerar log de erro, mas não erro de validação
            //Logger.getLogger(PadraoController.class.getName()).log(Level.SEVERE, null, ex);
            return returnException(ex);
        }

    }

    public Object deserializar(String json, Type type) {
        return Utils.deserializar(json, type);
    }

    @PutMapping()
    public String update(@RequestBody String json) {
        try {
            T entity = (T) deserializar(json, entityClass);

            return serializar(getPadraoService().update(entity));
        } catch (Exception ex) {
            return returnException(ex);
        }

    }

    @DeleteMapping()
    public String delete(@RequestBody String json) {
        try {
            T entity = (T) deserializar(json, entityClass);

            getPadraoService().delete(entity);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(PadraoController.class.getName()).log(Level.SEVERE, null, ex);
            return returnException(ex);
        }

    }
    // ****************************************************************************************

    protected String serializar(Object obj) {
        return Utils.serializar(obj);
    }

    protected String serializar(Object obj, ExclusionStrategy exclusionStrategy) {
        return Utils.serializar(obj, exclusionStrategy);
    }

    private PadraoService<T> getPadraoService() {
        if (padraoService == null) {
            padraoService = carregarPadraoService();
        }
        return padraoService;
    }

    @SuppressWarnings("unchecked")
    private PadraoService<T> carregarPadraoService() {

        for (Field f : this.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Autowired.class) && f.isAnnotationPresent(IsServiceDefault.class)) {
                try {
                    f.setAccessible(true);
                    Object ps = f.get(this);
                    if (ps instanceof PadraoService) {
                        return (PadraoService<T>) ps;
                    }
                } catch (Exception e) {

                }

            }
        }

        return null;
    }

    @ExceptionHandler()
    public ResponseEntity<ExceptionMessage> exceptionHandler(String ex) {
        ExceptionMessage error = new ExceptionMessage();
        error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
        error.setMessage(ex);
        return new ResponseEntity<ExceptionMessage>(error, HttpStatus.BAD_REQUEST);
    }

    public String returnException(Exception ex) {
        ExceptionMessage exceptionMessage = new ExceptionMessage();
        exceptionMessage.setErrorCode(1);
        exceptionMessage.setMessage(ex.getMessage());

        return serializar(exceptionMessage);
    }

    public void retirarItensDeletados(List itens) {

        List listaExcluir = new ArrayList();
        for (Object item : itens) {
            AtributoPadrao atributoPadrao = Utils.getAtributoPadraoByObject(item);

            if (atributoPadrao != null && atributoPadrao.getDominioEvento().equals(DominioEvento.E)) {
                listaExcluir.add(item);
            }
        }

        itens.removeAll(listaExcluir);
    }
}
