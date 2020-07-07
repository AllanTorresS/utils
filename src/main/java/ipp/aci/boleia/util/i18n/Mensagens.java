package ipp.aci.boleia.util.i18n;

import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Oferece acesso as mensagens internacionalizadas da aplicacao
 */
@Component
public class Mensagens {

    private static ResourceBundleMessageSource MESSAGE_SOURCE;

    @Autowired
    private ResourceBundleMessageSource resourceBundle;

    /**
     * Cria uma referencia estatica ao message source, para que a internacionalizacao das enums
     * possa ser invocada por objetos que nao possam ter o componente  injetado
     * (objetos que nao sao beans spring, como DTOs, por exemplo).
     */
    @PostConstruct
    public void init() {
        MESSAGE_SOURCE = resourceBundle;
    }

    /**
     * Obtem uma mensagem internacionalizada a partir de sua chave
     *
     * @param chave A chave da mensagem
     * @param args  Os argumentos para montagem da mensagem
     * @return A mensagem montada
     */
    public String obterMensagem(String chave, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return MESSAGE_SOURCE.getMessage(chave, args, locale);
    }

    /**
     * Obtem o label de uma enumeracao
     * @param e O valor da enumeracao
     * @param <E> O tipo da enumeracao
     * @return O label internacionalizado da enumeracao
     */
    public static <E extends Enum<E>> String obterLabelEnum(IEnumComLabel<E> e) {
        return obterLabelEnum(e.getClass().getSimpleName(), ((Enum) e).name());
    }
    /**
     * Obtem o label mapeado com o utilizado na base do corporativo para uma enumeracao
     * @param e O valor da enumeracao
     * @param <E> O tipo da enumeracao
     * @return O label internacionalizado da enumeracao
     */
    public static <E extends Enum<E>> String obterLabelValorIpiranga(IEnumComLabel<E> e) {
        return obterLabelEnum(e.getClass().getSimpleName(), ((Enum) e).name()+".valorIpiranga");
    }


    /**
     * Obtem o label de uma enumeracao
     *
     * @param nomeEntidade  O nome da Classe do Enum
     * @param nomeValorEnum A chave do valor do Enum
     * @return O label internacionalizado da enumeracao
     */
    public static String obterLabelEnum(String nomeEntidade, String nomeValorEnum) {
        Locale locale = LocaleContextHolder.getLocale();
        String chaveMsg = "enum." + nomeEntidade + "." + nomeValorEnum;
        return MESSAGE_SOURCE.getMessage(chaveMsg, null, locale);
    }

    /**
     * Obtem um label de enumeracao internacionalizado a partir de sua chave
     *
     * @param chave A chave do label
     * @return O label internacionalizado
     */
    public static String obterLabel(String chave) {
        Locale locale = LocaleContextHolder.getLocale();
        return MESSAGE_SOURCE.getMessage(chave, null, locale);
    }

    /**
     * Obtem uma lista de mensagens de exceção
     *
     * @param excecao A excecao capturada
     * @param chaveErro A chave de erro da exceção
     * @param args  Os argumentos para montagem da mensagem
     * @return A lista de mensagens
     */
    public List<String> obterMensagensExcecao(Throwable excecao, Erro chaveErro, Object[] args){

        List<String> detalhe = new ArrayList<>();

        if (excecao instanceof ExcecaoValidacao) {
            if(args != null && args.length > 0) {
                if(args[0] instanceof List && !((List) args[0]).isEmpty()) {
                    obterArgumentosMensagemExcecao(chaveErro, args[0], detalhe);
                } else if(args[0] instanceof String) {
                    detalhe.add(traduzirMensagem(chaveErro, (String) args[0]));
                }
            } else {
                detalhe.add(obterMensagem(chaveErro.getChaveMensagem()));
            }
        } else {
            detalhe.add(obterMensagem(chaveErro.getChaveMensagem(), args));
        }

        return detalhe;
    }

    /**
     * Extrai os argumentos da mensagem de excecao a ser exibida
     * @param chaveErro A chave do erro
     * @param arg A lista de argumentos
     * @param detalhe A lista na qual a mensagem gerada deve ser inserida
     */
    private void obterArgumentosMensagemExcecao(Erro chaveErro, Object arg, List<String> detalhe) {
        List listArgs = (List) arg;
        if(listArgs.get(0) instanceof String) {
            List<String> chavesMensagens = (List<String>) listArgs;
            for (String chaveMensagem : chavesMensagens) {
                detalhe.add(traduzirMensagem(chaveErro, chaveMensagem));
            }
        } else if(listArgs.get(0) instanceof Erro) {
            List<Erro> chavesErros = (List<Erro>) listArgs;
            for (Erro erro : chavesErros) {
                detalhe.add(obterMensagem(erro.getChaveMensagem()));
            }
        }
    }

    /**
     * Dado o erro e a chave da mensage, obtem o valor textual correspondente presente no arquivo de mensagens
     * @param chaveErro O erro
     * @param chaveMensagem A chave da mensagem
     * @return A mensagem textual
     */
    private String traduzirMensagem(Erro chaveErro, String chaveMensagem) {
        if(chaveMensagem.startsWith("{") && chaveMensagem.endsWith("}")) {
            return obterMensagem(chaveMensagem.substring(1, chaveMensagem.length() - 1));
        } else if(StringUtils.trim(chaveMensagem).contains(" ")) {
            return chaveMensagem;
        } else if (chaveErro.equals(Erro.ERRO_VALIDACAO_OBRIGATORIO) || chaveErro.equals(Erro.ERRO_VALIDACAO_SERVICO_TAMANHO) || chaveErro.equals(Erro.ERRO_VALIDACAO_TAMANHO) || chaveErro.equals(Erro.ERRO_VALIDACAO_SERVICO_TIPO) || chaveErro.equals(Erro.ERRO_VALIDACAO_TIPO)) {
            return obterMensagem(
                    chaveErro.getChaveMensagem(),
                    obterMensagem(chaveMensagem + ".label"),
                    obterMensagem(chaveMensagem + ".restricao." + (chaveErro.equals(Erro.ERRO_VALIDACAO_SERVICO_TAMANHO) ? "tamanho" : "tipo")));
        } else {
            return obterMensagem(chaveMensagem);
        }
    }
}
