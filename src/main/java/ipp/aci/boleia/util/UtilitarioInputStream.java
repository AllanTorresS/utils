package ipp.aci.boleia.util;

import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.apache.poi.util.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Utilitário para manipulação do Inputstream.
 */
public class UtilitarioInputStream {

    /**
     * Le um input stream e o  carrega em memoria, para reuso
     * @param in o stream de entrada
     * @return um vetor de bytes memoria
     */
    public static byte[] carregarEmMemoria(InputStream in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            IOUtils.copy(in,out);
            return out.toByteArray();
        } catch (IOException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }
}
