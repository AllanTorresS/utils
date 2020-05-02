package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.UtilitarioCalculo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Verifica se o volume de produto do abastecimento condiz com tamanho do tanque do
 * veciulo em questao
 */
@Component
public class LogicaParametroVolumeAbastecido implements ILogicaParametroSistema<AutorizacaoPagamento> {

    private static final String FORMATACAO_EXCEDENTE = "##0.0000";

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        if(veiculo.getCapacidadeTanque() != null) {
            for (ItemAutorizacaoPagamento item : autorizacao.getItems()) {
                if (item.isAbastecimento()) {
                    BigDecimal volumeAbastecido = item.getQuantidade();
                    BigDecimal capacidadeTanque = new BigDecimal(veiculo.getCapacidadeTanque());
                    if(volumeAbastecido.compareTo(capacidadeTanque) > 0){
                        resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                        BigDecimal excedente = UtilitarioCalculo.calcularPorcentagemExcedente(volumeAbastecido, capacidadeTanque);
                        resultado.setMensagemErro(
                            mensagens.obterMensagem("parametro.sistema.erro.abastecimento.volume.abastecido",
                                UtilitarioFormatacao.formatarDecimal(excedente, FORMATACAO_EXCEDENTE),
                                veiculo.getPlaca()));
                        break;
                    }
                }
            }
        }
        return resultado;
    }

}
