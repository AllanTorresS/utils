package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import java.util.List;

/**
 * Classe com informações referentes de uma paginação
 */
public class PaginacaoVo <T> {
    private List<T> itens;
    private Integer pagina;
    private Integer tamanho;
    private Integer total;

    public PaginacaoVo(){
        //contrutor default
    }

    public PaginacaoVo(List<T> itens, int pagina, int tamanho, int total){
        this.itens = itens;
        this.pagina = pagina;
        this.tamanho = tamanho;
        this.total = total;
    }

    public List<T> getItens() {
        return itens;
    }

    public void setItens(List<T> itens) {
        this.itens = itens;
    }

    public Integer getPagina() {
        return pagina;
    }

    public void setPagina(Integer pagina) {
        this.pagina = pagina;
    }

    public Integer getTamanho() {
        return tamanho;
    }

    public void setTamanho(Integer tamanho) {
        this.tamanho = tamanho;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
