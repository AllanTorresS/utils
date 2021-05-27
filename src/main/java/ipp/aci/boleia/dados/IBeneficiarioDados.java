package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Beneficiario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaBeneficiarioVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;

import java.util.List;

/**
 * Contrato para implementação de repositírios de entidades Beneficiario
 */
public interface IBeneficiarioDados extends IRepositorioBoleiaDados<Beneficiario>{

	/**
	 * Pesquisa um beneficiário a partir do seu cpf e frota 
	 * 
	 * @param cpf O cpf do beneficiário
	 * @param idFrota O id da frota a qual o beneficiário é associado
	 * @return O beneficiario escontrado
	 */
	Beneficiario obterBeneficiariosPorCpfFrota(Long cpf, Long idFrota);


	/**
	 * Pesquisa beneficiários a partir do filtro informado
	 * 
	 * @param filtro O filtro da busca
	 * @return A lista de beneficiarios escontrados
	 */
	List<Beneficiario> obterBeneficiariosPorNomeCpf(FiltroPesquisaParcialVo filtro);

    
	/**
	 * Pesquisa beneficiários a partir do filtro informado
	 * 
	 * @param filtro O filtro da busca
	 * @return A lista de beneficiarios escontrados
	 */
    ResultadoPaginado<Beneficiario> obterBeneficiarios(FiltroPesquisaBeneficiarioVo filtro);
}
