package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.List;

/**
 * Filtro de relatorio de abastecimento de Agenciador de Frete
 */
public class FiltroRelatorioTransacaoVo extends BaseFiltroPaginado {

    private String de;
    private String ate;
    private List<Long> idsPontoVenda;

    public FiltroRelatorioTransacaoVo() {
        //Construtor default
    }

    public String getDe() {
        return de;
    }

    public void setDe(String de) {
        this.de = de;
    }

    public String getAte() {
        return ate;
    }

    public void setAte(String ate) {
        this.ate = ate;
    }

    public List<Long> getIdsPontoVenda() {
        return idsPontoVenda;
    }

    public void setIdsPontoVenda(List<Long> idsPontoVenda) {
        this.idsPontoVenda = idsPontoVenda;
    }
}
