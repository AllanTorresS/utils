package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import ipp.aci.boleia.dominio.interfaces.IPersistente;

/**
 * Representa uma entidade a qual se faz referencia apenas pelo seu ID
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class EntidadeVo {

    private Long id;
    private String nome;

    public EntidadeVo(){
        // serializacao json
    }

    /**
     * Gera uma nova entidade com o ID dado
     * @param id O ID da entidade
     */
    public EntidadeVo(Long id) {
        this.id = id;
    }

    /**
     * Gera uma nova entidade com ID e nome dados
     * @param id O ID da entidade
     * @param nome O nome da entidade
     */
    public EntidadeVo(Long id, String nome) {
        this(id);
        this.nome = nome;
    }

    /**
     * Gera uma nova entidade com o ID de uma entidade persistente dada
     * @param p A entidade persistente
     */
    public EntidadeVo(IPersistente p) {
        this.id = p != null ? p.getId() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    /**
     * Método que obtém nome de uma entidade
     * @param vo Instancia do VO
     * @return Resultado. Null caso VO seja null
     */
    public static String obterNome(EntidadeVo vo) {
        return vo != null && vo.getId() != null  && vo.getId() != -1 ? vo.getNome() : null;
    }
}
