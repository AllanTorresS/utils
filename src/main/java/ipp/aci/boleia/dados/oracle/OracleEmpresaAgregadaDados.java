package ipp.aci.boleia.dados.oracle;


import ipp.aci.boleia.dados.IEmpresaAgregadaDados;
import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.enums.Estado;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEmpty;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaEmpresaAgregadaVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio de entidades Unidade
 */
@Repository
public class OracleEmpresaAgregadaDados extends OracleRepositorioBoleiaDados<EmpresaAgregada> implements IEmpresaAgregadaDados {

    /**
     * Instancia o repositorio
     */
    public OracleEmpresaAgregadaDados() {
        super(EmpresaAgregada.class);
    }

    @Override
    public ResultadoPaginado<EmpresaAgregada> pesquisar(FiltroPesquisaEmpresaAgregadaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getNomeRazao() != null) {
            parametros.add(new ParametroPesquisaOr(
                new ParametroPesquisaLike("razaoSocial", filtro.getNomeRazao()),
                new ParametroPesquisaLike("fantasia", filtro.getNomeRazao()))
            );
        }
        Long cnpj = UtilitarioFormatacao.obterLongMascara(filtro.getCnpj());
        povoarParametroIgual("cnpj", cnpj, parametros);
        povoarParametroIgual("municipio", filtro.getCidade(),parametros);
        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("status", StatusAtivacao.valueOf(filtro.getStatus().getName()).getValue()));
        }
        if (filtro.getUf() != null && filtro.getUf().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("uf", Estado.valueOf(filtro.getUf().getName()).getSigla()));
        }
        povoarParametroUnidade(filtro, parametros);
        if (filtro.getPaginacao() != null && CollectionUtils.isNotEmpty(filtro.getPaginacao().getParametrosOrdenacaoColuna())) {
            ParametroOrdenacaoColuna parametro = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0);
            if (parametro.getNome().contentEquals("telefone")) {
                filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("dddTelefone", parametro.getSentidoOrdenacao()));
            }
        }

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Método para povoar o parametro de unidade
     * @param filtro filtro aplicado
     * @param parametros lista de parametros a ser povoada
     */
    private void povoarParametroUnidade(FiltroPesquisaEmpresaAgregadaVo filtro, List<ParametroPesquisa> parametros) {
        if (filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            if (filtro.getUnidade().getId() > 0) {
                parametros.add(new ParametroPesquisaIgual("unidades.id", filtro.getUnidade().getId()));
            } else {
                parametros.add(new ParametroPesquisaNulo("unidades"));
            }
        }
    }

    @Override
    public EmpresaAgregada pesquisarPorCnpj(Long cnpj) {
        return pesquisarUnico(new ParametroPesquisaIgual("cnpj", cnpj));
    }

    @Override
    public List<EmpresaAgregada> pesquisarPorCnpjEFrota(Long cnpj, Long idFrota) {
        return pesquisar(new ParametroOrdenacaoColuna("id"),
                new ParametroPesquisaIgual("frota.id", idFrota),
                new ParametroPesquisaIgual("cnpj", cnpj));
    }

    @Override
    public List<EmpresaAgregada> pesquisarSemUnidade() {
        return pesquisar(new ParametroOrdenacaoColuna("cnpj"), new ParametroPesquisaEmpty("unidades"));
    }

    @Override
    public List<EmpresaAgregada> pesquisarPorFrotaSemUnidade(Long idFrota) {
        return pesquisar(new ParametroOrdenacaoColuna("razaoSocial"),
                new ParametroPesquisaIgual("frota", idFrota));
    }

    @Override
    public List<EmpresaAgregada> pesquisarPorFrotaComUnidade(Long idFrota, Long idUnidade){
        return pesquisar(new ParametroOrdenacaoColuna("razaoSocial"),
                new ParametroPesquisaIgual("frota", idFrota), new ParametroPesquisaIgual("unidades", idUnidade));
    }

    @Override
    public List<EmpresaAgregada> pesquisarPorRazaoSocial(String razaoSocial){
        return pesquisar(new ParametroOrdenacaoColuna("razaoSocial"),
                new ParametroPesquisaOr(
                        new ParametroPesquisaLike("razaoSocial", razaoSocial),
                        new ParametroPesquisaLike("fantasia", razaoSocial)
                ));
    }

    @Override
    public List<EmpresaAgregada> pesquisarPorCnpjRazaoSocialFrota(String termo, Long idFrota){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        String termoCnpj = (termo == null) ? null : termo.replaceAll("[-./]+", "")
                .replaceFirst("^0+(?!$)", "");
        parametros.add(new ParametroPesquisaOr(
                new ParametroPesquisaLike("cnpj", termoCnpj),
                new ParametroPesquisaLike("razaoSocial", termo)));
        if(idFrota != null){
            parametros.add(new ParametroPesquisaIgual("frota",idFrota));
        }
        return pesquisar(new ParametroOrdenacaoColuna("razaoSocial"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<EmpresaAgregada> obterPorUnidade(Long idUnidade) {
        return pesquisar(new ParametroOrdenacaoColuna("id"), new ParametroPesquisaIgual("unidades.id", idUnidade));
    }

    @Override
    public void desvincularUnidades(Long idUnidade) {
        String update = "DELETE FROM EmpresaAgregadaUnidade eau WHERE eau.idUnidade = :idUnidade";
        Query query = getGerenciadorDeEntidade().createQuery(update);
        query.setParameter("idUnidade", idUnidade);
        query.executeUpdate();
    }
}