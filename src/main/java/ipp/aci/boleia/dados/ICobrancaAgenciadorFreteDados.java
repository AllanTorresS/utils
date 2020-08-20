package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteCobranca;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.FiltroRelatorioCobrancaVo;

public interface ICobrancaAgenciadorFreteDados extends IRepositorioBoleiaDados<AgenciadorFreteCobranca> {

    /**
     * Pesquisa todas as cobranças
     * @param filtro Filtro que representa um critério de busca
     * @return Resultado paginado de cobranças
     */
    ResultadoPaginado<AgenciadorFreteCobranca> pesquisar(FiltroRelatorioCobrancaVo filtro);
}
