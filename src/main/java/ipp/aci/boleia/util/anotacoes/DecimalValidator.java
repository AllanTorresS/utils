package ipp.aci.boleia.util.anotacoes;


import ipp.aci.boleia.util.validador.ValidadorAlfanumerico;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe de validacao para valores decimais
 */
public class DecimalValidator implements ConstraintValidator<Decimal, String> {

    @Override
    public void initialize(Decimal constraintAnnotation) {
        // nada a fazer
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        return StringUtils.isEmpty(object) || ValidadorAlfanumerico.isDecimal(object);
    }
}