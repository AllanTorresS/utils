package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dados.ICombustivelMotorDados;
import ipp.aci.boleia.dados.IFrotaParametroSistemaProdutoAbastecimentoDados;
import ipp.aci.boleia.dados.ITipoCombustivelDados;
import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.CombustivelMotor;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaProdutoAbastecimento;
import ipp.aci.boleia.dominio.ItemAutorizacaoPagamento;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Verifica se o produto do abastecimento condiz com o tipo de motor/combustivel do veciulo em questao
 */
@Component
public class LogicaParametroProdutoAbastecido implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Autowired
    private ITipoCombustivelDados combustivelDados;

    @Autowired
    private ICombustivelMotorDados combustivelMotorDados;

    @Autowired
    private IFrotaParametroSistemaProdutoAbastecimentoDados repositorio;
    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParam) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        List<FrotaParametroSistemaProdutoAbastecimento> combustiveisPermitidos = repositorio.obterPorVeiculo(veiculo.getId());
        if (combustiveisPermitidos != null && !combustiveisPermitidos.isEmpty()) {
            combustiveisPermitidos = combustiveisPermitidos.stream().filter(c -> c.getPermitido().equals(true)).collect(Collectors.toList());
        }
        if(veiculo.isProprio()) {
            for (ItemAutorizacaoPagamento item : autorizacao.getItems()) {
                if (item.isAbastecimento()) {
                    boolean abastecimentoValido;
                    if (combustiveisPermitidos != null && !combustiveisPermitidos.isEmpty()) {
                        abastecimentoValido = combustivelAbastecidoPermitido(combustiveisPermitidos, item.getCombustivel().getId());
                    } else {
                        abastecimentoValido = combustivelAbastecidoValido(autorizacao.getVeiculo().getCombustivelMotor().getId(), item.getCombustivel().getId());
                    }
                    if (!abastecimentoValido) {
                        resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                        String nomeCombustivelInformado = obterNomeCombustivel(item.getCombustivel().getId(), item.getNome());
                        resultado.setMensagemErro(mensagens.obterMensagem("parametro.sistema.erro.abastecimento.produto.abastecido", autorizacao.getVeiculo().getPlaca(), nomeCombustivelInformado));
                        break;
                    }
                }
            }
        }
        return resultado;
    }

    /**
     * Quando um codigo de combustivel informado nao esta de acordo com o
     * combustivel aceito pelo veiculo em questao, tenta descobrir o nome
     * desse combustivel para exibicao de mensagem amigavel na negacao
     * da autorizacao.
     *
     * @param idInformado O id do combustivel informado
     * @param nomeInformado O nome combustivel, caso informado
     * @return O nome do combustivel informado, ou seu nome em banco de dados ou o id,
     *          caso nenhuma das duas informacoes estejam disponiveis
     */
    private String obterNomeCombustivel(Long idInformado, String nomeInformado) {
        String nome = nomeInformado;
        if (nome == null) {
            TipoCombustivel combustivel = combustivelDados.obterPorId(idInformado);
            nome = combustivel != null ? combustivel.getDescricao() : idInformado.toString();
        }
        return nome;
    }

    /**
     * Verifica se o combustivel motor do veiculo é compativel com o abastecido
     * @param idCombustivelMotorVeiculo tipo de combustível do veículo
     * @param idTipoCombustivelAbastecido combustível abastecido
     * @return true se o tipo de combustível abastecido está em conformidade com o tipo de combustível cadastrado para o veículo
     */
    private boolean combustivelAbastecidoValido(Long idCombustivelMotorVeiculo, Long idTipoCombustivelAbastecido) {
        CombustivelMotor combustivelMotor = combustivelMotorDados.obterPorId(idCombustivelMotorVeiculo);
        for(TipoCombustivel tipoCombustivel:combustivelMotor.getTiposCombustivel()) {
            if(tipoCombustivel.getId().equals(idTipoCombustivelAbastecido)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se o combustivel abastecido esta entre a lista de combustiveis permitidos, de acordo com o parametro definido
     * @param params a lista de combustiveis permitidos do veiculo
     * @param idTipoCombustivelAbastecido o id to combustivel abastecido
     * @return true caso o combustivel abastecido esta entre a lista de combustiveis permitidos
     */
    private boolean combustivelAbastecidoPermitido(List<FrotaParametroSistemaProdutoAbastecimento> params, Long idTipoCombustivelAbastecido){
        for (FrotaParametroSistemaProdutoAbastecimento param : params) {
            if (param.getTipoCombustivel().getId().equals(idTipoCombustivelAbastecido) && param.getPermitido()) {
                return true;
            }
        }
        return false;
    }
}
