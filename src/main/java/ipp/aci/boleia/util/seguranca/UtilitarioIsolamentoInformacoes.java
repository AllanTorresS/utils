package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Motorista;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceMotorista;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Prove recursos para garantia de isolamento de dados entre os utilizadores do sistema
 */
public final class UtilitarioIsolamentoInformacoes {

    private static final Logger LOGGER = LoggerFactory.getLogger(UtilitarioIsolamentoInformacoes.class);

    /**
     * Impede instanciacao e heranca
     */
    private UtilitarioIsolamentoInformacoes() {
        // impede instanciacao e heranca
    }

    /**
     * Exige que um usuario possua direitos de manipulacao sobre uma da entidade
     *
     * @param persistente A entidade em questao
     * @param usuario     o usuario em questao
     */
    public static void exigirPermissaoAcesso(IPersistente persistente, Usuario usuario) {
        verificarPermissaoAcessoFrota(persistente, usuario);
        verificarPermissaoAcessoRevenda(persistente, usuario);
        verificarPermissaoAcessoMotorista(persistente, usuario);
        verificarPermissaoAcessoAssessorOuCoordenador(persistente, usuario);
    }

    /**
     * Exige que um usuario possua direitos de manipulacao sobre uma da entidade
     *
     * @param persistentes Uma lista de entidades
     * @param usuario      o usuario em questao
     */
    public static void exigirPermissaoAcesso(List<IPersistente> persistentes, Usuario usuario) {
        if (persistentes != null) {
            for (IPersistente persistente : persistentes) {
                exigirPermissaoAcesso(persistente, usuario);
            }
        }
    }

    /**
     * Exige que um usuario possua direitos de manipulacao sobre uma da entidade
     *
     * @param id O identificador da entidade
     * @param classeEntidade O tipo da entidade
     * @param usuario o usuario em questao
     * @param gerenciadorDeEntidade O gerenciador da entidade
     * @param <T> O tipo da entidade alvo
     */
    public static <T extends IPersistente> void exigirPermissaoAcesso(Long id, Class<T> classeEntidade, Usuario usuario, EntityManager gerenciadorDeEntidade) {
        IPersistente persistente = gerenciadorDeEntidade.find(classeEntidade, id);
        exigirPermissaoAcesso(persistente, usuario);
    }

