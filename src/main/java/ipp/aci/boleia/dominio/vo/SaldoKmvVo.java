package ipp.aci.boleia.dominio.vo;

/**
 * Classe para armazenar informações do saldo do usuario no kmv
 */
public class SaldoKmvVo {

    private Integer saldo;

    private Integer statusCode;

    public SaldoKmvVo() {
        //Construtor default
    }

    /**
     * Preenche o saldo do usuario no kmv
     *
     * @param saldo o saldo do usuario
     */
    public SaldoKmvVo(Integer saldo) {
        this.saldo = saldo;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public Integer getStatusCode() { return statusCode; }

    public void setStatusCode(Integer statusCode) { this.statusCode = statusCode; }
}
