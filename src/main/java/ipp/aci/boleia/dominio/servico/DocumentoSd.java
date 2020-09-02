package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.IDocumentoDados;
import ipp.aci.boleia.dados.IUsuarioDados;
import ipp.aci.boleia.dominio.Documento;
import ipp.aci.boleia.dominio.FrotaPontoVenda;
import ipp.aci.boleia.dominio.enums.DocumentoStatus;
import ipp.aci.boleia.dominio.enums.DocumentoTipo;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.excecao.ExcecaoValidacao;
import ipp.aci.boleia.util.i18n.Mensagens;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Serviços de domínio da entidade {@link Documento}.
 */
@Component
public class DocumentoSd {

    @Autowired
    private UsuarioSd usuarioSd;

    @Autowired
    private DocumentoAceiteSd documentoAceiteSd;

    @Autowired
    private IDocumentoDados repositorio;

    @Autowired
    private IUsuarioDados repositorioUsuario;

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Autowired
    private Mensagens mensagens;

    /**
     * Retorna a quantidade total de aceites esperados por um documento especifico.
     *
     * @param documento O documento selecionado
     * @return O registro de {@link FrotaPontoVenda} criado.
     */
    public long obterTotalDeAceitesEsperado(Documento documento){
        if(documento.getDataFim() != null && documento.getQtdTotalEsperado() != null){
            return documento.getQtdTotalEsperado();
        }

        if (TipoPerfilUsuario.FROTA.getValue().equals(documento.getTipoPerfil().getId()) &&
                DocumentoTipo.INTEGRACAO_KMV.getValor().equals(documento.getTipo())) {
            return repositorioUsuario.obterTotalUsuariosDonosFrota();
        }

        return usuarioSd.obterQuantidadeTotalAtivosDeTipoPerfil(documento.getTipoPerfil().getTipoPerfilUsuario());
    }

    /**
     * Retorna a quantidade total de aceites de um documento especifico.
     *
     * @param documento O documento selecionado
     * @return O registro de {@link FrotaPontoVenda} criado.
     */
    public long obterTotalDeAceites(Documento documento){
        return documentoAceiteSd.obterQuantidadeAceites(documento);
    }

    /**
     * Verifica se um documento existe, através dos campos que representam a sua unicidade.
     *
     * @param documento O documento a ser validado
     * @return True se o documento existir e false se não existir
     */
    public boolean verificaSeDocumentoExiste(Documento documento) {
        return repositorio.obterDocumento(DocumentoTipo.obterPorValor(documento.getTipo()),
                documento.getVersaoDocumento(),
                documento.getTipoPerfil().getId()) != null;
    }

    /**
     * Obtem todos os documentos que estão aguardando publicação e seram publicados hoje
     *
     * @return Lista de docuemntos
     */
    public List<Documento> obterDocumentosParaPublicacao() {
        return repositorio.obterDocumentosParaPublicacao();
    }


    /**
     * Busca um documento ativo equivalente ao novo e o revoga.
     *
     * @param documento O documento de referencia para revogar o vigente
     */
    public void revogarDocumentoAtualCasoExista(Documento documento) {
        Documento documentoVigente =  repositorio.obterDocumentos(
                DocumentoTipo.obterPorValor(documento.getTipo()),
                DocumentoStatus.VIGENTE, documento.getTipoPerfil().getId()).getRegistros().stream()
                .findFirst()
                .orElse(null);
        if(documentoVigente != null){
            Date dataFim = UtilitarioCalculoData.obterUltimoInstanteDia(
                    UtilitarioCalculoData.adicionarDiasData(ambiente.buscarDataAmbiente(), -1));
            documentoVigente.setDataFim(dataFim);
            documentoVigente.setStatus(DocumentoStatus.REVOGADA.getValor());
            documentoVigente.setQtdTotalEsperado(obterTotalDeAceitesEsperado(documentoVigente));
            repositorio.armazenar(documentoVigente);
        }
    }

    /**
     * Publica um documento, tornando-o vigente
     *
     * @param documento O documento de referencia para torna-lo vigente
     * @return o documento publicado
     */
    public Documento publicarDocumento(Documento documento) {
        documento = repositorio.obterPorId(documento.getId());
        documento.setStatus(DocumentoStatus.VIGENTE.getValor());
        return repositorio.armazenar(documento);
    }

    /**
     * Retorna um documento aguardando publicação equivalente ao documento passado, se existir.
     *
     * @param tipo O tipo de documento
     * @param idTipoPerfil O id referente ao tipo de perfil do usuário
     * @return Um documento aguardando publicação equivalente
     */
    public Documento obterDocumentoEquivalente(DocumentoTipo tipo, Long idTipoPerfil) {
        return repositorio.obterDocumentos(tipo, DocumentoStatus.AGUARDANDO_PUBLICACAO, idTipoPerfil)
                .getRegistros().stream()
                .findFirst()
                .orElse(null);
    }

