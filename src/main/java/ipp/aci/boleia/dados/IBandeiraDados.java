package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Bandeira;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Bandeira
 */
public interface IBandeiraDados extends IRepositorioBoleiaDados<Bandeira> {

    /**
     * Retorna as bandeiras que possuem os codigos corporativos informados para o credenciamento de postos
     * ordenadas de forma decrescente através da propridade.
     * @param codigosCorp Lista dos códigos corporativos utilizados na busca.
     *
     * @return Lista de {@link Bandeira}.
     */
    List<Bandeira> obterParaCredenciamentoPosto(List<Integer> codigosCorp);

    /**
     * Pesquisa uma bandeira pela descrição
     * @param filtro o filtro que contém o termo desejado
     * @return Lista de bandeiras encontradas
     */
    List<Bandeira> pesquisarPorDescricao(FiltroPesquisaParcialVo filtro);

    /**
     * Obtém uma bandeira a partir do seu código corporativo
     * @param codigoCorporativo código corporativo da bandeira
     * @return a bandeira encontrada
     */
    Bandeira obterPorCodigoCorporativo(Long codigoCorporativo);
}
