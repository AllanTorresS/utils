package ipp.aci.boleia.util.validador;

import ipp.aci.boleia.util.anotacoes.NotBlank;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Validador de entidades persistentes e dados de entrada de servicos
 */
@Component
public class ValidadorEntidade {

    @Autowired
    private Validator validator;

    /**
     * Valida uma entidade persistente
     *
     * @param objeto O objeto a validar
     * @param acumularErros Caso true, evita o fast fail, executando todas as validacoes e armazendo os erros em uma lista para retorno.
     * @throws ExcecaoValidacao Em caso de violacao nas regras de preenchimento dos dados
     */
    public void validarDados(Object objeto, boolean acumularErros) throws ExcecaoValidacao {
        List<String> resultadoValidacao = new ArrayList<>();
        Set<ConstraintViolation<Object>> constraints = validator.validate(objeto);
        if (constraints != null) {
            for (ConstraintViolation<Object> constraint : constraints) {
                String field = constraint.getPropertyPath().toString().replaceAll("(\\[[\\d+]\\])", "");
                Annotation annotation = constraint.getConstraintDescriptor().getAnnotation();
                String messageField = objeto.getClass().getSimpleName().toLowerCase() + "." + field;
                verificarValidacaoDados(acumularErros, resultadoValidacao, annotation, messageField);
            }
        }
        if (!resultadoValidacao.isEmpty()) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_SERVICO_TAMANHO, resultadoValidacao);
        }
    }

    /**
     * Trata a violacao de dados capturada na validacao
     * @param acumularErros True caso os erros devam ser acumulados e lancados em lote
     * @param resultadoValidacao Os resultados da validacao
     * @param annotation A anotacao de validacao eventualmente violada
     * @param messageField O campo para o qual houve erro de validacao
     * @throws ExcecaoValidacao A excecao contendo a informacao sobre o erro nos dados recebidos
     */
    private void verificarValidacaoDados(boolean acumularErros, List<String> resultadoValidacao, Annotation annotation, String messageField) throws ExcecaoValidacao {
        if (isRestricaoTamanho(annotation)) {
            if (acumularErros) {
                resultadoValidacao.add(messageField);
            } else {
                throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_TAMANHO, messageField);
            }
        } else {
            if (annotation instanceof NotNull || annotation instanceof NotBlank) {
                throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_OBRIGATORIO, messageField);
            }
            if (annotation instanceof AssertTrue) {
                throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_VERDADEIRO, messageField);
            }
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_TIPO, messageField);
        }
    }

    /**
     * Verifica se a anotacao define uma restricao de tamanho
     * @param annotation A anotacao
     * @return True caso a anotacao defina uma restricao de tamanho
     */
    private boolean isRestricaoTamanho(Annotation annotation) {
        return annotation instanceof Size || annotation instanceof Digits ||
                annotation instanceof Min || annotation instanceof Max ||
                annotation instanceof DecimalMin || annotation instanceof DecimalMax;
    }

}
