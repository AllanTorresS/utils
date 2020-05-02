package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ContatoPontoDeVenda;

/**
 * Contrato para implementacao de repositorios de entidades Contato Ponto de Venda
 */
public interface IContatoPontoDeVendaDados extends IRepositorioBoleiaDados<ContatoPontoDeVenda> {

    /**
     * Obtem o contato adicional para dado ponto de venda
     * @param idPontoDeVenda id do ponto de venda
     * @return Contato adicional
     */
    ContatoPontoDeVenda obterAdicionalPorIdPontoDeVenda(Long idPontoDeVenda);
}
