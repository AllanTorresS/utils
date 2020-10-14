package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.CicloRepasse;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.vo.DetalheCicloRepasseVo;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Implementa as regras de negocio relacionadas ao detalhe do CicloRepasse
 */
@Component
public class DetalheCicloRepasseSd {

    /**
     * Gera os agrupamentos de produto por Ponto de Venda
     * dentro de um mesmo ciclo.
     * @param  repasses lista de repasses
     * @return Lista dos detalhes do ciclo de repasse
     */
    public ResultadoPaginado<DetalheCicloRepasseVo> agruparPorPvProduto(List<CicloRepasse> repasses) {
        List<DetalheCicloRepasseVo> detalhesDeRepasses = new ArrayList<>();
        for(CicloRepasse repasse : repasses) {
            List<AutorizacaoPagamento> autorizacoes = repasse.getAutorizacaoPagamentos();

            Map<String, BigDecimal> somaCombustiveis = new HashMap<>();

            for(AutorizacaoPagamento autorizacao: autorizacoes) {
                agruparPrecoProdutos(somaCombustiveis, autorizacao);
            }
            detalhesDeRepasses.addAll(
                    somaCombustiveis.entrySet().stream()
                            .map((e)-> new DetalheCicloRepasseVo(repasse.getDataInicio(), e.getKey(), e.getValue(), repasse))
                            .collect(Collectors.toList())
            );
        }
        return new ResultadoPaginado<>(detalhesDeRepasses, detalhesDeRepasses.size());
    }

    /**
     * Gera os agrupamentos de preço por produto levando em conta um dos itens em um dado abastecimento
     * dentro de um mesmo ciclo.
     * @param  somaProdutos o agrupamento de produtos do abastecimento e ciclo em questão
     * @param  abastecimento o abastecimento do ciclo
     */
    private void agruparPrecoProdutos(Map<String, BigDecimal> somaProdutos, AutorizacaoPagamento abastecimento) {
        abastecimento.getItems().stream().filter(ItemAutorizacaoPagamento::isAbastecimento).forEach(i -> {
            String nomeProduto = i.getNome();
            BigDecimal valorAgrupado = i.getValorTotal();
            if(somaProdutos.get(nomeProduto)!=null) {
                BigDecimal valor = somaProdutos.get(nomeProduto);
                somaProdutos.put(nomeProduto, valor.add(valorAgrupado));
            }
            else {
                somaProdutos.put(nomeProduto, valorAgrupado);
            }
        });
    }
}
