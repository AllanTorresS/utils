package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IConfiguracaoSistemaDados;
import ipp.aci.boleia.dados.IContingenciaSmsDados;
import ipp.aci.boleia.dados.IIbmMensagemDados;
import ipp.aci.boleia.dados.IMensagemDados;
import ipp.aci.boleia.dominio.ConfiguracaoSistema;
import ipp.aci.boleia.dominio.ContingenciaSms;
import ipp.aci.boleia.dominio.DispositivoMotorista;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.enums.ChaveConfiguracaoSistema;
import ipp.aci.boleia.dominio.enums.CodigoTelefonePais;
import ipp.aci.boleia.dominio.enums.StatusHabilitacao;
import ipp.aci.boleia.dominio.enums.TipoServicoSms;
import ipp.aci.boleia.dominio.vo.TokenVo;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static ipp.aci.boleia.util.UtilitarioFormatacaoData.formatarDataCurtaHora;

/**
 * Oferece funcionalidades para envio de sms
 */
@Component
public class SmsSd {

    @Value("${baseurl.sistema}")
    private String urlBaseSistema;

    @Autowired
    private MotoristaSd regrasNegocio;

    @Autowired
    private IMensagemDados envioMensagensAWS;

    @Autowired
    private IIbmMensagemDados envioMensagensIBM;

    @Autowired
    private IConfiguracaoSistemaDados repositorioConfiguracaoSistema;

    @Autowired
    private IContingenciaSmsDados contingenciaSmsDados;

    @Autowired
    private Mensagens mensagens;

    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    /**
     * Envia a senha de contingencia para o motorista de acordo com a configuracao de SMS selecionada na solucao
     *
     * @param motorista o motorista que recebera o SMS
     * @param senhaEmClaro a senha de contingencia que sera enviada ao motorista
     */
    public void enviarSenhaContingencia(Motorista motorista, String senhaEmClaro){
        if(servicoAwsAtivo()){
            String sms = regrasNegocio.obterMensagemSmsSenhaContingencia(motorista, senhaEmClaro);
            envioMensagensAWS.enviarSMS(sms, CodigoTelefonePais.BRASIL.getCodigoComPrefixo() + motorista.getDddTelefoneCelular() + motorista.getTelefoneCelular());
        } else {
            ContingenciaSms resposta = envioMensagensIBM.enviarSenhaContingencia(CodigoTelefonePais.BRASIL.getCodigo().toString() + motorista.getDddTelefoneCelular() + motorista.getTelefoneCelular(), senhaEmClaro, motorista);
            contingenciaSmsDados.armazenar(resposta);
        }
    }

    /**
     * Envia o codigo de abastecimento de acordo com a configuracao de SMS selecionada na solucao
     *
     * @param motorista o motorista que recebera o SMS
     * @param token o codigo de abastecimento a ser enviado ao motorista
     */
    public void enviarSoftToken(Motorista motorista, TokenVo token){
        if(servicoAwsAtivo()){
            envioMensagensAWS.enviarSMS(regrasNegocio.obterMensagemSmsSoftToken(token), CodigoTelefonePais.BRASIL.getCodigoComPrefixo() + motorista.getDddTelefoneCelular() + motorista.getTelefoneCelular());
        } else {
            ContingenciaSms resposta = envioMensagensIBM.enviarCodigoAbastecimento(CodigoTelefonePais.BRASIL.getCodigo().toString() + motorista.getDddTelefoneCelular() + motorista.getTelefoneCelular(), token.getToken());
            contingenciaSmsDados.armazenar(resposta);
        }
    }

    /**
     * Envia o codigo de bloqueio de acordo com a configuracao de SMS selecionada na solucao
     *
     * @param dispositivo o dispositivo do motorista
     * @param motorista o motorista que receberá o sms
     */
    public void enviarCodidoDesbloqueio(Motorista motorista, DispositivoMotorista dispositivo){
        if(servicoAwsAtivo()){
            String sms = mensagens.obterMensagem(dispositivo.getStatusHabilitacao().equals(StatusHabilitacao.NAO_HABILITADO.getValue()) ? "motorista.servico.sns.token" : "motorista.servico.sns.tokenRegerado", urlBaseSistema, dispositivo.getToken());
            envioMensagensAWS.enviarSMS(sms, CodigoTelefonePais.BRASIL.getCodigoComPrefixo() + motorista.getDddTelefoneCelular() + motorista.getTelefoneCelular());
        } else {
            if(dispositivo.getStatusHabilitacao().equals(StatusHabilitacao.NAO_HABILITADO.getValue())){
                ContingenciaSms resposta = envioMensagensIBM.enviarCodigoDesbloqueio(CodigoTelefonePais.BRASIL.getCodigo().toString() + motorista.getDddTelefoneCelular() + motorista.getTelefoneCelular(), dispositivo.getToken(), urlBaseSistema);
                contingenciaSmsDados.armazenar(resposta);
            } else {
                ContingenciaSms resposta = envioMensagensIBM.enviarCodigoRegerado(CodigoTelefonePais.BRASIL.getCodigo().toString() + motorista.getDddTelefoneCelular() + motorista.getTelefoneCelular(), dispositivo.getToken());
                contingenciaSmsDados.armazenar(resposta);
            }
        }
    }

    /**
     * Envia a senha temporária de acesso ao app motorista.
     *
     * @param motorista Motorista que receberá a senha.
     * @param senha Senha temporária do motorista.
     */
    public void enviarSenhaTemporariaMotorista(Motorista motorista, String senha){
        String dataHora = formatarDataCurtaHora(utilitarioAmbiente.buscarDataAmbiente());
        String sms = mensagens.obterMensagem("motorista.servico.senha.temporaria", dataHora, motorista.getNome(), senha);
        envioMensagensAWS.enviarSMS(sms, CodigoTelefonePais.BRASIL.getCodigoComPrefixo() + motorista.getDddTelefoneCelular() + motorista.getTelefoneCelular());
    }

    /**
     * Verifica qual serviço de SMS foi selecionado na solucao
     * @return true se o SMS deve ser enviado por AWS e false se deve ser utilizado o serviço da IBM
     */
    private Boolean servicoAwsAtivo(){
        ConfiguracaoSistema configuracaoSistema = repositorioConfiguracaoSistema.buscarConfiguracoes(ChaveConfiguracaoSistema.SERVICO_ENVIO_SMS);
        return configuracaoSistema.getParametro() == null || configuracaoSistema.getParametro().equals(TipoServicoSms.AWS.getValue());
    }

}
