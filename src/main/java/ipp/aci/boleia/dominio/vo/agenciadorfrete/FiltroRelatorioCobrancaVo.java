package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;

/**
 * Filtro de relatorio de Cobrança de Agenciador de Frete
 */
public class FiltroRelatorioCobrancaVo extends BaseFiltroPaginado {

    private Date de;
    private Date ate;

    public FiltroRelatorioCobrancaVo () {

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
}
