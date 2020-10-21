package ipp.aci.boleia.dominio.vo.agenciadorfrete.ndd;

/**
 * Classe para armazenar informações da transação do motorista autonomo para o serviço da Ndd
 */
public class RespostaTransacaoNddVo {
    private Integer code;
    private String message;

    public RespostaTransacaoNddVo() {
        //Construtor default
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
