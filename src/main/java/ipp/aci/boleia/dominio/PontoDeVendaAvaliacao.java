package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de Ponto de Venda Avaliacao
 */
@Entity
@Audited
@Table(name = "PONTO_VENDA_AVALIACAO")
public class PontoDeVendaAvaliacao implements IPersistente {

    private static final long serialVersionUID = -5932659633734250358L;

    private static final int NUMERO_MINIMO_NOTAS = 3;

    @Id
    @Column(name = "CD_PTOV_AV")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PTOV_AV")
    @SequenceGenerator(name = "SEQ_PTOV_AV", sequenceName = "SEQ_PTOV_AV", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PONTO_VENDA")
    private PontoDeVenda pontoDeVenda;

    @Column(name = "SOMATORIO_NOTAS")
    private Integer somatorioNotas;

    @Column(name = "NUMERO_NOTAS")
    private Integer numeroNotas;

    @Column(name = "DT_PRIMEIRA_AVALIACAO")
    private Date dataPrimeiraAvaliacao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getSomatorioNotas() {
        return somatorioNotas;
    }

    public void setSomatorioNotas(Integer somatorioNotas) {
        this.somatorioNotas = somatorioNotas;
    }

    public Integer getNumeroNotas() {
        return numeroNotas;
    }

    public void setNumeroNotas(Integer numeroNotas) {
        this.numeroNotas = numeroNotas;
    }

    public PontoDeVenda getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(PontoDeVenda pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    public Date getDataPrimeiraAvaliacao() {
        return dataPrimeiraAvaliacao;
    }

    public void setDataPrimeiraAvaliacao(Date dataPrimeiraAvaliacao) {
        this.dataPrimeiraAvaliacao = dataPrimeiraAvaliacao;
    }

    /**
     * Executa o cálculo da média das avaliações de um ponto de venda
     *
     * @return valor da média
     */

    @Transient
    public String getMediaAvaliacao() {
        if(numeroNotas != null && numeroNotas >= NUMERO_MINIMO_NOTAS) {
            Double media = (double) somatorioNotas / numeroNotas;
            if (media > Math.floor(media)){
                return new BigDecimal(media).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
            }
            return new BigDecimal(media).setScale(1).toString();
        }
        return null;
    }
}
