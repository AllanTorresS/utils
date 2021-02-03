package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IVeiculoDados;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.ClassificacaoAgregado;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCotaVeiculoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialVeiculoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaVeiculoVo;
import ipp.aci.boleia.dominio.vo.externo.FiltroPesquisaVeiculoExtVo;
import ipp.aci.boleia.dominio.vo.frotista.FiltroPesquisaVeiculoFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.InformacaoPaginacaoFrtVo;
import ipp.aci.boleia.dominio.vo.frotista.ResultadoPaginadoFrtVo;
import ipp.aci.boleia.util.negocio.ParametrosPesquisaBuilder;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
/**
 * Respositorio de entidades Veiculo
 */
@Repository
public class OracleVeiculoDados extends OracleRepositorioBoleiaDados<Veiculo> implements IVeiculoDados {

    @Autowired
    private UtilitarioAmbiente ambiente;

    private static final String PARAM_ID = "id";
    private static final String PARAM_PLACA = "placa";
    private static final String PARAM_TIPO_VEICULO = "subtipoVeiculo.tipoVeiculo";
    private static final String PARAM_SUB_TIPO_VEICULO = "subtipoVeiculo";
    private static final String PARAM_MARCA_VEICULO = "marcaVeiculo";
    private static final String PARAM_TIPO_COMBUSTIVEL = "combustivelMotor";
    private static final String PARAM_ANO_FABRICACAO = "modeloAnos.anoFabricacao";
    private static final String PARAM_ANO_MODELO = "modeloAnos.anoModelo";
    private static final String PARAM_MODELO = "modeloVeiculo";
    private static final String PARAM_STATUS = "status";
    private static final String PARAM_CLASSIFICACAO = "agregado";
    private static final String PARAM_CODIGO_GRUPO = "grupo.codigoCC";
    private static final String PARAM_CNPJ_UNIDADE = "unidade.cnpj";
    private static final String PARAM_CNPJ_AGREGADA = "empresaAgregada.cnpj";
    private static final String PARAM_CNPJ_FROTA = "frota.cnpj";
    private static final String PARAM_DATA_ATUALIZACAO = "dataAtualizacao";

    private static final String ORDER_BY_CLAUSE = " ORDER BY %s %s ";

    /**
     * Instancia o repositorio
     */
    public OracleVeiculoDados() {
        super(Veiculo.class);
    }

