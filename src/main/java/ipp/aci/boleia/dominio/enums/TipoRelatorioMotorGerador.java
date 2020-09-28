package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.dominio.vo.FiltroPesquisaAbastecimentoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaAlteracaoPrecoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCicloRepasseVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaCobrancaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaFrotaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaMotoristaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaNegociacaoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPontoDeVendaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPostoCredenciadoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPrecoMicromercadoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPrecoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaSaldoFrotaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaTransacaoConsolidadaVo;
import ipp.aci.boleia.util.TemplatePlanilha;
import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.HashMap;
import java.util.Map;

/**
 * Indica o tipo do relatório emitido pelo motor de geração de relatórios
 */
public enum TipoRelatorioMotorGerador implements IEnumComLabel<TipoRelatorioMotorGerador> {

    RELATORIO_PRECO_EXPORTACAO(0, obterMapaTemplatesPrecoMicromercado(), FiltroPesquisaPrecoMicromercadoVo.class),
    RELATORIO_PRECO_POSTO(1, obterMapaTemplatesPrecoPosto(), FiltroPesquisaAlteracaoPrecoVo.class),
    RELATORIO_ABASTECIMENTO(2, obterMapaTemplatesAbastecimento(), FiltroPesquisaAbastecimentoVo.class),
    RELATORIO_NOTAS_FISCAIS(3, obterMapaTemplatesNotasFiscais(), FiltroPesquisaTransacaoConsolidadaVo.class),
    RELATORIO_MOTORISTA(4, obterMapaTemplatesMotorista(), FiltroPesquisaMotoristaVo.class),
    RELATORIO_COBRANCA(5, obterMapaTemplatesCobranca(), FiltroPesquisaCobrancaVo.class),
    RELATORIO_CICLO_REPASSE(6, obterMapaTemplatesCicloRepasse(), FiltroPesquisaCicloRepasseVo.class),
    RELATORIO_PONTO_VENDA(7, obterMapaTemplatesPontoVenda(), FiltroPesquisaPontoDeVendaVo.class),
    RELATORIO_SALDO_FROTA(8, obterMapaTemplatesSaldoFrota(), FiltroPesquisaSaldoFrotaVo.class),
    RELATORIO_FROTA(9, obterMapaTemplatesFrota(), FiltroPesquisaFrotaVo.class),
    RELATORIO_POSTO_CREDENCIADO(10, obterMapaTemplatesPostoCredenciado(), FiltroPesquisaPostoCredenciadoVo.class),
    RELATORIO_ABASTECIMENTOS_ESTORNADOS(11, obterMapaTemplatesAbastecimentosEstornados(), FiltroPesquisaAbastecimentoVo.class),
    RELATORIO_ABASTECIMENTOS_AJUSTADOS(12, obterMapaTemplatesAbastecimentosAjustados(), FiltroPesquisaAbastecimentoVo.class),
    RELATORIO_PRECO_NEGOCIADO(13, obterMapaTemplatesPrecoNegociado(), FiltroPesquisaPrecoVo.class),
    RELATORIO_NEGOCIACAO(14, obterMapaTemplatesNegociacao(), FiltroPesquisaNegociacaoVo.class);

    private final Integer value;
    private final Map<TipoPerfilUsuario, TemplatePlanilha> templatePerfil;
    private final Class classeFiltro;

    /**
     * Construtor
     *  @param value O value do status
     */
    TipoRelatorioMotorGerador(Integer value, Map<TipoPerfilUsuario, TemplatePlanilha> templatePerfil, Class filtro) {
        this.value = value;
        this.templatePerfil = templatePerfil;
        this.classeFiltro = filtro;
    }

    public Integer getValue() {
        return value;
    }

    public Map<TipoPerfilUsuario, TemplatePlanilha> getTemplatePerfil() {
        return templatePerfil;
    }

    public Class getClasseFiltro() {
        return classeFiltro;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoRelatorioMotorGerador obterPorValor(Integer value) {
        for(TipoRelatorioMotorGerador status : TipoRelatorioMotorGerador.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    public TemplatePlanilha obterTemplatePorTipoPerfilUsuario(TipoPerfilUsuario tipoPerfilUsuario) {
        return templatePerfil.get(tipoPerfilUsuario);
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (preço micromercado)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesPrecoMicromercado(){
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_MICROMERCADO);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_MICROMERCADO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_MICROMERCADO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (preço posto)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesPrecoPosto(){
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_BASE_REVENDA);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_BASE_SOLUCAO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (abastecimento)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesAbastecimento(){
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTO_REVENDA);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTO_SOLUCAO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTO_FROTA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (notas fiscais)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesNotasFiscais() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_NOTAS_FISCAIS);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_NOTAS_FISCAIS);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_NOTAS_FISCAIS);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (motorista)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesMotorista() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_MOTORISTA_FROTA);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_MOTORISTA_SOLUCAO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (cobrança)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesCobranca() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_COBRANCA_COM_DESCONTO);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_COBRANCA);
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_COBRANCA_INTERNO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (ciclo repasse)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesCicloRepasse() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_REPASSE_SOLUCAO);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (ponto venda)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesPontoVenda() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_PONTO_VENDA_SOLUCAO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_PONTO_VENDA_FROTA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (saldo frota)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesSaldoFrota() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_CREDITO_FROTA);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_CREDITO_FROTA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (frota)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesFrota() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_FROTA_SOLUCAO);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_FROTA_REVENDA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (posto credenciado)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesPostoCredenciado() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_POSTO_CREDENCIADO_SOLUCAO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_POSTO_CREDENCIADO_FROTA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (preço negociado)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesPrecoNegociado() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_NEGOCIADO_SOLUCAO);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_NEGOCIADO_FROTA);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_PRECO_NEGOCIADO_REVENDA);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (abastecimentos estornados)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesAbastecimentosEstornados() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_ESTORNADOS);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_ESTORNADOS);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_ESTORNADOS);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (abastecimentos ajustados)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesAbastecimentosAjustados() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_AJUSTADOS);
        mapa.put(TipoPerfilUsuario.FROTA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_AJUSTADOS);
        mapa.put(TipoPerfilUsuario.REVENDA, TemplatePlanilha.TEMPLATE_RELATORIO_ABASTECIMENTOS_AJUSTADOS);
        return mapa;
    }

    /**
     * Gera um mapa de templates por perfil para um tipo de relatório (negociação)
     *
     * @return O mapa criado
     */
    private static Map<TipoPerfilUsuario, TemplatePlanilha> obterMapaTemplatesNegociacao() {
        Map<TipoPerfilUsuario, TemplatePlanilha> mapa = new HashMap<>();
        mapa.put(TipoPerfilUsuario.INTERNO, TemplatePlanilha.TEMPLATE_RELATORIO_NEGOCIACOES_SOLUCAO);
        return mapa;
    }










}
