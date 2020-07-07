package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.List;

/**
 * Filtro para pesquisa de usuario de ponto de venda
 */
public class FiltroPesquisaUsuarioVo extends BaseFiltroPaginado {

    private Long id;
    private String cpf;
    private String nome;
    private String email;
    private EnumVo status;
    private Boolean gestor;
    private EntidadeVo frota;
    private EntidadeVo unidade;
    private EntidadeVo grupoOperacional;
    private EntidadeVo rede;
    private EntidadeVo pontoDeVenda;
    private EntidadeVo perfil;
    private EntidadeVo tipoPerfil;
    private List<Long> pontosDeVenda;
    private EntidadeVo coordenadoria;

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
    }

    public Boolean getGestor() {
        return gestor;
    }

    public void setGestor(Boolean gestor) {
        this.gestor = gestor;
    }

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public EntidadeVo getRede() {
        return rede;
    }

    public void setRede(EntidadeVo rede) {
        this.rede = rede;
    }

    public EntidadeVo getPontoDeVenda() {
        return pontoDeVenda;
    }

    public void setPontoDeVenda(EntidadeVo pontoDeVenda) {
        this.pontoDeVenda = pontoDeVenda;
    }

    public EntidadeVo getUnidade() {
        return unidade;
    }

    public void setUnidade(EntidadeVo unidade) {
        this.unidade = unidade;
    }

    public EntidadeVo getGrupoOperacional() {
        return grupoOperacional;
    }

    public void setGrupoOperacional(EntidadeVo grupoOperacional) {
        this.grupoOperacional = grupoOperacional;
    }

    public EntidadeVo getPerfil() {
        return perfil;
    }

    public void setPerfil(EntidadeVo perfil) {
        this.perfil = perfil;
    }

    public EntidadeVo getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(EntidadeVo tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getPontosDeVenda() {
        return pontosDeVenda;
    }

    public void setPontosDeVenda(List<Long> pontosDeVenda) {
        this.pontosDeVenda = pontosDeVenda;
    }

    public EntidadeVo getCoordenadoria() {
        return coordenadoria;
    }

    public void setCoordenadoria(EntidadeVo coordenadoria) {
        this.coordenadoria = coordenadoria;
    }
}
