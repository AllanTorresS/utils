package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dados.ISistemaExternoDados;
import ipp.aci.boleia.dominio.Permissao;
import ipp.aci.boleia.dominio.SistemaExterno;
import static ipp.aci.boleia.util.seguranca.UtilitarioCriptografia.calcularHashSHA256;
import static ipp.aci.boleia.util.seguranca.UtilitarioCriptografia.gerarSaltBCrypt;
import static ipp.aci.boleia.util.seguranca.UtilitarioCriptografia.toBase64;
import ipp.aci.boleia.util.seguranca.UtilitarioJwt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Encapsula as regras de negocio ligadas ao SistemaExterno.
 *
 * @author pedro.silva
 */
@Component
public class SistemaExternoSd {

    @Autowired
    private ISistemaExternoDados repositorio;

    @Autowired
    private UtilitarioJwt utilitarioJwt;

    /**
     * Gera um par de chaves criptografadas para o sistema externo informado.
     *
     * @param sistemaExterno retorna o sistema externo com o novo par de chaves criado.
     */
    public void gerarChavesCriptografadas(SistemaExterno sistemaExterno){
        String infoClient = sistemaExterno.getNomeSistema() + sistemaExterno.getEmail();
        String infoSecret = sistemaExterno.getNomeSistema() + sistemaExterno.getCnpj().toString();
        String client = calcularHashSHA256(infoClient + toBase64(gerarSaltBCrypt()));
        String secret = calcularHashSHA256(infoSecret + toBase64(gerarSaltBCrypt()));
        sistemaExterno.setClient(client);
        sistemaExterno.setSecret(secret);
    }

    /**
     * Verificar permissão para acesso via Authentication Basic
     * @param client da autenticação básica do token enviado
     * @param secret da autenticação básica do token enviado
     */
    public SistemaExterno verificarAutorizacaoBasic(String client, String secret) {
        SistemaExterno sistemaExterno = this.repositorio.obterPorClientESecretComPermissao(client, secret);
        Set<String> setPerms  = sistemaExterno.getPermissoes().stream().map(Permissao::getChave).collect(Collectors.toSet());
        return sistemaExterno;
    }
}
