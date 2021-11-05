package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Frota;

/**
 * Classe usada para encapsular o telefone do cliente com pedido na Mundipagg
 */
public class MundipaggTelefoneVo {
    private MundipaggTelefoneMovelVo home_phone;

    /**
     * Construtor padrão
     */
    public MundipaggTelefoneVo() {
    }

    /**
     * Constrói o VO a partir de uma entidade Frota
     *
     * @param frota a frota
     */
    public MundipaggTelefoneVo(Frota frota) {
        this.home_phone = new MundipaggTelefoneMovelVo(frota);
    }

    public MundipaggTelefoneMovelVo getHome_phone() {
        return home_phone;
    }

    public void setHome_phone(MundipaggTelefoneMovelVo home_phone) {
        this.home_phone = home_phone;
    }
}