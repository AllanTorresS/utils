package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.dominio.ModuloInterno;
import org.springframework.stereotype.Component;

import static ipp.aci.boleia.util.seguranca.UtilitarioCriptografia.calcularHashSHA256;
import static ipp.aci.boleia.util.seguranca.UtilitarioCriptografia.gerarSaltBCrypt;
import static ipp.aci.boleia.util.seguranca.UtilitarioCriptografia.toBase64;

/**
 * Encapsula as regras de negócio ligadas aos Módulos Internos
 */
@Component
public class ModuloInternoSd {

    /**
     * Gera o par de chaves criptografadas que serão usadas para gerar o token JWT
     *
     * @param moduloInterno O módulo interno do sistema
     */
    public void gerarChavesCriptografadas(ModuloInterno moduloInterno) {
        String infoClient = moduloInterno.getNomeModulo();
        String saltClient = toBase64(gerarSaltBCrypt());
        String infoSecret = moduloInterno.getNomeModulo();
        String saltSecret = toBase64(gerarSaltBCrypt());
        String client = calcularHashSHA256(infoClient + saltClient);
        String secret = calcularHashSHA256(infoSecret + saltSecret);
        moduloInterno.setClient(client);
        moduloInterno.setClientSalt(saltClient);
        moduloInterno.setSecret(secret);
        moduloInterno.setSecretSalt(saltSecret);
    }
}
