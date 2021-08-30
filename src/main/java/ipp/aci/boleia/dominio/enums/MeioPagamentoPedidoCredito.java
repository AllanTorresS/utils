package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Indica o tipo de pagamento usado por uma frota pré-paga
 */
public enum MeioPagamentoPedidoCredito implements IEnumComLabel<MeioPagamentoPedidoCredito>, IEnumComValor {

    BOLETOBANCARIO(1, "boleto", 0),
    PIX(2, "pix", 1);

    private final Integer value;
    private final String  meioPagamentoMundipagg;
    private final Integer orden;

    /**
     * Construtor
     * @param value O value do status
     * @param meioPagamentoMundipagg
     * @param orden
     */
    MeioPagamentoPedidoCredito(Integer value, String meioPagamentoMundipagg, Integer orden) {
        this.value = value;
        this.meioPagamentoMundipagg = meioPagamentoMundipagg;
        this.orden = orden;
    }

    public Integer getValue() {
        return value;
    }


    /**
     * Lista os status ordenando-os de maneira funcional
     * @return os status possiveis
     */
    public static List<MeioPagamentoPedidoCredito> obterTiposPagamentosPedidoCreditoSolucao() {
        List<MeioPagamentoPedidoCredito> listaStatus = Arrays.asList(values());
        listaStatus.sort(Comparator.comparingInt(status -> status.orden));
        return listaStatus;
    }

    /**
     * Obtem a enumeração pelo nome retonado pela mundipagg
     * @param nomeMundi O nome a procurar
     * @return A enumeração para o nome
     */
    public static MeioPagamentoPedidoCredito obterPorNomeMundiPagg(String nomeMundi) {
        for(MeioPagamentoPedidoCredito status : MeioPagamentoPedidoCredito.values()) {
            if(status.meioPagamentoMundipagg.equals(nomeMundi)) {
                return status;
            }
        }
        return null;
    }


    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static MeioPagamentoPedidoCredito obterPorValor(Integer value) {
        for(MeioPagamentoPedidoCredito status : MeioPagamentoPedidoCredito.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Obtem por label
     * @param label label
     * @return Enum para o label
     */
    public static MeioPagamentoPedidoCredito obterPorLabel(String label) {
        for (MeioPagamentoPedidoCredito tipoPagamentoCreditoFrota : MeioPagamentoPedidoCredito.values()) {
            if(tipoPagamentoCreditoFrota.getLabel().equals(label)) {
                return tipoPagamentoCreditoFrota;
            }
        }
        return null;
    }
}
