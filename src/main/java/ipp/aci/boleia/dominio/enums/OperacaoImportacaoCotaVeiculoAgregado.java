package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Representa o tipo de Operação da importação de cota de veículo agregado.
 */
public enum OperacaoImportacaoCotaVeiculoAgregado implements IEnumComLabel<OperacaoImportacaoCotaVeiculoAgregado> {

    ADICIONAR(0),
    REMOVER(1),
    REDEFINIR(2);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value da operação
     */
    OperacaoImportacaoCotaVeiculoAgregado(Integer value){
        this.value = value;
    }

    public Integer getValue() {
        return  value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static OperacaoImportacaoCotaVeiculoAgregado obterPorValor(Integer value){
        for(OperacaoImportacaoCotaVeiculoAgregado op : OperacaoImportacaoCotaVeiculoAgregado.values()){
            if(op.value.equals(value)){
                return op;
            }
        }
        return null;
    }
}
