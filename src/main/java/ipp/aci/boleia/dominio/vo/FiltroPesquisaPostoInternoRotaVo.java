package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.util.List;

/**
 * Filtro para pesquisa de Posto Interno em rotas
 */
public class FiltroPesquisaPostoInternoRotaVo {

    private Long idFrota;
    private List<List<CoordenadaVo>> filtrosCoordenadas;
    private BigDecimal margemGrausFiltroCoordenadas;

    /**
     * Construtor Padrão
     */
    public FiltroPesquisaPostoInternoRotaVo() {
    }

    public FiltroPesquisaPostoInternoRotaVo(Long idFrota, List<List<CoordenadaVo>> filtrosCoordenadas, BigDecimal margemGrausFiltroCoordenadas) {
        this.idFrota = idFrota;
        this.filtrosCoordenadas = filtrosCoordenadas;
        this.margemGrausFiltroCoordenadas = margemGrausFiltroCoordenadas;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

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
