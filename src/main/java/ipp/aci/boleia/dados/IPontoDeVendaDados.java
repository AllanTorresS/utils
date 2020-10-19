package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialPtovVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaLocalizacaoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPontoDeVendaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaRotaPontoVendaServicosVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Ponto de Venda
 */
public interface IPontoDeVendaDados extends IRepositorioBoleiaDados<PontoDeVenda> {

    /**
     * Pesquisa PontoDeVenda a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<PontoDeVenda> pesquisar(FiltroPesquisaPontoDeVendaVo filtro);

    /**
     * Pesquisa PontoDeVenda a partir do codigo corporativo informado
     *
     * @param codigoCorporativoPv codigo corporativo do PV
     * @return Ponto de venda encontrado
     */
    PontoDeVenda buscarPorCodigoCorporativo(Long codigoCorporativoPv);

    /**
     * Pesquisa PontoDeVenda a partir do codigo abadi informado
     *
     * @param codigoAbadi codigo abadi do PV
     * @return Ponto de venda encontrado
     */
    PontoDeVenda buscarPorNumeroAbadi(Long codigoAbadi);

    /**
     * Pesquisa PontoDeVenda a partir do CNPJ ou Nome
     *
     * @param filtro O filtro da pesquisa
     * @return Lista de Pontos de Venda encontrados
     */
    List<PontoDeVenda> pesquisarPorCnpjRazaoSocial(FiltroPesquisaParcialPtovVo filtro);


    /**
     * Pesquisa Ponto de Venda a partir de um termo
     *
     * @param termo de um ponto de venda
     * @return Lista dos pontos de venda que contém o termo.
     */
    List<PontoDeVenda> pesquisarParaAutocomplete(String termo);

    /**
     * Obtem dos pontos de venda associados ao usuario autenticado
     *
     * @param usuario O usuario logado
     * @param ordenarPorDataPendenciaAceite Se deve ordenar por pendencia aceite
     * @return lista de ponto de venda associados ao usuario autenticado
     */
    List<PontoDeVenda> obterTodosParaContexto(Usuario usuario, boolean ordenarPorDataPendenciaAceite);

    /**
     * Obtém todos os pontos de venda relacionado a uma determinada rede
     *
     * @param idRede id da rede
     * @return Lista de ponto de venda vinculados a uma rede
     */
    List<PontoDeVenda> obterPontosVendaPorRede(Long idRede);

    /**
     * Obtém todos os pontos de venda pendentes de atualização no JDE
     *
     * @return Lista de ponto de venda
     */
    List<PontoDeVenda> obterPontosVendaParaAtualizacaoJde();

    /**
     * Pesquisa pontos de venda para busca na tela de rotas
     *
     * @param filtro opcoes de filtro para rota
     * @return pontos de venda pelo filtro
     */
    List<PontoDeVenda> pesquisarPontosVendaComServicos(FiltroPesquisaRotaPontoVendaServicosVo filtro);

    /**
     * Obtém o ponto de venda relacionado a rede de usuário.
     *
     * @param idPv   id do ponto de venda
     * @param idRede id da rede
     * @return pontoe de venda relacionado ao usuário
     */
    PontoDeVenda obterPontoVendaAceitacao(Long idPv, Long idRede);

    /**
     * Pesquisa PontoDeVenda a partir do Municipio ou Nome
     *
     * @param filtro O filtro da pesquisa
     * @return Lista de Pontos de Venda encontrados
     */
    List<PontoDeVenda> pesquisarPorMunicipioNome(FiltroPesquisaParcialPtovVo filtro);
    
    /**
     * Pesquisa PontoDeVenda sem vínculo com a frota a partir do CNPJ ou Nome
     *
     * @param filtro O filtro da pesquisa
     * @return Lista de Pontos de Venda encontrados
     */
    List<PontoDeVenda> pesquisarSemVinculoComFrota(FiltroPesquisaParcialPtovVo filtro);

    /**
     * Obtem todos PVs habilitados da solução
     *
     * @return pvs habilitados
     */
    List<PontoDeVenda> obterTodosHabilitados();

    /**
     * Obtém o ponto de venda por cnpj
     *
     * @param cnpj do ponto de venda
     * @return ponto de venda
     */
    PontoDeVenda obterPorCnpjAreaAbastecimento(Long cnpj);

    /**
     * Obter lista de pontos de venda pelos limites localização
     * @param filtro filtro com os limites de latitude e longitude
     * @return lista de postos de venda nesse limite
     */
    List<PontoDeVenda> obterPontoDeVendaPorLimitesLocalizacao(FiltroPesquisaLocalizacaoVo filtro);

    /**
     * Obtém a quantidade de pontos de venda integrados no JDE por cnpj
     * @param cnpj O cnpj do ponto de venda
     * @return A quantidade de pontos de venda integrados
     */
    Long obterQuantidadeIntegradaPorCnpj(Long cnpj);

    /**
     * Retorna um ponto de venda a partir do número abadi.
     * @param numeroAbadi número abadi a ser pesquisado.
     * @return ponto de venda.
     */
    PontoDeVenda obterPorNumeroAbadi(Integer numeroAbadi);
    
    /**
     * Retorna um ponto de venda a partir do cnpj.
     * @param cnpj número do cnpj a ser pesquisado.
     * @return ponto de venda.
     */
    PontoDeVenda obterPorCnpj(Long cnpj);
}
