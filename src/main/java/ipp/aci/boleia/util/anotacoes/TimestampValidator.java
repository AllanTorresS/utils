package ipp.aci.boleia.util.anotacoes;


import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe de validacao para valores do tipo Timestamp
 */
public class TimestampValidator implements ConstraintValidator<Timestamp, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(TimestampValidator.class);

    @Override
    public void initialize(Timestamp constraintAnnotation) {
        // nada a fazer
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        return StringUtils.isEmpty(object) || validateDatePattern(object);
    }

    private boolean validateDatePattern(String text) {
        java.util.Date data = null;
        try {
            data = UtilitarioFormatacaoData.lerDataIso8601(text);
        } catch(ExcecaoBoleiaRuntime e) {
            LOGGER.trace(e.getMessage(), e);
        }
        return data != null;
    }
}