package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPontoDeVendaDados;
import ipp.aci.boleia.dados.IPropostaAntecipacaoDados;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.PropostaAntecipacao;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusAntecipacao;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoAntecipacaoJde;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoReembolsoJde;
import ipp.aci.boleia.dominio.enums.StatusPropostaXP;
import ipp.aci.boleia.dominio.enums.TipoAntecipacao;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.vo.EnumVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAntecipacaoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação do repositório de dados da entidade {@link PropostaAntecipacao}.
 */
@Repository
public class OraclePropostaAntecipacaoDados extends OracleRepositorioBoleiaDados<PropostaAntecipacao> implements IPropostaAntecipacaoDados {

    private static final String CONSULTA_PESQUISA_ANTECIPACAO_PARCERIA =
            "SELECT PA " +
                "FROM PropostaAntecipacao PA " +
                    "JOIN FETCH PA.reembolsoAntecipado RA " +
                    "LEFT JOIN FETCH PA.cobrancaXp CXP " +
                    "LEFT JOIN FETCH PA.reembolsoXp RXP " +
                    "JOIN FETCH RA.transacaoConsolidada TC " +
                    "JOIN FETCH TC.frotaPtov FPV " +
                    "JOIN FPV.frota F " +
                    "JOIN FPV.pontoVenda PV " +
                "WHERE " +
                    " RA.tipoAntecipacao = " + TipoAntecipacao.PARCEIRO_XP.getValue() + " " +
                    "%s " +
                    "%s " +
                    "%s " +
                    "%s " +
                    "%s " +
                    "%s ";

    @Autowired
    private IPontoDeVendaDados repositorioPv;

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Instancia o repositorio
     */
    public OraclePropostaAntecipacaoDados() {
        super(PropostaAntecipacao.class);
    }


    @Override
    public PropostaAntecipacao obterPorIdParceiro(String idProposta) {
        return pesquisarUnico(new ParametroPesquisaIgual("idParceiro", idProposta));
    }

