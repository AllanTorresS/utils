package ipp.aci.boleia.dominio.interfaces;

import ipp.aci.boleia.dominio.AbastecimentoVeiculoMes;
import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.dominio.ExtratoPedidoTransacao;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.FrotaParametroSistemaConsumo;
import ipp.aci.boleia.dominio.FrotaParametroSistemaHodometroHorimetro;
import ipp.aci.boleia.dominio.FrotaParametroSistemaHorario;
import ipp.aci.boleia.dominio.FrotaParametroSistemaIntervaloAbastecimento;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPostoAutorizadoAbastecimento;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPrecoMaximoAbastecimento;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPrecoMaximoProduto;
import ipp.aci.boleia.dominio.FrotaParametroSistemaProduto;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.Negociacao;
import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.PontoRota;
import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.TransacaoConsolidadaDetalhe;
import ipp.aci.boleia.dominio.beneficios.OperacaoContaBeneficiario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface de abstracao para objetos do dominio pertencentes a uma frota especifica,
 * portanto no portal do frotista não deverá ser visível para outras frotas do sistema.
 */
public interface IPertenceFrota {

    String CAMPO_FROTA_ID = "id";
    String NOME_CAMPO = "frota." + CAMPO_FROTA_ID;
    String CAMPO_FROTA_PONTOVENDA = "frotaPtov." + NOME_CAMPO;
    String CAMPO_AUTORIZACAO_FROTA = "autorizacaoPagamento." + NOME_CAMPO;
    String CAMPO_AUTORIZACOES_FROTA = "autorizacoesPagamento." + NOME_CAMPO;
    String CAMPO_TRANSACOES_CONSOLIDADAS_FROTA = "transacoesConsolidadas." + CAMPO_FROTA_PONTOVENDA;
    String CAMPO_TRANSACAO_CONSOLIDADA_FROTA = "transacaoConsolidada." + CAMPO_FROTA_PONTOVENDA;
    String CAMPO_ROTA = "rota." + NOME_CAMPO;
    String CAMPO_PARAMETRO_SISTEMA = "frotaParametroSistema." + NOME_CAMPO;
    String CAMPO_VEICULO = "veiculo." + NOME_CAMPO;
    String CAMPO_TRANSACAO_FROTA = "transacaoFrota." + NOME_CAMPO;
    String CAMPO_FROTA_OPERACAO_CONTA_BENEFICIARIO = "contaBeneficiario.beneficiario." + NOME_CAMPO;

    Map<Class<?>, String> MAPA_RELACIONAMENTO_FROTA = inicializarMapaCaminhosFrota();

    /**
     * Obtem as frotas especificas da entidade
     * @return frotas da entidade
     */
    List<Frota> getFrotas();

    /**
     * Obtem o caminho para obtencao do campo de frota atraves da dada entidade
     * @param clazz Entidade a obter frota
     * @return Caminho
     */
    static String obterCaminhoFrota(Class<?> clazz) {

        if(isParametroSistema(clazz)) {
            return CAMPO_PARAMETRO_SISTEMA;
        }

        return MAPA_RELACIONAMENTO_FROTA.get(clazz) == null ? NOME_CAMPO: MAPA_RELACIONAMENTO_FROTA.get(clazz);
    }

    /**
     * Verifica se a classe e uma configuracao de parametros do sistema
     * @param clazz A classe
     * @return true caso a classe seja uma configuracao de parametros do sistema
     */
    static boolean isParametroSistema(Class clazz) {
        return
            clazz.equals(FrotaParametroSistemaConsumo.class) ||
            clazz.equals(FrotaParametroSistemaHodometroHorimetro.class) ||
            clazz.equals(FrotaParametroSistemaHorario.class) ||
            clazz.equals(FrotaParametroSistemaIntervaloAbastecimento.class) ||
            clazz.equals(FrotaParametroSistemaPostoAutorizadoAbastecimento.class) ||
            clazz.equals(FrotaParametroSistemaPrecoMaximoAbastecimento.class) ||
            clazz.equals(FrotaParametroSistemaPrecoMaximoProduto.class) ||
            clazz.equals(FrotaParametroSistemaProduto.class);
    }

    /**
     * Monta o mapa de caminhos do campo Frota para cada tipo de classe
     * @return O mapa de caminhos
     */
    static Map<Class<?>, String> inicializarMapaCaminhosFrota() {
        Map<Class<?>, String> mapa = new HashMap<>();
        mapa.put(Frota.class, CAMPO_FROTA_ID);
        mapa.put(Preco.class, CAMPO_FROTA_PONTOVENDA);
        mapa.put(Negociacao.class, CAMPO_FROTA_PONTOVENDA);
        mapa.put(TransacaoConsolidada.class, CAMPO_FROTA_PONTOVENDA);
        mapa.put(ItemAutorizacaoPagamento.class, CAMPO_AUTORIZACAO_FROTA);
        mapa.put(NotaFiscal.class, CAMPO_AUTORIZACOES_FROTA);
        mapa.put(TransacaoConsolidadaDetalhe.class, CAMPO_TRANSACAO_CONSOLIDADA_FROTA);
        mapa.put(PontoRota.class, CAMPO_ROTA);
        mapa.put(Cobranca.class, CAMPO_TRANSACOES_CONSOLIDADAS_FROTA);
        mapa.put(AbastecimentoVeiculoMes.class, CAMPO_VEICULO);
        mapa.put(ExtratoPedidoTransacao.class, CAMPO_TRANSACAO_FROTA);
        mapa.put(OperacaoContaBeneficiario.class, CAMPO_FROTA_OPERACAO_CONTA_BENEFICIARIO);
        return mapa;
    }
}
