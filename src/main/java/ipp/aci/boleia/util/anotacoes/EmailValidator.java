package ipp.aci.boleia.util.anotacoes;


import ipp.aci.boleia.util.validador.ValidadorEmail;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe de validacao para email
 */
public class EmailValidator implements ConstraintValidator<Email, String> {

    @Override
    public void initialize(Email constraintAnnotation) {
        // nada a fazer
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        return StringUtils.isEmpty(object) || ValidadorEmail.validar(object);
    }
}