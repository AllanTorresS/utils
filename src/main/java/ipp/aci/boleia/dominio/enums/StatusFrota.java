package ipp.aci.boleia.dominio.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.IEnumComLabel;
import org.apache.commons.lang3.StringUtils;

/**
 * Indica o status de uma Frota
 */
public enum StatusFrota implements IEnumComLabel<StatusFrota> {

    PRE_CADASTRO( -1),
    INATIVO(0),
    ATIVO(1);

    public static final String DECODE_FORMULA = "DECODE(ID_STATUS, -1, 'PRE-CADASTRO', 0, 'INATIVO', 1, 'ATIVO')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusFrota(Integer value) {
        this.value = value;
    }

    /**
     * Constrói uma instância da entidade de acordo com o código para o serializador JSON
     * @param codigo O valor para qual se deseja obter a representação
     * @return A enumeração correspondente ao código
     * @throws ExcecaoValidacao Se o código não existe
     */
    @JsonCreator
    public static StatusFrota forValue(String codigo) throws ExcecaoValidacao {
        if(codigo == null){
            throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO);
        }
        if (StringUtils.isBlank(codigo)){
            return null;
        }
        for (StatusFrota e : StatusFrota.values()) {
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
     *
     * @param value value
     * @return Enum para o valor
     */
    public static StatusFrota obterPorValor(Integer value) {
        for (StatusFrota status : StatusFrota.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
