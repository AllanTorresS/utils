package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Cobranca;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Classe utilizada para geração do relatório de conciliação contábil de cobranças e reembolsos
 */
public class ConciliacaoCobrancasVo {

    private String dataBase;
    private List<ConciliacaoCobrancasFrotaRevendedorVo> consolidadoFrotaRevendedor;
    private List<ConciliacaoCobrancasRevendedorVo> consolidadoRevendedor;

    /**
     * Gera a conciliacao de um grupo de cobrancas
     * @param dataBase A data base considerada
     * @param cobrancas A lista de cobrancas
     */
    public ConciliacaoCobrancasVo(Date dataBase, List<Cobranca> cobrancas){
        this.consolidadoFrotaRevendedor = new ArrayList<>();
        this.consolidadoRevendedor = new ArrayList<>();
        this.dataBase = UtilitarioFormatacaoData.formatarDataCurta(dataBase);

        this.consolidadoFrotaRevendedor = cobrancas.stream()
                .flatMap(trans ->
                    trans.getTransacoesConsolidadas().stream()
                            .filter(tc -> tc.getValores().getValorTotal().compareTo(BigDecimal.ZERO) > 0)
                            .map(ConciliacaoCobrancasFrotaRevendedorVo::new))
                .collect(Collectors.toList());

        Map<String, List<ConciliacaoCobrancasFrotaRevendedorVo>> consolidadoPorRevendedor =
                consolidadoFrotaRevendedor.stream()
                        .collect(Collectors.groupingBy(ConciliacaoCobrancasFrotaRevendedorVo::getPostoRevendedor));

        consolidadoPorRevendedor.forEach((k,v) ->
            this.consolidadoRevendedor.add(new ConciliacaoCobrancasRevendedorVo(
                    v.get(0).getPostoRevendedor(),
                    v.get(0).getDescricaoPostoRevendedor(),
                    v.stream().map(ConciliacaoCobrancasFrotaRevendedorVo::getValor).reduce(BigDecimal.ZERO, BigDecimal::add),
                    v.stream().map(ConciliacaoCobrancasFrotaRevendedorVo::getValorMdr).reduce(BigDecimal.ZERO, BigDecimal::add))
           )
        );
    }

    public ConciliacaoCobrancasVo(){
        // serializacao json
    }

    public String getDataBase() {
        return dataBase;
    }

    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }

    public List<ConciliacaoCobrancasFrotaRevendedorVo> getConsolidadoFrotaRevendedor() {
        return consolidadoFrotaRevendedor;
    }

    public void setConsolidadoFrotaRevendedor(List<ConciliacaoCobrancasFrotaRevendedorVo> consolidadoFrotaRevendedor) {
        this.consolidadoFrotaRevendedor = consolidadoFrotaRevendedor;
    }

    public List<ConciliacaoCobrancasRevendedorVo> getConsolidadoRevendedor() {
        return consolidadoRevendedor;
    }

    public void setConsolidadoRevendedor(List<ConciliacaoCobrancasRevendedorVo> consolidadoRevendedor) {
        this.consolidadoRevendedor = consolidadoRevendedor;
    }
}
