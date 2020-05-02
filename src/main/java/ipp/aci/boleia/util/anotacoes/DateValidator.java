package ipp.aci.boleia.util.anotacoes;


import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe de validacao para valores do tipo Date
 */
public class DateValidator implements ConstraintValidator<Date, String> {

    @Override
    public void initialize(Date constraintAnnotation) {
        // nada a fazer
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        return StringUtils.isEmpty(object) || validateDatePattern(object);
    }

    private boolean validateDatePattern(String text) {
        if (!text.matches("[0-9]{4}[-]{1}[0-9]{2}[-]{1}[0-9]{2}")) {
            return false;
        }
        java.util.Date data = UtilitarioFormatacaoData.lerDataJson(text);
        return data != null;
    }
}