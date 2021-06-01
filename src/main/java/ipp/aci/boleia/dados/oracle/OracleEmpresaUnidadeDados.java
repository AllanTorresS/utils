package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IEmpresaUnidadeDados;
import ipp.aci.boleia.dominio.EmpresaUnidade;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.vo.EmpresaUnidadeVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaEmpresaUnidadeFinanceiroVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.ATRASADO;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.A_DESCONTAR;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.NF_ATRASADA;
import static ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso.PAGO;
import static ipp.aci.boleia.dominio.enums.TipoEntidadeUnidadeEmpresaAgregada.EMPRESA_AGREGADA;
import static ipp.aci.boleia.dominio.enums.TipoEntidadeUnidadeEmpresaAgregada.UNIDADE;

/**
 * Implementação do repositório de dados da entidade Empresa/Unidade.
 */
@Repository
public class OracleEmpresaUnidadeDados extends OracleRepositorioBoleiaDados<EmpresaUnidade> implements IEmpresaUnidadeDados {

    private static final String CONSULTA_EMPRESA_UNIDADE_PARA_FINANCEIRO =
            "SELECT new ipp.aci.boleia.dominio.vo.EmpresaUnidadeVo(" +
                    "(CASE WHEN tc.unidade IS NOT NULL THEN euUnidade.id ELSE euAgregada.id END)," +
                    "(CASE WHEN tc.unidade IS NOT NULL THEN " + UNIDADE.getValue() + " ELSE " + EMPRESA_AGREGADA.getValue() + " END)," +
                    "f.id," +
                    "u.id," +
                    "ea.id" +
            ") " +
            "FROM TransacaoConsolidada tc " +
            "LEFT JOIN tc.empresaAgregada ea " +
            "LEFT JOIN ea.empresaUnidade euAgregada " +
            "LEFT JOIN tc.unidade u " +
            "LEFT JOIN u.empresaUnidade euUnidade " +
            "JOIN tc.prazos prz " +
            "LEFT JOIN tc.reembolso r " +
            "JOIN tc.frotaPtov fpv " +
            "JOIN fpv.frota f " +
            "WHERE f.id = :idFrota AND " +
            "      (tc.empresaAgregada IS NOT NULL OR tc.unidade IS NOT NULL) AND " +
            "      fpv.pontoVenda.id IN :idsPvs AND " +
            "      ((r.dataPagamento is null AND (r.dataVencimentoPgto >= :dataInicial AND r.dataVencimentoPgto <= :dataFinal)) OR (r.dataPagamento >= :dataInicial AND r.dataPagamento <= :dataFinal)) AND " +
            "      (r.status in (" + PAGO.getValue() + ", " + ATRASADO.getValue() + ", " + NF_ATRASADA.getValue() + ", " + A_DESCONTAR.getValue() + ")) " +
            "GROUP BY (CASE WHEN tc.unidade IS NOT NULL THEN euUnidade.id ELSE euAgregada.id END), " +
            "         (CASE WHEN tc.unidade IS NOT NULL THEN " + UNIDADE.getValue() + " ELSE " + EMPRESA_AGREGADA.getValue() + " END), " +
            "         f.id, " +
            "         u.id, " +
            "         ea.id";

    private static final String CONSULTA_EMPRESA_UNIDADE_PARA_DETALHAMENTO_CICLO =
            "SELECT new ipp.aci.boleia.dominio.vo.EmpresaUnidadeVo(" +
                    "(CASE WHEN tc.unidade IS NOT NULL THEN euUnidade.id ELSE euAgregada.id END)," +
                    "(CASE WHEN tc.unidade IS NOT NULL THEN " + UNIDADE.getValue() + " ELSE " + EMPRESA_AGREGADA.getValue() + " END)," +
                    "f.id," +
                    "u.id," +
                    "ea.id" +
                    ") " +
                    "FROM TransacaoConsolidada tc " +
                    "LEFT JOIN tc.empresaAgregada ea " +
                    "LEFT JOIN ea.empresaUnidade euAgregada " +
                    "LEFT JOIN tc.unidade u " +
                    "LEFT JOIN u.empresaUnidade euUnidade " +
                    "JOIN tc.prazos prz " +
                    "LEFT JOIN tc.reembolso r " +
                    "JOIN tc.frotaPtov fpv " +
                    "JOIN fpv.frota f " +
                    "WHERE f.id = :idFrota AND " +
                    "      (tc.empresaAgregada IS NOT NULL OR tc.unidade IS NOT NULL) AND " +
                    "      fpv.pontoVenda.id IN :idsPvs AND " +
                    "      TRUNC(tc.dataInicioPeriodo) = TRUNC(:dataInicial) AND " +
                    "      TRUNC(tc.dataFimPeriodo) = TRUNC(:dataFinal) AND " +
                    "      tc.statusConsolidacao = :statusConsolidacao AND " +
                    "      (tc.reembolso is NULL OR (r.status <> " + PAGO.getValue() + " AND r.status <> " + ATRASADO.getValue() + ")) " +
                    "GROUP BY (CASE WHEN tc.unidade IS NOT NULL THEN euUnidade.id ELSE euAgregada.id END), " +
                    "         (CASE WHEN tc.unidade IS NOT NULL THEN " + UNIDADE.getValue() + " ELSE " + EMPRESA_AGREGADA.getValue() + " END), " +
                    "         f.id, " +
                    "         u.id, " +
                    "         ea.id";

    /**
     * Construtor default da classe.
     */
    public OracleEmpresaUnidadeDados() {
        super(EmpresaUnidade.class);
    }

    @Override
    public List<EmpresaUnidadeVo> obterEmpresasUnidadesParaFinanceiro(FiltroPesquisaEmpresaUnidadeFinanceiroVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataInicial", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataFinal", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));
        parametros.add(new ParametroPesquisaIgual("idFrota", filtro.getIdFrota()));

        if(filtro.getIdPontoVenda() != null) {
            parametros.add(new ParametroPesquisaIn("idsPvs", Collections.singletonList(filtro.getIdPontoVenda())));
        } else if(usuarioLogado.isRevendedor()) {
            parametros.add(new ParametroPesquisaIn("idsPvs", usuarioLogado.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toList())));
        }

        return pesquisarComExcluidos(null, CONSULTA_EMPRESA_UNIDADE_PARA_FINANCEIRO, EmpresaUnidadeVo.class, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    @Override
    public List<EmpresaUnidadeVo> obterEmpresasUnidadesParaDetalhamentoCiclo(FiltroPesquisaEmpresaUnidadeFinanceiroVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataInicial", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataFinal", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));
        parametros.add(new ParametroPesquisaIgual("idFrota", filtro.getIdFrota()));

        if(filtro.getIdPontoVenda() != null) {
            parametros.add(new ParametroPesquisaIn("idsPvs", Collections.singletonList(filtro.getIdPontoVenda())));
        } else if(usuarioLogado.isRevendedor()) {
            parametros.add(new ParametroPesquisaIn("idsPvs", usuarioLogado.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toList())));
        }

        if(filtro.getStatusCiclo() != null && filtro.getStatusCiclo().getValue() != null){
            parametros.add(new ParametroPesquisaIgual("statusConsolidacao", filtro.getStatusCiclo().getValue()));
        }else{
            parametros.add(new ParametroPesquisaIgual("statusConsolidacao", null));
        }
        return pesquisarComExcluidos(null, CONSULTA_EMPRESA_UNIDADE_PARA_DETALHAMENTO_CICLO, EmpresaUnidadeVo.class, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    @Override
    public List<EmpresaUnidade> obterPorFrota(Long idFrota) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("frota.id", idFrota));
    }
}
