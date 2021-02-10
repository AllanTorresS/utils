package ipp.aci.boleia.dominio.vo;

/**
 * Classe abstrata para implementação de Empresa Abatescedora (Agenciador de frete e Frota)
 *
 */
public class EmpresaAbastecedoraVo {

    private Long id;
    private Integer tipo;
    private Long cnpj;
    private String razaoSocial;

    public EmpresaAbastecedoraVo(){

    }

    /**
     * Construtor
     * @param id O id da Empresa
     * @param tipo O tipo da empresa
     * @param cnpj o cnpj da empresa
     * @param razaoSocial o nome da empresa
     */
    public EmpresaAbastecedoraVo(Long id, Integer tipo, Long cnpj, String razaoSocial){
        this.id=id;
        this.tipo=tipo;
        this.cnpj=cnpj;
        this.razaoSocial=razaoSocial;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
}
