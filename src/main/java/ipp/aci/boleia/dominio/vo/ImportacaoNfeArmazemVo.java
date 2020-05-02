package ipp.aci.boleia.dominio.vo;

import java.util.Date;
import java.util.List;

/**
 * Objeto que representa o retorno do leitor de e-mails
 */
public class ImportacaoNfeArmazemVo {

    private List<EmailVo> mensagens;
    private Date dataLeitura;

    /**
     * Construtor padrão
     */
    public ImportacaoNfeArmazemVo() {
    }

    /**
     * Obtém instância com parâmetros já populados
     * @param mensagens A lista de e-mails
     * @param dataLeitura Data de processamento do lote de e-mails
     */
    public ImportacaoNfeArmazemVo(List<EmailVo> mensagens, Date dataLeitura) {
        this.mensagens = mensagens;
        this.dataLeitura = dataLeitura;
    }

    public List<EmailVo> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<EmailVo> mensagens) {
        this.mensagens = mensagens;
    }

    public Date getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(Date dataLeitura) {
        this.dataLeitura = dataLeitura;
    }
}
