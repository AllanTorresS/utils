package ipp.aci.boleia.dominio.vo.externo;


import io.swagger.annotations.ApiModelProperty;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo;

import java.util.Date;

/**
 * Filtro de Motorista para APIs externas.
 *
 */
public class FiltroPesquisaMotoristaExtVo extends FiltroPesquisaMotoristaFrtVo {


	@ApiModelProperty(value="${docs.dominio.vo.externo.FiltroPesquisaMotoristaExtVo.dataAtualizacao}")
	private Date dataAtualizacao;

	@ApiModelProperty(value="${docs.dominio.vo.externo.FiltroPesquisaMotoristaExtVo.cnpjFrota}", allowableValues="range[0,99999999999999]")
	private Long cnpjFrota;

	public Date getDataAtualizacao() {
		return dataAtualizacao;
	}

	public void setDataAtualizacao(Date dataAtualizacao) {
		this.dataAtualizacao = dataAtualizacao;
	}

	public Long getCnpjFrota() {
		return cnpjFrota;
	}

	public void setCnpjFrota(Long cnpjFrota) {
		this.cnpjFrota = cnpjFrota;
	}
}