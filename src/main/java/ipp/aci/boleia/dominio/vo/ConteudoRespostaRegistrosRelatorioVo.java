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

    public ConteudoRespostaRegistrosRelatorioVo() {
        //construtor padrão
    }

    public ConteudoRespostaRegistrosRelatorioVo(List<T> registros, Integer totalRegistros) {
        this.registros = registros;
        this.totalRegistros = totalRegistros;
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
}
