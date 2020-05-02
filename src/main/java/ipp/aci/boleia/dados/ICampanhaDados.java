package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.campanha.Campanha;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCampanhaVo;

import java.util.Date;
import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Campanha
 */
public interface ICampanhaDados extends IRepositorioBoleiaDados<Campanha> {

    /**
     * Pesquisa descontos a partir do filtro informado
     * @param filtro O filtro da busca
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Campanha> pesquisar(FiltroPesquisaCampanhaVo filtro);

    /**
     * Pesquisa {@link Campanha} ativas e agendadas
     *
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Campanha> buscarAtivasEAgendadas();

    /**
     * Pesquisa {@link Campanha} ativas no momento do abastecimento
     *
     * @param dataAbastecimento A data de referência a ser utilizada na busca
     *
     * @return Uma lista de entidades localizadas
     */
    ResultadoPaginado<Campanha> buscarAtivas(Date dataAbastecimento);

    /**
     * Pesquisa as campanhas a serem ativadas ou encerradas pela data de vigência
     * @return Uma lista de entidades localizadas
     */
    List<Campanha> buscarCampanhasASeremAlteradas();
}
