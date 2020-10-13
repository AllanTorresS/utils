package ipp.aci.boleia.dominio.servico.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe que valida se o código vip é permitido
 */
@Component
public class ValidacaoCodigoVipPermitidoSd {

    @Autowired
    private Mensagens mensagens;

    /**
     * Valida se o código vip é permitido
     * @param transacao A trasação a ser validada
     * @throws ExcecaoValidacao Caso a validação falhe
     */
    public void validar(Transacao transacao) throws ExcecaoValidacao {
        if(!transacao.getPosto().isBandeiraBranca() && (transacao.getCodigoVip() == null || !transacao.getCodigoVip().matches("\\d\\d+") || transacao.getPosto() == null)){
            throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.posto.vip.invalido"));
        }
    }
}
