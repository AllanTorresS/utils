package ipp.aci.boleia.dominio.pesquisa.comum;

import java.util.Date;

/**
 * Filtro base com intervalo de tempo (período)
 */
public class FiltroBasePeriodoPaginado extends BaseFiltroPaginado {
    private Date de;
    private Date ate;

    /**
     * Construtor padrão
     */
    public FiltroBasePeriodoPaginado(){}

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
