package ipp.aci.boleia.dominio.vo;

import java.util.Date;

/**
 * Representa um objeto de filtro para busca de Precos negociados
 */
public class PrecoNegociadoVo {
    private Long idFrota;
    private Long idPtov;
    private Long codigoCombustivel;
    private Date dataAbastecimento;

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public Long getIdPtov() {
        return idPtov;
    }

    public void setIdPtov(Long idPtov) {
        this.idPtov = idPtov;
    }

    public Long getCodigoCombustivel() {
        return codigoCombustivel;
    }

    public void setCodigoCombustivel(Long codigoCombustivel) {
        this.codigoCombustivel = codigoCombustivel;
    }

    public Date getDataAbastecimento() {
        return dataAbastecimento;
    }

    public void setDataAbastecimento(Date dataAbastecimento) {
        this.dataAbastecimento = dataAbastecimento;
    }
}


