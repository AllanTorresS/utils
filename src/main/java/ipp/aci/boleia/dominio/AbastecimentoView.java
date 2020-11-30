package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

/**
 * Representa o abastecimento de forma generica, agrupando as Empresas Abastecedoras
 */
@Entity
@Table(name = "V_ABASTECIMENTO")
public class AbastecimentoView implements IPersistente {

    @Id
    @Column(name = "CD_ABAST_ABASTECEDORA")
    private Long id;

    @Column(name = "CD_ABASTECIMENTO")
    private Long abastecimentoId;

    @Column(name = "DT_PROCESSAMENTO")
    private Date dataProcessamento;

    @Column(name = "CD_TIPO_EMPRESA")
    private Integer tipoEmpresa;

    @Column(name = "CD_EMPRESA")
    private Long idEmpresa;

    @Column(name = "NM_EMPRESA")
    private String nomeEmpresa;

    @Column(name = "CD_CNPJ_EMPRESA")
    private String cnpjEmpresa;

    @Column(name = "NM_MOTORISTA")
    private String nomeMotorista;

    @Column(name = "CD_MATRICULA_MOTORISTA")
    private String matriculaMotorista;

    @Column(name = "CD_CPF_MOTORISTA")
    private String cpfMotorista;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda posto;

    @Column(name = "DS_PLACA")
    private String placaVeiculo;

    @Column(name = "NO_HODOMETRO")
    private Long hodometro;

    @Column(name = "NO_HORIMETRO")
    private BigDecimal horimetro;

    @Column(name = "VA_LITRAGEM")
    private BigDecimal litragem;

    @Column(name = "NM_TIPO_COMBUSTIVEL")
    private String nomeCombustivel;

    @Column(name = "VA_COMBUSTIVEL")
    private BigDecimal valorUnitario;

    @Column(name = "VA_TOTAL")
    private BigDecimal valorTotal;

    public Date getDataProcessamento() {
        return dataProcessamento;
    }

    public void setDataProcessamento(Date dataProcessamento) {
        this.dataProcessamento = dataProcessamento;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public String getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(String cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }

    public PontoDeVenda getPosto() {
        return posto;
    }

    public void setPosto(PontoDeVenda posto) {
        this.posto = posto;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public BigDecimal getLitragem() {
        return litragem;
    }

    public void setLitragem(BigDecimal litragem) {
        this.litragem = litragem;
    }

    public String getNomeCombustivel() {
        return nomeCombustivel;
    }

    public void setNomeCombustivel(String nomeCombustivel) {
        this.nomeCombustivel = nomeCombustivel;
    }

    public Integer getTipoEmpresa() {
        return tipoEmpresa;
    }

    public void setTipoEmpresa(Integer tipoEmpresa) {
        this.tipoEmpresa = tipoEmpresa;
    }

    public Long getAbastecimentoId() {
        return abastecimentoId;
    }

    public void setAbastecimentoId(Long abastecimentoId) {
        this.abastecimentoId = abastecimentoId;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Long idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public String getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(String cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Long getHodometro() {
        return hodometro;
    }

    public void setHodometro(Long hodometro) {
        this.hodometro = hodometro;
    }

    public BigDecimal getHorimetro() {
        return horimetro;
    }

    public void setHorimetro(BigDecimal horimetro) {
        this.horimetro = horimetro;
    }

    public String getMatriculaMotorista() {
        return matriculaMotorista;
    }

    public void setMatriculaMotorista(String matriculaMotorista) {
        this.matriculaMotorista = matriculaMotorista;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(BigDecimal valorUnitario) {
        this.valorUnitario = valorUnitario;
    }
}
