package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.TipoCombustivel;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades das Tipo dos combustiveis.
 */
public interface ITipoCombustivelDados extends IRepositorioBoleiaDados<TipoCombustivel> {

    /**
     * Procura todos os combustiveis para os quais não existe preço cadastrado para o ponto de venda informado
     * @param idPontoVenda ID do ponto de venda procurado
     * @return Lista de Combustíveis ausentes
     */
    List<TipoCombustivel> buscarCombustiveisSemPrecoPV(Long idPontoVenda);

    /**
     * Retorna todos os combustíveis de um determinado tipo
     * @param tipoCombustivelMtec tipo combustivel mtec
     * @return Lista de Combustíveis
     */
    List<TipoCombustivel> buscarPorTipoCombustivelMtec(String tipoCombustivelMtec);


    /**
     * Realiza o de para buscando o tipo de combustivel de acordo com o código informado pela itegração Connect CTA
     * @param tipoCombustivelConnect Codigo do tipo de combustivel no Connect CTA
     * @return Tipo de combustivel
     */
    TipoCombustivel buscarPorTipoCombustivelConnect(Long tipoCombustivelConnect);
}
