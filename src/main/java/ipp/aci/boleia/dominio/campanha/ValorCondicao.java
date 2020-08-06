package ipp.aci.boleia.dominio.campanha;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
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

/**
 * Representa a tabela de Valores de uma Condição de uma Campanha
 */
@Audited
@Entity
@Table(name = "VALOR_CONDICAO", schema = "BOLEIA_CAMPANHA_SCHEMA")
public class ValorCondicao implements IPersistente, Cloneable {

    private static final long serialVersionUID = -2314063083945732443L;

    @Id
    @Column(name = "CD_VALOR_CONDICAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VALOR_CONDICAO")
    @SequenceGenerator(name = "SEQ_VALOR_CONDICAO", sequenceName = "BOLEIA_CAMPANHA_SCHEMA.SEQ_VALOR_CONDICAO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CONDICAO")
    private CondicaoCampanha condicao;

    @Column(name = "VR_CONDICAO")
    private String valor;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public CondicaoCampanha getCondicao() {
        return condicao;
    }

    public void setCondicao(CondicaoCampanha condicao) {
        this.condicao = condicao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    /**
     * Copia um valor de condição de campanha
     *
     * @return Uma cópia do valor
     */
    public ValorCondicao clone() {
        ValorCondicao valorCopia = new ValorCondicao();
        valorCopia.setValor(this.getValor());
        return valorCopia;
    }
}
