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

    ResultadoPaginado<OperacaoContaBeneficiario> pesquisarExtratos(FiltroPesquisaExtratoBeneficiariosVo filtro);
}
