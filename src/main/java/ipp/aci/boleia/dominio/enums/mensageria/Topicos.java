package ipp.aci.boleia.dominio.enums.mensageria;

public enum Topicos {
    AUTORIZACAO_PAGAMENTO(0),
    MOTOR_GERACAO_RELATORIOS(1);

    private Integer value;

    Topicos(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }
}