    /**
     * Retorna true caso a entidade e o usuario pertencam ao mesmo revendedor (ponto de venda)
     *
     * @param persistente A entidade
     * @param usuario     O usuario
     * @return true caso pertencam ao mesmo ponto de venda
     */
    private static boolean verificarMesmoRevendedor(IPertenceRevendedor persistente, Usuario usuario) {
        List<PontoDeVenda> pvsEntidade = persistente.getPontosDeVenda();
        List<PontoDeVenda> pvsUsuario = usuario.getPontosDeVenda();

        if(pvsUsuario == null || pvsUsuario.isEmpty() || pvsEntidade == null || pvsEntidade.isEmpty()) {
            return false;
        }

        for (PontoDeVenda pvUsuario : pvsUsuario) {
            for(PontoDeVenda pvEntidade : pvsEntidade) {
                if (pvUsuario != null && pvEntidade != null && pvUsuario.getId().equals(pvEntidade.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Retorna true caso a entidade e o usuario pertencam a mesma frota
     *
     * @param persistente a entidade
     * @param usuario     o usuario
     * @return true caso pertencam a mesma frota
     */
    private static boolean verificarMesmaFrota(IPertenceFrota persistente, Usuario usuario) {
        List<Frota> frotasEntidade = persistente.getFrotas();
        Frota frotaUsuario = usuario.getFrota();

        if(frotaUsuario == null || frotasEntidade == null || frotasEntidade.isEmpty()) {
            return false;
        }

        for (Frota frotaEntidade : frotasEntidade) {
            if (frotaEntidade != null && frotaUsuario.getId().equals(frotaEntidade.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retorna true caso a entidade e o usuario pertencam ao mesmo motorista
     *
     * @param persistente a entidade
     * @param usuario     o usuario
     * @return true caso pertencam ao mesmo motorista
     */
    private static boolean verificarMesmoMotorista(IPertenceMotorista persistente, Usuario usuario) {
        Motorista motorista = persistente.getMotorista();
        return motorista == null || usuario.getMotorista().getId().equals(motorista.getId());
    }


    /**
     * Lanca um erro informando que houve uma tentativa de violacao do isolamento de dados
     * @param persistente A entidade sendo manipulada
     * @param usuario O usuario logado
     */
    private static void lancarErro(IPersistente persistente, Usuario usuario) {

        LOGGER.error(
                "Isolamento de dados violado! A entidade {} com id={} não pertence ao usuário {}",
                persistente.getClass().getSimpleName(), persistente.getId(), usuario.getEmail()
        );

        throw new ExcecaoBoleiaRuntime(Erro.VIOLACAO_ISOLAMENTO_DADOS);
    }

    /**
     * Verifica se o isolamento de dados entre motoristas esta sendo respeitado
     * @param persistente A entidade persistente alvo
     * @param usuario O usuario solicitante
     */
    private static void verificarPermissaoAcessoMotorista(IPersistente persistente, Usuario usuario) {
        if (persistente instanceof IPertenceMotorista && usuario != null && usuario.getMotorista() != null) {
            if (!verificarMesmoMotorista((IPertenceMotorista) persistente, usuario)) {
                lancarErro(persistente, usuario);
            }
        }
    }

    /**
     * Verifica se o isolamento de dados entre revendedores esta sendo respeitado
     * @param persistente A entidade persistente alvo
     * @param usuario O usuario solicitante
     */
    private static void verificarPermissaoAcessoRevenda(IPersistente persistente, Usuario usuario) {
        if (persistente instanceof IPertenceRevendedor && usuario != null && usuario.isRevendedor()) {
            if (usuario.getRede() == null || !verificarMesmoRevendedor((IPertenceRevendedor) persistente, usuario)) {
                lancarErro(persistente, usuario);
            }
        }
    }

    /**
     * Verifica se o isolamento de dados entre frotistas esta sendo respeitado
     * @param persistente A entidade persistente alvo
     * @param usuario O usuario solicitante
     */
    private static void verificarPermissaoAcessoFrota(IPersistente persistente, Usuario usuario) {
        if (persistente instanceof IPertenceFrota && usuario != null && usuario.isFrotista()) {
            if (usuario.getFrota() == null || !verificarMesmaFrota((IPertenceFrota) persistente, usuario)) {
                lancarErro(persistente, usuario);
            }
        }
    }

    /**
     * Verifica se o isolamento de dados de assessor/coordenador esta sendo respeitado
     * @param persistente A entidade persistente alvo
     * @param usuario O usuario solicitante
     */
    private static void verificarPermissaoAcessoAssessorOuCoordenador(IPersistente persistente, Usuario usuario) {
        if (persistente instanceof IPertenceFrota && usuario != null && usuario.isInterno()) {
            if (usuario.possuiFrotasAssociadas() && !verificarFrotaAssociada((IPertenceFrota) persistente, usuario)) {
                lancarErro(persistente, usuario);
            }
        }
    }

    /**
     * Retorna true caso a entidade e o usuario pertencam a mesma frota
     *
     * @param persistente a entidade
     * @param usuario     o usuario
     * @return true caso pertencam a mesma frota
     */
    private static boolean verificarFrotaAssociada(IPertenceFrota persistente, Usuario usuario) {
        final List<Frota> frotasEntidade = persistente.getFrotas();
        final List<Long> idsFrotasAssociadas = usuario.listarIdsFrotasAssociadas();

        if (CollectionUtils.isEmpty(frotasEntidade) || CollectionUtils.isEmpty(idsFrotasAssociadas)) {
            return false;
        }

        for (Frota frotaEntidade : frotasEntidade) {
            for (Long idFrotas : idsFrotasAssociadas) {
                if (frotaEntidade != null && idFrotas.equals(frotaEntidade.getId())) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Verifica se o usuário interno é do tipo assessor ou coordenador para não segregar
     * @param usuarioLogado
     * @return true caso o usuário interno seja assessor ou coordenador
     */
    public static boolean isUsuarioInternoAssessorOuCoordenador(Usuario usuarioLogado) {
        return usuarioLogado.isInterno() && usuarioLogado.isAssessor() || usuarioLogado.isInterno() && usuarioLogado.isCoordenador();
    }
}
