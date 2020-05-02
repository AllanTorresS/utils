package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaProduto;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Verifica se os produtos e servicos adquiridos sao permitidos para pagamento pelo Boleia
 */
@Component
public class LogicaParametroProdutosAdicionais implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        if(veiculo.isProprio()) {
            Map<Long, FrotaParametroSistemaProduto> mapaProdutos = obterMapaProdutos(frotaParam);
            StringBuilder produtosInvalidos = new StringBuilder();
            for (ItemAutorizacaoPagamento item : autorizacao.getItems()) {
                if (!item.isAbastecimento()) {
                    FrotaParametroSistemaProduto permissaoProduto = mapaProdutos.get(item.getProduto().getId());
                    if (permissaoProduto != null && !permissaoProduto.isPermitido()) {
                        produtosInvalidos.append(permissaoProduto.getProduto().getNome()).append(", ");
                    }
                }
            }
            if (produtosInvalidos.length() > 0) {
                String strProdutos = produtosInvalidos.toString();
                resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.produtos.adicionais",
                        autorizacao.getVeiculo().getPlaca(), strProdutos.substring(0, strProdutos.length() - 2)));
            }
        }
        return resultado;
    }

    /**
     * Mapeia os produtos de um parametro frota pelos seus IDs
     * @param frotaParam O parametro frota que contem os produtos
     * @return O mapa
     */
    private Map<Long,FrotaParametroSistemaProduto> obterMapaProdutos(FrotaParametroSistema frotaParam) {
        List<FrotaParametroSistemaProduto> produtos = frotaParam.getProdutos();
        return produtos.stream().collect(Collectors.toMap(p -> p.getProduto().getId(), p -> p));
    }
}
