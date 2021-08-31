package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ISistemaExternoDados;
import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.enums.StatusTokenSistemaExterno;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaSistemaExternoVo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


/**
 * Respositório de entidades {@link SistemaExterno}
 */
@Repository
public class OracleSistemaExternoDados extends OracleRepositorioBoleiaDados<SistemaExterno> implements ISistemaExternoDados {

    /**
     * Instancia o repositório.
     */
    public OracleSistemaExternoDados() {
        super(SistemaExterno.class);
    }

    private static final String QUERY_SISTEMA_EXTERNO_PERMISSAO =
            " SELECT DISTINCT(se) " +
                    " FROM SistemaExterno se " +
                    " LEFT JOIN FETCH se.permissoes pe " +
                    " WHERE se.client = :client AND se.secret = :secret";
    @Override
    public SistemaExterno obterPorClientESecretComPermissao(String client, String secret) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("client", client));
        parametros.add(new ParametroPesquisaIgual("secret", secret));
        return pesquisarUnicoSemIsolamentoDados(QUERY_SISTEMA_EXTERNO_PERMISSAO, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }


    @Override
    public SistemaExterno obterPorClientESecret(String client, String secret) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("client", client));
        parametros.add(new ParametroPesquisaIgual("secret", secret));
        return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public SistemaExterno obterPorNome(String nome) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("nomeSistema", nome));
        return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<SistemaExterno> pesquisar(FiltroPesquisaSistemaExternoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametroIgual("id",  filtro.getNomeSistema()!= null ? filtro.getNomeSistema().getId(): null , parametros);
        povoarParametroIgual("status", (filtro.getStatus()!=null && filtro.getStatus().getName()!=null ? StatusTokenSistemaExterno.valueOf(filtro.getStatus().getName()).getValue(): null), parametros);

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<SistemaExterno> pesquisarPorCnpjNomeSistemaExterno(FiltroPesquisaParcialVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametrosParaAutocomplete(filtro, parametros);
        return pesquisar(new ParametroOrdenacaoColuna("nomeSistema"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Cria o parâmetro de pesquisa do autocomplete por nome ou cnpj.
     * @param filtro filtro filtro com o termo pesquisado.
     * @param parametros parâmetro de pesquisa no repositório.
     */
    private void povoarParametrosParaAutocomplete(FiltroPesquisaParcialVo filtro, List<ParametroPesquisa> parametros) {
        String termoCnpj = preparaTermoCnpj(filtro.getTermo());
        ParametroPesquisa paramCnpj = new ParametroPesquisaLike("cnpj", termoCnpj);
        ParametroPesquisa paramRazao = new ParametroPesquisaLike("nomeSistema", filtro.getTermo());

        parametros.add(new ParametroPesquisaOr(paramRazao, paramCnpj));
    }

    @Override
    public SistemaExterno desanexar(SistemaExterno entidade) {
        return super.desanexar(entidade);
    }
}
