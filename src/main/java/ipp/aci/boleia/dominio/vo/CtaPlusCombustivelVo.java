package ipp.aci.boleia.dominio.vo;

/**
 * Vo para obtenção da entidade combustivel enviada pelo CTA Plus
 */
public class CtaPlusCombustivelVo {

    private Long codigo;
    private String id;
    private String descricao;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
