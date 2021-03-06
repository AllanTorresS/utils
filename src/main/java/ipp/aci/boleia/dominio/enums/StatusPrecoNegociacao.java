package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;
import ipp.aci.boleia.util.i18n.Mensagens;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Apenas para exibição na tela de negociações da Frota
 */
public enum StatusPrecoNegociacao implements IEnumComLabel<StatusPrecoNegociacao> {

    VIGENTE,
    NEGOCIACAO,
    HISTORICO,
    REJEITADO,
    AGENDADO,
    AGENDADO_PENDENTE,
    CANCELADO;

    public static final String DECODE_FORMULA = "DECODE(ID_STATUS, 1, 'VIG', 2, 'EM_NEG', 3, 'EM_NEG', 4, 'REJ', 5, 'VIG', 6, 'HIST', 7, 'CANC')";

    /**
     * Obtem o status de preco para frota de acordo com o status da entidade
     * @param status Status da entidade
     * @return Status da frota
     */
    public static StatusPrecoNegociacao obterPorStatusPreco(StatusPreco status) {
        if(StatusPreco.ACEITO.equals(status) || StatusPreco.VIGENTE.equals(status)) {
            return VIGENTE;
        }
        if(StatusPreco.PENDENTE.equals(status) || StatusPreco.NOVO.equals(status)) {
            return NEGOCIACAO;
        }
        if(StatusPreco.REJEITADO.equals(status)){
            return REJEITADO;
        }
        if(StatusPreco.HISTORICO.equals(status)){
            return HISTORICO;
        }
        if(StatusPreco.CANCELADO.equals(status)){
            return CANCELADO;
        }
        return null;
    }

    /**
     * Obtem os status de preço da entidade de acordo com o status de preco da frota
     * @return Status de preco da frota convertidos
     */
    public List<StatusPreco> converterParaStatusPreco() {
        if(VIGENTE.equals(this)) {
            return Arrays.asList(StatusPreco.ACEITO,StatusPreco.VIGENTE);
        }
        if(NEGOCIACAO.equals(this)) {
            return Arrays.asList(StatusPreco.PENDENTE,StatusPreco.NOVO);
        }
        if(HISTORICO.equals(this)) {
            return Collections.singletonList(StatusPreco.HISTORICO);
        }
        if(CANCELADO.equals(this)) {
            return Collections.singletonList(StatusPreco.CANCELADO);
        }
        if(REJEITADO.equals(this)) {
            return Collections.singletonList(StatusPreco.REJEITADO);
        }
        return Collections.emptyList();
    }

    /**
     * Obtem o label da negociacao a partir do status do preco
     * @param statusPreco O status do preco
     * @return O label de internacionalizaco do status da negociacao correspondente
     */
    public static String getLabelStatusPreco(StatusPreco statusPreco) {
        return Mensagens.obterLabel("enum.StatusPrecoNegociacao.StatusPreco." + statusPreco.name());
    }
}
