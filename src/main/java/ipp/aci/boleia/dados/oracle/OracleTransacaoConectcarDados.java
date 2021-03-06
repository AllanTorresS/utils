package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITransacaoConectcarDados;
import ipp.aci.boleia.dominio.TransacaoConectcar;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusNotaFiscal;
import ipp.aci.boleia.dominio.enums.TipoTransacaoConectcar;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.vo.DiaValePedagioVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaExtratoValePedagioVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaUtilizacaoTagVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Respositorio de entidades AutorizacaoPagamento Consolidada
 */
@Repository
public class OracleTransacaoConectcarDados extends OracleRepositorioBoleiaDados<TransacaoConectcar>
        implements ITransacaoConectcarDados {

    private static final String CONSULTA_CONSOLIDADO_SEM_COBRANCA =
            " select t " +
                    " from TransacaoConectcar t " +
                    "     join FETCH t.frota ft " +
                    " where " +
                    "     t.cobranca is null " +
                    "     and t.dataFimPeriodo < :hoje " +
                    "     and (t.dataFimViagem is null or t.dataFimViagem < SYSDATE) " +
                    " order by t.dataFimPeriodo";

    private static final String CONSULTA_CONSOLIDADO_SEM_REEMBOLSO =
            " SELECT t " +
                    " FROM TransacaoConectcar t " +
                    "     JOIN FETCH t.frota fr  " +
                    " WHERE " +
                    "     t.reembolso IS NULL " +
                    "     AND t.dataFimPeriodo < :primeiroDiaDoMes " +
                    "     AND (t.dataFimViagem is null or t.dataFimViagem < SYSDATE)";

    private static final String CONSULTA_PESQUISA_GRID =
            "SELECT TC " +
                    " FROM TransacaoConectcar TC " +
                    "   LEFT JOIN TC.frota F " +
                    " WHERE (TC.dataInicioPeriodo <= :dataFimPeriodo OR :dataFimPeriodo is null) " +
                    "   AND (TC.dataFimPeriodo >= :dataInicioPeriodo OR :dataInicioPeriodo is null) " +
                    "   AND (F.id = :idFrota OR :idFrota is null) " +
                    "   AND (TC.empresaAgregada.id = :idEmpresaAgregada OR :idEmpresaAgregada is null) " +
                    "   AND (TC.unidade.id = :idUnidade OR :idUnidade is null) " +
                    "   %s " +
                    "   %s " +
                    " ORDER BY " +
                    "   TC.dataFimPeriodo";

    private static final String CONSULTA_PESQUISA_DETALHE_VALE_PEDAGIO =
            "SELECT TC " +
                    " FROM TransacaoConectcar TC " +
                    "   LEFT JOIN TC.frota F " +
                    " WHERE TC.dataTransacao BETWEEN :inicioDia AND :fimDia " +
                    "   AND F.id = :idFrota " +
    				"   AND TC.tipoTransacao IN (7, 10)";

    private static final String QUERY_VALOR_UTILIZADO =
            "SELECT NVL(SUM(tc.valorTotal),0) " +
                    " FROM TransacaoConectcar tc " +
                    " LEFT JOIN tc.cobranca c " +
                    "WHERE tc.frota.id  = :idFrota " +
                    "  AND (tc.dataFimViagem < SYSDATE OR tc.tipoTransacao NOT IN (7,8,9,10))" + 
                    "  AND c.dataPagamento is null";

    private static final String QUERY_CREDITO_VALE_PEDAGIO =
            "SELECT NVL(SUM(tc.valorTotal),0) * -1 " +
                    " FROM TransacaoConectcar tc " +
                    " LEFT JOIN tc.cobranca c " +
                    "WHERE tc.frota.id  = :idFrota " +
                    "  AND tc.tipoTransacao IN (7,8,9,10)" +
                    "  AND tc.dataFimViagem > SYSDATE" +
                    "  AND c.dataPagamento is null";

    private static final String QUERY_ULTIMA_TRANSACAO =
            "SELECT tc " +
                    " FROM TransacaoConectcar tc " +
                    "WHERE tc.frota.id  = :idFrota " +
                    " ORDER BY tc.id DESC";

    private static final String QUERY_PRIMEIRA_TRANSACAO =
            "SELECT tc " +
                    " FROM TransacaoConectcar tc " +
                    " WHERE tc.frota.id = :idFrota " +
                    " ORDER BY tc.dataTransacao ASC";

    private static final String QUERY_VALE_PEDAGIO_ATIVO =
            "SELECT tc " +
                    " FROM TransacaoConectcar tc " +
                    " WHERE tc.frota.id = :idFrota " +
                    " AND tc.tipoTransacao = 7" +
                    " AND TRUNC(tc.dataInicioViagem) <= TRUNC(SYSDATE)" +
                    " AND TRUNC(tc.dataFimViagem) >= TRUNC(SYSDATE)" +
    		        " AND tc.codigoViagem NOT IN (" +
    		 		"      SELECT tc1.codigoViagem" +
    				"	   FROM TransacaoConectcar tc1" +
    				"	   WHERE tc1.tipoTransacao = 10 AND tc1.frota.id = tc.frota.id AND tc1.codigoViagem = tc.codigoViagem)";

    private static final String QUERY_VALE_PEDAGIO_TROCANDO_STATUS =
            "SELECT tc " +
                    " FROM TransacaoConectcar tc " +
            		" LEFT JOIN FETCH tc.frota " +
            		" LEFT JOIN FETCH tc.tag " +
                    " WHERE tc.tipoTransacao = 7" +
                    " AND (TRUNC(tc.dataInicioViagem) = TRUNC(SYSDATE)" +
                    " OR TRUNC(tc.dataFimViagem) = TRUNC(SYSDATE) - 1)" +
                    " AND tc.codigoViagem NOT IN (" +
    		 		"      SELECT tc1.codigoViagem" +
    				"	   FROM TransacaoConectcar tc1" +
    				"	   WHERE tc1.tipoTransacao = 10 AND tc1.frota.id = tc.frota.id AND tc1.codigoViagem = tc.codigoViagem)";

    private static final String QUERY_TRANSACOES_DETALHE_COBRANCA =
            "SELECT tc " +
                    " FROM TransacaoConectcar tc " +
                    " WHERE tc.cobranca.id = :idCobranca " +
                    " ORDER BY tc.dataTransacao ASC";

    private static final String QUERY_EXTRATO_VALE_PEDAGIO = 
    		"SELECT new ipp.aci.boleia.dominio.vo.DiaValePedagioVo(TRUNC(tc.dataTransacao) as dia, " +
    		"tc.valorTotal as valorTotalTransacao, tc.tipoTransacao as tipoTransacao,c.valorMensalidade as valorMensalidade," +
            "c.valorTotal as valorTotalCobranca, c.dataPagamento as dataPagamentoCobranca,tc.dataTransacao as dataTransacao)" +
            "FROM TransacaoConectcar as tc LEFT JOIN tc.cobranca as c "  +
    		"WHERE tc.frota.id = :idFrota " + 
    		"AND tc.dataTransacao BETWEEN :dataInicioPeriodo AND :dataFimPeriodo " + 
    		"ORDER BY tc.dataTransacao DESC";

    private static final String QUERY_TODAS_TRANSACOES_POR_FROTA=
            "SELECT new ipp.aci.boleia.dominio.vo.DiaValePedagioVo(TRUNC(tc.dataTransacao) as dia, " +
                    "tc.valorTotal as valorTotalTransacao, tc.tipoTransacao as tipoTransacao,c.valorMensalidade as valorMensalidade," +
                    "c.valorTotal as valorTotalCobranca, c.dataPagamento as dataPagamentoCobranca,tc.dataTransacao as dataTransacao)" +
                    "FROM TransacaoConectcar as tc LEFT JOIN tc.cobranca as c "  +
                    "WHERE tc.frota.id = :idFrota " +
                    "ORDER BY tc.dataTransacao DESC";

    private static final String QUERY_PRIMEIRA_TRANSACAO_SEM_COBRANCA_DA_FROTA =
            "SELECT tc " +
                    " FROM TransacaoConectcar tc " +
                    " WHERE tc.frota.id  = :idFrota " +
                    " AND tc.cobranca IS NULL " +
                    " ORDER BY tc.dataFimPeriodo ASC";

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Value("${frota.controle.cnpj}")
    private Long cnpjFrotaControle;

    /**
     * Instancia o repositorio
     */
    public OracleTransacaoConectcarDados() {
        super(TransacaoConectcar.class);
    }

    @Override
    public List<TransacaoConectcar> obterTransacoesPorCobranca(Long idCobranca) {
        StringBuilder queryTransacoesDetalheCobranca = new StringBuilder(QUERY_TRANSACOES_DETALHE_COBRANCA);
        return pesquisar(null, queryTransacoesDetalheCobranca.toString(), new ParametroPesquisaIgual("idCobranca", idCobranca)).getRegistros();
    }

    /**
     * Realiza a pesquisa de Transacoes Consolidadas atrav??s de um filtor
     *
     * @param filtro        par??metros utilizados na consulta
     * @param usuarioLogado usu??rio que est?? realizando a consulta
     * @return retorna o resultado paginado da consulta
     */
    @Override
    public ResultadoPaginado<TransacaoConectcar> pesquisar(FiltroPesquisaTransacaoConsolidadaVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = criarParametrosPesquisaGrid(filtro, usuarioLogado);
        String filtroFrotaControle = "";
        if (usuarioLogado.getFrota() == null || !usuarioLogado.getFrota().getCnpj().equals(cnpjFrotaControle)) {
            filtroFrotaControle = "AND FR.frota.cnpj != " + cnpjFrotaControle + " ";
        }

        String consultaPesquisa = String.format(CONSULTA_PESQUISA_GRID, filtroFrotaControle);
        return pesquisar(filtro.getPaginacao(), consultaPesquisa, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Cria uma lista de parametros para a montagem da consulta de transacoes a ser exibida no grid
     *
     * @param filtro        O filtro informado pelo usuario
     * @param usuarioLogado usu??rio que est?? realizando a consulta
     * @return Uma lista de parametros
     */
    private List<ParametroPesquisa> criarParametrosPesquisaGrid(FiltroPesquisaTransacaoConsolidadaVo filtro, Usuario usuarioLogado) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        ParametroPesquisaIgual parametroDe = new ParametroPesquisaIgual("dataInicioPeriodo", null);
        ParametroPesquisaIgual parametroAte = new ParametroPesquisaIgual("dataFimPeriodo", null);
        ParametroPesquisaIgual parametroPV = new ParametroPesquisaIgual("idPontoVenda", null);
        ParametroPesquisaIgual parametroEmpresaAgregada = new ParametroPesquisaIgual("idEmpresaAgregada", null);
        ParametroPesquisaIgual parametroUnidade = new ParametroPesquisaIgual("idUnidade", null);
        ParametroPesquisaIgual parametroFrota = criarParametroFrota(filtro, usuarioLogado);

        if (filtro.getDe() != null) {
            parametroDe = new ParametroPesquisaIgual("dataInicioPeriodo", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe()));
        }

        if (filtro.getAte() != null) {
            parametroAte = new ParametroPesquisaIgual("dataFimPeriodo", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte()));
        }

        if (filtro.getEmpresaAgregada() != null && filtro.getEmpresaAgregada().getId() != null) {
            parametroEmpresaAgregada = new ParametroPesquisaIgual("idEmpresaAgregada", filtro.getEmpresaAgregada().getId());
        }

        if (filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            parametroUnidade = new ParametroPesquisaIgual("idUnidade", filtro.getUnidade().getId());
        }

        parametros.add(parametroDe);
        parametros.add(parametroAte);
        parametros.add(parametroFrota);
        parametros.add(parametroPV);
        parametros.add(parametroEmpresaAgregada);
        parametros.add(parametroUnidade);

        if (filtro.getNotaFiscal() != null && filtro.getNotaFiscal().trim().length() > 0) {
            povoarParametroIgual("notaFiscal", filtro.getNotaFiscal().toLowerCase(), parametros);

            ParametroPesquisaIgual parametroNumeroSerie = new ParametroPesquisaIgual("numeroSerie", null);
            if (filtro.getNumeroSerie() != null && filtro.getNumeroSerie().trim().length() > 0) {
                parametroNumeroSerie = new ParametroPesquisaIgual("numeroSerie", filtro.getNumeroSerie().toLowerCase());
            }
            parametros.add(parametroNumeroSerie);
        }

        return parametros;
    }

    /**
     * Cria um parametro para comparacao de frota a ser injetado na consulta
     *
     * @param filtro O filtro de pesquisa
     * @return O parametro para a consulta
     */
    private ParametroPesquisaIgual criarParametroFrota(FiltroPesquisaTransacaoConsolidadaVo filtro, Usuario usuarioLogado) {
        ParametroPesquisaIgual parametroFrota = new ParametroPesquisaIgual("idFrota", null);
        if (usuarioLogado.isFrotista()) {
            parametroFrota = new ParametroPesquisaIgual("idFrota", usuarioLogado.getFrota().getId());
        } else if (filtro.getFrota() != null && filtro.getFrota().getId() > 0) {
            parametroFrota = new ParametroPesquisaIgual("idFrota", filtro.getFrota().getId());
        }
        return parametroFrota;
    }

    /**
     * Cria um parametro para comparacao de frota a ser injetado na consulta
     *
     * @param filtro O filtro de pesquisa
     * @return O parametro para a consulta
     */
    private ParametroPesquisaIgual criarParametroFrota(FiltroPesquisaExtratoValePedagioVo filtro, Usuario usuarioLogado) {
        ParametroPesquisaIgual parametroFrota = new ParametroPesquisaIgual("idFrota", null);
        if (usuarioLogado.isFrotista()) {
            parametroFrota = new ParametroPesquisaIgual("idFrota", usuarioLogado.getFrota().getId());
        } else if (filtro.getFrota() != null && filtro.getFrota().getId() > 0) {
            parametroFrota = new ParametroPesquisaIgual("idFrota", filtro.getFrota().getId());
        }
        return parametroFrota;
    }

    @Override
    public List<TransacaoConectcar> obterConsolidacoesSemNotaFiscalEntreDatas(
            Date dataIntervaloMin, Date dataIntervaloMax) {
        ParametroPesquisa[] parametros = new ParametroPesquisa[]{
                new ParametroPesquisaIgual("statusNotaFiscal", StatusNotaFiscal.PENDENTE.getValue()),
                new ParametroPesquisaDataMaiorOuIgual("dataPrazoEmissaoNfe", dataIntervaloMin),
                new ParametroPesquisaDataMenorOuIgual("dataPrazoEmissaoNfe", dataIntervaloMax)
        };
        return pesquisar((ParametroOrdenacaoColuna) null, parametros);
    }

    @Override
    public List<TransacaoConectcar> obterConsolidacoesComCicloAbastecimentoEncerrado(
            Date dataIntervaloMin, Date dataIntervaloMax) {
        ParametroPesquisa[] parametros = new ParametroPesquisa[]{
                new ParametroPesquisaDataMaiorOuIgual("dataFimPeriodo", dataIntervaloMin),
                new ParametroPesquisaDataMenorOuIgual("dataFimPeriodo", dataIntervaloMax)
        };
        return pesquisar((ParametroOrdenacaoColuna) null, parametros);
    }

    @Override
    public List<TransacaoConectcar> obterTransacoesSemCobranca() {
        return pesquisarSemIsolamentoDados(null, CONSULTA_CONSOLIDADO_SEM_COBRANCA, new ParametroPesquisaIgual("hoje", obterDataHoje())).getRegistros();
    }

    @Override
    public List<TransacaoConectcar> obterTransacoesSemReembolso() {
        return pesquisarSemIsolamentoDados(null, CONSULTA_CONSOLIDADO_SEM_REEMBOLSO, new ParametroPesquisaIgual("primeiroDiaDoMes", new Date())).getRegistros();
    }

    /**
     * Obtem a primeira hora do dia de hoje
     *
     * @return O dia de hoje, as 0h da manha
     */
    private Date obterDataHoje() {
        return UtilitarioCalculoData.obterPrimeiroInstanteDia(ambiente.buscarDataAmbiente());
    }

    @Override
    public TransacaoConectcar obterTransacoesPorIdConectcar(Long codigoTransacaoConectcar) {
        ParametroPesquisa[] parametros = new ParametroPesquisa[]{
                new ParametroPesquisaIgual("codigoTransacaoConectcar", codigoTransacaoConectcar)
        };
        return pesquisarUnico(parametros);
    }

    @Override
    public BigDecimal obterValorUtilizadoCiclo(Long idFrota) {
        StringBuilder query = new StringBuilder(QUERY_VALOR_UTILIZADO);

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idFrota", idFrota));

        return pesquisarUnicoSemIsolamentoDados(query.toString(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public BigDecimal obterCreditoValePedagio(Long idFrota) {
        StringBuilder query = new StringBuilder(QUERY_CREDITO_VALE_PEDAGIO);

        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idFrota", idFrota));

        return pesquisarUnicoSemIsolamentoDados(query.toString(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<TransacaoConectcar> pesquisarUtilizacaoTag(
            FiltroPesquisaUtilizacaoTagVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIn("tipoTransacao", Arrays.asList(new Integer[] {
        		TipoTransacaoConectcar.RECARGA_VALE_PEDAGIO.getValue(), 
        		TipoTransacaoConectcar.CANCELAMENTO_VALE_PEDAGIO.getValue()
        }), true));

        if (filtro.getFrota() != null) {
            parametros.add(new ParametroPesquisaIgual("frota.id", filtro.getFrota().getId()));
        }

        if (filtro.getDe() != null) {
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataTransacao", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
        }

        if (filtro.getAte() != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataTransacao", UtilitarioCalculoData.obterUltimoInstanteDia((filtro.getAte()))));
        }

        if (filtro.getTag() != null) {
            parametros.add(new ParametroPesquisaIgual("tag.id", filtro.getTag()));
        }

        if (filtro.getPlaca() != null && !"".equals(filtro.getPlaca())) {
            parametros.add(new ParametroPesquisaIgualIgnoreCase("placa", filtro.getPlaca()));
        }

        if (filtro.getTipo() != null && filtro.getTipo().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("tipoTransacao", TipoTransacaoConectcar.valueOf(filtro.getTipo().getName()).getValue()));
        }

        if (filtro.getReembolso() != null && filtro.getReembolso().getId() != null) {
            parametros.add(new ParametroPesquisaIgual("reembolso.id", filtro.getReembolso().getId()));
        }

        if (filtro.getStatusTag() != null && filtro.getStatusTag().getName() != null) {
            if (filtro.getStatusTag().getName().equals(StatusAtivacao.ATIVO.name())) {
                parametros.add(new ParametroPesquisaNulo("tag.dataAtivacao", true));
            } else if (filtro.getStatusTag().getName().equals(StatusAtivacao.INATIVO.name())) {
                parametros.add(new ParametroPesquisaNulo("tag.dataBloqueio", true));
            }
        }

        if (filtro.getPaginacao() != null && CollectionUtils.isNotEmpty(filtro.getPaginacao().getParametrosOrdenacaoColuna())) {
            ParametroOrdenacaoColuna parametro = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0);
            String nomeOrdenacao = parametro.getNome();
            if (nomeOrdenacao != null) {
                if (nomeOrdenacao.contentEquals("tag")) {
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().remove(0);
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("tag.id", parametro.getSentidoOrdenacao()));
                } else if (nomeOrdenacao.contentEquals("statusTag")) {
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().remove(0);
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("tag.dataBloqueio", parametro.getSentidoOrdenacao()));
                } else if (nomeOrdenacao.contentEquals("tipo")) {
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().remove(0);
                    filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("tipoTransacao", parametro.getSentidoOrdenacao()));
                }
            }
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public TransacaoConectcar obterUltimaTransacaoPorFrota(Long idFrota) {

        Query query = getGerenciadorDeEntidade().createQuery(QUERY_ULTIMA_TRANSACAO);
        query.setParameter("idFrota", idFrota);
        query.setMaxResults(1);

        try {
            return (TransacaoConectcar) query.getResultList().get(0);
        } catch (NoResultException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    @Override
    public TransacaoConectcar obterPrimeiraTransacaoPorFrota(Long idFrota) {
        Query query = getGerenciadorDeEntidade().createQuery(QUERY_PRIMEIRA_TRANSACAO);
        query.setParameter("idFrota", idFrota);
        query.setMaxResults(1);

        try {
            return (TransacaoConectcar) query.getResultList().get(0);
        } catch (NoResultException | IndexOutOfBoundsException e) {
            return null;
        }
    }
    
    @Override
    public ResultadoPaginado<DiaValePedagioVo> obterExtratoValePedagio(FiltroPesquisaExtratoValePedagioVo filtro, Usuario usuarioLogado) {
    	
        List<ParametroPesquisa> parametros = new ArrayList<>();

        ParametroPesquisaIgual parametroDe = new ParametroPesquisaIgual("dataInicioPeriodo", null);
        ParametroPesquisaIgual parametroAte = new ParametroPesquisaIgual("dataFimPeriodo", null);
        ParametroPesquisaIgual parametroFrota = criarParametroFrota(filtro, ambiente.getUsuarioLogado());

        if (filtro.getDe() != null) {
            parametroDe = new ParametroPesquisaIgual("dataInicioPeriodo", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe()));
        }

        if (filtro.getAte() != null) {
            parametroAte = new ParametroPesquisaIgual("dataFimPeriodo", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte()));
        }

        parametros.add(parametroDe);
        parametros.add(parametroAte);
        parametros.add(parametroFrota);
    	
        String ordenacao = criarParametroOrdenacao(filtro.getPaginacao() != null ? filtro.getPaginacao().getParametrosOrdenacaoColuna() : null);
        String consultaPesquisa = String.format(QUERY_EXTRATO_VALE_PEDAGIO, ordenacao);
        
    	return pesquisar(null, consultaPesquisa, DiaValePedagioVo.class, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Realiza a pesquisa de Transacoes  atrav??s de um filtor
     *
     * @param filtro        par??metros utilizados na consulta
     * @return retorna o resultado paginado da consulta
     */
    @Override
    public ResultadoPaginado<DiaValePedagioVo> obterTodasAsTransacoesPorFrota(FiltroPesquisaExtratoValePedagioVo filtro, Usuario usuarioLogado) {

        List<ParametroPesquisa> parametros = new ArrayList<>();

        ParametroPesquisaIgual parametroFrota = criarParametroFrota(filtro, ambiente.getUsuarioLogado());

        parametros.add(parametroFrota);

        String ordenacao = criarParametroOrdenacao(filtro.getPaginacao() != null ? filtro.getPaginacao().getParametrosOrdenacaoColuna() : null);
        String consultaPesquisa = String.format(QUERY_TODAS_TRANSACOES_POR_FROTA, ordenacao);

        return pesquisar(null, consultaPesquisa, DiaValePedagioVo.class, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    /**
     * Realiza a pesquisa de Transacoes Consolidadas atrav??s de um filtor
     *
     * @param filtro        par??metros utilizados na consulta
     * @return retorna o resultado paginado da consulta
     */
    @Override
    public List<TransacaoConectcar> obterDetalheValePedagio(FiltroPesquisaExtratoValePedagioVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaIgual("inicioDia", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
        parametros.add(new ParametroPesquisaIgual("fimDia", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));
        parametros.add(new ParametroPesquisaIgual("idFrota", filtro.getFrota().getId()));

        return pesquisar(null, CONSULTA_PESQUISA_DETALHE_VALE_PEDAGIO, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    /**
     * Cria o par??metro de ordena????o para extrato Vale Ped??gio
     *
     * @param parametrosOrdenacaoColuna Os crit??rios de ordena????o do resultado definidos pelo usu??rio
     * @return O parametro de ordenacao criado.
     */
    private String criarParametroOrdenacao(List<ParametroOrdenacaoColuna> parametrosOrdenacaoColuna) {
        if (parametrosOrdenacaoColuna != null && !parametrosOrdenacaoColuna.isEmpty()) {
            return parametrosOrdenacaoColuna.get(0).getNome() + " " + (parametrosOrdenacaoColuna.get(0).isDecrescente() ? "DESC" : "ASC");
        }
        return "dia DESC";
    }

    @Override
    public List<TransacaoConectcar> obterValesPedagioAtivos(Long idFrota) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual("idFrota", idFrota));

        return pesquisar(null, QUERY_VALE_PEDAGIO_ATIVO, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    @Override
    public List<TransacaoConectcar> obterValesPedagioTrocandoStatus() {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        return pesquisar(null, QUERY_VALE_PEDAGIO_TROCANDO_STATUS, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }

    /**
     * Realiza a pesquisa da primeira transacao sem cobranca atrav??s de um filtro
     *
     * @param idFrota Identificador da frota.
     * @return a ultima transacao sem cobranca
     */
    public TransacaoConectcar obterPrimeiraTransacaoSemCobrancaPorFrota(Long idFrota) {
        Query query = getGerenciadorDeEntidade().createQuery(QUERY_PRIMEIRA_TRANSACAO_SEM_COBRANCA_DA_FROTA);
        query.setParameter("idFrota", idFrota);
        query.setMaxResults(1);

        try {
            return (TransacaoConectcar) query.getResultList().get(0);
        } catch (NoResultException | IndexOutOfBoundsException e) {
            return null;
        }
    }
}