    @Override
    public ResultadoPaginado<Veiculo> pesquisar(FiltroPesquisaVeiculoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        criarParametrosBasicosConsulta(filtro, parametros);
        if (filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            if (filtro.getUnidade().getId() > 0) {
                parametros.add(new ParametroPesquisaIgual("unidade", filtro.getUnidade().getId()));
            } else {
                parametros.add(new ParametroPesquisaNulo("unidade"));
            }
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<Veiculo> pesquisarCotaVeiculo(FiltroPesquisaCotaVeiculoVo filtro) {
        String consulta = CONSULTA_COTA_VEICULO_HQL;

        List<ParametroPesquisa> parametros = new ArrayList<>();
        List<Long> idFrota = filtro.getFrota() != null ? Collections.singletonList(filtro.getFrota().getId()) : null;
        parametros.add(new ParametroPesquisaIn("idFrota", idFrota));
        parametros.add(new ParametroPesquisaIgual("tipoVeiculo", filtro.getTipoVeiculo().getId() != null ? filtro.getTipoVeiculo().getId() : null));
        Integer classificacao = filtro.getClassificacao() != null && filtro.getClassificacao().getName() != null? ClassificacaoAgregado.valueOf(filtro.getClassificacao().getName()).getValue() : null;
        parametros.add(new ParametroPesquisaIgual("classificacao", classificacao));
        parametros.add(new ParametroPesquisaIgual("placa", filtro.getPlaca() != null ? filtro.getPlaca().toUpperCase(Locale.ROOT) : null));
        parametros.add(new ParametroPesquisaIgual("empresaAgregada", filtro.getEmpresaAgregada().getId() != null ? filtro.getEmpresaAgregada().getId() : null));
        if (filtro.getUnidade() != null && filtro.getUnidade().getId() != null) {
            if (filtro.getUnidade().getId() > 0) {
                parametros.add(new ParametroPesquisaIgual("unidade", filtro.getUnidade().getId()));
            } else {
                parametros.add(new ParametroPesquisaNulo("unidade"));
            }
        }

        String ordenacao = " ";
        if (CollectionUtils.isNotEmpty(filtro.getPaginacao().getParametrosOrdenacaoColuna())) {
            ParametroOrdenacaoColuna parametroOrdenacaoColuna = filtro.getPaginacao().getParametrosOrdenacaoColuna().get(0);
            String campoOrdenacao = null;
            String direcaoOrdenacao = parametroOrdenacaoColuna.isDecrescente() ? "DESC" : "ASC";
            switch (parametroOrdenacaoColuna.getNome()) {
                case "tipoVeiculo.descricao":
                    campoOrdenacao = "tv.descricao";
                    break;
                case "classificacao.label":
                    campoOrdenacao = "(CASE WHEN v.agregado = 1 THEN 'AGREGADO' ELSE 'PROPRIO' END)";
                    break;
                case "saldo":
                    campoOrdenacao = "(" +
                                        "CASE WHEN ps.emLitros = 0 THEN " +
                                            "((CASE WHEN sv.cotaValor IS NOT NULL THEN sv.cotaValor ELSE 0 END) - (CASE WHEN sv.valorConsumido IS NOT NULL THEN sv.valorConsumido ELSE 0 END)) " +
                                        "ELSE " +
                                            "((CASE WHEN sv.cotaLitros IS NOT NULL THEN sv.cotaLitros ELSE 0 END) - (CASE WHEN sv.litrosConsumidos IS NOT NULL THEN sv.litrosConsumidos ELSE 0 END)) " +
                                        "END" +
                                     ")";
                    break;
            }
            if (campoOrdenacao != null) {
                ordenacao = String.format(ORDER_BY_CLAUSE, campoOrdenacao, direcaoOrdenacao);
            }
        }

        return pesquisar(filtro.getPaginacao() ,
                consulta.concat(ordenacao) ,
                parametros.toArray(new ParametroPesquisa[parametros.size()]));
        }

    String CONSULTA_COTA_VEICULO_HQL =
            " SELECT v "+
                " FROM Veiculo v " +
                " INNER JOIN v.frota f " +
                " INNER JOIN f.parametrosSistema ps " +
                " LEFT JOIN v.saldoVeiculo sv " +
                " LEFT JOIN v.subtipoVeiculo stv " +
                " LEFT JOIN stv.tipoVeiculo tv " +
                " LEFT JOIN v.empresaAgregada ep " +
                " LEFT JOIN v.unidade u  " +
                " WHERE (:idFrota           IS NULL OR v.frota.id IN (:idFrota)) " +
                "   AND (:tipoVeiculo       IS NULL OR tv.id = :tipoVeiculo )" +
                "   AND (:classificacao     IS NULL OR v.agregado = :classificacao )" +
                "   AND (:placa             IS NULL OR v.placa = :placa )" +
                "   AND (:empresaAgregada   IS NULL OR ep.id = :empresaAgregada )" +
                "   AND (:unidade           IS NULL OR u.id = :unidade )" +
                "   AND ps.ativo = 1 " +
                "   AND ps.parametroSistema = 8 ";

    @Override
    public ResultadoPaginadoFrtVo<Veiculo> pesquisar(FiltroPesquisaVeiculoExtVo filtro) {
        ParametroPesquisa[] parametros = criaParametrosBasicosConsultaApi(filtro)
                .adicionarParametros(new ParametroPesquisaIgual(PARAM_CNPJ_FROTA, filtro.getCnpjFrota()))
                .adicionarParametros(new ParametroPesquisaDataMaiorOuIgual(PARAM_DATA_ATUALIZACAO, filtro.getDataUltimaAtualizacao()))
                .buildArray();

        InformacaoPaginacaoFrtVo paginacao = new InformacaoPaginacaoFrtVo(
                filtro.getPagina(),
                new ParametroOrdenacaoColuna(PARAM_PLACA),
                new ParametroOrdenacaoColuna(PARAM_ID)
        );

        ResultadoPaginado<Veiculo> resultadoPaginado = pesquisar(paginacao, parametros);
        return new ResultadoPaginadoFrtVo<>(resultadoPaginado, paginacao.getPagina(), paginacao.getTamanhoPagina());
    }

    @Override
    public ResultadoPaginadoFrtVo<Veiculo> pesquisar(FiltroPesquisaVeiculoFrtVo filtro) {
        ParametroPesquisa[] parametros = criaParametrosBasicosConsultaApi(filtro)
            .buildArray();

        InformacaoPaginacaoFrtVo paginacao = new InformacaoPaginacaoFrtVo(
            filtro.getPagina(), 
            new ParametroOrdenacaoColuna(PARAM_PLACA), 
            new ParametroOrdenacaoColuna(PARAM_ID)
        );

        ResultadoPaginado<Veiculo> resultadoPaginado = pesquisar(paginacao, parametros);
        return new ResultadoPaginadoFrtVo<>(resultadoPaginado, paginacao.getPagina(), paginacao.getTamanhoPagina());
    }

    @Override
    public Veiculo buscarPorPlaca(String placa) {
        return pesquisarUnico(new ParametroPesquisaLike("placa", placa));
    }

    @Override
    public Veiculo buscarPorVeiculoIdentificadorInterno(Long idFrota, String identificadorInterno) {
        return pesquisarUnico(
                new ParametroPesquisaIgual("frota.id", idFrota),
                new ParametroPesquisaIgual("identificadorInterno", identificadorInterno));
    }


    @Override
    public List<Veiculo> buscarVeiculosPorPlaca(String placa) {
        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaIgual("frota.excluido", false),
                new ParametroPesquisaIgualIgnoreCase("placa", placa));
    }

    @Override
    public ResultadoPaginado<Veiculo> buscarVeiculoComMesmaPlaca(FiltroPesquisaVeiculoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();

        parametros.add(new ParametroPesquisaIgualIgnoreCase("placa", filtro.getPlaca()));

        if (filtro.getFrota() != null && filtro.getFrota().getId() != null) {
            parametros.add(new ParametroPesquisaIgual("frota", filtro.getFrota().getId()));
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public Veiculo buscarPorPlacaFrota(String placa, Long idFrota) {
        return pesquisarUnico(new ParametroPesquisaIgualIgnoreCase("placa", placa), new ParametroPesquisaIgual("frota.id", idFrota));
    }

    @Override
    public Veiculo buscarPorPlacaFrotaSemIsolamento(String placa, Long idFrota) {
        return pesquisarUnicoSemIsolamentoDados(new ParametroPesquisaIgualIgnoreCase("placa", placa), new ParametroPesquisaIgual("frota.id", idFrota));
    }

    @Override
    public void desvincularUnidades(Long unidadeId) {
        String update = "update Veiculo v set v.unidade = null, v.status = 0 where v.unidade.id = :unidadeId";
        Query query = getGerenciadorDeEntidade().createQuery(update);
        query.setParameter("unidadeId", unidadeId);
        query.executeUpdate();
    }

    @Override
    public List<Veiculo> obterVeiculosSemConsumoEstimado(Long idFrota) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaIgual("frota.id", idFrota), new ParametroPesquisaNulo("consumoEstimadoLitro"));
    }

    @Override
    public List<Veiculo> obterVeiculosAgregadosDaFrota(Long idFrota) {
        return pesquisar(new ParametroOrdenacaoColuna("placa"), new ParametroPesquisaIgual("frota.id", idFrota), new ParametroPesquisaIgual("agregado", ClassificacaoAgregado.AGREGADO.getValue()));
    }

    @Override
    public List<Veiculo> obterVeiculosPropriosDaFrota(Long idFrota) {
        return pesquisar(new ParametroOrdenacaoColuna("placa"), new ParametroPesquisaIgual("frota.id", idFrota), new ParametroPesquisaIgual("agregado", ClassificacaoAgregado.PROPRIO.getValue()));
    }

    @Override
    public List<Veiculo> obterTodosDaFrota(Long idFrota) {
        return pesquisar(new ParametroOrdenacaoColuna("placa"), new ParametroPesquisaIgual("frota.id", idFrota));
    }

    @Override
    public List<Veiculo> obterVeiculosComSaldo(Long idFrota) {
        return pesquisar((ParametroOrdenacaoColuna) null, new ParametroPesquisaFetch("saldoVeiculo"), new ParametroPesquisaIgual("frota", idFrota));
    }

    /**
     * Cria os parametros basicos da consulta
     * @param filtro O filtro de pesquisa
     * @param parametros A lista de parametros da consulta
     */
    private void criarParametrosBasicosConsulta(FiltroPesquisaVeiculoVo filtro, List<ParametroPesquisa> parametros) {
        povoarParametroIgual("frota", filtro.getFrota() != null ? filtro.getFrota().getId() : null, parametros);
        povoarParametroLike("placa", filtro.getPlaca(), parametros);
        povoarParametroIgual("subtipoVeiculo.tipoVeiculo", filtro.getTipoVeiculo() != null ? filtro.getTipoVeiculo().getId() : null, parametros);
        povoarParametroIgual("subtipoVeiculo", filtro.getSubtipoVeiculo() != null ? filtro.getSubtipoVeiculo().getId() : null, parametros);
        povoarParametroIgual("marcaVeiculo", obterIdEntidadeFiltro(filtro.getMarca()), parametros);
        povoarParametroIgual("modeloAnos.modelo", obterIdEntidadeFiltro(filtro.getModelo()), parametros);
        povoarParametroIgual("combustivelMotor", obterIdEntidadeFiltro(filtro.getTipoCombustivel()), parametros);
        povoarParametroIgual("modeloAnos.anoFabricacao", filtro.getAnoFabricacao(), parametros);
        povoarParametroIgual("modeloAnos.anoModelo", filtro.getAnoModelo(), parametros);
        povoarParametroIgual("modeloVeiculo", obterIdEntidadeFiltro(filtro.getModelo()), parametros);
        Integer status = filtro.getStatus() != null && filtro.getStatus().getName() != null ? StatusAtivacao.valueOf(filtro.getStatus().getName()).getValue() : null;
        povoarParametroIgual("status", status, parametros);
        Integer classificacao = filtro.getClassificacao() != null && filtro.getClassificacao().getName() != null? ClassificacaoAgregado.valueOf(filtro.getClassificacao().getName()).getValue() : null;
        povoarParametroIgual("agregado", classificacao, parametros);
        povoarParametroIgual("empresaAgregada", obterIdEntidadeFiltro(filtro.getEmpresaAgregada()), parametros);
        povoarParametroNulo("consumoEstimadoLitro", filtro.getSemConsumo(), false , parametros);
        povoarParametroIgual("grupo", obterIdEntidadeFiltro(filtro.getGrupoOperacional()), parametros);
    }

    /**
     * Cria os parametros basicos de consulta para API
     * @param filtro O filtro de pesquisa
     * @return A lista de parâmetros de consulta
     */
    private ParametrosPesquisaBuilder criaParametrosBasicosConsultaApi(FiltroPesquisaVeiculoFrtVo filtro){
        return new ParametrosPesquisaBuilder()
                .adicionarParametros(filtro.getPlaca(), v -> new ParametroPesquisaIgual(PARAM_PLACA, v))
                .adicionarParametros(filtro.getStatus(), v -> new ParametroPesquisaIgual(PARAM_STATUS, v))
                .adicionarParametros(filtro.getTipo(), v -> new ParametroPesquisaIgual(PARAM_TIPO_VEICULO, v))
                .adicionarParametros(filtro.getSubtipo(), v -> new ParametroPesquisaIgual(PARAM_SUB_TIPO_VEICULO, v))
                .adicionarParametros(filtro.getMarca(), v -> new ParametroPesquisaIgual(PARAM_MARCA_VEICULO, v))
                .adicionarParametros(filtro.getModelo(), v -> new ParametroPesquisaIgual(PARAM_MODELO, v))
                .adicionarParametros(filtro.getAnoModelo(), v -> new ParametroPesquisaIgual(PARAM_ANO_MODELO, v))
                .adicionarParametros(filtro.getAnoFabricacao(), v -> new ParametroPesquisaIgual(PARAM_ANO_FABRICACAO, v))
                .adicionarParametros(filtro.getTipoCombustivel(), v -> new ParametroPesquisaIgual(PARAM_TIPO_COMBUSTIVEL, v))
                .adicionarParametros(filtro.getClassificacao(), v -> new ParametroPesquisaIgual(PARAM_CLASSIFICACAO, v))
                .adicionarParametros(filtro.getCnpjEmpresaAgregada(), v -> new ParametroPesquisaIgual(PARAM_CNPJ_AGREGADA, v))
                .adicionarParametros(filtro.getCodigoGrupoOperacional(), v -> new ParametroPesquisaIgual(PARAM_CODIGO_GRUPO, v))
                .adicionarParametros(filtro.getCnpjUnidade(), v -> new ParametroPesquisaIgual(PARAM_CNPJ_UNIDADE, v));
    }

    @Override
    public Veiculo obterPorIdentificadorPadrao(String identificador) {
        FiltroPesquisaVeiculoVo filtro = new FiltroPesquisaVeiculoVo();
        filtro.setPlaca(identificador);
        ResultadoPaginado<Veiculo> resultadoBusca = pesquisar(filtro);
        return resultadoBusca.getTotalItems() > 0 ? resultadoBusca.getRegistros().stream().findFirst().get() : null;
    }
    @Override
    public List<Veiculo> obterPorIdentificadorInterno(FiltroPesquisaParcialVeiculoVo filtro) {
        return pesquisar((ParametroOrdenacaoColuna) null,
                new ParametroPesquisaLike("identificadorInterno", filtro.getTermo()));
    }
}
