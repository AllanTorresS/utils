package ipp.aci.boleia.util.anotacoes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotação para instruir o {@link ipp.aci.boleia.util.seguranca.FiltroRequisicoes} a nao
 * sanitizar um determinado objeto ou propriedade.
 *
 * Alguns campos nao podem ser sanitizados pois possuem dados cifrados, como, por exemplo,
 * campos que contenha imagens codificadas em Base64.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface IgnoreSanitizing {

}
