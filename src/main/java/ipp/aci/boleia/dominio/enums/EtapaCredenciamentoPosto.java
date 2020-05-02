package ipp.aci.boleia.dominio.enums;

/**
 * Indica a etapa atual do credenciamento de posto.
 */
public enum EtapaCredenciamentoPosto {

    PRE_CADASTRO(0),
	DEFINICAO_SENHA(1),
	TERMOS_CONTRATO(2),
	CONTRATO_SOCIAL(3),
	DADOS_BANCARIOS(4),
	PRECO_BASE(5);
	
    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    EtapaCredenciamentoPosto(Integer value) {
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
    public static EtapaCredenciamentoPosto obterPorValor(Integer value) {
        for(EtapaCredenciamentoPosto status : EtapaCredenciamentoPosto.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
