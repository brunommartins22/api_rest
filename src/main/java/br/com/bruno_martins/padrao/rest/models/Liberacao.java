package br.com.bruno_martins.padrao.rest.models;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Bruno Martins
 */
@Entity
@Table(schema = "seguranca", name = "liberacao")
public class Liberacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gen_liberacao")
    @SequenceGenerator(name = "gen_liberacao", sequenceName = "seguranca.seq_liberacao", initialValue = 0, allocationSize = 1)
    private Long id;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataArquivo;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDownload;
    @Lob
    private String arquivo;

    //**************************** get && sets *********************************
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataArquivo() {
        return dataArquivo;
    }

    public void setDataArquivo(Date dataArquivo) {
        this.dataArquivo = dataArquivo;
    }

    public Date getDataDownload() {
        return dataDownload;
    }

    public void setDataDownload(Date dataDownload) {
        this.dataDownload = dataDownload;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

}
