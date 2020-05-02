package ipp.aci.boleia.dominio.enums;


import com.fasterxml.jackson.annotation.JsonCreator;
import ipp.aci.boleia.dominio.enums.campanha.IEnumComValorCampanha;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import org.apache.commons.lang3.StringUtils;

/**
 * Indica os tipos de porte de empresas baseado na quantidade de funcionários
 */
public enum PorteEmpresa implements IEnumComValorCampanha<PorteEmpresa> {

    MICRO(0),
    PEQUENA(1),
    MEDIA(2),
    GRANDE(3);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    PorteEmpresa(Integer value) {
        this.value = value;
    }

    /**
     * Constrói uma instância da entidade de acordo com o código para o serializador JSON
     * @param codigo O valor para qual se deseja obter a representação
     * @return A enumeração correspondente ao código
     * @throws ExcecaoValidacao Se o código não existe
     */
    @JsonCreator
    public static PorteEmpresa forValue(String codigo) throws ExcecaoValidacao {
        if(codigo == null){
            throw new ExcecaoValidacao(Erro.JSON_MAL_FORMADO);
        }
        if (StringUtils.isBlank(codigo)){
            return null;
        }
        for (PorteEmpresa e : PorteEmpresa.values()) {
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
    public static PorteEmpresa obterPorValor(Integer value) {
        for(PorteEmpresa status : PorteEmpresa.values()) {
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
    public static PorteEmpresa obterPorValor(String value) {
        return obterPorValor(Integer.parseInt(value));
    }

    @Override
    public String obterValor() {
        return this.value.toString();
    }}
