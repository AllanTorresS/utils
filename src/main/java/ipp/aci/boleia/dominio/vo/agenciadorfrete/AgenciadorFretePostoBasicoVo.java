package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.validador.ValidadorCnpj;

import javax.validation.constraints.NotNull;

/**
* Classe com informações referentes do posto de um extrato de motorista autonomo
*/
public class AgenciadorFretePostoBasicoVo {
    private String nome;
    @NotNull
    private String cnpj;

    public AgenciadorFretePostoBasicoVo() {
        //construtor default
    }

    public AgenciadorFretePostoBasicoVo(PontoDeVenda posto) {
        if(posto!=null) {
            this.nome = posto.getNome();
            this.cnpj = UtilitarioFormatacao.formatarNumeroZerosEsquerda(posto.getCnpj(), UtilitarioFormatacao.TAMANHO_CNPJ);
        }
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
}
