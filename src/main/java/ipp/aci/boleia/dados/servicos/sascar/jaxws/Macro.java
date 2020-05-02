
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "macro", propOrder = {
    "desricao",
    "idMacro",
    "layout"
})
public class Macro {

    protected String desricao;
    protected Integer idMacro;
    protected String layout;

    
    public String getDesricao() {
        return desricao;
    }

    
    public void setDesricao(String value) {
        this.desricao = value;
    }

    
    public Integer getIdMacro() {
        return idMacro;
    }

    
    public void setIdMacro(Integer value) {
        this.idMacro = value;
    }

    
    public String getLayout() {
        return layout;
    }

    
    public void setLayout(String value) {
        this.layout = value;
    }

}
