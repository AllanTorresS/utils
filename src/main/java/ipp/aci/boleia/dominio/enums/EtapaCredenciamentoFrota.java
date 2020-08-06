package ipp.aci.boleia.dominio.enums;

/**
 * Indica a etapa atual do credenciamento de frota.
 */
public enum EtapaCredenciamentoFrota {

    PRE_CADASTRO(0),
	DADOS_FROTA(1),
	DADOS_ENDERECO(2),
	DADOS_RESPONSAVEL(3),
	DADOS_ADICIONAIS(4),
	CONTRATO_SOCIAL(5);
	
    private final Integer value;
    
    /**
     * Construtor
     *
     * @param value O value do status
     */
    EtapaCredenciamentoFrota(Integer value) {
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
    public static EtapaCredenciamentoFrota obterPorValor(Integer value) {
        for(EtapaCredenciamentoFrota status : EtapaCredenciamentoFrota.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
