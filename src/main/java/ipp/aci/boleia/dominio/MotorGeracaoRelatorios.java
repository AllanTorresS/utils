package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Motor de Geração de Relatórios
 */
@Audited
@Entity
@Table(name = "MOTOR_GER_RELATORIOS")
public class MotorGeracaoRelatorios implements IPersistente {

    private static final long serialVersionUID = 3050921001310015191L;

    @Id
    @Column(name = "CD_MOTOR_GER_RELATORIOS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOTOR_GER_RELATORIOS")
    @SequenceGenerator(name = "SEQ_MOTOR_GER_RELATORIOS", sequenceName = "SEQ_MOTOR_GER_RELATORIOS", allocationSize = 1)
    private Long id;

    @Column(name = "DT_REQUISICAO")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRequisicao;

    @Column(name = "DT_PERIODO_FILTRADO_INI")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPeriodoFiltradoInicial;

    @Column(name = "DT_PERIODO_FILTRADO_FIM")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPeriodoFiltradoFinal;

    @Column(name = "DT_DESCARTE")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataDescarte;

    @NotNull
    @Column(name = "ID_TIPO_RELATORIO")
    private Integer tipoRelatorio;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name="DS_MSG_ERRO")
    private String msgErro;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CD_USUARIO")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CD_ARQUIVO")
    private Arquivo arquivo;

    @Column(name="DS_FILTRO")
    private String filtro;

    @NotNull
    @Column(name="ID_EXTENSAO_ARQUIVO")
    private Integer extensaoArquivo;

    @Column(name = "DS_NOME_TEMPLATE")
    private String nomeTemplate;

    @Column(name = "NO_ULTIMA_PAGINA_PROCESSADA")
    private Integer ultimaPaginaProcessada;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "motorGeracaoRelatorio", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<AbaRelatorio> abasRelatorio;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Date getDataRequisicao() { return dataRequisicao; }

    public void setDataRequisicao(Date dataRequisicao) { this.dataRequisicao = dataRequisicao; }

    public Integer getTipoRelatorio() { return tipoRelatorio; }

    public void setTipoRelatorio(Integer tipoRelatorio) { this.tipoRelatorio = tipoRelatorio; }

    public Integer getStatus() { return status; }

    public void setStatus(Integer status) { this.status = status; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Arquivo getArquivo() { return arquivo; }

    public void setArquivo(Arquivo arquivo) { this.arquivo = arquivo; }

    public Date getDataPeriodoFiltradoInicial() { return dataPeriodoFiltradoInicial; }

    public void setDataPeriodoFiltradoInicial(Date dataPeriodoFiltradoInicial) { this.dataPeriodoFiltradoInicial = dataPeriodoFiltradoInicial; }

    public Date getDataPeriodoFiltradoFinal() { return dataPeriodoFiltradoFinal; }

    public void setDataPeriodoFiltradoFinal(Date dataPeriodoFiltradoFinal) { this.dataPeriodoFiltradoFinal = dataPeriodoFiltradoFinal; }

    public Date getDataDescarte() { return dataDescarte; }

    public void setDataDescarte(Date dataDescarte) { this.dataDescarte = dataDescarte; }

    public String getFiltro() { return filtro; }

    public void setFiltro(String filtro) { this.filtro = filtro; }

    public Integer getExtensaoArquivo() { return extensaoArquivo; }

    public void setExtensaoArquivo(Integer extensaoArquivo) { this.extensaoArquivo = extensaoArquivo; }

    public String getMsgErro() {
        return msgErro;
    }

    public void setMsgErro(String msgErro) {
        this.msgErro = msgErro;
    }

    public String getNomeTemplate() {
        return nomeTemplate;
    }

    public void setNomeTemplate(String nomeTemplate) {
        this.nomeTemplate = nomeTemplate;
    }

    public List<AbaRelatorio> getAbasRelatorio() {
        return abasRelatorio;
    }

    public void setAbasRelatorio(List<AbaRelatorio> abasRelatorio) {
        this.abasRelatorio = abasRelatorio;
    }

    public Integer getUltimaPaginaProcessada() {
        return ultimaPaginaProcessada != null ? ultimaPaginaProcessada : 0;
    }

    public void setUltimaPaginaProcessada(Integer ultimaPaginaProcessada) {
        this.ultimaPaginaProcessada = ultimaPaginaProcessada;
    }
}
