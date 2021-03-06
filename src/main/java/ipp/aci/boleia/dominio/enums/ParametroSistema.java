package ipp.aci.boleia.dominio.enums;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.parametros.ILogicaParametroSistema;
import ipp.aci.boleia.dominio.parametros.LogicaParametroConsumoEstimado;
import ipp.aci.boleia.dominio.parametros.LogicaParametroConsumoEstimadoPreAutorizacao;
import ipp.aci.boleia.dominio.parametros.LogicaParametroCotaVeiculo;
import ipp.aci.boleia.dominio.parametros.LogicaParametroCotaVeiculoPreAutorizacao;
import ipp.aci.boleia.dominio.parametros.LogicaParametroCreditoVeiculoAgregado;
import ipp.aci.boleia.dominio.parametros.LogicaParametroCreditoVeiculoAgregadoPreAutorizacao;
import ipp.aci.boleia.dominio.parametros.LogicaParametroHodometroHorimetro;
import ipp.aci.boleia.dominio.parametros.LogicaParametroHodometroHorimetroPreAutorizacao;
import ipp.aci.boleia.dominio.parametros.LogicaParametroHorariosAbastecimento;
import ipp.aci.boleia.dominio.parametros.LogicaParametroHorariosAbastecimentoPreAutorizacao;
import ipp.aci.boleia.dominio.parametros.LogicaParametroIntervaloAbastecimento;
import ipp.aci.boleia.dominio.parametros.LogicaParametroIntervaloAbastecimentoPreAutorizacao;
import ipp.aci.boleia.dominio.parametros.LogicaParametroLocalizacaoAbastecimento;
import ipp.aci.boleia.dominio.parametros.LogicaParametroPostosAutorizadosAbastecimento;
import ipp.aci.boleia.dominio.parametros.LogicaParametroPostosAutorizadosAbastecimentoPreAutorizacao;
import ipp.aci.boleia.dominio.parametros.LogicaParametroPrecoMaximo;
import ipp.aci.boleia.dominio.parametros.LogicaParametroPrecoMaximoPreAutorizacao;
import ipp.aci.boleia.dominio.parametros.LogicaParametroProdutoAbastecido;
import ipp.aci.boleia.dominio.parametros.LogicaParametroProdutoAbastecidoPreAutorizacao;
import ipp.aci.boleia.dominio.parametros.LogicaParametroProdutosAdicionais;
import ipp.aci.boleia.dominio.parametros.LogicaParametroVolumeAbastecido;
import ipp.aci.boleia.dominio.parametros.LogicaParametroVolumeAbastecidoPreAutorizacao;
import ipp.aci.boleia.dominio.vo.PreAutorizacaoPedidoVo;
import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * Enumera os possiveis parametros de sistema, configura????es default, l??gica e contextos de execu????o.
 */
public enum ParametroSistema implements IEnumComLabel, IEnumComValor{

