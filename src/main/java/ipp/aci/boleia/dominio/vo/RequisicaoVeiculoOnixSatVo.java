package ipp.aci.boleia.dominio.vo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Classe com informações relacionadas à requisição OnixSat de veículo.
 */
@XmlRootElement(name = "RequestVeiculo")
@XmlAccessorType(XmlAccessType.FIELD)
public class RequisicaoVeiculoOnixSatVo {

    @XmlElement(required = true)
    private String login;

    @XmlElement(required = true)
    private String senha;

    public RequisicaoVeiculoOnixSatVo() {
        //Construtor default
    }

    /**
     * Construtor da Requisição de Veículo
     * @param login o login no serviço
     * @param senha a senha do serviço
     */
    public RequisicaoVeiculoOnixSatVo(String login, String senha) {
        this.login = login;
        this.senha = senha;
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
}
