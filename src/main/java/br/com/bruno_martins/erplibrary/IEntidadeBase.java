/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.erplibrary;

import java.io.Serializable;

/**
 *
 * @author Bruno Martins
 * @param <PK>
 */
public interface IEntidadeBase<PK extends Serializable> extends Serializable,
		Cloneable {

	/**
	 * Define a chave primaria da classe.
	 * 
	 * @return pk o novo valor para pk
	 */
	PK getChavePrimaria();

}