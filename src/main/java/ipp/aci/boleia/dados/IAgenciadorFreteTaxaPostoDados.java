package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteTaxaPosto;

/**
 * Interface responsável pelo acesso dos dados de MDR do Ponto de Venda na base
 */
public interface IAgenciadorFreteTaxaPostoDados extends IRepositorioBoleiaDados<AgenciadorFreteTaxaPosto> {

    /**
     * Obtem o AgenciadorFreteTaxaPosto pelo ponto de venda
     * @param id O id do ponto de venda
     * @return Um AgenciadorFreteTaxaPosto, caso localizado. Nulo caso contrário.
     */
    AgenciadorFreteTaxaPosto obterPorPontoVenda(Long id);

}
