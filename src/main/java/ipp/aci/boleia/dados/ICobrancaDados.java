package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCobrancaVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaCobrancaFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Cobranca
 */
public interface ICobrancaDados extends IRepositorioBoleiaDados<Cobranca> {

    /**
     * Pesquisa Cobranças a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Cobranca> pesquisar(FiltroPesquisaCobrancaVo filtro);

    /**
     * Obtem as cobrancas em aberto para uma Frota
     * @param idFrota id da frota
     * @return Cobrancas em aberto para frota
     */
    List<Cobranca> obterCobrancasEmAberto(Long idFrota);

    /**
     * Verifica se a frota a ser excluída possui débitos em aberto.
     *
     * @param idFrota identificador unico da Frota
     * @return Verdadeiro se existir cobrancas em aberto, Falso caso contrario
     */
    boolean verificarCobrancasEmAberto(Long idFrota);

    /**
     * Pesquisa os as cobranças candidatas a realizar consulta no JDE
     * para consultar o status do aviso de débito
     *
     * @return As cobranças candidatas a consulta de aviso de débito.
     */
    List<Cobranca> buscarCobrancasParaConsultarAvisoDebito();

    /**
     * Obtem a última cobranca gerada para determinada frota
     * @param idFrota id da frota
     * @return Cobranca encontrada
     */
    Cobranca obterUltimaCobranca(Long idFrota);

    /**
     * Obtem a cobranca de um abastecimento que foi consolidado
     * @param idAutorizacaoPagamento id da autorização pagamento
     * @return Cobranca encontrada
     */
    Cobranca obterPorAutorizacaoPagamentoId(Long idAutorizacaoPagamento);

    /**
     * Verifica se a frota a possui debitos vencidos
     *
     * @param idFrota identificador unico da Frota
     * @return Verdadeiro se existir cobrancas em vencidas, Falso caso contrario
     */
    boolean verificarCobrancasVencidas(Long idFrota);

    /**
     * Pesquisa Cobranças a partir do filtro informado no contexto da API de frotista.
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginadoFrtVo<Cobranca> pesquisar(FiltroPesquisaCobrancaFrtVo filtro);

    /**
     * Pesquisa Cobranças para o relatorio de conciliação a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    List<Cobranca> pesquisarParaConciliacao(FiltroPesquisaCobrancaVo filtro);

    /**
     * Busca a cobrança do ciclo anterior à uma cobrança específica.
     *
     * @param cobranca Cobrança que será utilizada na pesquisa
     * @return Cobrança do ciclo anterior
     */
    Cobranca obterCobrancaAnterior(Cobranca cobranca);

    /**
     * Retorna as cobranças com erro de integração.
     *
     * @param numeroTentativas Número máximo de tentativas considerado na consulta
     * @return lista com as cobranças
     */
    List<Cobranca> obterCobrancasErroEnvio(Integer numeroTentativas);
}
