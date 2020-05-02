package ipp.aci.boleia.util.anotacoes;

import ipp.aci.boleia.util.validador.ValidadorAlfanumerico;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe de validacao para valores alfa-numericos
 */
public class AlphanumericValidator implements ConstraintValidator<Alphanumeric, String> {

    @Override
    public void initialize(Alphanumeric constraintAnnotation) {
        // nada a fazer
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        return StringUtils.isEmpty(object) || ValidadorAlfanumerico.isAlfanumerico(object);
    }
}
