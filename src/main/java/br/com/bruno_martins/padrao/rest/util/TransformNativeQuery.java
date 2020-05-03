/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.bruno_martins.padrao.rest.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.springframework.util.StringUtils;

/**
 *
 * @author Bruno Martins
 */
public class TransformNativeQuery {

    private final Class clazz;

    public TransformNativeQuery(Class clazz) {
        this.clazz = clazz;
    }

    public List execute(List<Object[]> list) throws InstantiationException, IllegalAccessException {

        List resultado = new ArrayList<>();
        for (Object[] o : list) {

            Object objectResult = this.clazz.newInstance();
            resultado.add(objectResult);

            int numField = 0;

            for (Object value : o) {

                Field field = objectResult.getClass().getDeclaredFields()[numField];

                try {

                    if (value != null) {

                        Method method = objectResult.getClass().getMethod("set" + StringUtils.capitalize(field.getName()), value.getClass());

                        method.invoke(objectResult, value);

                    }

                } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {

                    e.printStackTrace();

                }

                numField++;

            }

        }

        return resultado;

    }

}
