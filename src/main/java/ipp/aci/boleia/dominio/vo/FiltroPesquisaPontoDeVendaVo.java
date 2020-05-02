package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de pontos de venda
 */
public class FiltroPesquisaPontoDeVendaVo extends BaseFiltroPaginado {

    private String id;
    private EntidadeVo nomeCnpj;
    private String cidade;
    private EnumVo uf;
    private EnumVo status;
    private EnumVo situacao;
    private EnumVo fotos;
    private String revendedor;
    private String idUsuario;
    private String idFrota;
    private EntidadeVo rede;
    private EnumVo permissaoPrecoPosto;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public EntidadeVo getNomeCnpj() {
        return nomeCnpj;
    }

    public void setNomeCnpj(EntidadeVo nomeCnpj) {
        this.nomeCnpj = nomeCnpj;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public EnumVo getUf() {
        return uf;
    }

    public void setUf(EnumVo uf) {
        this.uf = uf;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
    }

    public EnumVo getFotos() {
        return fotos;
    }

    public void setFotos(EnumVo fotos) {
        this.fotos = fotos;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(String idFrota) {
        this.idFrota = idFrota;
    }

    public EnumVo getSituacao() {
        return situacao;
    }

    public void setSituacao(EnumVo situacao) {
        this.situacao = situacao;
    }

    public String getRevendedor() {
        return revendedor;
    }

    public void setRevendedor(String revendedor) {
        this.revendedor = revendedor;
    }

    public EntidadeVo getRede() {
        return rede;
    }

    public void setRede(EntidadeVo rede) {
        this.rede = rede;
    }

    public EnumVo getPermissaoPrecoPosto() {
        return permissaoPrecoPosto;
    }

    public void setPermissaoPrecoPosto(EnumVo permissaoPrecoPosto) {
        this.permissaoPrecoPosto = permissaoPrecoPosto;
    }
}
