package ipp.aci.boleia.dominio.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.IEnumComLabel;
import org.apache.commons.lang3.StringUtils;

/**
 * Indica as categorias de quantidade de veiculo leves e pesados
 */
public enum QuantidadeVeiculos implements IEnumComLabel<QuantidadeVeiculos> {

    ZERO(0),
    MAX_VINTE(1),
    MAX_CINQUENTA(2),
    MAX_CEM(3),
    MAX_QUINHENTOS(4),
    MAX_MIL(5),
    MAIS_DE_MIL(6);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    QuantidadeVeiculos(Integer value) {
        this.value = value;
    }

    /**
     * Constrói uma instância da entidade de acordo com o código para o serializador JSON
     * @param codigo O valor para qual se deseja obter a representação
     * @return A enumeração correspondente ao código
     * @throws ExcecaoValidacao Se o código não existe
     */
    @JsonCreator
    public static QuantidadeVeiculos forValue(String codigo) throws ExcecaoValidacao {
        if(codigo == null){
            throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO);
        }
        if (StringUtils.isBlank(codigo)){
            return null;
        }
        for (QuantidadeVeiculos e : QuantidadeVeiculos.values()) {
            if (e.value.toString().equals(codigo) || e.name().equals(codigo)) {
                return e;
            }
        }
        throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO_CAMPO, codigo);
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static QuantidadeVeiculos obterPorValor(Integer value) {
        for(QuantidadeVeiculos status : QuantidadeVeiculos.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
