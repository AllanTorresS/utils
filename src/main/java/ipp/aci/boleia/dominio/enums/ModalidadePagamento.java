 package ipp.aci.boleia.dominio.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import ipp.aci.boleia.dominio.enums.campanha.IEnumComValorCampanha;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import org.apache.commons.lang3.StringUtils;

/**
 * Indica os tipos de modalidades de pagamento
 */
public enum ModalidadePagamento implements IEnumComValorCampanha<ModalidadePagamento> {

    PRE_PAGO(0),
    POS_PAGO(1);

    public static final String DECODE_FORMULA = "DECODE(CD_MOD_PGTO, 0, 'PRE', 1, 'POS')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    ModalidadePagamento(Integer value) {
        this.value = value;
    }

    /**
     * Constrói uma instância da entidade de acordo com o código para o serializador JSON
     * @param codigo O valor para qual se deseja obter a representação
     * @return A enumeração correspondente ao código
     * @throws ExcecaoValidacao Se o código não existe
     */
    @JsonCreator
    public static ModalidadePagamento forValue(String codigo) throws ExcecaoValidacao {
        if(codigo == null){
            throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO);
        }
        if (StringUtils.isBlank(codigo)){
            return null;
        }
        for (ModalidadePagamento e : ModalidadePagamento.values()) {
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
    public static ModalidadePagamento obterPorValor(Integer value) {
        for(ModalidadePagamento status : ModalidadePagamento.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static ModalidadePagamento obterPorValor(String value) {
        return obterPorValor(Integer.parseInt(value));
    }

    @Override
    public String obterValor() {
        return this.value.toString();
    }
}
