package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.CobrancaConectcar;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCobrancaVo;

/**
 * Contrato para implementacao de repositorios de entidades Cobranca
 */
public interface ICobrancaConectcarDados extends IRepositorioBoleiaDados<CobrancaConectcar> {

    /**
     * Pesquisa Cobranças a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<CobrancaConectcar> pesquisar(FiltroPesquisaCobrancaVo filtro);

    /**
     * Obtem a última cobranca gerada para determinada frota
     * @param idFrota id da frota
     * @return Cobranca encontrada
     */
    CobrancaConectcar obterUltimaCobranca(Long idFrota);

}