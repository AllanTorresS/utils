package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IRepositorioBoleiaDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogicaComData;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceMotorista;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import ipp.aci.boleia.dominio.pesquisa.comum.GrupoParametrosPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEmpty;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEntre;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMultiJoin;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.EntidadeVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import ipp.aci.boleia.util.seguranca.UtilitarioIsolamentoInformacoes;
import ipp.aci.pdsiframework.dados.oracle.OracleRepositorioGenericoDados;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CommonAbstractCriteria;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.PluralJoin;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;
import javax.persistence.criteria.Subquery;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

/**
 * Classe base para implementacao de repositorios de entidades. Extende
 * o repositorio generico do Framework Ipiranga, adicionando novas
 * funcionalidades.
 *
 * @param <T> A classe das entidades a serem manipuladas pelo repositorio
 */
public abstract class OracleRepositorioBoleiaDados<T extends IPersistente>
        extends OracleRepositorioGenericoDados<T, Long> implements IRepositorioBoleiaDados<T> {

    private final Class<T> classeAlvo;
    private static final Logger LOGGER = LoggerFactory.getLogger(OracleRepositorioBoleiaDados.class);

    private static final String LOWER_FUNCTION = "LOWER";
    private static final String LOWER_FUNCTION_CALL = LOWER_FUNCTION + "(%s)";
    private static final String TRANSLATE_FUNCTION = "TRANSLATE";
    private static final String TRANSLATE_ACCENTS_FROM = "'áàâãäéèêëíìïóòôõöúùûüÁÀÂÃÄÉÈÊËÍÌÏÓÒÔÕÖÚÙÛÜçÇ'";
    private static final String TRANSLATE_ACCENTS_TO = "'aaaaaeeeeiiiooooouuuuAAAAAEEEEIIIOOOOOUUUUcC'";
    private static final String TRANSLATE_FUNCTION_CALL = TRANSLATE_FUNCTION + "(%s, " + TRANSLATE_ACCENTS_FROM + "," + TRANSLATE_ACCENTS_TO + ")";

    private static final Map<String, Boolean> CACHE_RELACIONAMENTOS_MANY = new HashMap<>();
    private static final Map<String, Boolean> CACHE_RELACOES = new HashMap<>();
    private static final Lock LOCK = new ReentrantLock();

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Instancia o repositorio
     *
     * @param classe A classe da entidade alvo
     */
    public OracleRepositorioBoleiaDados(Class<T> classe) {
        super(classe);
        this.classeAlvo = classe;
    }

    @Override
    public T obterPorIdSemIsolamento(Long id) {
        return obterPorId(id, false);
    }

    @Override
    public T obterPorId(Long id) {
        return obterPorId(id, true);
    }

    /**
     * Obtem entidade pelo identificador
     *
     * @param id identificador da entidade
     * @param comIsolamento flag de isolamento
     * @return entidade obtida
     */
    private T obterPorId(Long id, boolean comIsolamento) {
        if (id == null) {
            return null;
        }
        T t = super.obterPorId(id);
        if (t instanceof IExclusaoLogica) {
            Boolean excluido = ((IExclusaoLogica) t).getExcluido();
            if (excluido != null && excluido) {
                return null;
            }
        }
        if (t instanceof IExclusaoLogicaComData) {
            Boolean excluido = ((IExclusaoLogicaComData) t).getExcluido();
            if (excluido != null && excluido) {
                return null;
            }
        }
        if (comIsolamento) {
            UtilitarioIsolamentoInformacoes.exigirPermissaoAcesso(t, ambiente.getUsuarioLogado());
        }
        return t;
    }

    @Override
    public T armazenar(T t) {
        return armazenar(t, true);
    }

    @Override
    public T armazenarSemIsolamentoDeDados(T t) {
        return armazenar(t, false);
    }

    /**
     * Armazena uma entidade no banco de dados
     *
     * @param t                  A entidade
     * @param comIsolamentoDados True caso se deseje isolamento de dados
     * @return A entidade recem armazenada
     */
    private T armazenar(T t, boolean comIsolamentoDados) {
        try {
            if (comIsolamentoDados) {
                UtilitarioIsolamentoInformacoes.exigirPermissaoAcesso(t, ambiente.getUsuarioLogado());
            }
            if (t instanceof IExclusaoLogica) {
                ((IExclusaoLogica) t).setExcluido(false);
            }
            if (t instanceof IExclusaoLogicaComData) {
                ((IExclusaoLogicaComData) t).setExcluido(false);
            }
            if (t != null && t.getId() != null && t.getId() > 0) {
                t = getGerenciadorDeEntidade().merge(t);
            } else {
                getGerenciadorDeEntidade().persist(t);
            }
            getGerenciadorDeEntidade().flush();
            return t;
        } catch (OptimisticLockException e) {
            throw new ExcecaoBoleiaRuntime(Erro.ALTERAR_REGISTRO_DESATUALIZADO, e);
        }
    }

    @Override
    public List<T> armazenarLista(List<T> list) {
        List<T> resultado = new ArrayList<>();
        try {
            for (T t : list) {
                UtilitarioIsolamentoInformacoes.exigirPermissaoAcesso(t, ambiente.getUsuarioLogado());
                if (t instanceof IExclusaoLogica) {
                    ((IExclusaoLogica) t).setExcluido(false);
                }
                if (t instanceof IExclusaoLogicaComData) {
                    ((IExclusaoLogicaComData) t).setExcluido(false);
                }
                if (t != null && t.getId() != null && t.getId() > 0) {
                    t = getGerenciadorDeEntidade().merge(t);
                } else {
                    getGerenciadorDeEntidade().persist(t);
                }
                resultado.add(t);
            }
            getGerenciadorDeEntidade().flush();
            return resultado;
        } catch (OptimisticLockException e) {
            throw new ExcecaoBoleiaRuntime(Erro.ALTERAR_REGISTRO_DESATUALIZADO, e);
        }
    }


    @Override
    public void excluir(Long... ids) {
        excluirPorIds(true, ids);
    }

    @Override
    public void excluirSemIsolamentoDeDados(Long... ids) {
        excluirPorIds(false, ids);
    }

    /**
     * Remove uma ou mais entidades pelo seus ids
     *
     * @param ids                os identificadores dos registros a serem removidos
     * @param comIsolamentoDados True caso se deseje isolamento de dados
     */
    private void excluirPorIds(Boolean comIsolamentoDados, Long... ids) {
        for (Long id : ids) {
            T t = getGerenciadorDeEntidade().find(getClassePersistente(), id);
            if (t == null) {
                throw new ExcecaoBoleiaRuntime(Erro.REGISTRO_INEXISTENTE);
            }
            if (comIsolamentoDados) {
                UtilitarioIsolamentoInformacoes.exigirPermissaoAcesso(t, ambiente.getUsuarioLogado());
            }
            if (t instanceof IExclusaoLogica) {
                ((IExclusaoLogica) t).setExcluido(true);
                getGerenciadorDeEntidade().merge(t);
            } else if (t instanceof IExclusaoLogicaComData) {
                ((IExclusaoLogicaComData) t).setExcluido(true);
                ((IExclusaoLogicaComData) t).setDataExclusao(new Date());
                getGerenciadorDeEntidade().merge(t);
            } else {
                getGerenciadorDeEntidade().remove(t);
            }
        }
        getGerenciadorDeEntidade().flush();
    }

    @Override
    public List<T> obterTodosComRelacionamento(ParametroOrdenacaoColuna ordenacao, String... relacionamentos) {
        ParametroPesquisaFetch[] params = new ParametroPesquisaFetch[relacionamentos.length];
        for (int i = 0; i < relacionamentos.length; i++) {
            params[i] = new ParametroPesquisaFetch(relacionamentos[i]);
        }
        return pesquisar(ordenacao, params);
    }

    @Override
    public List<T> obterTodos() {
        return obterTodos(null);
    }

    @Override
    public List<T> obterTodos(ParametroOrdenacaoColuna ordenacao) {
        List<ParametroOrdenacaoColuna> ordenacoes = ordenacao != null ? Collections.singletonList(ordenacao) : null;
        return converterParaEntidade(criarConsultaSemPaginacao(ordenacoes, true, new HashMap<>()).getResultList(), true);
    }

    /**
     * Obtem o total de registros para os dados parametros
     *
     * @param parametros parametros de consulta
     * @return Total de registros no banco
     */
    protected Long pesquisarTotalRegistros(ParametroPesquisa... parametros) {
        return pesquisarTotalRegistros(true, parametros);
    }

    /**
     * Altera o estado da entidade para desanexado
     *
     * @param entidade entidade transiente
     * @return A entidade desanexada
     */
    protected T desanexar(T entidade) {
        getGerenciadorDeEntidade().detach(entidade);
        return entidade;
    }

    /**
     * Realiza uma pesquisa com paginação de uma servicos a partir dos parametros informados
     *
     * @param paginacao  Os dados da paginacao
     * @param parametros Os parametros da busca
     * @return O resultado da consulta, contendo os dados de paginacao
     */
    protected ResultadoPaginado<T> pesquisar(InformacaoPaginacao paginacao, ParametroPesquisa... parametros) {
        return pesquisar(paginacao, true, parametros);
    }

    /**
     * Realiza uma pesquisa ordenada sem paginação a partir dos parametros informados.
     *
     * @param orderBy    A coluna de ordenacao
     * @param parametros Os parametros da busca
     * @return Uma lista de entidades localizadas
     */
    protected List<T> pesquisar(ParametroOrdenacaoColuna orderBy, ParametroPesquisa... parametros) {
        return pesquisar(orderBy, true, parametros);
    }

    /**
     * Realiza uma pesquisa ordenada sem paginação a partir dos parametros informados.
     *
     * @param orderBy    A coluna de ordenacao
     * @param parametros Os parametros da busca
     * @return Uma lista de entidades localizadas
     */
    protected List<T> pesquisarSemIsolamentoDados(ParametroOrdenacaoColuna orderBy, ParametroPesquisa... parametros) {
        return pesquisar(orderBy, false, parametros);
    }

    /**
     * Realiza uma pesquisa com paginação de uma servicos a partir dos parametros informados sem isolar dados
     *
     * @param paginacao  Os dados da paginacao
     * @param parametros Os parametros da busca
     * @return O resultado da consulta, contendo os dados de paginacao
     */
    protected ResultadoPaginado<T> pesquisarSemIsolamentoDados(InformacaoPaginacao paginacao, ParametroPesquisa... parametros) {
        return pesquisar(paginacao, false, parametros);
    }

    /**
     * Realiza uma pesquisa com paginação de uma servicos a partir de uma queryString
     *
     * @param paginacao   Os dados da paginacao
     * @param queryString A consulta em HQL
     * @param parametros  Os parametros da busca
     * @return O resultado da consulta, contendo os dados de paginacao
     */
    protected ResultadoPaginado<T> pesquisar(InformacaoPaginacao paginacao, String queryString, ParametroPesquisa... parametros) {
        return pesquisar(paginacao, queryString, true, getClassePersistente(), parametros);
    }

    /**
     * Realiza uma pesquisa com paginação de uma servicos a partir de uma queryString
     *
     * @param paginacao   Os dados da paginacao
     * @param queryString A consulta em HQL
     * @param tipoResultado A classe do tipo do objeto de retorno
     * @param parametros  Os parametros da busca
     * @param <K> O tipo do objeto de retorno
     * @return O resultado da consulta, contendo os dados de paginacao
     */
    protected <K> ResultadoPaginado<K> pesquisar(InformacaoPaginacao paginacao, String queryString, Class<K> tipoResultado, ParametroPesquisa... parametros) {
        return pesquisar(paginacao, queryString, true, tipoResultado, parametros);
    }

    /**
     * Aplica paginação em uma lista de registros obtidos no banco.
     *
     * @param paginacao Informação de paginação
     * @param totalRegistros Lista de registros obtidos.
     * @param <K> Tipo de objeto da lista
     * @return Lista paginada.
     */
    private <K> List<K> aplicarPaginacaoListaRegistros(InformacaoPaginacao paginacao, List<K> totalRegistros) {
        List<K> registros = new ArrayList<>();
        if (paginacao.getPagina() != null && paginacao.getTamanhoPagina() != null) {
            int first = (paginacao.getPagina() - 1) * paginacao.getTamanhoPagina();
            int last = (first + paginacao.getTamanhoPagina() < totalRegistros.size()) ? first + paginacao.getTamanhoPagina() : totalRegistros.size();
            if (last > first) {
                registros.addAll(totalRegistros.subList(first, last));
            }
        } else {
            registros.addAll(totalRegistros);
        }
        return registros;
    }

    /**
     * Realiza uma pesquisa com paginação de uma servicos a partir de uma queryString sem isolar dados
     *
     * @param paginacao   Os dados da paginacao
     * @param queryString A consulta em HQL
     * @param parametros  Os parametros da busca
     * @return O resultado da consulta, contendo os dados de paginacao
     */
    protected ResultadoPaginado<T> pesquisarSemIsolamentoDados(InformacaoPaginacao paginacao, String queryString, ParametroPesquisa... parametros) {
        return pesquisar(paginacao, queryString, false, getClassePersistente(), parametros);
    }

    /**
     * Realiza uma pesquisa retornando apenas um registro. Caso muitos sejam encontrados, lanca erro.
     *
     * @param parametros Os parametros da busca
     * @return A entidade localizada
     */
    protected T pesquisarUnico(ParametroPesquisa... parametros) {
        try {
            return obterEntidadeObjetoResultado(criarConsultaSemPaginacao(true, parametros).getSingleResult(), true);
        } catch (NoResultException e) {
            LOGGER.trace(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Realiza uma pesquisa retornando apenas um registro sem isolamento de dados. Caso muitos sejam encontrados, lanca erro.
     * @param parametros Os parametros da busca
     * @return A entidade localizada
     */
    protected T pesquisarUnicoSemIsolamentoDados(ParametroPesquisa... parametros) {
        try {
            return obterEntidadeObjetoResultado(criarConsultaSemPaginacao(false, parametros).getSingleResult(), false);
        } catch (NoResultException e) {
            LOGGER.trace(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Realiza uma pesquisa retornando apenas um registro. Caso muitos sejam encontrados, lanca erro.
     *
     * @param queryString Query em string
     * @param parametros  Os parametros da busca
     * @param <C> O tipo do objeto de retorno
     * @return O resultado localizado
     */
    protected <C> C pesquisarUnicoSemIsolamentoDados(String queryString, ParametroPesquisa... parametros) {
        Query query = criarConsultaComParametros(queryString, parametros);
        try {
            return (C) query.getSingleResult();
        } catch (NoResultException e) {
            LOGGER.trace(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Realiza uma pesquisa retornando apenas um registro. Caso muitos sejam encontrados, lanca erro.
     *
     * @param queryString Query em string
     * @param parametros  Os parametros da busca
     * @return O resultado localizado
     */
    protected T pesquisarUnico(String queryString, ParametroPesquisa... parametros) {
        Query query = criarConsultaComParametros(queryString, parametros);
        try {
            T result = (T) query.getSingleResult();
            UtilitarioIsolamentoInformacoes.exigirPermissaoAcesso(result, ambiente.getUsuarioLogado());
            return result;
        } catch (NoResultException e) {
            LOGGER.trace(e.getMessage(), e);
            return null;
        }
    }

    /**
     * Executa uma pesquisa com ou sem isolamento de dados a partir dos parametros informados
     *
     * @param paginacao          A paginacao desejada
     * @param comIsolamentoDados True caso se deseje isolamento de dados
     * @param parametros         Os parametros da consulta
     * @return O resultado da consulta
     */
    private ResultadoPaginado<T> pesquisar(InformacaoPaginacao paginacao, Boolean comIsolamentoDados, ParametroPesquisa... parametros) {
        ResultadoPaginado<T> resultado = new ResultadoPaginado<>();
        if (paginacao != null) {
            TypedQuery query = criarConsultaPorFiltro(paginacao, comIsolamentoDados, new HashMap<>(), parametros);
            TypedQuery<Long> count = criarConsultaTotalRegistros(comIsolamentoDados, parametros);
            resultado.setRegistros(converterParaEntidade(query.getResultList(), comIsolamentoDados));
            resultado.setTotalItems(count.getSingleResult().intValue());
        } else {
            List<T> registros = converterParaEntidade(criarConsultaSemPaginacao(comIsolamentoDados, parametros).getResultList(), comIsolamentoDados);
            resultado.setRegistros(registros);
            resultado.setTotalItems(registros.size());
        }
        return resultado;
    }

    /**
     * Executa uma pesquisa com ou sem isolamento de dados a partir dos parametros informados
     *
     * @param paginacao   A paginacao  desejada
     * @param queryString A consulta a ser executada
     * @param isolamento  True caso se deseje isolamento de dados
     * @param tipoRetorno O tipo de retorno da consulta
     * @param parametros  Os parametros da consulta
     * @return O resultado da consulta
     */
    private <K> ResultadoPaginado<K> pesquisar(InformacaoPaginacao paginacao, String queryString, Boolean isolamento, Class<K> tipoRetorno, ParametroPesquisa... parametros) {
        ResultadoPaginado<K> resultado = new ResultadoPaginado<>();
        if (paginacao != null) {
            Query query = criarConsultaComParametros(queryString, parametros);

            List<K> totalRegistros = query.getResultList();
            totalRegistros = removerRegistrosExcluidos(totalRegistros, tipoRetorno);

            List<K> registrosPaginados = aplicarPaginacaoListaRegistros(paginacao, totalRegistros);
            resultado.setRegistros(registrosPaginados);
            resultado.setTotalItems(totalRegistros.size());
        } else {
            List<K> registros = criarConsultaComParametros(queryString, parametros).getResultList();
            registros = removerRegistrosExcluidos(registros, tipoRetorno);
            resultado.setRegistros(registros);
            resultado.setTotalItems(registros.size());
        }
        if (isolamento && IPersistente.class.isAssignableFrom(tipoRetorno)) {
            UtilitarioIsolamentoInformacoes.exigirPermissaoAcesso((List<IPersistente>) resultado.getRegistros(), ambiente.getUsuarioLogado());
        }
        return resultado;
    }

    /**
     * Remove registros excluidos de query string
     *
     * @param totalRegistros todos registros encontrados
     * @param tipoRetorno    tipo dos registros
     * @param <K>            tipo registro clazz
     * @return Registros encontrados e não excluidos
     */
    private <K> List<K> removerRegistrosExcluidos(List<K> totalRegistros, Class<K> tipoRetorno) {
        if (IExclusaoLogica.class.isAssignableFrom(tipoRetorno)) {
            List<K> result = new ArrayList<>();
            for (K registro : totalRegistros) {
                if (!((IExclusaoLogica) registro).getExcluido()) {
                    result.add(registro);
                }
            }
            return result;
        } else if (IExclusaoLogicaComData.class.isAssignableFrom(tipoRetorno)) {
            List<K> result = new ArrayList<>();
            for (K registro : totalRegistros) {
                if (!((IExclusaoLogicaComData) registro).getExcluido()) {
                    result.add(registro);
                }
            }
            return result;
        } else {
            return totalRegistros;
        }
    }

    /**
     * Realiza uma pesquisa ordenada, sem paginacao, com isolamento de dados.
     *
     * @param orderBy    O criterio de  ordenacao
     * @param parametros Os parametros da consulta
     * @return O resultado da consulta
     */

    protected List<T> pesquisar(List<ParametroOrdenacaoColuna> orderBy, ParametroPesquisa... parametros) {
        return pesquisar(orderBy, true, parametros);
    }

    /**
     * Realiza uma pesquisa ordenada, sem paginacao, com ou sem isolamento de dados.
     *
     * @param orderBy            O criterio de  ordenacao
     * @param comIsolamentoDados True caso se deseje isolamento de dados
     * @param parametros         Os parametros da consulta
     * @return O resultado da consulta
     */
    private List<T> pesquisar(ParametroOrdenacaoColuna orderBy, Boolean comIsolamentoDados, ParametroPesquisa... parametros) {
        List<ParametroOrdenacaoColuna> listaParam = orderBy != null ? Collections.singletonList(orderBy) : null;
        return pesquisar(listaParam, comIsolamentoDados, parametros);
    }

    /**
     * Realiza uma pesquisa ordenada, sem paginacao, com ou sem isolamento de dados.
     *
     * @param orderBy            O criterio de  ordenacao
     * @param comIsolamentoDados True caso se deseje isolamento de dados
     * @param parametros         Os parametros da consulta
     * @return O resultado da consulta
     */
    private List<T> pesquisar(List<ParametroOrdenacaoColuna> orderBy, Boolean comIsolamentoDados, ParametroPesquisa... parametros) {
        return converterParaEntidade(criarConsultaSemPaginacao(orderBy, comIsolamentoDados, new HashMap<>(), parametros).getResultList(), comIsolamentoDados);
    }

    /**
     * Cria consulta HQL atraves de query em string e dados parametros
     *
     * @param queryString Query em string
     * @param parametros  Parametro para preenchimento da consulta
     * @return Query preenchida
     */
    private Query criarConsultaComParametros(String queryString, ParametroPesquisa... parametros) {
        Query query = getGerenciadorDeEntidade().createQuery(queryString);
        for (ParametroPesquisa param : parametros) {
            if (param.getValor() != null && param.getValor().getClass().equals(Date.class)) {
                query.setParameter(param.getNome(), (Date) param.getValor(), TemporalType.TIMESTAMP);
            } else {
                query.setParameter(param.getNome(), param.getValor());
            }
        }
        return query;
    }

    /**
     * Cria uma consulta paginada com os parametros informados
     *
     * @param paginacao          Os dados de paginacao
     * @param comIsolamentoDados Se true, adiciona os filtros que garantem o isolamento de dados
     * @param cachePaths         Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     * @param parametrosPesquisa Os parametros da pesquisa
     * @return A consulta gerada
     */
    private TypedQuery criarConsultaPorFiltro(InformacaoPaginacao paginacao, Boolean comIsolamentoDados, Map<String, Path<?>> cachePaths, ParametroPesquisa... parametrosPesquisa) {
        CriteriaBuilder builder = getGerenciadorDeEntidade().getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery();
        Root<T> entityRoot = criteria.from(getClassePersistente());
        criteria.select(entityRoot);
        povoarParametrosPesquisa(builder, criteria, entityRoot, comIsolamentoDados, cachePaths, parametrosPesquisa);
        povoarParametrosOrdenacao(builder, criteria, entityRoot, paginacao.getParametrosOrdenacaoColuna(), cachePaths);
        TypedQuery query = getGerenciadorDeEntidade().createQuery(criteria);
        if (paginacao.getPagina() != null && paginacao.getTamanhoPagina() != null) {
            int first = (paginacao.getPagina() - 1) * paginacao.getTamanhoPagina();
            query.setFirstResult(first);
            query.setMaxResults(paginacao.getTamanhoPagina());
        }
        return query;
    }

    /**
     * Obtem o total de registros para os dados parametros
     *
     * @param comIsolamentoDados True caso se deseje isolamento de dados
     * @param parametros         parametros de consulta
     * @return Total de registros no banco
     */
    private Long pesquisarTotalRegistros(Boolean comIsolamentoDados, ParametroPesquisa... parametros) {
        return criarConsultaTotalRegistros(comIsolamentoDados, parametros).getSingleResult();
    }

    /**
     * Cria uma consulta que informa quantos resultados foram encontrados que atendem aos parametros informados
     *
     * @param comIsolamentoDados True caso se deseje isolamento de dados
     * @param parametros         Os parametros da busca
     * @return A consulta a ser executada
     */
    private TypedQuery<Long> criarConsultaTotalRegistros(Boolean comIsolamentoDados, ParametroPesquisa... parametros) {
        CriteriaBuilder builder = getGerenciadorDeEntidade().getCriteriaBuilder();
        CriteriaQuery<Long> criteria = builder.createQuery(Long.class);
        Root<T> entityRoot = criteria.from(getClassePersistente());
        criteria.select(builder.count(entityRoot));
        if (parametros != null) {
            List<ParametroPesquisa> paramsSemFetch = Arrays.asList(parametros);
            paramsSemFetch = paramsSemFetch.stream().filter(p -> !(p instanceof ParametroPesquisaFetch)).collect(Collectors.toList());
            parametros = paramsSemFetch.toArray(new ParametroPesquisa[paramsSemFetch.size()]);
        }
        povoarParametrosPesquisa(builder, criteria, entityRoot, comIsolamentoDados, new HashMap<>(), parametros);
        return getGerenciadorDeEntidade().createQuery(criteria);
    }

    /**
     * Cria uma consulta sem paginacao com ordenacao
     *
     * @param orderBys           A coluna de ordenacao
     * @param comIsolamentoDados True caso se deseje isolamento de dados
     * @param cachePaths         Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     * @param parametros         Os parametros da busca
     * @return Uma consulta a ser executada
     */
    private TypedQuery criarConsultaSemPaginacao(List<ParametroOrdenacaoColuna> orderBys, boolean comIsolamentoDados, Map<String, Path<?>> cachePaths, ParametroPesquisa... parametros) {
        CriteriaBuilder builder = getGerenciadorDeEntidade().getCriteriaBuilder();
        CriteriaQuery criteria = builder.createQuery();
        Root<T> entityRoot = criteria.from(getClassePersistente());
        criteria.select(entityRoot);
        if (orderBys != null) {
            povoarParametrosOrdenacao(builder, criteria, entityRoot, orderBys, cachePaths);
        }
        povoarParametrosPesquisa(builder, criteria, entityRoot, comIsolamentoDados, cachePaths, parametros);
        return getGerenciadorDeEntidade().createQuery(criteria);
    }

    /**
     * Cria uma consulta sem paginacao nem ordenacao
     *
     * @param comIsolamentoDados Informa se a busca deve ou não ser feita com isolamento
     * @param parametros Os parametros da busca
     * @return A consulta a ser executada
     */
    private TypedQuery<T> criarConsultaSemPaginacao(Boolean comIsolamentoDados, ParametroPesquisa... parametros) {
        return criarConsultaSemPaginacao(null, comIsolamentoDados, new HashMap<>(), parametros);
    }

    /**
     * Obtem o path do atributo passado de acordo com as relacoes da entidade
     *
     * @param campo          O caminho para acesso ao campo
     * @param entityRoot     A entidade raiz da consulta (FROM)
     * @param criarLeftJoin  Se true, cria a navegacao usando left join
     * @param criarFetchJoin Se true, cria a navegacao usando fetch join (em vez de left ou inner join)
     * @param cachePaths     Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     * @param <P>            Classe tipo do campo
     * @return O path que contem a relacao ou atributo
     */
    private <P> Path<P> obterAtributoEntidade(String campo, Root<?> entityRoot, boolean criarLeftJoin, boolean criarFetchJoin, Map<String, Path<?>> cachePaths) {
        String[] caminhoAtributo = campo.split("\\.");
        return obterAtributoEntidade(entityRoot, caminhoAtributo, criarLeftJoin, criarFetchJoin, cachePaths, 0);
    }

    /**
     * Obtem o path do atributo passado de acordo com as relacoes da entidade
     *
     * @param relacaoPai      A relacao pai
     * @param caminhoAtributo Os nomes dos campos de navegacao ate a relacao desejada
     * @param criarLeftJoin   Se true, cria a navegacao usando left join
     * @param criarFetchJoin  Se true, cria a navegacao usando fetch join (em vez de left ou inner join)
     * @param cachePaths      Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     * @param nivel           O indice da relacao desejada dentro do vetor de <code>relacoes</code>
     * @param <P>             Classe tipo do campo
     * @return O path que contem a relacao ou atributo
     */
    private <P> Path<P> obterAtributoEntidade(From<?, ?> relacaoPai, String[] caminhoAtributo, boolean criarLeftJoin, boolean criarFetchJoin, Map<String, Path<?>> cachePaths, int nivel) {
        boolean isUltimoAtributo = nivel == caminhoAtributo.length - 1;
        if (isUltimoAtributo) {
            Path path = relacaoPai.get(caminhoAtributo[nivel]);
            if (path != null && !IPersistente.class.isAssignableFrom(path.getJavaType()) && !List.class.isAssignableFrom(path.getJavaType())) {
                return relacaoPai.get(caminhoAtributo[nivel]);
            } else {
                return (Path<P>) resolverRelacao(relacaoPai, caminhoAtributo, criarLeftJoin, criarFetchJoin, cachePaths, nivel);
            }
        } else {
            From<?, ?> relacao = resolverRelacao(relacaoPai, caminhoAtributo, criarLeftJoin, criarFetchJoin, cachePaths, nivel);
            return obterAtributoEntidade(relacao, caminhoAtributo, criarLeftJoin, criarFetchJoin, cachePaths, nivel + 1);
        }
    }

    /**
     * Retorna o caminho da relacao representada pelo atributo de nome <code>caminhoAtributo[nivel]</code>, que, por sua vez eh filha da
     * relacao <code>relacao</code>.
     *
     * @param relacaoPai      A relacao pai
     * @param caminhoAtributo Os nomes dos campos de navegacao ate a relacao desejada
     * @param usarLeftJoins   Se true, cria o caminho como um leftJoin
     * @param fazerFetch      Se true, cria o caminho como um fetchJoin
     * @param cachePaths      Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     * @param nivel           O indice da relacao desejada dentro do vetor de <code>relacoes</code>
     * @return O caminho da relacao desejada
     */
    private From<?, ?> resolverRelacao(From<?, ?> relacaoPai, String[] caminhoAtributo, boolean usarLeftJoins, boolean fazerFetch, Map<String, Path<?>> cachePaths, int nivel) {
        String chaveCache = StringUtils.join(caminhoAtributo, '.', 0, nivel + 1);
        Path<?> pathResolvido = cachePaths.get(chaveCache);
        if (pathResolvido == null) {
            if (fazerFetch) {
                Object fetch = relacaoPai.fetch(caminhoAtributo[nivel], JoinType.LEFT);
                pathResolvido = (Join<?, ?>) fetch;
            } else {
                pathResolvido = relacaoPai.join(caminhoAtributo[nivel], usarLeftJoins ? JoinType.LEFT : JoinType.INNER);
            }
            cachePaths.put(chaveCache, pathResolvido);
        }
        return (From<?, ?>) pathResolvido;
    }

    /**
     * Povoa uma consulta com os parametros informados
     *
     * @param builder            O construtor da consulta
     * @param criteria           A consulta
     * @param entityRoot         A enidade alvo
     * @param comIsolamentoDados Se true, adiciona os filtros que garantem o isolamento de dados
     * @param cachePaths         Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     * @param parametros         Os parametros da consulta
     */
    private void povoarParametrosPesquisa(CriteriaBuilder builder, CriteriaQuery<?> criteria, Root<T> entityRoot, Boolean comIsolamentoDados, Map<String, Path<?>> cachePaths, ParametroPesquisa... parametros) {
        List<ParametroPesquisa> listaParams = new ArrayList<>();
        if (parametros != null) {
            listaParams.addAll(Arrays.asList(parametros));
            listaParams.sort((p1, p2) -> {
                boolean o1Fetch = p1 instanceof ParametroPesquisaFetch;
                boolean o2Fetch = p2 instanceof ParametroPesquisaFetch;
                if (o1Fetch == o2Fetch) {
                    return 0;
                }
                return o1Fetch ? -1 : 1;
            });
        }
        List<Predicate> predicates = montarPredicados(builder, criteria, entityRoot, comIsolamentoDados, listaParams, cachePaths);
        criteria.where(predicates.toArray(new Predicate[predicates.size()]));
    }

    /**
     * Monta uma lista de predicados a  partir da lista de parametros informada
     *
     * @param builder            O construtor da consulta
     * @param criteria           A consulta
     * @param entityRoot         A enidade alvo
     * @param comIsolamentoDados Se true, adiciona os filtros que garantem o isolamento de dados
     * @param parametros         Os parametros a serem transformados em predicados
     * @param cachePaths         Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     * @return Uma lista de predicados
     */
    private List<Predicate> montarPredicados(CriteriaBuilder builder, CommonAbstractCriteria criteria, Root<?> entityRoot, Boolean comIsolamentoDados, List<ParametroPesquisa> parametros, Map<String, Path<?>> cachePaths) {

        List<Predicate> predicados = new ArrayList<>();

        if (IExclusaoLogica.class.isAssignableFrom(getClassePersistente())) {
            predicados.add(builder.equal(entityRoot.get(IExclusaoLogica.NOME_CAMPO), Boolean.FALSE));
        }
        if (IExclusaoLogicaComData.class.isAssignableFrom(getClassePersistente())) {
            predicados.add(builder.equal(entityRoot.get(IExclusaoLogicaComData.NOME_CAMPO), Boolean.FALSE));
        }

        Usuario usuarioLogado = ambiente.getUsuarioLogado();
        if (comIsolamentoDados && usuarioLogado != null) {
            adicionarParametroIsolamentoUsuarioAssessorOuCoordenador(parametros, usuarioLogado);
            adicionarParametroIsolamentoFrotista(parametros);
            adicionarParametroIsolamentoRevendedor(parametros, usuarioLogado);
            adicionarParametroIsolamentoMotorista(parametros, usuarioLogado);
        }

        for (ParametroPesquisa param : parametros) {
            povoarParametroPesquisa(param, criteria, builder, entityRoot, predicados, cachePaths);
        }

        return predicados;
    }

    /**
     * Cria e adiciona o parametro de pesquisa para isolamento de dados dos motoristas
     *
     * @param parametros    Os parametros atuais
     * @param usuarioLogado O usuario logado
     */
    private void adicionarParametroIsolamentoMotorista(List<ParametroPesquisa> parametros, Usuario usuarioLogado) {
        if (IPertenceMotorista.class.isAssignableFrom(getClassePersistente()) && usuarioLogado.getMotorista() != null) {
            String nomeCampo = IPertenceMotorista.obterCaminhoMotorista(getClassePersistente());
            parametros.add(new ParametroPesquisaIgual(nomeCampo, usuarioLogado.getMotorista().getId()));
        }
    }

    /**
     * Cria e adiciona o parametro de pesquisa para isolamento de dados dos assessores e coordenadores
     *
     * @param parametros    Os parametros atuais
     * @param usuarioLogado O usuario logado
     */
    private void adicionarParametroIsolamentoUsuarioAssessorOuCoordenador(List<ParametroPesquisa> parametros, Usuario usuarioLogado){
        if (IPertenceFrota.class.isAssignableFrom(getClassePersistente()) && usuarioLogado.isInterno()) {
            String nomeCampo = IPertenceFrota.obterCaminhoFrota(getClassePersistente());
            if (usuarioLogado.possuiFrotasAssociadas()) {
                parametros.add(new ParametroPesquisaIn(nomeCampo, usuarioLogado.listarIdsFrotasAssociadas()));
            }
        }
    }

    /**
     * Cria e adiciona o parametro de pesquisa para isolamento de dados dos revendedores
     *
     * @param parametros    Os parametros atuais
     * @param usuarioLogado O usuario logado
     */
    private void adicionarParametroIsolamentoRevendedor(List<ParametroPesquisa> parametros, Usuario usuarioLogado) {
        if (IPertenceRevendedor.class.isAssignableFrom(getClassePersistente()) &&
                usuarioLogado.getTipoPerfil().isRevendedor()) {
            if (CollectionUtils.isNotEmpty(usuarioLogado.getPontosDeVenda())) {
                String nomeCampo = IPertenceRevendedor.obterCaminhoPontoVenda(getClassePersistente());
                List<Long> idsPontoVenda = new ArrayList<>();
                usuarioLogado.getPontosDeVenda().forEach(pv -> idsPontoVenda.add(pv.getId()));
                parametros.add(new ParametroPesquisaIn(nomeCampo, idsPontoVenda));
            }
        }
    }

    /**
     * Cria e adiciona o parametro de pesquisa para isolamento de dados dos frotistas
     *
     * @param parametros Os parametros atuais
     */
    private void adicionarParametroIsolamentoFrotista(List<ParametroPesquisa> parametros) {
        if (IPertenceFrota.class.isAssignableFrom(getClassePersistente())) {
            Frota frota = ambiente.getFrotaUsuarioLogado();
            if (frota != null) {
                String nomeCampo = IPertenceFrota.obterCaminhoFrota(getClassePersistente());
                parametros.add(new ParametroPesquisaIgual(nomeCampo, frota.getId()));
            }
        }
    }

    /**
     * Povoa a consulta com um parametro de pesquisa informado
     *
     * @param param      o parametro a ser processado
     * @param criteria   A consulta em construcao
     * @param builder    O construtor da consuolta
     * @param entityRoot A entidade raiz
     * @param predicates A lista de predicados sendo construidas
     * @param cachePaths Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     */
    private void povoarParametroPesquisa(ParametroPesquisa param, CommonAbstractCriteria criteria, CriteriaBuilder builder, Root<?> entityRoot, List<Predicate> predicates, Map<String, Path<?>> cachePaths) {
        String campo = param.getNome();
        boolean rootQuery = criteria instanceof CriteriaQuery;
        boolean parametroFetch = param instanceof ParametroPesquisaFetch;

        if (rootQuery && !parametroFetch && !cacheContemRelacao(entityRoot.getJavaType(), campo, cachePaths) && parametroNavegaRelacaoMany(param, entityRoot.getJavaType())) {
            povoarParametroExists(param, entityRoot, criteria, builder, predicates, new HashMap<>());
        } else {
            povoarParametroSimples(param, criteria, builder, entityRoot, predicates, cachePaths);
        }
    }

    /**
     * Verifica se o cache de paths contem a relacao necessaria para o predicado a ser construido
     *
     * @param classe A classe desejada
     * @param cachePaths O cache de paths
     * @param campo      O campo para o qual sera construido o predicado
     * @return True caso a relacao esteja no cache
     */
    private boolean cacheContemRelacao(Class classe, String campo, Map<String, Path<?>> cachePaths) {
        while (StringUtils.isNotBlank(campo) && campo.contains(".") && !isRelacao(campo, classe)) {
            campo = campo.substring(0, campo.lastIndexOf("."));
        }
        return cachePaths.containsKey(campo);
    }

    /**
     * Verifica se um dado parametro de pesquiasa acessa relacionamentos do tipo X-To-Many
     *
     * @param param      O parametro de pesquisa
     * @param entityRoot A entidade raiz
     * @return True caso acesse relacionamentos do tipo X-To-Many
     */
    private boolean parametroNavegaRelacaoMany(ParametroPesquisa param, Class entityRoot) {
        if (param instanceof GrupoParametrosPesquisa) {
            ParametroPesquisa[] paramsInternos = ((GrupoParametrosPesquisa) param).getParametros();
            for (ParametroPesquisa paramInterno : paramsInternos) {
                if (parametroNavegaRelacaoMany(paramInterno, entityRoot)) {
                    return true;
                }
            }
        } else {
            return campoNavegaRelacaoMany(param.getNome(), entityRoot);
        }
        return false;
    }

    /**
     * Verifica se uma dado campo acessa relacionamentos do tipo X-To-Many.
     * Memoriza o resultado em cache estatico para ganho de performance.
     *
     * @param caminhoAtributo O caminho do atributo
     * @param classe          A classe alvo
     * @return True caso acesse relacionamentos do tipo X-To-Many
     */
    private Boolean campoNavegaRelacaoMany(String caminhoAtributo, Class classe) {
        String chaveCache = montarChaveCacheRelacionamentos(caminhoAtributo, classe);
        if (CACHE_RELACIONAMENTOS_MANY.get(chaveCache) == null) {
            LOCK.lock();
            try {
                if (CACHE_RELACIONAMENTOS_MANY.get(chaveCache) == null) {
                    CriteriaBuilder builder = getGerenciadorDeEntidade().getCriteriaBuilder();
                    CriteriaQuery criteria = builder.createQuery();
                    Root<T> entityRoot = criteria.from(getClassePersistente());
                    String[] partesCaminho = StringUtils.split(caminhoAtributo, '.');
                    Map<String, Path<?>> cachePaths = new HashMap<>();
                    boolean atravessouMany = false;
                    for (int i = 0; i < partesCaminho.length; i++) {
                        String parcial = StringUtils.join(partesCaminho, '.', 0, i + 1);
                        atravessouMany = atravessouMany || obterAtributoEntidade(parcial, entityRoot, true, false, cachePaths) instanceof PluralJoin;
                        CACHE_RELACIONAMENTOS_MANY.put(montarChaveCacheRelacionamentos(parcial, classe), atravessouMany);
                    }
                }
            } finally {
                LOCK.unlock();
            }
        }
        return CACHE_RELACIONAMENTOS_MANY.get(chaveCache);
    }

    /**
     * Verifica se o atributo presente no caminho informado eh uma relacao com outra entidade
     *
     * @param caminhoAtributo O caminho do atributo
     * @param classe          A classe alvo
     * @return True caso seja um relacionamento
     */
    private Boolean isRelacao(String caminhoAtributo, Class classe) {
        String chaveCache = montarChaveCacheRelacionamentos(caminhoAtributo, classe);
        if (CACHE_RELACOES.get(chaveCache) == null) {
            LOCK.lock();
            try {
                if (CACHE_RELACOES.get(chaveCache) == null) {
                    CriteriaBuilder builder = getGerenciadorDeEntidade().getCriteriaBuilder();
                    CriteriaQuery criteria = builder.createQuery();
                    Root<T> entityRoot = criteria.from(getClassePersistente());
                    String[] partesCaminho = StringUtils.split(caminhoAtributo, '.');
                    Map<String, Path<?>> cachePaths = new HashMap<>();
                    for (int i = 0; i < partesCaminho.length; i++) {
                        String parcial = StringUtils.join(partesCaminho, '.', 0, i + 1);
                        boolean isAtributo = (i == partesCaminho.length - 1) && !(obterAtributoEntidade(parcial, entityRoot, true, false, cachePaths) instanceof Join);
                        CACHE_RELACOES.put(montarChaveCacheRelacionamentos(parcial, classe), !isAtributo);
                    }
                }
            } finally {
                LOCK.unlock();
            }
        }
        return CACHE_RELACOES.get(chaveCache);
    }

    /**
     * Monta a chave para indexacao no cache estatico de relacionamentos many
     *
     * @param caminhoAtributo O caminho do atributo
     * @param classe          A classe alvo
     * @return A chave para indexacao
     */
    private String montarChaveCacheRelacionamentos(String caminhoAtributo, Class classe) {
        return classe.getName() + "->" + caminhoAtributo;
    }

    /**
     * Trata um parametro que exige navegacao em um relacionamento do tipo X-to-Many, gerando
     * um predicado de inner-query no seguinte formato:
     * <code>AND EXISTS (SELECT 1 FROM Entidade e JOIN e.filha WHERE filha.[parametroPesquisa])</code>.
     * Este tratamento evita a utilizacao de clausulas DISTINCT, pois, caso o parametro
     * fosse tratado como um JOIN convencional, causaria a redundancia de registros resultantes,
     * devido a expansao do produto cartesiano produzido pela cardinalidade MANY do
     * relacionamento. A utilizacao de clausulas DISTINCT deve ser, via de regra, evitada,
     * pois causa a comparacao campo a campo de cada cada registro resultante na projecao, degradando
     * o desempenho computacional do banco de dados.
     *
     * @param param        o parametro a ser processado
     * @param entidadeRoot A entidade raiz da consulta
     * @param criteria     A consulta em construcao
     * @param builder      O construtor da consuolta
     * @param predicados   A lista de predicados sendo construidas
     * @param cachePaths   Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     */
    private <R> void povoarParametroExists(ParametroPesquisa param, Root<R> entidadeRoot, CommonAbstractCriteria criteria, CriteriaBuilder builder, List<Predicate> predicados, Map<String, Path<?>> cachePaths) {
        Subquery<Integer> subquery = criteria.subquery(Integer.class);
        Class<R> tipoEntidadePai = (Class<R>) entidadeRoot.getJavaType();
        Root<R> entidadeSubqueryRoot = subquery.from(tipoEntidadePai);
        subquery.select(builder.literal(1));
        List<ParametroPesquisa> params = new ArrayList<>();
        params.add(param);
        List<Predicate> subPredicados = montarPredicados(builder, subquery, entidadeSubqueryRoot, false, params, cachePaths);
        if (subPredicados.size() > 0) {
            Predicate join = builder.equal(entidadeRoot.get("id"), entidadeSubqueryRoot.get("id"));
            subPredicados.add(join);
            subquery.where(subPredicados.toArray(new Predicate[subPredicados.size()]));
            predicados.add(builder.exists(subquery));
        }
    }

    /**
     * Cria um predicado simples a partir do parametro informado, adicionando-o a lista <code>predicates</code>
     *
     * @param param      O  parametro
     * @param criteria   A consulta em construcao
     * @param builder    O builder da consulta
     * @param entityRoot A entidade raiz da consulta
     * @param predicates A lista de predicados alvo
     * @param cachePaths Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     */
    private void povoarParametroSimples(ParametroPesquisa param, CommonAbstractCriteria criteria, CriteriaBuilder builder, Root<?> entityRoot, List<Predicate> predicates, Map<String, Path<?>> cachePaths) {
        String campo = param.getNome();
        Object valor = param.getValor();
        boolean usarLeftJoins = criteria instanceof CriteriaQuery;
        if (valor != null) {
            povoarParametroSimplesComValor(param, builder, entityRoot, predicates, cachePaths, usarLeftJoins);
        } else if (param instanceof ParametroPesquisaNulo) {
            povoarParametroPesquisaNulo((ParametroPesquisaNulo) param, criteria, builder, entityRoot, predicates, cachePaths);
        } else if (param instanceof ParametroPesquisaAnd) {
            povoarParametroPesquisaAnd((ParametroPesquisaAnd) param, criteria, builder, entityRoot, predicates, cachePaths);
        } else if (param instanceof ParametroPesquisaOr) {
            povoarParametroPesquisaOr((ParametroPesquisaOr) param, criteria, builder, entityRoot, predicates, cachePaths);
        } else if (param instanceof ParametroPesquisaFetch) {
            obterAtributoEntidade(campo, entityRoot, usarLeftJoins, true, cachePaths);
        } else if (param instanceof ParametroPesquisaMultiJoin) {
            povoarParametroPesquisaMultiExists((ParametroPesquisaMultiJoin) param, criteria, builder, entityRoot, predicates);
        } else if (param instanceof ParametroPesquisaEmpty) {
            povoarParametroPesquisaEmpty((ParametroPesquisaEmpty) param, builder, entityRoot, predicates);
        }
    }

    /**
     * Cria um predicado simples a partir do parametro informado, desde que seu valor nao seja nulo
     *
     * @param param         O parametro de pesquisa
     * @param builder       O builder da consulta
     * @param entityRoot    A entidade raiz da consulta
     * @param predicates    A lista de predicados alvo
     * @param cachePaths    Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     * @param usarLeftJoins O criterio aplicado a query
     */
    private void povoarParametroSimplesComValor(ParametroPesquisa param, CriteriaBuilder builder, Root<?> entityRoot, List<Predicate> predicates, Map<String, Path<?>> cachePaths, boolean usarLeftJoins) {
        String campo = param.getNome();
        Object valor = param.getValor();
        if (param instanceof ParametroPesquisaLike) {
            Path<String> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate like = builder.like(removerAcentosCampo(builder, navegacaoCampo), (String) valor);
            predicates.add(like);
        } else if (param instanceof ParametroPesquisaIgualIgnoreCase) {
            Path<String> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate equalIgnoreCase = builder.equal(builder.upper(navegacaoCampo), ((String) valor).toUpperCase());
            predicates.add(equalIgnoreCase);
        } else if (param instanceof ParametroPesquisaIgual) {
            Path<?> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate equal = builder.equal(navegacaoCampo, valor);
            predicates.add(equal);
        } else if (param instanceof ParametroPesquisaDiferente) {
            Path<?> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate notEqual = builder.notEqual(navegacaoCampo, valor);
            predicates.add(notEqual);
        } else if (param instanceof ParametroPesquisaIn) {
            Path<?> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            if (navegacaoCampo != null) {
                List<Predicate> in = obterParametroIn((Path<Collection>) navegacaoCampo, (Collection) valor);
                predicates.add(builder.or(in.toArray(new Predicate[in.size()])));
            }
        } else if (valor instanceof Date) {
            povoarParametroTemporalComValor(param, builder, entityRoot, predicates, cachePaths, usarLeftJoins);
        } else {
            povoarParametroComparacaoMagnitude(param, builder, entityRoot, predicates, cachePaths, usarLeftJoins);
        }
    }

    /**
     * Cria um predicado simples a partir do parametro de comparacao de magnitude (maior, menor) informado
     *
     * @param param         O parametro de pesquisa
     * @param builder       O builder da consulta
     * @param entityRoot    A entidade raiz da consulta
     * @param predicates    A lista de predicados alvo
     * @param cachePaths    Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     * @param usarLeftJoins O criterio aplicado a query
     */
    private void povoarParametroComparacaoMagnitude(ParametroPesquisa param, CriteriaBuilder builder, Root<?> entityRoot, List<Predicate> predicates, Map<String, Path<?>> cachePaths, boolean usarLeftJoins) {
        String campo = param.getNome();
        Object valor = param.getValor();
        if (param instanceof ParametroPesquisaEntre) {
            Path<BigDecimal> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate greaterThan = builder.between(navegacaoCampo, (BigDecimal) valor, ((ParametroPesquisaEntre) param).getValorSecundario());
            predicates.add(greaterThan);
        }
        if (param instanceof ParametroPesquisaMaior) {
            Path<BigDecimal> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate greaterThan = builder.greaterThan(navegacaoCampo, (BigDecimal) valor);
            predicates.add(greaterThan);
        }
        if (param instanceof ParametroPesquisaMaiorOuIgual) {
            Path<BigDecimal> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate greaterThanOrEqualTo = builder.greaterThanOrEqualTo(navegacaoCampo, (BigDecimal) valor);
            predicates.add(greaterThanOrEqualTo);
        } else if (param instanceof ParametroPesquisaMenor) {
            Path<BigDecimal> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate lessThan = builder.lessThan(navegacaoCampo, (BigDecimal) valor);
            predicates.add(lessThan);
        } else if (param instanceof ParametroPesquisaMenorOuIgual) {
            Path<BigDecimal> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate lessThanOrEqualTo = builder.lessThanOrEqualTo(navegacaoCampo, (BigDecimal) valor);
            predicates.add(lessThanOrEqualTo);
        }
    }

    /**
     * Cria um predicado simples a partir do parametro temporal informado
     *
     * @param param         O parametro de pesquisa
     * @param builder       O builder da consulta
     * @param entityRoot    A entidade raiz da consulta
     * @param predicates    A lista de predicados alvo
     * @param cachePaths    Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     * @param usarLeftJoins O criterio aplicado a query
     */
    private void povoarParametroTemporalComValor(ParametroPesquisa param, CriteriaBuilder builder, Root<?> entityRoot, List<Predicate> predicates, Map<String, Path<?>> cachePaths, boolean usarLeftJoins) {
        String campo = param.getNome();
        Object valor = param.getValor();
        if (param instanceof ParametroPesquisaDataMenor) {
            Path<Date> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate lessThan = builder.lessThan(navegacaoCampo, (Date) valor);
            predicates.add(lessThan);
        } else if (param instanceof ParametroPesquisaDataMaior) {
            Path<Date> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate greaterThan = builder.greaterThan(navegacaoCampo, (Date) valor);
            predicates.add(greaterThan);
        } else if (param instanceof ParametroPesquisaDataMenorOuIgual) {
            Path<Date> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate lessThanOrEqualTo = builder.lessThanOrEqualTo(navegacaoCampo, (Date) valor);
            predicates.add(lessThanOrEqualTo);
        } else if (param instanceof ParametroPesquisaDataMaiorOuIgual) {
            Path<Date> navegacaoCampo = obterAtributoEntidade(campo, entityRoot, usarLeftJoins, false, cachePaths);
            Predicate greaterThanOrEqualTo = builder.greaterThanOrEqualTo(navegacaoCampo, (Date) valor);
            predicates.add(greaterThanOrEqualTo);
        }
    }

    /**
     * Povoa na consula um parametro de pesquisa OR
     *
     * @param param      o parametro a ser processado
     * @param criteria   A consulta em construcao
     * @param builder    O construtor da consulta
     * @param entityRoot A entidade raiz
     * @param predicates A lista de predicados sendo construidas
     * @param cachePaths Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     */
    private void povoarParametroPesquisaOr(ParametroPesquisaOr param, CommonAbstractCriteria criteria, CriteriaBuilder builder, Root<?> entityRoot, List<Predicate> predicates, Map<String, Path<?>> cachePaths) {
        ParametroPesquisa[] params = param.getParametros();
        List<Predicate> predicadosOr = new ArrayList<>();
        for (ParametroPesquisa p : params) {
            povoarParametroPesquisa(p, criteria, builder, entityRoot, predicadosOr, cachePaths);
        }
        if (predicadosOr.size() > 0) {
            Predicate predicado = builder.or(predicadosOr.toArray(new Predicate[predicadosOr.size()]));
            predicates.add(predicado);
        }
    }

    /**
     * Povoa na consula um parametro de pesquisa AND
     *
     * @param param      o parametro a ser processado
     * @param criteria   A consulta em construcao
     * @param builder    O construtor da consulta
     * @param entityRoot A entidade raiz
     * @param predicates A lista de predicados sendo construidas
     * @param cachePaths Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     */
    private void povoarParametroPesquisaAnd(ParametroPesquisaAnd param, CommonAbstractCriteria criteria, CriteriaBuilder builder, Root<?> entityRoot, List<Predicate> predicates, Map<String, Path<?>> cachePaths) {
        ParametroPesquisa[] params = param.getParametros();
        List<Predicate> predicadosAnd = new ArrayList<>();
        for (ParametroPesquisa p : params) {
            povoarParametroPesquisa(p, criteria, builder, entityRoot, predicadosAnd, cachePaths);
        }
        if (predicadosAnd.size() > 0) {
            Predicate predicado = builder.and(predicadosAnd.toArray(new Predicate[predicadosAnd.size()]));
            predicates.add(predicado);
        }
    }

    /**
     * Povoa na consula um parametro de pesquisa MultiExists
     *
     * @param param      o parametro a ser processado
     * @param criteria   A consulta em construcao
     * @param builder    O construtor da consulta
     * @param entityRoot A entidade raiz
     * @param predicates A lista de predicados sendo construidas
     */
    private void povoarParametroPesquisaMultiExists(ParametroPesquisaMultiJoin param, CommonAbstractCriteria criteria, CriteriaBuilder builder, Root<?> entityRoot, List<Predicate> predicates) {
        ParametroPesquisa[] params = param.getParametros();
        List<Predicate> predicados = new ArrayList<>();
        for (ParametroPesquisa p : params) {
            povoarParametroPesquisa(p, criteria, builder, entityRoot, predicados, new HashMap<>());
        }
        if (predicados.size() > 0) {
            Predicate predicado = builder.and(predicados.toArray(new Predicate[predicados.size()]));
            predicates.add(predicado);
        }
    }

    /**
     * Povoa na consula um parametro de pesquisa nulo
     *
     * @param param      o parametro a ser processado
     * @param criteria   A consulta sendo construida
     * @param builder    O construtor da consulta
     * @param entityRoot A entidade raiz
     * @param predicates A lista de predicados sendo construidas
     * @param cachePaths Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     */
    private void povoarParametroPesquisaNulo(ParametroPesquisaNulo param, CommonAbstractCriteria criteria, CriteriaBuilder builder, Root<?> entityRoot, List<Predicate> predicates, Map<String, Path<?>> cachePaths) {
        Path<?> navegacaoCampo = obterAtributoEntidade(param.getNome(), entityRoot, criteria instanceof CriteriaQuery, false, cachePaths);
        Predicate nullPredicate;
        if (param.getNot()) {
            if (navegacaoCampo != null && Collection.class.isAssignableFrom(navegacaoCampo.getJavaType())) {
                nullPredicate = builder.not(builder.isEmpty((Path<Collection>) navegacaoCampo));
            } else {
                nullPredicate = builder.isNotNull(navegacaoCampo);
            }
        } else {
            if (navegacaoCampo != null && Collection.class.isAssignableFrom(navegacaoCampo.getJavaType())) {
                nullPredicate = builder.isEmpty((Path<Collection>) navegacaoCampo);
            } else {
                nullPredicate = builder.isNull(navegacaoCampo);
            }
        }
        predicates.add(nullPredicate);
    }

    /**
     * Povoa na consula um parametro de pesquisa empty
     *
     * @param param      o parametro a ser processado
     * @param builder    O construtor da consulta
     * @param entityRoot A entidade raiz
     * @param predicates A lista de predicados sendo construidas
     */
    private void povoarParametroPesquisaEmpty(ParametroPesquisaEmpty param, CriteriaBuilder builder, Root<?> entityRoot, List<Predicate> predicates) {
        Path<Collection<?>> navegacaoCampo = entityRoot.get(param.getNome());
        Predicate emptyPredicate = builder.isEmpty(navegacaoCampo);
        if (param.getNot()) {
            emptyPredicate = builder.not(emptyPredicate);
        }
        predicates.add(emptyPredicate);
    }

    /**
     * Obtem os predicados de IN quebrados a cada mil, devido a limitação do JPA
     *
     * @param navegacaoCampo campo da entidade
     * @param colecao        lista a ser comparada
     * @return Lista de predicados
     */
    private List<Predicate> obterParametroIn(Path<Collection> navegacaoCampo, Collection colecao) {
        List<Predicate> inPredicates = new ArrayList<>();
        List lista = new ArrayList(colecao);
        while (lista.size() > 0) {
            inPredicates.add(navegacaoCampo.in(lista.subList(0, lista.size() > 1000 ? 1000 : lista.size())));
            if (lista.size() > 1000) {
                lista = lista.subList(1000, lista.size());
            } else {
                lista = new ArrayList();
            }
        }
        return inPredicates;
    }

    /**
     * Povoa os parametros de ordenacao em uma consulta
     *
     * @param builder                   O gerador de consultas
     * @param criteria                  A consulta
     * @param entityRoot                A entidade alvo
     * @param parametrosOrdenacaoColuna Os parametros de ordenacao
     * @param cachePaths                Um map que servira de cache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     */
    private void povoarParametrosOrdenacao(CriteriaBuilder builder, CriteriaQuery<T> criteria, Root<T> entityRoot, List<ParametroOrdenacaoColuna> parametrosOrdenacaoColuna, Map<String, Path<?>> cachePaths) {
        List<Order> orders = new ArrayList<>();
        for (ParametroOrdenacaoColuna parametroOrdenacao : parametrosOrdenacaoColuna) {
            if (StringUtils.isNotEmpty(parametroOrdenacao.getNome())) {
                povoarParametroOrdenacao(parametroOrdenacao, builder, entityRoot, cachePaths, orders);
            }
        }
        if (!orders.isEmpty() && orders.get(0) != null) {
            criteria.orderBy(orders);
            List<Selection> expressions = new ArrayList<>();
            expressions.add(criteria.getSelection());
            for (Order order : orders) {
                expressions.add(order.getExpression());
            }
            criteria.multiselect(expressions.toArray(new Selection[expressions.size()]));
        }
    }

    /**
     * Povoa os parametros de ordenacao que nao estejam vazios em uma consulta
     *
     * @param parametroOrdenacao O parametro de ordenacao de cada coluna
     * @param builder            O gerador de consultas
     * @param entityRoot         A entidade alvo
     * @param cachePaths         Um map que servira de chache para os paths de cada parametro de pesquisa, para otimizacao dos joins criados pela consulta
     * @param orders             A lista de Orders a ser povoada
     */
    private void povoarParametroOrdenacao(ParametroOrdenacaoColuna parametroOrdenacao, CriteriaBuilder builder, Root<T> entityRoot, Map<String, Path<?>> cachePaths, List<Order> orders) {
        Path<?> campoEntidade = obterAtributoEntidade(parametroOrdenacao.getNome(), entityRoot, true, false, cachePaths);
        if (campoEntidade != null && campoEntidade.getJavaType().equals(String.class)) {
            Expression<String> campoEntidadeExp =
                    parametroOrdenacao.getNomeNulo() != null
                            ? builder.lower(builder.coalesce((Path<String>) campoEntidade, parametroOrdenacao.getNomeNulo().toLowerCase()))
                            : builder.lower((Path<String>) campoEntidade);
            adicionarParmetroOrdenacao(campoEntidadeExp, parametroOrdenacao, builder, orders);
        } else if (campoEntidade != null && Number.class.isAssignableFrom(campoEntidade.getJavaType()) && parametroOrdenacao.isTratarComoString()) {
            Expression<String> campoEntidadeStr = builder.lower((Path<String>) campoEntidade);
            adicionarParmetroOrdenacao(campoEntidadeStr, parametroOrdenacao, builder, orders);
        } else if (campoEntidade != null && campoEntidade.getJavaType().equals(Date.class) && parametroOrdenacao.isTruncarData()) {
            Expression<Date> campoDate = builder.function("TRUNC", Date.class, campoEntidade);
            adicionarParmetroOrdenacao(campoDate, parametroOrdenacao, builder, orders);
        } else {
            adicionarParmetroOrdenacao(campoEntidade, parametroOrdenacao, builder, orders);
        }
    }

    /**
     * Cria um parametro de ordenacao ASC ou DESC e adiciona-o a lista
     *
     * @param expressao          A expressao a ser usada como order by
     * @param parametroOrdenacao O parametro de ordenacao
     * @param builder            O builder da consulta
     * @param orders             A lista de predicados de ordenacao
     */
    private void adicionarParmetroOrdenacao(Expression<?> expressao, ParametroOrdenacaoColuna parametroOrdenacao, CriteriaBuilder builder, List<Order> orders) {
        orders.add(
                parametroOrdenacao.isDecrescente()
                        ? builder.desc(expressao)
                        : builder.asc(expressao)
        );
    }

    /**
     * Metodo responsavel por separar a clausula de select da entidade, da clasusulas de select de ordenacao
     *
     * @param resultList         Resultado da consulta
     * @param comIsolamentoDados True caso se deseje isolamento de dados
     * @return Resultado da consulta convertido para entidade
     */
    private List<T> converterParaEntidade(List resultList, Boolean comIsolamentoDados) {
        List<T> convertidos = new ArrayList<>();
        for (Object result : resultList) {
            T t = obterEntidadeObjetoResultado(result, comIsolamentoDados);
            if (comIsolamentoDados) {
                UtilitarioIsolamentoInformacoes.exigirPermissaoAcesso(t, ambiente.getUsuarioLogado());
            }
            convertidos.add(t);
        }
        return convertidos;
    }

    /**
     * Método para obter a entidade atraves do objeto generico obtido da consulta
     *
     * @param result objeto complexo a obter a entidade
     * @param comIsolamentoDados Informa se a consulta deve ou não ser feita com isolamento
     * @return A entidade desejada
     */
    private T obterEntidadeObjetoResultado(Object result, Boolean comIsolamentoDados) {
        T t = null;
        if (getClassePersistente().isAssignableFrom(result.getClass())) {
            t = (T) result;
        } else if (result.getClass().isArray() && getClassePersistente().isAssignableFrom(Array.get(result, 0).getClass())) {
            t = (T) Array.get(result, 0);
        }
        if (comIsolamentoDados) {
            UtilitarioIsolamentoInformacoes.exigirPermissaoAcesso(t, ambiente.getUsuarioLogado());
        }
        return t;
    }

    /**
     * Remove acentos de uma determinado campo
     *
     * @param criteriaBuilder O gerador de consultas
     * @param path            O caminho do atributo
     * @return Retorna uma Expression que realiza a remoção dos acentos.
     */
    private Expression removerAcentosCampo(CriteriaBuilder criteriaBuilder, Path<String> path) {

        return criteriaBuilder.function(
                TRANSLATE_FUNCTION,
                String.class,
                criteriaBuilder.lower(path),
                criteriaBuilder.literal(TRANSLATE_ACCENTS_FROM),
                criteriaBuilder.literal(TRANSLATE_ACCENTS_TO));
    }

    /**
     * Cria uma string representando a chamada de uma funcao que
     * remove os acentos de um dado campo de um registor no banco,
     * para construcao de consultas JPQL que utilizem buscas por
     * campos textuais.
     * @param nomeCampo O nome do campo alvo
     * @return O compo sem acentos
     */
    protected static String removerAcentosCampo(String nomeCampo) {
        return String.format(TRANSLATE_FUNCTION_CALL, nomeCampo);
    }
    
    /**
     * Cria uma string representando a chamada de uma funcao que
     * remove o case de um dado campo de um registor no banco,
     * para construcao de consultas JPQL que utilizem buscas por
     * campos textuais.
     * @param nomeCampo O nome do campo alvo
     * @return O compo sem case
     */
    protected static String removerCaseCampo(String nomeCampo) {
        return String.format(LOWER_FUNCTION_CALL, nomeCampo);
    }
    
    /**
     * Prepara o termo de consulta de cnpj para ser buscado no banco.
     * 
     * @param termo o termo de busca do cpnj
     * @return o termo formatado removendo a pontuação e os zeros a esquerda.
     */
    protected static String preparaTermoCnpj(String termo){
        return (termo == null) ? null : termo.replaceAll("[-./]+", "").replaceFirst("^0+(?!$)", "");
    }


    /**
     * Retorna a classe da entidade persistente
     *
     * @return A classe da entidade alvo
     */
    private Class<T> getClassePersistente() {
        return classeAlvo;
    }

    /**
     * Cria um parametro de pesquisa de data maior ou igual e adiciona-o a lista informada
     *
     * @param nomeCampo      O nome do campo alvo
     * @param valorParametro O valor do parametro a ser comparado
     * @param parametros     A lista de parametros
     */
    protected void povoarParametroDataMaiorIgual(String nomeCampo, Date valorParametro, List<ParametroPesquisa> parametros) {
        if (valorParametro != null) {
            parametros.add(new ParametroPesquisaDataMaiorOuIgual(nomeCampo, UtilitarioCalculoData.obterPrimeiroInstanteDia(valorParametro)));
        }
    }

    /**
     * Cria um parametro de pesquisa de data e hora maior ou igual e adiciona-o a lista informada
     *
     * @param nomeCampo      O nome do campo alvo
     * @param valorParametro O valor do parametro a ser comparado
     * @param parametros     A lista de parametros
     */
    protected void povoarParametroDataHoraMaiorIgual(String nomeCampo, Date valorParametro, List<ParametroPesquisa> parametros) {
        if (valorParametro != null) {
            parametros.add(new ParametroPesquisaDataMaiorOuIgual(nomeCampo, valorParametro));
        }
    }

    /**
     * Cria um parametro de pesquisa de data menor ou igual e adiciona-o a lista informada
     *
     * @param nomeCampo      O nome do campo alvo
     * @param valorParametro O valor do parametro a ser comparado
     * @param parametros     A lista de parametros
     */
    protected void povoarParametroDataMenorIgual(String nomeCampo, Date valorParametro, List<ParametroPesquisa> parametros) {
        if (valorParametro != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual(nomeCampo, UtilitarioCalculoData.obterUltimoInstanteDia(valorParametro)));
        }
    }

    /**
     * Cria um parametro de pesquisa de data e hora menor ou igual e adiciona-o a lista informada
     *
     * @param nomeCampo      O nome do campo alvo
     * @param valorParametro O valor do parametro a ser comparado
     * @param parametros     A lista de parametros
     */
    protected void povoarParametroDataHoraMenorIgual(String nomeCampo, Date valorParametro, List<ParametroPesquisa> parametros) {
        if (valorParametro != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual(nomeCampo, valorParametro));
        }
    }

    /**
     * Cria um parametro de pesquisa LIKE e adiciona-o a lista informada
     *
     * @param nomeCampo      O nome do campo alvo
     * @param valorParametro O valor do parametro a ser comparado
     * @param parametros     A lista de parametros
     */
    protected void povoarParametroLike(String nomeCampo, String valorParametro, List<ParametroPesquisa> parametros) {
        if (StringUtils.isNotBlank(valorParametro)) {
            parametros.add(new ParametroPesquisaLike(nomeCampo, valorParametro));
        }
    }

    /**
     * Cria um parametro de pesquisa IGUAL e adiciona-o a lista informada
     *
     * @param nomeCampo      O nome do campo alvo
     * @param valorParametro O valor do parametro a ser comparado
     * @param parametros     A lista de parametros
     */
    protected void povoarParametroIgual(String nomeCampo, Object valorParametro, List<ParametroPesquisa> parametros) {
        if (valorParametro != null || (valorParametro instanceof String && StringUtils.isNotBlank((String) valorParametro))) {
            parametros.add(new ParametroPesquisaIgual(nomeCampo, valorParametro));
        }
    }

    /**
     * Cria um parametro de pesquisa NULO e adiciona-o a lista informada
     *
     * @param nomeCampo  nome do campo alvo
     * @param condicao   condicao a ser comparada para adicionar o parametro
     * @param notNull    booleano para inverter o parametro de "isNull" para "isNotNull"
     * @param parametros lista de parametros
     */
    protected void povoarParametroNulo(String nomeCampo, Boolean condicao, boolean notNull, List<ParametroPesquisa> parametros) {
        if (condicao != null && condicao) {
            parametros.add(new ParametroPesquisaNulo(nomeCampo, notNull));
        }
    }

    /**
     * Extrai o id de uma entidade contida em um filtro de pesquisa
     *
     * @param entidade A entidade
     * @return O id da entidade ou nulo
     */
    protected Long obterIdEntidadeFiltro(EntidadeVo entidade) {
        return entidade != null ? entidade.getId() : null;
    }

    @Override
    public Class<T> getClasseDoRepositorio() {
        return (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), IRepositorioBoleiaDados.class);
    }

    /**
     * Recupera a entidade a partir de um identificador padrão
     *
     * @param identificador O identificador
     * @return A entidade obtida
     */
    @Override
    public T obterPorIdentificadorPadrao(String identificador) {
        return obterPorId(Long.parseLong(identificador));
    }
}