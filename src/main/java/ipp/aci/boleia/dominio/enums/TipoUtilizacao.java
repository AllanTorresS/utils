package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os tipos de utilização para uma TAG
 */
public enum TipoUtilizacao implements IEnumComLabel<TipoUtilizacao> {

    PEDAGIO(3),
    ESTACIONAMENTO(1),
    ESTACIONAMENTO_PEDAGIO(2);

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
     * Obtem o Tipo de Utilização por valor
     * @param value Valor do tipo de utilização 
     * @return Enum para o valor informado
     */
    public static TipoUtilizacao obterPorValor(Integer value) {
        for(TipoUtilizacao tipo : TipoUtilizacao.values()) {
            if(tipo.value.equals(value)) {
                return tipo;
            }
        }
        return null;
    }
    
    /**
     * Obtem o Tipo de Utilização por nome
     * @param name Nome do tipo de utilização
     * @return Enum para o nome informado
     */
    public static TipoUtilizacao obterPorNome(String name){
        for(TipoUtilizacao tipo : values()){
            if(tipo.name().equals(name)){
                return tipo;
            }
        }
        return null;
    }
}
