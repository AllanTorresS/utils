package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Representa uma nota de compra enviada na notificação push do app motorista.
 *
 * @author pedro.silva
 */
public class NotaMotoristaVo {
    private Long id;
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ssZ")
    private Date data;
    private Long postoId;
    private String postoNome;
    private String postoEndereco;
    private ItemNotaMotoristaVo abastecimento;
    private List<ItemNotaMotoristaVo> prodServ;
    private BigDecimal total;
    private String placa;
    private BigDecimal kilometragem;
    private String kilometragemTipo;
    private Boolean avaliacaoPendente;
    private Integer pontosAcumuladosKmv;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Long getPostoId() {
        return postoId;
    }

    public void setPostoId(Long postoId) {
        this.postoId = postoId;
    }

    public String getPostoNome() {
        return postoNome;
    }

    public void setPostoNome(String postoNome) {
        this.postoNome = postoNome;
    }

    public String getPostoEndereco() {
        return postoEndereco;
    }

    public void setPostoEndereco(String postoEndereco) {
        this.postoEndereco = postoEndereco;
    }

    public ItemNotaMotoristaVo getAbastecimento() {
        return abastecimento;
    }

    public void setAbastecimento(ItemNotaMotoristaVo abastecimento) {
        this.abastecimento = abastecimento;
    }

    public List<ItemNotaMotoristaVo> getProdServ() {
        return prodServ;
    }

    public void setProdServ(List<ItemNotaMotoristaVo> prodServ) {
        this.prodServ = prodServ;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public BigDecimal getKilometragem() {
        return kilometragem;
    }

    public void setKilometragem(BigDecimal kilometragem) {
        this.kilometragem = kilometragem;
    }

    public String getKilometragemTipo() {
        return kilometragemTipo;
    }

    public void setKilometragemTipo(String kilometragemTipo) {
        this.kilometragemTipo = kilometragemTipo;
    }

    public Boolean getAvaliacaoPendente() {
        return avaliacaoPendente;
    }

    public void setAvaliacaoPendente(Boolean avaliacaoPendente) {
        this.avaliacaoPendente = avaliacaoPendente;
    }

    public Integer getPontosAcumuladosKmv() {
        return pontosAcumuladosKmv;
    }

    public void setPontosAcumuladosKmv(Integer pontosAcumuladosKmv) {
        this.pontosAcumuladosKmv = pontosAcumuladosKmv;
    }
}
