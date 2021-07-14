package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPendenciaChamadoDados;
import ipp.aci.boleia.dominio.PendenciaChamado;
import ipp.aci.boleia.dominio.enums.salesforce.StatusPendenciaChamado;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do repositório de dados de uma {@link PendenciaChamado}.
 *
 * @author pedro.silva
 */
@Repository
public class OraclePendenciaChamadoDados extends OracleRepositorioBoleiaDados<PendenciaChamado> implements IPendenciaChamadoDados {

    private static final String ULTIMA_PENDENCIA_QUERY =
            "SELECT pc " +
            "FROM PendenciaChamado pc " +
            "WHERE pc.usuario.id = :idUsuario AND " +
            "      pc.statusPendencia = " + StatusPendenciaChamado.EM_ABERTO.getValue() + " AND " +
            "      (pc.naoLembrar IS NULL OR pc.naoLembrar = 0) " +
            "ORDER BY pc.dataPendencia DESC";

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Instancia o repositorio
     */
    public OraclePendenciaChamadoDados() {
        super(PendenciaChamado.class);
    }

    @Override
    public PendenciaChamado obterUltimaPendencia(Long idUsuario) {
        return pesquisarUnicoSemIsolamentoDados(ULTIMA_PENDENCIA_QUERY, true, new ParametroPesquisaIgual("idUsuario", idUsuario));
    }

    @Override
    public List<PendenciaChamado> listarPendenciasEmAberto(String idSalesforce) {
        List<ParametroPesquisa> parametrosPesquisa = new ArrayList<>();
        parametrosPesquisa.add(new ParametroPesquisaIgual("idChamadoSalesforce", idSalesforce));
        parametrosPesquisa.add(new ParametroPesquisaIgual("statusPendencia", StatusPendenciaChamado.EM_ABERTO.getValue()));
        return pesquisar((ParametroOrdenacaoColuna) null, parametrosPesquisa.toArray(new ParametroPesquisa[parametrosPesquisa.size()]));
    }

    @Override
    public List<PendenciaChamado> listarPendenciasVencidas() {
        return listarPendenciasVencidas(null);
    }

    @Override
    public List<PendenciaChamado> listarPendenciasVencidas(String idSalesforce) {
        List<ParametroPesquisa> parametrosPesquisa = new ArrayList<>();
        if(idSalesforce != null) {
            parametrosPesquisa.add(new ParametroPesquisaIgual("idChamadoSalesforce", idSalesforce));
        }
        parametrosPesquisa.add(new ParametroPesquisaIgual("statusPendencia", StatusPendenciaChamado.EM_ABERTO.getValue()));
        parametrosPesquisa.add(new ParametroPesquisaDataMenor("dataPendencia", UtilitarioCalculoData.obterPrimeiroInstanteDia(UtilitarioCalculoData.adicionarDiasData(ambiente.buscarDataAmbiente(), -7))));
        return pesquisar((ParametroOrdenacaoColuna) null, parametrosPesquisa.toArray(new ParametroPesquisa[parametrosPesquisa.size()]));
    }
}
