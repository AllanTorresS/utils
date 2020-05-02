package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.util.UtilitarioFormatacao;

/**
 * Representa o endereco de uma das partes na negociacao entre frota e Boleia
 */
public class MundipaggEnderecoVo {

    private static final String BR_COUNTRY = "BR";

    private String street;
    private String number;
    @JsonProperty("zip_code")
    private String zipCode;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String complement;

    public MundipaggEnderecoVo() {

    }

    /**
     * Gera o endereco da frota
     * @param frota A frota em negociacao com o Boleia
     */
    public MundipaggEnderecoVo(Frota frota) {
        street = frota.getLogradouro();
        number = frota.getNumero().toString();
        zipCode = UtilitarioFormatacao.formatarCepApresentacao(frota.getCep());
        neighborhood = frota.getBairro();
        city = frota.getMunicipio();
        state = frota.getUnidadeFederativa();
        complement = frota.getComplemento();
        country = BR_COUNTRY;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }
}
 