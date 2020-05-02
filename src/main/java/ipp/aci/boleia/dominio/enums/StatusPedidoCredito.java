package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;
import ipp.aci.boleia.util.i18n.Mensagens;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Indica os estados possíveis de um pedido de crédito no sistema e na Mundipagg
 */
public enum StatusPedidoCredito implements IEnumComLabel<StatusPedidoCredito> {

    PENDENTE    ( 0, "pending",     "enum.StatusPedidoCredito.PENDENTE.labelMundipagg",     0),
    PROCESSANDO ( 2, "processing",  "enum.StatusPedidoCredito.PROCESSANDO.labelMundipagg",  1),
    PAGOAMENOS  ( 3, "underpaid",   "enum.StatusPedidoCredito.PAGOAMENOS.labelMundipagg",   2),
    PAGOAMAIS   ( 4, "overpaid",    "enum.StatusPedidoCredito.PAGOAMAIS.labelMundipagg",    3),
    PAGO        ( 1, "paid",        "enum.StatusPedidoCredito.PAGO.labelMundipagg",         4),
    FALHA       (-1, "failed",      "enum.StatusPedidoCredito.FALHA.labelMundipagg",        5),
    CANCELADO   (-2, "canceled",    "enum.StatusPedidoCredito.CANCELADO.labelMundipagg",    6),
    VENCIDO     (-3 ,"",            "enum.StatusPedidoCredito.VENCIDO.labelMundipagg",      7);

    public static final String DECODE_FORMULA_SOLUCAO = "DECODE(ID_STATUS, 0, 'GER', 2, 'GER' , 3, 'PEND', 4, 'PEND', 1,'PAG', -2,'CANC', -3,'VENC', -2, 'ANC', -1, 'FAL')";
    public static final String DECODE_FORMULA_PROCESSAMENTO = "DECODE(ID_STATUS, 0, 'PEND', 2, 'PROC', 4,'PAGMAIS', 3,'PAGMEN', -2,'CANC', 1,'PAG', -1, 'FAL', -3, 'VENC')";
    public static final String DECODE_FORMULA_VALOR_PAGO_ORDENACAO_STATUS_FROTISTA = "CASE WHEN ID_STATUS IN (-2, 3, 4) THEN NULL ELSE VA_VALOR_PAGO END";
    public static final String DECODE_FORMULA_VALOR_PAGO_ORDENACAO_STATUS_SOLUCAO = "CASE WHEN ID_STATUS IN (-2) THEN NULL ELSE VA_VALOR_PAGO END";
    private final Integer value;
    private final String nameMundipagg;
    private final String labelMundipagg;
    private final int ordem;

    /**
     * Construtor do status
     * @param value O valor do status na base de dados
     * @param nameMundipagg O nome correspondente nas respostas Mundipagg
     * @param labelMundipagg O label correspondente na Mundipagg
     * @param ordem A ordem do status, em relacao a cadeia de processamento
     */
    StatusPedidoCredito(Integer value, String nameMundipagg, String labelMundipagg, int ordem) {
        this.value = value;
        this.nameMundipagg = nameMundipagg;
        this.labelMundipagg = labelMundipagg;
        this.ordem = ordem;
    }

    public Integer getValue() {
        return value;
    }

    public String getNameMundipagg() {
        return nameMundipagg;
    }

    public String getLabelMundipagg() {
        return Mensagens.obterLabel(this.labelMundipagg);
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static StatusPedidoCredito obterPorValor(Integer value) {
        for(StatusPedidoCredito status : StatusPedidoCredito.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Obtem a enumeração pelo nome na mundipagg
     * @param nomeMundi O nome a procurar
     * @return A enumeração para o nome
     */
    public static StatusPedidoCredito obterPorNomeMundiPagg(String nomeMundi) {
        for(StatusPedidoCredito status : StatusPedidoCredito.values()) {
            if(status.nameMundipagg.equals(nomeMundi)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Lista os status ordenando-os de maneira funcional
     * @return os status possiveis
     */
    public static List<StatusPedidoCredito> listarOrdenados() {
        List<StatusPedidoCredito> listaStatus = Arrays.asList(values());
        listaStatus.sort(Comparator.comparingInt(status -> status.ordem));
        return listaStatus;
    }

    /**
     * Lista os status visiveis no escopo da solucao
     * @return os status possiveis para o escopo da solucao
     */
    public static List<StatusPedidoCredito> obterStatusPedidoCreditoSolucao() {
        return listarOrdenados()
                .stream()
                .filter(s -> !s.equals(PROCESSANDO) && !s.equals(PAGOAMAIS) &&!s.equals(FALHA))
                .collect(Collectors.toList());
    }

    /**
     * Lista os status visiveis no escopo do processador
     * @return os status possiveis para o escopo do processador
     */
    public static List<StatusPedidoCredito> obterStatusPedidoCreditoProcessador() {
        return listarOrdenados()
                .stream()
                .filter(s -> !s.equals(FALHA))
                .collect(Collectors.toList());
    }
}
