package ipp.aci.boleia.dados.servicos.chavevalor;

import ipp.aci.boleia.dados.IChaveValorDados;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * Implementação de uma estrutura chave-valor através do Radis
 */
@Repository
public class ChaveValorDados<T> implements IChaveValorDados<T> {

    @Autowired
    private RedissonClient redisson;

    @Override
    public void inserir(String nome, String chave, T valor) {
        RMapCache<String, T> map = redisson.getMapCache(nome);
        map.fastPut(chave, valor);
    }

    @Override
    public void inserirComExpiracao(String nome, String chave, T valor, long minutosParaExpirar) {
        RMapCache<String, T> map = redisson.getMapCache(nome);
        map.fastPut(chave, valor, minutosParaExpirar, TimeUnit.MINUTES);
    }

    @Override
    public T obter(String nome, String chave) {
        RMapCache<String, T> map = redisson.getMapCache(nome);
        return map.get(chave);
    }
}
