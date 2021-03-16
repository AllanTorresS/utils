package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.dominio.parametros.ILogicaParametroSistema;
import ipp.aci.boleia.dominio.parametros.LogicaParametroConsumoEstimado;
import ipp.aci.boleia.dominio.parametros.LogicaParametroCotaVeiculo;
import ipp.aci.boleia.dominio.parametros.LogicaParametroCreditoVeiculoAgregado;
import ipp.aci.boleia.dominio.parametros.LogicaParametroHodometroHorimetro;
import ipp.aci.boleia.dominio.parametros.LogicaParametroHorariosAbastecimento;
import ipp.aci.boleia.dominio.parametros.LogicaParametroIntervaloAbastecimento;
import ipp.aci.boleia.dominio.parametros.LogicaParametroLocalizacaoAbastecimento;
import ipp.aci.boleia.dominio.parametros.LogicaParametroPostosAutorizadosAbastecimento;
import ipp.aci.boleia.dominio.parametros.LogicaParametroPrecoMaximo;
import ipp.aci.boleia.dominio.parametros.LogicaParametroProdutoAbastecido;
import ipp.aci.boleia.dominio.parametros.LogicaParametroProdutosAdicionais;
import ipp.aci.boleia.dominio.parametros.LogicaParametroVolumeAbastecido;
import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Enumera os possiveis parametros de sistema
 */
public enum ParametroSistema implements IEnumComLabel, IEnumComValor{

    VOLUME_ABASTECIDO                (1,  TipoRestritividade.RESTRITIVA, true,  true, Collections.singletonList(TipoPerfilUsuario.FROTA), EstruturaParametroSistema.SIMPLES, "parametro.sistema.volume.abastecido.nome", "parametro.sistema.volume.abastecido.descricao", LogicaParametroVolumeAbastecido.class),
    PRODUTO_ABASTECIMENTO            (2,  TipoRestritividade.RESTRITIVA, true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA), EstruturaParametroSistema.PRODUTO_ABASTECIMENTO, "parametro.sistema.produto.abastecido.nome", "parametro.sistema.produto.abastecido.descricao", LogicaParametroProdutoAbastecido.class),
    PRODUTOS_ADICIONAIS_PERMITIDOS   (3,  TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA), EstruturaParametroSistema.LISTA_PRODUTOS, "parametro.sistema.produtos.adicionais.nome", "parametro.sistema.produtos.adicionais.descricao", LogicaParametroProdutosAdicionais.class),
    HORARIOS_ABASTECIMENTO           (4,  TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA), EstruturaParametroSistema.LISTA_HORARIOS, "parametro.sistema.horario.abastecimento.nome", "parametro.sistema.horario.abastecimento.descricao", LogicaParametroHorariosAbastecimento.class),
    CONSUMO_ESTIMADO                 (5,  TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA), EstruturaParametroSistema.CONSUMO_ESTIMADO, "parametro.sistema.consumo.estimado.nome", "parametro.sistema.consumo.estimado.descricao", LogicaParametroConsumoEstimado.class),
    PRECO_MAXIMO                     (6,  TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA), EstruturaParametroSistema.PRECO_MAXIMO, "parametro.sistema.preco.maximo.nome", "parametro.sistema.preco.maximo.descricao", LogicaParametroPrecoMaximo.class),
    HODOMETRO_HORIMETRO              (7,  TipoRestritividade.RESTRITIVA, true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA), EstruturaParametroSistema.HODOMETRO_HORIMETRO, "parametro.sistema.hodometro.horimetro.nome", "parametro.sistema.hodometro.horimetro.descricao", LogicaParametroHodometroHorimetro.class),
    COTA_VEICULO                     (8,  TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA), EstruturaParametroSistema.COTA_VEICULO, "parametro.sistema.cota.veiculo.nome", "parametro.sistema.cota.veiculo.descricao", LogicaParametroCotaVeiculo.class),
    INTERVALO_ABASTECIMENTO          (9,  TipoRestritividade.VERSATIL,   false, false, Collections.singletonList(TipoPerfilUsuario.FROTA), EstruturaParametroSistema.INTERVALO_ABASTECIMENTO, "parametro.sistema.intervalo.abastecimento.nome", "parametro.sistema.intervalo.abastecimento.descricao", LogicaParametroIntervaloAbastecimento.class),
    POSTOS_AUTORIZADOS_ABASTECIMENTO (10, TipoRestritividade.VERSATIL,   true,  false, Arrays.asList(TipoPerfilUsuario.FROTA, TipoPerfilUsuario.INTERNO), EstruturaParametroSistema.POSTOS_AUTORIZADOS_ABASTECIMENTO, "parametro.sistema.postos.permitidos.nome", "parametro.sistema.postos.permitidos.descricao", LogicaParametroPostosAutorizadosAbastecimento.class),
    CREDITO_VEICULO_AGREGADO         (11, TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA), EstruturaParametroSistema.CREDITO_VEICULO_AGREGADO, "parametro.sistema.credito.veiculo.agregado.nome", "parametro.sistema.credito.veiculo.agregado.descricao", LogicaParametroCreditoVeiculoAgregado.class),
    LOCALIZACAO_ABASTECIMENTO        (12, TipoRestritividade.VERSATIL,   true,  false, Collections.singletonList(TipoPerfilUsuario.FROTA), EstruturaParametroSistema.SIMPLES, "parametro.sistema.localizacao.abastecimento.nome", "parametro.sistema.localizacao.abastecimento.descricao", LogicaParametroLocalizacaoAbastecimento.class);

    private final Integer codigo;
    private final String nome;
    private final String descricao;
    private final TipoRestritividade tipoRestritividade;
    private final boolean restritivaPorDefault;
    private final boolean sempreAtivo;
    private final List<TipoPerfilUsuario> tiposPerfil;
    private final EstruturaParametroSistema estrutura;
    private final Class<ILogicaParametroSistema<?>> logicaExecucao;

    /**
     * Construtor do enum
     * @param codigo Código do parâmetro
     * @param tipoRestritividade Tipo de restritividade do parâmetro
     * @param restritivaPorDefault Se o parâmetro é restritivo por default
     * @param estrutura Estrutura do parâmetro
     * @param nome Nome do parâmetro
     * @param descricao Descrição do parâmetro
     * @param logicaExecucao Classe que contém a lógica de execução do parâmetro
     */
    ParametroSistema(Integer codigo, TipoRestritividade tipoRestritividade, boolean restritivaPorDefault, boolean sempreAtivo, List<TipoPerfilUsuario> tiposPerfil, EstruturaParametroSistema estrutura, String nome, String descricao, Class logicaExecucao) {
        this.codigo = codigo;
        this.tipoRestritividade = tipoRestritividade;
        this.restritivaPorDefault = restritivaPorDefault;
        this.sempreAtivo = sempreAtivo;
        this.tiposPerfil = tiposPerfil;
        this.estrutura = estrutura;
        this.nome = nome;
        this.descricao = descricao;
        this.logicaExecucao = logicaExecucao;
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

    public Class<ILogicaParametroSistema<?>> getLogicaExecucao() {
        return logicaExecucao;
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

    /**
     * Obtem o parametro pelo codigo
     * @param codigo O codigo do parametro
     * @return O parametro correspondente ao codigo informado
     */
    public static ParametroSistema obterPorCodigo(Integer codigo) {
        for(ParametroSistema param : ParametroSistema.values()) {
            if(param.codigo.equals(codigo)) {
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