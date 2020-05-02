package ipp.aci.boleia.dominio.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.IEnumComLabel;
import org.apache.commons.lang3.StringUtils;

/**
 * Indica o status de um contrato da Frota
 */
public enum StatusContrato implements IEnumComLabel<StatusContrato> {

    NAO_SE_APLICA(2),
    ASSINADO(0),
    ENCERRADO(1),
    VENCIDO(3);

    public static final String DECODE_FORMULA = "DECODE(CD_STATUS_CNTR, 0, 'ASS', 1, 'ENCER', 2, '', 3, 'VENC')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do status
     */
    StatusContrato(Integer value) {
        this.value = value;
    }

    /**
     * Constrói uma instância da entidade de acordo com o código para o serializador JSON
     * @param codigo O valor para qual se deseja obter a representação
     * @return A enumeração correspondente ao código
     * @throws ExcecaoValidacao Se o código não existe
     */
    @JsonCreator
    public static StatusContrato forValue(String codigo) throws ExcecaoValidacao {
        if (codigo == null) {
            throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO);
        }
        if (StringUtils.isBlank(codigo)){
            return null;
        }
        if (!NAO_SE_APLICA.value.toString().equals(codigo)) {
            for (StatusContrato e : StatusContrato.values()) {
                if (e.value.toString().equals(codigo) || e.name().equals(codigo)) {
                    return e;
                }
            }
        }
        throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO_CAMPO, codigo);
    }

    /**
     * Obtem o valor do status
     * @return o valor do status
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static StatusContrato obterPorValor(Integer value) {
        for (StatusContrato status : StatusContrato.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
