package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro de relatorio de abastecimento de Agenciador de Frete
 */
public class FiltroRelatorioTransacaoVo extends BaseFiltroPaginado {

    private String de;
    private String ate;

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
}
