package ipp.aci.boleia.dominio.agenciadorfrete;

import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.TipoCombustivel;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.List;

@Entity
@Audited
@Table(name = "AGENCIADOR_FRETE")
public class AgenciadorFrete implements IPersistente {
    @Id
    @Column(name = "CD_AGENCIADOR_FRETE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AGENCIADOR_FRETE")
    @SequenceGenerator(name = "SEQ_AGENCIADOR_FRETE", sequenceName = "SEQ_AGENCIADOR_FRETE", allocationSize = 1)
    private Long id;

    @Column(name="CD_CNPJ")
    private Long cnpj;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_SISTEMA")
    private SistemaExterno sistemaExterno;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="AG_FRETE_COMBUSTIVEL", joinColumns={@JoinColumn(name="CD_AGENCIADOR_FRETE")}, inverseJoinColumns={@JoinColumn(name="CD_TIPO_COMBUSTIVEL")})
    private List<TipoCombustivel> tiposCombustivel;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public SistemaExterno getSistemaExterno() {
        return sistemaExterno;
    }

    public void setSistemaExterno(SistemaExterno sistemaExterno) {
        this.sistemaExterno = sistemaExterno;
    }

    public List<TipoCombustivel> getTiposCombustivel() {
        return tiposCombustivel;
    }

    public void setTiposCombustivel(List<TipoCombustivel> tiposCombustivel) {
        this.tiposCombustivel = tiposCombustivel;
    }
}
