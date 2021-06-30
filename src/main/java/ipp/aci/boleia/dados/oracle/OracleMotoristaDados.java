package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMotoristaDados;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.enums.ClassificacaoAgregado;
import ipp.aci.boleia.dominio.enums.PeriodoVencimentoCnh;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusFrota;
import ipp.aci.boleia.dominio.enums.UtilizaAppMotorista;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMotoristaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialMotoristaVo;
import ipp.aci.boleia.dominio.vo.externo.FiltroPesquisaMotoristaExtVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaMotoristaFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioLambda;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarMesesData;
import static ipp.aci.boleia.util.UtilitarioFormatacao.obterDigitosMascara;
import static ipp.aci.boleia.util.UtilitarioFormatacao.obterInteiroMascara;
import static ipp.aci.boleia.util.UtilitarioFormatacao.obterLongMascara;

/**
 * Respositorio de entidades Motorista
 */
@Repository
public class OracleMotoristaDados extends OracleRepositorioBoleiaDados<Motorista> implements IMotoristaDados {

    private static final String QUERY_EXCLUSAO_DADOS_MOTORISTA = "DELETE FROM AbastecimentoCta ac " +
            "WHERE ac.dataProcessamento <= :dataProcessamento";

    private static final String LISTAR_MOTORISTAS_SEM_ABASTECIMENTO =
            "SELECT m " +
                    "FROM Motorista m " +
                    "WHERE trunc(m.dataCriacao) = trunc(:diasDeVerificacao) and " +
                    "NOT EXISTS (" +
                    " 	SELECT 1 " +
                    " 	FROM AbastecimentoCta ac " +
                    " 	JOIN ac.autorizacaoPagamentoImportada ap " +
                    " 	WHERE ap.motorista.id = m.id " +
                    ") " +
                    "ORDER BY m.frota, m.dataCriacao ";

    private static final String LISTAR_MOTORISTAS_INATIVOS_COM_ABASTECIMENTO =
            "SELECT m " +
                    "FROM Motorista m " +
                    "WHERE m.status = 0 AND trunc(m.dataInativacao) = trunc(:diasDeVerificacao) and " +
                    "EXISTS (" +
                    " 	SELECT 1 " +
                    " 	FROM AutorizacaoPagamento ap " +
                    " 	WHERE ap.motorista.id = m.id " +
                    ") " +
                    "ORDER BY m.frota, m.dataCriacao ";

    private static final String LISTAR_MOTORISTAS_POR_CONSOLIDADO =
            "SELECT DISTINCT m " +
            "FROM Motorista m " +
            "JOIN m.autorizacoesPagamento ap " +
            "WHERE (ap.transacaoConsolidadaPostergada.id = :idConsolidado OR ap.transacaoConsolidada.id = :idConsolidado) AND " +
            "       upper(m.nome) LIKE :nome AND " +
            "       m.excluido = false";

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Instancia o repositorio
     */
    public OracleMotoristaDados() {
        super(Motorista.class);
    }

    @Override
    public List<Motorista> listarMotoristasPorTermoEConsolidado(String termo, Long idConsolidado) {
        ParametroPesquisaIgual parametroConsolidado = new ParametroPesquisaIgual("idConsolidado", idConsolidado);
        ParametroPesquisaIgual parametroTermo = new ParametroPesquisaIgual("nome", "%" + termo.toUpperCase() + "%");
        return pesquisar(null, LISTAR_MOTORISTAS_POR_CONSOLIDADO, parametroConsolidado, parametroTermo).getRegistros();
    }