    /**
     * Obtém um documento de acordo com seu tipo, tipo de perfil do usuário e status, se existir.
     *
     * @param tipo O tipo de documento
     * @param status O status associado ao documento
     * @param idTipoPerfil O id referente ao tipo de perfil do usuário
     * @return Um documento aguardando publicação equivalente
     */
    public List<Documento> obterDocumentos(DocumentoTipo tipo, DocumentoStatus status, Long idTipoPerfil) {
        return repositorio.obterDocumentos(tipo, status, idTipoPerfil)
                .getRegistros();
    }

    /**
     * Obtém um documento de integração de acordo com seu tipo,
     * e tipo de perfil do usuário, se existir.
     *
     * Note que, integrações podem ser aceitas mesmo que
     * o seu status não esteja vigente
     *
     * @param tipo O tipo de documento
     * @param idTipoPerfil O id referente ao tipo de perfil do usuário
     * @return Um documento aguardando publicação equivalente
     */
    public List<Documento> obterDocumentosIntegracao(DocumentoTipo tipo, Long idTipoPerfil) {
        return repositorio.obterDocumentosIntegracao(tipo, idTipoPerfil).getRegistros();
    }

    /**
     * Obtém uma lista de documentos de acordo com seu tipo, tipo de perfil do usuário  e status, se existir.
     *
     * @param tipo O tipo de documento
     * @param idTipoPerfil O tipo de perfil do usuário
     * @param status O status associado ao documento
     * @return Lista de documentos
     */
    public List<Documento> obterListaDocumentos(DocumentoTipo tipo, Long idTipoPerfil, DocumentoStatus status) {
        return repositorio.obterListaDocumentos(tipo, idTipoPerfil, status);
    }

    /**
     * Obter a versão posterior de um documento, dado um perfil do usuário ou tipo do documento
     *
     * @param tipo O tipo de documento
     * @param idTipoPerfil O id do tipo de perfil do usuário
     * @return A sugestão de versão.
     * @throws ExcecaoValidacao Caso falhe alguma validação
     */
    public String sugerirVersao(DocumentoTipo tipo, Long idTipoPerfil) throws ExcecaoValidacao {
        Documento documentoEquivalente = obterDocumentoEquivalente(tipo, idTipoPerfil);
        if(documentoEquivalente != null) {
            throw new ExcecaoValidacao(mensagens.obterMensagem("documento.servico.validacao.existe.aguardando.publicacao", documentoEquivalente.getId()));
        }

        ResultadoPaginado<Documento> documentos = repositorio.obterDocumentos(tipo, null, idTipoPerfil);
        if(documentos.getTotalItems()<1) {
            return gerarVersao(null);
        }

        String versaoAnterior = documentos.getRegistros().stream()
                .map(Documento::getVersaoDocumento)
                .max(Comparator.naturalOrder()).orElse(null);
        return gerarVersao(versaoAnterior);
    }

    /**
     * Gera a versão posterior
     *
     * @param versaoAnterior uma versão a ser incrementada
     * @return A versão gerada
     */
    private String gerarVersao(String versaoAnterior) {
        int primeiroValor = 0;
        int segundoValor = 0;
        int terceiroValor = 1;

        if (versaoAnterior == null) {
            return String.join(".", String.valueOf(primeiroValor), String.valueOf(segundoValor), String.valueOf(terceiroValor));
        }
        String[] versaoDividida = versaoAnterior.split("\\.");
        int valorTotal = Integer.parseInt(versaoDividida[0]) *100 + Integer.parseInt(versaoDividida[1])*10+ Integer.parseInt(versaoDividida[2]) + 1;

        primeiroValor = valorTotal/100;
        segundoValor = (valorTotal - primeiroValor*100)/10;
        terceiroValor = valorTotal % 10;
        return String.join(".", String.valueOf(primeiroValor), String.valueOf(segundoValor), String.valueOf(terceiroValor));
    }

    /**
     * Armazena um documento
     * @param documento O documento a ser armazenado
     * @return O documento armazenado
     */
    public Documento armazenar(Documento documento) {
        return repositorio.armazenar(documento);
    }

    /**
     * Obtém um documento pelo seu identificador
     * @param id O identificador do documento
     * @return O documento encontrado ou null caso não exista
     */
    public Documento obterDocumento(Long id) {
        return repositorio.obterPorId(id);
    }
}
