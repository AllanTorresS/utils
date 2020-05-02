package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.CamposSms;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa a transação (assunto) do SMS enviado ao motorista
 */
public class TransacaoVo {

    private String assunto;
    private List<ParametroMensagemVo> parametros;

    /**
     * Construtor da transacao
     */
    public TransacaoVo() {
        parametros = new ArrayList<>();
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public List<ParametroMensagemVo> getParametros() {
        return parametros;
    }

    public void setParametros(List<ParametroMensagemVo> parametros) {
        this.parametros = parametros;
    }

    /**
     * Adiciona um dado parametro ao SMS
     * @param nome O nome do parametro
     * @param valor O valor do parametro
     * @return O parametro adicionado
     */
    public TransacaoVo adicionarParametro(CamposSms nome, String valor) {
        parametros.add(new ParametroMensagemVo(nome.getCodigoCampo(), valor));
        return this;
    }
}
