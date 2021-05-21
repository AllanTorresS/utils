package ipp.aci.boleia.dominio.vo.salesforce;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

import java.util.Date;
import java.util.List;

/**
 * Filtro de pesquisa da consulta de chamados.
 */
public class FiltroConsultaChamadosVo extends BaseFiltroPaginado {
    private String numeroChamado;
    private Date dataAbertura;
    private String status;
    private List<String> cnpjsContato;
    private String cpfContato;
    private boolean contatoFrota;
    private boolean contatoRevenda;
    private boolean contatoInterno;

    public String getNumeroChamado() {
        return numeroChamado;
    }

    public void setNumeroChamado(String numeroChamado) {
        this.numeroChamado = numeroChamado;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getCnpjsContato() {
        return cnpjsContato;
    }

    public void setCnpjsContato(List<String> cnpjsContato) {
        this.cnpjsContato = cnpjsContato;
    }

    public String getCpfContato() {
        return cpfContato;
    }

    public void setCpfContato(String cpfContato) {
        this.cpfContato = cpfContato;
    }

    public boolean isContatoFrota() {
        return contatoFrota;
    }

    public void setContatoFrota(boolean contatoFrota) {
        this.contatoFrota = contatoFrota;
    }

    public boolean isContatoRevenda() {
        return contatoRevenda;
    }

    public void setContatoRevenda(boolean contatoRevenda) {
        this.contatoRevenda = contatoRevenda;
    }

    public boolean isContatoInterno() {
        return contatoInterno;
    }

    public void setContatoInterno(boolean contatoInterno) {
        this.contatoInterno = contatoInterno;
    }
}
