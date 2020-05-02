
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "logComando", propOrder = {
    "codigo",
    "mensagem"
})
public class LogComando {

    protected String codigo;
    protected String mensagem;

    
    public String getCodigo() {
        return codigo;
    }

    
    public void setCodigo(String value) {
        this.codigo = value;
    }

    
    public String getMensagem() {
        return mensagem;
    }

    
    public void setMensagem(String value) {
        this.mensagem = value;
    }

}
