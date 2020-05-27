package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaFrotaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialFrotaVo;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Frota
 */
public interface IFrotaDados extends IRepositorioBoleiaDados<Frota> {

    /**
     * Pesquisa frota por CNPJ
     * @param cnpj cnpj da frota
     * @return Frota com o mesmo cnpj
     */
    Frota pesquisarPorCnpj(Long cnpj);

    /**
     * Pesquisa uma lista de frotas na solução com um CNPJ
     * @param cnpj cnpj da frota
     * @return Frotas com o mesmo cnpj
     */
    List<Frota> pesquisarPorCnpjSemIsolamento(Long cnpj);

    /**
     * Pesquisa frota a partir do filtro informado
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Frota> pesquisar(FiltroPesquisaFrotaVo filtro);

    /**
     * Pesquisa por frotas utilizando os campos CNPJ e/ou Razao Social
     * @param filtro Filtro contendo os parametros da pesquisa
     * @return Lista com as entidades encontradas
     */
    List<Frota> pesquisarPorCnpjRazaoSocial(FiltroPesquisaParcialFrotaVo filtro);

    /**
     * Pesquisa todas as frotas relacionadas a um certo termo.
     * @param termoPesquisa trecho do nome da frota.
     * @return Lista de frota encontradas
     */
    List<Frota> pesquisarParaAutocomplete(String termoPesquisa);

    /**
     * Realiza uma pesquisa de frota por posto interno
     * @param idFrota o ID da frota
     * @return true se a frota tiver posto interno, false caso contrario
     */
    boolean pesquisarPorPostoInterno(Long idFrota);

    /**
     * Obtem a quantidade de frotas integradas (excluídas logicamente ou não)
     *
     * @param cnpj o Cnpj da frota a ser consultada
     * @return a quantidade de frotas persistidas
     */
    Long obterQuantidadeIntegradaPorCnpj(Long cnpj);

    /**
     * Obtem as frotas inativas em periodo de ativacao temporaria
     * @return Frotas inativas
     */
    List<Frota> obterFrotasInativasEmAtivacaoTemporaria();

    /**
     * Obtem as frotas ativas que passaram do priodo de ativacao temporario
     * @return Frotas ativas
     */
    List<Frota> obterFrotasAtivasAposAtivacaoTemporaria();

    /**
     * Obtém as frotas ativas que contem um determinado veículo
     * @param placa placa do veículo
     * @return Lista de frotas que contém o veículo
     */
    List<Frota> obterFrotaPorPlacaVeiculo(String placa);

    /**
     * Obtem uma lista com os ids de frotas que estão desativadas
     *
     * @return As frotas desativadas
     */
    List<Long> buscarFrotasInativadas();

    /**
     * Obtem uma lista de frotas que contem posto interno cadastrado
     * @param filtro O filtro de pesquisa
     * @return Uma lista de frota com posto interno
     */
    List<Frota> buscarFrotaComPostoInterno(FiltroPesquisaParcialFrotaVo filtro);

    /**
     * Obtém a listagem de frotas de acordo com a data da ultima atualizacao.
     * @param dataUltimaAtualizacao Data de busca para trazer ulimas alterações
     * @param parametros lista de parametros já preenchida caso se aplique
     * @return Listagem de frotas que possuiram alteração após a data informada.
     */
    List<Frota> buscarFrotaAtivaComPostoInternoPorDataAtualizacao(Date dataUltimaAtualizacao, List<ParametroPesquisa> parametros);

    /**
     * Lista todas as frotas que possuem cadastro no connect cta
     * @return Lista das frotas que possuem token do connect cta
     */
    List<Frota> listarFrotasPossuemTokenConnectCTA();

    /**
     * Retorna a lista das frotas ativas com postos internos
     * @param parametros Lista de parametros
     * @return Lista de frotas
     */
    List<Frota> buscarFrotasAtivasComPostoInterno(List<ParametroPesquisa> parametros);

    /**
     * Retorna as frotas ativas com posto interno com data de atualizacao e CNPJ
     * @param dataUltimaAtualizacao data ultima atualizacao
     * @param cnpj cnpj da frota
     * @return Lista de Frotas paginada
     */
    List<Frota> buscarFrotaUnidadeAtivaComPostoInternoPorDataAtualizacaoECnpj(Date dataUltimaAtualizacao, Long cnpj);

    /**
     * Retorna as frotas que os donos acúmularam KMV em um intervalo
     * @param dataInicio o inicio do intervalo
     * @param dataFim o final do intervalo
     * @return Lista de Frotas paginada
     */
    List<Frota> buscarFrotaComKmvAcumuladosParaDonoDaFrota(Date dataInicio, Date dataFim);

    /**
     * Obtem a frota associada à cobrança informada
     * @param idCobranca o id da cobrança
     * @return a frota associada à cobrança informada
     */
    Frota obterPorCobranca(Long idCobranca);
}
