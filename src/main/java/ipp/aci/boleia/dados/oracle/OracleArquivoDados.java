package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IArquivoDados;
import ipp.aci.boleia.dominio.Arquivo;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades de
 * Arquivo
 */
@Repository
public class OracleArquivoDados extends OracleRepositorioBoleiaDados<Arquivo> implements IArquivoDados {

	/**
	 * Instancia o repositorio
	 */
	public OracleArquivoDados() {
		super(Arquivo.class);
	}
}
