package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica os motivos de um estorno de transação
 */
public enum MotivoLiberacaoConectCar implements IEnumComLabel<MotivoLiberacaoConectCar> {

    ATIVADO (0, true),
	DEBITO_VENCIDO(1, false),
    INADIMPLECIA (2,false),
    SUSPEITA_FRAUDE (3, false),
    SUSPEITA_ATIVIDADE (4, false),
    CREDITO_EXCEDIDO (5, false),
    OUTROS(6, false);

	public static final String DECODE_FORMULA = "DECODE(ID_MOTIVO, 0, 'ATIVADO', 1, 'DÉBITO VENCIDO', 2, 'INADIMPLENCIA', 3, 'USPEITA FRAUDE', 4 , 'SUSPEITA ATIVIDADE', 5, 'CREDITO EXCEDIDO', 6, 'OUTROS')";
	
    private final Integer value;
    private final Boolean semAlteracao;

    /**
     * Construtor
     *
     * @param value O value do status
     * @param  semAlteracao verifica se existe alteracao
     */
    MotivoLiberacaoConectCar(Integer value, Boolean semAlteracao) {
        this.value = value;
        this.semAlteracao = semAlteracao;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean getSemAlteracao() {
        return semAlteracao;
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
