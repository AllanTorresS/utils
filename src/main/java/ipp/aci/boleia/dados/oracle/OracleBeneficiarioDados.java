package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IBeneficiarioDados;
import ipp.aci.boleia.dominio.beneficios.Beneficiario;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.EntidadeVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaBeneficiarioVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;
import ipp.aci.boleia.util.UtilitarioLambda;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Repositório de entidades Beneficiario
 */
@Repository
public class OracleBeneficiarioDados extends OracleRepositorioBoleiaDados<Beneficiario> implements IBeneficiarioDados {

    /**
     * Instancia o repositorio
     */
    public OracleBeneficiarioDados() {
        super(Beneficiario.class);
    }

    @Override
    public Beneficiario obterBeneficiariosPorCpfFrota(Long cpf, Long idFrota) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaIgual("cpf", cpf));
        parametros.add(new ParametroPesquisaIgual("frota", idFrota));
        parametros.add(new ParametroPesquisaIgual("excluido", false));

        return pesquisarUnicoSemIsolamentoDados(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<Beneficiario> obterBeneficiariosPorNomeCpf(FiltroPesquisaParcialVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametrosParaAutocomplete(filtro, parametros);
        return UtilitarioLambda.filtrarDistintosPorPropriedade(pesquisar(new ParametroOrdenacaoColuna("nome"), parametros.toArray(new ParametroPesquisa[parametros.size()])), Beneficiario::getCpf);
    }

    @Override
    public ResultadoPaginado<Beneficiario> obterBeneficiarios(FiltroPesquisaBeneficiarioVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        if(filtro.getBeneficiario() != null && filtro.getBeneficiario().getId() != null) {
            parametros.add(new ParametroPesquisaIgual("id", filtro.getBeneficiario().getId()));
        }

        if(filtro.getBeneficios() != null && !filtro.getBeneficios().isEmpty()){
            parametros.add(new ParametroPesquisaIn("contaBeneficiario.contasBeneficio.beneficio.id",
                    filtro.getBeneficios().stream().map(EntidadeVo::getId).collect(Collectors.toList())));
        }
        parametros.add(new ParametroPesquisaIgual("status", StatusAtivacao.ATIVO.getValue()));
        parametros.add(new ParametroPesquisaIgual("excluido", false));

        if(filtro.getPaginacao() != null && filtro.getPaginacao().getParametrosOrdenacaoColuna().isEmpty()) {
            filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("nome"));
        }

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Povoa os parametros de pesquisa para a busca do componente de auto-complete
     *
     * @param filtro     O filtro de pesquisa
     * @param parametros Os parametros da consulta
     */
    private void povoarParametrosParaAutocomplete(FiltroPesquisaParcialVo filtro, List<ParametroPesquisa> parametros) {
        String termoCnpj = preparaTermoCnpj(filtro.getTermo());
        ParametroPesquisa paramCnpj = new ParametroPesquisaLike("cpf", termoCnpj);
        ParametroPesquisa paramRazao = new ParametroPesquisaLike("nome", filtro.getTermo());

        parametros.add(new ParametroPesquisaOr(paramRazao, paramCnpj));
        parametros.add(new ParametroPesquisaIgual("status", StatusAtivacao.ATIVO.getValue()));
        parametros.add(new ParametroPesquisaIgual("excluido", false));
    }
}
