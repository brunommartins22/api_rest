/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.util;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bruno Martins
 */
public class FiltroParametro {

    private Integer posicaoInicial;
    private Integer quantidadeRegistros;

    private List<FiltroParametroItem> itens = new ArrayList<>();

    public FiltroParametroItem add(String nmField) {

        FiltroParametroItem item = getFilter(nmField);

        if (item == null) {
            item = new FiltroParametroItem();
            item.setField(nmField);
            itens.add(item);
        }

        return item;
    }
    
    public FiltroParametroItem add(String nmField, String value) {

        FiltroParametroItem item = getFilter(nmField);

        if (item == null) {
            item = new FiltroParametroItem();
            item.setField(nmField);
            item.setValue(value);
            itens.add(item);
        }

        return item;
    }

    public Object get(String nmField) {
        for (FiltroParametroItem iten : itens) {
            if (iten.getField().equals(nmField)) {
                return iten.getValue();
            }
        }

        return null;
    }

    public FiltroParametroItem getFilter(String nmField) {
        for (FiltroParametroItem iten : itens) {
            if (iten.getField().equals(nmField)) {
                return iten;
            }
        }

        return null;
    }

    public void empty(String nmField) {
        FiltroParametroItem remover = null;
        for (FiltroParametroItem iten : itens) {
            if (iten.getField().equals(nmField)) {
                remover = iten;
                break;
            }
        }

        if (remover != null) {
            itens.remove(remover);
        }

    }
    
    public boolean containsField(String nmField){
        return get(nmField) != null;
    }
        

    public void clear() {
        itens.clear();
    }

    //**************************************************************************
    /**
     * @return the itens
     */
    public List<FiltroParametroItem> getItens() {
        return itens;
    }

    /**
     * @param itens the itens to set
     */
    public void setItens(List<FiltroParametroItem> itens) {
        this.itens = itens;
    }

    /**
     * @return the posicaoInicial
     */
    public Integer getPosicaoInicial() {
        return posicaoInicial;
    }

    /**
     * @param posicaoInicial the posicaoInicial to set
     */
    public void setPosicaoInicial(Integer posicaoInicial) {
        this.posicaoInicial = posicaoInicial;
    }

    /**
     * @return the quantidadeRegistros
     */
    public Integer getQuantidadeRegistros() {
        return quantidadeRegistros;
    }

    /**
     * @param quantidadeRegistros the quantidadeRegistros to set
     */
    public void setQuantidadeRegistros(Integer quantidadeRegistros) {
        this.quantidadeRegistros = quantidadeRegistros;
    }

}
