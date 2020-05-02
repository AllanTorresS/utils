package ipp.aci.boleia.dominio;


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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela de Componente
 */
@Entity
@Audited
@Table(name = "COMPONENTE")
public class Componente implements IPersistente, IPertenceRevendedor {

    public static final String TIPO_PESSOA_PF = "PF";
    public static final String TIPO_PESSOA_PJ = "PJ";
    private static final long serialVersionUID = 2897667623853191031L;

    @Id
    @Column(name = "CD_COMP")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COMPONENTE")
    @SequenceGenerator(name = "SEQ_COMPONENTE", sequenceName = "SEQ_COMPONENTE", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoDeVenda;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_GRUPO_ECON")
    private GrupoEconomico grupoEconomico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_BAND")
    private Bandeira bandeira;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_ATIV_COMP")
    private AtividadeComponente atividadeComponente;

    @Size(max=20)
    @Column(name = "NO_TEL")
    private String telefone;

    @Max(99999999L)
    @Column(name = "CD_COMP_CORP")
    private Long codigoCorporativo;

    @NotNull
    @Max(9L)
    @Column(name = "ID_STATUS_CORP")
    private Integer status;

    @Max(99999999)
    @Column(name = "CD_COMP_JDE")
    private Integer numeroJde;

    @Max(99)
    @Column(name = "CD_COMP_ABADI")
    private Integer numeroAbadi;

    @NotNull
    @Size(max=2)
    @Column(name = "TP_PESSOA")
    private String tipoPessoa;

    @NotNull
    @Digits(integer=22, fraction = 0)
    @Column(name = "CD_PESSOA")
    private Long codigoPessoa;

    @NotNull
    @Size(max=40)
    @Column(name = "NM_PESSOA")
    private String nomePessoa;

    @Size(max=10)
    @Column(name = "NM_APELIDO")
    private String apelido;

    @Size(max=20)
    @Column(name = "CD_INSCRE")
    private String inscricaoEstadual;

    @Size(max=20)
    @Column(name = "CD_INSCRM")
    private String inscricaoMunicipal;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public PontoDeVenda getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(PontoDeVenda pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    public GrupoEconomico getGrupoEconomico() {
        return grupoEconomico;
    }

    public void setGrupoEconomico(GrupoEconomico grupoEconomico) {
        this.grupoEconomico = grupoEconomico;
    }

    public Bandeira getBandeira() {
        return bandeira;
    }

    public void setBandeira(Bandeira bandeira) {
        this.bandeira = bandeira;
    }

    public AtividadeComponente getAtividadeComponente() {
        return atividadeComponente;
    }

    public void setAtividadeComponente(AtividadeComponente atividadeComponente) {
        this.atividadeComponente = atividadeComponente;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Long getCodigoCorporativo() {
        return codigoCorporativo;
    }

    public void setCodigoCorporativo(Long codigoCorporativo) {
        this.codigoCorporativo = codigoCorporativo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getNumeroJde() {
        return numeroJde;
    }

    public void setNumeroJde(Integer numeroJde) {
        this.numeroJde = numeroJde;
    }

    public Integer getNumeroAbadi() {
        return numeroAbadi;
    }

    public void setNumeroAbadi(Integer numeroAbadi) {
        this.numeroAbadi = numeroAbadi;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public Long getCodigoPessoa() {
        return codigoPessoa;
    }

    public void setCodigoPessoa(Long codigoPessoa) {
        this.codigoPessoa = codigoPessoa;
    }

    public String getNomePessoa() {
        return nomePessoa;
    }

    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getInscricaoEstadual() {
        return inscricaoEstadual;
    }

    public void setInscricaoEstadual(String inscricaoEstadual) {
        this.inscricaoEstadual = inscricaoEstadual;
    }

    public String getInscricaoMunicipal() {
        return inscricaoMunicipal;
    }

    public void setInscricaoMunicipal(String inscricaoMunicipal) {
        this.inscricaoMunicipal = inscricaoMunicipal;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return Collections.singletonList(pontoDeVenda);
    }
}
