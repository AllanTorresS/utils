package ipp.aci.boleia.dominio.servico.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteCobranca;
import ipp.aci.boleia.dominio.agenciadorfrete.Consolidado;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Implementa as regras de negócio relativas a Cobrança utilizado pelo Agenciador de Frete (AgenciadorFreteCobranca)
 */
@Component
public class AgenciadorFreteCobrancaSd {

    @Autowired
    private AbastecimentoSd abastecimentoSd;

    /***
     * Obtém o desconto do saque
     * @param consolidados a lista de consolidados
     * @return o desconto do saque
     */
    public BigDecimal obterDescontoSaque(List<Consolidado> consolidados) {
        return consolidados.stream().flatMap(c -> c.getTransacoes().stream()
                .filter(t -> t.getSaque() != null))
                .map(t -> t.getSaque().getTaxaAgenciadorFrete())
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /***
     * Obtém o desconto do abastecimento
     * @param consolidados a lista de consolidados
     * @return o deconto
     */
    public BigDecimal obterDescontoAbastecimento(List<Consolidado> consolidados) {
        return consolidados.stream().flatMap(c -> c.getTransacoes().stream())
                .map(t -> abastecimentoSd.obterValorTotalFee(t.getAbastecimento()))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /***
     * Obtém o valor total do saque
     * @param consolidado o consolidado
     * @return o valor total do saque
     */
    public BigDecimal obterValorTotalSaque(Consolidado consolidado) {
        return consolidado.getTransacoes().stream()
                .filter(t -> t.getSaque() != null)
                .map(t -> t.getSaque().getValorSolicitado())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    /***
     * Obtém o valor total do saque
     * @param consolidados a lista consolidado
     * @return o valor total do saque
     */
    public BigDecimal obterValorTotalSaque(List<Consolidado> consolidados) {
        return  consolidados.stream().flatMap(c -> c.getTransacoes().stream()
                .filter(t -> t.getSaque() != null))
                .map(t -> t.getSaque().getValorSolicitado())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    /***
     * Obtém o valor total do abastecimento
     * @param consolidado o consolidado
     * @return o valor total do abastecimento
     */
    public BigDecimal obterValorTotalAbastecimento(Consolidado consolidado) {
        return consolidado.getTransacoes().stream()
                .map(t -> abastecimentoSd.obterValorTotal(t.getAbastecimento()))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    /***
     * Obtém a data fim
     * @param cobranca a cobranca do agenciador de frete
     * @return a data fim
     */
    public Date obterDataFim(AgenciadorFreteCobranca cobranca) {
        return cobranca.getConsolidados().stream()
                .map(Consolidado::getDataFimPeriodo)
                .findFirst().orElseThrow(() -> new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO));
    }
}
