package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IRotaDados;
import ipp.aci.boleia.dados.IRotaParametroFrotaDados;
import ipp.aci.boleia.dominio.Rota;
import ipp.aci.boleia.dominio.RotaParametroFrota;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.RestricaoVisibilidadePontoVenda;
import ipp.aci.boleia.dominio.enums.StatusVinculoFrotaPontoVenda;
import ipp.aci.boleia.dominio.enums.TipoPontoRota;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDiferente;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgualIgnoreCase;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaRotaVo;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Respositorio de entidades Rota
 */
@Repository
public class OracleRotaParametroFrotaDados extends OracleRepositorioBoleiaDados<RotaParametroFrota> implements IRotaParametroFrotaDados {

    /**
     * Construtor
     */
    public OracleRotaParametroFrotaDados() {
        super(RotaParametroFrota.class);
    }
}
