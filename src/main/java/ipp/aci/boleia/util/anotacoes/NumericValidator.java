package ipp.aci.boleia.util.anotacoes;


import ipp.aci.boleia.util.validador.ValidadorAlfanumerico;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe de validacao para valores numericos
 */
public class NumericValidator implements ConstraintValidator<Numeric, String> {

    private long min, max;
    private boolean nullOrEmpty;

    @Override
    public void initialize(Numeric constraintAnnotation) {
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
        nullOrEmpty = constraintAnnotation.nullOrEmpty();
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        return (nullOrEmpty && StringUtils.isEmpty(object))
            || (object != null
                && ValidadorAlfanumerico.isNumerico(object)
                && (Long.parseLong(object) >= min && Long.parseLong(object) <= max) );
    }
}