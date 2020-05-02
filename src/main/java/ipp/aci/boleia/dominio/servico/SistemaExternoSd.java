package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dominio.SistemaExterno;
import org.springframework.stereotype.Component;

import static ipp.aci.boleia.util.seguranca.UtilitarioCriptografia.calcularHashSHA256;
import static ipp.aci.boleia.util.seguranca.UtilitarioCriptografia.gerarSaltBCrypt;
import static ipp.aci.boleia.util.seguranca.UtilitarioCriptografia.toBase64;

/**
 * Encapsula as regras de negocio ligadas ao SistemaExterno.
 *
 * @author pedro.silva
 */
@Component
public class SistemaExternoSd {

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
}
