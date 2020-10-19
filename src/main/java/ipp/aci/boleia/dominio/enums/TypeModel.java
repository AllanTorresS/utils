package ipp.aci.boleia.dominio.enums;

/**
 * Representa os tipos de entidade obtidas no kmv pdv caminhoneiro
 */
public enum TypeModel {

    BICO ("BicoCombustivel"),
    ABASTECIMENTO ("AbastecimentoCombustivel"),
    PARAMETRO ("Parametro");

    private final String nomeModel;

    /**
     * Contrutor do enum
     * @param nomeModel Nome do model
     */
    TypeModel(String nomeModel) {
        this.nomeModel = nomeModel;
    }

    public String getNomeModel() {
        return nomeModel;
    }
}
