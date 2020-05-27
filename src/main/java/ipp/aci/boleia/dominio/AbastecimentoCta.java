package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusImportacaoCta;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;


/**
 * Representa a tabela de abastecimentos obtidos do Ipiranga Connect para possível importação
 */
@Entity
@Audited
@Table(name = "ABASTECIMENTO_CTA")
public class AbastecimentoCta implements IPersistente {

    private static final long serialVersionUID = 7391597436983162339L;

    @Id
    @Column(name = "CD_ABASTECIMENTO_CTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ABASTECIMENTO_CTA")
    @SequenceGenerator(name = "SEQ_ABASTECIMENTO_CTA", sequenceName = "SEQ_ABASTECIMENTO_CTA", allocationSize = 1)
    private Long id;

    @Column(name = "ID_STATUS_IMPORT")
    private Integer statusImportacao = StatusImportacaoCta.NAO_IMPORTADO.getValue();

    @Column(name = "DS_ERRO_IMPORT")
    private String erroImportacao;

    @Column(name = "DS_ERRO_SINC")
    private String erroSincronismo;

    @Column(name = "DT_PROCESSAMENTO")
    private Date dataProcessamento;

    @Column(name = "DT_IMPORT")
    private Date dataImportacao;

    @Column(name = "CD_CTA")
    private Long idCTA;

    @Lob
    @Column(name = "DS_JSON_IMPORT")
    private String abastecimentoJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AUTORIZACAO_PAGAMENTO")
    private AutorizacaoPagamento autorizacaoPagamentoImportada;

    public AutorizacaoPagamento getAutorizacaoPagamentoImportada() {
        return autorizacaoPagamentoImportada;
    }

    public void setAutorizacaoPagamentoImportada(AutorizacaoPagamento autorizacaoPagamentoImportada) {
        this.autorizacaoPagamentoImportada = autorizacaoPagamentoImportada;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getErroImportacao() {
        return erroImportacao;
    }

    public void setErroImportacao(String erroImportacao) {
        this.erroImportacao = erroImportacao;
    }

    public String getErroSincronismo() {
        return erroSincronismo;
    }

    public void setErroSincronismo(String erroSincronismo) {
        this.erroSincronismo = erroSincronismo;
    }

    public Long getIdCTA() {
        return idCTA;
    }

    public void setIdCTA(Long idCTA) {
        this.idCTA = idCTA;
    }

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public Date getDataImportacao() {
        return dataImportacao;
    }

    public void setDataImportacao(Date dataImportacao) {
        this.dataImportacao = dataImportacao;
    }

    public void setAbastecimentoJson(String abastecimentoJson) {
        this.abastecimentoJson = abastecimentoJson;
    }

    public String getAbastecimentoJson() {
        return abastecimentoJson;
    }

    public Integer getStatusImportacao() {
        return statusImportacao;
    }

    public void setStatusImportacao(Integer statusImportacao) {
        this.statusImportacao = statusImportacao;
    }

    /**
     * Informa se o status de importação foi de erro
     *
     * @return true se o status for de erro
     */
    public boolean impotadoComErro() {
        return StatusImportacaoCta.ERRO.getValue().equals(this.statusImportacao);
    }

    /**
     * Remove erro de sincronização
     */
    public void limparErroSincronizacao() {
        this.erroSincronismo = null;
    }

    /**
     * Define Status como impotado e remove o erro de importação
     */
    public void defineComoImportado() {
        this.setStatusImportacao(StatusImportacaoCta.IMPORTADO.getValue());
        this.setErroImportacao(null);
    }
}
