package ipp.aci.boleia.util.excecao;

import com.fasterxml.jackson.databind.JsonMappingException;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.hibernate.exception.JDBCConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;

/**
 * Trata excecoes lancadas pela aplicacao, escrevendo-as no log e enviando mensagens ao
 * client REST que solicitou a operacao
 */
@RestControllerAdvice
public class TratadorDeExcecoes extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TratadorDeExcecoes.class);

    @Autowired
    private Mensagens mensagens;

    /**
     * Trata excecoes lancadas pela aplicacao, escrevendo-as no log e enviando mensagens ao
     * client REST que solicitou a operacao
     *
     * @param ex  A excecao lancada pela aplicacao
     * @return Uma resposta JSON contendo uma mensagem sobre o erro ocorrido
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<Object> tratarExcecao(Throwable ex) {

        LOGGER.error(ex.getMessage(), ex);

        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        MensagemErro mensagemErro;

        if(ex instanceof AccessDeniedException) {
            mensagemErro = new MensagemErro(Erro.PERMISSAO_NEGADA.getCodigo(), mensagens.obterMensagensExcecao(ex, Erro.PERMISSAO_NEGADA, null));
            status = HttpStatus.FORBIDDEN;
        } else if(ex.getCause() instanceof JDBCConnectionException) {
            mensagemErro = new MensagemErro(Erro.SOLUCAO_INDISPONIVEL.getCodigo(), mensagens.obterMensagensExcecao(ex, Erro.SOLUCAO_INDISPONIVEL, null));
        } else if (ex instanceof ExcecaoRecursoNaoEncontrado) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            Erro chaveErro = Erro.ERRO_GENERICO;
            Object[] args = null;
            if (ex instanceof IExcecaoBoleia) {
                IExcecaoBoleia e = (IExcecaoBoleia) ex;
                chaveErro = e.getErro();
                args = e.getArgs();
                if(Erro.LIMITE_REQUISICOES_POR_SEGUNDO_EXCEDIDO.equals(e.getErro())) {
                    status = HttpStatus.TOO_MANY_REQUESTS;
                }
            }
            List<String> detalhe = mensagens.obterMensagensExcecao(ex, chaveErro, args);
            mensagemErro = new MensagemErro(chaveErro.getCodigo(), detalhe);
        }

        return new ResponseEntity<>(mensagemErro, status);
    }

    @Override
    @ResponseBody
    public ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        LOGGER.error("Erro no parse do JSON enviado pelo cliente.", ex);
        String mensagem = mensagens.obterMensagem(Erro.JSON_MAL_FORMADO.getChaveMensagem());
        Integer codigo = Erro.JSON_MAL_FORMADO.getCodigo();
        Throwable cause = ex.getCause();
        if (cause instanceof JsonMappingException && cause.getCause() instanceof ExcecaoValidacao) {
            ExcecaoValidacao excecaoValidacao = (ExcecaoValidacao) cause.getCause();
            Erro erro = excecaoValidacao.getErro();
            if (erro.equals(Erro.JSON_MAL_FORMADO_CAMPO)) {
                codigo = erro.getCodigo();
                mensagem = mensagens.obterMensagem(
                    erro.getChaveMensagem(),
                    ((JsonMappingException) cause).getPath().get(0).getFieldName(),
                    excecaoValidacao.getArgs()[0]);
            }

        }
        return new ResponseEntity<>(new MensagemErro(codigo, Collections.singletonList(mensagem)), HttpStatus.BAD_REQUEST);
    }
}