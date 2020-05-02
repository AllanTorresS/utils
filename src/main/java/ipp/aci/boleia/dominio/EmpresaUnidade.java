package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.TipoEntidadeUnidadeEmpresaAgregada;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela da Entidade Empresa/Unidade.
 */
@Audited
@Entity
@Table(name = "ENTIDADE_EMPRESA_UNIDADE")
public class EmpresaUnidade implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -4794051548193908150L;

    @Id
    @Column(name = "CD_ENTIDADE_EMPRESA_UNIDADE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ENTIDADE_EMPRESA_UNIDADE")
    @SequenceGenerator(name = "SEQ_ENTIDADE_EMPRESA_UNIDADE", sequenceName = "SEQ_ENTIDADE_EMPRESA_UNIDADE", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @Column(name = "ID_TIPO_ENTIDADE")
    private Integer tipoEntidade;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_EMPR_AGREGADA")
    private EmpresaAgregada empresaAgregada;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_UNIDADE")
    private Unidade unidade;

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

    public Integer getTipoEntidade() {
        return tipoEntidade;
    }

    public void setTipoEntidade(Integer tipoEntidade) {
        this.tipoEntidade = tipoEntidade;
    }

    public EmpresaAgregada getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EmpresaAgregada empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public Unidade getUnidade() {
        return unidade;
    }

    public void setUnidade(Unidade unidade) {
        this.unidade = unidade;
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