    @Override
    public ResultadoPaginado<Motorista> pesquisar(FiltroPesquisaMotoristaVo filtro) {

        List<ParametroPesquisa> parametros = new ArrayList<>();

        povoarParametroNome(filtro, parametros);
        povoarFiltroUnidade(filtro, parametros);
        povoarParametroIgual("grupo", filtro.getGrupo() != null ? filtro.getGrupo().getId() : null, parametros);
        povoarParametroIgual("cpf", obterLongMascara(filtro.getCpf()), parametros);
        povoarParametroIgual("empresaAgregada", filtro.getEmpresaAgregada() != null ? filtro.getEmpresaAgregada().getId() : null, parametros);
        povoarParametroIgual("frota.id", filtro.getFrota() != null ? filtro.getFrota().getId() : null, parametros);
        povoarParametrosPeriodoVencimento(filtro, parametros);

        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("status", StatusAtivacao.valueOf(filtro.getStatus().getName()).getValue()));
        }
        if (filtro.getClassificacao() != null && filtro.getClassificacao().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("agregado", ClassificacaoAgregado.valueOf(filtro.getClassificacao().getName()).getValue()));
        }

        povoarParametrosOrdenacao(filtro);
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginadoFrtVo<Motorista> pesquisar(FiltroPesquisaMotoristaFrtVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getCpf() != null) {
            parametros.add( new ParametroPesquisaIgual("cpf", filtro.getCpf()) );
        }

        if(StringUtils.isNotEmpty(filtro.getNomeOuApelido())){
            povoarParametroPesquisaFrotistaNomeApelido(filtro.getNomeOuApelido(), parametros);
        }

        criarParametrosPesquisa(filtro, parametros);
        parametros = povoarParametrosPeriodoVencimento(filtro, parametros);
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(filtro.getPagina() == null ? 1 : filtro.getPagina());
        paginacao.setTamanhoPagina(100);
        List<ParametroOrdenacaoColuna> parametroOrdenacaoColunas = Arrays.asList(new ParametroOrdenacaoColuna("nome"), new ParametroOrdenacaoColuna("id"));
        paginacao.setParametrosOrdenacaoColuna(parametroOrdenacaoColunas);
        ResultadoPaginado<Motorista> resultadoPaginado = pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()]));
        return new ResultadoPaginadoFrtVo<>(resultadoPaginado, paginacao.getPagina(), paginacao.getTamanhoPagina());
    }

    @Override
    public ResultadoPaginado<Motorista> pesquisar(FiltroPesquisaMotoristaExtVo filtro){
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getCpf() != null) {
            parametros.add( new ParametroPesquisaIgual("cpf", filtro.getCpf()) );
        }

        if(StringUtils.isNotEmpty(filtro.getNomeOuApelido())){
            povoarParametroPesquisaFrotistaNomeApelido(filtro.getNomeOuApelido(), parametros);
        }

        criarParametrosPesquisa(filtro, parametros);
        povoarParametroIgual("frota.cnpj", filtro.getCnpjFrota(), parametros);
        parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataAtualizacao", filtro.getDataAtualizacao()));
        parametros = povoarParametrosPeriodoVencimento(filtro, parametros);
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(filtro.getPagina() == null ? 1 : filtro.getPagina());
        paginacao.setTamanhoPagina(100);
        List<ParametroOrdenacaoColuna> parametroOrdenacaoColunas = Arrays.asList(new ParametroOrdenacaoColuna("nome"), new ParametroOrdenacaoColuna("id"));
        paginacao.setParametrosOrdenacaoColuna(parametroOrdenacaoColunas);
        ResultadoPaginado<Motorista> resultadoPaginado = pesquisar(paginacao, parametros.toArray(new ParametroPesquisa[parametros.size()]));
        return new ResultadoPaginadoFrtVo<>(resultadoPaginado, paginacao.getPagina(), paginacao.getTamanhoPagina());
    }

    @Override
    public Motorista obterPorCpf(Long cpf) {
        return pesquisarUnico(new ParametroPesquisaIgual("cpf", cpf));
    }

    @Override
    public List<Motorista> obterPorCpfSemIsolamento(Long cpf) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("id"), new ParametroPesquisaIgual("cpf", cpf));
    }

    @Override
    public List<Motorista> obterPorCpfSemIsolamento(Long cpf, Integer quantidadeResultados) {
        if (quantidadeResultados == null || quantidadeResultados < 1) {
            return new ArrayList<>();
        }
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
        paginacao.setPagina(1);
        paginacao.setTamanhoPagina(quantidadeResultados);
        paginacao.setParametrosOrdenacaoColuna(Arrays.asList(new ParametroOrdenacaoColuna("id")));
        return pesquisarSemIsolamentoDados(paginacao, new ParametroPesquisaIgual("cpf", cpf)).getRegistros();
    }

    @Override
    public Motorista obterPorCelular(String celular) {
        String celularCompleto = obterDigitosMascara(celular);
        return pesquisarUnico(
                new ParametroPesquisaIgual("dddTelefoneCelular", obterInteiroMascara(celularCompleto.substring(0, 2))),
                new ParametroPesquisaIgual("telefoneCelular", obterInteiroMascara(celularCompleto.substring(2))));
    }

    @Override
    public Motorista obterPorCpfFrotaSemIsolamento(Long cpf, Long idFrota) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgual("cpf", cpf), new ParametroPesquisaIgual("frota.id", idFrota));
    }

    @Override
    public void desvincularUnidades(Long unidadeId) {
        String update = "update Motorista m set m.unidade = null, m.status = 0 where m.unidade.id = :unidadeId";
        Query query = getGerenciadorDeEntidade().createQuery(update);
        query.setParameter("unidadeId", unidadeId);
        query.executeUpdate();
    }

    @Override
    public List<Motorista> obterAtivosPorCpfSemIsolamento(String cpf) {
        return pesquisarSemIsolamentoDados(new ParametroOrdenacaoColuna("frota.id"),
                new ParametroPesquisaIgual("cpf", cpf),
                new ParametroPesquisaIgual("frota.status", StatusFrota.ATIVO.getValue()),
                new ParametroPesquisaIgual("frota.excluido", false),
                new ParametroPesquisaIgual("status", StatusAtivacao.ATIVO.getValue()),
                new ParametroPesquisaIgual("utilizaAppMotorista", UtilizaAppMotorista.SIM.getValue()));
    }

    /**
     * Monta os parametros de pesquisa relacionados ao periodo de vencimento da CNH para Frotista API
     *
     * @param filtro     O filtro de busca informado
     * @param parametros Os parametros para a consulta
     * @return Parametros alterados
     */
    private List<ParametroPesquisa> povoarParametrosPeriodoVencimento(FiltroPesquisaMotoristaFrtVo filtro, List<ParametroPesquisa> parametros) {
        if (filtro.getVencimentoCNH() != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataVencimentoCnh", adicionarMesesData(new Date(), filtro.getVencimentoCNH())));
            if (filtro.getVencimentoCNH() != 0) {
                parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataVencimentoCnh", utilitarioAmbiente.buscarDataAmbiente()));
            }
        }
        return parametros;
    }

    /**
     * Monta os parametros de pesquisa relacionados ao periodo de vencimento da CNH
     *
     * @param filtro     O filtro de busca informado
     * @param parametros Os parametros para a consulta
     */
    private void povoarParametrosPeriodoVencimento(FiltroPesquisaMotoristaVo filtro, List<ParametroPesquisa> parametros) {
        if (filtro.getVencimentoCNH() != null && filtro.getVencimentoCNH().getName() != null) {
            PeriodoVencimentoCnh periodo = PeriodoVencimentoCnh.valueOf(filtro.getVencimentoCNH().getName());
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataVencimentoCnh", adicionarMesesData(new Date(), periodo.getMeses())));
            if (!periodo.equals(PeriodoVencimentoCnh.VENCIDO)) {
                parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataVencimentoCnh", utilitarioAmbiente.buscarDataAmbiente()));
            }
        }
    }

    /**
     * Monta os parametros de pesquisa relacionados a unidade
     *
     * @param filtro     O filtro de busca informado
     * @param parametros Os parametros para a consulta
     */
    private void povoarFiltroUnidade(FiltroPesquisaMotoristaVo filtro, List<ParametroPesquisa> parametros) {
        if (filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            if (filtro.getUnidade().getId() > 0) {
                parametros.add(new ParametroPesquisaIgual("unidade", filtro.getUnidade().getId()));
            } else {
                parametros.add(new ParametroPesquisaNulo("unidade"));
            }
        }
    }

    /**
     * Povoa os parametros de pesquisa referente a paginacao no filtro
     *
     * @param filtro filtro de pesquisa motorista
     */
    private void povoarParametrosOrdenacao(FiltroPesquisaMotoristaVo filtro) {
        if (filtro.getPaginacao() != null && CollectionUtils.isNotEmpty(filtro.getPaginacao().getParametrosOrdenacaoColuna())) {
            ParametroOrdenacaoColuna parametro = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0);
            if (parametro.getNome().contentEquals("telefoneCelular")) {
                filtro.getPaginacao().getParametrosOrdenacaoColuna().add(0, new ParametroOrdenacaoColuna("dddTelefoneCelular", parametro.getSentidoOrdenacao()));
            }
        }
    }

    /**
     * Povoa os parametros de nome e apelido da pesquisa de motorista
     * @param filtro O filtro de pesquisa
     * @param parametros A lista atual de parametros
     */
    private void povoarParametroNome(FiltroPesquisaMotoristaVo filtro, List<ParametroPesquisa> parametros) {
        if (filtro.getNome() != null) {
            parametros.add(new ParametroPesquisaOr(
                    new ParametroPesquisaLike("nome", filtro.getNome()),
                    new ParametroPesquisaLike("apelido", filtro.getNome()))
            );
        }
    }

    /**
     * Povoa os parametros de pesquisa referente ao nome ou apelido de um motorista pela frota
     * @param nomeOuApelido nome ou apelido de pesquisa do motorista
     * @param parametros lista de parametros de pesquisa para a consulta
     */
    private void povoarParametroPesquisaFrotistaNomeApelido(String nomeOuApelido, List<ParametroPesquisa> parametros) {
        parametros.add( new ParametroPesquisaOr(
                            new ParametroPesquisaLike("nome", nomeOuApelido),
                            new ParametroPesquisaLike("apelido", nomeOuApelido)
                    )
        );
    }

    /**
     * Povoa os parâmetros de pesquisa referente a pesquisa de um motorista
     * @param filtro o filtro de pesquisa
     * @param parametros lista de parâmetros de pesquisa para a consulta
     */
    private void criarParametrosPesquisa(FiltroPesquisaMotoristaFrtVo filtro, List<ParametroPesquisa> parametros){
        povoarParametroIgual("status",  filtro.getStatus(), parametros);
        povoarParametroIgual("matricula",  filtro.getMatricula(), parametros);
        povoarParametroIgual("agregado",  filtro.getClassificacao(), parametros);
        povoarParametroIgual("unidade.cnpj",  filtro.getCnpjUnidade(), parametros);
        povoarParametroIgual("grupo.id",  filtro.getCodigoGrupoOperacional(), parametros);
        povoarParametroIgual("empresaAgregada.cnpj",  filtro.getCnpjEmpresaAgregada(), parametros);
    }

    @Override
    public Motorista obterUnicoPorCpfSemIsolamento(Long cpf) {
        InformacaoPaginacao paginacao = new InformacaoPaginacao();
		paginacao.setPagina(1);
		paginacao.setTamanhoPagina(1);
		paginacao.getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("id"));

		ResultadoPaginado<Motorista> rp = pesquisarSemIsolamentoDados(paginacao, new ParametroPesquisaIgual("cpf", cpf));
		return rp.getRegistros().isEmpty() ? null : rp.getRegistros().get(0);
    }

    @Override
    public Motorista obterAtivoPorCpf(String cpf) {
        return pesquisarUnico(
                new ParametroPesquisaIgual("cpf", cpf),
                new ParametroPesquisaIgual("excluido", false),
                new ParametroPesquisaIgual("status", StatusAtivacao.ATIVO.getValue()));
    }

    @Override
    public List<Motorista> pesquisarPorCpfNome(FiltroPesquisaParcialMotoristaVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        povoarParametrosParaAutocomplete(filtro, parametros);
        return UtilitarioLambda.filtrarDistintosPorPropriedade(pesquisar(new ParametroOrdenacaoColuna("nome"), parametros.toArray(new ParametroPesquisa[parametros.size()])), Motorista::getCpf);
    }

    /**
     * Povoa os parametros de pesquisa para a busca do componente de auto-complete
     *
     * @param filtro     O filtro de pesquisa
     * @param parametros Os parametros da consulta
     */
    private void povoarParametrosParaAutocomplete(FiltroPesquisaParcialMotoristaVo filtro, List<ParametroPesquisa> parametros) {
        String termoCnpj = preparaTermoCnpj(filtro.getTermo());
        ParametroPesquisa paramCnpj = new ParametroPesquisaLike("cpf", termoCnpj);
        ParametroPesquisa paramRazao = new ParametroPesquisaLike("nome", filtro.getTermo());

        parametros.add(new ParametroPesquisaOr(paramRazao, paramCnpj));

        if (filtro.getApenasMotoristasHabilitados()) {
            parametros.add(new ParametroPesquisaIgual("status", StatusAtivacao.ATIVO.getValue()));

        }
    }

    @Override
    public Motorista  obterPorIdentificadorPadrao(String identificador) {
        FiltroPesquisaMotoristaVo filtro = new FiltroPesquisaMotoristaVo();
        filtro.setCpf(identificador);
        ResultadoPaginado<Motorista> resultadoBusca = pesquisar(filtro);
        return resultadoBusca.getTotalItems() > 0 ? resultadoBusca.getRegistros().stream().findFirst().get() : null;
    }

    @Override
    public void excluirDadosPessoais(Integer diasDeArmazenamento) {
        Query query = getGerenciadorDeEntidade().createQuery(QUERY_EXCLUSAO_DADOS_MOTORISTA);
        query.setParameter("dataProcessamento",  UtilitarioCalculoData.obterPrimeiroInstanteDia(UtilitarioCalculoData.adicionarDiasData(utilitarioAmbiente.buscarDataAmbiente(), -diasDeArmazenamento)));
        query.executeUpdate();
    }

    @Override
    public List<Motorista> obterMotoristasSemAbastecimento(Integer diasDeVerificacao) {
        ParametroPesquisaIgual parametroDiasDeVerificacao = new ParametroPesquisaIgual("diasDeVerificacao", UtilitarioCalculoData.obterPrimeiroInstanteDia(UtilitarioCalculoData.adicionarDiasData(utilitarioAmbiente.buscarDataAmbiente(), -diasDeVerificacao)));
        return pesquisar(null, LISTAR_MOTORISTAS_SEM_ABASTECIMENTO, parametroDiasDeVerificacao).getRegistros();
    }

    @Override
    public List<Motorista> obterMotoristasInativosComAbastecimento(Integer diasDeVerificacao) {
        ParametroPesquisaIgual parametroDiasDeVerificacao = new ParametroPesquisaIgual("diasDeVerificacao", UtilitarioCalculoData.adicionarDiasData(utilitarioAmbiente.buscarDataAmbiente(), -diasDeVerificacao));
        return pesquisar(null, LISTAR_MOTORISTAS_INATIVOS_COM_ABASTECIMENTO, parametroDiasDeVerificacao).getRegistros();
    }

}
