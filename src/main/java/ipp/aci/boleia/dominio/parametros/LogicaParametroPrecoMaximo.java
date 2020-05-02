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
            StringBuilder produtosViolados = new StringBuilder();
            for (ItemAutorizacaoPagamento item : autorizacao.getItems()) {
                BigDecimal precoMaximoPermitido;
                if (item.isAbastecimento()) {
                    precoMaximoPermitido = precoMaximoCombustiveis.get(item.getCombustivel().getId());
                } else {
                    precoMaximoPermitido = precoMaximoProdutos.get(item.getProduto().getId());
                }
                if (precoMaximoPermitido != null && item.getValorUnitario().compareTo(precoMaximoPermitido) > 0) {
                    resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                    produtosViolados.append(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.preco.maximo.produto", item.getNome(), UtilitarioFormatacao.formatarDecimal(precoMaximoPermitido), UtilitarioFormatacao.formatarDecimal(item.getValorUnitario())));
                }
            }
            if (resultado.getStatusResultado().equals(StatusExecucaoParametroSistema.ERRO)) {
                resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.preco.maximo", veiculo.getPlaca(), produtosViolados.toString()));
            }
        }
        return resultado;
    }

    private Map<Long,BigDecimal> obterMapaProdutos(List<FrotaParametroSistemaPrecoMaximoProduto> precoMaximoProdutos) {
        Map<Long,BigDecimal> resultado = new HashMap<>();
        precoMaximoProdutos.forEach(precoMaximo ->
            resultado.put(precoMaximo.getProduto().getId(), precoMaximo.getPrecoPermitido())
        );
        return resultado;
    }

    private Map<Long,BigDecimal> obterMapaCombustiveis(List<FrotaParametroSistemaPrecoMaximoAbastecimento> precoMaximoCombustiveis) {
        Map<Long,BigDecimal> resultado = new HashMap<>();
        precoMaximoCombustiveis.forEach(precoMaximo ->
            resultado.put(precoMaximo.getCombustivel().getId(), precoMaximo.getPrecoPermitido())
        );
        return resultado;
    }
}
