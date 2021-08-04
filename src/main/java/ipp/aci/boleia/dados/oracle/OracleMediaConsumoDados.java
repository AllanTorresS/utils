package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IMediaConsumoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.ClassificacaoAgregado;
import ipp.aci.boleia.dominio.enums.StatusAutorizacao;
import ipp.aci.boleia.dominio.enums.TipoConsumo;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.vo.EntidadeVo;
import ipp.aci.boleia.dominio.vo.EnumVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMediaConsumoVo;
import ipp.aci.boleia.dominio.vo.MediaConsumoVo;
import ipp.aci.boleia.dominio.vo.VolumeAbastecidoTipoCombustivelVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.negocio.ParametrosPesquisaBuilder;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterUltimoInstanteDia;

/**
 * Respositorio de entidades AutorizacaoPagamento
 */

@Repository
public class OracleMediaConsumoDados extends OracleRepositorioBoleiaDados<AutorizacaoPagamento> implements IMediaConsumoDados {
    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    private static final String QUERY_PESQUISAR_MEDIA_CONSUMO_MOTORISTA =
            "SELECT new ipp.aci.boleia.dominio.vo.MediaConsumoVo(" +
                    "a.nomeMotorista, " +
                    "a.cpfMotorista, " +
                    "a.cnpjUnidadeMotorista,        " +
                    "a.nomeUnidadeMotorista,        " +
                    "a.codigoGrupoMotorista,        " +
                    "a.nomeGrupoMotorista,          " +
                    "a.agregadoMotorista,           " +
                    "a.razaoSocialEmpresaMotorista, " +
                    "a.frota.id AS idFrota,         " +
                    "CASE WHEN MAX(a.hodometro) - MIN(a.hodometro) > 0 " +
                    "THEN (MAX(a.hodometro) - MIN(a.hodometro)) " +
                    "ELSE (MAX(a.horimetro) - MIN(a.horimetro))" +
                    "END AS mediaHorHod, " +
                    "SUM(a.totalLitrosAbastecimento) AS mediaTotalLitrosAbastecimento, " +
                    "CASE WHEN MAX(a.hodometro) - MIN(a.hodometro) > 0 " +
                    "THEN " + TipoConsumo.KML.getValue() + " " +
                    "ELSE " + TipoConsumo.LH.getValue() + " " +
                    "END AS tipoConsumo, " +
                    "a.razaoSocialFrota, " +
                    "a.cnpjFrota) " +
                    "FROM AutorizacaoPagamento a " +
                    "WHERE " +
                    "(a.valorTotal IS NULL or a.valorTotal >= 0) AND " +
                    "(valorUnitarioAbastecimento IS NULL or a.valorUnitarioAbastecimento >= 0) AND " +
                    "a.status = " + StatusAutorizacao.AUTORIZADO.getValue() + " AND " +
                    "a.dataRequisicao BETWEEN :de and :ate AND " +
                    "( :idFrota IS NULL OR a.frota.id = :idFrota) AND " +
                    "( :motorista IS NULL OR " + String.format(UtilitarioFormatacao.TO_LOWER, String.format(UtilitarioFormatacao.REMOVER_ACENTO, "a.nomeMotorista")) + " LIKE :motorista OR cast(a.cpfMotorista as string) LIKE :motorista) AND " +
                    "( :motoristaClassificacao IS NULL " +
                    "OR :motoristaClassificacao = " + ClassificacaoAgregado.AGREGADO.getValue() + " AND a.cnpjEmpresaMotorista IS NOT NULL " +
                    "OR :motoristaClassificacao = " + ClassificacaoAgregado.PROPRIO.getValue() + " AND a.cnpjEmpresaMotorista IS NULL) AND " +
                    "( :motoristaUnidade IS NULL OR a.nomeUnidadeMotorista = :motoristaUnidade) AND " +
                    "( :motoristaGrupo IS NULL OR a.nomeGrupoMotorista = :motoristaGrupo) AND " +
                    "( :motoristaEmpresa IS NULL OR a.razaoSocialEmpresaMotorista = :motoristaEmpresa) " +
                    " %s " +
                    "GROUP BY " +
                    " a.nomeMotorista, a.cpfMotorista, a.cnpjUnidadeMotorista, a.razaoSocialFrota, a.cnpjFrota, " +
                    "a.nomeUnidadeMotorista, a.codigoGrupoMotorista, a.nomeGrupoMotorista, a.agregadoMotorista, a.razaoSocialEmpresaMotorista, a.frota.id " +
                    "HAVING (MAX(a.hodometro) - MIN(a.hodometro) > 0 OR MAX(a.horimetro) - MIN(a.horimetro) > 0) AND " +
                    "( :tipoConsumo IS NULL OR " +
                    ":tipoConsumo = " + TipoConsumo.KML.getValue() + " AND " +
                    "(MAX(a.hodometro) - MIN(a.hodometro) > 0) " +
                    "OR :tipoConsumo = " + TipoConsumo.LH.getValue() + " AND " +
                    "(MAX(a.horimetro) - MIN(a.horimetro) > 0) ) " +
                    "ORDER BY  ";


