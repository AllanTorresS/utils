
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "layoutDetalhado", propOrder = {
    "descricao",
    "idLayout",
    "macros"
})
public class LayoutDetalhado {

    protected String descricao;
    protected Integer idLayout;
    @XmlElement(nillable = true)
    protected List<Macro> macros;

    
    public String getDescricao() {
        return descricao;
    }

    
    public void setDescricao(String value) {
        this.descricao = value;
    }

    
    public Integer getIdLayout() {
        return idLayout;
    }

    
    public void setIdLayout(Integer value) {
        this.idLayout = value;
    }

    
    public List<Macro> getMacros() {
        if (macros == null) {
            macros = new ArrayList<Macro>();
        }
        return this.macros;
    }

}
