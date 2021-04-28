package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.PostoInternoTipoCombustivelPreco;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades RotaPostoDesconsiderado
 */
public interface IPostoInternoTipoCombustivelPrecoDados extends IRepositorioBoleiaDados<PostoInternoTipoCombustivelPreco> {

    List<PostoInternoTipoCombustivelPreco> obterPorPostoInternoTipoCombustivel(Long idFrota, Long idUnide, Long idTipoCombustivel);

}