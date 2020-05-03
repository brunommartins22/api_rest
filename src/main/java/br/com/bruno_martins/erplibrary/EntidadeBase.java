/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.erplibrary;

import java.io.Serializable;
import java.util.Objects;
import java.util.Stack;
import javax.persistence.Transient;

/**
 *
 * @author Bruno Martins
 * @param <PK>
 */
public abstract class EntidadeBase<PK extends Serializable> implements IEntidadeBase<PK> {

    private static final long serialVersionUID = -8169225005255215418L;

    @Transient
    private final Stack<EntidadeBase<PK>> memento = new Stack<>();

    public EntidadeBase<PK> getMemento() {

        return this.memento.peek();

    }

    public void criarMemento() {

        this.memento.push(this);

    }

    public boolean isExisteMemento() {

        return !this.memento.empty();

    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        return super.clone();

    }

    public <E extends IEntidadeBase<?>> E cloneMe() {

        E retorno = null;

        try {

            retorno = (E) this.clone();

        } catch (final CloneNotSupportedException e) {

            e.printStackTrace();

        }

        return retorno;
    }

    public Object getClone() {

        this.criarMemento();

        return this.cloneMe();

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof EntidadeBase)) {
            return false;
        }

        final EntidadeBase<PK> other = (EntidadeBase<PK>) obj;

        if (this.getChavePrimaria() == null) {

            if (other.getChavePrimaria() != null) {
                return false;
            }
        } else if (!this.getChavePrimaria().equals(other.getChavePrimaria())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.getChavePrimaria());
        return hash;
    }

}
