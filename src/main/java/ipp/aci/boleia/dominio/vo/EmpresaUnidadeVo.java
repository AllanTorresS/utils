package ipp.aci.boleia.dominio.vo;

/**
 * Representa uma entidade EmpresaUnidade
 */
public class EmpresaUnidadeVo {
    private Long id;
    private EnumVo tipo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumVo getTipo() {
        return tipo;
    }

    public void setTipo(EnumVo tipo) {
        this.tipo = tipo;
    }
}
