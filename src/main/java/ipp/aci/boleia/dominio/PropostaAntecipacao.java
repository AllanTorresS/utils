package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.StatusPropostaXP;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Proposta Antecipação
 */
@Entity
@Audited
@Table(name = "PROPOSTA_ANTECIPACAO")
public class PropostaAntecipacao implements IPersistente, IPertenceRevendedor, IExclusaoLogica {

    private static final long serialVersionUID = -2504208956728309180L;

    @Id
    @Column(name = "CD_PROPOSTA_ANTECIPACAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PROPOSTA_ANTECIPACAO")
    @SequenceGenerator(name = "SEQ_PROPOSTA_ANTECIPACAO", sequenceName = "SEQ_PROPOSTA_ANTECIPACAO", allocationSize = 1)
    private Long id;

    @Column(name = "CD_PROPOSTA_XP")
    private String idParceiro;

    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name = "ID_ACEITE_USUARIO")
    private Boolean isAceito;

    @Lob
    @Column(name = "DS_PROPOSTA_JSON")
    private String propostaJson;

    @OneToOne(mappedBy = "propostaAntecipacao", fetch = FetchType.LAZY)
    private ReembolsoAntecipado reembolsoAntecipado;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DT_DESEMBOLSO")
    private Date dataDesembolso;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DT_CRIACAO")
    private Date dataCriacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DT_ATUALIZACAO")
    private Date dataAtualizacao;

    @Lob
    @Column(name = "DS_TEXTO_CONTRATO")
    private String textoContrato;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DT_CIENCIA_ERRO")
    private Date dataCienciaErro;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO_CIENTE", referencedColumnName = "CD_USUARIO")
    private Usuario usuarioCienteErro;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_COBRANCA_XP")
    private CobrancaXP cobrancaXp;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_REEMBOLSO_XP")
    private ReembolsoXP reembolsoXp;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getIdParceiro() {
        return idParceiro;
    }

    public void setIdParceiro(String idParceiro) {
        this.idParceiro = idParceiro;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public StatusPropostaXP getStatusEnum() {
        return StatusPropostaXP.obterPorValor(status);
    }

    public Boolean isAceito() {
        return isAceito;
    }

    public void setAceito(Boolean aceito) {
        isAceito = aceito;
    }

    public String getPropostaJson() {
        return propostaJson;
    }

    public void setPropostaJson(String propostaJson) {
        this.propostaJson = propostaJson;
    }

    public ReembolsoAntecipado getReembolsoAntecipado() {
        return reembolsoAntecipado;
    }

    public void setReembolsoAntecipado(ReembolsoAntecipado reembolsoAntecipado) {
        this.reembolsoAntecipado = reembolsoAntecipado;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    public Date getDataDesembolso() {
        return dataDesembolso;
    }

    public void setDataDesembolso(Date dataDesembolso) {
        this.dataDesembolso = dataDesembolso;
    }

    public String getTextoContrato() {
        return textoContrato;
    }

    public void setTextoContrato(String textoContrato) {
        this.textoContrato = textoContrato;
    }

    public Date getDataCienciaErro() {
        return dataCienciaErro;
    }

    public void setDataCienciaErro(Date dataCienciaErro) {
        this.dataCienciaErro = dataCienciaErro;
    }

    public Usuario getUsuarioCienteErro() {
        return usuarioCienteErro;
    }

    public void setUsuarioCienteErro(Usuario usuarioCienteErro) {
        this.usuarioCienteErro = usuarioCienteErro;
    }

    public CobrancaXP getCobrancaXp() {
        return cobrancaXp;
    }

    public void setCobrancaXp(CobrancaXP cobrancaXp) {
        this.cobrancaXp = cobrancaXp;
    }

    public ReembolsoXP getReembolsoXp() {
        return reembolsoXp;
    }

    public void setReembolsoXp(ReembolsoXP reembolsoXp) {
        this.reembolsoXp = reembolsoXp;
    }

    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return this.reembolsoAntecipado.getPontosDeVenda();
    }

    /**
     * Atualiza o status da proposta de crédito
     * @param novoStatus o novo status da proposta
     * @param dataReferencia a data de alteração do status
     */
    public void atualizarStatus(StatusPropostaXP novoStatus, Date dataReferencia) {
        this.setStatus(novoStatus.getValue());
        this.setDataAtualizacao(dataReferencia);
    }
}
