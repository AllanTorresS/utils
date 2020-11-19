package ipp.aci.boleia.dominio.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.IEnumComLabel;
import org.apache.commons.lang3.StringUtils;

/**
 * Indica o status de uma Frota na conectcar
 */
public enum StatusFrotaConectcar implements IEnumComLabel<StatusFrotaConectcar> {

	ATIVO(1),
    INATIVO(0);    

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusFrotaConectcar(Integer value) {
        this.value = value;
    }

    /**
     * Constrói uma instância da entidade de acordo com o código para o serializador JSON
     * @param codigo O valor para qual se deseja obter a representação
     * @return A enumeração correspondente ao código
     * @throws ExcecaoValidacao Se o código não existe
     */
    @JsonCreator
    public static StatusFrotaConectcar forValue(String codigo) throws ExcecaoValidacao {
        if(codigo == null){
            throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO);
        }
        if (StringUtils.isBlank(codigo)){
            return null;
        }
        for (StatusFrotaConectcar e : StatusFrotaConectcar.values()) {
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
    public static StatusFrotaConectcar obterPorValor(Integer value) {
        for (StatusFrotaConectcar status : StatusFrotaConectcar.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
