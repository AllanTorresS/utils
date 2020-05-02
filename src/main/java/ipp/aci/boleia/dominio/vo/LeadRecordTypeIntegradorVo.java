package ipp.aci.boleia.dominio.vo;

/**
 * Vo para envio do nome do tipo de registro para o lead do Salesforce.
 */
public class LeadRecordTypeIntegradorVo {
	
	private String name;

    /**
     * Construtor default da classe.
     */
	public LeadRecordTypeIntegradorVo() {
		// serializacao json
	}

    /**
     * Constrói o objeto a partir do tipo de registro.
     *
     * @param recordTypeName Tipo de registro.
	 */
	public LeadRecordTypeIntegradorVo(String recordTypeName) {
		this.name = recordTypeName;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}