package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica a classificação de motivo de ativação ou inativação de uma frota
 */
public enum ClassificacaoStatusFrota implements IEnumComLabel<ClassificacaoStatusFrota> {

    OUTROS(-1, "", ""),
    SUSPEITA_DE_FRAUDE(0, "", ""),
    INADIMPLENCIA(1, "", ""),
    SUSPENSAO_DE_ATIVIDADE(2, "", ""),
    DEBITO_VENCIDO(3, "cobranca.servico.motivo.inativacao.debito.vencido", "frota.credito.pre.pago.ativar.motivo"),
    SALDO_ZERADO(4, "frota.credito.pre.pago.inativar.saldo.zerado.motivo", "frota.credito.pre.pago.ativar.motivo");

    private final Integer value;
    private final String chaveMensagemInativacao;
    private final String chaveMensagemReativacao;

    /**
     * Construtor
     *
     * @param value O value do status
     * @param chaveMensagemInativacao Chave da mensagem de inativação da frota
     * @param chaveMensagemReativacao Chave da mensagem de reativação da frota
     */
    ClassificacaoStatusFrota(Integer value,  String chaveMensagemInativacao, String chaveMensagemReativacao) {
        this.value = value;
        this.chaveMensagemInativacao = chaveMensagemInativacao;
        this.chaveMensagemReativacao = chaveMensagemReativacao;
    }

    public Integer getValue() {
        return value;
    }

    public String getChaveMensagemInativacao() {
        return chaveMensagemInativacao;
    }

    public String getChaveMensagemReativacao() {
        return chaveMensagemReativacao;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static ClassificacaoStatusFrota obterPorValor(Integer value) {
        for(ClassificacaoStatusFrota status : ClassificacaoStatusFrota.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
