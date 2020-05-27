package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.CampoEdicaoAbastecimento;

/**
 * VO que representa a edição de um campo de um abastecimento da solução.
 */
public class EdicaoCampoAbastecimentoVo {
    private String categoria;
    private String campo;
    private String valorAutorizacao;
    private String valorEdicao;

    /**
     * Construtor default
     */
    public EdicaoCampoAbastecimentoVo() {
    }

    /**
     * Construtor da classe.
     *
     * @param campo entidade de Autorizacao de Pagamento
     * @param valorAutorizacao Valor do campo na tabela de autorização pagamento
     * @param valorEdicao Valor do campo na tabela de edição
     */
    public EdicaoCampoAbastecimentoVo(CampoEdicaoAbastecimento campo, String valorAutorizacao, String valorEdicao) {
        this.campo = campo.getLabel();
        this.valorAutorizacao = valorAutorizacao;
        this.valorEdicao = valorEdicao;
    }

    /**
     * Construtor da classe.
     *
     * @param categoria entidade de Autorizacao de Pagamento
     * @param campo Itens adicionais relacionados ao abastecimento
     * @param valorEdicao Valor do campo na tabela de edição
     */
    public EdicaoCampoAbastecimentoVo(String categoria, CampoEdicaoAbastecimento campo, String valorEdicao) {
        this.categoria = categoria;
        this.campo = campo.getLabel();
        this.valorEdicao = valorEdicao;
    }

    /**
     * Construtor da classe.
     * @param categoria entidade de Autorizacao de Pagamento
     * @param campo entidade de Autorizacao de Pagamento
     * @param valorAutorizacao Valor do campo na tabela de autorização pagamento
     * @param valorEdicao Valor do campo na tabela de edição
     */
    public EdicaoCampoAbastecimentoVo(String categoria, CampoEdicaoAbastecimento campo, String valorAutorizacao, String valorEdicao) {
        this(campo, valorAutorizacao, valorEdicao);
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public String getValorAutorizacao() {
        return valorAutorizacao;
    }

    public void setValorAutorizacao(String valorAutorizacao) {
        this.valorAutorizacao = valorAutorizacao;
    }

    public String getValorEdicao() {
        return valorEdicao;
    }

    public void setValorEdicao(String valorEdicao) {
        this.valorEdicao = valorEdicao;
    }
}
