package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IChamadoTipoDados;
import ipp.aci.boleia.dados.IPontoDeVendaDados;
import ipp.aci.boleia.dominio.AtividadeComponente;
import ipp.aci.boleia.dominio.ChamadoTipo;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.enums.StatusAlteracaoPrecoPosto;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.enums.StatusHabilitacaoPontoVenda;
import ipp.aci.boleia.dominio.enums.StatusPermissaoPreco;
import ipp.aci.boleia.dominio.enums.StatusPosse;
import ipp.aci.boleia.dominio.enums.TipoFiltroPontoVendaPrimario;
import ipp.aci.boleia.dominio.enums.TipoFiltroPontoVendaSecundario;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.enums.TipoServico;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaAnd;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEmpty;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaEntre;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIn;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMenor;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaNulo;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaOr;
import ipp.aci.boleia.dominio.vo.CoordenadaVo;
import ipp.aci.boleia.dominio.vo.EntidadeVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaLocalizacaoVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaParcialPtovVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaPontoDeVendaVo;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaRotaPontoVendaServicosVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioLambda;
import ipp.aci.boleia.util.UtilitarioParse;
import ipp.aci.boleia.util.negocio.ParametrosPesquisaBuilder;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Respositorio de entidades ChamadoTipo
 */
@Repository
public class OracleChamadoTipoDados extends OracleRepositorioBoleiaDados<ChamadoTipo> implements IChamadoTipoDados {


    /**
     * Instancia o repositorio
     */
    public OracleChamadoTipoDados() {
        super(ChamadoTipo.class);
    }

    @Override
    public List<ChamadoTipo> obterTiposChamado(TipoPerfilUsuario tipoPerfil) {
        List<ParametroPesquisa> params = new ArrayList<>();
        if(TipoPerfilUsuario.FROTA.equals(tipoPerfil)){
            params.add(new ParametroPesquisaIgual("portalFrotista",  Boolean.TRUE));
        }else if(TipoPerfilUsuario.REVENDA.equals(tipoPerfil)){
            params.add(new ParametroPesquisaIgual("portalRevendedor",  Boolean.TRUE));
        }else if (TipoPerfilUsuario.INTERNO.equals(tipoPerfil)){
            params.add(new ParametroPesquisaIgual("portalSolucao", Boolean.TRUE));
        }

        return pesquisar(new ParametroOrdenacaoColuna("nome"), params.toArray(new ParametroPesquisa[params.size()]));
    }
}
