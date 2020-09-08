package ipp.aci.boleia.dominio.servico.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteCobranca;
import ipp.aci.boleia.dominio.agenciadorfrete.Consolidado;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Implementa as regras de negócio relativas a Cobrança utilizado pelo Agenciador de Frete (AgenciadorFreteCobranca)
 */
@Component
public class AgenciadorFreteCobrancaSd {

    public BigDecimal obterDescontoSaque(List<Consolidado> consolidados) {
        return consolidados.stream().flatMap(c -> c.getTransacoes().stream())
                .map(t -> t.getSaque().getTaxa().multiply(t.getSaque().getValorSolicitado()))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public BigDecimal obterDescontoAbastecimento(List<Consolidado> consolidados) {
        return consolidados.stream().flatMap(c -> c.getTransacoes().stream()).map(t -> t.getAbastecimento().getLitragem()
                .multiply(t.getAbastecimento().getPrecoCombustivel())
                .multiply(t.getAbastecimento().getMdr())
                .multiply(t.getAbastecimento().getTaxaFee()))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public BigDecimal obterValorTotalSaque(Consolidado consolidado) {
        return consolidado.getTransacoes().stream()
                .map(t -> t.getSaque().getValorSolicitado())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    public BigDecimal obterValorTotalAbastecimento(Consolidado consolidado) {
        return consolidado.getTransacoes().stream().map(t -> t.getAbastecimento().getLitragem()
                .multiply(t.getAbastecimento().getPrecoCombustivel()))
                .reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
    }

    public Date obterDataFim(AgenciadorFreteCobranca cobranca) {
        return cobranca.getConsolidados().stream()
                .map(Consolidado::getDataFimPeriodo)
                .findFirst().orElseThrow(() -> new ExcecaoBoleiaRuntime(Erro.ERRO_INTEGRACAO));
    }
}
