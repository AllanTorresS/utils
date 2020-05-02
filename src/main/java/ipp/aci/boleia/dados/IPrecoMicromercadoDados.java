package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PrecoMicromercado;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPrecoMicromercadoVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades PrecoMicromercado
 */
public interface IPrecoMicromercadoDados extends IRepositorioBoleiaDados<PrecoMicromercado> {

    /**
     * Pesquisa os Precos de Micromercado a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de ResultadoPaginado localizadas
     */
    ResultadoPaginado<PrecoMicromercado> pesquisaPaginada(FiltroPesquisaPrecoMicromercadoVo filtro);

    /**
     * Obtem os Precos de Micromercado atuais
     *
     * @return A lista dos precos micromercado mais recentes
     */
    List<PrecoMicromercado> obterAtuais();

    /**
     * Obtem os Precos de Micromercado atuais, filtrando pelo identificador do micromercado
     *
     * @param idMicroMercado O identificador do micromercado
     * @return A lista de precos
     */
    List<PrecoMicromercado> obterAtuaisPorMicromercado(Long idMicroMercado);

    /**
     * Obtem o precoMicromercado atual para dado micromercado e combustivel
     *
     * @param idMicromercado id do micromercado
     * @param idCombustivel id do combustivel
     * @return O preco atual do combustivel naquele micromercado
     */
    PrecoMicromercado obterAtual(Long idMicromercado, Long idCombustivel);
}
