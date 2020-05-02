package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.TipoEntidadeUnidadeEmpresaAgregada;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela da Entidade Empresa/Unidade.
 */
@Entity
@Table(name = "V_EMPRESA_UNIDADE")
public class EmpresaUnidadeView implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -4794051548193908150L;

    @Id
    @Column(name = "CD_EMPRESA_UNIDADE")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE")
    private Unidade unidade;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_EMPR_AGREGADA")
    private EmpresaAgregada empresaAgregada;

    @Column(name = "NM_RAZAO_SOCIAL")
    private String razaoSocial;

    @Column(name = "CD_CNPJ")
    private Long cnpj;

    @Column(name = "ID_TIPO_ENTIDADE")
    private Integer tipoEntidade;

    @Column(name = "NM_MUNIC")
    private String municipio;

    @Column(name = "SG_UF")
    private String uf;

    @Max(99)
    @Column(name = "CD_DDD_TEL")
    private Integer dddTelefone;

    @Max(999999999L)
    @Column(name = "NO_TEL")
    private Long telefone;

    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name = "ID_EXIGE_NF")
    private Boolean exigeNotaFiscal;

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

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
    }

    public EmpresaAgregada getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EmpresaAgregada empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public Integer getTipoEntidade() {
        return tipoEntidade;
    }

    public void setTipoEntidade(Integer tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Integer getDddTelefone() {
        return dddTelefone;
    }

    public void setDddTelefone(Integer dddTelefone) {
        this.dddTelefone = dddTelefone;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Boolean getExigeNotaFiscal() {
        return exigeNotaFiscal;
    }

    public void setExigeNotaFiscal(Boolean exigeNotaFiscal) {
        this.exigeNotaFiscal = exigeNotaFiscal;
    }

    @Override
    @Transient
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    @Transient
    public TipoEntidadeUnidadeEmpresaAgregada getTipoEntidadeEnum() {
        return TipoEntidadeUnidadeEmpresaAgregada.obterPorValor(tipoEntidade);
    }
}
