package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.agenciadorfrete.Consolidado;
import ipp.aci.boleia.dominio.agenciadorfrete.Transacao;

import java.util.List;

/**
 * Interface responsável pelo acesso aos dados do Consolidado do Agenciador de frete na base
 */
public interface IAgenciadorFreteConsolidadoDados extends IRepositorioBoleiaDados<Consolidado> {

    Consolidado obterPorTransacao(Transacao transacao);

    List<Consolidado> obterAbertosParaCobranca();

    List<Consolidado> obterAbertosParaReembolsos();
}