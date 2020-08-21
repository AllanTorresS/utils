package ipp.aci.boleia.dominio.enums;

/**
 * Representa a api que originou o ponto da Rota persistida
 *
 * GOOGLE_MAPS Indica que o ponto foi calculado pela api do Google Maps padrão
 * OSRM Indica que o ponto foi calculado através da api Open Street Routing Map
 */
public enum ApiPontoRota {

    GOOGLE_MAPS(0),
    OSRM(1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor da enum
     */
    ApiPontoRota(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enumercao
     * @return o valor da enumeracao
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static ApiPontoRota obterPorValor(Integer value) {
        for(ApiPontoRota status : ApiPontoRota.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
