package ipp.aci.boleia.dominio.servico.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.Abastecimento;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
/**
 * Implementa as regras de negócio relativas ao Abastecimento utilizado pelo Agenciador de Frete
 */
@Component
public class AbastecimentoSd {

    /**
     * Calcula valor do Fee relativo ao valor do MDR aplicado
     *
     * @return valor relativo do Fee em relação ao valor do MDR
     */
    public BigDecimal obterValorTotalFee(Abastecimento abastecimento) {
        return obterValorMdr(abastecimento).multiply(abastecimento.getTaxaFee()).divide(BigDecimal.valueOf(100L), 2, BigDecimal.ROUND_HALF_UP);
    }


    /**
     * Calcula valor do abastecimento conforme quantidade e preço unitario
     *
     * @return valor do abastecimento
     */
    public BigDecimal obterValorTotal(Abastecimento abastecimento) {
        return abastecimento.getLitragem().multiply(abastecimento.getPrecoCombustivel());
    }

    /**
     * Calcula valor do MDR aplicando seu valor no valor do Abastecimento
     *
     * @return valor relativo do MDR em relação ao valor do abastecimento
     */
    public BigDecimal obterValorMdr(Abastecimento abastecimento) {
        return obterValorTotal(abastecimento).multiply(abastecimento.getMdr()).divide(BigDecimal.valueOf(100L), BigDecimal.ROUND_HALF_UP);
    }
}
