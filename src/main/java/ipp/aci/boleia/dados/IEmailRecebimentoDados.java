package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.vo.ConfiguracaoLeitorEmailVo;
import ipp.aci.boleia.dominio.vo.ImportacaoNfeArmazemVo;

import java.util.Date;

/**
 * Contrato para implementação de repositório de recebimento de e-mails
 */
public interface IEmailRecebimentoDados {

    /**
     * Conecta ao e-mail, obtém os e-mails e retorna as mensagens
     * @param config Objeto de configuração da conexão ao e-mail
     * @param dataInicio Data a partir da qual serão buscados os e-mails
     * @return Objeto contendo os e-mails encontrados
     */
    ImportacaoNfeArmazemVo obterEmailsAPartirDe(ConfiguracaoLeitorEmailVo config, Date dataInicio);

    /**
     * Conecta ao e-mail, Obtém todos os e-mails entre uma determinada data e hora até uma data e hora final
     * @param config Objeto de configuração da conexão ao e-mail
     * @param dataInicio Data de início da busca
     * @param dataFinal Data de Fim da busca
     * @return Objeto contendo os e-mails encontrados
     */
    ImportacaoNfeArmazemVo obterEmailsEntre(ConfiguracaoLeitorEmailVo config, Date dataInicio, Date dataFinal);

    /**
     * Conecta ao e-mail e obtém todos os e-mails
     * @param config Objeto de configuração da conexão ao e-mail
     * @return Objeto contendo os e-mails encontrados
     */
    ImportacaoNfeArmazemVo obterTodosOsEmails(ConfiguracaoLeitorEmailVo config);

    /**
     * Retorna o protocolo da implementação de recebimento de e-mail
     * @return O nome do protocolo
     */
    String getProtocolo();
}
