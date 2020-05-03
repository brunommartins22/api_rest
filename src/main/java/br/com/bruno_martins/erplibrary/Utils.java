/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.erplibrary;

import br.com.bruno_martins.erplibrary.AtributoPadrao;
import com.google.gson.ExclusionStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.swing.text.MaskFormatter;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 *
 * @author Bruno Martins
 */
public class Utils {

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};
    private static final int[] pesoCNPJ = {6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

    private static final char[] FIRST_CHAR
            = (" !'#$%&'()*+\\-./0123456789:;<->?@ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                    + "[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~ E ,f'.++^%S<O Z  ''''.-"
                    + "-~Ts>o ZY !C#$Y|$'(a<--(_o+23'u .,1o>113?AAAAAAACEEEEIIIIDNOO"
                    + "OOOXOUUUUyTsaaaaaaaceeeeiiiidnooooo/ouuuuyty").toCharArray();

    private static final char[] SECOND_CHAR
            = ("  '         ,                                               "
                    + "\\                                   $  r'. + o  E      ''  "
                    + "  M  e     #  =  'C.<  R .-..     ..>424     E E            "
                    + "   E E     hs    e e         h     e e     h ").toCharArray();

    //**************************************************************************
    public static String retirarCaracteresXML(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        char[] chars = str.toCharArray();
        StringBuilder ret = new StringBuilder(chars.length * 2);
        for (int i = 0; i < chars.length; ++i) {
            char aChar = chars[i];
            if (aChar == ' ' || aChar == '\t') {
                ret.append(' '); // convertido para espaço
            } else if (aChar > ' ' && aChar < 256) {
                if (FIRST_CHAR[aChar - ' '] != ' ') {
                    ret.append(FIRST_CHAR[aChar - ' ']); // 1 caracter
                }
                if (SECOND_CHAR[aChar - ' '] != ' ') {
                    ret.append(SECOND_CHAR[aChar - ' ']); // 2 caracteres
                }
            }
        }

        String result = ret.toString();

        result = result.replace(".", "");
        result = result.replace("\\", "");
        result = result.trim();

        result = result.replace("º", "");
        result = result.replace("ª", "");
        result = result.replace("º", "");
        result = result.replace("§", "");
        result = result.replace("'", "");
        result = result.replace("&", "");

        return result;
    }

    public static String retirarCaracteresXML2(String s) {

        if (s != null && !s.equals("")) {
            s = s.trim();

            s = s.replace("º", "");
            s = s.replace("ª", "");
            s = s.replace("º", "");
            s = s.replace("§", "");
            s = s.replace("'", "");
            s = s.replace("&", "");

            s = s.trim();

            String acentuado = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
            String semAcento = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";

            char[] tabela = new char[256];

            for (int i = 0; i
                    < tabela.length;
                    ++i) {
                tabela[i] = (char) i;

            }
            for (int i = 0; i
                    < acentuado.length();
                    ++i) {
                tabela[acentuado.charAt(i)] = semAcento.charAt(i);

            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i
                    < s.length();
                    ++i) {
                char ch = s.charAt(i);

                if (ch < 256) {
                    sb.append(tabela[ch]);

                } else {
                    sb.append(ch);
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static int getMod10(String num) {

        //vari�veis de instancia
        int soma = 0;
        int resto = 0;
        int dv = 0;
        String[] numeros = new String[num.length() + 1];
        int multiplicador = 2;
        String aux;
        String aux2;
        String aux3;

        for (int i = num.length(); i > 0; i--) {
            //Multiplica da direita pra esquerda, alternando os algarismos 2 e 1
            if (multiplicador % 2 == 0) {
                // pega cada numero isoladamente
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * 2);
                multiplicador = 1;
            } else {
                numeros[i] = String.valueOf(Integer.valueOf(num.substring(i - 1, i)) * 1);
                multiplicador = 2;
            }
        }

        // Realiza a soma dos campos de acordo com a regra
        for (int i = (numeros.length - 1); i > 0; i--) {
            aux = String.valueOf(Integer.valueOf(numeros[i]));

            if (aux.length() > 1) {
                aux2 = aux.substring(0, aux.length() - 1);
                aux3 = aux.substring(aux.length() - 1, aux.length());
                numeros[i] = String.valueOf(Integer.valueOf(aux2) + Integer.valueOf(aux3));
            } else {
                numeros[i] = aux;
            }
        }

        //Realiza a soma de todos os elementos do array e calcula o digito verificador
        //na base 10 de acordo com a regra.
        for (int i = numeros.length; i > 0; i--) {
            if (numeros[i - 1] != null) {
                soma += Integer.valueOf(numeros[i - 1]);
            }
        }
        resto = soma % 10;
        dv = 10 - resto;

        //retorna o digito verificador
        if (dv == 10) {
            dv = 0;
        }
        return dv;
    }

    public static String casasDecimais(Integer casas, BigDecimal valor) {
        return casasDecimais(casas, valor.doubleValue());
    }

    public static String casasDecimais(Integer casas, Double valor) {
        /*
         * if (casas != null && valor != null) { String quantCasas = "%." +
         * casas + "f", textoValor = "0"; try { textoValor =
         * String.format(Locale.getDefault(), quantCasas, valor);
         *
         * } catch (java.lang.IllegalArgumentException e) { if
         * (e.getMessage().equals("Digits < 0")) { textoValor = "0";
         *
         * }
         * System.out.println(e.getMessage()); }
         *
         * return textoValor.replace(",", "."); } return null;
         */

        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));

        nf.setMaximumFractionDigits(casas);
        nf.setMinimumFractionDigits(casas);

        if (valor == null) {
            valor = 0.0;
        }

        String sValor = nf.format(valor);

        sValor = sValor.replace(".", "");
        sValor = sValor.replace(",", ".");

        return sValor;

    }

    public static int getMod11(String num) {
        /**
         * Autor: Douglas Tybel <dtybel@yahoo.com.br>
         *
         * Fun??o: Calculo do Modulo 11 para geracao do digito verificador de
         * boletos bancarios conforme documentos obtidos da Febraban -
         * www.febraban.org.br
         *
         * Entrada: $num: string num?rica para a qual se deseja calcularo digito
         * verificador; $base: valor maximo de multiplicacao [2-$base] $r:
         * quando especificado um devolve somente o resto
         *
         * Sa?da: Retorna o Digito verificador.
         *
         * Observa??es: - Script desenvolvido sem nenhum reaproveitamento de
         * c?digo existente. - Script original de Pablo Costa
         * <pablo@users.sourceforge.net>
         * - Transportado de php para java - Exemplo de uso:
         * getMod11(nossoNumero, 9,1) - 9 e 1 s?o fixos de acordo com a base -
         * Assume-se que a verifica??o do formato das vari?veis de entrada ?
         * feita antes da execu??o deste script.
         */
        int base = 9;
        int r = 0;

        int soma = 0;
        int fator = 2;
        String[] numeros, parcial;
        numeros = new String[num.length() + 1];
        parcial = new String[num.length() + 1];

        /* Separacao dos numeros */
        for (int i = num.length(); i > 0; i--) {
            // pega cada numero isoladamente
            numeros[i] = num.substring(i - 1, i);
            // Efetua multiplicacao do numero pelo falor
            parcial[i] = String.valueOf(Integer.parseInt(numeros[i]) * fator);
            // Soma dos digitos
            soma += Integer.parseInt(parcial[i]);
            if (fator == base) {
                // restaura fator de multiplicacao para 2
                fator = 1;
            }
            fator++;

        }

        /* Calculo do modulo 11 */
        if (r == 0) {
            soma *= 10;
            int digito = soma % 11;
            if (digito == 10) {
                digito = 0;
            }
            return digito;
        } else {
            int resto = soma % 11;
            return resto;
        }
    }

    public static boolean validarCpfCnpj(String cpfcnpj) {

        if (cpfcnpj == null) {
            return false;
        }

        cpfcnpj = cpfcnpj.replace(".", "");
        cpfcnpj = cpfcnpj.replace("-", "");
        cpfcnpj = cpfcnpj.replace("/", "");

        if (cpfcnpj.length() == 11) {
            boolean valido = isValidCPF(cpfcnpj);
            if (!valido) {
                return false;
            }
        } else if (!isValidCNPJ(cpfcnpj)) {
            return false;
        }

        return true;
    }

    public static String formataStringCPF(String valor) {
        if (valor == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(valor);
        //218.654.654-54
        builder.insert(3, ".").insert(7, ".").insert(11, "-");

        return builder.toString();
    }

    public static String formataStringCNPJ(String valor) {
        if (valor == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(valor);
        //21.654.654/0001-54
        builder.insert(2, ".").insert(6, ".").insert(10, "/").insert(15, "-");

        return builder.toString();
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice = str.length() - 1, digito; indice >= 0; indice--) {
            digito = Integer.parseInt(str.substring(indice, indice + 1));
            soma += digito * peso[peso.length - str.length() + indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf == null) || (cpf.length() != 11) || "00000000000".equals(cpf)) {
            return false;
        }

        Integer digito1 = calcularDigito(cpf.substring(0, 9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0, 9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0, 9) + digito1.toString() + digito2.toString());
    }

    public static boolean isValidCNPJ(String cnpj) {
        if ((cnpj == null) || (cnpj.length() != 14) || "00000000000000".equals(cnpj)) {
            return false;
        }

        Integer digito1 = calcularDigito(cnpj.substring(0, 12), pesoCNPJ);
        Integer digito2 = calcularDigito(cnpj.substring(0, 12) + digito1, pesoCNPJ);
        return cnpj.equals(cnpj.substring(0, 12) + digito1.toString() + digito2.toString());
    }

    public static byte[] inputStreamToByte(InputStream inputStream) {

        try {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = inputStream.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum);
                }
            } catch (IOException ex) {

                return null;
            }
            byte[] bytes = bos.toByteArray();
            inputStream.close();
            return bytes;
        } catch (Exception e) {
            try {
                inputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    public static byte[] fileToByte(File file) {
        FileInputStream fis = null;

        try {

            fis = new FileInputStream(file);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            try {
                for (int readNum; (readNum = fis.read(buf)) != -1;) {
                    bos.write(buf, 0, readNum); //no doubt here is 0
                    //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.

                }
            } catch (IOException ex) {

                return null;
            }
            byte[] bytes = bos.toByteArray();
            fis.close();
            return bytes;
        } catch (Exception e) {
            try {
                fis.close();
            } catch (IOException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }

    public static Double arredondamento(Double valor) {

        return arredondamento(valor, 2);
    }

    public static BigDecimal arredondamento(BigDecimal valor, int numCasas) {

        return valor.setScale(numCasas, RoundingMode.HALF_EVEN);
    }

    public static BigDecimal arredondamento(BigDecimal valor) {

        return valor.setScale(2, RoundingMode.HALF_EVEN);
    }

    public static Double arredondamento(Double valor, int numCasas) {
        if (valor == null) {
            return null;
        }
        BigDecimal bigDecimal = new BigDecimal(valor);
        bigDecimal = bigDecimal.setScale(numCasas, RoundingMode.HALF_UP);
        valor = bigDecimal.doubleValue();

        return valor;
    }

    public static String normalize(String str) {
        char[] chars = str.toCharArray();
        StringBuffer ret = new StringBuffer(chars.length * 2);
        for (int i = 0; i < chars.length; ++i) {
            char aChar = chars[i];
            if (aChar == ' ' || aChar == '\t') {
                ret.append(' '); // convertido para espa?o
            } else if (aChar > ' ' && aChar < 256) {
                if (FIRST_CHAR[aChar - ' '] != ' ') {
                    ret.append(FIRST_CHAR[aChar - ' ']); // 1 caracter
                }
                if (SECOND_CHAR[aChar - ' '] != ' ') {
                    ret.append(SECOND_CHAR[aChar - ' ']); // 2 caracteres
                }
            }
        }

        return ret.toString();
    }

    public static String retirarCaracteresEspeciais(String s) {

        if (s != null && !s.equals("")) {
            s = s.trim();
            s = s.replace(".", "");
            s = s.replace("º", "");
            s = s.replace("ª", "");
            s = s.replace("º", "");
            s = s.replace("§", "");
            s = s.replace("-", "");
            s = s.replace("/", "");
            s = s.replace(")", "");
            s = s.replace("(", "");

            s = s.trim();

            String acentuado = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
            String semAcento = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";

            char[] tabela = new char[256];

            for (int i = 0; i
                    < tabela.length;
                    ++i) {
                tabela[i] = (char) i;

            }
            for (int i = 0; i
                    < acentuado.length();
                    ++i) {
                tabela[acentuado.charAt(i)] = semAcento.charAt(i);

            }

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i
                    < s.length();
                    ++i) {
                char ch = s.charAt(i);

                if (ch < 256) {
                    sb.append(tabela[ch]);

                } else {
                    sb.append(ch);
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static long difDatasMilliSeg(Date date1, Date date2) {
        long differenceMilliSeconds = date2.getTime() - date1.getTime();
        if (date1.after(date2)) {
            differenceMilliSeconds = (differenceMilliSeconds * -1);

        }
        return differenceMilliSeconds;
    }

    /*
     * long differenceMilliSeconds = date2.getTime() - date1.getTime(); return
     * differenceMilliSeconds; System.out.println("diferenca em milisegundos: "
     * + differenceMilliSeconds); System.out.println("diferenca em segundos: " +
     * (differenceMilliSeconds / 1000)); System.out.println("diferenca em
     * minutos: " + (differenceMilliSeconds / 1000 / 60));
     * System.out.println("diferenca em horas: " + (differenceMilliSeconds /
     * 1000 / 60 / 60)); System.out.println("diferenca em dias: " +
     * (differenceMilliSeconds / 1000 / 60 / 60 / 24)); }
     */
    public static long difdatasHoras(Date date1, Date date2) {
        long diffhoras = (difDatasMilliSeg(date1, date2) / 1000 / 60 / 60);
        return diffhoras;
    }

    public static long difdatasSeg(Date date1, Date date2) {
        long diffhoras = (difDatasMilliSeg(date1, date2) / 1000);
        return diffhoras;
    }

    public static long difdatasMin(Date date1, Date date2) {
        long diffhoras = (difDatasMilliSeg(date1, date2) / 1000 / 60);
        return diffhoras;
    }

    public static long difdatasDias(Date date1, Date date2) {
        long diffhoras = (difDatasMilliSeg(date1, date2) / 1000 / 60 / 60 / 24);
        return diffhoras;
    }

    public static String retornaNumeros(String texto) {
        texto = texto.replaceAll("\\D*", ""); //To numeric digits only
        return texto;
    }

    public static boolean somenteNumeros(String valor) {

        boolean aprovado = false;

        try {
            Double test1 = Double.parseDouble(valor);
            aprovado = true;
        } catch (Exception e) {
        }

        if (aprovado) {
            return true;
        }

        try {
            Integer test2 = Integer.parseInt(valor);
            aprovado = true;
        } catch (Exception e) {
        }

        if (aprovado) {
            return true;
        }

        try {
            Long test3 = Long.parseLong(valor);
            aprovado = true;
        } catch (Exception e) {
        }

        if (aprovado) {
            return true;
        } else {
            return false;
        }

    }

    public static String primeiraLetraMaiuscula(String palavra) {

        if (palavra != null) {
            int len = palavra.length();
            String out = "";
            out += palavra.substring(0, 1).toUpperCase();
            out += palavra.substring(1, len);
            return out;
        }
        return palavra;
    }

    public static String tirarCaracteresEspeciais(String s) {

        if (s != null && !s.equals("")) {
            s = s.trim();
            s = s.replace(".", "");
            s = s.replace("~", "");
            s = s.replace("^", "");
            s = s.replace("´", "");
            s = s.replace("`", "");
            s = s.replace("º", "");
            s = s.replace("ª", "");
            s = s.replace("º", "");
            s = s.replace("§", "");
            //s = s.replace("-", "");
            s = s.replace("/", "");
            s = s.replace(")", "");
            s = s.replace("(", "");
            s = s.trim();

            String acentuado = "çÇáéíóúýÁÉÍÓÚÝàèìòùÀÈÌÒÙãõñäëïöüÿÄËÏÖÜÃÕÑâêîôûÂÊÎÔÛ";
            String semAcento = "cCaeiouyAEIOUYaeiouAEIOUaonaeiouyAEIOUAONaeiouAEIOU";

            char[] tabela = new char[256];

            for (int i = 0; i
                    < tabela.length;
                    ++i) {
                tabela[i] = (char) i;

            }
            for (int i = 0; i
                    < acentuado.length();
                    ++i) {
                tabela[acentuado.charAt(i)] = semAcento.charAt(i);

            }

            StringBuffer sb = new StringBuffer();

            for (int i = 0; i
                    < s.length();
                    ++i) {
                char ch = s.charAt(i);

                if (ch < 256) {
                    sb.append(tabela[ch]);

                } else {
                    sb.append(ch);
                }
            }
            return sb.toString();
        }
        return null;
    }

    //
    public static void main(String[] args) {
        System.out.println(Encriptar("INTER123"));
        System.out.println(alterTextExtesion("TEST NAME EXTENSION LOWERCASE"));
    }

    public static String Encriptar(String senha) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(senha.getBytes());
            BigInteger hash = new BigInteger(1, md.digest());
            String retornaSenha = hash.toString(16);
            return retornaSenha;
        } catch (NoSuchAlgorithmException ns) {
            ns.printStackTrace();
            return null;
        }

    }

    public static String replicar(String texto, int vezes) {
        String resultado = "";
        for (int i = 0; i < vezes; i++) {
            resultado = resultado + texto;
        }
        return resultado;
    }

    public static String zeroEsquerda(String valor, int quantDigito) {
        String resultado = replicar("0", quantDigito - valor.length());
        resultado = resultado + valor;
        return resultado;
    }

    public static String espacoEsquerda(String valor, int quantDigito) {
        String resultado = replicar(" ", quantDigito - valor.length());
        resultado = resultado + valor;
        return resultado;
    }

    public static String espacoDireita(String valor, int quantDigito) {
        for (int i = 0; i < quantDigito; i++) {
            valor += " ";
        }
        return valor;
    }

    public static String pontoDireita(int quantDigito) {
        String valor = "";
        for (int i = 0; i < quantDigito; i++) {
            valor += " . ";
        }
        return valor;

    }

    public static String reaisExtenso(String valor) {
        String extenso = ""; //variavel que retornar� o valor por extenso
        String tipo = ""; //variavel que definira o tipo de n�mero ( unitario, dezena, centena)
        String parte1 = ""; //variavel que armazenar� temporariamente o valor que foi analizado
        int cont = valor.length(); //conta quantos n�meros tem o valor
        int i = 0; //variavel de controle do while
        int somar = 1; //variavel que dir� o valor a ser somado � vari�vel "i"

        while (i < cont) {
            somar = 1;
//verifica se o caracter atual � n�mero ou uma "," ou "."
            if (valor.substring(i, i + 1).equals(",") == false && valor.substring(i, i + 1).equals(".") == false) {
//o valor passado tem 3 d�gitos, ex: R$ 1,20
                if (cont == 4 && i == 0) {
                    tipo = "unitario";
                } else if (cont == 4 && i == 2) {
                    tipo = "dezena";
                } else if (cont == 4 && i == 3) {
                    tipo = "unitario";
                } //o valor passado tem 4 d�gitos, ex: R$ 11,20
                else if (cont == 5 && i == 0) {
                    tipo = "dezena";
                } else if (cont == 5 && i == 1) {
                    tipo = "unitario";
                } else if (cont == 5 && i == 3) {
                    tipo = "dezena";
                } else if (cont == 5 && i == 4) {
                    tipo = "unitario";
                } //o valor passado tem 5 d�gitos, ex: R$ 111,20
                else if (cont == 6 && i == 0) {
                    tipo = "centena";
                } else if (cont == 6 && i == 1) {
                    tipo = "dezena";
                } else if (cont == 6 && i == 2) {
                    tipo = "unitario";
                } else if (cont == 6 && i == 4) {
                    tipo = "dezena";
                } else if (cont == 6 && i == 5) {
                    tipo = "unitario";
                } //o valor passado tem 6 d?gitos, ex: R$ 1.111,20
                else if (cont == 8 && i == 0) {
                    tipo = "unitario";
                } else if (cont == 8 && i == 2) {
                    tipo = "centena";
                } else if (cont == 8 && i == 3) {
                    tipo = "dezena";
                } else if (cont == 8 && i == 4) {
                    tipo = "unitario";
                } else if (cont == 8 && i == 6) {
                    tipo = "dezena";
                } else if (cont == 8 && i == 7) {
                    tipo = "unitario";
                } //o valor passado tem 7 d�gitos, ex: R$ 11.111,20
                else if (cont == 9 && i == 0) {
                    tipo = "dezena";
                } else if (cont == 9 && i == 1) {
                    tipo = "unitario";
                } else if (cont == 9 && i == 3) {
                    tipo = "centena";
                } else if (cont == 9 && i == 4) {
                    tipo = "dezena";
                } else if (cont == 9 && i == 5) {
                    tipo = "unitario";
                } else if (cont == 9 && i == 7) {
                    tipo = "dezena";
                } else if (cont == 9 && i == 8) {
                    tipo = "unitario";
                } //o valor passado tem 8 d�gitos, ex: R$ 111.111,20
                else if (cont == 10 && i == 0) {
                    tipo = "centena";
                } else if (cont == 10 && i == 1) {
                    tipo = "dezena";
                } else if (cont == 10 && i == 2) {
                    tipo = "unitario";
                } else if (cont == 10 && i == 4) {
                    tipo = "centena";
                } else if (cont == 10 && i == 5) {
                    tipo = "dezena";
                } else if (cont == 10 && i == 6) {
                    tipo = "unitario";
                } else if (cont == 10 && i == 8) {
                    tipo = "dezena";
                } else if (cont == 10 && i == 9) {
                    tipo = "unitario";
                }

// se o tipo analisado for do tipo unit�rio, define a vari�vel "parte1"
                if (tipo.equals("unitario")) {
                    if (valor.substring(i, i + 1).equals("1")) {
                        parte1 = "Um ";
                    } else if (valor.substring(i, i + 1).equals("2")) {
                        parte1 = "Dois ";
                    } else if (valor.substring(i, i + 1).equals("3")) {
                        parte1 = "Tr?s ";
                    } else if (valor.substring(i, i + 1).equals("4")) {
                        parte1 = "Quatro ";
                    } else if (valor.substring(i, i + 1).equals("5")) {
                        parte1 = "Cinco ";
                    } else if (valor.substring(i, i + 1).equals("6")) {
                        parte1 = "Seis ";
                    } else if (valor.substring(i, i + 1).equals("7")) {
                        parte1 = "Sete ";
                    } else if (valor.substring(i, i + 1).equals("8")) {
                        parte1 = "Oito ";
                    } else if (valor.substring(i, i + 1).equals("9")) {
                        parte1 = "Nove ";
                    } else if (valor.substring(i, i + 1).equals("0")) {
                        parte1 = "";
                    }
                }
// se o tipo analisado for do tipo dezena, define a vari�vel "parte1"
                if (tipo.equals("dezena")) {
                    if (valor.substring(i, i + 1).equals("1")) {
//se o caracter come�a com "1" � avaliado tb o caracter seguinte
                        if (valor.substring(i, i + 2).equals("10")) {
                            parte1 = "Dez ";
                        } else if (valor.substring(i, i + 2).equals("11")) {
                            parte1 = "Onze ";
                        } else if (valor.substring(i, i + 2).equals("12")) {
                            parte1 = "Doze ";
                        } else if (valor.substring(i, i + 2).equals("13")) {
                            parte1 = "Treze ";
                        } else if (valor.substring(i, i + 2).equals("14")) {
                            parte1 = "Quatorze ";
                        } else if (valor.substring(i, i + 2).equals("15")) {
                            parte1 = "Quinze ";
                        } else if (valor.substring(i, i + 2).equals("16")) {
                            parte1 = "Dezesseis ";
                        } else if (valor.substring(i, i + 2).equals("17")) {
                            parte1 = "Dezesete ";
                        } else if (valor.substring(i, i + 2).equals("18")) {
                            parte1 = "Dezoito ";
                        } else if (valor.substring(i, i + 2).equals("19")) {
                            parte1 = "Dezenove ";
                        }
                        somar = 2; //como foi analisado duas casas do valor, a vari�vel "i" ser� acrescentada em 2
                    } //se o caracter n�o come�a com "1", j� � definido a vari�vel parte1
                    else if (valor.substring(i, i + 1).equals("2")) {
                        parte1 = "Vinte ";
                    } else if (valor.substring(i, i + 1).equals("3")) {
                        parte1 = "Trinta ";
                    } else if (valor.substring(i, i + 1).equals("4")) {
                        parte1 = "Quarenta ";
                    } else if (valor.substring(i, i + 1).equals("5")) {
                        parte1 = "Cinquenta ";
                    } else if (valor.substring(i, i + 1).equals("6")) {
                        parte1 = "Sessenta ";
                    } else if (valor.substring(i, i + 1).equals("7")) {
                        parte1 = "Setenta ";
                    } else if (valor.substring(i, i + 1).equals("8")) {
                        parte1 = "Oitenta ";
                    } else if (valor.substring(i, i + 1).equals("9")) {
                        parte1 = "Noventa ";
                    } else if (valor.substring(i, i + 1).equals("0")) {
                        parte1 = "";
                    }
                }
// se o tipo analisado for do tipo centena, define a vari�vel "parte1"
                if (tipo.equals("centena")) {
                    if (valor.substring(i, i + 1).equals("1")) {
//se o caracter come�a com "1", avalia os proximos dois caracteres, para
//definir se � "Cem" ou "Cento"
                        if (valor.substring(i, i + 3).equals("100")) {
                            parte1 = "Cem ";
                        } else {
                            parte1 = "Cento ";
                        }
                    } else if (valor.substring(i, i + 1).equals("2")) {
                        parte1 = "Duzentos ";
                    } else if (valor.substring(i, i + 1).equals("3")) {
                        parte1 = "Trezentos ";
                    } else if (valor.substring(i, i + 1).equals("4")) {
                        parte1 = "Quatrocentos ";
                    } else if (valor.substring(i, i + 1).equals("5")) {
                        parte1 = "Quinhentos ";
                    } else if (valor.substring(i, i + 1).equals("6")) {
                        parte1 = "Seiscentos ";
                    } else if (valor.substring(i, i + 1).equals("7")) {
                        parte1 = "Setecentos ";
                    } else if (valor.substring(i, i + 1).equals("8")) {
                        parte1 = "Oitocentos ";
                    } else if (valor.substring(i, i + 1).equals("9")) {
                        parte1 = "Novecentos ";
                    } else if (valor.substring(i, i + 1).equals("0")) {
                        parte1 = "";
                    }
                }
//se o caracter � igual a "0" ou "," n�o � adicionado a palavra "e "
                if (i == 0 || valor.substring(i - 1, i) == ",") {
                    extenso = extenso + parte1;
                } else if (valor.substring(i, i + 1).equals("0") == false) { //se o caracter � igual � "0"
                    if (extenso.equals("")) { //verifica se a vari�vel extenso � nula
                        extenso = extenso + parte1;
                    } else if (extenso.substring(extenso.length() - 4).equals("Mil ")) { //verifica se a vari�vel extenso � nula
                        extenso = extenso + parte1;
                    } else {
                        extenso = extenso + "e " + parte1;
                    }
                }

            } //verifica se o caracter atual � ","
            else if (valor.substring(i, i + 1).equals(",")) {
                if (cont == 4 && Integer.parseInt(valor.substring(i - 1, i)) == 1) {
//se o valor tem 3 d�gitos e come�a com "1", adiciona a palavra Real
                    extenso = extenso + "Real ";
                } else if (cont > 4 || Integer.parseInt(valor.substring(i - 1, i)) > 1) {
//se o valor tem mais 3 d�gitos ou n�o come�a com "1", adiciona a palavra Reais
                    extenso = extenso + "Reais ";
                }

            } //verifica se o caracter atual � "."
            else if (valor.substring(i, i + 1).equals(".")) {
                extenso = extenso + "Mil "; //se sim, acrescenta a palavra "Mil"
            }

            i = i + somar;

        }
//verifica se as duas casas decimais � maior que 1
        if (Integer.parseInt(valor.substring(cont - 2, cont)) > 1) {
            extenso = extenso + "Centavos"; //se sim, acrescenta a palavra Centavos
        } else if (Integer.parseInt(valor.substring(cont - 2, cont)) == 1) {
            extenso = extenso + "Centavo"; //se n�o, acrescenta a palavra Centavo
        }

        return extenso;
    }

    public static void zeraHora(Date date) {
        Date result = new Date(date.getYear(), date.getMonth(), date.getDate(), 0, 0, 0);
        date.setTime(result.getTime());
    }

    public static void zeraDate(Date date) {
        Date result = new Date(0, 0, 0, date.getHours(), date.getMinutes(), date.getSeconds());
        date.setTime(result.getTime());
    }

    public static Date StringToDate(String ddmmaaaa) {
        if (!ddmmaaaa.isEmpty()) {
            ddmmaaaa = Utils.retornaNumeros(ddmmaaaa);
            Integer ano = Integer.parseInt(ddmmaaaa.substring(4)) - 1900;
            Integer mes = Integer.parseInt(ddmmaaaa.substring(2, 3)) - 1;
            Integer dia = Integer.parseInt(ddmmaaaa.substring(0, 1));

            return new Date(ano, mes, dia);
        }
        return null;
    }

    public static Double StringToDouble(String valor, String dec, String separador) {
        Double val = 0.0;
        if (!valor.isEmpty()) {
            Integer d = 0;
            if (dec.equalsIgnoreCase("A")) {
                if (valor.contains(separador)) {
                    d = (valor.length() - valor.indexOf(separador)) - 1;
                }
            } else {
                d = Integer.parseInt(dec);
            }
            Double div = Double.parseDouble("1" + Utils.replicar("0", d));
            val = Double.parseDouble(Utils.retornaNumeros(valor)) / div;
        }
        return val;
    }

    public static Double StringToDouble(String valor, String dec) {
        return StringToDouble(valor, dec, ",");
    }

    public static Double StringToDouble(String valor) {
        return StringToDouble(valor, "A", ",");
    }

    public static Date getDatePrimeiroDiaDoMes() {
        Calendar primeiroDiaMes = Calendar.getInstance();
        primeiroDiaMes.set(Calendar.DAY_OF_MONTH, 1);

        return primeiroDiaMes.getTime();
    }

    public static Date getDateUltimoDiaDoMes() {

        Calendar ultimoDiaMes = Calendar.getInstance();
        ultimoDiaMes.add(Calendar.MONTH, 1);
        ultimoDiaMes.set(Calendar.DAY_OF_MONTH, 1);
        ultimoDiaMes.add(Calendar.DAY_OF_MONTH, -1);

        return ultimoDiaMes.getTime();
    }

    public static Date getDateUltimoDiaDoMes(Date dataReferencia) {

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(dataReferencia);

        int dia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mes = (cal.get(Calendar.MONDAY) + 1);
        int ano = cal.get(Calendar.YEAR);

        try {
            Date data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mes + "/" + ano);
            return data;
        } catch (ParseException e) {
            return null;
        }

    }

    public static Date getDatePrimeiroDiaDoMes(Date dataReferencia) {

        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(dataReferencia);

        int dia = 1;
        int mes = (cal.get(Calendar.MONDAY) + 1);
        int ano = cal.get(Calendar.YEAR);

        try {
            Date data = (new SimpleDateFormat("dd/MM/yyyy")).parse(dia + "/" + mes + "/" + ano);
            return data;
        } catch (ParseException e) {
            return null;
        }

    }

    public static String formatarMoeda(BigDecimal vlr) {
        return formatarMoeda(vlr.doubleValue());
    }

    public static String formatarMoeda(Double vlr) {
        java.text.DecimalFormat df = new java.text.DecimalFormat("###,###,##0.00");
        return df.format(vlr);
    }

    public static String maiuscula(String entrada) {
        entrada = entrada.toLowerCase();
        entrada = entrada.trim();

        String posicao;
        String mais = "";
        String tudo;
        String maiuscula2;

        posicao = "" + entrada.charAt(0);//pega a primeira letra que sera maiuscula
        String pos = posicao.toUpperCase();    //transforma em maiuscula

        for (int i = 1; i < entrada.length(); i++) {  //coloca o for de acordo com o tamanho
            mais = mais + entrada.charAt(i); //acrescenta as letras

            if (entrada.charAt(i) == ' ') {  //se houver um espa�o a pr�xima vai ser transformada em maisucula.
                String maiuscula = "" + entrada.charAt(i + 1);//pega a pr�xima apos o espa�o
                maiuscula2 = maiuscula.toUpperCase();       //transforma em maiuscula.
                mais = mais + maiuscula2;//acrescenta a maiuscula a palavra completa, que se chama mais
                i = i + 1; //soma um, pois uma letra minuscula foi substituida e ja foi acrescentada
            }
        }
        tudo = pos + mais;
        return tudo;
    }

    public static String getExtensaoByFileName(String fileName) {
        String extensao = "";

        for (StringTokenizer stringTokenizer = new StringTokenizer(fileName, "."); stringTokenizer.hasMoreTokens();) {
            extensao = stringTokenizer.nextToken();
        }

        return extensao;
    }

    public static boolean isValidBarCodeEAN(String barCode) {
        int digit;
        int calculated;
        String ean;
        String checkSum = "131313131313";
        int sum = 0;
        if (barCode.length() == 8 || barCode.length() == 13) {
            digit = Integer.parseInt("" + barCode.charAt(barCode.length() - 1));
            ean = barCode.substring(0, barCode.length() - 1);
            for (int i = 0; i <= ean.length() - 1; i++) {
                sum += (Integer.parseInt("" + ean.charAt(i))) * (Integer.parseInt("" + checkSum.charAt(i)));
            }
            calculated = 10 - (sum % 10);
            return (digit == calculated);
        } else {
            return false;
        }
    }

    /**
     *
     * @param valor este valor não esta formatado esta como inteiro e suas casas
     * decimais sao definidas pela variavel casas decimais
     * @param casasDecimais
     * @return
     */
    public static Double StringToDouble(String valor, int casasDecimais) {
        //000001511207000  15c
        //0000015112.07000 . na posicao 10
        StringBuilder sb = new StringBuilder(valor);
        sb.insert(valor.length() - casasDecimais, ".");

        BigDecimal i = new BigDecimal(sb.toString());

        //BigDecimal result = new BigDecimal(i.doubleValue() / (10 ^ casasDecimais));
        return i.doubleValue();
    }

    public static String getStringTamanhoLimitado(String valor, int tamanhoMaximo) {
        if (valor.length() > tamanhoMaximo) {
            valor = valor.substring(0, tamanhoMaximo);
        }

        return valor;
    }

    public static String doubleToString(BigDecimal value, int quantCasaDecimalMax, int quantCasaDecimalMin) {
        return doubleToString(value.doubleValue(), quantCasaDecimalMax, quantCasaDecimalMin);
    }

    public static String doubleToString(BigDecimal value) {
        return doubleToString(value.doubleValue());
    }

    public static String doubleToString(Double value) {
        return doubleToString(value, 2, 2);
    }

    public static String doubleToString(Double value, int quantCasaDecimalMax, int quantCasaDecimalMin) {
        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        nf.setMaximumFractionDigits(quantCasaDecimalMax);
        nf.setMinimumFractionDigits(quantCasaDecimalMin);

        return nf.format(value);
    }

    public static boolean salvar(String diretorio, String nome, String conteudo) throws IOException {

        FileWriter fw;

        File fDir = new File(diretorio);
        fDir.mkdirs();

        File file = new File(diretorio + "/" + nome);

        if (file.exists()) {
            file.delete();
        }

        fw = new FileWriter(file, true);
        fw.write(conteudo);
        fw.close();
        return true;

    }

    public static void gerarArquivoByByte(String caminho, byte[] bytes) throws FileNotFoundException, IOException {

        File someFile = new File(caminho);
        File diretorio = new File(someFile.getPath().replace(someFile.getName(), ""));
        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();

    }

    public static String formatStringDoubleToString(String qCom, int minDigFracao, int maxDigFracao) {
        Double value = Double.parseDouble(qCom);
        BigDecimal bd;

        NumberFormat nf = NumberFormat.getInstance(new Locale("pt", "BR"));
        nf.setMaximumFractionDigits(maxDigFracao);
        nf.setMinimumFractionDigits(minDigFracao);

        return nf.format(value);

    }

    public static String getStringValidada(String value) {
        if (value == null || value.trim().equals("")) {
            return null;
        }

        value = value.trim();
        value = tirarCaracteresEspeciais(value);

        value = value.replaceAll("\r", "");
        value = value.replaceAll("\t", "");
        value = value.replaceAll("\n", "");

        return value;
    }

    public static boolean isData(String filtro) {
        try {
            new SimpleDateFormat("dd/MM/yyyy").parse(filtro);
            return true;
        } catch (Exception e) {
        }

        try {
            new SimpleDateFormat("dd/MM/yy").parse(filtro);
            return true;
        } catch (Exception e) {
        }

        return false;
    }

    public static String converterStringToDateMySql(String filtro) {

        try {
            Date data = new SimpleDateFormat("dd/MM/yy").parse(filtro);
            return new SimpleDateFormat("yyyy-MM-dd").format(data);
        } catch (Exception e) {
        }

        try {
            Date data = new SimpleDateFormat("dd/MM/yyyy").parse(filtro);
            return new SimpleDateFormat("yyyy-MM-dd").format(data);
        } catch (Exception e) {
        }

        return null;
    }

    public static String getIDEntity(Object entity) throws Exception {
        Object id = getValueFromObject(entity, "id");

        return id == null ? null : id.toString();

    }

    public static void setValueToObject(String fieldName, Object o, Object value) throws Exception {
        Field field = null;
        for (Field f : o.getClass().getDeclaredFields()) {
            if (f.getName().equalsIgnoreCase(fieldName)) {
                field = f;
                break;
            }
        }

        if (field == null) {
            throw new Exception("Campo não localizado no objeto");
        }

        field.setAccessible(true);
        field.set(o, value);

        /* Class[] partypes = new Class[1];
        partypes[0] = field.getType();
        Method method;
        method = o.getClass().getMethod("set" + Utils.primeiraLetraMaiuscula(field.getName()), partypes);
        Object[] arglist = new Object[1];
        arglist[0] = value;
        method.invoke(o, arglist);*/
    }

    public static Object getValueFromObject(Object o, String fieldName) throws Exception {

        String campoMetodo = Utils.primeiraLetraMaiuscula(fieldName);
        Method m = null;
        Object result;

        Method[] methods = o.getClass().getMethods();
        String nm1 = "get" + campoMetodo;
        String nm2 = "is" + campoMetodo;
        for (Method method : methods) {
            String nomeMetodo = method.getName();

            if (nomeMetodo.equals(nm1) || nomeMetodo.equals(nm2) || nomeMetodo.equals(fieldName)) {
                m = method;
                break;
            }
        }

        //m = o.getClass().getMethod("get" + campoMetodo);
        result = m.invoke(o);

        return result;

    }

    public static boolean isWindows() {
        String os = System.getProperty("os.name");
        if (os.startsWith("Windows")) {
            return true;
        } else {
            return false;
        }
    }

    public final static String getHDSerial() throws IOException {
        String os = System.getProperty("os.name");
        try {
            if (os.startsWith("Windows")) {
                String serial = getHDSerialWindows("C");
                for (StringTokenizer stringTokenizer = new StringTokenizer(serial, " ", true); stringTokenizer.hasMoreTokens();) {
                    String token = stringTokenizer.nextToken();
                    return Encriptar(token);
                }
            } else {
                String serial = getHDSerialLinux("/");
                for (StringTokenizer stringTokenizer = new StringTokenizer(serial, " ", true); stringTokenizer.hasMoreTokens();) {
                    String token = stringTokenizer.nextToken();
                    return Encriptar(token);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IOException(ex.getMessage());
        }
        return null;
    }

    private static String getHDSerialWindows(String drive) throws IOException, InterruptedException {
        String sc = "cmd /c" + "wmic diskdrive get serialnumber";

        Process p = Runtime.getRuntime().exec(sc);
        p.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));

        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        return (sb.substring(sb.toString().lastIndexOf("r") + 1).trim());
    }

    private static String getHDSerialLinux(String drive) throws IOException, InterruptedException {
        String sc = "/sbin/udevadm info --query=property --name=sda"; // get HDD parameters as non root user
        String[] scargs = {"/bin/sh", "-c", sc};

        Process p = Runtime.getRuntime().exec(scargs);
        p.waitFor();

        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;
        StringBuilder sb = new StringBuilder();

        while ((line = reader.readLine()) != null) {
            if (line.indexOf("ID_SERIAL_SHORT") != -1) { // look for ID_SERIAL_SHORT or ID_SERIAL
                sb.append(line);
            }
        }

        return (sb.toString().substring(sb.toString().indexOf("=") + 1));
    }

    public static Object deserializar(String json, Type type) {
        if (json == null || json.trim().equals("")) {
            return null;
        }

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();

        Object entity = gson.fromJson(json, type);
        return entity;
        /* ObjectMapper mapper = new ObjectMapper();
        Object jsonInString;

        try {
            jsonInString = mapper.readValue(json, type);
            return jsonInString;
        } catch (IOException ex) {
            return null;
        }*/

    }

    public static String serializar(Object obj) {
        return serializar(obj, null);
    }

    public static String serializar(Object obj, ExclusionStrategy exclusionStrategy) {
        Gson gson;
        if (obj == null) {
            return null;
        }

        if (exclusionStrategy != null) {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .addSerializationExclusionStrategy(exclusionStrategy).create();
        } else {
            gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

        }

        /*

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString;
        try {
            jsonInString = mapper.writeValueAsString(obj);
            return jsonInString;
        } catch (JsonProcessingException ex) {
            return null;
        }*/
        return gson.toJson(obj);
    }

    public final static String getMotherboardSerial() throws IOException {
        String os = System.getProperty("os.name");
        try {
            if (os.startsWith("Windows")) {
                return getMotherboardSerialWindows();
            } else if (os.startsWith("Linux")) {
                return getMotherboardSerialLinux();
            } else {
                throw new IOException("unknown operating system: " + os);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IOException(ex.getMessage());
        }
    }

    // Implementacoes
    /*
     * Captura serial de placa mae no WINDOWS, atraves da execucao de script
     * visual basic
     */
    private static String getMotherboardSerialWindows() {
        String result = "";
        try {
            File file = File.createTempFile("realhowto", ".vbs");
            file.deleteOnExit();
            FileWriter fw = new java.io.FileWriter(file);
            String vbs = "Set objWMIService = GetObject(\"winmgmts:\\\\.\\root\\cimv2\")\n"
                    + "Set colItems = objWMIService.ExecQuery _ \n"
                    + "   (\"Select * from Win32_BaseBoard\") \n"
                    + "For Each objItem in colItems \n"
                    + "    Wscript.Echo objItem.SerialNumber \n"
                    + "    exit for  ' do the first cpu only! \n" + "Next \n";
            fw.write(vbs);
            fw.close();
            Process p = Runtime.getRuntime().exec(
                    "cscript //NoLogo " + file.getPath());
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.trim();
    }

    /*
     * Captura serial de placa mae em sistemas LINUX, atraves da execucao de
     * comandos em shell.
     */
    private static String getMotherboardSerialLinux() {
        String result = "";
        try {
            String[] args = {"bash", "-c", "lshw -class bus | grep serial"};
            Process p = Runtime.getRuntime().exec(args);
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = input.readLine()) != null) {
                result += line;
            }
            input.close();
        } catch (Exception e) {
        }
        if (result.trim().length() < 1 || result == null) {
            result = "NO_DISK_ID";
        }
        return filtraString(result, "serial: ");
    }

    private static String filtraString(String nome, String delimitador) {
        return nome.split(delimitador)[1];
    }

    public static Type getTypeIDByEntity(Object o) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {

                return field.getType();
            }
        }

        return null;
    }

    public static Class getIDTypeByClass(Class clazz) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                return field.getType();
            }
        }

        return null;
    }

    public static Object getIDByEntity(Object o) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                return field.get(o);
            }
        }

        return null;
    }

    public static boolean possuiSequence(Object o) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : o.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class) && field.isAnnotationPresent(GeneratedValue.class)) {
                return true;
            }
        }

        return false;
    }

    public static void setIDIntoEntity(Object entidade, Object id) throws IllegalArgumentException, IllegalAccessException {
        for (Field field : entidade.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                field.setAccessible(true);
                field.set(entidade, id);
                break;
            }
        }
    }

    public static AtributoPadrao getAtributoPadraoByClass(Class clazz) {

        try {
            Field fieldSelecionado = null;
            for (Field field : clazz.getDeclaredFields()) {
                if (field.getType() == AtributoPadrao.class) {
                    fieldSelecionado = field;
                    break;
                }
            }

            if (fieldSelecionado == null) {
                return null;
            }

            fieldSelecionado.setAccessible(true);
            return (AtributoPadrao) fieldSelecionado.get(clazz);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            return null;
        }

    }

    public static AtributoPadrao getAtributoPadraoByObject(Object obj) {

        try {
            Field fieldSelecionado = null;
            for (Field field : obj.getClass().getDeclaredFields()) {
                if (field.getType() == AtributoPadrao.class) {
                    fieldSelecionado = field;
                    break;
                }
            }

            if (fieldSelecionado == null) {
                return null;
            }

            fieldSelecionado.setAccessible(true);
            AtributoPadrao atributoPadrao = (AtributoPadrao) fieldSelecionado.get(obj);
            fieldSelecionado.setAccessible(false);
            return atributoPadrao;
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            return null;
        }

    }

    public static boolean existeAtributoPadraoByObjectClass(Class clazz) {

        for (Field field : clazz.getDeclaredFields()) {
            if (field.getType() == AtributoPadrao.class) {
                return true;
            }
        }

        return false;

    }

    public static AtributoPadrao getAtributoPadraoByObjectClass(Object object) {
        return getAtributoPadraoByObject(object);
    }

    public static Date localDateToDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static LocalDate dateToLocalDate(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate;
    }

    /**
     * Este metodo deve ser chamado ao receber uma invocação de client !
     *
     * @param palavra
     * @return
     */
    public static String decodeString(String palavra) {
        if (palavra == null) {
            return null;
        }
        palavra = palavra.replace("_17953_", "/");
        return palavra;
    }

    /**
     * Este metodo deve ser utilizado para chamar uma url de rest
     *
     * @param palavra
     * @return
     */
    public static String encodeString(String palavra) {
        char one;
        StringBuffer n = new StringBuffer(palavra.length());
        for (int i = 0; i < palavra.length(); i++) {
            one = palavra.charAt(i);
            switch (one) {
                case ' ':
                    n.append('%');
                    n.append('2');
                    n.append('0');
                    break;

                case '/': {
                    n.append('_');
                    n.append('1');
                    n.append('7');
                    n.append('9');
                    n.append('5');
                    n.append('3');
                    n.append('_');
                    break;
                }
                default:
                    n.append(one);
            }
        }
        return n.toString();
    }

    public static InputStream byteArrToInputStream(byte[] bytes) {

        String str = "converting to input stream";
        byte[] content = str.getBytes();
        int size = content.length;
        InputStream is = null;
        byte[] b = new byte[size];
        try {
            is = new ByteArrayInputStream(content);
            is.read(b);
            return is;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception ex) {

            }
        }

    }

    public static void unzip(File filezip) throws IOException {

        byte[] buffer = new byte[1024];
        ZipInputStream zis = new ZipInputStream(new FileInputStream(filezip));
        ZipEntry zipEntry = zis.getNextEntry();
        while (zipEntry != null) {
            String fileName = zipEntry.getName();
            String path = filezip.getAbsolutePath();
            path = path.replace(filezip.getName(), "");

            File newFile = new File(path + fileName);
            FileOutputStream fos = new FileOutputStream(newFile);
            int len;
            while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
            fos.close();
            zipEntry = zis.getNextEntry();
        }
        zis.closeEntry();
        zis.close();
    }

    public static byte[] zip(List<File> files, String diretorio, String nmArquivo) throws IOException {

        File caminho = new File(diretorio);
        if (!caminho.exists()) {
            caminho.mkdirs();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ZipOutputStream zipfile = new ZipOutputStream(new FileOutputStream(diretorio + "/" + nmArquivo));

        ZipEntry zipentry = null;
        for (File file : files) {

            zipentry = new ZipEntry(file.getName());
            zipfile.putNextEntry(zipentry);
            zipfile.write(Utils.fileToByte(file));
        }
        zipfile.close();
        return bos.toByteArray();
    }

    public static File instanceFileDirectoryCreate(String home) {
        File file = new File(home);

        if (!file.exists()) {
            file.mkdirs();
        }

        return file;

    }

    public static void salveBytes(byte[] bytes, String diretorio) {
        FileOutputStream in = null;
        try {
            java.io.File file = new java.io.File(diretorio);
            in = new FileOutputStream(file);
            in.write(bytes);
            in.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static String getNomeSobrenomePessoa(String nome, String tipoPessoa) {
        if (nome == null) {
            return null;
        }

        if (tipoPessoa.equals("PJ")) {
            return nome;
        }

        List lista = new ArrayList();
        String palavra = "";
        nome = nome.trim();
        for (int i = 0; i < nome.length(); i++) {
            String caracter = nome.substring(i, i + 1);
            if (caracter.equals(" ") || i == nome.length() - 1) {
                if (i == nome.length() - 1) {
                    palavra += caracter;
                }
                palavra = palavra.trim();
                if (!palavra.equals("")) {
                    lista.add(palavra);
                    palavra = "";
                }
            } else if (!caracter.equals(" ")) {
                palavra += caracter;
            }
        }
        if (lista.size() == 1) {
            return (String) lista.get(0);
        } else {
            return lista.get(0) + " " + lista.get(lista.size() - 1);
        }
    }

    public static boolean compareDoubleIgual(Double valor1, Double valor2) {
        valor1 = arredondamento(valor1);
        valor2 = arredondamento(valor2);
        return valor1.equals(valor2);
    }

    public static boolean compareDoubleMenor(Double valor1, Double valor2) {
        valor1 = arredondamento(valor1);
        valor2 = arredondamento(valor2);
        return valor1 < valor2;
    }

    public static boolean compareDoubleMenorOuIgual(Double valor1, Double valor2) {
        valor1 = arredondamento(valor1);
        valor2 = arredondamento(valor2);
        return valor1 <= valor2;
    }

    public static boolean compareDoubleMaior(Double valor1, Double valor2) {
        valor1 = arredondamento(valor1);
        valor2 = arredondamento(valor2);
        return valor1 > valor2;
    }

    public static boolean compareDoubleMaiorOuIgual(Double valor1, Double valor2) {
        valor1 = arredondamento(valor1);
        valor2 = arredondamento(valor2);
        return valor1 >= valor2;
    }

    public static void clonar(Object objetoRepassa, Object objetoRecebe) {
        Field[] fieldso1 = objetoRepassa.getClass().getDeclaredFields();

        for (Field field : fieldso1) {
            try {
                field.setAccessible(true);

                Object o = field.get(objetoRepassa);

                field.set(objetoRecebe, o);
            } catch (IllegalArgumentException | IllegalAccessException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static String normalizeXML(String xml) {
        if ((xml != null) && (!"".equals(xml))) {
            xml = xml.replaceAll("\\r\\n", "");
            xml = xml.replaceAll(" standalone=\"no\"", "");
        }
        return xml;
    }

    public static String convertFilepathToJdbcUrl(String filePath) {

        String jdbcPath = "";

        String separator = ":";

        String[] split = filePath.split(separator);

        List<String> hds = Arrays.asList("C:", "c:", "E:", "e:", "D:", "d:", "F:", "f:", "G:", "g:", "H:", "h:", "I:", "i:");

        //Vou verificar se o split resultou em 3 elementos para afirmar
        //se é windows ou linux
        switch (split.length) {
            case 3:
                jdbcPath = "jdbc:firebirdsql:" + split[0] + "/3050" + separator + split[1] + separator + split[2] + "?enconding=ISO8859_1";
                break;
            case 2:
                boolean containsHD = hds.stream().anyMatch((hd) -> {
                    return filePath.contains(hd);
                });

                if (containsHD) {
                    jdbcPath = "jdbc:firebirdsql:localhost/3050" + separator + split[0] + separator + split[1] + "?enconding=ISO8859_1";
                } else {
                    jdbcPath = "jdbc:firebirdsql:" + split[0] + "/3050" + separator + split[1] + "?enconding=ISO8859_1";
                }
                break;
            case 1://Quando o endereço for somente o caminho do .GDB, sem o ip
                jdbcPath = "jdbc:firebirdsql:localhost/3050" + separator + split[0] + "?enconding=ISO8859_1";
                break;
            default:
                break;
        }

        return jdbcPath;
    }

    public static String toCamelCase(String text) {
        char[] carac = new char['_'];
        return StringUtils.remove(WordUtils.capitalizeFully(text, carac), "_");
    }

    public static String alterTextExtesion(String resp) {
        String nameExtension = "";
        for (String s : resp.split(" ")) {
            nameExtension += s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase() + " ";
        }

        return nameExtension;
    }

    public static Map<String, String> getDecodificacaoLiberacao(String arquivo) {
        Map<String, String> hm = new HashMap<String, String>();
        try {
            if (arquivo != null) {
                Properties liberacao = new Properties();
                // String decode = liberacaoDecode(7, arquivo);
                liberacao.load(new StringReader(arquivo));
                Enumeration<Object> e = liberacao.keys();
                while (e.hasMoreElements()) {
                    String s = (String) e.nextElement();
                    hm.put(s, liberacao.getProperty(s));
                }
                return hm;
            }
            return hm;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String liberacaoDecode(Integer chave, String texto) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < texto.length(); i++) {
            result.append((char) (((int) texto.charAt(i) - chave) % 255));
        }
        return result.toString();
    }

    public static String formatDateSefaz(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssXXX");
        return sdf.format(date);
    }

    public static String formatarString(String texto, String mascara) {

        try {

            MaskFormatter mf = new MaskFormatter(mascara);

            mf.setValueContainsLiteralCharacters(false);

            return mf.valueToString(texto);

        } catch (ParseException e) {

            return null;

        }
    }

}
