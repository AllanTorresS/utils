package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica os motivos de um estorno de transação
 */
public enum MotivoLiberacaoConectCar implements IEnumComLabel<MotivoLiberacaoConectCar> {

	DEBITO_VENCIDO(1),
    INADIMPLECIA (2),
    SUSPEITA_FRAUDE (3),
    SUSPEITA_ATIVIDADE (4),
    CREDITO_EXCEDIDO (5),
    OUTROS(6);

	public static final String DECODE_FORMULA = "DECODE(ID_MOTIVO, 1, 'Débito Vencido', 2, 'Inadimplência', 3, 'Suspeita de Fraude', 4 , 'Suspeita de Ativade', 5, 'Crédito Excedido', 6, 'Outros')";
	
    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status

     */
    MotivoLiberacaoConectCar(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static MotivoLiberacaoConectCar obterPorValor(Integer value) {
        for(MotivoLiberacaoConectCar status : MotivoLiberacaoConectCar.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
