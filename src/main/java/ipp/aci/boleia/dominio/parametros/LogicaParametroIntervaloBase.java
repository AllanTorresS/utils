package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dados.IAutorizacaoPagamentoDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaIntervaloAbastecimento;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * Implementa a logica da restricao de intervalos de abastecimento
 */
public abstract class LogicaParametroIntervaloBase {

    @Autowired
    protected Mensagens mensagens;

    @Autowired
    private IAutorizacaoPagamentoDados repositorioAutorizacao;

    /**
     * Executa a validação de intervalo entre abastecimentos com base na data e hodometro do veiculo.
     *
     * @param veiculo veiculo a ser validado
     * @param dataRequisicao data da criação do abastecimento
     * @param hodometro hodometro informado
     * @param frotaParam parametro da execução
     * @param resultado que será preenchido com a validação
     */
    public void executarValidacao(Veiculo veiculo, Date dataRequisicao, Long hodometro, FrotaParametroSistema frotaParam, ResultadoExecucaoParametroSistemaVo<?> resultado) {
        if(veiculo.isProprio() || veiculo.isAgregado()) {
            boolean ativoParaTodos = frotaParam.getVerificarIntervaloAbastecimentoTodosVeiculos() != null && frotaParam.getVerificarIntervaloAbastecimentoTodosVeiculos();
            FrotaParametroSistemaIntervaloAbastecimento intervalo = getConfiguracaoVeiculo(frotaParam, veiculo);
            if (ativoParaTodos || (intervalo != null && intervalo.isAtivo())) {
                AutorizacaoPagamento ultimo = repositorioAutorizacao.obterUltimoAbastecimentoVeiculo(veiculo.getId());
                if (ultimo != null) {
                    if(dataRequisicao != null && ((intervalo != null && intervalo.getMinutosIntervaloAbastecimento() != null && intervalo.getMinutosIntervaloAbastecimento() > 0) ||
                            (ativoParaTodos && frotaParam.getMinutosIntervaloAbastecimentoTodosVeiculos() != null && frotaParam.getMinutosIntervaloAbastecimentoTodosVeiculos() > 0))){
                        int minimoMinutos = 0;
                        if (ativoParaTodos && frotaParam.getMinutosIntervaloAbastecimentoTodosVeiculos() != null) {
                            minimoMinutos = frotaParam.getMinutosIntervaloAbastecimentoTodosVeiculos();
                        } else if (intervalo != null && intervalo.getMinutosIntervaloAbastecimento() != null) {
                            minimoMinutos = intervalo.getMinutosIntervaloAbastecimento();
                        }
                        Date dataUltimo = ultimo.getDataRequisicao();
                        long decorrido = (dataRequisicao.getTime() - dataUltimo.getTime()) / 1000 / 60;
                        if (minimoMinutos > 0 && decorrido < minimoMinutos) {
                            resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                            resultado.setCodigoErro(Erro.ERRO_AUTORIZACAO_INTERVALO_PERMITIDO_HORAS);
                            String mensagemErro = obterMensagemErro(UtilitarioFormatacao.formatarPlacaVeiculo(veiculo.getPlaca()), minimoMinutos - decorrido);
                            resultado.setMensagemErro(mensagemErro);
                        }
                    }
                    if(hodometro != null && hodometro > 0 && ((intervalo != null && intervalo.getQuilometrosIntervaloAbastecimento() != null && intervalo.getQuilometrosIntervaloAbastecimento() > 0) ||
                            (ativoParaTodos && frotaParam.getQuilometrosIntervaloAbastecimentoTodosVeiculos() != null && frotaParam.getQuilometrosIntervaloAbastecimentoTodosVeiculos() > 0))){
                        long minimoKm = 0;
                        if (ativoParaTodos && frotaParam.getQuilometrosIntervaloAbastecimentoTodosVeiculos() != null) {
                            minimoKm = frotaParam.getQuilometrosIntervaloAbastecimentoTodosVeiculos();
                        } else if (intervalo != null && intervalo.getQuilometrosIntervaloAbastecimento() != null) {
                            minimoKm = intervalo.getQuilometrosIntervaloAbastecimento();
                        }
                        Long ultimoHodometro = ultimo.getHodometro();
                        long diferenca = hodometro - ultimoHodometro;
                        if (minimoKm > 0 && diferenca < minimoKm) {
                            resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                            resultado.setCodigoErro(Erro.ERRO_AUTORIZACAO_INTERVALO_PERMITIDO_KM);
                            String mensagemErro = obterMensagemErroKm( UtilitarioFormatacao.formatarPlacaVeiculo(veiculo.getPlaca()), minimoKm - diferenca);
                            resultado.setMensagemErro(mensagemErro);
                        }
                    }
                }
            }
        }
    }

    public abstract String obterMensagemErro(String placa, long restante);

    public abstract String obterMensagemErroKm(String placa, long restante);

    /**
     * Obtem a configuracao para o veiculo em questao
     * @param param O parametro
     * @param veiculo O veiculo em questao
     * @return A configuracao para o veiculo em questao
     */
    private FrotaParametroSistemaIntervaloAbastecimento getConfiguracaoVeiculo(FrotaParametroSistema param, Veiculo veiculo) {
        for(FrotaParametroSistemaIntervaloAbastecimento p : param.getIntervalosAbastecimento()) {
            if(p.getVeiculo().getId().equals(veiculo.getId())) {
                return p;
            }
        }
        return null;
    }
}
