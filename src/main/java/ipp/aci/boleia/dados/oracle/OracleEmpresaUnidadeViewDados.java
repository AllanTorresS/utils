package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IEmpresaUnidadeViewDados;
import ipp.aci.boleia.dominio.EmpresaUnidadeView;
import ipp.aci.boleia.dominio.enums.Estado;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.TipoEntidadeUnidadeEmpresaAgregada;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.vo.EnumVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUnidadeEmpresaAgregadaVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do repositório de dados da view de Empresa/Unidade.
 */
@Repository
public class OracleEmpresaUnidadeViewDados extends OracleRepositorioBoleiaDados<EmpresaUnidadeView> implements IEmpresaUnidadeViewDados {

    /**
     * Construtor default da classe.
     */
    public OracleEmpresaUnidadeViewDados() {
        super(EmpresaUnidadeView.class);
    }

    @Override
    public ResultadoPaginado<EmpresaUnidadeView> pesquisar(FiltroPesquisaUnidadeEmpresaAgregadaVo filtro) {
        List<ParametroPesquisa> parametros = montarParametrosPesquisa(filtro);
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Monta a lista com os parametros de pesquisa que serão aplicados na consulta.
     *
     * @param filtro Filtro utilizado pelo usuário.
     * @return Lista com os parametros de pesquisa.
     */
    private List<ParametroPesquisa> montarParametrosPesquisa(FiltroPesquisaUnidadeEmpresaAgregadaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("empresaAgregada.id", filtro.getEmpresaAgregada() != null ? filtro.getEmpresaAgregada().getId() : null));
        parametros.add(new ParametroPesquisaIgual("unidade.id", filtro.getUnidade() != null ? filtro.getUnidade().getId() : null));

        parametros.add(new ParametroPesquisaIgual("frota.id", filtro.getFrota() != null ? filtro.getFrota().getId() : null));
        parametros.add(new ParametroPesquisaIgual("municipio", filtro.getCidade() != null ? "%" + filtro.getCidade() + "%" : null));
        montarParametroPesquisaUf(filtro.getUf(), parametros);
        montarParametroPesquisaTipoEntidade(filtro.getTipoEntidade(), parametros);
        montarParametroPesquisaStatus(filtro.getStatus(), parametros);
        return parametros;
    }

    /**
     * Inclui na lista de parametros o parametro de pesquisa por UF.
     *
     * @param filtroUf   Filtro de UF.
     * @param parametros Lista de parametros.
     */
    private void montarParametroPesquisaUf(EnumVo filtroUf, List<ParametroPesquisa> parametros) {
        String uf = null;
        if (filtroUf != null && filtroUf.getName() != null) {
            uf = Estado.valueOf(filtroUf.getName()).getSigla();
        }
        parametros.add(new ParametroPesquisaIgual("uf", uf));
    }

    /**
     * Inclui na lista de parametros o parametro de pesquisa por tipo de entidade.
     *
     * @param filtroTipoEntidade Filtro de tipo de entidade.
     * @param parametros         Lista de parametros.
     */
    private void montarParametroPesquisaTipoEntidade(EnumVo filtroTipoEntidade, List<ParametroPesquisa> parametros) {
        TipoEntidadeUnidadeEmpresaAgregada tipoEntidade = null;
        if (filtroTipoEntidade != null && filtroTipoEntidade.getName() != null) {
            tipoEntidade = TipoEntidadeUnidadeEmpresaAgregada.valueOf(filtroTipoEntidade.getName());
        }
        parametros.add(new ParametroPesquisaIgual("tipoEntidade", tipoEntidade != null ? tipoEntidade.getValue() : null));
    }

    /**
     * Inclui na lista de parametros o parametro de pesquisa por status.
     *
     * @param filtroStatus Filtro de status.
     * @param parametros   Lista de parametros.
     */
    private void montarParametroPesquisaStatus(EnumVo filtroStatus, List<ParametroPesquisa> parametros) {
        Integer statusAtivacao = null;
        if (filtroStatus != null && filtroStatus.getName() != null) {
            statusAtivacao = StatusAtivacao.valueOf(filtroStatus.getName()).getValue();
        }
        parametros.add(new ParametroPesquisaIgual("status", statusAtivacao));
    }
}
