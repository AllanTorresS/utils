package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.dominio.Permissao;
import ipp.aci.boleia.dominio.Usuario;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static ipp.aci.boleia.util.UtilitarioLambda.converterLista;

/**
 * Armazena as informacoes sobre a autenticacao do usuario
 */
public class InformacoesAutenticacao extends UsernamePasswordAuthenticationToken {

    private static final String SPRING_DEFAULT_ROLE_PREFIX = "ROLE_";

    private Usuario usuario;

    /**
     * Construtor default
     */
    public InformacoesAutenticacao() {
        super(null, null, null);
    }

    /**
     * Construtor completo
     *
     * @param principal O nome do usuario
     * @param credentials A senha do usuario
     * @param authorities As permissoes do usuario
     * @param usuario O usuario
     */
    public InformacoesAutenticacao(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities, Usuario usuario) {
        super(principal, credentials, authorities);
        this.usuario = usuario;
    }

    /**
     * Retorna o usuario autenticado
     * @return O usuario autenticado
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * Seta o usuario autenticado
     * @param usuario O usuario
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    /**
     * Constroi uma instancia do objeto a partir dos parametros informados
     * @param principal O username
     * @param credenciais A senha
     * @param usuario o Usuario
     * @return uma instancia do VO
     */
    public static InformacoesAutenticacao build(String principal, Object credenciais, Usuario usuario) {
        Set<Permissao> permissoes = usuario.getPermissoes();
        List<GrantedAuthority> auths = converterLista(permissoes, p -> new SimpleGrantedAuthority(SPRING_DEFAULT_ROLE_PREFIX + p.getChave()));
        return new InformacoesAutenticacao(principal, credenciais, auths, usuario);
    }
}