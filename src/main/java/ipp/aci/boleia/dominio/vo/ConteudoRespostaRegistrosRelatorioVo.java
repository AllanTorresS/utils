package ipp.aci.boleia.dominio.vo;

import java.util.List;

/**
 * Representa o conteúdo de um response de integração
 * entre módulos do Pró-Frotas para obtenção de registros para relatórios
 *
 * @param <T> O tipo da lista de registros
 */
public class ConteudoRespostaRegistrosRelatorioVo<T> extends RespostaIntegracaoVo implements IRespostaModuloInternoVo {
    private List<T> registros;
    private Integer totalRegistros;
    private String cabecalhoDinamico;

    public ConteudoRespostaRegistrosRelatorioVo() {
        //construtor padrão
    }

    /**
     * Construtor para obtenção de registros de relatório
     * @param registros Lista de registros
     * @param totalRegistros Número total de registros contabilizando todos os lotes de dados a serem processados
     */
    public ConteudoRespostaRegistrosRelatorioVo(List<T> registros, Integer totalRegistros) {
        this.registros = registros;
        this.totalRegistros = totalRegistros;
    }

    /**
     * Construtor para obtenção de cabeçalho dinâmico de relatórios
     * @param cabecalhoDinamico A string a ser usada como cabeçalho
     */
    public ConteudoRespostaRegistrosRelatorioVo(String cabecalhoDinamico) {
        this.cabecalhoDinamico = cabecalhoDinamico;
    }

    public List<T> getRegistros() {
        return registros;
    }

    public void setRegistros(List<T> registros) {
        this.registros = registros;
    }

    public Integer getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(Integer totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public String getCabecalhoDinamico() {
        return cabecalhoDinamico;
    }

    public void setCabecalhoDinamico(String cabecalhoDinamico) {
        this.cabecalhoDinamico = cabecalhoDinamico;
    }
}
