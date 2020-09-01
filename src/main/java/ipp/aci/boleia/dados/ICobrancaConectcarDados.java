package ipp.aci.boleia.dados;

import java.util.List;

import ipp.aci.boleia.dominio.CobrancaConectcar;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCobrancaConectcarVo;

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
    ResultadoPaginado<CobrancaConectcar> pesquisar(FiltroPesquisaCobrancaConectcarVo filtro);

    /**
     * Obtem a última cobranca gerada para determinada frota
     * @param idFrota id da frota
     * @return Cobranca encontrada
     */
    CobrancaConectcar obterUltimaCobranca(Long idFrota);

    /**
     * Busca a cobrança do ciclo anterior à uma cobrança específica.
     *
     * @param cobranca Cobrança que será utilizada na pesquisa
     * @return Cobrança do ciclo anterior
     */
    CobrancaConectcar obterCobrancaAnterior(CobrancaConectcar cobranca);

    /**
     * Retorna as cobranças com erro de integração.
     *
     * @param numeroTentativas Número máximo de tentativas considerado na consulta
     * @return lista com as cobranças
     */
    List<CobrancaConectcar> obterCobrancasErroEnvio(Integer numeroTentativas);

    /**
     * Altera o estado da entidade para desanexado
     * @param cobranca a cobrança a ser desanexada
     * @return a cobrança desanexada
     */
    CobrancaConectcar desanexar(CobrancaConectcar cobranca);

}