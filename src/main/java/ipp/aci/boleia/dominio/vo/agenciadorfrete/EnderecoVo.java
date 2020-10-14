package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.util.UtilitarioFormatacao;

/**
 * Classe com informações do endereço
 */
public class EnderecoVo {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String municipio;
    private String uf;


    public EnderecoVo() {
        // serializacao json
    }

    /**
     * Monta um DTO com os dados de endereço de um ponto de venda
     *
     * @param pv O ponto de venda
     */
    public EnderecoVo (PontoDeVenda pv) {
        this.bairro = pv.getBairro();
        this.cep = UtilitarioFormatacao.formatarCepApresentacao(pv.getCep());
        this.complemento = pv.getComplementoEndereco();
        this.logradouro = pv.getLogradouroEndereco();
        this.municipio = pv.getMunicipio();
        this.numero = pv.getNumeroEndereco()!=null ? pv.getNumeroEndereco().toString() : null;
        this.uf = pv.getUf();
    }


    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }
}
