package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IFrotaDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.ClassificacaoStatusFrota;
import ipp.aci.boleia.dominio.enums.ModalidadePagamento;
import ipp.aci.boleia.dominio.enums.StatusAcumuloKmv;
import ipp.aci.boleia.dominio.enums.StatusApiToken;
import ipp.aci.boleia.dominio.enums.StatusAtivado;
import ipp.aci.boleia.dominio.enums.StatusContrato;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.enums.StatusFrotaConectcar;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.dominio.enums.StatusVigenciaAlteracaoStatusFrota;
import ipp.aci.boleia.dominio.enums.TipoAcumuloKmv;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEntre;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.CoordenadaVo;
import ipp.aci.boleia.dominio.vo.FiltroAutoCompletePostoRotaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAbastecimentoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDetalheCicloVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaFinanceiroVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaFrotaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialFrotaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPostoInternoRotaVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.seguranca.UtilitarioIsolamentoInformacoes;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Respositorio de entidades Frota
 */
@Repository
public class OracleFrotaDados extends OracleRepositorioBoleiaDados<Frota> implements IFrotaDados {

    @Autowired
    private UtilitarioAmbiente ambiente;

    private static final String CLAUSULA_DATA_REEMB_GERADO =
            " tc.reembolso IS NOT NULL AND " +
            " ((r.dataPagamento is null  AND r.status <> " + StatusPagamentoReembolso.SEM_REEMBOLSO.getValue() + " AND (r.dataVencimentoPgto >= :dataInicial AND r.dataVencimentoPgto <= :dataFinal)) " +
            " OR (r.dataPagamento >= :dataInicial AND r.dataPagamento <= :dataFinal) " +
            " OR ((r.status = " + StatusPagamentoReembolso.SEM_REEMBOLSO.getValue() + " OR r.valorDescontoAntecipacao > 0) AND tc.dataInicioPeriodo >= :dataInicial AND tc.dataFimPeriodo <= :dataFinal)) " ;

    private static final String CLAUSULA_DATA_REEMB_NAO_GERADO =
            " tc.reembolso IS NULL AND " +
            " ((f.modoPagamento = " + ModalidadePagamento.POS_PAGO.getValue() +
            " AND (trunc(tc.dataFimPeriodo + prz.prazoReembolso) >= :dataInicial AND trunc(tc.dataFimPeriodo + prz.prazoReembolso) <= :dataFinal)) " +
            " OR (f.modoPagamento = " + ModalidadePagamento.PRE_PAGO.getValue() +
            " AND (trunc(tc.dataFimPeriodo + 2) >= :dataInicial AND trunc(tc.dataFimPeriodo + 2) <= :dataFinal))) ";

    private static final String CONSULTA_DONO_FROTA_COM_ACUMULO =
            "SELECT DISTINCT fr" +
                " FROM AcumuloKmv ac " +
                " INNER JOIN ac.frota fr" +
                " WHERE ac.statusAcumulo = " + StatusAcumuloKmv.ACUMULADO.getValue().toString() +
                    " AND ac.tipoAcumulo = " + TipoAcumuloKmv.DONO_FROTA.getValue().toString()  +
                    " AND fr.emailDonoFrota is not null" +
                    " AND ac.dataEnvio BETWEEN :dataInicial AND :dataFinal";

    private static final String CONSULTA_FROTA_UNIDADE_HQL = "SELECT DISTINCT fr" +
            " FROM Frota fr " +
            " LEFT JOIN fetch fr.unidades u" +
            " WHERE fr.postoInterno = true AND fr.status = 1" +
            "  %s " +
            "  %s ";

    private static final String CONSULTA_FROTA_POR_COBRANCA =
            "SELECT DISTINCT f " +
            "FROM TransacaoConsolidada tc " +
            "JOIN tc.frotaPtov.frota f " +
            "WHERE tc.cobranca.id = :idCobranca " +
            "     AND f.excluido = 0";

