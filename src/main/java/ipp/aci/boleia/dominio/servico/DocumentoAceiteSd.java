package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IDocumentoAceiteDados;
import ipp.aci.boleia.dados.IUsuarioDados;
import ipp.aci.boleia.dominio.Documento;
import ipp.aci.boleia.dominio.DocumentoAceite;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.vo.DocumentoAceiteVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Implementa as regras de negocio relacionadas a entidade Usuário
 */
@Component
public class DocumentoAceiteSd {


    @Autowired
    private UtilitarioAmbiente utilitarioAmbiente;

    @Autowired
    private IDocumentoAceiteDados repositorio;

    @Autowired
    private IUsuarioDados repositorioUsuario;

    /**
     * Obtém a quantidade total de aceites de um documento
     *
     * @param documento O documento selecionado
     * @return Quantidade total de aceites
     */
    public long obterQuantidadeAceites(Documento documento){
        return repositorio.obterQuantidadeAceitesDocumento(documento);
    }

    /**
     * Registra o aceite ou a recusa do documento pelo usuário
     *
     * @param documento O documento selecionado
     * @param usuario O usuario selecionado
     * @param aceite Vo com as informações referentes ao aceite do usuário e o sistema em que a operação foi realizada
     * @return Quantidade total de aceites
     */
    public DocumentoAceite registrarAceite(Documento documento, Usuario usuario, DocumentoAceiteVo aceite) {
        DocumentoAceite documentoAceite = Optional.ofNullable(repositorio.obterAceite(documento, usuario))
                .orElse(new DocumentoAceite());
        Usuario usuarioArmazenado = repositorioUsuario.obterPorId(usuario.getId());
        documentoAceite.setUsuario(usuarioArmazenado);
        documentoAceite.setDocumento(documento);
        documentoAceite.setUsuarioAceitou(aceite.getHouveAceite());
        documentoAceite.setSistema(aceite.getIdSistema());
        documentoAceite.setDataAceite(utilitarioAmbiente.buscarDataAmbiente());
        return repositorio.armazenar(documentoAceite);
    }

    /**
     * Obtém o aceite ou a recusa do documento pelo usuário
     *
     * @param documento O documento selecionado
     * @param usuario O usuario selecionado
     * @return O DocumentoAceite que representa o aceite
     */
    public DocumentoAceite obterAceite(Documento documento, Usuario usuario) {
        return repositorio.obterAceite(documento, usuario);
    }
}