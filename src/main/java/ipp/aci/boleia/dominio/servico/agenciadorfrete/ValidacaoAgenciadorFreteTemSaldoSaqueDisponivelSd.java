package ipp.aci.boleia.dominio.servico.agenciadorfrete;

import ipp.aci.boleia.dados.IAgenciadorFreteExternoDados;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.excecao.ExcecaoServicoIndisponivel;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


/**
 * Classe que valida se o agenciador de frete permite o saque solicitado na transação
 */
@Component
public class ValidacaoAgenciadorFreteTemSaldoSaqueDisponivelSd {

    @Autowired
    private IAgenciadorFreteExternoDados agenciadorFreteExternoDados;

    @Autowired
    private Mensagens mensagens;

    /**
     * Valida se o agenciador de frete permite o saque solicitado na transação
     * @param transacao A trasação a ser validada
     * @throws ExcecaoServicoIndisponivel Caso a validação falhe
     */
    public void validar(Transacao transacao) throws ExcecaoValidacao, ExcecaoServicoIndisponivel {
        if(transacao.getSaque() != null && transacao.getSaque().getValorSolicitado() != null){
            BigDecimal saldoSaque = agenciadorFreteExternoDados.obterSaldoDeSaqueDisponivel(transacao);
            if(transacao.getSaque().getValorSolicitado().compareTo(saldoSaque) > 0){
                throw new ExcecaoValidacao(mensagens.obterMensagem("agentefrete.api.validacao.saldo.insuficiente.agenciador",
                        UtilitarioFormatacao.formatarCnpjApresentacao(transacao.getMotorista().getAgenciadorFrete().getCnpj()),
                        transacao.getMotorista().getAgenciadorFrete().getSistemaExterno().getNomeSistema()));
            }
        }
    }
}
