package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de coordenadorias
 */
public class FiltroPesquisaCoordenadoriaVo extends BaseFiltroPaginado {
    private String nome;
    private EnumVo status;
    private String nomeLoginCoordenador;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
    }

    public String getNomeLoginCoordenador() {
        return nomeLoginCoordenador;
    }

    public void setNomeLoginCoordenador(String nomeLoginCoordenador) {
        this.nomeLoginCoordenador = nomeLoginCoordenador;
    }
}
