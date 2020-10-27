package ipp.aci.boleia.util;

/**
 * Templates de exportação em PDF.
 */
public enum TemplatePdf {

    TEMPLATE_RELATORIO_FINANCEIRO("template-relatorio-pdf-financeiro");

    private final String name;

    TemplatePdf(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
