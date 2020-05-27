
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;



@XmlType(name = "tipoLayout")
@XmlEnum
public enum TipoLayout {

    @XmlEnumValue("LAYOUT_TD40")
    LAYOUT_TD_40("LAYOUT_TD40"),
    @XmlEnumValue("LAYOUT_TD50")
    LAYOUT_TD_50("LAYOUT_TD50"),
    LAYOUT_TMCD("LAYOUT_TMCD"),
    @XmlEnumValue("LAYOUT_SEQUENCIAMENTO_TD50")
    LAYOUT_SEQUENCIAMENTO_TD_50("LAYOUT_SEQUENCIAMENTO_TD50"),
    @XmlEnumValue("LAYOUT_TMS3")
    LAYOUT_TMS_3("LAYOUT_TMS3");
    private final String value;

    TipoLayout(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TipoLayout fromValue(String v) {
        for (TipoLayout c: TipoLayout.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
