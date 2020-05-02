
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "caixaPretaList", propOrder = {
    "caixaPreta"
})
public class CaixaPretaList {

    protected List<CaixaPreta> caixaPreta;

    
    public List<CaixaPreta> getCaixaPreta() {
        if (caixaPreta == null) {
            caixaPreta = new ArrayList<CaixaPreta>();
        }
        return this.caixaPreta;
    }

}
