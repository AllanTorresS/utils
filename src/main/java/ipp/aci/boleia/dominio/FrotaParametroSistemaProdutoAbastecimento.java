package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Representa a tabela de Produtos dos Parametros do Sistema para a Frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "FROTA_PARAM_SIS_PROD_ABAST")
public class FrotaParametroSistemaProdutoAbastecimento implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -893186232070025470L;

    @Id
    @Column(name = "CD_FROTA_PARAM_SIS_PROD_ABAST")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FROTA_PARAM_SIS_PROD_ABAST")
    @SequenceGenerator(name = "SEQ_FROTA_PARAM_SIS_PROD_ABAST", sequenceName = "SEQ_FROTA_PARAM_SIS_PROD_ABAST", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS")
    private FrotaParametroSistema frotaParametroSistema;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_VEICULO")
    private Veiculo veiculo;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name="CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel tipoCombustivel;

    @NotNull
    @Column(name="ID_PERMITIDO")
    private Boolean permitido;

    @Column(name = "NO_VERSAO")
    @Version
    private Long versao;

    /**
     * Construtor
     */
    public FrotaParametroSistemaProdutoAbastecimento() {
    }

    /**
     * Construtor
     * @param frotaParametroSistema frotaParametroSistema para o veiculo
     * @param veiculo veiculo do parametro do sistema
     * @param tipoCombustivel combustível  do veiculo
     * @param permitido informa se o combustível  é permitido ou não
     */
    public FrotaParametroSistemaProdutoAbastecimento(FrotaParametroSistema frotaParametroSistema, Veiculo veiculo, TipoCombustivel tipoCombustivel, Boolean permitido) {
        this.frotaParametroSistema = frotaParametroSistema;
        this.veiculo = veiculo;
        this.tipoCombustivel = tipoCombustivel;
        this.permitido = permitido;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public FrotaParametroSistema getFrotaParametroSistema() {
        return frotaParametroSistema;
    }

    public void setFrotaParametroSistema(FrotaParametroSistema frotaParametroSistema) {
        this.frotaParametroSistema = frotaParametroSistema;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public List<Frota> getFrotas() {
        return frotaParametroSistema != null ? frotaParametroSistema.getFrotas() : null;
    }


    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Boolean getPermitido() {
        return permitido;
    }

    public void setPermitido(Boolean permitido) {
        this.permitido = permitido;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
}
