package ipp.aci.boleia.dominio.servico.agenciadorfrete;

import ipp.aci.boleia.dados.IAgenciadorFreteTransacaoDados;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.agenciadorfrete.MotoristaAutonomo;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Classe que valida se o valor de saque está dentro do limite permitido
 */
@Component
public class ValidacaoTransacaoNaoTemSimilaridade {

    @Autowired
    private IAgenciadorFreteTransacaoDados transacaoDados;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Valida se o valor de saque está dentro do limite permitido
     * @param transacao A trasnação a ser validada
     * @throws ExcecaoValidacao Caso a validação falhe
     */
    public void validar(Transacao transacao) throws ExcecaoValidacao {
        if(transacao != null) {
            PontoDeVenda pontoDeVenda = transacao.getPosto();
            MotoristaAutonomo motorista = transacao.getMotorista();
            String placaVeiculo = transacao.getPlacaVeiculo();
            BigDecimal litragem = transacao.getAbastecimento().getLitragem();
            BigDecimal precoCombustivel = transacao.getAbastecimento().getPrecoCombustivel();
            List<Transacao> transacoesSimilares = transacaoDados.obterTransacao(pontoDeVenda, motorista, placaVeiculo, StatusAutorizacao.AUTORIZADO, litragem, precoCombustivel);
            if(transacoesSimilares != null && !transacoesSimilares.isEmpty()
                    && transacoesSimilares.stream().anyMatch(t->ambiente.buscarDataAmbiente().compareTo(DateUtils.addMinutes(t.getDataCriacao(),10)) < 0)) {
                throw new ExcecaoValidacao(mensagens.obterMensagem("agenciador.frete.api.validacao.transacao.similaridade"));
            }
        }
    }
}
