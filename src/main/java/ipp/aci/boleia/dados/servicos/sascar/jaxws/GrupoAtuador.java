
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "grupoAtuador", propOrder = {
    "descricao",
    "idAtuador",
    "tipoPorta"
})
public class GrupoAtuador {

    protected String descricao;
    protected Integer idAtuador;
    protected String tipoPorta;

    
    public String getDescricao() {
        return descricao;
    }

    
    public void setDescricao(String value) {
        this.descricao = value;
    }

    
    public Integer getIdAtuador() {
        return idAtuador;
    }

    
    public void setIdAtuador(Integer value) {
        this.idAtuador = value;
    }

    
    public String getTipoPorta() {
        return tipoPorta;
    }

    
    public void setTipoPorta(String value) {
        this.tipoPorta = value;
    }

}
