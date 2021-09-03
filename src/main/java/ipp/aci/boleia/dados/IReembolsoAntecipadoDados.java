package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.ReembolsoAntecipado;
import ipp.aci.boleia.dominio.TransacaoConsolidada;

import java.math.BigDecimal;
import java.util.List;

/**
 * Contrato de implementação de repositório de Reembolso Antecipado
 */
public interface IReembolsoAntecipadoDados extends IRepositorioBoleiaDados<ReembolsoAntecipado> {

    /**
     * Obtém o total antecipado bruto para um conjunto de transações consolidadas
     * @param transacoesConsolidadas as transações consolidadas
     * @return o valor total antecipado para os consolidados informados
     */
    BigDecimal calcularTotalAntecipadoBruto(List<TransacaoConsolidada> transacoesConsolidadas);
}
