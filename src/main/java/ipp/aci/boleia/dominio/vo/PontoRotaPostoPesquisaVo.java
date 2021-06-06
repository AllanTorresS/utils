package ipp.aci.boleia.dominio.vo;

/**
 * VO usado para pesquisar postos presentes numa rota
 */
public class PontoRotaPostoPesquisaVo {

    private Long idPontoVenda;
    private Long idFrota;
    private Long idUnidade;

    /**
     * Construtor padrão
     */
    public PontoRotaPostoPesquisaVo() { }

    public Long getIdPontoVenda() {
        return idPontoVenda;
    }

    public void setIdPontoVenda(Long idPontoVenda) {
        this.idPontoVenda = idPontoVenda;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public Long getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
    }
}
