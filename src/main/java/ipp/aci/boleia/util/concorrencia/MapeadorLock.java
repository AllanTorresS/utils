package ipp.aci.boleia.util.concorrencia;

import java.util.concurrent.locks.Lock;
import java.util.function.Function;

/**
 * Cria um monitor para gerenciar a criação dos locks em um determinado escopo
 * @param <T> Tipo do dado para efetuar a a criação da chave
 */
public class MapeadorLock<T> {

    /**
     * Sincronizador capaz de gerar locks
     */
    private Sincronizador sincronizador;

    /**
     * Função de mapeamento de um objeto para a chave do lock
     */
    private Function<T, String> funcaoMapeadora;

    /**
     * Constroi uma instancia de monitor
     *
     * @param sincronizador Sincronizador utilizado para obter o lock
     * @param funcaoMapeadora Função mapeadora responsável por definir a chave do lock
     */
    public MapeadorLock(Sincronizador sincronizador, Function<T, String> funcaoMapeadora) {
        this.sincronizador = sincronizador;
        this.funcaoMapeadora = funcaoMapeadora;
    }

    /**
     * Obtem um lock para a partir de um objeto
     *
     * @param object Objeto chave do lock
     * @return Lock obtido.
     */
    public Lock getLock(T object) {
        return sincronizador.getLock(this.getKey(object));
    }

    /**
     * Gera a chave do lock
     *
     * @param object Objeto chave do lock
     * @return Chave do lock gerada
     */
    private String getKey(T object) {
        return funcaoMapeadora.apply(object);
    }

}
