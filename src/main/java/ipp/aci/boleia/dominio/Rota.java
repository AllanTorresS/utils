package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela de Rota
 */
@Entity
@Audited
@Table(name = "ROTA")
public class Rota implements IPersistente, IExclusaoLogica, IPertenceFrota {

    private static final long serialVersionUID = -2992575318587803234L;

    private static final String FORMULA_DURACAO_EM_MINUTOS = " ( " +
            "TO_NUMBER( " +
            "    LTRIM(RTRIM( " +
            "        SUBSTR(   TEMPO_TOTAL, " +
            "                        0, " +
            "                  INSTR(TEMPO_TOTAL, 'hora', 1, 1) - 1 " +
            "        ) " +
            "    )) " +
            ") * 60 + " +
            "TO_NUMBER( " +
            "    LTRIM(RTRIM( " +
            "        SUBSTR(   TEMPO_TOTAL, " +
            "                  INSTR(TEMPO_TOTAL, 'hora', 1, 1) + 5, " +
            "                  INSTR(TEMPO_TOTAL, 'minuto', 1, 1) - instr(TEMPO_TOTAL, 'hora', 1, 1) - 5" +
            "        ) " +
            "    )) " +
            ") " +
    ") ";

    @Id
    @Column(name = "CD_ROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ROTA")
    @SequenceGenerator(name = "SEQ_ROTA", sequenceName = "SEQ_ROTA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @Size(max=250)
    @Column(name = "NM_ROTA")
    private String nome;

    @NotNull
    @DecimalMin("000000.000")
    @DecimalMax("999999.999")
    @Column(name = "KM_DISTANCIA")
    private BigDecimal distancia;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @OneToMany(mappedBy = "rota", fetch = FetchType.LAZY)
    private List<PontoRota> pontos;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @NotNull
    @Column(name = "ID_TIPO")
    private Integer tipo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PLANO")
    private PlanoViagem planoViagem;

    @Size(max=100)
    @Column(name = "TEMPO_TOTAL")
    private String tempo;

    @Column(name = "ID_PRINCIPAL")
    private Boolean principal;

    @NotAudited
    @Formula(FORMULA_DURACAO_EM_MINUTOS)
    private BigDecimal duracaoEmMinutos;

    @Transient
    private Long quantidadePostos;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    public BigDecimal getDistancia() {
        return distancia;
    }

    public void setDistancia(BigDecimal distancia) {
        this.distancia = distancia;
    }

    public List<PontoRota> getPontos() {
        return pontos;
    }

    public void setPontos(List<PontoRota> pontos) {
        this.pontos = pontos;
    }

    public Integer getTipo() { return tipo; }

    public void setTipo(Integer tipo) { this.tipo = tipo; }

    public PlanoViagem getPlanoViagem() { return planoViagem; }

    public void setPlanoViagem(PlanoViagem planoViagem) { this.planoViagem = planoViagem; }

    public String getTempo() { return tempo; }

    public void setTempo(String tempo) { this.tempo = tempo; }

    public Boolean getPrincipal() { return principal; }

    public void setPrincipal(Boolean principal) { this.principal = principal; }

    public BigDecimal getDuracaoEmMinutos() { return duracaoEmMinutos; }

    public void setDuracaoEmMinutos(BigDecimal duracaoEmMinutos) { this.duracaoEmMinutos = duracaoEmMinutos; }

    @Transient
    public Long getQuantidadePostos() {
        return quantidadePostos;
    }

    @Transient
    public void setQuantidadePostos(Long quantidadePostos) {
        this.quantidadePostos = quantidadePostos;
    }

}
