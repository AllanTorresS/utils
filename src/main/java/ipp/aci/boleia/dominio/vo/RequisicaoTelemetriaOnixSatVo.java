package ipp.aci.boleia.dominio.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe com informações relacionadas à requisição OnixSat de telemetria.
 */
@XmlRootElement(name = "RequestMensagemCB")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequisicaoTelemetriaOnixSatVo {

    @XmlElement(required = true)
    private String login;

    @XmlElement(required = true)
    private String senha;

    @XmlElement(name = "mId", required = true)
    private Long idMensagem;

    public RequisicaoTelemetriaOnixSatVo() {
        //Construtor default
    }

    /**
     * Construtor da Requisição de Telemetria
     * @param login o login no serviço.
     * @param senha a senha do serviço.
     * @param idMensagem o id da última mensagem recebida.
     */
    public RequisicaoTelemetriaOnixSatVo(String login, String senha, Long idMensagem) {
        this.login = login;
        this.senha = senha;
        this.idMensagem = idMensagem;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(Long idMensagem) {
        this.idMensagem = idMensagem;
    }
}
