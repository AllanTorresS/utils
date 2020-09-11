package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAbaRelatorioDados;
import ipp.aci.boleia.dados.IMotorGeracaoRelatoriosDados;
import ipp.aci.boleia.dominio.MotorGeracaoRelatorios;
import ipp.aci.boleia.dominio.NotificacaoUsuario;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusMotorGeradorRelatorio;
import ipp.aci.boleia.dominio.enums.TipoSubcategoriaNotificacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarMinutosData;

@Component
public class MotorGeracaoRelatorioSd {

    @Autowired
    private IMotorGeracaoRelatoriosDados repositorio;

    @Autowired
    private IAbaRelatorioDados repositorioAbas;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private  NotificacaoUsuarioSd notificacaoUsuarioSd;

    /**
     * Informar conclusão da geração do relatório no motor de geração de relatórios.
     * @param motor o relatório gerado no motor.
     * @param status Novo status a ser aplicado.
     * @param msgErro Mensagem de erro relacionada a algum erro ao gerar o relatorio.
     * @return motor de geração de relatórios.
     */
    public MotorGeracaoRelatorios salvarStatus(MotorGeracaoRelatorios motor, StatusMotorGeradorRelatorio status, String msgErro) {
        Date dataEmissao = ambiente.buscarDataAmbiente();
        Date dataDescarte = UtilitarioCalculoData.adicionarDiasData(dataEmissao, 2);
        motor.setDataRequisicao(dataEmissao);
        motor.setDataDescarte(dataDescarte);
        motor.setStatus(status.getValue());
        if(status.equals(StatusMotorGeradorRelatorio.ERRO)){
            motor.setMsgErro(msgErro);
        }
        motor = repositorio.armazenar(motor);
        motor.getAbasRelatorio().stream().forEach(repositorioAbas::armazenar);
        if(status.equals(StatusMotorGeradorRelatorio.CONCLUIDO)) {
            this.notificarUsuarioRelatorioConcluido(motor.getUsuario());
        }
        return motor;
    }

    /**
     * Envia uma notificação para o usuário
     *
     * @param usuario usuario que solicitou o relatorio
     */
    private void notificarUsuarioRelatorioConcluido(Usuario usuario){
        NotificacaoUsuario notificacaoUsuario = notificacaoUsuarioSd.obterUltimaNotificacaoUsuarioPorSubCategoria(usuario, TipoSubcategoriaNotificacao.RELATORIO_CONCLUIDO);

        if (notificacaoUsuario == null ) {
            notificacaoUsuarioSd.enviarNotificacaoRelatorioConcluido(usuario);
        } else if (adicionarMinutosData(notificacaoUsuario.getNotificacao().getDataEnvio(),5).before(ambiente.buscarDataAmbiente())) {
            notificacaoUsuarioSd.enviarNotificacaoRelatorioConcluido(usuario);
        }
    }

}