    private static final String QUERY_PESQUISAR_ULTIMO_ABASTECIMENTO_MEDIA_CONSUMO_MOTORISTA =
            "FROM AutorizacaoPagamento a " +
                    "WHERE a.status = " + StatusAutorizacao.AUTORIZADO.getValue() + " AND " +
                    "(a.valorTotal IS NULL or a.valorTotal >= 0) AND " +
                    "(valorUnitarioAbastecimento IS NULL or a.valorUnitarioAbastecimento >= 0) AND " +
                    "a.dataRequisicao BETWEEN :de and :ate AND " +
                    "a.cpfMotorista = :cpfMotorista AND " +
                    "( :motoristaClassificacao IS NULL OR " +
                    ":motoristaClassificacao = " + ClassificacaoAgregado.AGREGADO.getValue() + " AND " +
                    "a.cnpjEmpresaMotorista IS NOT NULL OR " +
                    ":motoristaClassificacao = " + ClassificacaoAgregado.PROPRIO.getValue() + " AND " +
                    "a.cnpjEmpresaMotorista IS NULL) AND " +
                    "( :razaoSocialEmpresaMotorista IS NULL OR a.razaoSocialEmpresaMotorista = :razaoSocialEmpresaMotorista) AND " +
                    "( :cnpjUnidadeMotorista IS NULL OR a.cnpjUnidadeMotorista = :cnpjUnidadeMotorista) AND " +
                    "( :codigoGrupoMotorista IS NULL OR a.codigoGrupoMotorista = :codigoGrupoMotorista) AND " +
                    "a.frota.id = :idFrota "  +
                    " %s " +
                    "ORDER BY a.dataRequisicao DESC ";


    private static final String QUERY_PESQUISAR_MEDIA_CONSUMO_VEICULO =
            "SELECT new ipp.aci.boleia.dominio.vo.MediaConsumoVo(" +
                    "a.placaVeiculo, " +
                    "a.nomeUnidadeVeiculo,          " +
                    "a.cnpjUnidadeVeiculo,          " +
                    "a.codigoGrupoVeiculo,          " +
                    "a.nomeGrupoVeiculo,            " +
                    "a.subTipoVeiculo,              " +
                    "a.agregadoVeiculo,             " +
                    "a.razaoSocialEmpresaVeiculo,   " +
                    "a.frota.id AS idFrota,         " +
                    "CASE WHEN MAX(a.hodometro) - MIN(a.hodometroAnterior) > 0 " +
                    "THEN cast( (MAX(a.hodometro) - MIN(a.hodometroAnterior) ) as big_decimal) " +
                    "ELSE (MAX(a.horimetro) - MIN(a.horimetroAnterior))" +
                    "END AS mediaHorHod, " +
                    "SUM(a.totalLitrosAbastecimento) AS mediaTotalLitrosAbastecimento, " +
                    "CASE WHEN MAX(a.hodometro) - MIN(a.hodometroAnterior) > 0 " +
                    "THEN " + TipoConsumo.KML.getValue() + " " +
                    "ELSE " + TipoConsumo.LH.getValue() + " " +
                    "END AS tipoConsumo, " +
                    "a.razaoSocialFrota, " +
                    "a.cnpjFrota) " +
                    "FROM AutorizacaoPagamento a " +
                    "WHERE a.status = " + StatusAutorizacao.AUTORIZADO.getValue() + " AND " +
                    "(a.hodometro IS NOT NULL OR a.horimetro IS NOT NULL) AND "+
                    "(a.valorTotal IS NULL or a.valorTotal >= 0) AND " +
                    "(a.valorUnitarioAbastecimento IS NULL or a.valorUnitarioAbastecimento >= 0) AND " +
                    "a.dataRequisicao BETWEEN :de and :ate AND " +
                    "( :idFrota IS NULL OR a.frota.id = :idFrota) AND " +
                    "( :placa IS NULL OR " + String.format(UtilitarioFormatacao.TO_LOWER, String.format(UtilitarioFormatacao.REMOVER_ACENTO, "a.placaVeiculo")) + " LIKE :placa ) AND " +
                    "( :veiculoClassificacao IS NULL " +
                    "OR :veiculoClassificacao = " + ClassificacaoAgregado.AGREGADO.getValue() + " AND a.cnpjEmpresaVeiculo IS NOT NULL " +
                    "OR :veiculoClassificacao = " + ClassificacaoAgregado.PROPRIO.getValue() + " AND a.cnpjEmpresaVeiculo IS NULL) AND " +
                    "( :veiculoUnidade IS NULL OR a.nomeUnidadeVeiculo = :veiculoUnidade) AND " +
                    "( :veiculoGrupo IS NULL OR a.nomeGrupoVeiculo = :veiculoGrupo) AND " +
                    "( :veiculoEmpresa IS NULL OR a.razaoSocialEmpresaVeiculo = :veiculoEmpresa) AND" +
                    "( :subTipoVeiculo IS NULL OR a.subTipoVeiculo = :subTipoVeiculo)" +
                    " %s " +
                    "GROUP BY a.placaVeiculo, a.nomeUnidadeVeiculo, a.cnpjUnidadeVeiculo, a.razaoSocialFrota, a.cnpjFrota, " +
                    "a.codigoGrupoVeiculo, a.nomeGrupoVeiculo, a.subTipoVeiculo, a.agregadoVeiculo,  a.razaoSocialEmpresaVeiculo, a.frota.id " +
                    "HAVING (MAX(a.hodometro) - MIN(a.hodometroAnterior) > 0 OR MAX(a.horimetro) - MIN(a.horimetroAnterior) > 0) AND " +
                    "( :tipoConsumo IS NULL OR " +
                    ":tipoConsumo = " + TipoConsumo.KML.getValue() + " AND " +
                    "(MAX(a.hodometro) - MIN(a.hodometroAnterior) > 0) " +
                    "OR :tipoConsumo = " + TipoConsumo.LH.getValue() + " AND " +
                    "(MAX(a.horimetro) - MIN(a.horimetroAnterior) > 0) ) " +
                    "ORDER BY  ";


