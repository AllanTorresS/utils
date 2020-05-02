package ipp.aci.boleia.util.anotacoes;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface de validacao para Binario
 */
@Constraint(validatedBy = {BinarioValidator.class})
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface Binario {

    /**
     * Mensagem de erro de validacao
     * @return a mensagem de erro
     */
    String message() default "";

    /**
     * Metodo obrigatorio
     * Representa um nome de grupo de validacao para validacao parcial
     * @return nome do grupo ao qual esta anotacao esta associada
     */
    Class<?>[] groups() default {};

    /**
     * Metodo obrigatorio
     * Permite associar um contexto de validacao
     * @return contexto de validacao
     */
    Class<? extends Payload>[] payload() default {};

}
