package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de motoristas
 */
public class FiltroPesquisaRotaVo extends BaseFiltroPaginado {

    private String nome;
    private String origem;
    private String destino;
    private Boolean possuiPostos;
    private EntidadeVo pontoVenda;
    private Boolean rotaInteligente;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public Boolean getPossuiPostos() {
        return possuiPostos;
    }

    public void setPossuiPostos(Boolean possuiPostos) {
        this.possuiPostos = possuiPostos;
    }

    public EntidadeVo getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(EntidadeVo pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public Boolean getRotaInteligente() { return rotaInteligente; }

    public void setRotaInteligente(Boolean rotaInteligente) { this.rotaInteligente = rotaInteligente; }

}
