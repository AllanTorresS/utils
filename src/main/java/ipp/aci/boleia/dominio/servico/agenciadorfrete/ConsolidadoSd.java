package ipp.aci.boleia.dominio.servico.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.Consolidado;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementa as regras de negócio relativas ao @{@link Consolidado} utilizado pelo Agenciador de Frete
 */
@Component
public class ConsolidadoSd {

    /**
     * Filtrar os consolidados que tem saque, a partir de uma lista de consolidados
     * @param consolidados Os consolidados a serem filtrados
     * @return A lista de consolidados que contém saque
     */
    public List<Consolidado> obterConsolidadosComSaque(List<Consolidado> consolidados) {
        return consolidados.stream()
                .filter(c -> c.getTransacoes() != null && c.getTransacoes().stream().anyMatch(t->t.getSaque() != null))
                .collect(Collectors.toList());
    }
}
