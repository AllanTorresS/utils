package ipp.aci.boleia.dominio.vo;

/**
 * Vo para envio das informações de anexo do credenciamento para o Salesforce.
 */
public class AnexoCredenciamentoIntegradorVo {
	
	private String name;
	private String body;
	private String parentId;

	/**
	 * Construtor default da classe.
	 */
	public AnexoCredenciamentoIntegradorVo() {}

	/**
	 * Constrói o objeto a partir dos dados informados.
	 *
	 * @param nome Nome no contrato social.
	 * @param conteudo Conteúdo do contrato social.
	 * @param idLead Identificador de credenciamento.
	 */
	public AnexoCredenciamentoIntegradorVo(String nome, String conteudo, String idLead) {
		this.name = nome;
		this.body = conteudo;
		this.parentId = idLead;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	
}