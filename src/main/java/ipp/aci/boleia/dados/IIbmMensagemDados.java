package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.ContingenciaSms;
import ipp.aci.boleia.dominio.Motorista;


/**
 * Contrato para implementacao de repositórios do IBM Marketing Cloud
 */
public interface IIbmMensagemDados {

    /**
     * Envia SMS com o codigo de desbloqueio para o motorista
     *
     * @param telefone o telefone do motorista
     * @param codigoAcesso o codigo de acesso gerado
     * @param urlBaseSistema o link para instalar o aplicativo
     *
     * @return entidade contendo a resposta da requisição
     */
    ContingenciaSms enviarCodigoDesbloqueio(String telefone, String codigoAcesso, String urlBaseSistema);

    /**
     * Envia SMS com o token de autenticacao regerado para o motorista
     *
     * @param telefone o telefone do motorista
     * @param codigoAcesso o codigo de acesso regerado
     *
     * @return entidade contendo a resposta da requisição
     */
    ContingenciaSms enviarCodigoRegerado(String telefone, String codigoAcesso);

    /**
     * Envia SMS com o código de abastecimento para o motorista
     *
     * @param telefone o telefone do motorista
     * @param codigoAbastecimento o codigo para abastecimentos
     *
     * @return entidade contendo a resposta da requisição
     */
    ContingenciaSms enviarCodigoAbastecimento(String telefone, String codigoAbastecimento);

    /**
     * Envia SMS com a senha de contingencia para o motorista
     *
     * @param telefone o telefone do motorista
     * @param senhaContingencia a senha de contingencia do motorista
     * @param motorista o motorista que ira receber o SMS
     *
     * @return entidade contendo a resposta da requisição
     */
    ContingenciaSms enviarSenhaContingencia(String telefone, String senhaContingencia, Motorista motorista);

}