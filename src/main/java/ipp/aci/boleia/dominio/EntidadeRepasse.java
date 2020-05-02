package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
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
import java.math.BigDecimal;

/**
 * Representa a entidade que realiza repasses para o Pró-frotas
 */
@Audited
@Entity
@Table(name="ENTIDADE_REPASSE")
public class EntidadeRepasse implements IPersistente, IExclusaoLogica {

    private static final long serialVersionUID = 1347873257169824805L;

    @Id
    @Column(name = "CD_ENTIDADE_REPASSE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENTIDADE_REPASSE")
    @SequenceGenerator(name = "SEQ_ENTIDADE_REPASSE", sequenceName = "SEQ_ENTIDADE_REPASSE", allocationSize = 1)
    private Long id;

    @Column(name = "DS_NOME")
    private String nome;

    @Column(name="CD_CNPJ")
    private Long cnpj;

    @Column(name="ID_STATUS")
    private Integer status;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Column(name = "VA_PERCENTUAL_REPASSE")
    private BigDecimal valorPercentualRepasse;

    /**
     * Configuração de repasse corrente da entidade
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CD_CONFIGURACAO_REPASSE")
    private ConfiguracaoRepasse configuracaoRepasse;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome(){
        return nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public BigDecimal getValorPercentualRepasse() {
        return valorPercentualRepasse;
    }

    public void setValorPercentualRepasse(BigDecimal valorPercentualRepasse) {
        this.valorPercentualRepasse = valorPercentualRepasse;
    }

    public ConfiguracaoRepasse getConfiguracaoRepasse() {
        return configuracaoRepasse;
    }

    public void setConfiguracaoRepasse(ConfiguracaoRepasse configuracaoRepasse) {
        this.configuracaoRepasse = configuracaoRepasse;
    }
}
