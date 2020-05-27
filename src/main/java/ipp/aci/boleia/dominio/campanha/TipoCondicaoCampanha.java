package ipp.aci.boleia.dominio.campanha;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Representa a tabela de Tipos de Condições de uma Campanha
 */
@Entity
@Table(name = "TIPO_CONDICAO_CAMPANHA", schema = "BOLEIA_CAMPANHA_SCHEMA")
public class TipoCondicaoCampanha implements IPersistente {

    private static final long serialVersionUID = 5683897906987027021L;

    @Id
    @Column(name = "CD_TIPO_CONDICAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_CONDICAO")
    @SequenceGenerator(name = "SEQ_TIPO_CONDICAO", sequenceName = "BOLEIA_CAMPANHA_SCHEMA.SEQ_TIPO_CONDICAO", allocationSize = 1)
    private Long id;

    @Column(name = "NM_TIPO_CONDICAO")
    private String nome;

    @Column(name = "ID_TIPO_CAMPO")
    private Integer tipoCampo;

    @Column(name = "NM_ENTIDADE")
    private String nomeEntidade;

    @Column(name = "NM_LABEL")
    private String label;

    @Column(name = "NM_VALOR")
    private String nomeValor;

    @Column(name = "ID_ENUM")
    private Boolean isEnum;

    @Column(name = "DS_CAMINHO_ABASTECIMENTO")
    private String caminhoCampoABastecimento;

    @Column(name = "ID_DESCRICAO_OPERACAO")
    private Integer descricaoOperacao;

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

    public Integer getTipoCampo() {
        return tipoCampo;
    }

    public void setTipoCampo(Integer tipoCampo) {
        this.tipoCampo = tipoCampo;
    }

    public String getNomeEntidade() {
        return nomeEntidade;
    }

    public void setNomeEntidade(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getNomeValor() {
        return nomeValor;
    }

    public void setNomeValor(String nomeValor) {
        this.nomeValor = nomeValor;
    }

    public Boolean getEnum() {
        return isEnum;
    }

    public void setEnum(Boolean anEnum) {
        isEnum = anEnum;
    }

    public String getCaminhoCampoABastecimento() {
        return caminhoCampoABastecimento;
    }

    public void setCaminhoCampoABastecimento(String caminhoCampoABastecimento) {
        this.caminhoCampoABastecimento = caminhoCampoABastecimento;
    }

    public Integer getDescricaoOperacao() {
        return descricaoOperacao;
    }

    public void setDescricaoOperacao(Integer descricaoOperacao) {
        this.descricaoOperacao = descricaoOperacao;
    }
}
