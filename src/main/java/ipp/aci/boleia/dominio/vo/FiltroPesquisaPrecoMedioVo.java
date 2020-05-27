package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Filtro para pesquisa de preço médio dos postos
 */
public class FiltroPesquisaPrecoMedioVo {
    private Long idCombustivel;
    private String municipioPontoDeVenda;
    private List<Long> pontoVendaRemovidosConsulta;

    public Long getIdCombustivel() { return idCombustivel; }

    public void setIdCombustivel(Long idCombustivel) { this.idCombustivel = idCombustivel; }

    public String getMunicipioPontoDeVenda() { return municipioPontoDeVenda; }

    public void setMunicipioPontoDeVenda(String municipioPontoDeVenda) { this.municipioPontoDeVenda = municipioPontoDeVenda; }

    public List<Long> getPontoVendaRemovidosConsulta() { return pontoVendaRemovidosConsulta; }

    public void setPontoVendaRemovidosConsulta(List<Long> pontoVendaRemovidosConsulta) { this.pontoVendaRemovidosConsulta = pontoVendaRemovidosConsulta; }
}