    private static final String QUERY_PESQUISAR_PRIMEIRO_ABASTECIMENTO_MEDIA_CONSUMO_VEICULO =
            "FROM AutorizacaoPagamento a " +
                    "WHERE a.status = " + StatusAutorizacao.AUTORIZADO.getValue() + " AND " +
                    "(a.valorTotal IS NULL or a.valorTotal >= 0) AND " +
                    "(a.valorUnitarioAbastecimento IS NULL or a.valorUnitarioAbastecimento >= 0) AND " +
                    "a.dataRequisicao BETWEEN :de and :ate AND " +
                    "( :idFrota IS NULL OR a.frota.id = :idFrota) AND " +
                    "( :placa IS NULL OR " + String.format(UtilitarioFormatacao.TO_LOWER, String.format(UtilitarioFormatacao.REMOVER_ACENTO, "a.placaVeiculo")) + " LIKE :placa ) AND " +
                    "( :veiculoClassificacao IS NULL " +
                    "OR :veiculoClassificacao = " + ClassificacaoAgregado.AGREGADO.getValue() + " AND a.cnpjEmpresaVeiculo IS NOT NULL " +
                    "OR :veiculoClassificacao = " + ClassificacaoAgregado.PROPRIO.getValue() + " AND a.cnpjEmpresaVeiculo IS NULL) AND " +
                    "( :cnpjUnidadeVeiculo IS NULL OR a.cnpjUnidadeVeiculo = :cnpjUnidadeVeiculo) AND " +
                    "( :codigoGrupoVeiculo IS NULL OR a.codigoGrupoVeiculo = :codigoGrupoVeiculo) AND " +
                    "( :razaoSocialEmpresaVeiculo IS NULL OR a.razaoSocialEmpresaVeiculo = :razaoSocialEmpresaVeiculo) " +
                    " %s " +
                    " ORDER BY a.dataRequisicao ASC ";

    private static final String QUERY_PESQUISAR_MEDIA_CONSUMO_MOTORISTA_VEICULO =
            "SELECT new ipp.aci.boleia.dominio.vo.MediaConsumoVo(" +
                    "upper(a.nomeMotorista) AS nomeMotorista, " +
                    "a.cpfMotorista, " +
                    "a.cnpjUnidadeMotorista, " +
                    "a.nomeUnidadeMotorista, " +
                    "a.codigoGrupoMotorista, " +
                    "a.nomeGrupoMotorista, " +
                    "a.placaVeiculo, " +
                    "a.subTipoVeiculo, " +
                    "a.agregadoMotorista,           " +
                    "a.razaoSocialEmpresaMotorista, " +
                    "a.agregadoVeiculo,             " +
                    "a.razaoSocialEmpresaVeiculo,   " +
                    "a.frota.id AS idFrota, " +
                    "CASE WHEN MAX(a.hodometro) - MIN(a.hodometro) > 0 " +
                    "THEN (MAX(a.hodometro) - MIN(a.hodometro)) " +
                    "ELSE (MAX(a.horimetro) - MIN(a.horimetro))" +
                    "END AS mediaHorHod, " +
                    "SUM(a.totalLitrosAbastecimento) AS mediaTotalLitrosAbastecimento, " +
                    "CASE WHEN MAX(a.hodometro) - MIN(a.hodometro) > 0 " +
                    "THEN " + TipoConsumo.KML.getValue() + " " +
                    "ELSE " + TipoConsumo.LH.getValue() + " " +
                    "END AS tipoConsumo, " +
                    "a.razaoSocialFrota, " +
                    "a.cnpjFrota) " +
                    "FROM AutorizacaoPagamento a " +
                    "WHERE a.status = " + StatusAutorizacao.AUTORIZADO.getValue() + " AND " +
                    "(a.valorTotal IS NULL or a.valorTotal >= 0) AND " +
                    "(a.valorUnitarioAbastecimento IS NULL or a.valorUnitarioAbastecimento >= 0) AND " +
                    "a.dataRequisicao BETWEEN :de and :ate AND " +
                    "( :idFrota IS NULL OR a.frota.id = :idFrota) AND " +
                    "( :motorista IS NULL OR "  + String.format(UtilitarioFormatacao.TO_LOWER, String.format(UtilitarioFormatacao.REMOVER_ACENTO, "a.nomeMotorista")) + " LIKE :motorista OR cast(a.cpfMotorista as string) LIKE :motorista) AND " +
                    "( :motoristaClassificacao IS NULL " +
                    "OR :motoristaClassificacao = " + ClassificacaoAgregado.AGREGADO.getValue() + " AND a.cnpjEmpresaMotorista IS NOT NULL " +
                    "OR :motoristaClassificacao = " + ClassificacaoAgregado.PROPRIO.getValue() + " AND a.cnpjEmpresaMotorista IS NULL) AND " +
                    "( :motoristaUnidade IS NULL OR a.nomeUnidadeMotorista = :motoristaUnidade) AND " +
                    "( :motoristaGrupo IS NULL OR a.nomeGrupoMotorista = :motoristaGrupo) AND " +
                    "( :motoristaEmpresa IS NULL OR a.razaoSocialEmpresaMotorista = :motoristaEmpresa) AND " +
                    "( :subTipoVeiculo IS NULL OR a.subTipoVeiculo = :subTipoVeiculo) AND " +
                    "( :placa IS NULL OR " + String.format(UtilitarioFormatacao.TO_LOWER, String.format(UtilitarioFormatacao.REMOVER_ACENTO, "a.placaVeiculo")) + " LIKE :placa ) " +
                    " %s " +
                    "GROUP BY upper(a.nomeMotorista), a.cpfMotorista, a.cnpjUnidadeMotorista, a.razaoSocialFrota, a.cnpjFrota, " +
                    "a.nomeUnidadeMotorista, a.codigoGrupoMotorista, a.nomeGrupoMotorista,  a.agregadoMotorista, " +
                    "a.razaoSocialEmpresaMotorista, a.agregadoVeiculo,  a.razaoSocialEmpresaVeiculo,   a.frota.id, a.placaVeiculo, a.subTipoVeiculo " +
                    "HAVING (MAX(a.hodometro) - MIN(a.hodometro) > 0 OR MAX(a.horimetro) - MIN(a.horimetro) > 0) AND " +
                    "( :tipoConsumo IS NULL OR " +
                    ":tipoConsumo = " + TipoConsumo.KML.getValue() + " AND " +
                    "(MAX(a.hodometro) - MIN(a.hodometro) > 0) " +
                    "OR :tipoConsumo = " + TipoConsumo.LH.getValue() + " AND " +
                    "(MAX(a.horimetro) - MIN(a.horimetro) > 0) ) " +
                    "ORDER BY  ";

