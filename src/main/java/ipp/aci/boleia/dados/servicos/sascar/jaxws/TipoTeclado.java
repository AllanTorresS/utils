
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "tipoTeclado")
@XmlEnum
public enum TipoTeclado {

    TECLADO_TMCD("TECLADO_TMCD"),
    @XmlEnumValue("TECLADO_TD50")
    TECLADO_TD_50("TECLADO_TD50"),
    @XmlEnumValue("TECLADO_TMS3")
    TECLADO_TMS_3("TECLADO_TMS3");
    private final String value;

    TipoTeclado(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoTeclado fromValue(String v) {
        for (TipoTeclado c: TipoTeclado.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
