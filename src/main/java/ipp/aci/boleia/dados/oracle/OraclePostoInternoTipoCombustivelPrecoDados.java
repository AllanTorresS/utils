package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IPostoInternoTipoCombustivelPrecoDados;
import ipp.aci.boleia.dominio.PostoInternoTipoCombustivelPreco;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Respositorio de entidades Rota
 */
@Repository
public class OraclePostoInternoTipoCombustivelPrecoDados extends OracleRepositorioBoleiaDados<PostoInternoTipoCombustivelPreco> implements IPostoInternoTipoCombustivelPrecoDados {

    /**
     * ConstrutorPostoInternoTipoCombustivelPreco
     */
    public OraclePostoInternoTipoCombustivelPrecoDados() {
        super(PostoInternoTipoCombustivelPreco.class);
    }

}
