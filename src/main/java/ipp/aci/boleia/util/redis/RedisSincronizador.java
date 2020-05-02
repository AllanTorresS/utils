package ipp.aci.boleia.util.redis;

import ipp.aci.boleia.util.concorrencia.Sincronizador;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.Lock;

/**
 * Implementação do Sincronizador através do Radis
 */
@Component(value = "Redis")
public class RedisSincronizador implements Sincronizador {


    @Autowired
    private RedissonClient redisson;

    /**
     * Cria uma lock para uma chave qualquer
     * @param s nome do lock
     * @return Lock obtido
     */
    @Override
    public Lock getLock(String s) {
        return redisson.getLock(s);
    }
}
