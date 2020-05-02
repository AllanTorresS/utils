package ipp.aci.boleia.util.seguranca;

import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.bouncycastle.crypto.generators.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

/**
 * Utilitario de criptografia
 */
public final class UtilitarioCriptografia {

    private static final int BCRYPT_ROUDNS = 12;
    private static final int MAX_BCRYPT_SUPPORTED = 72;
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilitarioCriptografia.class);

    /**
     * Evita instanciacao e heranca
     */
    private UtilitarioCriptografia() {
        // evita instanciacao e heranca
    }

    /**
     * Calcula o hash BCrypt para uma dada informacao
     *
     * @param informacao A informacao
     * @param salt O salt para aumento de entropia
     * @return O hash calculado
     */
    public static byte[] calcularHashBCrypt(byte[] informacao, byte[] salt) {
        if(informacao != null && informacao.length > MAX_BCRYPT_SUPPORTED) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_TAMANHO_SENHA_BCRYPT);
        }
        return BCrypt.generate(informacao, salt, BCRYPT_ROUDNS);
    }

    /**
     * Calcula o hash BCrypt para uma dada informacao
     *
     * @param informacaoBase64 A informacao codificada em Base64
     * @param saltBase64 O salt, codificado em Base64, para aumento de entropia
     * @return O hash calculado, codificado em Base64
     */
    public static String calcularHashBCrypt(String informacaoBase64, String saltBase64) {
        byte[] informacao = fromBase64(informacaoBase64);
        byte[] salt = fromBase64(saltBase64);
        return toBase64(calcularHashBCrypt(informacao, salt));
    }

    /**
     * Verifica se o hash informado esta coerente com a informacao original
     *
     * @param informacao A informacao desejada
     * @param salt O salt
     * @param hashEsperado O hash esperado
     * @return True caso o hash esteja coerente com a informacao
     */
    public static boolean verificarHashBCrypt(byte[] informacao, byte[] salt, byte[] hashEsperado) {
        byte[] hashCalculado = calcularHashBCrypt(informacao, salt);
        return Arrays.equals(hashEsperado, hashCalculado);
    }

    /**
     * Verifica se o hash informado esta coerente com a informacao original
     *
     * @param informacaoBase64 A informacao desejada
     * @param saltBase64 O salt
     * @param hashEsperadoBase64 O hash esperado
     * @return True caso o hash esteja coerente com a informacao
     */
    public static boolean verificarHashBCrypt(String informacaoBase64, String saltBase64, String hashEsperadoBase64) {
        return verificarHashBCrypt(fromBase64(informacaoBase64),fromBase64(saltBase64),fromBase64(hashEsperadoBase64));
    }

    /**
     * Gera um vetor de bytes para utilizacao como salt no algoritmo BCrypt
     * @return salta gerado randomicamente
     */
    public static byte[] gerarSaltBCrypt() {
        byte[] bytes = new byte[16];
        UtilitarioCriptografia.getRandom().nextBytes(bytes);
        return bytes;
    }

    /**
     * Retorna a informacao codificada como ums string Base64
     *
     * @param informacao O vetor de bytes contendo a informacao
     * @return Uma string Base64
     */
    public static String toBase64(byte[] informacao) {
        return Base64.encodeBase64String(informacao);
    }

    /**
     * Retorna os bytes a partir da  informacao codificada como ums string Base64
     *
     * @param informacaoBase64 A informacao codificada em Base64
     * @return Um vetor de bytes
     */
    public static byte[] fromBase64(String informacaoBase64) {
        return Base64.decodeBase64(informacaoBase64);
    }

    /**
     * Retorna um gerador de numeros aleatorios criptograficamente seguro
     * (CSPRNG) "nao blockante" (caso o pool de seeds fique vazio,
     * retorna um gerador deterministico (PRNG).
     *
     * @return Um gerador de numeros aleatorios
     */
    public static SecureRandom getRandom() {
        try {
            return SecureRandom.getInstance("NativePRNGNonBlocking");
        } catch (NoSuchAlgorithmException e) {
            String mensagem = "Nao foi possivel obter um gerador de numeros aleatorios seguro \"nao-blocante\". " +
                    "Utilizando a implementacao padrao da JVM.";
            LOGGER.warn(mensagem);
            LOGGER.debug(mensagem, e);
            return new SecureRandom();
        }
    }

    /**
     * Retorna um token do tipo numérico gerado aleatoriamente
     *
     * @param tamanho O tamanho do token numérico
     * @return O token numérico aleatório
     */
    public static String gerarTokenNumerico(int tamanho){
        int maxRandomValue = (int) Math.pow(10,tamanho);
        return UtilitarioFormatacao.formatarNumeroZerosEsquerda(getRandom().nextInt(maxRandomValue), tamanho);
    }

    /**
     * Retorna um token do tipo alfanumérico gerado aleatoriamente
     *
     * @param tamanho O tamanho do token alfanumérico
     * @return O token alfanumérico aleatório
     */
    public static String gerarTokenAlfanumerico(int tamanho){
        return RandomStringUtils.random(tamanho, 0, 0, true, true, null, getRandom());
    }

    /**
     * Calcula o hash SHA-265 de uma informacao textual
     *
     * @param informacao A informacao textual
     * @return O hash SHA-256 da informacao
     */
    public static String calcularHashSHA256(String informacao){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(informacao.getBytes(StandardCharsets.UTF_8));
            byte[] hash = digest.digest();
            return String.format( "%064x", new BigInteger(1, hash));
        } catch (NoSuchAlgorithmException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }
}
