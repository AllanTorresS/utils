
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "statusComando", propOrder = {
    "dataExec",
    "idStatusComando"
})
public class StatusComando {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataExec;
    protected Integer idStatusComando;

    
    public XMLGregorianCalendar getDataExec() {
        return dataExec;
    }

    
    public void setDataExec(XMLGregorianCalendar value) {
        this.dataExec = value;
    }

    
    public Integer getIdStatusComando() {
        return idStatusComando;
    }

    
    public void setIdStatusComando(Integer value) {
        this.idStatusComando = value;
    }

}
