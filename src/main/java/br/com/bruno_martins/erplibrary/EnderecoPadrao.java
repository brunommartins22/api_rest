/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.erplibrary;

import br.com.bruno_martins.padrao.rest.models.Cidade;
import br.com.bruno_martins.padrao.rest.models.Estado;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 *
 * @author Bruno Martins
 */
@Embeddable
public class EnderecoPadrao implements Serializable {

    @ManyToOne
    private Estado estado;
        
    @ManyToOne
    private Cidade cidade;
    
    @Column(length = 50 )
    private String bairro;
    
    @Column(length = 11 )
    private String cep;
    
    @Column(length = 100)
    private String endereco;
    
    @Column(length = 15)
    private String numero;
    
    @Column(length = 100)
    private String complemento;
    
    @Column(length = 20)
    private String fone1;
    
    @Column(length = 20)
    private String fone2;
    
    @Column(length = 100)
    private String email;

    
    //**************************************************************************

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public Cidade getCidade() {
        return cidade;
    }

    public void setCidade(Cidade cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getFone1() {
        return fone1;
    }

    public void setFone1(String fone1) {
        this.fone1 = fone1;
    }

    public String getFone2() {
        return fone2;
    }

    public void setFone2(String fone2) {
        this.fone2 = fone2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
   
}
