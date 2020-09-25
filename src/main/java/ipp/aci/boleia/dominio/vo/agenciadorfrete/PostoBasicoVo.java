package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.util.UtilitarioFormatacao;

/**
* Classe com informações referentes do posto de um extrato de motorista autonomo
*/
public class PostoBasicoVo {
    private String nome;
    private String cnpj;

    public PostoBasicoVo() {
        //construtor default
    }

    public PostoBasicoVo(PontoDeVenda posto) {
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
