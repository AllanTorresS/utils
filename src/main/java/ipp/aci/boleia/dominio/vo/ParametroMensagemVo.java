package ipp.aci.boleia.dominio.vo;

/**
 * Representa um dos parâmetros a serem enviados via SMS
 */
public class ParametroMensagemVo {

    private String nome;
    private String valor;

    public ParametroMensagemVo() {
        //Construtor default
    }

    /**
     * Consgtutor do parametro da mensagem
     * @param nome O nome do parametro
     * @param valor O valor do parametro
     */
    public ParametroMensagemVo(String nome, String valor) {
        this.nome = nome;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
