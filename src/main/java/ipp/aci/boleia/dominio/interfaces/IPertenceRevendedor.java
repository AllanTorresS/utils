package ipp.aci.boleia.dominio.interfaces;

import ipp.aci.boleia.dominio.Componente;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.Negociacao;
import ipp.aci.boleia.dominio.NotaFiscal;
import ipp.aci.boleia.dominio.Perfil;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Preco;
import ipp.aci.boleia.dominio.Reembolso;
import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.TransacaoConsolidadaDetalhe;
import ipp.aci.boleia.dominio.Usuario;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Interface de abstracao para objetos do dominio pertencentes a revendedor especifico,
 * portanto no portal do revendedor não deverá ser vísivel para os outros revendedores do sistema.
 */
public interface IPertenceRevendedor {

    Map<Class<?>, String> MAPA_CAMINHOS_REVENDA = inicializarMapaCaminhosRevenda();
    String CAMPO_ID = "id";
    String CAMPO_COMPONENTE = "pontoDeVenda." + CAMPO_ID;
    String CAMPO_PONTOVENDA = "pontoVenda." + CAMPO_ID;
    String CAMPO_FROTA_PONTOVENDA = "frotaPtov." + CAMPO_PONTOVENDA;
    String CAMPO_AUTORIZACAO_PAGAMENTO = "autorizacaoPagamento." + CAMPO_PONTOVENDA;
    String CAMPO_AUTORIZACOES_PAGAMENTO = "autorizacoesPagamento." + CAMPO_PONTOVENDA;
    String CAMPO_PONTOSVENDA = "pontosDeVenda.id";
    String CAMPO_TRANSACAO_CONSOLIDADA = "transacaoConsolidada." + CAMPO_FROTA_PONTOVENDA;
    String CAMPO_TRANSACOES_CONSOLIDADAS = "transacoesConsolidadas." + CAMPO_FROTA_PONTOVENDA;
    String CAMPO_REDE = "rede." + CAMPO_PONTOSVENDA;

    /**
     * Obtem os pontos de venda relacionados a entidade
     * @return Pontos de venda relacionados
     */
    List<PontoDeVenda> getPontosDeVenda();

    /**
     * Obtem o caminho para obtencao do campo de frota atraves da dada entidade
     * @param clazz Entidade a obter o ponto de venda
     * @return Caminho
     */
    static String obterCaminhoPontoVenda(Class<?> clazz) {
        return MAPA_CAMINHOS_REVENDA.get(clazz) == null ? CAMPO_PONTOVENDA: MAPA_CAMINHOS_REVENDA.get(clazz);
    }

    /**
     * Monta o mapa de caminhos do campo da Revenda para cada tipo de classe
     * @return O Mapa de caminhos
     */
    static Map<Class<?>, String> inicializarMapaCaminhosRevenda() {
        Map<Class<?>, String> mapa = new HashMap<>();
        mapa.put(PontoDeVenda.class, CAMPO_ID);
        mapa.put(Preco.class, CAMPO_FROTA_PONTOVENDA);
        mapa.put(Usuario.class, CAMPO_PONTOSVENDA);
        mapa.put(ItemAutorizacaoPagamento.class, CAMPO_AUTORIZACAO_PAGAMENTO);
        mapa.put(TransacaoConsolidada.class, CAMPO_FROTA_PONTOVENDA);
        mapa.put(TransacaoConsolidadaDetalhe.class, CAMPO_TRANSACAO_CONSOLIDADA);
        mapa.put(Perfil.class, CAMPO_REDE);
        mapa.put(Componente.class, CAMPO_COMPONENTE);
        mapa.put(Reembolso.class, CAMPO_TRANSACOES_CONSOLIDADAS);
        mapa.put(NotaFiscal.class, CAMPO_AUTORIZACOES_PAGAMENTO);
        mapa.put(Negociacao.class, CAMPO_FROTA_PONTOVENDA);
        return mapa;
    }
}
