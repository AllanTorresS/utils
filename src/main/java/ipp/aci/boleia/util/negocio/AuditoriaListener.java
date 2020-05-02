package ipp.aci.boleia.util.negocio;

import ipp.aci.boleia.dominio.EntidadeAuditoria;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.util.seguranca.ProvedorAutenticacao;
import org.hibernate.envers.RevisionListener;

/**
 * Interceptador do envers para setar campos extras de auditoria
 */
public class AuditoriaListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        EntidadeAuditoria entidadeAuditoria = (EntidadeAuditoria) revisionEntity;
        Usuario usuario = ProvedorAutenticacao.getUsuarioLogado();
        if(usuario!=null) {
            entidadeAuditoria.setUsuario(usuario.obterIdentificadorAuditoria());
        }
        entidadeAuditoria.setIp(ProvedorAutenticacao.getIpOrigemRequisicao());
    }
}
