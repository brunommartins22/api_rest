/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.services;

import br.com.bruno_martins.padrao.rest.models.Sequencia;
import br.com.bruno_martins.padrao.rest.util.PadraoService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author Bruno Martins
 */
@Service
public class SequenciaService extends PadraoService<Sequencia> {

    public SequenciaService() {
      //  super(Sequencia.class);
    }

    public Long getSequenciaAtual(Class clazz, boolean verificarUltimoID) {
        String model = clazz.getSimpleName();

        List<Sequencia> lista = em.createQuery("SELECT o from Sequencia o "
                + "where  "
                + "o.entidade = '" + model + "' ").getResultList();

        Sequencia sequencia;

        if (lista.isEmpty()) {
            Long max = 0L;

            /**
             * possui essa exceção para que no caso da venda que o campo ID não
             * é inteiro, retorne 0 em caso de erro, neste caso o sistema sempre
             * inica a venda com 0
             */
            if (verificarUltimoID) {
                max = (Long) em.createQuery("SELECT max(o.id) from " + model + " o ").getSingleResult();

                if (max == null) {
                    max = 0L;
                }
            }

            return max;
        } else {
            sequencia = lista.get(0);
            return sequencia.getSequencia();
        }

    }

    public Long getProximaSequencia(Class clazz, boolean verificarUltimoID, int increment) {

        String model = clazz.getSimpleName();

        List<Sequencia> lista = em.createQuery("SELECT o from Sequencia o "
                + "where  "
                + "o.entidade = '" + model + "' ").getResultList();

        Sequencia sequencia;

        if (lista.isEmpty()) {
            Long max = 0L;

            /**
             * possui essa exceção para que no caso da venda que o campo ID não
             * é inteiro, retorne 0 em caso de erro, neste caso o sistema sempre
             * inica a venda com 0
             */
            if (verificarUltimoID) {
                max = (Long) em.createQuery("SELECT max(o.id) from " + model + " o ").getSingleResult();

                if (max == null) {
                    max = 0L;
                }
            }
            sequencia = new Sequencia();
            sequencia.setEntidade(model);
            sequencia.setSequencia(max);
            try {
                super.create(sequencia);
            } catch (Exception ex) {
                Logger.getLogger(SequenciaService.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            sequencia = lista.get(0);
        }

        Long seq = sequencia.getSequencia();
        seq += increment;

        sequencia.setSequencia(seq);
        try {
            super.update(sequencia);
        } catch (Exception ex) {
            Logger.getLogger(SequenciaService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return seq;
    }

    public Long getProximaSequencia(Class clazz, boolean verificarUltimoID) {
        return getProximaSequencia(clazz, verificarUltimoID, 1);
    }

    public Long getProximaSequencia(Class clazz) {
        return getProximaSequencia(clazz, true, 1);
    }

    public Sequencia findBySimpleClass(String simpleName) {
        List<Sequencia> lista = em.createQuery("select o from Sequencia o where "
                + "o.entidade = '" + simpleName + "' ").getResultList();

        if (lista.isEmpty()) {
            return null;
        } else {
            return lista.get(0);
        }

    }

}
