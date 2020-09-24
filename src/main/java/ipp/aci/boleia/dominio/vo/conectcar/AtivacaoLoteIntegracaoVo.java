package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import ipp.aci.boleia.dominio.TagConectcar;

public class AtivacaoLoteIntegracaoVo {

	@JsonProperty("CodigoInternoParceiro")
	private String codigoInternoParceiro;

	@JsonProperty("Adesivos")
	private List<AdesivosIntegracaoVo> adesivos;

	public AtivacaoLoteIntegracaoVo() {

	}
	
	public AtivacaoLoteIntegracaoVo(List<TagConectcar> listaConectCar, String codigoInternoParceiro) {
		this.codigoInternoParceiro = codigoInternoParceiro;
		
		this.adesivos = new ArrayList<AdesivosIntegracaoVo>();
		
		for (TagConectcar tag : listaConectCar) {
			adesivos.add(new AdesivosIntegracaoVo(false, tag.getId(), new VeiculoIntegracaoVo(tag.getPlaca())));
		}
		
	}
	

	public AtivacaoLoteIntegracaoVo(String codigoInternoParceiro, List<AdesivosIntegracaoVo> adesivos) {
		super();
		this.codigoInternoParceiro = codigoInternoParceiro;
		this.adesivos = adesivos;
	}

	public String getCodigoInternoParceiro() {
		return codigoInternoParceiro;
	}

	public void setCodigoInternoParceiro(String codigoInternoParceiro) {
		this.codigoInternoParceiro = codigoInternoParceiro;
	}

	public List<AdesivosIntegracaoVo> getAdesivos() {
		return adesivos;
	}

	public void setAdesivos(List<AdesivosIntegracaoVo> adesivos) {
		this.adesivos = adesivos;
	}

}