    @Override
    public ResultadoPaginado<PropostaAntecipacao> pesquisarAntecipacoesReembolso(FiltroPesquisaAntecipacaoVo filtro) {
        String clausulaAssessorFrota = "";
        String clausulaPontoVenda = "";
        String clausulaStatus = "";
        String clausulaStatusIntegracao = "";
        String clausulaPeriodo = "";

        Long idptov = null;
        List<ParametroPesquisa> parametros = new ArrayList<>();
        Usuario usuarioLogado = ambiente.getUsuarioLogado();

        if(filtro.getStatusAntecipacao().getValue() != null) {
            clausulaStatus = montarFiltroStatus(filtro.getStatusAntecipacao());
            if(filtro.getStatusAntecipacao().getValue().equals(StatusAntecipacao.AGUARDANDO_ACEITE.getValue()) ||
                    filtro.getStatusAntecipacao().getValue().equals(StatusAntecipacao.CANCELADO_SEM_RESPOSTA.getValue())) {
                parametros.add(new ParametroPesquisaIgual("dataAtual", ambiente.buscarDataAmbiente()));
            }
        }

        if(filtro.getStatusIntegracao().getValue() != null) {
            clausulaStatusIntegracao = montarFiltroStatusIntegracao(filtro.getStatusIntegracao());
        }

        if(filtro.getDe() != null && filtro.getAte() != null) {
            clausulaPeriodo = " AND PA.dataCriacao BETWEEN :de AND :ate ";
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("de", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
            parametros.add(new ParametroPesquisaDataMenorOuIgual("ate", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));
        }

        if(usuarioLogado.isInterno() && usuarioLogado.possuiFrotasAssociadas()) {
            clausulaAssessorFrota = "AND F.id in (:idsFrota) ";
            parametros.add(new ParametroPesquisaIn("idsFrota", usuarioLogado.listarIdsFrotasAssociadas()));
        }

        if(filtro.getPontoDeVenda() != null) {
            clausulaPontoVenda = " AND PV.id = :idptov ";
            parametros.add(new ParametroPesquisaIgual("idptov", filtro.getPontoDeVenda().getId()));
        } else if(usuarioLogado.isRevendedor()) {
            clausulaPontoVenda = " AND PV.id in :idsptov ";
            parametros.add(new ParametroPesquisaIn("idsptov", usuarioLogado.getPontosDeVenda().stream()
                    .map(PontoDeVenda::getId).collect(Collectors.toList())));
        }
        String clausulaOrdenacao = montarClausulaOrdenacao(filtro.getPaginacao());

        return pesquisar(filtro.getPaginacao(), String.format(CONSULTA_PESQUISA_ANTECIPACAO_PARCERIA, clausulaPeriodo, clausulaStatus, clausulaStatusIntegracao, clausulaAssessorFrota, clausulaPontoVenda, clausulaOrdenacao),
                parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Monta a clausula de ordenação para a consulta de antecipacao
     * @param paginacao informacao de paginacao e ordenacao recebido na requisicao
     * @return QueryString de ordenacao
     */
    private String montarClausulaOrdenacao(InformacaoPaginacao paginacao) {
        String clausulaOrdenacao = "";
        if(paginacao != null && !paginacao.getParametrosOrdenacaoColuna().isEmpty()){
            clausulaOrdenacao = "ORDER BY ";
            List<String> parametrosOrdenacao = new ArrayList<>();
            paginacao.getParametrosOrdenacaoColuna().forEach(parametroOrdenacaoColuna -> {
                String sentidoOrdenacao = Ordenacao.DECRESCENTE.equals(parametroOrdenacaoColuna.getSentidoOrdenacao()) ? "DESC" : "ASC";
                parametrosOrdenacao.add("PA." + parametroOrdenacaoColuna.getNome() + " " + sentidoOrdenacao);
            });
            clausulaOrdenacao += String.join(",", parametrosOrdenacao);
        }
        return clausulaOrdenacao;
    }

    /**
     * Monta o filtro de status de antecipação baseado no status obtido na requisição
     * @param status o status recebido na requisição
     * @return A clausula de filtro do status de antecipação
     */
    private String montarFiltroStatus(EnumVo status) {
        String clausulaStatus = null;
        StatusAntecipacao statusAntecipacao = StatusAntecipacao.valueOf(status.getName());
        switch (statusAntecipacao) {
            case ANTECIPADO:
                clausulaStatus = " AND PA.isAceito = true AND PA.status = " + StatusPropostaXP.APPROVED.getValue() +
                        " AND RA.statusIntegracao = " + StatusIntegracaoReembolsoJde.ANTECIPADO.getValue() + " ";
                break;
            case AGUARDANDO_ACEITE:
                clausulaStatus = " AND PA.isAceito IS NULL AND TRUNC(:dataAtual) < TRUNC(RA.dataVencimentoPgto) ";
                break;
            case CANCELADO_CLIENTE:
                clausulaStatus = " AND PA.isAceito = false ";
                break;
            case CANCELADO_SEM_RESPOSTA:
                clausulaStatus = " AND PA.isAceito IS NULL AND TRUNC(:dataAtual) >= TRUNC(RA.dataVencimentoPgto) ";
                break;
            case EM_ANDAMENTO:
                clausulaStatus = " AND PA.isAceito = true" +
                        " AND RA.statusIntegracao = " + StatusIntegracaoReembolsoJde.REALIZADO.getValue() +
                        " AND CXP.statusIntegracao = " + StatusIntegracaoJde.REALIZADO.getValue() +
                        " AND RXP.statusIntegracao = " + StatusIntegracaoJde.REALIZADO.getValue() + " ";
                break;
            case PENDENTE:
                clausulaStatus = " AND PA.isAceito = true AND (RA.statusIntegracao IS NULL OR RA.statusIntegracao IN (" + StatusIntegracaoReembolsoJde.ERRO_ENVIO.getValue() +
                        ", " + StatusIntegracaoReembolsoJde.ERRO_LIBERACAO.getValue() + ", " + StatusIntegracaoReembolsoJde.PENDENTE.getValue()  + ")) ";
                break;
            default:
                clausulaStatus = "";
                break;
        }
        return clausulaStatus;
    }

    /**
     * Monta o filtro de status de integração da antecipação baseado no status obtido na requisição
     * @param statusIntegracao o status de integração recebido na requisição
     * @return A clausula de filtro do status de integração da antecipação
     */
    private String montarFiltroStatusIntegracao(EnumVo statusIntegracao) {
        String clausulaStatusIntegracao = null;
        StatusIntegracaoAntecipacaoJde statusIntegracaoAntecipacaoJde = StatusIntegracaoAntecipacaoJde.valueOf(statusIntegracao.getName());
        switch (statusIntegracaoAntecipacaoJde) {
            case PREVISTO:
                clausulaStatusIntegracao = " AND RA.statusIntegracao IS NULL" +
                        " AND (CXP IS NULL OR CXP.statusIntegracao IS NULL)" +
                        " AND (RXP IS NULL OR RXP.statusIntegracao IS NULL) ";
                break;
            case REALIZADO:
                clausulaStatusIntegracao = " AND RA.statusIntegracao = " + StatusIntegracaoReembolsoJde.REALIZADO.getValue() +
                        " AND CXP.statusIntegracao = " + StatusIntegracaoJde.REALIZADO.getValue() +
                        " AND RXP.statusIntegracao = " + StatusIntegracaoJde.REALIZADO.getValue() + " ";
                break;
            case ERRO_ENVIO_F7:
                clausulaStatusIntegracao = " AND CXP.statusIntegracao = " + StatusIntegracaoJde.ERRO_ENVIO.getValue() +
                        " AND RA.statusIntegracao = " + StatusIntegracaoJde.REALIZADO.getValue() +
                        " AND RXP.statusIntegracao = " + StatusIntegracaoJde.REALIZADO.getValue() + " ";
                break;
            case ERRO_ENVIO_PV:
                clausulaStatusIntegracao = " AND (RA.statusIntegracao = " + StatusIntegracaoReembolsoJde.ERRO_ENVIO.getValue() +
                        " OR RXP.statusIntegracao = " + StatusIntegracaoJde.ERRO_ENVIO.getValue() + ")" +
                        " AND CXP.statusIntegracao = " + StatusIntegracaoJde.REALIZADO.getValue() + " ";
                break;
            case ERRO_ENVIO_F7_PV:
                clausulaStatusIntegracao = " AND CXP.statusIntegracao = " + StatusIntegracaoJde.ERRO_ENVIO.getValue() +
                        " AND ( RA.statusIntegracao = " + StatusIntegracaoReembolsoJde.ERRO_ENVIO.getValue() +
                        " OR RXP.statusIntegracao = " + StatusIntegracaoJde.ERRO_ENVIO.getValue() + " ) ";
                break;
            default:
                clausulaStatusIntegracao = "";
                break;
        }
        return clausulaStatusIntegracao;
    }
}