    private static final String CONSULTA_FROTA_POR_COBRANCA_REEMBOLSO_CONSOLIDADO =
    "SELECT DISTINCT f " +
    "FROM TransacaoConsolidada tc " +
    "JOIN tc.frotaPtov.frota f " +
    "WHERE ((:idConsolidado IS NOT NULL AND tc.id = :idConsolidado) " +
    "OR (:idCobranca IS NOT NULL AND tc.cobranca.id = :idCobranca) " +
    "OR (:idReembolso IS NOT NULL AND tc.reembolso.id = :idReembolso)) " +
    "     AND f.excluido = 0";


    private static final String CONSULTA_CLIENTE_PROFROTAS =
            "SELECT new ipp.aci.boleia.dominio.vo.apco.ClienteProFrotaVo(" +
                    "f.id, f.cnpj, f.razaoSocial, f.status, MAX(m.dataInicio)) " +
                    "FROM MotivoAlteracaoStatusFrota AS m " +
                    "RIGHT JOIN m.frota AS f";

    private static final String CONSULTA_FROTAS_ASSOCIADAS_A_CICLOS_CONTIDOS_NO_PERIODO =
            "SELECT DISTINCT f " +
                    "FROM " +
                    "TransacaoConsolidada tc " +
                    "JOIN tc.frotaPtov fp " +
                    "JOIN fp.frota f " +
                    "LEFT JOIN tc.reembolso r " +
                    "JOIN tc.prazos prz " +
                    "WHERE " +
                    "((" + CLAUSULA_DATA_REEMB_GERADO + ") OR (" + CLAUSULA_DATA_REEMB_NAO_GERADO + "))" +
                    "AND (fp.pontoVenda.id IN :idsPvs) " +
                    "ORDER BY f.razaoSocial";

    private static final String CONSULTA_FROTAS_PARA_DETALHE_CICLO =
            "SELECT DISTINCT f " +
                    "FROM " +
                    "TransacaoConsolidada tc " +
                    "JOIN tc.frotaPtov fp " +
                    "JOIN fp.frota f " +
                    "LEFT JOIN tc.reembolso rm	" +
                    "WHERE " +
                    "   (trunc(tc.dataInicioPeriodo) = trunc(:dataInicial) and trunc(tc.dataFimPeriodo) = trunc(:dataFinal)) AND " +
                    "   (tc.statusConsolidacao = :statusCiclo) AND " +
                    "   (fp.pontoVenda.id IN :idsPvs) AND " +
                    "   (tc.reembolso is NULL OR tc.reembolso.status = " + StatusPagamentoReembolso.EM_ABERTO.getValue() + " OR tc.reembolso.status = " + StatusPagamentoReembolso.PREVISTO.getValue() + ")" +
                    "ORDER BY f.nomeRazaoFrota";

    private static final String CONSULTA_FROTAS_MULTIPLOS_MOTIVOS =
            "SELECT DISTINCT F " +
            "FROM " +
            "Frota F " +
            "JOIN FETCH F.motivosAlteracaoStatus MAS " +
            "LEFT JOIN FETCH MAS.usuario U " +
            "WHERE size(MAS) > 1 " +
            "AND MAS.statusVigenciaAlteracao = " + StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue();

    /**
     * Instancia o repositorio
     */
    public OracleFrotaDados() {
        super(Frota.class);
    }

