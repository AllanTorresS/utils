package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.beneficios.OperacaoContaBeneficiario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.beneficios.FiltroPesquisaExtratoBeneficiariosVo;

/**
 * Repositório de dados da entidade {@link OperacaoContaBeneficiario}
 *
 * @author pedro.silva
 */
public interface IOperacaoContaBeneficiarioDados extends IRepositorioBoleiaDados<OperacaoContaBeneficiario> {

    /**
     * Busca a lista de todas operações de distribuição (valor total positivo) realizadas para os beneficiários
     *
     * @param filtro Filtro com as informações que devem ser consideradas na busca
     * @return Lista de operações de distribuição que foram realizadas
     */
    ResultadoPaginado<OperacaoContaBeneficiario> pesquisarExtratoFrota(FiltroPesquisaExtratoBeneficiariosVo filtro);
}
