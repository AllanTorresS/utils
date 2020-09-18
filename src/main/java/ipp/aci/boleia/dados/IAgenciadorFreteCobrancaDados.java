package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.AgenciadorFreteCobranca;
import ipp.aci.boleia.dominio.enums.agenciadorfrete.StatusDocumento;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.agenciadorfrete.FiltroRelatorioCobrancaVo;

import java.util.List;

public interface IAgenciadorFreteCobrancaDados extends IRepositorioBoleiaDados<AgenciadorFreteCobranca> {

    /**
     * Pesquisa todas as cobranças
     * @param filtro Filtro que representa um critério de busca
     * @return Resultado paginado de cobranças
     */
    ResultadoPaginado<AgenciadorFreteCobranca> pesquisar(FiltroRelatorioCobrancaVo filtro);

    /**
     * Obtém todos as cobrnaças de um agenciador de frete que ainda não integraram com o JDE
     * @return Lista de cobranças que não tem o documento Jde
     */
    List<AgenciadorFreteCobranca> obterCobrancasSemDocumentos();

    /**
     * Obtém todas as cobranças filtradas pelo status
     * @param status o status do documento da cobrança
     * @return A lista de cobranças
     */
    List<AgenciadorFreteCobranca> obterCobrancasPorStatus(StatusDocumento status);
}
