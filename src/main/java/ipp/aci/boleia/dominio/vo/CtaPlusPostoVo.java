package ipp.aci.boleia.dominio.vo;

/**
 * Vo para obtenção da entidade posto enviada pelo CTA Plus
 */
public class CtaPlusPostoVo {

    private Long codigo;
    private String cnpj;
    private String nome;
    private String id;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
