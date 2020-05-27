
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "layoutGrupoPontos", propOrder = {
    "idLayoutGrupoPonto",
    "nome"
})
public class LayoutGrupoPontos {

    protected Integer idLayoutGrupoPonto;
    protected String nome;

    
    public Integer getIdLayoutGrupoPonto() {
        return idLayoutGrupoPonto;
    }

    
    public void setIdLayoutGrupoPonto(Integer value) {
        this.idLayoutGrupoPonto = value;
    }

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String value) {
        this.nome = value;
    }

}
