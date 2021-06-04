package ipp.aci.boleia.dominio.vo.conectcar;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import ipp.aci.boleia.dominio.TagConectcar;
import ipp.aci.boleia.dominio.enums.TipoUtilizacao;

/**
 * Vo de integração dos lotes de adesivos com a conectcar 
 *
 */
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
			String[] servicosBloqueio = null;
			boolean bloquear = false;
			if(TipoUtilizacao.ESTACIONAMENTO.getValue().equals(tag.getTipoUtilizacao())) {
				bloquear = true;
				servicosBloqueio = new String[1];
				servicosBloqueio[0] = TipoUtilizacao.PEDAGIO.name();
			} else if(TipoUtilizacao.PEDAGIO.getValue().equals(tag.getTipoUtilizacao())) {
				bloquear = true;
				servicosBloqueio = new String[1];
				servicosBloqueio[0] = TipoUtilizacao.ESTACIONAMENTO.name();
			}
			adesivos.add(new AdesivosIntegracaoVo(bloquear, servicosBloqueio, tag.getId(), new VeiculoIntegracaoVo(tag.getPlaca())));
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