    private static final String QUERY_PESQUISAR_ULTIMO_ABASTECIMENTO_MEDIA_CONSUMO_MOTORISTA_VEICULO =
            "FROM AutorizacaoPagamento a " +
                    "WHERE a.status = " + StatusAutorizacao.AUTORIZADO.getValue() + " AND " +
                    "(a.valorTotal IS NULL or a.valorTotal >= 0) AND " +
                    "(valorUnitarioAbastecimento IS NULL or a.valorUnitarioAbastecimento >= 0) AND " +
                    "a.dataRequisicao BETWEEN :de and :ate AND " +
                    "( :idFrota IS NULL OR a.frota.id = :idFrota) AND " +
                    "(a.cpfMotorista = :cpfMotorista) AND " +
                    "( :cnpjUnidadeMotorista IS NULL OR a.cnpjUnidadeMotorista = :cnpjUnidadeMotorista) AND " +
                    "( :codigoGrupoMotorista IS NULL OR a.codigoGrupoMotorista = :codigoGrupoMotorista) AND " +
                    "( :razaoSocialEmpresaMotorista IS NULL OR a.razaoSocialEmpresaMotorista = :razaoSocialEmpresaMotorista) AND " +
                    "( :razaoSocialEmpresaVeiculo IS NULL OR a.razaoSocialEmpresaVeiculo = :razaoSocialEmpresaVeiculo) AND " +
                    "( :subTipoVeiculo IS NULL OR a.subTipoVeiculo = :subTipoVeiculo) AND " +
                    "( :placa IS NULL OR " + String.format(UtilitarioFormatacao.TO_LOWER, String.format(UtilitarioFormatacao.REMOVER_ACENTO, "a.placaVeiculo")) + " LIKE :placa ) " +
                    " %s " +
                    "ORDER BY a.dataRequisicao DESC ";

    private static final String QUERY_VOLUME_ABASTECIDO_POR_TIPO_COMBUSTIVEL_EM_PERIODO = ""
            + "SELECT new ipp.aci.boleia.dominio.vo.VolumeAbastecidoTipoCombustivelVo("
            + "(SUM(CASE WHEN aut.dataRequisicao BETWEEN (CURRENT_TIMESTAMP - :periodo) AND CURRENT_TIMESTAMP THEN aut.totalLitrosAbastecimento END)),"
            + "(SUM(CASE WHEN aut.dataRequisicao BETWEEN (CURRENT_TIMESTAMP - :periodoAnterior) AND (CURRENT_TIMESTAMP - :periodo) THEN aut.totalLitrosAbastecimento END)),"
            + "c.descricao"
            + ") "
            + "FROM ItemAutorizacaoPagamento ite INNER JOIN ite.autorizacaoPagamento aut INNER JOIN aut.frota f INNER JOIN ite.combustivel c "
            + "WHERE aut.status = 1 "
            + "AND aut.nomePv IS NOT NULL "
            + "AND aut.totalLitrosAbastecimento > 0 "
            + "AND ite.tipoItem = 1 "
            + "AND f.id = :idFrota "
            + "GROUP BY c.descricao";

    private static final String PARAM_ORDENACAO_PADRAO_MEDIA_CONSUMO_MOTORISTA = "nomeMotorista";
    private static final String PARAM_ORDENACAO_PADRAO_MEDIA_CONSUMO_MOTORISTA_UPPER_CASE = "upper("+PARAM_ORDENACAO_PADRAO_MEDIA_CONSUMO_MOTORISTA+")";
    private static final String PARAM_ORDENACAO_PADRAO_MEDIA_CONSUMO_VEICULO = "placaVeiculo";
    private static final String CLAUSE_IN_SUBTIPOS = "AND a.subTipoVeiculo IN (:subTiposVeiculos) ";
    private static final String CLAUSE_UNIDADE_MOTORISTA_MATRIZ = "AND a.nomeUnidadeMotorista IS NULL ";
    private static final String CLAUSE_UNIDADE_VEICULO_MATRIZ = "AND a.nomeUnidadeVeiculo IS NULL ";
    private static final String ORDENACAO_GRUPO_MOTORISTA = "CONCAT(codigoGrupoMotorista, ' - ', nomeGrupoMotorista)";
    private static final String ORDENACAO_GRUPO_VEICULO = "CONCAT(codigoGrupoVeiculo, ' - ', nomeGrupoVeiculo)";

    /**
     * Construtor default da classe OracleMediaConsumoDados.
     */
    public OracleMediaConsumoDados() {
        super(AutorizacaoPagamento.class);
    }


    @Override
    public ResultadoPaginado<MediaConsumoVo> pesquisarMediaConsumoMotorista(FiltroPesquisaMediaConsumoVo filtro) {
        TipoConsumo tipoConsumo = EnumVo.fromName(TipoConsumo.class, filtro.getTipoConsumo());
        Integer tipoConsumoInteger = tipoConsumo != null ? tipoConsumo.getValue() : null;
        ClassificacaoAgregado motoristaClassificacao = EnumVo.fromName(ClassificacaoAgregado.class, filtro.getClassificacaoMotorista());
        Integer motoristaClassificacaoIntegeer = motoristaClassificacao != null ? motoristaClassificacao.getValue() : null;

        ParametrosPesquisaBuilder builder = new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaIgual("de", obterPrimeiroInstanteDia(filtro.getDe())),
                        new ParametroPesquisaIgual("ate", obterUltimoInstanteDia(filtro.getAte())),
                        new ParametroPesquisaIgual("idFrota", filtro.getIdFrota()),
                        new ParametroPesquisaLike("motorista", filtro.getMotorista()),
                        new ParametroPesquisaIgual("motoristaUnidade", EntidadeVo.obterNome(filtro.getUnidadeMotorista())),
                        new ParametroPesquisaIgual("motoristaGrupo", EntidadeVo.obterNome(filtro.getGrupoMotorista())),
                        new ParametroPesquisaIgual("motoristaEmpresa", EntidadeVo.obterNome(filtro.getEmpresaMotorista())),
                        new ParametroPesquisaIgual("motoristaClassificacao", motoristaClassificacaoIntegeer),
                        new ParametroPesquisaIgual("tipoConsumo", tipoConsumoInteger)
                );

