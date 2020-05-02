package ipp.aci.boleia.dados;

import ipp.aci.boleia.dominio.Banco;

import java.util.List;

/**
 * Contrato para implementacao de repositorios de entidades Banco
 */
public interface IBancoDados extends IRepositorioBoleiaDados<Banco> {

    /**
     * Pesquisar redes pelo termo dentro do nome
     * @param termo a pesquisar no nome do Banco
     * @return lista de Banco localizados
     */
    List<Banco> pesquisar(String termo);
}
