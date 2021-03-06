package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFluxoAbastecimentoMotoristaDados;
import ipp.aci.boleia.dominio.FluxoAbastecimentoMotoristaConfig;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMotoristaVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades FluxoAbastecimentoMotoristaConfig
 */
@Repository
public class OracleFluxoAbastecimentoMotoristaDados extends OracleRepositorioBoleiaDados<FluxoAbastecimentoMotoristaConfig> implements IFluxoAbastecimentoMotoristaDados {

    private  static final String LISTA_FLUXOS_POR_ID_MOTORISTA_INCLUINDO_EXCLUIDOS =
            "SELECT fm FROM FluxoAbastecimentoMotoristaConfig fm " +
                    "WHERE fm.motorista.id = :idMotorista";

    private static final String EXCLUSAO_POR_ID_MOTORISTA =
            "DELETE FROM FluxoAbastecimentoMotoristaConfig fm " +
            "WHERE fm.motorista.id = :idMotorista";

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Instancia o repositorio
     */
    public OracleFluxoAbastecimentoMotoristaDados() {
        super(FluxoAbastecimentoMotoristaConfig.class);
    }

    @Override
    public FluxoAbastecimentoMotoristaConfig obterFluxoPorMotorista(Motorista motorista) {
        List<FluxoAbastecimentoMotoristaConfig> result = pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("motorista.id", motorista.getId()));
        return result.isEmpty() ? null : result.get(0);
    }

    @Override
    public List<FluxoAbastecimentoMotoristaConfig> obterFluxosPorMotorista(Motorista motorista) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("motorista.id", motorista.getId()));
    }

    @Override
    public List<FluxoAbastecimentoMotoristaConfig> obterFluxosPorMotoristaIncluindoExcluidos(Motorista motorista) {
        Query query = getGerenciadorDeEntidade().createQuery(LISTA_FLUXOS_POR_ID_MOTORISTA_INCLUINDO_EXCLUIDOS);
        query.setParameter("idMotorista", motorista.getId());
        return query.getResultList();
    }

    @Override
    public List<FluxoAbastecimentoMotoristaConfig> obterFluxosPorUsuario(Usuario usuario) {
        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIgual("motorista.usuarioMotorista.usuario.id", usuario.getId()),
                new ParametroPesquisaFetch("motorista"),
                new ParametroPesquisaFetch("veiculo"));
    }

    @Override
    public List<FluxoAbastecimentoMotoristaConfig> obterFluxosMotoristaPorVeiculo(Long idVeiculo) {
        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIgual("veiculo.id", idVeiculo));
    }

    @Override
    public List<FluxoAbastecimentoMotoristaConfig> obterFluxosPorFrota(Long idFrota) {
        return pesquisar(new ParametroOrdenacaoColuna("motorista.nome"),
                new ParametroPesquisaIgual("motorista.frota.id", idFrota),
                new ParametroPesquisaFetch("motorista"),
                new ParametroPesquisaFetch("veiculo"));
    }

    @Override
    public ResultadoPaginado<FluxoAbastecimentoMotoristaConfig> pesquisarConfigMotorista(FiltroPesquisaMotoristaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getNome() != null) {
            parametros.add(new ParametroPesquisaOr(
                    new ParametroPesquisaLike("motorista.nome", filtro.getNome()),
                    new ParametroPesquisaLike("motorista.apelido", filtro.getNome()))
            );
        }
        parametros.add(new ParametroPesquisaFetch("motorista"));
        parametros.add(new ParametroPesquisaFetch("veiculo"));

        return pesquisar(filtro.getPaginacao(),parametros.toArray(new ParametroPesquisa[0]));
    }

    @Override
    public void excluirPermanentementePorIdMotorista(Long idMotorista) {
        Query query = getGerenciadorDeEntidade().createQuery(EXCLUSAO_POR_ID_MOTORISTA);
        query.setParameter("idMotorista", idMotorista);
        query.executeUpdate();
    }
}