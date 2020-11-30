package ipp.aci.boleia.dados;

import java.util.List;

import ipp.aci.boleia.dominio.SituacaoConectCar;

/**
 * Contrato para implementacao de repositorios de entidades HistoricoLiberacaoConectCar
 */
public interface ISituacaoConectCar extends IRepositorioBoleiaDados<SituacaoConectCar> {
	
	SituacaoConectCar buscarPorFrota(Long idFrota);
	
	List<Long> buscarFrotasInativadas();

}
