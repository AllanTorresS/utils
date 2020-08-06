package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.TipoAtividadeComponente;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.List;

/**
 * Representa a tabela de Atividade Componente
 */
@Entity
@Audited
@Table(name = "ATIVIDADE_COMPONENTE")
public class AtividadeComponente implements IPersistente {

    private static final long serialVersionUID = 4827215351632681697L;

    @Id
    @Column(name = "CD_ATIV_COMP")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ATIVIDADE_COMPONENTE")
    @SequenceGenerator(name = "SEQ_ATIVIDADE_COMPONENTE", sequenceName = "SEQ_ATIVIDADE_COMPONENTE", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max=2)
    @Column(name = "CD_ATIV_COMP_CORP")
    private String codigoCorporativo;

    @NotNull
    @Size(max=30)
    @Column(name = "DS_ATIV_COMP")
    private String descricao;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoCorporativo() {
        return codigoCorporativo;
    }

    public void setCodigoCorporativo(String codigoCorporativo) {
        this.codigoCorporativo = codigoCorporativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    /**
     * Lista contendo as constantes de area de abastecimentos possiveis,
     * de acordo com os dados importados via ETL da Ipiranga
     * @return lista de area de abastecimentos
     */
    public static List<String> obterCodigosAreaAbastecimento(){
        return Arrays.asList(TipoAtividadeComponente.AREA_ABASTECIMENTO.getCodigoCorporativo(),
                TipoAtividadeComponente.AREA_ABASTECIMENTO_OUTRA.getCodigoCorporativo());
    }
}
