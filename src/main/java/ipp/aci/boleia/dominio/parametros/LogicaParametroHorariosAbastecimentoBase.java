package ipp.aci.boleia.dominio.parametros;

import ipp.aci.boleia.dominio.FrotaParametroSistema;
import ipp.aci.boleia.dominio.FrotaParametroSistemaHorario;
import ipp.aci.boleia.dominio.TipoVeiculo;
import ipp.aci.boleia.dominio.Veiculo;
import ipp.aci.boleia.dominio.enums.StatusExecucaoParametroSistema;
import ipp.aci.boleia.dominio.vo.ResultadoExecucaoParametroSistemaVo;
import ipp.aci.boleia.util.DiaSemana;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Classe que implementa validação de lógica de Horários de Abastecimentos permitidos.
 */
public abstract class LogicaParametroHorariosAbastecimentoBase {

    @Autowired
    private Mensagens mensagens;

    /**
     * Executa a validação do abastecimento na configuração de datas permitidas.
     *
     * @param veiculo veiculo a ser validado
     * @param dataProcessamento data da criação/processamento do abastecimento
     * @param frotaParametroSistema parametro da execução
     * @param resultado que será preenchido com a validação
     */
    public void executarValidacao(Veiculo veiculo, Date dataProcessamento, FrotaParametroSistema frotaParametroSistema, ResultadoExecucaoParametroSistemaVo<?> resultado) {
        if(veiculo.isProprio()) {
            TipoVeiculo tipoVeiculo = veiculo.getSubtipoVeiculo().getTipoVeiculo();
            Integer diaSemana = UtilitarioCalculoData.obterCampoData(dataProcessamento, Calendar.DAY_OF_WEEK);
            Integer hora = UtilitarioCalculoData.obterCampoData(dataProcessamento, Calendar.HOUR_OF_DAY);
            frotaParametroSistema.getHorarios().stream()
                    .filter(h -> h.getTipoVeiculo().getId().equals(tipoVeiculo.getId()))
                    .filter(h -> h.getDiaSemana().equals(diaSemana))
                    .filter(h -> !h.isPermitido() || hora < h.getDe() || hora > h.getAte())
                    .findFirst()
                    .ifPresent(h -> {
                        resultado.setStatusResultado(StatusExecucaoParametroSistema.ERRO);
                        resultado.setCodigoErro(Erro.ERRO_AUTORIZACAO_DIAS_HORARIOS_PERMITIDOS);
                        resultado.setMensagemErro(obterMensagemErro(veiculo, dataProcessamento, frotaParametroSistema.getHorarios(), diaSemana));
                    });
        }
    }

    /**
     * Retorna a mensagem de erro lançada pelo parametro de uso.
     *
     * @param veiculo veiculo do abastecimento.
     * @param horarios Lista de horários permitidos para abastecimento.
     * @param diaSemana Dia da semana que ocorreu a tentativa de abastecimento.
     * @return A mensagem lançada.
     */
    private String obterMensagemErro(Veiculo veiculo, Date dataProcessamento, List<FrotaParametroSistemaHorario> horarios, Integer diaSemana) {
        TipoVeiculo tipoVeiculo = veiculo.getSubtipoVeiculo().getTipoVeiculo();
        String mensagem = mensagens.obterMensagem(
                "parametro.sistema.erro.abastecimento.horario",
                veiculo.getPlaca(),
                UtilitarioFormatacaoData.formatarDataCurta(dataProcessamento),
                DiaSemana.obterNomePorDia(diaSemana),
                UtilitarioFormatacaoData.formatarHoraMinutos(dataProcessamento),
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
