package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.AbastecimentoView;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUltimosAbastecimentosVo;

/**
 * Contrato para implementação de repositório de EmpresaAbastecedoraView (AbastecimentosAgenciadorFrete/Frota)
 */
public interface IAbastecimentoViewDados extends IRepositorioBoleiaDados<AbastecimentoView> {

    ResultadoPaginado<AbastecimentoView> pesquisaPaginada(FiltroPesquisaUltimosAbastecimentosVo filtro);
}