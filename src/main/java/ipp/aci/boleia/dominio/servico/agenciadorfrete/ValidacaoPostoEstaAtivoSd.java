package ipp.aci.boleia.dominio.servico.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe que valida se o posto de uma transação está ativo
 */
@Component
public class ValidacaoPostoEstaAtivoSd {

    @Autowired
    private Mensagens mensagens;

    /**
     * Valida se o posto de uma transação está ativo
     * @param transacao A trasação a ser validada
     * @throws ExcecaoValidacao Caso a validação falhe
     */
    public void validar(Transacao transacao) throws ExcecaoValidacao {

        if(transacao.getPosto() == null){
            throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.posto.invalido"));
        }

        if(!StatusAtivacao.ATIVO.getValue().equals(transacao.getPosto().getStatus())){
            throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.posto.inativo", transacao.getPosto().getNome()));
        }
    }
}
