package ipp.aci.boleia.util.anotacoes;


import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe de validacao para valores NotBlank
 */
public class NotBlankValidator implements ConstraintValidator<NotBlank, String> {

    @Override
    public void initialize(NotBlank constraintAnnotation) {
        // nada a fazer
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        return !StringUtils.isBlank(object);
    }
}