        StringBuffer strBufferOutrasClausulas = new StringBuffer(StringUtils.EMPTY);

        if(filtro.isUnidadeMotoristaMatriz()) {
            strBufferOutrasClausulas.append(CLAUSE_UNIDADE_MOTORISTA_MATRIZ);
        }

        if (utilitarioAmbiente.getUsuarioLogado().isInterno() && utilitarioAmbiente.getUsuarioLogado().possuiFrotasAssociadas()) {
            strBufferOutrasClausulas.append(" AND a.frota.id IN (:idsFrota) ");
            builder.adicionarParametros(new ParametroPesquisaIn("idsFrota", utilitarioAmbiente.getUsuarioLogado().listarIdsFrotasAssociadas()));
        }

        String query = obterQueryPesquisarMediaConsumo(
                String.format(QUERY_PESQUISAR_MEDIA_CONSUMO_MOTORISTA, strBufferOutrasClausulas.toString()),
                PARAM_ORDENACAO_PADRAO_MEDIA_CONSUMO_MOTORISTA,
                filtro.getPaginacao()
        );

        ResultadoPaginado<MediaConsumoVo> mediaConsumoVoResultado = pesquisar(filtro.getPaginacao(), query, MediaConsumoVo.class, builder.buildArray());
        mediaConsumoVoResultado.setRegistros(mediaConsumoVoResultado.getRegistros().stream()
                .map(vo-> processarMediaConsumoMotorista(vo,filtro)).filter(vo -> vo != null)
                .collect(Collectors.toList()));
        return mediaConsumoVoResultado;
    }

    /**
     * Processa a média de consumo do motorista
     * @param vo  as informações do motorista
     * @param filtro o filtro de média de consumo
     * @return a média de consumo do motorista
     */
    private MediaConsumoVo processarMediaConsumoMotorista(MediaConsumoVo vo, FiltroPesquisaMediaConsumoVo filtro)  {
        ParametrosPesquisaBuilder builder = new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaIgual("de", obterPrimeiroInstanteDia(filtro.getDe())),
                        new ParametroPesquisaIgual("ate", obterUltimoInstanteDia(filtro.getAte())),
                        new ParametroPesquisaIgual("idFrota", vo.getIdFrota()),
                        new ParametroPesquisaIgual("cpfMotorista", vo.getCpfMotorista()),
                        new ParametroPesquisaIgual("cnpjUnidadeMotorista", vo.getCnpjUnidadeMotorista()),
                        new ParametroPesquisaIgual("codigoGrupoMotorista", vo.getCodigoGrupoMotorista()),
                        new ParametroPesquisaIgual("razaoSocialEmpresaMotorista",vo.getRazaoSocialEmpresaMotorista()),
                        new ParametroPesquisaIgual("motoristaClassificacao", vo.getClassificacaoMotorista())
                );

        String outrasClausulas = "";

        if(filtro.isUnidadeMotoristaMatriz()) {
            outrasClausulas += CLAUSE_UNIDADE_MOTORISTA_MATRIZ;
        }

        InformacaoPaginacao infoPag=new InformacaoPaginacao();
        infoPag.setTamanhoPagina(1);
        ResultadoPaginado<AutorizacaoPagamento> ultimosAbastecimentos = pesquisar(infoPag, String.format(QUERY_PESQUISAR_ULTIMO_ABASTECIMENTO_MEDIA_CONSUMO_MOTORISTA, outrasClausulas), AutorizacaoPagamento.class, builder.buildArray());

        if(ultimosAbastecimentos.getRegistros() == null || ultimosAbastecimentos.getTotalItems() < 1
                || vo.getMediaTotalLitrosAbastecimento().compareTo(BigDecimal.ZERO) == 0
                ||vo.getMediaTotalLitrosAbastecimento().subtract(ultimosAbastecimentos.getRegistros().get(0).getTotalLitrosAbastecimento()).compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        vo.setMedia(vo.getMediaHorHod()
                .divide((vo.getMediaTotalLitrosAbastecimento()
                        .subtract(ultimosAbastecimentos.getRegistros().get(0).getTotalLitrosAbastecimento())), 3, RoundingMode.HALF_EVEN));
        return vo;
    }

    @Override
    public ResultadoPaginado<MediaConsumoVo> pesquisarMediaConsumoVeiculo(FiltroPesquisaMediaConsumoVo filtro,
                                                                          Function<Long, Collection<String>> obterSubtiposVeiculosPorTipo) {
        TipoConsumo tipoConsumo = EnumVo.fromName(TipoConsumo.class, filtro.getTipoConsumo());
        Integer tipoConsumoInteger = tipoConsumo != null ? tipoConsumo.getValue() : null;
        ClassificacaoAgregado veiculoClassificacao = EnumVo.fromName(ClassificacaoAgregado.class, filtro.getClassificacaoVeiculo());
        Integer veiculoClassificacaoIntegeer = veiculoClassificacao != null ? veiculoClassificacao.getValue() : null;

        ParametrosPesquisaBuilder parametrosBuilder = new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaIgual("de", obterPrimeiroInstanteDia(filtro.getDe())),
                        new ParametroPesquisaIgual("ate", obterUltimoInstanteDia(filtro.getAte())),
                        new ParametroPesquisaIgual("idFrota", filtro.getIdFrota()),
                        new ParametroPesquisaLike("placa", filtro.getPlacaVeiculo()),
                        new ParametroPesquisaIgual("veiculoUnidade", EntidadeVo.obterNome(filtro.getUnidadeVeiculo())),
                        new ParametroPesquisaIgual("veiculoGrupo", EntidadeVo.obterNome(filtro.getGrupoVeiculo())),
                        new ParametroPesquisaIgual("veiculoEmpresa", EntidadeVo.obterNome(filtro.getEmpresaVeiculo())),
                        new ParametroPesquisaIgual("veiculoClassificacao", veiculoClassificacaoIntegeer),
                        new ParametroPesquisaIgual("subTipoVeiculo", EntidadeVo.obterNome(filtro.getSubtipoVeiculo())),
                        new ParametroPesquisaIgual("tipoConsumo", tipoConsumoInteger)
                );

        String outrasClausulas = filtro.isUnidadeVeiculoMatriz()? CLAUSE_UNIDADE_VEICULO_MATRIZ : "";
        StringBuffer strBuffer = new StringBuffer(outrasClausulas);
        if(filtro.getTipoVeiculo() != null && filtro.getTipoVeiculo().getId() != null) {
            Collection<String> subTiposVeiculos = obterSubtiposVeiculosPorTipo.apply(filtro.getTipoVeiculo().getId());
            if(!subTiposVeiculos.isEmpty()) {
                strBuffer.append(CLAUSE_IN_SUBTIPOS);
                parametrosBuilder.adicionarParametros(new ParametroPesquisaIgual("subTiposVeiculos", subTiposVeiculos));
            }
        }

        if (utilitarioAmbiente.getUsuarioLogado().isInterno() && utilitarioAmbiente.getUsuarioLogado().possuiFrotasAssociadas()) {
            strBuffer.append(" AND a.frota.id IN (:idsFrota) ");
            parametrosBuilder.adicionarParametros(new ParametroPesquisaIn("idsFrota", utilitarioAmbiente.getUsuarioLogado().listarIdsFrotasAssociadas()));
        }

        String query = obterQueryPesquisarMediaConsumo(
                String.format(QUERY_PESQUISAR_MEDIA_CONSUMO_VEICULO, strBuffer.toString()),
                PARAM_ORDENACAO_PADRAO_MEDIA_CONSUMO_VEICULO,
                filtro.getPaginacao()
        );


        ResultadoPaginado<MediaConsumoVo> mediaConsumoVoResultado = pesquisar(filtro.getPaginacao(), query, MediaConsumoVo.class, parametrosBuilder.buildArray());
        mediaConsumoVoResultado.setRegistros(mediaConsumoVoResultado.getRegistros().stream()
                .map(vo-> processarMediaConsumoVeiculo(vo,filtro,obterSubtiposVeiculosPorTipo)).filter(vo -> vo != null)
                .collect(Collectors.toList()));

        return mediaConsumoVoResultado;
    }

    /**
     * Processa a média de consumo de um veículo
     *
     * @param vo vo com a média de consumo
     * @param filtro o filtro de pesquisa da média de consumo
     * @param obterSubtiposVeiculosPorTipo subtipos de veículos por tipo
     * @return a média de consumo
     */
    private MediaConsumoVo processarMediaConsumoVeiculo(MediaConsumoVo vo, FiltroPesquisaMediaConsumoVo filtro, Function<Long, Collection<String>> obterSubtiposVeiculosPorTipo) {
        ParametrosPesquisaBuilder builder = new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaIgual("de", obterPrimeiroInstanteDia(filtro.getDe())),
                        new ParametroPesquisaIgual("ate", obterUltimoInstanteDia(filtro.getAte())),
                        new ParametroPesquisaIgual("idFrota", vo.getIdFrota()),
                        new ParametroPesquisaLike("placa", vo.getPlacaVeiculo()),
                        new ParametroPesquisaIgual("cnpjUnidadeVeiculo",vo.getCnpjUnidadeVeiculo()),
                        new ParametroPesquisaIgual("codigoGrupoVeiculo", vo.getCodigoGrupoVeiculo()),
                        new ParametroPesquisaIgual("razaoSocialEmpresaVeiculo", vo.getRazaoSocialEmpresaVeiculo()),
                        new ParametroPesquisaIgual("veiculoClassificacao", vo.getClassificacaoVeiculo())
                );

        String outrasClausulas = filtro.isUnidadeVeiculoMatriz() ? CLAUSE_UNIDADE_VEICULO_MATRIZ : "";
        if(filtro.getTipoVeiculo() != null && filtro.getTipoVeiculo().getId() != null) {
            Collection<String> subTiposVeiculos = obterSubtiposVeiculosPorTipo.apply(filtro.getTipoVeiculo().getId());
            if(!subTiposVeiculos.isEmpty()) {
                outrasClausulas += CLAUSE_IN_SUBTIPOS;
                builder.adicionarParametros(new ParametroPesquisaIgual("subTiposVeiculos", subTiposVeiculos));
            }
        }

        InformacaoPaginacao infoPag=new InformacaoPaginacao();
        infoPag.setTamanhoPagina(1);
        ResultadoPaginado<AutorizacaoPagamento> primeiroAbastecimento = pesquisar(infoPag, String.format(QUERY_PESQUISAR_PRIMEIRO_ABASTECIMENTO_MEDIA_CONSUMO_VEICULO, outrasClausulas), AutorizacaoPagamento.class, builder.buildArray());

        boolean naoPossuiRegistrosPrimeiroAbast = primeiroAbastecimento.getRegistros() == null || primeiroAbastecimento.getTotalItems() < 1;
        boolean naoPossuiMediaTotalLitrosAbast = vo.getMediaTotalLitrosAbastecimento() == null || vo.getMediaTotalLitrosAbastecimento().compareTo(BigDecimal.ZERO) == 0;
        boolean naoPossuiMediaHorHod = vo.getMediaHorHod() == null || vo.getMediaHorHod().compareTo(BigDecimal.ZERO) == 0;

        if (naoPossuiRegistrosPrimeiroAbast || naoPossuiMediaTotalLitrosAbast || naoPossuiMediaHorHod) {
            return null;
        }

        BigDecimal difMediaTotalLitrosAbastPorTotalPrimeiroAbast = vo.getMediaTotalLitrosAbastecimento()
                .subtract(primeiroAbastecimento.getRegistros().get(0).getTotalLitrosAbastecimento());

        if(difMediaTotalLitrosAbastPorTotalPrimeiroAbast.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }

        switch (TipoConsumo.obterPorValor(vo.getTipoConsumo())) {
            case KML:
                vo.setMedia(vo.getMediaHorHod()
                        .divide(difMediaTotalLitrosAbastPorTotalPrimeiroAbast, 3, RoundingMode.HALF_EVEN));
                break;
            case LH:
                vo.setMedia(difMediaTotalLitrosAbastPorTotalPrimeiroAbast
                        .divide(vo.getMediaHorHod(), 3, RoundingMode.HALF_EVEN));
                break;
        }

        return vo;
    }

    @Override
    public ResultadoPaginado<MediaConsumoVo> pesquisarMediaConsumoMotoristaVeiculo(FiltroPesquisaMediaConsumoVo filtro, Function<Long, Collection<String>> obterSubtiposVeiculosPorTipo) {
        TipoConsumo tipoConsumo = EnumVo.fromName(TipoConsumo.class, filtro.getTipoConsumo());
        Integer tipoConsumoInteger = tipoConsumo != null ? tipoConsumo.getValue() : null;
        ClassificacaoAgregado motoristaClassificacao = EnumVo.fromName(ClassificacaoAgregado.class, filtro.getClassificacaoMotorista());
        Integer motoristaClassificacaoIntegeer = motoristaClassificacao != null ? motoristaClassificacao.getValue() : null;

        ParametrosPesquisaBuilder builder = new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaIgual("de", obterPrimeiroInstanteDia(filtro.getDe())),
                        new ParametroPesquisaIgual("ate", obterUltimoInstanteDia(filtro.getAte())),
                        new ParametroPesquisaIgual("idFrota", filtro.getIdFrota()),
                        new ParametroPesquisaLike("motorista", filtro.getMotorista()),
                        new ParametroPesquisaIgual("motoristaUnidade", EntidadeVo.obterNome(filtro.getUnidadeMotorista())),
                        new ParametroPesquisaIgual("motoristaGrupo", EntidadeVo.obterNome(filtro.getGrupoMotorista())),
                        new ParametroPesquisaIgual("motoristaEmpresa", EntidadeVo.obterNome(filtro.getEmpresaMotorista())),
                        new ParametroPesquisaIgual("motoristaClassificacao", motoristaClassificacaoIntegeer),
                        new ParametroPesquisaIgual("subTipoVeiculo", EntidadeVo.obterNome(filtro.getSubtipoVeiculo())),
                        new ParametroPesquisaLike("placa", filtro.getPlacaVeiculo()),
                        new ParametroPesquisaIgual("tipoConsumo", tipoConsumoInteger)
                );

        String outrasClausulas = filtro.isUnidadeMotoristaMatriz() ? CLAUSE_UNIDADE_MOTORISTA_MATRIZ : "";
        StringBuffer strBuffer = new StringBuffer(outrasClausulas);
        if(filtro.getTipoVeiculo() != null && filtro.getTipoVeiculo().getId() != null) {
            Collection<String> subTiposVeiculos = obterSubtiposVeiculosPorTipo.apply(filtro.getTipoVeiculo().getId());
            if(!subTiposVeiculos.isEmpty()) {
                strBuffer.append(CLAUSE_IN_SUBTIPOS);
                builder.adicionarParametros(new ParametroPesquisaIgual("subTiposVeiculos", subTiposVeiculos));
            }
        }

        if (utilitarioAmbiente.getUsuarioLogado().isInterno() && utilitarioAmbiente.getUsuarioLogado().possuiFrotasAssociadas()) {
            strBuffer.append(" AND a.frota.id IN (:idsFrota) ");
            builder.adicionarParametros(new ParametroPesquisaIn("idsFrota", utilitarioAmbiente.getUsuarioLogado().listarIdsFrotasAssociadas()));
        }

        String query = obterQueryPesquisarMediaConsumo(
                String.format(QUERY_PESQUISAR_MEDIA_CONSUMO_MOTORISTA_VEICULO, strBuffer.toString()),
                PARAM_ORDENACAO_PADRAO_MEDIA_CONSUMO_MOTORISTA_UPPER_CASE,
                filtro.getPaginacao()
        );
        ResultadoPaginado<MediaConsumoVo> mediaConsumoVoResultado = pesquisar(filtro.getPaginacao(), query, MediaConsumoVo.class, builder.buildArray());
        mediaConsumoVoResultado.setRegistros(mediaConsumoVoResultado.getRegistros().stream()
                .map(vo-> processarMediaConsumoMotoristaVeiculo(vo,filtro,obterSubtiposVeiculosPorTipo)).filter(vo -> vo != null)
                .collect(Collectors.toList()));
        return mediaConsumoVoResultado;
    }

    @Override
    public ResultadoPaginado<VolumeAbastecidoTipoCombustivelVo> pesquisarVolumeAbastecidoPorTipooCombustivel(Usuario usuarioFrotista, int periodo) {
        ParametrosPesquisaBuilder builder = new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaIgual("idFrota", usuarioFrotista.getFrota().getId()),
                        new ParametroPesquisaIgual("periodo", periodo),
                        new ParametroPesquisaIgual("periodoAnterior", periodo*2));

        InformacaoPaginacao infoPag = new InformacaoPaginacao();

        return pesquisar(infoPag, String.format(QUERY_VOLUME_ABASTECIDO_POR_TIPO_COMBUSTIVEL_EM_PERIODO), VolumeAbastecidoTipoCombustivelVo.class, builder.buildArray());
    }

    /**
     * Processa a média de consumo do motorista
     *
     * @param vo  vo com a média de consumo
     * @param filtro o filtro de pesquisa de média de consumo
     * @param obterSubtiposVeiculosPorTipo os subtipos dos veículos por tipo
     * @return a média de consumo
     */
    private MediaConsumoVo processarMediaConsumoMotoristaVeiculo(MediaConsumoVo vo, FiltroPesquisaMediaConsumoVo filtro, Function<Long, Collection<String>> obterSubtiposVeiculosPorTipo) {
        ParametrosPesquisaBuilder builder = new ParametrosPesquisaBuilder()
                .adicionarParametros(
                        new ParametroPesquisaIgual("de", obterPrimeiroInstanteDia(filtro.getDe())),
                        new ParametroPesquisaIgual("ate", obterUltimoInstanteDia(filtro.getAte())),
                        new ParametroPesquisaIgual("idFrota", vo.getIdFrota()),
                        new ParametroPesquisaIgual("cpfMotorista",vo.getCpfMotorista()),
                        new ParametroPesquisaIgual("cnpjUnidadeMotorista", vo.getCnpjUnidadeMotorista()),
                        new ParametroPesquisaIgual("codigoGrupoMotorista", vo.getCodigoGrupoMotorista()),
                        new ParametroPesquisaIgual("razaoSocialEmpresaMotorista", vo.getRazaoSocialEmpresaMotorista()),
                        new ParametroPesquisaIgual("razaoSocialEmpresaVeiculo", vo.getRazaoSocialEmpresaVeiculo()),
                        new ParametroPesquisaIgual("subTipoVeiculo", vo.getSubTipoVeiculo()),
                        new ParametroPesquisaLike("placa", vo.getPlacaVeiculo())
                );

        String outrasClausulas = filtro.isUnidadeMotoristaMatriz() ? CLAUSE_UNIDADE_MOTORISTA_MATRIZ : "";
        if(filtro.getTipoVeiculo() != null && filtro.getTipoVeiculo().getId() != null) {
            Collection<String> subTiposVeiculos = obterSubtiposVeiculosPorTipo.apply(filtro.getTipoVeiculo().getId());
            if(!subTiposVeiculos.isEmpty()) {
                outrasClausulas += CLAUSE_IN_SUBTIPOS;
                builder.adicionarParametros(new ParametroPesquisaIgual("subTiposVeiculos", subTiposVeiculos));
            }
        }

        InformacaoPaginacao infoPag=new InformacaoPaginacao();
        infoPag.setTamanhoPagina(1);
        ResultadoPaginado<AutorizacaoPagamento> ultimosAbastecimentos = pesquisar(infoPag, String.format(QUERY_PESQUISAR_ULTIMO_ABASTECIMENTO_MEDIA_CONSUMO_MOTORISTA_VEICULO, outrasClausulas), AutorizacaoPagamento.class, builder.buildArray());

        if(ultimosAbastecimentos.getRegistros() == null || ultimosAbastecimentos.getTotalItems() < 1
                || vo.getMediaTotalLitrosAbastecimento().compareTo(BigDecimal.ZERO) == 0
                ||vo.getMediaTotalLitrosAbastecimento().subtract(ultimosAbastecimentos.getRegistros().get(0).getTotalLitrosAbastecimento()).compareTo(BigDecimal.ZERO) == 0){
            return  null;
        }

        vo.setMedia(vo.getMediaHorHod()
                .divide((vo.getMediaTotalLitrosAbastecimento()
                        .subtract(ultimosAbastecimentos.getRegistros().get(0).getTotalLitrosAbastecimento())), 3, RoundingMode.HALF_EVEN));
        return vo;
    }

    /**
     * Método que obtém query para Pesquisar Média de Consumo com parâmetros de ordenação
     * @param query A query base a ser executada
     * @param ordencacaoPadrao O parâmetro de ordenção padrão
     * @param paginacao Informação de paginação
     * @return Query com informações extra
     */
    private String obterQueryPesquisarMediaConsumo(String query, String ordencacaoPadrao, InformacaoPaginacao paginacao)  {
        List<ParametroOrdenacaoColuna> ordenacaoColunas = paginacao != null && paginacao.existemParametrosOrdenacaoColuna()
                ? paginacao.getParametrosOrdenacaoColuna()
                : Collections.singletonList(new ParametroOrdenacaoColuna(ordencacaoPadrao));
        String orderby = ordenacaoColunas.stream()
                .map(this::obterStringParametroOrdenacaoPesquisarMediaConsumo)
                .collect(Collectors.joining(", "));
        return query.concat(orderby);
    }

    /**
     * Método que obtém string de ordenção para cada parâmetro de ordenação de coluna
     * @param ordenacaoColuna O parâmetro de ordenação
     * @return A clausula de orderby
     */
    private String obterStringParametroOrdenacaoPesquisarMediaConsumo(ParametroOrdenacaoColuna ordenacaoColuna) {
        String orderBy;
        switch (ordenacaoColuna.getNome()) {
            case "grupoMotorista":
                orderBy = ORDENACAO_GRUPO_MOTORISTA;
                break;
            case "grupoVeiculo":
                orderBy = ORDENACAO_GRUPO_VEICULO;
                break;
            default:
                orderBy = ordenacaoColuna.getNome();
                break;
        }
        return orderBy + (ordenacaoColuna.isDecrescente() ? " DESC" : " ASC");
    }
}
