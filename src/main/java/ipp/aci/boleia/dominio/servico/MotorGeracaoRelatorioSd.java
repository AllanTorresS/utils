package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IMotorGeracaoRelatoriosDados;
import ipp.aci.boleia.dominio.MotorGeracaoRelatorios;
import ipp.aci.boleia.dominio.NotificacaoUsuario;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusMotorGeradorRelatorio;
import ipp.aci.boleia.dominio.enums.TipoExtensaoArquivo;
import ipp.aci.boleia.dominio.enums.TipoRelatorioMotorGerador;
import ipp.aci.boleia.dominio.enums.TipoSubcategoriaNotificacao;
import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioJson;
import ipp.aci.boleia.util.mensageria.UtilitarioEnvioMensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarMinutosData;

/**
 * Classe responsável pelo motor de geração de relatório.
 */

@Component
public class MotorGeracaoRelatorioSd {

    @Autowired
    private IMotorGeracaoRelatoriosDados repositorio;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private  NotificacaoUsuarioSd notificacaoUsuarioSd;

    @Autowired
    private UtilitarioEnvioMensagens utilitarioEnvioMensagens;

    @Value("${rabbitmq.prefixo.chave-rota}")
    private String prefixoChaveRotaRelatorio;

    /**
     * Inclusão de relatório no motor de geração de relatórios
     * e envio para o listener
     *
     * @param dataPeriodoFiltradoInicial Data inicial do filtro do relatório
     * @param dataPeriodoFiltradoFinal Data final do filtro do relatório
     * @param filtroRelatorio Filtro usado para emissão do relatório
     * @param tipoRelatorioMotorGerador Tipo do relatório emitido
     * @param tipoExtensaoArquivo Tipo da Extensao do arquivo a ser armazenado
     * @param usuarioLogado Usuario logado no sistema
     * @param hoje Data de hoje
     *
     * @return MotorGeracaoRelatorios Retorna o registro criado para o relatório no motor de geração de relatórios
     * @throws IOException Exceção lançada haja um erro no envio do relatório para a fila.
     */
    public MotorGeracaoRelatorios incluir(Date dataPeriodoFiltradoInicial, Date dataPeriodoFiltradoFinal,
                                          BaseFiltroPaginado filtroRelatorio, TipoRelatorioMotorGerador tipoRelatorioMotorGerador, TipoExtensaoArquivo tipoExtensaoArquivo, Usuario usuarioLogado, Date hoje) throws IOException {
        MotorGeracaoRelatorios m = this.salvarNovoRelatorio(dataPeriodoFiltradoInicial, dataPeriodoFiltradoFinal, filtroRelatorio, tipoRelatorioMotorGerador, tipoExtensaoArquivo, usuarioLogado, hoje);

        enviarRelatorioParaProcessamento(m.getId(), tipoRelatorioMotorGerador);

        return m;
    }

    /**
     * Envia um relatório para ser processado no módulo de relatórios
     *
     * @param idRelatorio O identificador da solicitação de relatório
     * @param tipoRelatorioMotorGerador O tipo do relatório a ser emitido
     * @throws IOException Exceção lançada haja um erro no envio do relatório para a fila.
     */
    private void enviarRelatorioParaProcessamento(Long idRelatorio, TipoRelatorioMotorGerador tipoRelatorioMotorGerador) throws IOException {
        utilitarioEnvioMensagens.enviarMensagem(idRelatorio.toString(), tipoRelatorioMotorGerador.name());
    }

    /**
     * Inclusão de relatório no motor de geração de relatórios
     *
     * @param dataPeriodoFiltradoInicial Data inicial do filtro do relatório
     * @param dataPeriodoFiltradoFinal Data final do filtro do relatório
     * @param filtroRelatorio Filtro usado para emissão do relatório
     * @param tipoRelatorioMotorGerador Tipo do relatório emitido
     * @param tipoExtensaoArquivo Tipo da Extensao do arquivo a ser armazenado
     * @param usuarioLogado Usuario logado no sistema
     * @param hoje Data de hoje
     * @return MotorGeracaoRelatorios Retorna o registro criado para o relatório no motor de geração de relatórios
     */
    private MotorGeracaoRelatorios salvarNovoRelatorio(Date dataPeriodoFiltradoInicial, Date dataPeriodoFiltradoFinal,
                                                       BaseFiltroPaginado filtroRelatorio, TipoRelatorioMotorGerador tipoRelatorioMotorGerador, TipoExtensaoArquivo tipoExtensaoArquivo, Usuario usuarioLogado, Date hoje) {
        Date dataDescarte = UtilitarioCalculoData.adicionarDiasData(hoje, 2);
        MotorGeracaoRelatorios m = new MotorGeracaoRelatorios();
        m.setDataRequisicao(hoje);
        m.setTipoRelatorio( tipoRelatorioMotorGerador.getValue() );

        m.setDataPeriodoFiltradoInicial( dataPeriodoFiltradoInicial );
        m.setDataPeriodoFiltradoFinal( dataPeriodoFiltradoFinal );
        m.setStatus(StatusMotorGeradorRelatorio.EM_ANDAMENTO_AGUARDANDO.getValue());

        m.setUsuario(usuarioLogado);
        m.setFiltro( UtilitarioJson.toJSON( filtroRelatorio ) );
        m.setExtensaoArquivo(tipoExtensaoArquivo.getValue());

        m.setArquivo( null );
        m.setDataDescarte( dataDescarte );
        m = repositorio.armazenar( m );

        return m;
    }

    /**
     * Informar conclusão da geração do relatório no motor de geração de relatórios.
     * @param idMotor id do relatório gerado no motor.
     * @param status Novo status a ser aplicado.
     * @param msgErro Mensagem de erro relacionada a algum erro ao gerar o relatorio.
     * @return motor de geração de relatórios.
     */
    public MotorGeracaoRelatorios salvarStatus( Long idMotor, StatusMotorGeradorRelatorio status, String msgErro) {
        MotorGeracaoRelatorios motor = repositorio.obterPorId(idMotor);
        Date dataEmissao = ambiente.buscarDataAmbiente();
        Date dataDescarte = UtilitarioCalculoData.adicionarDiasData(dataEmissao, 2);
        motor.setDataRequisicao(dataEmissao);
        motor.setDataDescarte(dataDescarte);
        motor.setStatus(status.getValue());
        if(status.equals(StatusMotorGeradorRelatorio.ERRO)){
            motor.setMsgErro(msgErro);
        }
        motor = repositorio.armazenar(motor);
        if(status.equals(StatusMotorGeradorRelatorio.CONCLUIDO)) {
            this.notificarUsuarioRelatorioConcluido(motor.getUsuario());
        }
        return motor;
    }

    /**
     * Valida se o status atual do motor de relatorio é igual a cancelado
     * @param idMotorRelatorio id do motor de relatorio a ser verificado
     * @return boleano se status eh cancelado ou nao
     */
    public Boolean validaStatusRelatorioAtualEhCancelado(Long idMotorRelatorio){
        return StatusMotorGeradorRelatorio.CANCELADO.getValue().equals(repositorio.obterPorId(idMotorRelatorio).getStatus());
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
