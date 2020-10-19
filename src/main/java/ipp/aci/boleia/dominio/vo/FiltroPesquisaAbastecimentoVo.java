package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;
import java.util.List;

/**
 * Filtro para pesquisa de abastecimento
 */
public class FiltroPesquisaAbastecimentoVo extends BaseFiltroPaginado {

    private Long idConsolidado;
    private boolean considerarPostergados;
    private Long idCobranca;
    private Long idReembolso;
    private Date de;
    private Date ate;
    private Date requisicaoDe;
    private Date requisicaoAte;
    private Date processamentoDe;
    private Date processamentoAte;
    private Date dataAbastecimento;
    private String placa;
    private EnumVo statusAutorizacao;
    private EntidadeVo frota;
    private EntidadeVo pontoDeVenda;
    private List<Long> idsPontoVenda;
    private boolean contingencia;
    private EnumVo statusEmissaoNf;
    private String notaFiscal;
    private String numeroSerie;
    private Boolean semEstorno;
    private EntidadeVo unidade;
    private EnumVo classificacao;
    private FiltroPesquisaEmpresaAgregadaVo empresaAgregada;
    private EnumVo agruparExibicao;
    private int [] camposExportacaoRelatorio;
    private boolean pendenteConfirmacaoPOS;
    private Boolean apenasEstornos;
    private TipoPerfilUsuario tipoPerfilUsuario;
    private Boolean apenasAjustados;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date dataHoraProcessamentoDe;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSZ")
    private Date dataHoraProcessamentoAte;
    private boolean pdv;

    public Date getDataAbastecimento() {
        return dataAbastecimento;
    }

    public void setDataAbastecimento(Date dataAbastecimento) {
        this.dataAbastecimento = dataAbastecimento;
    }

    public Date getDe() {
        return de;
    }

    public void setDe(Date de) {
        this.de = de;
    }

    public Date getAte() {
        return ate;
    }

    public void setAte(Date ate) {
        this.ate = ate;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public EnumVo getStatusAutorizacao() {
        return statusAutorizacao;
    }

    public void setStatusAutorizacao(EnumVo statusAutorizacao) {
        this.statusAutorizacao = statusAutorizacao;
    }

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public EntidadeVo getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    public boolean isContingencia() {
        return contingencia;
    }

    public void setContingencia(boolean contingencia) {
        this.contingencia = contingencia;
    }

    public EntidadeVo getUnidade() {
        return unidade;
    }

    public void setUnidade(EntidadeVo unidade) {
        this.unidade = unidade;
    }

    public EnumVo getStatusEmissaoNf() {
        return statusEmissaoNf;
    }

    public void setStatusEmissaoNf(EnumVo statusEmissaoNf) {
        this.statusEmissaoNf = statusEmissaoNf;
    }

    public String getNotaFiscal() {
        return notaFiscal;
    }

    public void setNotaFiscal(String notaFiscal) {
        this.notaFiscal = notaFiscal;
    }

    public String getNumeroSerie() { return numeroSerie; }

    public void setNumeroSerie(String numeroSerie) { this.numeroSerie = numeroSerie; }

    public Boolean getSemEstorno() {
        return semEstorno;
    }

    public void setSemEstorno(Boolean semEstorno) {
        this.semEstorno = semEstorno;
    }

    public EnumVo getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(EnumVo classificacao) {
        this.classificacao = classificacao;
    }

    public FiltroPesquisaEmpresaAgregadaVo getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(FiltroPesquisaEmpresaAgregadaVo empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public Date getRequisicaoDe() {
        return requisicaoDe;
    }

    public void setRequisicaoDe(Date requisicaoDe) {
        this.requisicaoDe = requisicaoDe;
    }

    public Date getRequisicaoAte() {
        return requisicaoAte;
    }

    public void setRequisicaoAte(Date requisicaoAte) {
        this.requisicaoAte = requisicaoAte;
    }

    public Long getIdConsolidado() {
        return idConsolidado;
    }

    public void setIdConsolidado(Long idConsolidado) {
        this.idConsolidado = idConsolidado;
    }

    public boolean isConsiderarPostergados() {
        return considerarPostergados;
    }

    public void setConsiderarPostergados(boolean considerarPostergados) {
        this.considerarPostergados = considerarPostergados;
    }

    public Long getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(Long idCobranca) {
        this.idCobranca = idCobranca;
    }

    public Date getProcessamentoDe() {
        return processamentoDe;
    }

    public void setProcessamentoDe(Date processamentoDe) {
        this.processamentoDe = processamentoDe;
    }

    public Date getProcessamentoAte() {
        return processamentoAte;
    }

    public void setProcessamentoAte(Date processamentoAte) {
        this.processamentoAte = processamentoAte;
    }

    public EnumVo getAgruparExibicao() {
        return agruparExibicao;
    }

    public void setAgruparExibicao(EnumVo agruparExibicao) {
        this.agruparExibicao = agruparExibicao;
    }

    public Date getDataHoraProcessamentoDe() { return dataHoraProcessamentoDe; }

    public void setDataHoraProcessamentoDe(Date dataHoraProcessamentoDe) { this.dataHoraProcessamentoDe = dataHoraProcessamentoDe; }

    public Date getDataHoraProcessamentoAte() { return dataHoraProcessamentoAte; }

    public void setDataHoraProcessamentoAte(Date dataHoraProcessamentoAte) { this.dataHoraProcessamentoAte = dataHoraProcessamentoAte; }

    public boolean isPdv() {
        return pdv;
    }

    public void setPdv(boolean pdv) {
        this.pdv = pdv;
    }

    public int[] getCamposExportacaoRelatorio() { return camposExportacaoRelatorio; }

    public void setCamposExportacaoRelatorio(int[] camposExportacaoRelatorio) { this.camposExportacaoRelatorio = camposExportacaoRelatorio; }

    public List<Long> getIdsPontoVenda() {
        return idsPontoVenda;
    }

    public void setIdsPontoVenda(List<Long> idsPontoVenda) {
        this.idsPontoVenda = idsPontoVenda;
    }

    public boolean getPendenteConfirmacaoPOS() {
        return pendenteConfirmacaoPOS;
    }

    public void setPendenteConfirmacaoPOS(boolean pendenteConfirmacaoPOS) {
        this.pendenteConfirmacaoPOS = pendenteConfirmacaoPOS;
    }

    public Long getIdReembolso() {
        return idReembolso;
    }

    public void setIdReembolso(Long idReembolso) {
        this.idReembolso = idReembolso;
    }

    public Boolean isApenasEstornos() { return apenasEstornos; }

    public void setApenasEstornos(Boolean apenasEstornos) { this.apenasEstornos = apenasEstornos; }

    public boolean isPendenteConfirmacaoPOS() {
        return pendenteConfirmacaoPOS;
    }

    public TipoPerfilUsuario getTipoPerfilUsuario() {
        return tipoPerfilUsuario;
    }

    public void setTipoPerfilUsuario(TipoPerfilUsuario tipoPerfilUsuario) {
        this.tipoPerfilUsuario = tipoPerfilUsuario;
    }

    public Boolean isApenasAjustados() { return apenasAjustados; }

    public void setApenasAjustados(Boolean apenasAjustados) { this.apenasAjustados = apenasAjustados; }
}
