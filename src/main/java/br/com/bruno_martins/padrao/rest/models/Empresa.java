package br.com.bruno_martins.padrao.rest.models;

import br.com.bruno_martins.erplibrary.AtributoPadrao;
import br.com.bruno_martins.erplibrary.EnderecoPadrao;
import br.com.bruno_martins.padrao.rest.domains.DominioTipoPessoa;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(schema = "seguranca", name = "empresa")
public class Empresa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_empresa")
    @SequenceGenerator(name = "gen_empresa", sequenceName = "seguranca.seq_empresa", initialValue = 0, allocationSize = 1)
    private Long id;

    @Column(length = 60, nullable = false)
    private String razaoSocial;

    @Column(length = 60)
    private String fantasia;

    @Column(length = 20, nullable = false)
    private String cnpjCpf;

    @Column(length = 20, nullable = false)
    private String ieRg;

    @Column(length = 30, nullable = false)
    @Enumerated(EnumType.STRING)
    private DominioTipoPessoa tipoPessoa;

    @Embedded
    private EnderecoPadrao endereco = new EnderecoPadrao();

    @Embedded
    private AtributoPadrao atributoPadrao = new AtributoPadrao();

    //**************************** get && sets *********************************
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getFantasia() {
        return fantasia;
    }

    public void setFantasia(String fantasia) {
        this.fantasia = fantasia;
    }

    public String getCnpjCpf() {
        return cnpjCpf;
    }

    public void setCnpjCpf(String cnpjCpf) {
        this.cnpjCpf = cnpjCpf;
    }

    public String getIeRg() {
        return ieRg;
    }

    public void setIeRg(String ieRg) {
        this.ieRg = ieRg;
    }

    public DominioTipoPessoa getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(DominioTipoPessoa tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public EnderecoPadrao getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoPadrao endereco) {
        this.endereco = endereco;
    }

    public AtributoPadrao getAtributoPadrao() {
        return atributoPadrao;
    }

    public void setAtributoPadrao(AtributoPadrao atributoPadrao) {
        this.atributoPadrao = atributoPadrao;
    }

}
