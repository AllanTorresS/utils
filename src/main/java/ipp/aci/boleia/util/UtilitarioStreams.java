package ipp.aci.boleia.util;

import com.amazonaws.util.IOUtils;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.commons.codec.binary.Base64OutputStream;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Utilitario para manipulacao de streams
 */
public final class UtilitarioStreams {

    /**
     * Impede a instanciacao e a heranca
     */
    private UtilitarioStreams() {
        // Impede a instanciacao e a heranca
    }

    /**
     * Copia um stream de entrada para um stream de saida.<br>
     * O stream de entrada é fechado após o seu processamento.<br>
     * Por outro lado, o stream de saída permanece aberto após a execução deste método.
     *
     * @param in  entrada
     * @param out saida
     */
    public static void copiarStream(InputStream in, OutputStream out) {
        try (InputStream inputStream = in) {
            StreamUtils.copy(inputStream, out);
        } catch (IOException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Copia um stream de entrada para um stream de saida.<br>
     * O stream de entrada é fechado após o seu processamento.<br>
     * Por outro lado, o stream de saída permanece aberto após a execução deste método.
     *
     * @param caminho O caminho de uma entrada do classpath
     * @param out     Saida
     */
    public static void copiarResource(String caminho, OutputStream out) {
        copiarStream(UtilitarioStreams.class.getResourceAsStream(caminho), out);
    }

    /**
     * Compacta uma lista de input streams.
     *
     * @param in Mapa de streams a serem comprimidos nos zip. A chave do mapa é o nome do
     *             arquivo.
     * @param out Stream onde será escrito o resultado da compressão dos arquivos.
     */
    public static void compactarStreams(Map<String, InputStream> in, OutputStream out) {
        try (
            Base64OutputStream out64 = new Base64OutputStream(out);
            ZipOutputStream zip = new ZipOutputStream(out64, Charset.forName("Cp860"))) {

            for (Map.Entry<String, InputStream> entry : in.entrySet()) {
                zip.putNextEntry(new ZipEntry(entry.getKey()));
                copiarStream(entry.getValue(), zip);
                zip.closeEntry();
            }
        } catch (IOException ex) {
            throw new ExcecaoBoleiaRuntime(ex);
        }
    }

    /**
     * Decodifica arquivo zip extraindo seu contaúdo
     *
     * @param in Stream de entrada
     * @return Um mapa com o nome dos arquivos extraidos e um input stream com os dados de cada arquivo
     */
    public static Map<String, InputStream> descompactarStream(InputStream in) {
        Map<String, InputStream> streamMap = new HashMap<>();
        try{
            ZipInputStream zipStream = new ZipInputStream(in);
            ZipEntry entry;
            while ((entry = zipStream.getNextEntry()) != null) {
                streamMap.put(entry.getName(),new ByteArrayInputStream(StreamUtils.copyToByteArray(zipStream)));
            }
        } catch (Exception e){
            throw new ExcecaoBoleiaRuntime(e);
        }
        return streamMap;
    }

    /**
     * Ler um stream de bytes de entrada e decodifica-o segundo a cadeia de caracteres informada.
     *
     * @param in Stream de entrada.
     * @param charset Cadeia de caracteres a ser utilizada para decodificar o stream.
     * @return Uma string com o conteúdo do stream codificado.
     */
    public static String decodificarStream(InputStream in, Charset charset){
        StringBuilder builder = new StringBuilder();
        try{
            Reader decoder = new InputStreamReader(in, charset);
            BufferedReader bufferedReader = new BufferedReader(decoder);
            String aux;
            while((aux = bufferedReader.readLine()) != null){
                builder.append(aux);
            }
        } catch (Exception e){
            throw new ExcecaoBoleiaRuntime(e);
        }
        return builder.toString();
    }

    /**
     * Le um input stream e o  carrega em memoria, para reuso
     *
     * @param in o stream de entrada
     * @return um vetor de bytes memoria
     */
    public static byte[] carregarEmMemoria(InputStream in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.copy(in, out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }
}