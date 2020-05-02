package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;

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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Abastecimento Veiculo Mes
 */
@Entity
@Table(name = "ABST_VEICULO_MES")
public class AbastecimentoVeiculoMes implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -8204204972230061023L;

    @Id
    @Column(name = "CD_ABST_VEICULO_MES")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ABST_VEICULO_MES")
    @SequenceGenerator(name = "SEQ_ABST_VEICULO_MES", sequenceName = "SEQ_ABST_VEICULO_MES", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "DT_MES_ANO")
    @Temporal(TemporalType.DATE)
    private Date mesAno;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

    @Column(name = "QT_TOTAL_LIT_ABAS")
    private BigDecimal totalLitrosAbastecimento;

    @Column(name = "QT_VALOR_TOTAL")
    private BigDecimal valorTotal;

    @Column(name = "NO_TOTAL_HODOMETRO")
    private Long totalHodometro;

    @Column(name = "NO_TOTAL_HORIMETRO")
    private BigDecimal totalHorimetro;

    @NotNull
    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getMesAno() {
        return mesAno;
    }

    public void setMesAno(Date mesAno) {
        this.mesAno = mesAno;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public BigDecimal getTotalLitrosAbastecimento() {
        return totalLitrosAbastecimento;
    }

    public void setTotalLitrosAbastecimento(BigDecimal totalLitrosAbastecimento) {
        this.totalLitrosAbastecimento = totalLitrosAbastecimento;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getTotalHodometro() {
        return totalHodometro;
    }

    public void setTotalHodometro(Long totalHodometro) {
        this.totalHodometro = totalHodometro;
    }

    public BigDecimal getTotalHorimetro() {
        return totalHorimetro;
    }

    public void setTotalHorimetro(BigDecimal totalHorimetro) {
        this.totalHorimetro = totalHorimetro;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }

    @Override
    public List<Frota> getFrotas() {
        return veiculo.getFrotas();
    }
}
