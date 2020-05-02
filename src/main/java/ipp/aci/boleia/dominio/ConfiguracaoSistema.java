package ipp.aci.boleia.dominio;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa a tabela de Configuracao Sistema
 */
@Audited
@Entity
@Table(name = "CONFIGURACAO_SISTEMA")
public class ConfiguracaoSistema{

    @Id
    @Column(name = "CD_CHAVE_CONFIGURACAO")
    private String chaveConfiguracao;

    @Column(name = "VA_PARAMETRO")
    private String parametro;

    public String getChaveConfiguracao() {
        return chaveConfiguracao;
    }

    public void setChaveConfiguracao(String chaveConfiguracao) {
        this.chaveConfiguracao = chaveConfiguracao;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }
}