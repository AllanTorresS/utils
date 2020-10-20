package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPrecoMaximoAbastecimento;
import ipp.aci.boleia.dominio.FrotaParametroSistemaPrecoMaximoProduto;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Verifica se o produto do abastecimento condiz com o tipo de motor/combustivel do veciulo em questao
 */
@Component
public class LogicaParametroPrecoMaximo implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        if(veiculo.isProprio()) {
            Map<Long, BigDecimal> precoMaximoProdutos = obterMapaProdutos(frotaParam.getPrecoMaximoProdutos());
            Map<Long, BigDecimal> precoMaximoCombustiveis = obterMapaCombustiveis(frotaParam.getPrecoMaximoAbastecimentos());
            Map<Long, BigDecimal> quantidadeMaximaProdutosPorUnidade= obterMapaQuantidadePorProduto(frotaParam.getQuantidadeMaximaPorProduto());
            Map<Long, BigDecimal> quantidadeMaximaLitrosCombustivel = obterMapaQuantidadePorAbastecimento(frotaParam.getQuantidadeMaximaPorCombustivel());
            StringBuilder produtosViolados = new StringBuilder();
            for (ItemAutorizacaoPagamento item : autorizacao.getItems()) {
                BigDecimal precoMaximoPermitido;
                BigDecimal quantidadeMaximaPermitidaAbastecimento = null;
                BigDecimal quantidadeMaximaPermitidaProduto = null;

                if (item.isAbastecimento()) {
                    precoMaximoPermitido = precoMaximoCombustiveis.get(item.getCombustivel().getId());
                    quantidadeMaximaPermitidaAbastecimento = quantidadeMaximaLitrosCombustivel.get(item.getCombustivel().getId());
                } else {
                    precoMaximoPermitido = precoMaximoProdutos.get(item.getProduto().getId());
                    quantidadeMaximaPermitidaProduto = quantidadeMaximaProdutosPorUnidade.get(item.getProduto().getId());
                }

                if ((quantidadeMaximaPermitidaProduto != null && item.getQuantidade().compareTo(quantidadeMaximaPermitidaProduto) > 0 || quantidadeMaximaPermitidaAbastecimento != null && item.getQuantidade().compareTo((quantidadeMaximaPermitidaAbastecimento)) > 0)) {
                    resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                    BigDecimal quantidadeMaximaPermitida = quantidadeMaximaPermitidaProduto;
                    if(item.isAbastecimento()) {
                        quantidadeMaximaPermitida = quantidadeMaximaPermitidaAbastecimento;
                    }

                    if(item.isAbastecimento() || item.getProduto().isLitragem()) {
                        produtosViolados.append(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.quantidade.maxima.produto", item.getNome(), UtilitarioFormatacao.formatarDecimal(quantidadeMaximaPermitida), UtilitarioFormatacao.formatarDecimal(item.getQuantidade())));
                    } else {
                        produtosViolados.append(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.quantidade.maxima.produto", item.getNome(), UtilitarioFormatacao.formatarInteiro(quantidadeMaximaPermitida), UtilitarioFormatacao.formatarDecimal(item.getQuantidade())));
                    }
                } else if (precoMaximoPermitido != null && item.getValorUnitario().compareTo(precoMaximoPermitido) > 0) {
                    resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                    produtosViolados.append(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.preco.maximo.produto", item.getNome(), UtilitarioFormatacao.formatarDecimal(precoMaximoPermitido), UtilitarioFormatacao.formatarDecimal(item.getValorUnitario())));
                }
            }
            if (resultado.getStatusResultado().equals(StatusExecucaoParametroSistema.ERRO)) {
                resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.quantidadePreco.maximo", veiculo.getPlaca(), produtosViolados.toString()));
            }
        }
        return resultado;
    }

    /**
     * Obtem a quantidade de litros do abastecimento
     * @param quantidadePermitidaProdutos
     * @return retorna um mapa de quantidade de produtos que foram obtidos
     */
    private Map<Long,BigDecimal> obterMapaQuantidadePorProduto(List<FrotaParametroSistemaPrecoMaximoProduto> quantidadePermitidaProdutos) {
        Map<Long,BigDecimal> resultado = new HashMap<>();
        quantidadePermitidaProdutos.forEach(quantidadeMaxima ->
                resultado.put(quantidadeMaxima.getProduto().getId(), quantidadeMaxima.getQuantidadePermitida())
        );
        return resultado;
    }

    /**
     * Obtem a quantidade de litros do abastecimento
     * @param quantidadePermitidaCombustivel
     * @return retorna um mapa de litros abastecidos
     */
    private Map<Long,BigDecimal> obterMapaQuantidadePorAbastecimento(List<FrotaParametroSistemaPrecoMaximoAbastecimento> quantidadePermitidaCombustivel) {
        Map<Long,BigDecimal> resultado = new HashMap<>();
        quantidadePermitidaCombustivel.forEach(quantidadeMaxima ->
                resultado.put(quantidadeMaxima.getCombustivel().getId(), quantidadeMaxima.getQuantidadePermitida())
        );
        return resultado;
    }

    /**
     * Obtém um mapa que relaciona o id de cada preço máximo de um produto com seu respectivo preço permitido
     * @param precoMaximoProdutos Lista de entidades de preço máximo produto
     * @return retorna um mapa de id e preço máximo permitido por produto
     */
    private Map<Long,BigDecimal> obterMapaProdutos(List<FrotaParametroSistemaPrecoMaximoProduto> precoMaximoProdutos) {
        Map<Long,BigDecimal> resultado = new HashMap<>();
        precoMaximoProdutos.forEach(precoMaximo ->
            resultado.put(precoMaximo.getProduto().getId(), precoMaximo.getPrecoPermitido())
        );
        return resultado;
    }

    /**
     * Obtém um mapa que relaciona o id de cada preço máximo de um abastecimento com seu respectivo preço permitido
     * @param precoMaximoCombustiveis Lista de entidades de preço máximo abastecimento
     * @return retorna um mapa de id e preço máximo permitido por abastecimento
     */
    private Map<Long,BigDecimal> obterMapaCombustiveis(List<FrotaParametroSistemaPrecoMaximoAbastecimento> precoMaximoCombustiveis) {
        Map<Long,BigDecimal> resultado = new HashMap<>();
        precoMaximoCombustiveis.forEach(precoMaximo ->
            resultado.put(precoMaximo.getCombustivel().getId(), precoMaximo.getPrecoPermitido())
        );
        return resultado;
    }
}
