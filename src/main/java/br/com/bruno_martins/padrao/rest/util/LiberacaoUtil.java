
package br.com.bruno_martins.padrao.rest.util;

import java.io.StringReader;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.servlet.ServletRequest;

public class LiberacaoUtil {
    
//    public static Map<String, String> getDecodificacaoLiberacao(String arquivo) {
//        Map<String, String> hm = new HashMap<String, String>();
//        try {
//            if (arquivo != null) {
//                ServletRequest s = null;
//                
//                Properties liberacao = new Properties();
//                String decode = StringUtil.liberacaoDecode(7, arquivo);
//                liberacao.load(new StringReader(decode));
//                Enumeration<Object> e = liberacao.keys();
//                while (e.hasMoreElements()) {
//                    String s = (String) e.nextElement();
//                    hm.put(s, liberacao.getProperty(s));
//                }
//                return hm;
//            }
//            return hm;
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//    
//    public static String liberacaoDecode(Integer chave, String texto) {
//        StringBuilder result = new StringBuilder();
//        for (int i = 0; i < texto.length(); i++) {
//            result.append((char) (((int) texto.charAt(i) - chave) % 255));
//        }
//        return result.toString();
//    }
    
}
