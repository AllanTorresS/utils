
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "obterLayoutDetalhado", propOrder = {
    "usuario",
    "senha",
    "layout",
    "idLayout",
    "dataReferencia"
})
public class ObterLayoutDetalhado {

    protected String usuario;
    protected String senha;
    @XmlSchemaType(name = "string")
    protected TipoLayout layout;
    protected Integer idLayout;
    protected String dataReferencia;

    
    public String getUsuario() {
        return usuario;
    }

    
    public void setUsuario(String value) {
        this.usuario = value;
    }

    
    public String getSenha() {
        return senha;
    }

    
    public void setSenha(String value) {
        this.senha = value;
    }

    
    public TipoLayout getLayout() {
        return layout;
    }

    
    public void setLayout(TipoLayout value) {
        this.layout = value;
    }

    
    public Integer getIdLayout() {
        return idLayout;
    }

    
    public void setIdLayout(Integer value) {
        this.idLayout = value;
    }

    
    public String getDataReferencia() {
        return dataReferencia;
    }

    
    public void setDataReferencia(String value) {
        this.dataReferencia = value;
    }

}
