package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.MotorGeracaoRelatorios;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMotorGeracaoRelatoriosVo;

/**
 * Contrato para implementacao de repositorios de entidades do Motor de Geração de Relatórios
 */
public interface IMotorGeracaoRelatoriosDados extends IRepositorioBoleiaDados<MotorGeracaoRelatorios> {

    /**
     * Pesquisa registros a partir do filtro informado
     *
     * @param filtro O filtro da busca
     * @return lista de registros
     */
    ResultadoPaginado<MotorGeracaoRelatorios> pesquisar(FiltroPesquisaMotorGeracaoRelatoriosVo filtro);

    /**
     * Obtém o relatório informado para processamento, caso ele já não esteja sendo processado
     * @param id O identificador do relatório
     * @return O relatório desejado
     */
    MotorGeracaoRelatorios obterRelatorioParaProcessamento(Long id);
}
