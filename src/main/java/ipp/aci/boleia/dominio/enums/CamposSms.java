package ipp.aci.boleia.dominio.enums;


/**
 * Indica os campos necessários ao enviar um SMS pelo IBM marketing Cloud
 */
public enum CamposSms {

    CODIGO_ACESSO("codigoAcesso"),
    CODIGO_ABASTECIMENTO("codigoAbastecimento"),
    SENHA_CONTINGENCIA("senhaContingencia"),
    NOME_MOTORISTA("nomeMotorista"),
    NOME_SOLUCAO("nomeSolucao"),
    NOME_FROTA("nomeFrota"),
    CODIGO_DESBLOQUEIO("codigoAcessoDesbloqueio"),
    LINK_APP("linkApp"),
    TELEFONE("SMS_Phone_Number"),
    TRANSACAO_TIPO("transacao_tipo"),
    DATA("data");

    private final String codigoCampo;

    /**
     * Construtor
     *
     * @param codigoCampo O codigo do campo
     */
    CamposSms(String codigoCampo) {
        this.codigoCampo = codigoCampo;
    }

    /**
     * Obtem o codigo do campo
     * @return O codigo do campo
     */
    public String getCodigoCampo() {
        return codigoCampo;
    }
}
