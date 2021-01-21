package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.UnidadeMedida;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Representa a tabela de Produto
 */
@Entity
@Table(name = "PRODUTO")
public class Produto implements IPersistente, IExclusaoLogica {

    private static final long serialVersionUID = 5483211605656167805L;

    public static final String OUTROS = "Outros";

    @Id
    @Column(name = "CD_PRODUTO")
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "NM_PRODUTO")
    private String nome;

    @NotNull
    @Column(name = "CD_TIPO_PRODUTO")
    private Integer tipo;

    @NotNull
    @Column(name = "CD_UNIDADE_MEDIDA")
    private Integer unidadeMedida;

    @Column(name = "ID_UTILIZ_PDV")
    private Boolean disponivelPdv;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @OneToMany(mappedBy = "produto", fetch = FetchType.LAZY)
    private List<ItemAutorizacaoPagamento> itensAutorizacaoPagamento;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Integer getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(Integer unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Boolean getDisponivelPdv() { return disponivelPdv; }

    public void setDisponivelPdv(Boolean utilizadoPdv) { this.disponivelPdv = utilizadoPdv; }

    public List<ItemAutorizacaoPagamento> getItensAutorizacaoPagamento() {
        return itensAutorizacaoPagamento;
    }

    public void setItensAutorizacaoPagamento(List<ItemAutorizacaoPagamento> itensAutorizacaoPagamento) {
        this.itensAutorizacaoPagamento = itensAutorizacaoPagamento;
    }

    @Transient
    public boolean isDisponivelPdv() {
        return this.disponivelPdv != null && this.disponivelPdv;
    }

    @Transient
    public boolean isOutros() {
        return OUTROS.equalsIgnoreCase(this.nome);
    }

    @Transient
    public boolean isLitragem() {
        return UnidadeMedida.LITRO.getValue().equals(this.getUnidadeMedida());
    }

}
