package ipp.aci.boleia.dominio;


import com.fasterxml.jackson.annotation.JsonIgnore;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.util.UtilitarioJson;
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
import javax.persistence.Transient;
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
    @ManyToOne(fetch = FetchType.EAGER)
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

    @Column(name = "NO_TOTAL_PAGINAS")
    private Integer totalDePaginas;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "motorGeracaoRelatorio", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<AbaRelatorio> abasRelatorio;

    @Column(name = "NO_ROTA_EM_PROCESSAMENTO")
    private Integer rotaEmProcessamento;

    @Transient
    @JsonIgnore
    private transient Object filtroTipado;

    /**
     * Obtém o filtro conforme tipagem parametrizada, guarda filtro para operações futuras.
     *
     * @param classeFiltro Classe que representa o filtro
     * @return instância do filtro tipado
     */
    @Transient
    @JsonIgnore
    public <T> T obterFiltro(Class<T> classeFiltro) {
        if (filtroTipado == null) {
            filtroTipado = UtilitarioJson.toObject(filtro, classeFiltro);
        }
        return (T) filtroTipado;
    }

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

    public Integer getTotalDePaginas() {
        return totalDePaginas != null ? totalDePaginas : 0;
    }

    public void setTotalDePaginas(Integer totalDePaginas) {
        this.totalDePaginas = totalDePaginas;
    }

    public void setUltimaPaginaProcessada(Integer ultimaPaginaProcessada) {
        this.ultimaPaginaProcessada = ultimaPaginaProcessada;
    }

    public Long getTotalRegistros() {
        return this.abasRelatorio.stream()
                    .mapToLong(a -> a.getTotalRegistros() != null ? a.getTotalRegistros() : 0).sum();
    }

    public Long getRegistrosProcessados() {
        return this.abasRelatorio.stream()
                .mapToLong(a -> a.getRegistrosProcessados() != null ? a.getRegistrosProcessados() : 0).sum();
    }

    public Boolean processouTodosRegistros(){
        return this.getRegistrosProcessados().compareTo(this.getTotalRegistros()) >= 0;
    }

    public Integer getRotaEmProcessamento() {
        return rotaEmProcessamento;
    }

    public void setRotaEmProcessamento(Integer rotaEmProcessamento) {
        this.rotaEmProcessamento = rotaEmProcessamento;
    }

    @Transient
    @JsonIgnore
    public Object getFiltroTipado() {
        return filtroTipado;
    }

    public void setFiltroTipado(Object filtroTipado) {
        this.filtroTipado = filtroTipado;
    }
}
