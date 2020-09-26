package ipp.aci.boleia.dados;


import ipp.aci.boleia.dominio.Documento;
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
   
}
