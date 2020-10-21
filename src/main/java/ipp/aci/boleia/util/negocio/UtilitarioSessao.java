package ipp.aci.boleia.util.negocio;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;

/**
 * Prove recursos da sessão do sistema
 */
@Component
public class UtilitarioSessao {

    /**
     * Armazena um atributo na sessão a partir de uma chave.
     * @param chave Representa o nome do atributo a ser armazenado.
     * @param valor O valor a ser armazenado na sessão.
     */
    public static void atualizaAtributoDaSessao(String chave, Object valor) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        session.setAttribute(chave, valor);

    }

    /**
     * Obter um atributo na sessão a partir de uma chave.
     * @param chave Representa o nome do atributo a ser armazenado.
     * @return O valor armazenado na sessão.
     */
    public static Object obterAtributoDaSessao(String chave) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession();
        return session.getAttribute(chave);
    }
}
