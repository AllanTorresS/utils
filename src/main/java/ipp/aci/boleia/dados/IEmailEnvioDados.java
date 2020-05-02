package ipp.aci.boleia.dados;

import javax.activation.DataSource;
import java.util.List;

/**
 * Contrato para implementacao de reposiórios de emails.
 */
public interface IEmailEnvioDados {

    /**
     * Envia um email para os destinatarios definidos
     *
     * @param assunto Assunto do email
     * @param corpo   Corpo HTML do email
     * @param destinatarios Destinatarios do email
     */
    void enviarEmail(String assunto, String corpo, List<String> destinatarios);

    /**
     * Envia um email para os destinatarios definidos, com arquivo anexado
     *
     * @param assunto       Assunto do email
     * @param corpo         Corpo HTML do email
     * @param destinatarios Destinatarios do email
     * @param anexo         Arquivo que será anexado ao email
     * @param nomeAnexo     Nome do arquivo anexado
     */
    void enviarEmail(String assunto, String corpo, List<String> destinatarios, DataSource anexo, String nomeAnexo);
}