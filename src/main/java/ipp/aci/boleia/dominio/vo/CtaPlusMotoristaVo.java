package ipp.aci.boleia.dominio.vo;

/**
 * Vo para obtenção de entidade de motorista enviada pelo CTA Plus
 */
public class CtaPlusMotoristaVo {

    private Long codigo;
    private String nome;
    private String id;
    private String cpf;

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
}
