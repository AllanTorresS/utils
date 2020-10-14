package ipp.aci.boleia.dominio.servico.agenciadorfrete;

import ipp.aci.boleia.dados.IAgenciadorFreteTransacaoDados;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Classe que valida se o a transação ainda não foi autorizada
 */
@Component
public class ValidacaoTransacaoAindaNaoAutorizadaSd {

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private IAgenciadorFreteTransacaoDados agenciadorFreteTransacaoDados;

    /**
     * Valida se o a transação ainda não foi autorizada
     * @param transacao A trasnação a ser validada
     * @throws ExcecaoValidacao Caso a validação falhe
     */
    public void validar(Transacao transacao) throws ExcecaoValidacao {
        Transacao transacaoBase = agenciadorFreteTransacaoDados.obterPorPedido(transacao.getPedido().getMotorista().getCpf(), transacao.getPedido().getNumero())
                                    .stream().filter(t -> t.getStatus() != null && t.getStatus().equals(StatusAutorizacao.AUTORIZADO.getValue()))
                                    .findFirst().orElse(null);

        if(transacaoBase != null && transacaoBase.getStatus().equals(StatusAutorizacao.AUTORIZADO.getValue())){
            throw new ExcecaoValidacao(mensagens.obterMensagem("agenciador.api.validacao.transacao.ja.autorizada"));
        }
    }
}
