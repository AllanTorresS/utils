package ipp.aci.boleia.util.validador;

import ipp.aci.boleia.util.anotacoes.NotBlank;
import ipp.aci.boleia.util.anotacoes.Numeric;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Validador de entidades persistentes e dados de entrada de servicos
 */
@Component
public class ValidadorDTO {

    @Autowired
    private Validator validator;

    @Autowired
    private Mensagens mensagens;

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidadorDTO.class);
    private static final Map<String,String> MAPA_MENSAGENS = new HashMap<>();
    private static final Lock LOCK = new ReentrantLock();
    private static final int PRIORIDADE_OBRIGATORIEDADE = 0;
    private static final int PRIORIDADE_TIPO = 1;
    private static final int PRIORIDADE_TAMANHO = 2;

    /**
     * Valida um dto de api externa
     * 
     * @param objeto O objeto a validar
     * @throws ExcecaoValidacao Caso o objeto esteja inválido.
     */
    public void validarServicosExternos(Object objeto) throws ExcecaoValidacao {
        Set<ConstraintViolation<Object>> constraints = validator.validate(objeto);
        if (CollectionUtils.isNotEmpty(constraints)) {
            List<String> msgList = constraints.stream()
                .map(this::obterMensagemValidacao)
                .collect(Collectors.toList());
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_SERVICOS_EXTERNOS, msgList);
        }
    }

    /**
     * Obtem uma mensagem amigavel para o problema de validacao encontrado nos dados recebidos
     *
     * @param constraint As violacoes de validacao detectadas
     * @return Uma mensagem amigavel
     */
    private String obterMensagemValidacao(ConstraintViolation<Object> constraint) {
        String field = constraint.getPropertyPath().toString().replaceAll("(\\[[\\d+]\\])", "");
        Annotation annotation = constraint.getConstraintDescriptor().getAnnotation();
        if (annotation instanceof Min) {
            Min min = (Min) annotation;
            return mensagens.obterMensagem("api.externa.validacao.anotacao.min", field, min.value());
        } else if (annotation instanceof Max) {
            Max max = (Max) annotation;
            return mensagens.obterMensagem("api.externa.validacao.anotacao.max", field, max.value());
        } else if (annotation instanceof DecimalMin) {
            DecimalMin decimalMin = (DecimalMin) annotation;
            return mensagens.obterMensagem("api.externa.validacao.anotacao.decimalMin", field, decimalMin.value());
        } else if (annotation instanceof DecimalMax) {
            DecimalMax decimalMax = (DecimalMax) annotation;
            return mensagens.obterMensagem("api.externa.validacao.anotacao.decimalMax", field, decimalMax.value());
        } else if (annotation instanceof Size) {
            return tratarViolacaoTamanho(constraint, field, (Size) annotation);
        } else if (annotation instanceof NotNull || annotation instanceof NotBlank) {
            return mensagens.obterMensagem("api.externa.validacao.anotacao.notnull", field);
        } else if (annotation instanceof Numeric) {
            return mensagens.obterMensagem("api.externa.validacao.anotacao.numeric", field);
        } else if (annotation instanceof NotEmpty) {
            return mensagens.obterMensagem("api.externa.validacao.anotacao.notempty", field);
        }
        return mensagens.obterMensagem("api.externa.validacao.generica", field);
    }

    /**
     * Valida um DTO recebido por um servico atraves das propriedades anotadas
     *
     * @param dto O dto recebido
     * @throws ExcecaoValidacao Em caso de falha da validação
     */
    public void validarDtoServico(Object dto) throws ExcecaoValidacao {

        List<String> tiposViolados = new ArrayList<>();
        List<String> tamanhosViolados = new ArrayList<>();

        Set<ConstraintViolation<Object>> restricoesVioladas = validator.validate(dto);
        if (restricoesVioladas != null) {
            for (ConstraintViolation<Object> restricaoViolada : restricoesVioladas) {
                Annotation anotacao = restricaoViolada.getConstraintDescriptor().getAnnotation();
                String mensagem = obterMensagemErro(dto, restricaoViolada, anotacao);
                int prioridade = obterPrioridadeErro(anotacao);
                if (prioridade == PRIORIDADE_OBRIGATORIEDADE) {
                    throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_OBRIGATORIO, mensagem);
                }
                if (prioridade == PRIORIDADE_TIPO) {
                    tiposViolados.add(mensagem);
                } else {
                    tamanhosViolados.add(mensagem);
                }
            }
        }

        if (!tiposViolados.isEmpty()) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_SERVICO_TIPO, tiposViolados);
        }

        if (!tamanhosViolados.isEmpty()) {
            throw new ExcecaoValidacao(Erro.ERRO_VALIDACAO_SERVICO_TAMANHO, tamanhosViolados);
        }
    }

    /**
     * Obtem a mensagem de erro a ser lancada para a restricao violada
     *
     * @param alvoValidacao O objeto alvo da validacao
     * @param restricaoViolada  Uma restricao de validacao violada
     * @param anotacao A anotacao de validacao para a qual a violacao de restricao ocorreu
     * @return A mensagem de erro a ser lancada
     */
    private String obterMensagemErro(Object alvoValidacao, ConstraintViolation restricaoViolada, Annotation anotacao) {
        String nomeClasse = alvoValidacao.getClass().getSimpleName().toLowerCase();
        String nomeCampo = restricaoViolada.getPropertyPath().toString();
        String chaveMapMensagens = nomeClasse + "_$_" + nomeCampo + "_$_" + anotacao.getClass().getSimpleName();
        String mensagem = MAPA_MENSAGENS.get(chaveMapMensagens);
        if (mensagem == null) {
            LOCK.lock();
            try {
                mensagem = MAPA_MENSAGENS.get(chaveMapMensagens);
                if (mensagem == null) {
                    mensagem = obterMensagemAnotacao(anotacao);
                    int prioridade = obterPrioridadeErro(anotacao);
                    if (mensagem == null) {
                        if (prioridade == PRIORIDADE_OBRIGATORIEDADE) {
                            mensagem = nomeCampo.replace("endereco.", "");
                        } else {
                            mensagem = (nomeClasse.endsWith("frotadto") ? "frota." : nomeClasse.replace("dto", ".")) + nomeCampo;
                        }
                    }
                    MAPA_MENSAGENS.put(chaveMapMensagens, mensagem);
                }
            } finally {
                LOCK.unlock();
            }
        }
        return mensagem;
    }

    /**
     * Obtem a mensagem de erro informada na anotacao, caso exista.
     * @param anotacao A anotacao de validacao
     * @return A mensagem de erro, caso informada na anotacao
     */
    private String obterMensagemAnotacao(Annotation anotacao) {
        try {
            Method method = anotacao.getClass().getMethod("message");
            String mensagem = (String) method.invoke(anotacao);
            if(StringUtils.isNotBlank(mensagem) && !mensagem.contains("javax.validation") && !mensagem.contains("org.hibernate.validator")) {
                return mensagem;
            }
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            // nada a fazer, a mensagem sera obtida a partir do arquivo de internacionalizacao
            LOGGER.debug(e.getMessage(), e);
        }
        return null;
    }

    /**
     * Determina a prioridade de exibicao de uma mensagem de erro em funcao de sua natureza
     * @param annotation A anotacao que contem a restricao violada
     * @return A prioridade de exibicao do erro
     */
    private int obterPrioridadeErro(Annotation annotation) {
        if (annotation instanceof Size) {
            return PRIORIDADE_TAMANHO;
        }
        if (annotation instanceof NotNull || annotation instanceof NotBlank || annotation instanceof NotEmpty) {
            return PRIORIDADE_OBRIGATORIEDADE;
        }
        return PRIORIDADE_TIPO;
    }


    /**
     * Trata a violacao de uma anotacao <code>@Size</code>
     * @param constraint A violacao
     * @param field O campo
     * @param size A anotacao
     * @return A mensagem de erro a ser enviada
     */
    private String tratarViolacaoTamanho(ConstraintViolation<Object> constraint, String field, Size size) {
        if (constraint.getInvalidValue() instanceof CharSequence) {
            Integer length = ((CharSequence)constraint.getInvalidValue()).length();
            if (length > size.max()) {
                return mensagens.obterMensagem("api.externa.validacao.anotacao.size.max", field, size.max());
            } else {
                return mensagens.obterMensagem("api.externa.validacao.anotacao.size.min", field, size.min());
            }
        }
        return mensagens.obterMensagem("api.externa.validacao.anotacao.size", field, size.min(), size.max());
    }
}
