package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDownloadNotaVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios
 * de entidades nota fiscal
 */
public interface INotaFiscalDados extends IRepositorioBoleiaDados<NotaFiscal> {

    /**
     * Realiza uma busca por uma lista de notas a partir de um filtro de pesquisa.
     *
     * @param filtro Filtro de pesquisa.
     * @return Lista de notas encontradas.
     */
    List<NotaFiscal> pesquisarParaDownload(FiltroPesquisaDownloadNotaVo filtro);

    /**
     * Obtem as notas fiscais dos abastecimentos informados
     * @param idsAutorizacoes Os abastecimentos
     * @return A lista de notas fiscais
     */
    List<NotaFiscal> obterNotaDeVariosAbastecimentos(List<Long> idsAutorizacoes);

    /**
     * Obtem as notas fiscais que possuam o numero informado
     * @param numero O numero da nota
     * @return Uma lista de notas que possuam aquele numero
     */
    List<NotaFiscal> obterNotaPorNumero(String numero);

    /**
     * Retorna uma lista de notas fiscais justificadas ou não a partir de uma lista de abastecimentos.
     *
     * @param idsAutorizacoes Os abastecimentos
     * @return A lista de notas fiscais
     */
    List<NotaFiscal> obterNotasEJustificativasPorAbastecimentos(List<Long> idsAutorizacoes);
}
