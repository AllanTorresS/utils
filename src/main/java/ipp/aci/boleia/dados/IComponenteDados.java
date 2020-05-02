package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.Componente;

/**
 * Contrato para implementacao de repositorios de entidades de Componentes de Ponto de Venda
 */
public interface IComponenteDados extends IRepositorioBoleiaDados<Componente> {

    /**
     * Pesquisa Componentes de um PontoDeVenda a partir do cnpj informado
     *
     * @param cnpj Cnpj do componente
     * @param idPv id do ponto de venda
     * @return O componente localizado
     */
    Componente buscarPorCnpjPv(Long cnpj, Long idPv);

    /**
     * Pesquisa um Componente a partir de um CNPJ informado
     * @param cnpj CNPJ do componente
     * @return O Componente encontrado
     */
    Componente buscarPorCnpj(Long cnpj);
}
