package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.IEnumComValor;
import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * VO para apresentacao de tipos enumerados
 */
public class EnumVo {

    private String name;
    private String label;
    private Integer value;

    /**
     * Construtr default, necessario para a serializacao JSON
     */
    public EnumVo() {
        // necessario para a serializacao JSON
    }

    /**
     * Construtor parametrizado, para agilidade na codificacao.
     *
     * @param e   O valor enumerado
     * @param <E> A classe da enumeracao
     */
    public <E extends IEnumComLabel> EnumVo(E e) {
        this.setName(e.toString());
        this.label = e.getLabel();
        if(e instanceof IEnumComValor) {
            this.value = ((IEnumComValor) e).getValue();
        }
    }

    /**
     * Retorna o nome do valor enumerado (enum.name)
     *
     * @return O nome do valor enumerado
     */
    public String getName() {
        return name;
    }

    /**
     * Atribui o nome do valor enumerado (enum.name)
     *
     * @param name O nome
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retorna o label do valor enumerado (enum.label)
     *
     * @return O label do valor enumerado
     */
    public String getLabel() {
        return label;
    }

    /**
     * Atribui o label do valor enumerado (enum.label)
     *
     * @param label O label
     */
    public void setLabel(String label) {
        this.label = label;
    }

      /**
     * Retorna o valor do enumerado (enum.value)
     *
     * @return O valor do enumerado
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Atribui o valor do enumerado (enum.valor)
     *
     * @param value O valor
     */
    public void setValue(Integer value) {
        this.value = value;
    }

    /**
     * Converte este objeto para o valor enumerado correspondente
     *
     * @param classe A classe da enumeracao
     * @param <E> A classe da enumeracao
     * @return A instancia do valor enumerado
     */
    public <E extends Enum<E>> E fromName(Class<E> classe) {
        E[] values = classe.getEnumConstants();
        for (E value : values) {
            if (value.name().equals(getName())) {
                return value;
            }
        }
        return null;
    }

    /**
     * Converte este objeto para o valor enumerado correspondente
     *
     * @param classe A classe da enumeracao
     * @param vo O Vo
     * @param <E> A classe da enumeracao
     * @return A instancia do valor enumerado
     */
    public static <E extends Enum<E>> E fromName(Class<E> classe, EnumVo vo) {
        return vo != null ? vo.fromName(classe) : null;
    }

}
