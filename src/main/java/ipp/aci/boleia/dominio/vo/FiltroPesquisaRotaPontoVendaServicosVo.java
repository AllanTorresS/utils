package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Filtro para pesquisa de pvs em rotas
 */
public class FiltroPesquisaRotaPontoVendaServicosVo {

    private String nome;
    private Long idFrota;
    private Boolean postoUrbano;
    private List<Integer> parametrosDeUso;
    private List<Long> postosParametizados;
    private List<EntidadeVo> tiposCombustivel;
    private List<EnumVo> opcoesPrimarias;
    private List<EnumVo> opcoesSecundarias;
    private List<List<CoordenadaVo>> filtrosCoordenadas;
    private BigDecimal margemGrausFiltroCoordenadas;


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public Boolean getPostoUrbano() {
        return postoUrbano;
    }

    public void setPostoUrbano(Boolean postoUrbano) {
        this.postoUrbano = postoUrbano;
    }

    public List<Integer> getParametrosDeUso() {
        return parametrosDeUso;
    }

    public void setParametrosDeUso(List<Integer> parametrosDeUso) {
        this.parametrosDeUso = parametrosDeUso;
    }

    public List<Long> getPostosParametizados() {
        return postosParametizados;
    }

    public void setPostosParametizados(List<Long> postosParametizados) {
        this.postosParametizados = postosParametizados;
    }

    public List<EntidadeVo> getTiposCombustivel() { return tiposCombustivel; }

    public void setTiposCombustivel(List<EntidadeVo> tiposCombustivel) { this.tiposCombustivel = tiposCombustivel; }

    public List<EnumVo> getOpcoesPrimarias() { return opcoesPrimarias; }

    public void setOpcoesPrimarias(List<EnumVo> opcoesPrimarias) { this.opcoesPrimarias = opcoesPrimarias; }

    public List<EnumVo> getOpcoesSecundarias() { return opcoesSecundarias; }

    public void setOpcoesSecundarias(List<EnumVo> opcoesSecundarias) { this.opcoesSecundarias = opcoesSecundarias; }

    public List<List<CoordenadaVo>> getFiltrosCoordenadas() {
        return filtrosCoordenadas;
    }

    public void setFiltrosCoordenadas(List<List<CoordenadaVo>> filtrosCoordenadas) {
        this.filtrosCoordenadas = filtrosCoordenadas;
    }

    public BigDecimal getMargemGrausFiltroCoordenadas() {
        return margemGrausFiltroCoordenadas;
    }

    public void setMargemGrausFiltroCoordenadas(BigDecimal margemGrausFiltroCoordenadas) {
        this.margemGrausFiltroCoordenadas = margemGrausFiltroCoordenadas;
    }
}
