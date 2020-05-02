package ipp.aci.boleia.util.anotacoes;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Classe de validacao para valores binarios
 */
public class BinarioValidator implements ConstraintValidator<Binario, String> {

    @Override
    public void initialize(Binario constraintAnnotation) {
        // nada a fazer
    }

    @Override
    public boolean isValid(String object, ConstraintValidatorContext constraintContext) {
        return object == null || "0".equals(object) || "1".equals(object);
    }
}