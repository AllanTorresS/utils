package ipp.aci.boleia.dados;

import java.util.List;

import ipp.aci.boleia.dominio.TagConectcar;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTagConectcarVo;

/**
 * Contrato para implementacao de repositorios de entidades TagConectcar
 */
public interface ITagConectcarDados extends IRepositorioBoleiaDados<TagConectcar> {

    ResultadoPaginado<TagConectcar> pesquisar(FiltroPesquisaTagConectcarVo filtro);
    
    /**
     * Pesquisa a quantidade total de tags de uma frota
     *
     * @param codigoFrota código identificador
     * @return quantidade de tags
     */
    long obterQuantidadeTotalTags(Long codigoFrota);
    
    
    /**
     * Pesquisa a quantidade total de tags de uma frota
     *
     * @param codigoFrota código identificador
     * @return quantidade de tags
     */
    long obterQuantidadeTotalTagsAtivas(Long codigoFrota);

    /**
     * Obtem a primeira tag ativa de uma frota, utilizado para calcular o ciclo de uma frota 
     * na criação de uma transacao
     * 
     * param idFrota código identificador da frota
     * @return tag encontrada
     */
    TagConectcar obtemPrimeiraTagAtivaPorFrota(Long idFrota);
    
    /**
     * Obtem as tags ativas de uma frota
     *
     * @param codigoFrota código identificador
     * @return tags ativas
     */
    List<TagConectcar> obterTagsAtivas(Long codigoFrota);

}