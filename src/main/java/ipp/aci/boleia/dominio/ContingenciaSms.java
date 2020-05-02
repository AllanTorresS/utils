package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Representa a tabela de Contingencia SMS
 */
@Entity
@Audited
@Table(name = "CONTINGENCIA_SMS")
public class ContingenciaSms implements IPersistente {

    private static final String SUCESSO = "200";
    private static final long serialVersionUID = -8536928732394059411L;

    @Id
    @Column(name = "CD_CONTINGENCIA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTINGENCIA")
    @SequenceGenerator(name = "SEQ_CONTINGENCIA", sequenceName = "SEQ_CONTINGENCIA", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "NM_HTTP_STATUS")
    private String httpStatus;

    @NotNull
    @Column(name = "NM_TRANSACAO")
    private String transacao;

    @Column(name = "NM_ERRO")
    private String erro;

    @Column(name = "NM_MENSAGEM")
    private String mensagem;

    @NotNull
    @Column(name = "DT_RETORNO")
    private String dataHora;

    /*
     *  Instancia uma nova entidade do tipo ContingenciaSms
     */
    public ContingenciaSms() {
        //Construtor default
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(String httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getTransacao() {
        return transacao;
    }

    public void setTransacao(String transacao) {
        this.transacao = transacao;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    /**
     * Em caso de erro na chamada, a mensagem de erro deve ser armazenada no campo "erro" da entidade
     */
    public void setMensagemDeErro() {
        if(!httpStatus.equals(SUCESSO)){
            this.erro = this.mensagem;
            this.mensagem = null;
        }
    }
}