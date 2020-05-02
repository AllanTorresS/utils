package ipp.aci.boleia.util.concorrencia;

import java.util.concurrent.locks.Lock;

/**
 * Decreve os formatos das sincronizações disponiveis no projeto,
 * permitindo fazer semaforos, monitores, lockers e etc
 *
 */
public interface Sincronizador {

    /**
     * Obtem um lock
     *
     * @param s Chave do lock
     * @return Lock obtido
     */
    Lock getLock(String s);
}