    @Override
    public ResultadoPaginado<Frota> pesquisar(FiltroPesquisaFrotaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        povoarParametroIgual("id", filtro.getFrota() != null ? filtro.getFrota().getId() : null, parametros);
        povoarParametroLike("municipio", filtro.getCidade(), parametros);
        povoarParametroLike("unidadeFederativa", filtro.getUf() != null ? filtro.getUf().getName() : null, parametros);
        povoarParametroIgual("usuarioAssessorResponsavel.id", filtro.getAssessor() != null ? filtro.getAssessor().getId() : null, parametros);

        if (filtro.getCnpj() != null) {
            parametros.add(new ParametroPesquisaIgual("cnpj", UtilitarioFormatacao.obterLongMascara(filtro.getCnpj())));
        }

        if (!StringUtils.isEmpty(filtro.getRazaoSocial())) {
            parametros.add(new ParametroPesquisaOr(
                    new ParametroPesquisaLike("nomeFantasia", filtro.getRazaoSocial()),
                    new ParametroPesquisaLike("razaoSocial", filtro.getRazaoSocial())
            ));
        }

        if (filtro.getStatusContrato() != null && filtro.getStatusContrato().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("statusContrato", StatusContrato.valueOf(filtro.getStatusContrato().getName()).getValue()));
        }

        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("status", StatusFrota.valueOf(filtro.getStatus().getName()).getValue()));
        }
        
        if (filtro.getStatusConectcar() != null && filtro.getStatusConectcar().getName() != null) {
        	
        	if (StatusFrotaConectcar.ATIVO.equals(StatusFrotaConectcar.valueOf(filtro.getStatusConectcar().getName()))) {
        		parametros.add(new ParametroPesquisaOr(
                        new ParametroPesquisaNulo("situacaoConectCar"),
                        new ParametroPesquisaIgual("situacaoConectCar.status", StatusFrota.ATIVO.getValue())
                ));
        	} else {
        		parametros.add(new ParametroPesquisaIgual("situacaoConectCar.status", StatusFrota.INATIVO.getValue()));
        	}
        }
        
        if (filtro.getPossuiCondicaoComercialConectcar() != null && filtro.getPossuiCondicaoComercialConectcar()) {
            parametros.add(new ParametroPesquisaNulo("condicoesComerciais", true));
        }

        if (ambiente.getUsuarioLogado().isRevendedor()) {
            parametros.add(new ParametroPesquisaIgual("negociacoes.pontoVenda.rede.id", ambiente.getUsuarioLogado().getRede().getId()));
            parametros.add(new ParametroPesquisaDiferente("semNotaFiscal", StatusAtivado.ATIVO.getValue()));
        }

        if (filtro.getPaginacao() != null && CollectionUtils.isNotEmpty(filtro.getPaginacao().getParametrosOrdenacaoColuna())) {
            ParametroOrdenacaoColuna parametro = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0);
            String nomeOrdenacao = parametro.getNome();
            if (nomeOrdenacao != null) {
            	if (nomeOrdenacao.contentEquals("statusConectcar")) {
            		filtro.getPaginacao().getParametrosOrdenacaoColuna().remove(0);
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("situacaoConectCar.status", parametro.getSentidoOrdenacao()));
                }
            }
        }

        if (filtro.getClassificacaoStatusFrota() != null && filtro.getClassificacaoStatusFrota().getName() != null) {
            parametros.add(new ParametroPesquisaAnd(
                    new ParametroPesquisaIgual("motivosAlteracaoStatus.tipoMotivo", ClassificacaoStatusFrota.valueOf(filtro.getClassificacaoStatusFrota().getName()).getValue()),
                    new ParametroPesquisaIgual("motivosAlteracaoStatus.statusVigenciaAlteracao", StatusVigenciaAlteracaoStatusFrota.VIGENTE.getValue()))
            );
        }

        if(filtro.getGerenciaNf() != null) {
            parametros.add(new ParametroPesquisaIgual("gerenciaNf", filtro.getGerenciaNf()));
        }
        
        povoarParametroApiToken(filtro, parametros);

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));

    }

    @Override
    public ResultadoPaginado<Frota> pesquisarValidacaoSegregacao(FiltroPesquisaFrotaVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        povoarParametroIgual("id", filtro.getFrota() != null ? filtro.getFrota().getId() : null, parametros);
        povoarParametroLike("municipio", filtro.getCidade(), parametros);
        povoarParametroLike("unidadeFederativa", filtro.getUf() != null ? filtro.getUf().getName() : null, parametros);
        povoarParametroIgual("usuarioAssessorResponsavel.id", filtro.getAssessor() != null ? filtro.getAssessor().getId() : null, parametros);

        if (filtro.getCnpj() != null) {
            parametros.add(new ParametroPesquisaIgual("cnpj", UtilitarioFormatacao.obterLongMascara(filtro.getCnpj())));
        }

        if (!StringUtils.isEmpty(filtro.getRazaoSocial())) {
            parametros.add(new ParametroPesquisaOr(
                    new ParametroPesquisaLike("nomeFantasia", filtro.getRazaoSocial()),
                    new ParametroPesquisaLike("razaoSocial", filtro.getRazaoSocial())
            ));
        }

        if (filtro.getStatusContrato() != null && filtro.getStatusContrato().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("statusContrato", StatusContrato.valueOf(filtro.getStatusContrato().getName()).getValue()));
        }

        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("status", StatusFrota.valueOf(filtro.getStatus().getName()).getValue()));
        }

        if (filtro.getStatusConectcar() != null && filtro.getStatusConectcar().getName() != null) {

            if (StatusFrotaConectcar.ATIVO.equals(StatusFrotaConectcar.valueOf(filtro.getStatusConectcar().getName()))) {
                parametros.add(new ParametroPesquisaOr(
                        new ParametroPesquisaNulo("situacaoConectCar"),
                        new ParametroPesquisaIgual("situacaoConectCar.status", StatusFrota.ATIVO.getValue())
                ));
            } else {
                parametros.add(new ParametroPesquisaIgual("situacaoConectCar.status", StatusFrota.INATIVO.getValue()));
            }
        }

        if (filtro.getPossuiCondicaoComercialConectcar() != null && filtro.getPossuiCondicaoComercialConectcar()) {
            parametros.add(new ParametroPesquisaNulo("condicoesComerciais", true));
        }

        if (filtro.getPaginacao() != null && CollectionUtils.isNotEmpty(filtro.getPaginacao().getParametrosOrdenacaoColuna())) {
            ParametroOrdenacaoColuna parametro = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0);
            String nomeOrdenacao = parametro.getNome();
            if (nomeOrdenacao != null) {
                if (nomeOrdenacao.contentEquals("statusConectcar")) {
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().remove(0);
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("situacaoConectCar.status", parametro.getSentidoOrdenacao()));
                }
            }
        }

        povoarParametroApiToken(filtro, parametros);
        if(UtilitarioIsolamentoInformacoes.isUsuarioInternoAssessorOuCoordenador(usuarioLogado)) {
            return pesquisarSemIsolamentoDados(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
        } else {
            return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
        }
    }

    /**
     * Povoa os parametros referentes a api de token
     *
     * @param filtro     filtro de pesquisa frota
     * @param parametros parametros do token api
     */
    private void povoarParametroApiToken(FiltroPesquisaFrotaVo filtro, List<ParametroPesquisa> parametros) {
        if (filtro.getStatusApiToken() != null && filtro.getStatusApiToken().getName() != null) {
            StatusApiToken filtroStatus = StatusApiToken.valueOf(filtro.getStatusApiToken().getName());
            if (StatusApiToken.EXPIRADA.equals(filtroStatus)) {
                parametros.add(
                        new ParametroPesquisaAnd(
                                new ParametroPesquisaDataMenor("apiTokens.dataExpiracao", ambiente.buscarDataAmbiente()),
                                parametroTokenNaoContingencia()
                        )
                );
            } else if (!StatusApiToken.NAO_GERADA.getValue().equals(filtroStatus.getValue())) {
                parametros.add(
                        new ParametroPesquisaAnd(
                                new ParametroPesquisaIgual("apiTokens.status", filtroStatus.getValue()),
                                new ParametroPesquisaDataMaiorOuIgual("apiTokens.dataExpiracao", ambiente.buscarDataAmbiente()),
                                parametroTokenNaoContingencia()
                        )
                );
            } else {
                parametros.add(new ParametroPesquisaNulo("apiTokens"));
            }
        }
    }

    /**
     * Povoa o parametro de pesquisa token em nao contigencia
     *
     * @return parametro de pesquisa
     */
    private ParametroPesquisa parametroTokenNaoContingencia() {
        return new ParametroPesquisaOr(
                new ParametroPesquisaNulo("apiTokens.contingencia"),
                new ParametroPesquisaIgual("apiTokens.contingencia", false)
        );
    }

    @Override
    public Frota pesquisarPorCnpj(Long cnpj) {
        if (cnpj == null) {
            return null;
        }
        return pesquisarUnico(new ParametroPesquisaIgual("cnpj", cnpj));
    }
    
    @Override
    public List<Frota> pesquisarPorCnpjSemIsolamento(Long cnpj) {
        if(cnpj == null) {
            return null;
        }
        return pesquisarSemIsolamentoDados((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("cnpj", cnpj));
    }

    @Override
    public List<Frota> pesquisarPorCnpjRazaoSocial(FiltroPesquisaParcialFrotaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametrosParaAutocomplete(filtro, parametros);

        return pesquisar(new ParametroOrdenacaoColuna("razaoSocial"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<Frota> pesquisarPorCnpjRazaoSocialValidacaoSegregacao(FiltroPesquisaParcialFrotaVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametrosParaAutocomplete(filtro, parametros);

        if(UtilitarioIsolamentoInformacoes.isUsuarioInternoAssessorOuCoordenador(usuarioLogado)) {
            return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("razaoSocial"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
        } else {
            return pesquisar(new ParametroOrdenacaoColuna("razaoSocial"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
        }
    }

    @Override
    public List<Frota> pesquisarParaAutocomplete(String termo) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaLike("razaoSocial", termo.replaceAll("[-./]+", "")));
        if (ambiente.getUsuarioLogado().isRevendedor()) {
            parametros.add(new ParametroPesquisaIgual("negociacoes.pontoVenda.rede.id", ambiente.getUsuarioLogado().getRede().getId()));
        }

        return pesquisar(new ParametroOrdenacaoColuna("razaoSocial"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public boolean pesquisarPorPostoInterno(Long idFrota) {
        Frota frota = pesquisarUnico(new ParametroPesquisaIgual("id", idFrota), new ParametroPesquisaIgual("postoInterno", 1));
        return frota != null;
    }

    @Override
    public Long obterQuantidadeIntegradaPorCnpj(Long cnpj) {
        String consulta = "SELECT count(0) FROM Frota f WHERE f.cnpj = :cnpj and f.numeroJdeInterno is not null";
        Long result = pesquisarUnicoSemIsolamentoDados(consulta, new ParametroPesquisaIgual("cnpj", cnpj));
        if (result == null) {
            return 0L;
        }
        return result;
    }

    @Override
    public List<Frota> obterFrotasInativasEmAtivacaoTemporaria() {
        Date dataAtual = ambiente.buscarDataAmbiente();
        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIgual("status", StatusFrota.INATIVO.getValue()),
                new ParametroPesquisaDataMaiorOuIgual("fimAtivacaoTemporaria", dataAtual),
                new ParametroPesquisaDataMenorOuIgual("inicioAtivacaoTemporaria", dataAtual));
    }

    @Override
    public List<Frota> obterFrotasAtivasAposAtivacaoTemporaria() {
        Date dataAtual = ambiente.buscarDataAmbiente();
        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIgual("status", StatusFrota.ATIVO.getValue()),
                new ParametroPesquisaDataMenorOuIgual("fimAtivacaoTemporaria", dataAtual));
    }

    @Override
    public List<Frota> obterFrotaPorPlacaVeiculo(String placa) {
        return pesquisar(new ParametroOrdenacaoColuna("razaoSocial"), new ParametroPesquisaIgualIgnoreCase("veiculos.placa", placa));
    }

    @Override
    public List<Long> buscarFrotasInativadas() {
        List<Frota> frotas = pesquisar(new ParametroOrdenacaoColuna("id"), new ParametroPesquisaIgual("status", StatusFrota.INATIVO.getValue()));
        return frotas.stream().map(Frota::getId).collect(Collectors.toList());
    }

    @Override
    public List<Frota> buscarFrotaComPostoInterno(FiltroPesquisaParcialFrotaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametrosParaAutocomplete(filtro, parametros);
        ParametroPesquisaIgual parametroPostoInterno = new ParametroPesquisaIgual("postoInterno", true);
        parametros.add(parametroPostoInterno);

        return pesquisar(new ParametroOrdenacaoColuna("razaoSocial"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<Frota> buscarFrotaAtivaComPostoInternoPorDataAtualizacao(Date dataUltimaAtualizacao, List<ParametroPesquisa> parametros) {
        if (parametros == null) {
            parametros = new ArrayList<>();
        }
        povoarParametroData(dataUltimaAtualizacao, parametros);
        return this.buscarFrotasAtivasComPostoInterno(parametros);
    }

    /**
     * Adiciona o parametro de data de ultima atualizacao a consulta de frota
     * @param dataUltimaAtualizacao a data da ultima atualizacao
     * @param parametros lista de parametros da consulta
     */
    private void povoarParametroData(Date dataUltimaAtualizacao, List<ParametroPesquisa> parametros) {
        if (dataUltimaAtualizacao != null) {
            parametros.add(
                    new ParametroPesquisaOr(
                            new ParametroPesquisaAnd(
                                    new ParametroPesquisaDataMaior("dataCriacao", dataUltimaAtualizacao),
                                    new ParametroPesquisaNulo("dataAtualizacao")),
                            new ParametroPesquisaDataMaior("dataAtualizacao", dataUltimaAtualizacao)));
        }
    }

    @Override
    public List<Frota> buscarFrotasAtivasComPostoInterno(List<ParametroPesquisa> parametros) {
        if (parametros == null) {
            parametros = new ArrayList<>();
        }

        parametros.add(new ParametroPesquisaIgual("postoInterno", true));
        parametros.add(new ParametroPesquisaIgual("status", StatusFrota.ATIVO.getValue()));

        return pesquisar(new ParametroOrdenacaoColuna("dataAtualizacao"), parametros.toArray(new ParametroPesquisa[parametros.size()]));

    }

    @Override
    public List<Frota> listarFrotasPossuemTokenConnectCTA() {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaNulo("connectCTAToken", true));

        return pesquisar(new ParametroOrdenacaoColuna("nomeRazaoFrota"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Povoa os parametros de pesquisa para a busca do componente de auto-complete
     *
     * @param filtro     O filtro de pesquisa
     * @param parametros Os parametros da consulta
     */
    private void povoarParametrosParaAutocomplete(FiltroPesquisaParcialFrotaVo filtro, List<ParametroPesquisa> parametros) {
        // Zeros a esquerda e quaisquer pontuacoes devem ser removidos pois CNPJ sao gravados como numeros inteiros no banco
        String termoCnpj = (filtro.getTermo() == null) ? null : filtro.getTermo().replaceAll("[-./]+", "").replaceFirst("^0+(?!$)", "");
        ParametroPesquisa paramCnpj = new ParametroPesquisaLike("cnpj", termoCnpj);
        ParametroPesquisa paramRazao = new ParametroPesquisaLike("razaoSocial", filtro.getTermo());

        parametros.add(new ParametroPesquisaOr(paramRazao, paramCnpj));

        if (ambiente.getUsuarioLogado().isRevendedor()) {
            parametros.add(new ParametroPesquisaIgual("negociacoes.pontoVenda.rede.id", ambiente.getUsuarioLogado().getRede().getId()));
        }

        if (filtro.getApenasFrotasHabilitadas()) {
            parametros.add(new ParametroPesquisaIgual("status", StatusFrota.ATIVO.getValue()));

        }
        if (filtro.getPontoDeVenda() != null) {
            parametros.add(new ParametroPesquisaIgual("negociacoes.pontoVenda.id", filtro.getPontoDeVenda().getId()));
        }
    }

    @Override
    public List<Frota> buscarFrotaUnidadeAtivaComPostoInternoPorDataAtualizacaoECnpj(Date dataUltimaAtualizacao, Long cnpj) {

        String consulta = CONSULTA_FROTA_UNIDADE_HQL;

        String filtroData = "";
        if (dataUltimaAtualizacao != null) {
            filtroData = " AND ((fr.dataAtualizacao IS NULL AND fr.dataCriacao > :dataUltimaAtualizacao) OR (fr.dataAtualizacao > :dataUltimaAtualizacao))";
        }
        
        String filtroCnpjFrotaUnidade = "";

        if (cnpj != null) {
            filtroCnpjFrotaUnidade = " AND (fr.cnpj = :cnpj OR u.cnpj = :cnpj)";
        }
        consulta = String.format(consulta, filtroData, filtroCnpjFrotaUnidade);
        List<ParametroPesquisa> parametros = new ArrayList<>();

        if (cnpj != null) {
            parametros.add(new ParametroPesquisaIgual("cnpj", cnpj));
        }

        if(dataUltimaAtualizacao!=null){
            parametros.add(new ParametroPesquisaIgual("dataUltimaAtualizacao",dataUltimaAtualizacao));
        }
        return pesquisar(null, consulta, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }
    
    @Override
    public Frota obterPorCnpj(Long cnpj) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("cnpj", cnpj));
    }
    
    @Override
    public List<Frota> buscarFrotaComKmvAcumuladosParaDonoDaFrota(Date dataInicio, Date dataFim) {
        return pesquisar(null, CONSULTA_DONO_FROTA_COM_ACUMULO,new ParametroPesquisaIgual("dataInicial", dataInicio), new ParametroPesquisaIgual("dataFinal", dataFim)).getRegistros();
    }

    @Override
    public Frota obterPorCobranca(Long idCobranca) {
        return pesquisarUnico(CONSULTA_FROTA_POR_COBRANCA, new ParametroPesquisaIgual("idCobranca", idCobranca));
    }

    @Override
    public Frota obterPorConsolidadoCobrancaOuReembolso(FiltroPesquisaAbastecimentoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idConsolidado", filtro.getIdConsolidado()));
        parametros.add(new ParametroPesquisaIgual("idCobranca", filtro.getIdCobranca()));
        parametros.add(new ParametroPesquisaIgual("idReembolso", filtro.getIdReembolso()));

        return pesquisarUnicoSemIsolamentoDados(CONSULTA_FROTA_POR_COBRANCA_REEMBOLSO_CONSOLIDADO,parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<Frota> obterFrotasClienteApcoPorData(Date dataUltimoEnvio){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataAtualizacao",dataUltimoEnvio));
        parametros.add(new ParametroPesquisaDiferente("status",StatusFrota.PRE_CADASTRO.getValue()));
        return pesquisar(new ParametroOrdenacaoColuna("dataAtualizacao", Ordenacao.CRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<Frota> obterFrotasClienteApco(){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaDiferente("status",StatusFrota.PRE_CADASTRO.getValue()));
        return pesquisar(new ParametroOrdenacaoColuna("dataAtualizacao", Ordenacao.CRESCENTE), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }
    
    @Override
    public Frota desanexar(Frota frota) {
        return super.desanexar(frota);
    }

    @Override
    public List<Frota> pesquisarFrotasAssociadasACiclosContidosNoPeriodo(FiltroPesquisaFinanceiroVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataInicial", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataFinal", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));

        if(filtro.getPontoDeVenda() != null) {
            parametros.add(new ParametroPesquisaIn("idsPvs", Collections.singletonList(filtro.getPontoDeVenda().getId())));
        } else if(usuarioLogado.isRevendedor()) {
            parametros.add(new ParametroPesquisaIn("idsPvs", usuarioLogado.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toList())));
        }

        return pesquisarComExcluidos(null, CONSULTA_FROTAS_ASSOCIADAS_A_CICLOS_CONTIDOS_NO_PERIODO, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    @Override
    public List<Frota> pesquisarFrotasParaDetalheCiclo(FiltroPesquisaDetalheCicloVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaIgual("dataInicial", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getInicio())));
        parametros.add(new ParametroPesquisaIgual("dataFinal", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getFim())));
        parametros.add(new ParametroPesquisaIgual("statusCiclo", filtro.getStatusCiclo().getValue()));

        if(filtro.getIdPv() != null) {
            parametros.add(new ParametroPesquisaIn("idsPvs", Collections.singletonList(filtro.getIdPv())));
        } else if(usuarioLogado.isRevendedor()) {
            parametros.add(new ParametroPesquisaIn("idsPvs", usuarioLogado.getPontosDeVenda().stream().map(PontoDeVenda::getId).collect(Collectors.toList())));
        }

        return pesquisarComExcluidos(null, CONSULTA_FROTAS_PARA_DETALHE_CICLO, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    @Override
    public List<Frota> pesquisarParaAutocompleteRota(FiltroAutoCompletePostoRotaVo filtro){
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaNulo("latitude", true));
        parametros.add(new ParametroPesquisaNulo("longitude", true));
        parametros.add(new ParametroPesquisaIgual("postoInterno",true));
        parametros.add(new ParametroPesquisaIgual("id", filtro.getIdFrota()));
        parametros.add(new ParametroPesquisaLike("razaoSocial", filtro.getTermo()));

        return pesquisar(new ParametroOrdenacaoColuna("razaoSocial"), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<Frota> pesquisarPostoInternoNaRota(FiltroPesquisaPostoInternoRotaVo filtro){
        List<ParametroPesquisa> params = new ArrayList();
        params.add(new ParametroPesquisaNulo("latitude", true));
        params.add(new ParametroPesquisaNulo("longitude", true));
        params.add(new ParametroPesquisaIgual("id", filtro.getIdFrota()));

        if (CollectionUtils.isNotEmpty(filtro.getFiltrosCoordenadas())) {
            ParametroPesquisaOr condicoesOr = new ParametroPesquisaOr(new ParametroPesquisa[0]);
            BigDecimal margem = filtro.getMargemGrausFiltroCoordenadas() != null ? filtro.getMargemGrausFiltroCoordenadas() : BigDecimal.valueOf(0L);
            Iterator var5 = filtro.getFiltrosCoordenadas().iterator();

            while(var5.hasNext()) {
                List<CoordenadaVo> listaCoordenadas = (List)var5.next();

                for(int i = 0; i < listaCoordenadas.size() - 1; ++i) {
                    CoordenadaVo ca = listaCoordenadas.get(i);
                    BigDecimal caLat = ca.getLatitude();
                    BigDecimal caLong = ca.getLongitude();
                    CoordenadaVo cb = listaCoordenadas.get(i + 1);
                    BigDecimal cbLat = cb.getLatitude();
                    BigDecimal cbLong = cb.getLongitude();
                    BigDecimal xa = (cbLat.compareTo(caLat) > 0 ? caLat : cbLat).subtract(margem);
                    BigDecimal xb = (cbLat.compareTo(caLat) > 0 ? cbLat : caLat).add(margem);
                    BigDecimal ya = (cbLong.compareTo(caLong) > 0 ? caLong : cbLong).subtract(margem);
                    BigDecimal yb = (cbLong.compareTo(caLong) > 0 ? cbLong : caLong).add(margem);
                    condicoesOr.addParametro(new ParametroPesquisaAnd(new ParametroPesquisa[]{new ParametroPesquisaEntre("latitude", xa, xb), new ParametroPesquisaEntre("longitude", ya, yb)}));
                }
            }

            params.add(condicoesOr);
        }

        return this.pesquisar((ParametroOrdenacaoColuna)null, params.toArray(new ParametroPesquisa[params.size()]));
    }

	@Override
    public List<Frota> buscarFrotasComMultiplosMotivosAlteracaoVigentes() {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        return pesquisar(null, CONSULTA_FROTAS_MULTIPLOS_MOTIVOS, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }
}
