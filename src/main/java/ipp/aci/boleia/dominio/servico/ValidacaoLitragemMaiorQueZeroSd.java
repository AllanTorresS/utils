package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Classe que valida se a litragem de uma Transacao é maior que zero
 */
@Component
public class ValidacaoLitragemMaiorQueZeroSd {

    @Autowired
    private Mensagens mensagens;

    /**
     * Valida se o motorista existe
     * @param transacao A trasação a ser validada
     * @throws ExcecaoValidacao Caso a validação falhe
     */
    public void validar(Transacao transacao) throws ExcecaoValidacao {
        if(transacao.getAbastecimento() != null && transacao.getAbastecimento().getLitragem() != null && transacao.getAbastecimento().getLitragem().compareTo(BigDecimal.ZERO) <= 0 ){
            throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.pedido.litragem.invalida"));
        }
    }
}
