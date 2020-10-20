package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.Componente;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.util.UtilitarioFormatacao;

import java.math.BigDecimal;
import java.util.List;

import static ipp.aci.boleia.util.UtilitarioFormatacao.TAMANHO_CNPJ;
import static ipp.aci.boleia.util.UtilitarioFormatacao.formatarNumeroZerosEsquerda;

/**
 * Vo que representa o posto da api de agenciador de frete
 */
public class PostoVo {

    private String nome;
    private String cnpj;
    private EnderecoVo endereco;
    private String latitude;
    private String longitude;
    private BigDecimal mdr;
    private List<CombustivelVo> combustiveis;

    public PostoVo() {
        // serializacao json
    }

    /**
     * Constrói a representação com os dados fornecidos
     * @param pontoDeVenda O Ponto de Venda que se deseja representar
     * @param combustiveis Lista de combustivel
     */
    public PostoVo(PontoDeVenda pontoDeVenda, BigDecimal mdr, List<CombustivelVo> combustiveis) {
        this.nome = pontoDeVenda.getNome().trim();
        Long cnpjNumerico = obterCnpj(pontoDeVenda);
        this.cnpj = cnpjNumerico != null ? formatarNumeroZerosEsquerda(cnpjNumerico, TAMANHO_CNPJ): null;
        this.endereco = new EnderecoVo(pontoDeVenda);
        this.latitude = UtilitarioFormatacao.formatarDecimal(pontoDeVenda.getLatitude());
        this.longitude = UtilitarioFormatacao.formatarDecimal(pontoDeVenda.getLongitude());
        this.mdr = mdr;
        this.combustiveis = combustiveis;
    }

    /**
     * Obtém o cnpj do ponto de venda através da lista dos seus componentes.
     * @param ptov ponto de venda
     * @return cnpj da area de abastecimento do ponto de venda.
     */
    private Long obterCnpj(PontoDeVenda ptov) {
        Componente areaAbastecimento = ptov.getComponenteAreaAbastecimento();
        return areaAbastecimento != null ? areaAbastecimento.getCodigoPessoa() : null;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public EnderecoVo getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoVo endereco) {
        this.endereco = endereco;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<CombustivelVo> getCombustiveis() {
        return combustiveis;
    }

    public void setCombustiveis(List<CombustivelVo> combustiveis) {
        this.combustiveis = combustiveis;
    }

    public BigDecimal getMdr() {
        return mdr;
    }

    public void setMdr(BigDecimal mdr) {
        this.mdr = mdr;
    }
}
