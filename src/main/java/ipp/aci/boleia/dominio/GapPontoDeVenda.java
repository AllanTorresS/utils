package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;

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
import javax.persistence.Transient;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

/**
 * Representa a tabela de Gap Ponto de Venda
 */
@Entity
@Audited
@Table(name = "GAP_PONTO_VENDA")
public class GapPontoDeVenda implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -1449514130296994292L;

    @Id
    @Column(name = "CD_GAP_PONTO_VENDA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GAP_PONTO_VENDA")
    @SequenceGenerator(name = "SEQ_GAP_PONTO_VENDA", sequenceName = "SEQ_GAP_PONTO_VENDA", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "NM_PTOV")
    private String nome;

    @Digits(integer=22, fraction = 0)
    @Column(name = "CD_CNPJ")
    private Long cnpj;

    @Size(max = 250)
    @Column(name = "NM_ORIGEM")
    private String origem;

    @Size(max = 250)
    @Column(name = "NM_DESTINO")
    private String destino;

    @NotNull
    @Column(name = "VA_VOLUME")
    private BigDecimal volumeMedio;

    @NotNull
    @Column(name = "VA_PRECO")
    private BigDecimal preco;

    @NotNull
    @Digits(integer=3, fraction = 0)
    @Column(name = "VA_PRAZO")
    private Integer prazoPagamento;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public BigDecimal getVolumeMedio() {
        return volumeMedio;
    }

    public void setVolumeMedio(BigDecimal volumeMedio) {
        this.volumeMedio = volumeMedio;
    }

    public Integer getPrazoPagamento() {
        return prazoPagamento;
    }

    public void setPrazoPagamento(Integer prazoPagamento) {
        this.prazoPagamento = prazoPagamento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return usuario!=null ? usuario.getFrotas() : null;
    }
}
