
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;



@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "layout", propOrder = {
    "codigo",
    "descricao",
    "idLayout",
    "tipoTeclado"
})
public class Layout {

    protected Integer codigo;
    protected String descricao;
    protected Integer idLayout;
    protected int tipoTeclado;

    
    public Integer getCodigo() {
        return codigo;
    }

    
    public void setCodigo(Integer value) {
        this.codigo = value;
    }

    
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

    
    public int getTipoTeclado() {
        return tipoTeclado;
    }

    
    public void setTipoTeclado(int value) {
        this.tipoTeclado = value;
    }

}
