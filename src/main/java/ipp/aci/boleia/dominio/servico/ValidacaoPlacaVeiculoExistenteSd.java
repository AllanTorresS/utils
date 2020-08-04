package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.validador.ValidadorPlacaVeiculo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Nó da cadeia que valida se a placa do veiculo de uma Transacao é válida e existe
 */
@Component
public class ValidacaoPlacaVeiculoExistenteSd {

    @Autowired
    private Mensagens mensagens;

    /**
     * Valida se a placa do veiculo de uma Transacao é válida e existe
     * @param transacao A trasação a ser validada
     * @throws ExcecaoValidacao Caso a validação falhe
     */
    public void validar(Transacao transacao) throws ExcecaoValidacao {
        if(transacao.getPlacaVeiculo() == null || !ValidadorPlacaVeiculo.validarPlacaVeiculo(transacao.getPlacaVeiculo().toUpperCase())){
            throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.motorista.placa.invalida"));
        }
    }
}
