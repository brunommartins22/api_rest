
package br.com.bruno_martins.padrao.rest.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 *
 * @author Bruno Martins
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface Conversor {
    
    Class<?> classeDeOrigem() default Object.class;
    
    String atributoDeOrigem() default "";
    
    
}
