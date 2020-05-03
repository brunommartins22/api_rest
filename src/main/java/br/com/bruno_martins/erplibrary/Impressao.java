/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.erplibrary;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author Bruno Martins
 */
public class Impressao {

    private List listagem;
    private Map parametros = new HashMap();
    private String arquivoJasper = null;
    private JasperReport relatorioJasper;
    private byte[] bytes;
    private JRDataSource ds;

    private InputStream inputStream;

    //private String  urlImage="/home/adam/Imagens/logo.png";
    private final String urlImage;
    private final boolean listaDeMap;

    public Impressao(String urlImage, InputStream inputStream, boolean listaDeMap) {

        this.urlImage = urlImage;
        this.inputStream = inputStream;
        this.listaDeMap = listaDeMap;

    }

    public Impressao(String urlImage, InputStream inputStream) {

        this(urlImage, inputStream, false);

    }

    public JasperPrint geraJasperPrint(String nmUsuario, String nmEmpresa, String endereco) throws Exception {

        run(nmUsuario, nmEmpresa, endereco, inputStream);
        /*if (inputStream != null) {
            run(nmUsuario, nmEmpresa, endereco, inputStream);
        } else {
            run(nmUsuario, nmEmpresa, endereco);
        }*/

        JasperPrint print;

        java.util.Locale locale = new Locale("pt", "BR");
        parametros.put(JRParameter.REPORT_LOCALE, locale);

        if (ds != null) {
            print = JasperFillManager.fillReport(relatorioJasper, parametros, ds);
        } else {
            print = JasperFillManager.fillReport(relatorioJasper, parametros);
        }

        return print;

    }

    public byte[] gera() throws Exception {

        run(inputStream);

        return generateBytes();

    }

    public byte[] gera(String nmUsuario, String nmEmpresa, String endereco) throws Exception {

        run(nmUsuario, nmEmpresa, endereco, inputStream);

        return generateBytes();

    }

    private byte[] generateBytes() throws Exception {

        java.util.Locale locale = new Locale("pt", "BR");
        parametros.put(JRParameter.REPORT_LOCALE, locale);

        if (ds != null) {

            bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros, ds);

        } else {

            bytes = JasperRunManager.runReportToPdf(relatorioJasper, parametros);
        }

        return bytes;
    }

    public void run(InputStream inputStream) throws Exception {

        List lista = getListagem();
        //List lista = padrao.getFacade().findByQueries();

        if (lista != null) {

            if (listaDeMap) {

                ds = new JRMapCollectionDataSource(lista);

            } else {

                ds = new JRBeanCollectionDataSource(lista, false);

            }

        }

        relatorioJasper = (JasperReport) JRLoader.loadObject(inputStream);

        parametros = getParametros();

    }

    public void run(String nmUsuario, String nmEmpresa, String endereco, InputStream inputStream) throws Exception {

        run(inputStream);
        // carrega os arquivos jasper
        //String buscaArquivo = (pathReport + "/" + arquivoJasper);

        if (parametros.get("path") == null) {

            //parametros.put("path", pathReport);
            parametros.put("path", "");

        }

        if (parametros.get("usuario") == null) {

            parametros.put("usuario", nmUsuario);

        }

        /*if (parametros.get("diretorioInstalacao") == null) {
            parametros.put("diretorioInstalacao", pathReport);
        }*/
        if (parametros.get("nmEmpresa") == null) {

            parametros.put("nmEmpresa", nmEmpresa);

        }

        if (parametros.get("endere") == null) {

            parametros.put("endereco", endereco);

        }

        if (parametros.get("urlImage") == null) {

            //parametros.put("urlImage", "/home/adam/Imagens/logo.png");
            parametros.put("urlImage", urlImage);

        }

    }

    /*public void run(String nmUsuario, String nmEmpresa, String endereco) throws Exception {
        //Usuario usuario
        List lista = getListagem();
        //Empresa emp = usuario.getEmpresa();

        //List lista = padrao.getFacade().findByQueries();
        ds = new JRBeanCollectionDataSource(lista);
        // carrega os arquivos jasper

        //String buscaArquivo = (pathReport + "/" + arquivoJasper);
        String buscaArquivo = (pathReport + arquivoJasper);
        relatorioJasper = (JasperReport) JRLoader.loadObjectFromFile(buscaArquivo);

        parametros = getParametros();

        String imagem = null;

        parametros.put("imagem", imagem);

        if (parametros.get("path") == null) {
            //parametros.put("path", pathReport);
            parametros.put("path", "");
        }

        if (parametros.get("usuario") == null) {
            parametros.put("usuario", nmUsuario);
        }

        if (parametros.get("diretorioInstalacao") == null) {
            parametros.put("diretorioInstalacao", pathReport);
        }

        if (parametros.get("nmEmpresa") == null) {
            parametros.put("nmEmpresa", nmEmpresa);
        }

        if (parametros.get("endere") == null) {
            parametros.put("endereco", endereco);
        }

        if (parametros.get("urlImage") == null) {
            parametros.put("urlImage", urlImage);
        }

    }*/
    public List getListagem() {

        return listagem;

    }

    public void setListagem(List listagem) {

        this.listagem = listagem;

    }

    public Map getParametros() {

        return parametros;

    }

    public void setParametros(Map parametros) {

        this.parametros = parametros;

    }

    public String getArquivoJasper() {

        return arquivoJasper;

    }

    public void limparImpressao() {

        listagem = null;
        parametros.clear();
        arquivoJasper = null;

    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {

        return inputStream;

    }

}
