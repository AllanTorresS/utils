package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Frota;

/**
 * Representa o telefone do cliente com pedido na Mundipagg
 */
public class MundipaggTelefoneMovelVo {

    private static final String BR_COUNTRY_CODE = "55";

    private String country_code;
    private String number;
    private String area_code;

    /**
     * Construtor padrão
     */
    public MundipaggTelefoneMovelVo() {
    }

    /**
     * Constrói o VO a partir de uma entidade Frota
     *
     * @param frota a frota
     */
    public MundipaggTelefoneMovelVo(Frota frota) {
        this.country_code = BR_COUNTRY_CODE;
        this.number =  frota.getTelefone().toString();
        this.area_code =  frota.getDddTelefone().toString();
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getArea_code() {
        return area_code;
    }

    public void setArea_code(String area_code) {
        this.area_code = area_code;
    }
}