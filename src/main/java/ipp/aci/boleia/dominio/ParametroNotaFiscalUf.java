package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
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
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.envers.Audited;

/**
 * Representa a tabela de Parametrizacao de Nota Fiscal UF
 * Guarda os parametros especificos para uma UF 
 */
@Audited
@Entity
@Table(name = "PARAMETRO_NF_UF", uniqueConstraints={@UniqueConstraint(columnNames = {"CD_PARAMETRO_NF" , "SG_UF"})})
public class ParametroNotaFiscalUf implements IPersistente {
    
    @Id
    @Column(name = "CD_PARAMETRO_NF_UF")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PARAM_NF_UF")
    @SequenceGenerator(name = "SEQ_PARAM_NF_UF", sequenceName = "SEQ_PARAM_NF_UF", allocationSize = 1)
    private Long id;
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PARAMETRO_NF")
    private ParametroNotaFiscal parametroNotaFiscal;
    
    @NotNull
    @Size(max=2)
    @Column(name = "SG_UF")
    private String uf;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE_LOCAL_DEST")
    private Unidade unidadeLocalDestino;

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

    public ParametroNotaFiscal getParametroNotaFiscal() {
        return parametroNotaFiscal;
    }

    public void setParametroNotaFiscal(ParametroNotaFiscal parametroNotaFiscal) {
        this.parametroNotaFiscal = parametroNotaFiscal;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public Unidade getUnidadeLocalDestino() {
        return unidadeLocalDestino;
    }

    public void setUnidadeLocalDestino(Unidade unidadeLocalDestino) {
        this.unidadeLocalDestino = unidadeLocalDestino;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
