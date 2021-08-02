package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.UtilitarioCalculo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Verifica se o volume de produto do abastecimento condiz com tamanho do tanque do
 * veciulo em questao
 */
public abstract class LogicaParametroVolumeAbastecidoBase  {

    private static final String FORMATACAO_EXCEDENTE = "##0.0000";

    @Autowired
    private Mensagens mensagens;

    /**
     * Executa a validação do Volume abastecido e preenche o resultado parametrizado.
     *
     * @param veiculo veiculo a ser validado
     * @param volumeAbastecido volume abastecido
     * @param resultado que será preenchido com a validação
     * @return true se a validação falhar
     */
    public boolean executarValidacao(Veiculo veiculo, BigDecimal volumeAbastecido, ResultadoExecucaoParametroSistemaVo<?> resultado) {
        BigDecimal capacidadeTanque = new BigDecimal(veiculo.getCapacidadeTanque());
        if(volumeAbastecido.compareTo(capacidadeTanque) > 0){
            resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
            resultado.setCodigoErro(Erro.ERRO_AUTORIZACAO_VOLUME_ABASTECIDO);
            BigDecimal excedente = UtilitarioCalculo.calcularPorcentagemExcedente(volumeAbastecido, capacidadeTanque);
            resultado.setMensagemErro(
                    mensagens.obterMensagem("parametro.sistema.erro.abastecimento.volume.abastecido",
                            UtilitarioFormatacao.formatarDecimal(excedente, FORMATACAO_EXCEDENTE),
                            veiculo.getPlaca()));
            return true;
        }
        return false;
    }
}
