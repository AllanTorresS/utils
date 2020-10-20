package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.agenciadorfrete.PrecoFrete;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPrecoFreteVo;

/**
 * Contrato para implementacao de repositorios de entidades PrecoFrete
 */
public interface IPrecoFreteDados extends IRepositorioBoleiaDados<PrecoFrete> {

    /**
     * Obtem o Preco Frete vigente de acordo com o ponto de venda e o tipo de combustível
     *
     * @param pontoDeVenda Ponto de Venda com o preço  frete cadastrado
     * @param combustivel Tipo de combustível para o preço frete
     * @return o preço frete vigente encontrado para o combustível do ponto de venda
     */
    PrecoFrete obterPrecoFreteVigente(PontoDeVenda pontoDeVenda, TipoCombustivel combustivel);

    /**
     * Pesquisa Preços Frete a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<PrecoFrete> pesquisar(FiltroPesquisaPrecoFreteVo filtro);
}
