package ipp.aci.boleia.dominio.vo;

/**
 * Representa a mensagem a ser enviada através do serviço IBM Marketing Cloud
 */
public class IbmMensagemVo {

    private AutenticacaoVo autenticacao;
    private TransacaoVo transacao;

    public IbmMensagemVo() {
        //Construtor default
    }

    /**
     * Construtor da mensagem IBM
     * @param transacao O assunto da mensagem
     */
    public IbmMensagemVo(TransacaoVo transacao) {
        this.transacao = transacao;
    }

    public AutenticacaoVo getAutenticacao() {
        return autenticacao;
    }

    public void setAutenticacao(AutenticacaoVo autenticacao) {
        this.autenticacao = autenticacao;
    }

    public TransacaoVo getTransacao() {
        return transacao;
    }

    public void setTransacao(TransacaoVo transacao) {
        this.transacao = transacao;
    }
}
