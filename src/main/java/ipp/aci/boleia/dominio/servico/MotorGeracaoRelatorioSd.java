package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IAbaRelatorioDados;
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
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarMinutosData;

/**
 * Regras de negócio relacionadas ao motor de relatórios
 */
@Component
public class MotorGeracaoRelatorioSd {

    @Autowired
    private IMotorGeracaoRelatoriosDados repositorio;

    @Autowired
    private IAbaRelatorioDados repositorioAbas;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private NotificacaoUsuarioSd notificacaoUsuarioSd;

    @Autowired
    private Mensagens mensagens;

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
     *
     * @return MotorGeracaoRelatorios Retorna o registro criado para o relatório no motor de geração de relatórios
     */
    public MotorGeracaoRelatorios incluir(Date dataPeriodoFiltradoInicial, Date dataPeriodoFiltradoFinal,
                                          BaseFiltroPaginado filtroRelatorio, TipoRelatorioMotorGerador tipoRelatorioMotorGerador, TipoExtensaoArquivo tipoExtensaoArquivo, Usuario usuarioLogado, Date hoje) {

        Date dataDescarte = UtilitarioCalculoData.adicionarDiasData(hoje, 2);
        MotorGeracaoRelatorios m = new MotorGeracaoRelatorios();
        m.setDataRequisicao(hoje);
        m.setTipoRelatorio(tipoRelatorioMotorGerador.getValue());

        m.setDataPeriodoFiltradoInicial(dataPeriodoFiltradoInicial);
        m.setDataPeriodoFiltradoFinal(dataPeriodoFiltradoFinal);
        m.setStatus(StatusMotorGeradorRelatorio.EM_ANDAMENTO.getValue());

        m.setUsuario(usuarioLogado);
        m.setFiltro(UtilitarioJson.toJSON(filtroRelatorio));
        m.setExtensaoArquivo(tipoExtensaoArquivo.getValue());
        m.setNomeTemplate(tipoRelatorioMotorGerador.obterTemplatePorTipoPerfilUsuario(usuarioLogado.getTipoPerfilUsuario()) != null ? tipoRelatorioMotorGerador.obterTemplatePorTipoPerfilUsuario(usuarioLogado.getTipoPerfilUsuario()).getName() : null);

        m.setArquivo(null);
        m.setDataDescarte(dataDescarte);

        m = repositorio.armazenar(m);

        return m;
    }

    /**
     * Obtém um registro de motor de relatórios a partir do seu id
     * @param idMotor o id do registro de motor de relatórios
     * @return o registro de motor relatório
     */
    public MotorGeracaoRelatorios obterPorId(Long idMotor) {
        return repositorio.obterPorId(idMotor);
    }

    /**
     * Armazena um registro de motor de relatórios no banco de dados
     *
     * @param motor A entidade a ser persistida
     * @return A entidade persistida
     */
    public MotorGeracaoRelatorios armazenar(MotorGeracaoRelatorios motor) {
        return repositorio.armazenar(motor);
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
        if (motor.getAbasRelatorio() != null) {
            motor.getAbasRelatorio().forEach(repositorioAbas::armazenar);
        }
        if(status.equals(StatusMotorGeradorRelatorio.CONCLUIDO)) {
            this.notificarUsuarioRelatorioConcluido(motor.getUsuario());
        }
        return motor;
    }

    /**
     * Limpa informações relativas ao andamento do processamento do relatório
     *
     * @param motor O objeto do motor a ser restaurado
     */
    public void preparaMotorParaRefazerRelatorio(MotorGeracaoRelatorios motor) {
        motor.setMsgErro(null);
        motor.setUltimaPaginaProcessada(null);
        motor.setTotalDePaginas(null);
        motor.getAbasRelatorio().forEach(abaRelatorio -> {
            abaRelatorio.setRegistrosProcessados(null);
            abaRelatorio.setTotalRegistros(null);
        });
    }

    /**
     * Valida se existe um relatório igual já em andamento
     *
     * @param filtro O filtro fornecido
     * @param tipoRelatorio O tipo de relatório a ser procurado
     * @throws ExcecaoValidacao Caso haja um relatório em andamento
     */
    public void validaSeRelatorioEmAndamento(String filtro, Integer tipoRelatorio) throws ExcecaoValidacao {
        if (repositorio.pesquisaRelatorioEmAndamento(filtro, tipoRelatorio)) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("servico.exportacao.relatorio.em.andamento"));
        }
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
