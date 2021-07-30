package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IOperacaoContaBeneficiarioDados;
import ipp.aci.boleia.dominio.beneficios.OperacaoContaBeneficiario;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataEntre;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaMaior;
import ipp.aci.boleia.dominio.vo.beneficios.FiltroPesquisaExtratoBeneficiariosVo;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do repositório de dados da entidade {@link OperacaoContaBeneficiario}.
 *
 * @author pedro.silva
 */
@Repository
public class OracleOperacaoContaBeneficiarioDados extends OracleRepositorioBoleiaDados<OperacaoContaBeneficiario> implements IOperacaoContaBeneficiarioDados {

    /**
     * Instancia o repositorio
     */
    public OracleOperacaoContaBeneficiarioDados() {
        super(OperacaoContaBeneficiario.class);
    }

    @Override
    public ResultadoPaginado<OperacaoContaBeneficiario> pesquisarExtratos(FiltroPesquisaExtratoBeneficiariosVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaMaior("valorTotal", BigDecimal.ZERO));
        if (filtro.getDe() != null && filtro.getAte() != null) {
            parametros.add(new ParametroPesquisaDataEntre("dataCriacao", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe()), UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));
        }
        if (filtro.getCpfBeneficiario() != null) {
            parametros.add(new ParametroPesquisaIgual("contaBeneficiario.beneficiario.cpf", UtilitarioFormatacao.obterLongMascara(filtro.getCpfBeneficiario())));
        }
        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }
}
