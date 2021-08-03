package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.StatusAntecipacao;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoAntecipacaoJde;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;
import ipp.aci.boleia.dominio.enums.StatusIntegracaoReembolsoJde;
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
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

import static ipp.aci.boleia.dominio.enums.StatusIntegracaoJde.ERRO_ENVIO;
import static ipp.aci.boleia.dominio.enums.StatusIntegracaoJde.REALIZADO;
import static ipp.aci.boleia.util.UtilitarioCalculoData.adicionarSegundosData;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterPrimeiroInstanteDia;
import static ipp.aci.boleia.util.UtilitarioCalculoData.obterUltimoInstanteDia;

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

    @NotNull
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_HIST_CONFIG_ANTECIPACAO")
    private HistoricoConfiguracaoAntecipacao configuracao;

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

    public HistoricoConfiguracaoAntecipacao getConfiguracao() {
        return configuracao;
    }

    public void setConfiguracao(HistoricoConfiguracaoAntecipacao configuracao) {
        this.configuracao = configuracao;
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

    /**
     * Obtém o status da antecipação em uma data específica
     * @param dataReferencia a data a ser usada como referência
     * @return o status da antecipação
     */
    @Transient
    public StatusAntecipacao getStatusAntecipacao(Date dataReferencia) {
        if(isAceito == null) {
            if(dataReferencia.after(obterUltimoInstanteDia(reembolsoAntecipado.getDataVencimentoPgto()))) {
                return StatusAntecipacao.CANCELADO_SEM_RESPOSTA;
            } else {
                return StatusAntecipacao.AGUARDANDO_ACEITE;
            }
        } else if(!isAceito) {
            return StatusAntecipacao.CANCELADO_CLIENTE;
        } else if(StatusIntegracaoReembolsoJde.ANTECIPADO.getValue().equals(reembolsoAntecipado.getStatusIntegracao())) {
            return StatusAntecipacao.ANTECIPADO;
        } else if (getStatusIntegracao() == StatusIntegracaoAntecipacaoJde.REALIZADO) {
            return StatusAntecipacao.EM_ANDAMENTO;
        } else {
            return StatusAntecipacao.PENDENTE;
        }
    }

    /**
     * Verifica se a proposta está disponível para aceite na data informada
     * @param dataReferencia a data usada como referência
     * @return true se a proposta pode receber aceite, false caso contrário
     */
    @Transient
    public boolean isAceiteDisponivel(Date dataReferencia) {
        Date dataLimite = adicionarSegundosData(obterPrimeiroInstanteDia(dataCriacao), configuracao.getHorarioLimiteVigente(dataCriacao));
        return isAceito == null && dataReferencia.before(dataLimite);
    }

    /**
     * Obtém o status da integração dos documentos de antecipação no JDE
     * @return o status da integração
     */
    @Transient
    public StatusIntegracaoAntecipacaoJde getStatusIntegracao() {
        StatusIntegracaoJde statusFaturaXp = cobrancaXp != null ? StatusIntegracaoJde.obterPorValor(cobrancaXp.getStatusIntegracaoJDE()) : null;
        StatusIntegracaoJde statusVoucherXp = reembolsoXp != null ? StatusIntegracaoJde.obterPorValor(reembolsoXp.getStatusIntegracao()) : null;
        StatusIntegracaoReembolsoJde statusVoucherPv = StatusIntegracaoReembolsoJde.obterPorValor(reembolsoAntecipado.getStatusIntegracao());

        if (statusVoucherPv == null && statusFaturaXp == null && statusVoucherXp == null) {
            return StatusIntegracaoAntecipacaoJde.PREVISTO;
        } else if (statusVoucherPv == StatusIntegracaoReembolsoJde.REALIZADO && statusFaturaXp == REALIZADO && statusVoucherXp == REALIZADO) {
            return StatusIntegracaoAntecipacaoJde.REALIZADO;
        } else if (statusFaturaXp == ERRO_ENVIO && (statusVoucherPv == StatusIntegracaoReembolsoJde.ERRO_ENVIO || statusVoucherXp == ERRO_ENVIO)) {
            return StatusIntegracaoAntecipacaoJde.ERRO_ENVIO_F7_PV;
        } else if (statusFaturaXp == ERRO_ENVIO) {
            return StatusIntegracaoAntecipacaoJde.ERRO_ENVIO_F7;
        } else {
            return StatusIntegracaoAntecipacaoJde.ERRO_ENVIO_PV;
        }
    }
}
