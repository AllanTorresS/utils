package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.ReembolsoConectcar;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoConectcarVo;

/**
 * Contrato para implementacao de repositorios de entidades Reembolso da conectcar
 */
public interface IReembolsoConectcarDados extends IRepositorioBoleiaDados<ReembolsoConectcar> {

    /**
     * Pesquisa Reembolsos a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<ReembolsoConectcar> pesquisar(FiltroPesquisaReembolsoConectcarVo filtro);
   
}
