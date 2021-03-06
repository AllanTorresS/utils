package ipp.aci.boleia.util.negocio;

import ipp.aci.boleia.dados.IAmbienteDados;
import ipp.aci.boleia.dados.ISistemaExternoDados;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Permissao;
import ipp.aci.boleia.dominio.Rede;
import ipp.aci.boleia.dominio.SistemaExterno;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.enums.TipoTokenJwt;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.seguranca.ProvedorAutenticacao;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * Prove recursos do ambiente do sistema
 */
@Component
public class UtilitarioAmbiente {

    private static final String URL_VERIFICACAO_RECAPTCHA = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";

    private static final String RESPOSTA_SUCESSO_RECAPTCHA = "success";

    private static final String AMBIENTE_PRODUCAO = "prd";

    private static final String CHAVE_HEADER_USUARIO_RELATORIO = "USUARIO_RELATORIO";

    @Value("${identificador.ambiente}")
    private String identificadorAmbiente;

    @Value("${nome.sistema}")
    private String nomeSistema;

    @Value("${baseurl.sistema}")
    private String urlSistema;

    @Autowired
    private IAmbienteDados repositorio;

    @Value("${google.recaptcha.secret.key}")
    private String recapcthaSecretKey;

    @Autowired
    private ISistemaExternoDados sistemaExternoDados;

    /**
     * Retorna o nome do sistema
     *
     * @return O nome do sistema
     */
    public String getNomeSistema(){
        return this.nomeSistema;
    }

    /**
     * Retorna o identificador do ambiente em execu????o
     *
     * @return O identificador do ambiente
     */
    public String getIdentificadorAmbiente(){
        return this.identificadorAmbiente;
    }

    /**
     * Busca data da base de dados do ambiente
     *
     * @return data do ambiente
     */
    public Date buscarDataAmbiente() {
        return repositorio.obterDataAmbiente();
    }

    /**
     * Obtem o usuario logado no sistema
     *
     * @return O usuario logado no sistema
     */
    public Usuario getUsuarioLogado() {
        Usuario usuario = ProvedorAutenticacao.getUsuarioLogado();
        if (RequestContextHolder.getRequestAttributes() != null) {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            if (attr != null && attr.getRequest() != null && attr.getRequest().getSession() != null) {
                HttpSession session = attr.getRequest().getSession();
                if (session.getAttribute(CHAVE_HEADER_USUARIO_RELATORIO) != null) {
                    Object usuarioRelatorio = session.getAttribute(CHAVE_HEADER_USUARIO_RELATORIO);
                    if (usuarioRelatorio != null && usuarioRelatorio instanceof Usuario) {
                        return (Usuario) usuarioRelatorio;
                    }
                    return usuario;
                }
            }
        }
       return usuario;
    }

    /**
     * Obtem Id do sistema externo no sistema
     *
     * @return O id do sistema externo logado no sistema
     */
    public SistemaExterno getSistemaExterno() {
        Usuario usuario = getUsuarioLogado();
        if(usuario != null && usuario.getTipoTokenJwt() != null && usuario.getTipoTokenJwt().equals(TipoTokenJwt.SISTEMA_EXTERNO)) {
            return sistemaExternoDados.obterPorId(usuario.getId());
        }
        return null;
    }

    /**
     * Obtem o IP de origem da requisicao
     *
     * @return O IP de origem da requisicao
     */
    public String getIpOrigemRequisicao() {
        return ProvedorAutenticacao.getIpOrigemRequisicao();
    }

    /**
     * Obtem a frota do usuario logado
     *
     * @return A frota do usuario logado
     */
    public Frota getFrotaUsuarioLogado() {
        return getUsuarioLogado().getFrota();
    }

    /**
     * Obtem a rede do usuario logado
     *
     * @return A rede do usuario logado
     */
    public Rede getRedeUsuarioLogado() {
        return getUsuarioLogado().getRede();
    }

    /**
     * Obtem a URL de contexto da aplica????o em execu????o
     * @return URL de contexto da aplica????o
     */
    public String getURLContextoAplicacao() {
        return urlSistema;
    }

    /**
     * Exige que o usuario logado possua um dos tipos de perfil informados.
     * Caso nao possua, lan??a um erro de autoriza????o.
     *
     * @param tipoPerfilUsuarios Os tipos de perfil aceitos
     */
    public void exigirTipoPerfil(TipoPerfilUsuario... tipoPerfilUsuarios) {
        Usuario usuario = getUsuarioLogado();
        if(usuario != null) {
            for(TipoPerfilUsuario tp : tipoPerfilUsuarios) {
                if(usuario.getTipoPerfil().getId().equals(tp.getValue())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException(null);
    }

    /**
     * Exige que o usuario logado possua um dos tipos de perfil informados e seja gestor.
     * Caso nao possua, lan??a um erro de autoriza????o.
     *
     * @param tipoPerfilUsuarios Os tipos de perfil aceitos
     */
    public void exigirTipoPerfilGestor(TipoPerfilUsuario...tipoPerfilUsuarios) {
        Usuario usuario = getUsuarioLogado();
        if(usuario == null || !usuario.isGestor()) {
            throw new AccessDeniedException(null);
        }

        exigirTipoPerfil(tipoPerfilUsuarios);
    }

    /**
     * Valida a resposta de um desafio recaptcha respondido pelo usuario
     *
     * @param tokenRecaptcha A resposta do desafio
     */
    public void validarRecaptcha(String tokenRecaptcha) {
        try {
            if(tokenRecaptcha != null) {
                CloseableHttpClient cliente = HttpClientBuilder.create().build();
                HttpPost request = new HttpPost(String.format(URL_VERIFICACAO_RECAPTCHA, recapcthaSecretKey, tokenRecaptcha));
                CloseableHttpResponse response = cliente.execute(request);
                String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8.displayName());
                Map<String, Object> mapaResposta = JsonParserFactory.getJsonParser().parseMap(json);
                if(Boolean.TRUE.equals(mapaResposta.get(RESPOSTA_SUCESSO_RECAPTCHA))) {
                    return;
                }
            }
        } catch (IOException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
        throw new ExcecaoBoleiaRuntime(Erro.RECAPTCHA_INCORRETO);
    }

    /**
     * Atualiza o usuario logado na sessao redis
     * @param usuarioLogado o usuario atualizado
     */
    public void updateUsuarioLogado(Usuario usuarioLogado) {
        ProvedorAutenticacao.updateUsuarioLogado(usuarioLogado);
    }

    /**
     * Verifica se o usuario logado possui determinada permissao
     * @param chave da permissao
     * @return True se possui permissao
     */
    public boolean verificarPermissaoUsuarioLogado(String chave) {
        for(Permissao permissao : getUsuarioLogado ().getPermissoes()) {
            if(permissao.getChave().contentEquals(chave)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se o ambiente atual ?? de producao
     * @return True se possui ?? ambiente de produ????o
     */
    public Boolean verificaSeEstaEmAmbienteDeProducao(){
        return getIdentificadorAmbiente().equals(AMBIENTE_PRODUCAO);
    }

    /**
     * Obtem o IP do servidor corrente
     * @return o IP do servidor
     */
    public String obterIPServidor(){
        try {
            String ipServidor = InetAddress.getLocalHost().getHostAddress();
            if(ipServidor.length() > 15 ){
                ipServidor = ipServidor.substring(0,15);
            }
            return ipServidor;
        } catch (UnknownHostException e) {
            return "";
        }
    }

    public static String getChaveHeaderUsuarioRelatorio() {
        return CHAVE_HEADER_USUARIO_RELATORIO;
    }
}
