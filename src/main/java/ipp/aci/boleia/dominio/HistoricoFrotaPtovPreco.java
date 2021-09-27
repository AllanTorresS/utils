package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de histórico de preços negociados entre Frotas e PVs
 */
@Entity
@Table(name="HIST_FROTA_PTOV_PRECO")
public class HistoricoFrotaPtovPreco  implements IPersistente, IPertenceRevendedor, IPertenceFrota {

    private static final long serialVersionUID = -5362432477898350598L;

    @Id
    @Column(name = "CD_HIST_FROTA_PTOV_PRECO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FROTA_PTOV_PRECO")
    @SequenceGenerator(name = "SEQ_HIST_FROTA_PTOV_PRECO", sequenceName = "SEQ_HIST_FROTA_PTOV_PRECO", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PTOV_PRECO")
    private Preco precoNegociado;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PTOV")
    private FrotaPontoVenda frotaPtov;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel tipoCombustivel;

    @NotNull
    @Column(name = "VA_PRECO")
    private BigDecimal preco;

    @Column(name = "VA_DESCONTO_VIGENTE")
    private BigDecimal descontoVigente;

    @Column(name = "VA_DESCONTO_SOLICITADO")
    private BigDecimal descontoSolicitado;

    @Column(name = "DT_VIGENCIA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVigencia;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Preco getPrecoNegociado() {
        return precoNegociado;
    }

    public void setPrecoNegociado(Preco precoNegociado) {
        this.precoNegociado = precoNegociado;
    }

    public FrotaPontoVenda getFrotaPtov() {
        return frotaPtov;
    }

    public void setFrotaPtov(FrotaPontoVenda frotaPtov) {
        this.frotaPtov = frotaPtov;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getDescontoVigente() {
        return descontoVigente;
    }

    public void setDescontoVigente(BigDecimal descontoVigente) {
        this.descontoVigente = descontoVigente;
    }

    public BigDecimal getDescontoSolicitado() {
        return descontoSolicitado;
    }

    public void setDescontoSolicitado(BigDecimal descontoSolicitado) {
        this.descontoSolicitado = descontoSolicitado;
    }

    public Date getDataVigencia() {
        return dataVigencia;
    }

    public void setDataVigencia(Date dataVigencia) {
        this.dataVigencia = dataVigencia;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frotaPtov.getFrota());
    }

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return Collections.singletonList(frotaPtov.getPontoVenda());
    }


}
