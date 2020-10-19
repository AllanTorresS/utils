package ipp.aci.boleia.dominio.servico.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Nó da cadeia que valida se o motorista existe
 */
@Component
public class ValidacaoMotoristaExisteSd {

    @Autowired
    private Mensagens mensagens;

    /**
     * Valida se o motorista existe
     * @param transacao A trasação a ser validada
     * @throws ExcecaoValidacao Caso a validação falhe
     */
    public void validar(Transacao transacao) throws ExcecaoValidacao {

        if (transacao.getMotorista() == null){
            throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.motorista.cpf.invalido"));
        }
    }
}
