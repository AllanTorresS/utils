package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa a tabela de Permissao
 */
@Entity
@Audited
@Table(name = "PERMISSAO")
public class Permissao implements IPersistente {

	private static final long serialVersionUID = -6226038120046024032L;

    @Id
    @Column(name = "CD_PERMISSAO")
    private Long id;

    @NotNull
    @Size(max=50)
    @Column(name = "NM_PERMISSAO")
    private String nome;

    @NotNull
    @Size(max=50)
    @Column(name = "NM_CHAVE_PERMISSAO")
    private String chave;

    @NotNull
    @Column(name = "ID_FROTISTA")
    private boolean portalFrotista;

    @NotNull
    @Column(name = "ID_REVENDEDOR")
    private boolean portalRevendedor;

    @NotNull
    @Column(name = "ID_SOLUCAO")
    private boolean portalSolucao;

    @NotNull
    @Column(name = "ID_PRECOS")
    private boolean portalPrecos;

    @NotNull
    @Column(name = "ID_MOTORISTA")
    private boolean portalMotorista;

    @NotNull
    @Column(name = "ID_EXTERNO")
    private boolean sistemaExterno;

    @NotNull
    @Column(name = "ID_PERMISSAO_FROTA")
    private boolean permissaoFrota;

	@Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public boolean isPortalFrotista() {
        return portalFrotista;
    }

    public void setPortalFrotista(boolean portalFrotista) {
        this.portalFrotista = portalFrotista;
    }

    public boolean isPortalRevendedor() {
        return portalRevendedor;
    }

    public void setPortalRevendedor(boolean portalRevendedor) {
        this.portalRevendedor = portalRevendedor;
    }

    public boolean isPortalSolucao() {
        return portalSolucao;
    }

    public void setPortalSolucao(boolean portalSolucao) {
        this.portalSolucao = portalSolucao;
    }

    public boolean isPortalPrecos() {
        return portalPrecos;
    }

    public void setPortalPrecos(boolean portalPrecos) {
        this.portalPrecos = portalPrecos;
    }

    public boolean isPortalMotorista() {
        return portalMotorista;
    }

    public void setPortalMotorista(boolean portalMotorista) {
        this.portalMotorista = portalMotorista;
    }

    public boolean isSistemaExterno() {
        return sistemaExterno;
    }

    public void setSistemaExterno(boolean sistemaExterno) {
        this.sistemaExterno = sistemaExterno;
    }

    public boolean isPermissaoFrota() {
		return permissaoFrota;
	}

	public void setPermissaoFrota(boolean permissaoFrota) {
		this.permissaoFrota = permissaoFrota;
	}

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((chave == null) ? 0 : chave.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
		if (obj == null) {
            return false;
        }
		if (getClass() != obj.getClass()) {
            return false;
        }
		Permissao other = (Permissao) obj;
		if (chave == null) {
			if (other.chave != null) {
                return false;
            }
		} else if (!chave.equals(other.chave)) {
            return false;
        }
		return true;
	}

}