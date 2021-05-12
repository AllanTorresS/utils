package ipp.aci.boleia.dados;

import java.util.List;

import ipp.aci.boleia.dominio.HistoricoTagConectcar;

/**
 * Contrato para implementacao de repositorios de entidades HistoricoTagConectcar
 */
public interface IHistoricoTagConectcarDados extends IRepositorioBoleiaDados<HistoricoTagConectcar> {

	List<HistoricoTagConectcar> pesquisar(String placa, Long frotaId);
    
}
