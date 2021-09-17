package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import ipp.aci.boleia.util.ConstantesSalesForce;
import ipp.aci.boleia.util.anotacoes.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * DTO com informações de uma solicitacao de um lead ao sales force.
 *
 * @author wlima
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class SolicitacaoLeadExtVo {

	@JsonProperty("Id")
	private String id;
	
	@JsonProperty("AccountId")
	private String accountId;
	
	@NotNull
	@NotBlank
	@JsonProperty("QtdeVeiculosLeves__c")
    private String qdtVeiculosLeves;
	
	@JsonProperty("QtdeVeiculosPesados__c")
    private String qdtVeiculosPesados;
	
	@JsonProperty("QtdeVeiculosTAGEquip__c")
    private String qdtVeiculoTagEquip;
	
	@JsonProperty("GastoMensalPedagio__c")
    private String gastoMensal;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("StageName")
	private String stageName = ConstantesSalesForce.PROSPECT;
	
	@JsonProperty("CloseDate")
	private String closeDate;
	
	@JsonProperty("CNPJTexto__c")
	private String cnpj;
	
	@JsonProperty("RecordType")
	private SolicitacaoLeadExtRegistroVo recordType;
    
    /**
     * Construtor padrão da classe.
     */
    public SolicitacaoLeadExtVo() { }

    /**
     * Constrói o DTO com as informações passadas.
     *
     * @param qdtVeiculosLeves Número de veículos leves da frota
     * @param qdtVeiculosPesados Número de veículos pesados da frota
     * @param qdtVeiculoTagEquip Número de veículos, tag ou equip
     * @param gastoMensal Tipo de contato
     */
    public SolicitacaoLeadExtVo(String qdtVeiculosLeves, String qdtVeiculosPesados, String qdtVeiculoTagEquip, String gastoMensal) {
        this.qdtVeiculosLeves = qdtVeiculosLeves;
        this.qdtVeiculosPesados = qdtVeiculosPesados;
        this.qdtVeiculoTagEquip = qdtVeiculoTagEquip;
        this.gastoMensal = gastoMensal;
    }

    public String getId() {
		return id;
	}
    
    public void setId(String id) {
		this.id = id;
	}
    
    public String getAccountId() {
		return accountId;
	}
    
    public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
    
	public String getQdtVeiculosLeves() {
		return qdtVeiculosLeves;
	}

	public void setQdtVeiculosLeves(String qdtVeiculosLeves) {
		this.qdtVeiculosLeves = qdtVeiculosLeves;
	}

	public String getQdtVeiculosPesados() {
		return qdtVeiculosPesados;
	}

	public void setQdtVeiculosPesados(String qdtVeiculosPesados) {
		this.qdtVeiculosPesados = qdtVeiculosPesados;
	}

	public String getQdtVeiculoTagEquip() {
		return qdtVeiculoTagEquip;
	}

	public void setQdtVeiculoTagEquip(String qdtVeiculoTagEquip) {
		this.qdtVeiculoTagEquip = qdtVeiculoTagEquip;
	}

	public String getGastoMensal() {
		return gastoMensal;
	}

	public void setGastoMensal(String gastoMensal) {
		this.gastoMensal = gastoMensal;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStageName() {
		return stageName;
	}

	public void setStageName(String stageName) {
		this.stageName = stageName;
	}

	public String getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(String closeDate) {
		this.closeDate = closeDate;
	}

	public String getCnpj() {
		return cnpj;
	}
	
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public SolicitacaoLeadExtRegistroVo getRecordType() {
		return recordType;
	}

	public void setRecordType(SolicitacaoLeadExtRegistroVo recordType) {
		this.recordType = recordType;
	}
	
	
}
