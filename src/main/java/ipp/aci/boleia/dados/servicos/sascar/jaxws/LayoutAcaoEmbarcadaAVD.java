
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "layoutAcaoEmbarcadaAVD", propOrder = {
    "idLayoutAcaoEmbarcadaAVD",
    "nome"
})
public class LayoutAcaoEmbarcadaAVD {

    protected Integer idLayoutAcaoEmbarcadaAVD;
    protected String nome;

    
    public Integer getIdLayoutAcaoEmbarcadaAVD() {
        return idLayoutAcaoEmbarcadaAVD;
    }

    
    public void setIdLayoutAcaoEmbarcadaAVD(Integer value) {
        this.idLayoutAcaoEmbarcadaAVD = value;
    }

    
    public String getNome() {
        return nome;
    }

    
    public void setNome(String value) {
        this.nome = value;
    }

}
