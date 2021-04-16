package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os tipos de utilização para uma TAG
 */
public enum TipoUtilizacao implements IEnumComLabel<TipoUtilizacao> {

    PEDAGIO(1),
    ESTACIONAMENTO(2),
    ESTACIONAMENTO_PEDAGIO(3);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    TipoUtilizacao(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoUtilizacao obterPorValor(Integer value) {
        for(TipoUtilizacao tipo : TipoUtilizacao.values()) {
            if(tipo.value.equals(value)) {
                return tipo;
            }
        }
        return null;
    }
    
    public static TipoUtilizacao obterPorNome(String name){
        for(TipoUtilizacao tipo : values()){
            if(tipo.name().equals(name)){
                return tipo;
            }
        }
        return null;
    }
}
