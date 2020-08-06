package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaSistemaExternoVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades SistemaExterno
 */
public interface ISistemaExternoDados extends IRepositorioBoleiaDados<SistemaExterno>{

    /**
     * Obtem o Sistema de Externo
     * @param client credencial de id do sistema de integração
     * @param secret secret id do sistema externo
     * @return Sistema de Externo
     */
    SistemaExterno obterPorClientESecret(String client, String secret);

    /**
     * Obtem o Sistema de Externo
     * @param nome nome do sistema Externo cadastrado
     * @return Sistema de Externo
     */
    SistemaExterno obterPorNome(String nome);

    /**
     * Pesquisa sistemas externos.
     * @param filtro filtro de pesquisa.
     * @return resultado da pesquisa com os sistemas externos encontrados.
     */
    ResultadoPaginado<SistemaExterno> pesquisar(FiltroPesquisaSistemaExternoVo filtro);

    /**
     * Pesquisa sistemas externos para autocomplete a partir de nome ou cnpj.
     * @param filtro filtro com o termo pesquisado.
     * @return resultado da pesquisa com os sistemas externos encontrados.
     */
    List<SistemaExterno> pesquisarPorCnpjNomeSistemaExterno(FiltroPesquisaParcialVo filtro);

    /**
     * Altera o estado da entidade para desanexado
     *
     * @param entidade entidade transiente
     * @return A entidade desanexada
     */
    SistemaExterno desanexar(SistemaExterno entidade);
}
