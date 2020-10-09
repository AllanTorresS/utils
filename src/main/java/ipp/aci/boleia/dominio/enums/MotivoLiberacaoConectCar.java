package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica os motivos de um estorno de transação
 */
public enum MotivoLiberacaoConectCar implements IEnumComLabel<MotivoLiberacaoConectCar> {

    ATIVADO (0),
	DEBITO_VENCIDO(1),
    INADIMPLECIA (2),
    SUSPEITA_FRAUDE (3),
    SUSPEITA_ATIVIDADE (4),
    CREDITO_EXCEDIDO (5),
    OUTROS(6);

	public static final String DECODE_FORMULA = "DECODE(ID_MOTIVO, 0, 'ATIVADO', 1, 'DÉBITO VENCIDO', 2, 'INADIMPLENCIA', 3, 'SUSPEITA FRAUDE', 4 , 'SUSPEITA ATIVIDADE', 5, 'CRÉDITO EXCEDIDO', 6, 'OUTROS')";
	
    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     * @param  semAlteracao verifica se existe alteracao
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
