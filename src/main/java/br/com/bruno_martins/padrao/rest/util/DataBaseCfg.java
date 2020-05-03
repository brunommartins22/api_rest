
package br.com.bruno_martins.padrao.rest.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;


/**
 *
 * @author Bruno Martins
 */
public class DataBaseCfg  {

    private final String databaseCfgPath = "C:\\InterageSE\\DataBase.cfg";
    private Map<String, String> map;

    public DataBaseCfg() {
        try {
            Properties props = new Properties();
            props.load(new StringReader(readDatabaseCfg()));
            map = new HashMap();
            for (String stringPropertyName : props.stringPropertyNames()) {
                map.put(stringPropertyName, props.getProperty(stringPropertyName));
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler arquivo DataBase.cfg.", e);
        }
    }
    
    private String readDatabaseCfg() throws IOException{
        StringBuilder builder = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(databaseCfgPath)) {
            try (Scanner scanner = new Scanner(fis)) {
                while (scanner.hasNext()) {
                    builder.append(scanner.nextLine()).append("\n");
                }
            }
        }
          
        return builder.toString().replace("\\", "/");
    }

    
    public Map<String, String> getProperties() {
        return map;
    }

}

