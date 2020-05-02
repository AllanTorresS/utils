package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.MotorGeracaoRelatorios;
import ipp.aci.boleia.dominio.enums.TipoRelatorioMotorGerador;
import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;
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
     * Verifica se já existe uma geração de relatório em andamento com base no filtro
     * e tipo de relatório informados.
     *
     * @param filtro Filtro de consulta utilizado
     * @param tipoRelatorio Tipo de relatório a ser consultado
     * @return true caso o relatório esteja sendo produzido.
     */
    <F extends BaseFiltroPaginado> Boolean pesquisarGeracaoRelatorioEmAndamento(F filtro, TipoRelatorioMotorGerador tipoRelatorio);
}
