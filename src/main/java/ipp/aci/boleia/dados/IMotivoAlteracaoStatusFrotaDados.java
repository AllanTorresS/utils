package ipp.aci.boleia.dados;
import ipp.aci.boleia.dominio.MotivoAlteracaoStatusFrota;

/**
 * Contrato para implementacao de repositório da entidade {@link MotivoAlteracaoStatusFrota}
 */
public interface IMotivoAlteracaoStatusFrotaDados extends IRepositorioBoleiaDados<MotivoAlteracaoStatusFrota> {

	/**
	 * Recupera o registro mais recente de alteração de frota para operações
	 * de inataivação.
	 * @param idFrota o identificador da frota
	 * @return o registro de inativação mais recente
	 */
	MotivoAlteracaoStatusFrota obterUltimaInativacao(Long idFrota);
}