    VOLUME_ABASTECIDO                (1,  TipoRestritividade.RESTRITIVA, true,  true,  Collections.singletonList(TipoPerfilUsuario.FROTA),                   EstruturaParametroSistema.SIMPLES,                          "parametro.sistema.volume.abastecido.nome",         "parametro.sistema.volume.abastecido.descricao",         ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroVolumeAbastecido.class, PreAutorizacaoPedidoVo.class, LogicaParametroVolumeAbastecidoPreAutorizacao.class),                             false),
    PRODUTO_ABASTECIMENTO            (2,  TipoRestritividade.RESTRITIVA, true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA),                   EstruturaParametroSistema.PRODUTO_ABASTECIMENTO,            "parametro.sistema.produto.abastecido.nome",        "parametro.sistema.produto.abastecido.descricao",        ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroProdutoAbastecido.class, PreAutorizacaoPedidoVo.class, LogicaParametroProdutoAbastecidoPreAutorizacao.class),                           true),
    PRODUTOS_ADICIONAIS_PERMITIDOS   (3,  TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA),                   EstruturaParametroSistema.LISTA_PRODUTOS,                   "parametro.sistema.produtos.adicionais.nome",       "parametro.sistema.produtos.adicionais.descricao",       ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroProdutosAdicionais.class),                                                                                                              false),
    HORARIOS_ABASTECIMENTO           (4,  TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA),                   EstruturaParametroSistema.LISTA_HORARIOS,                   "parametro.sistema.horario.abastecimento.nome",     "parametro.sistema.horario.abastecimento.descricao",     ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroHorariosAbastecimento.class, PreAutorizacaoPedidoVo.class, LogicaParametroHorariosAbastecimentoPreAutorizacao.class),                   false),
    CONSUMO_ESTIMADO                 (5,  TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA),                   EstruturaParametroSistema.CONSUMO_ESTIMADO,                 "parametro.sistema.consumo.estimado.nome",          "parametro.sistema.consumo.estimado.descricao",          ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroConsumoEstimado.class, PreAutorizacaoPedidoVo.class, LogicaParametroConsumoEstimadoPreAutorizacao.class),                               false),
    PRECO_MAXIMO                     (6,  TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA),                   EstruturaParametroSistema.PRECO_MAXIMO,                     "parametro.sistema.preco.maximo.nome",              "parametro.sistema.preco.maximo.descricao",              ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroPrecoMaximo.class, PreAutorizacaoPedidoVo.class, LogicaParametroPrecoMaximoPreAutorizacao.class),                                       true),
    HODOMETRO_HORIMETRO              (7,  TipoRestritividade.RESTRITIVA, true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA),                   EstruturaParametroSistema.HODOMETRO_HORIMETRO,              "parametro.sistema.hodometro.horimetro.nome",       "parametro.sistema.hodometro.horimetro.descricao",       ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroHodometroHorimetro.class, PreAutorizacaoPedidoVo.class, LogicaParametroHodometroHorimetroPreAutorizacao.class),                         false),
    COTA_VEICULO                     (8,  TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA),                   EstruturaParametroSistema.COTA_VEICULO,                     "parametro.sistema.cota.veiculo.nome",              "parametro.sistema.cota.veiculo.descricao",              ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroCotaVeiculo.class, PreAutorizacaoPedidoVo.class, LogicaParametroCotaVeiculoPreAutorizacao.class),                                       false),
    INTERVALO_ABASTECIMENTO          (9,  TipoRestritividade.VERSATIL,   false, false, Collections.singletonList(TipoPerfilUsuario.FROTA),                   EstruturaParametroSistema.INTERVALO_ABASTECIMENTO,          "parametro.sistema.intervalo.abastecimento.nome",   "parametro.sistema.intervalo.abastecimento.descricao",   ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroIntervaloAbastecimento.class, PreAutorizacaoPedidoVo.class, LogicaParametroIntervaloAbastecimentoPreAutorizacao.class),                 true),
    POSTOS_AUTORIZADOS_ABASTECIMENTO (10, TipoRestritividade.VERSATIL,   true,  false, ImmutableList.of(TipoPerfilUsuario.FROTA, TipoPerfilUsuario.INTERNO), EstruturaParametroSistema.POSTOS_AUTORIZADOS_ABASTECIMENTO, "parametro.sistema.postos.permitidos.nome",         "parametro.sistema.postos.permitidos.descricao",         ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroPostosAutorizadosAbastecimento.class, PreAutorizacaoPedidoVo.class, LogicaParametroPostosAutorizadosAbastecimentoPreAutorizacao.class), true),
    CREDITO_VEICULO_AGREGADO         (11, TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA),                   EstruturaParametroSistema.CREDITO_VEICULO_AGREGADO,         "parametro.sistema.credito.veiculo.agregado.nome",  "parametro.sistema.credito.veiculo.agregado.descricao",  ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroCreditoVeiculoAgregado.class, PreAutorizacaoPedidoVo.class, LogicaParametroCreditoVeiculoAgregadoPreAutorizacao.class),                 false),
    LOCALIZACAO_ABASTECIMENTO        (12, TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA),                   EstruturaParametroSistema.SIMPLES,                          "parametro.sistema.localizacao.abastecimento.nome", "parametro.sistema.localizacao.abastecimento.descricao", ImmutableMap.of(AutorizacaoPagamento.class, LogicaParametroLocalizacaoAbastecimento.class),                                                                                                        false);

    private final Integer codigo;
    private final String nome;
    private final String descricao;
    private final TipoRestritividade tipoRestritividade;
    private final boolean restritivaPorDefault;
    private final boolean sempreAtivo;
    private final List<TipoPerfilUsuario> tiposPerfil;
    private final EstruturaParametroSistema estrutura;
    private final Map<Class<?>, Class<? extends ILogicaParametroSistema<?>>> logicaExecucaoPorContexto;
    private final boolean parametroRI;

    /**
     * Construtor do enum
     *
     * @param codigo               C??digo do par??metro
     * @param tipoRestritividade   Tipo de restritividade do par??metro
     * @param restritivaPorDefault Se o par??metro ?? restritivo por default
     * @param estrutura            Estrutura do par??metro
     * @param nome                 Nome do par??metro
     * @param descricao            Descri????o do par??metro
     * @param logicaExecucaoPorContexto registros de l??gicas de execu????o separadas por tipo de contexto
     * @param parametroRI Flag que sinaliza se parametro vai ser op????o de filtro no Roteirizador Inteligente
     */
    ParametroSistema(Integer codigo, TipoRestritividade tipoRestritividade, boolean restritivaPorDefault, boolean sempreAtivo, List<TipoPerfilUsuario> tiposPerfil, EstruturaParametroSistema estrutura, String nome, String descricao, Map<Class<?>, Class<? extends ILogicaParametroSistema<?>>> logicaExecucaoPorContexto, boolean parametroRI) {
        this.codigo = codigo;
        this.tipoRestritividade = tipoRestritividade;
        this.restritivaPorDefault = restritivaPorDefault;
        this.sempreAtivo = sempreAtivo;
        this.tiposPerfil = tiposPerfil;
        this.estrutura = estrutura;
        this.nome = nome;
        this.descricao = descricao;
        this.logicaExecucaoPorContexto = logicaExecucaoPorContexto;
        this.parametroRI = parametroRI;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public TipoRestritividade getTipoRestritividade() {
        return tipoRestritividade;
    }

    public EstruturaParametroSistema getEstrutura() {
        return estrutura;
    }

    /**
     * Obt??m a l??gica de execu????o conforme o tipo parametrizado.
     *
     * @param contextType tipo usado na execu????oo do parametro
     * @return l??gica de execu????o do parametro
     */
    public Class<? extends ILogicaParametroSistema<?>> getLogicaExecucao(Type contextType) {
        return logicaExecucaoPorContexto.entrySet().stream().filter(tipo -> tipo.getKey().equals(contextType))
                .map(Map.Entry::getValue)
                .findFirst().orElseThrow(IllegalArgumentException::new);
    }

    public boolean isRestritivaPorDefault() {
        return restritivaPorDefault;
    }

    public boolean isSempreAtivo() {
        return sempreAtivo;
    }

    public List<TipoPerfilUsuario> getTiposPerfil() {
        return tiposPerfil;
    }

    public boolean isParametroRI() {
        return parametroRI;
    }

    /**
     * Obtem o parametro pelo codigo
     *
     * @param codigo O codigo do parametro
     * @return O parametro correspondente ao codigo informado
     */
    public static ParametroSistema obterPorCodigo(Integer codigo) {
        for (ParametroSistema param : ParametroSistema.values()) {
            if (param.codigo.equals(codigo)) {
                return param;
            }
        }
        return null;
    }

    @Override
    public Integer getValue() {
        return codigo;
    }

    @Override
    public String getLabel() {
        return nome;
    }
}