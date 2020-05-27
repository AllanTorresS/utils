package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaHorario;
import ipp.aci.boleia.dominio.TipoVeiculo;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ContextoExecucaoParametroSistemaVo;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.DiaSemana;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Classe que implementa validação de lógica de Horários de Abastecimentos permitidos.
 */
@Component
public class LogicaParametroHorariosAbastecimento implements ILogicaParametroSistema<AutorizacaoPagamento> {

    @Autowired
    private Mensagens mensagens;

    @Override
    public ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> executar(ContextoExecucaoParametroSistemaVo<AutorizacaoPagamento> contexto, FrotaParametroSistema frotaParametroSistema) {
        AutorizacaoPagamento autorizacao = contexto.getDados();
        ResultadoExecucaoParametroSistemaVo<AutorizacaoPagamento> resultado = new ResultadoExecucaoParametroSistemaVo<>(autorizacao);
        Veiculo veiculo = autorizacao.getVeiculo();
        if(veiculo.isProprio()) {
            TipoVeiculo tipoVeiculo = veiculo.getSubtipoVeiculo().getTipoVeiculo();
            Date data = autorizacao.getDataProcessamento();
            Integer diaSemana = UtilitarioCalculoData.obterCampoData(data, Calendar.DAY_OF_WEEK);
            Integer hora = UtilitarioCalculoData.obterCampoData(data, Calendar.HOUR_OF_DAY);
            frotaParametroSistema.getHorarios().stream()
                .filter(h -> h.getTipoVeiculo().getId().equals(tipoVeiculo.getId()))
                .filter(h -> h.getDiaSemana().equals(diaSemana))
                .filter(h -> !h.isPermitido() || hora < h.getDe() || hora > h.getAte())
                .findFirst()
                .ifPresent(h -> {
                    resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                    resultado.setCodigoErro(Erro.ERRO_AUTORIZACAO_DIAS_HORARIOS_PERMITIDOS);
                    resultado.setMensagemErro(obterMensagemErro(autorizacao, frotaParametroSistema.getHorarios(), tipoVeiculo, diaSemana));
                });
        }
        return resultado;
    }

    /**
     * Retorna a mensagem de erro lançada pelo parametro de uso.
     *
     * @param autorizacaoPagamento Autorização de pagamento que foi verificada.
     * @param horarios Lista de horários permitidos para abastecimento.
     * @param tipoVeiculo Tipo de veículo que tentou realizar o abastecimento.
     * @param diaSemana Dia da semana que ocorreu a tentativa de abastecimento.
     * @return A mensagem lançada.
     */
    private String obterMensagemErro(AutorizacaoPagamento autorizacaoPagamento, List<FrotaParametroSistemaHorario> horarios, TipoVeiculo tipoVeiculo, Integer diaSemana) {
        Veiculo veiculo = autorizacaoPagamento.getVeiculo();
        String mensagem = mensagens.obterMensagem(
                "parametro.sistema.erro.abastecimento.horario",
                veiculo.getPlaca(),
                UtilitarioFormatacaoData.formatarDataCurta(autorizacaoPagamento.getDataProcessamento()),
                DiaSemana.obterNomePorDia(diaSemana),
                UtilitarioFormatacaoData.formatarHoraMinutos(autorizacaoPagamento.getDataProcessamento()),
                tipoVeiculo.isLeve() ? mensagens.obterMensagem("parametro.sistema.veiculo.leve.plural") : mensagens.obterMensagem("parametro.sistema.veiculo.pesado.plural")
        );


        String listaHorarios = horarios.stream()
                .filter(h -> h.getTipoVeiculo().getId().equals(tipoVeiculo.getId()))
                .filter(FrotaParametroSistemaHorario::isPermitido)
                .map(h ->
                    mensagens.obterMensagem("parametro.sistema.erro.abastecimento.horario.lista",
                        DiaSemana.obterNomePorDia(h.getDiaSemana()),
                        h.getDe(),
                        h.getAte()
                    )
                ).reduce(String::concat).orElse("");

        return mensagem.concat(listaHorarios);

    }
}
