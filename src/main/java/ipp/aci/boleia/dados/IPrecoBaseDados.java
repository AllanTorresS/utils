package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PrecoBase;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAlteracaoPrecoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaLocalizacaoVo;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Preco Base
 */
public interface IPrecoBaseDados extends IRepositorioBoleiaDados<PrecoBase> {

    /**
     * Pesquisa Preco paginado a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @param usuario o usuário especifico para a busca, em casos de usuários que não sejam internos
     * @param statusPossiveis lista de status a serem condiderados na busca
     * @return Uma lista de ResultadoPaginado localizadas
     */
    ResultadoPaginado<PrecoBase> pesquisaPaginada(FiltroPesquisaAlteracaoPrecoVo filtro, Usuario usuario, Integer... statusPossiveis);

    /**
     * Busca todos os precos atuais
     * @return Os precos atuais
     */
    List<PrecoBase> buscarPrecosAtuais();

    /**
     * Busca os precos base atuais de um PV.
     * @param idPV identificador do PV
     * @return Os precos atuais para o PV.
     */
    List<PrecoBase> buscarPrecosAtuaisPorPV(Long idPV);

    /**
     * Busca os precos pendentes de aceitacao mais antigos que a data informada
     *
     * @param dataAtualizacao A data de corte
     * @return A lista de precos
     */
    List<PrecoBase> buscarPrecosNovosParaVigenciaAutomatica(Date dataAtualizacao);

    /**
     * Busca os preços vigentes para o Ponto de venda e combustível informados
     * @param idPtov id da Ptov
     * @param idTipoCombustivel id do TipoCombustivel
     * @return Precos vigentes
     */
    List<PrecoBase> buscarPrecosVigentes(Long idPtov, Long idTipoCombustivel);

    /**
     * Busca o preço vigentes para o Ponto de venda e combutivel na data informada
     * @param idPtov id do ponto de venda
     * @param idTipoCombustivel id do TipoCombustivel
     * @param dataAbastecimento data do abastecimento
     * @return O preco na data informada
     */
    PrecoBase buscarPrecosPorData(Long idPtov, Long idTipoCombustivel, Date dataAbastecimento);

    /**
     * Busca os precos pendentes de pontos de venda para exibir lembrete
     * @param idsPvs ids dos PVs obter precos pendentes
     * @return Lista de precos pendentes
     */
    List<PrecoBase> buscarPendentes(List<Long> idsPvs);

    /**
     * Obtem os precos base atuais para o pv e combustivel dados
     * @param idPtov id do pv
     * @param idTipoCombustivel id do combustivel
     * @return Precos atuais
     */
    List<PrecoBase> buscarPrecosAtuais(Long idPtov, Long idTipoCombustivel);

    /**
     * Busca preço dos combustíveis por ponto de venda na regiao informada para uma dada frota
     * @param filtro Os dados de localizacao
     * @return Uma lista de precos
     */
    List<PrecoBase> listarPrecosPorFrotaLocalizacao(FiltroPesquisaLocalizacaoVo filtro);

    /**
     * Busca todos os preços agendados para entrar em vigência na data especificada.
     *
     * @param data a data base para pesquisa
     * @return Lista de preços agendados para entrear em vigência no dia
     */
    List<PrecoBase> buscarPrecosAgendadosPorData(Date data);

    /**
     * Busca todos os preços de um produto e ponto de venda.
     *
     * @param idPtov o id do ponto de venda
     * @param idCombustivel o id do produto
     * @return a lista de preços
     */
    List<PrecoBase> buscarPrecosPorPontoDeVendaCombustivelSemAgendamento(Long idPtov, Long idCombustivel);

    /**
     * Busca todos os preços bases de um combustível
     *
     * @param idCombustivel Id do produto
     * @param municipioPontoDeVenda Nome do município
     * @return Lista de preços
     */
    List<PrecoBase> buscarPrecosPorCombustivelERegiao(Long idCombustivel, String municipioPontoDeVenda);
}
