
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sequenciamentoEvento", propOrder = {
    "atuador",
    "descricao",
    "idSequenciamentoEvento"
})
public class SequenciamentoEvento {

    protected Integer atuador;
    protected String descricao;
    protected Integer idSequenciamentoEvento;

    
    public Integer getAtuador() {
        return atuador;
    }

    
    public void setAtuador(Integer value) {
        this.atuador = value;
    }

    
    public String getDescricao() {
        return descricao;
    }

    
    public void setDescricao(String value) {
        this.descricao = value;
    }

    
    public Integer getIdSequenciamentoEvento() {
        return idSequenciamentoEvento;
    }

    
    public void setIdSequenciamentoEvento(Integer value) {
        this.idSequenciamentoEvento = value;
    }

}
