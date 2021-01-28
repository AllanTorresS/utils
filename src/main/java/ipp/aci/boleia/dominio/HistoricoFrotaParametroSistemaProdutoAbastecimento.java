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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela Historica de Produtos dos Parametros do Sistema para a Frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "HIST_FROTA_PARAM_PROD_ABAS")
public class HistoricoFrotaParametroSistemaProdutoAbastecimento implements IPersistente {

    private static final long serialVersionUID = -893186232070025470L;

    @Id
    @Column(name = "CD_HIST_FROTA_PARAM_PROD_ABAS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FROTA_PARAM_PROD_ABAS")
    @SequenceGenerator(name = "SEQ_HIST_FROTA_PARAM_PROD_ABAS", sequenceName = "SEQ_HIST_FROTA_PARAM_PROD_ABAS", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS_PROD_ABAST")
    private FrotaParametroSistemaProdutoAbastecimento frotaParametroSistemaProdutoAbastecimento;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "DT_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;



    /**
     * Construtor
     */
    public HistoricoFrotaParametroSistemaProdutoAbastecimento() {
    }


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public FrotaParametroSistemaProdutoAbastecimento getFrotaParametroSistemaProdutoAbastecimento() {
        return frotaParametroSistemaProdutoAbastecimento;
    }

    public void setFrotaParametroSistemaProdutoAbastecimento(FrotaParametroSistemaProdutoAbastecimento frotaParametroSistemaProdutoAbastecimento) {
        this.frotaParametroSistemaProdutoAbastecimento = frotaParametroSistemaProdutoAbastecimento;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public Boolean getPermitido() {
        return permitido;
    }

    public void setPermitido(Boolean permitido) {
        this.permitido = permitido;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
}
