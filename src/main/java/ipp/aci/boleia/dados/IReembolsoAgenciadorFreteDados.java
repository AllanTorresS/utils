package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteCobranca;
import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteReembolso;
import ipp.aci.boleia.dominio.enums.agenciadorfrete.StatusDocumento;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaReembolsoAgenciadorFreteVo;

import java.util.List;

public interface IReembolsoAgenciadorFreteDados extends IRepositorioBoleiaDados<AgenciadorFreteReembolso> {
    /**
     * Pesquisa registros a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return lista de registros
     */
    ResultadoPaginado<AgenciadorFreteReembolso> pesquisar(FiltroPesquisaReembolsoAgenciadorFreteVo filtro);

    /**
     * Obtém todos os reembolsos que não foram integrados
     * @return Lista de reembolsos
     */
    List<AgenciadorFreteReembolso> obterReembolsosSemDocumentos();


    /**
     * Obtém todas os reembolsos filtrados pelo status
     * @param status o status do documento do reembolso
     * @return A lista de reembolso
     */
    List<AgenciadorFreteReembolso> obterReembolsosPorStatus(StatusDocumento status);
}
