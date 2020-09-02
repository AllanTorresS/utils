package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IDocumentoDados;
import ipp.aci.boleia.dominio.Documento;
import ipp.aci.boleia.dominio.enums.DocumentoStatus;
import ipp.aci.boleia.dominio.enums.DocumentoTipo;
import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.pesquisa.comum.InformacaoPaginacao;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroOrdenacaoColuna;
import ipp.aci.boleia.dominio.pesquisa.comum.ParametroPesquisa;
import ipp.aci.boleia.dominio.pesquisa.comum.ResultadoPaginado;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMaiorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaDataMenorOuIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaFetch;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaLike;
import ipp.aci.boleia.dominio.vo.FiltroPesquisaDocumentoVo;
import ipp.aci.boleia.util.Ordenacao;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementação do repositório de dados da entidade Documento.
 */
@Repository
public class OracleDocumentoDados extends OracleRepositorioBoleiaDados<Documento> implements IDocumentoDados {

    @Autowired
    private UtilitarioAmbiente ambiente;

    /**
     * Construtor default da classe.
     */
    public OracleDocumentoDados() {
        super(Documento.class);
    }

    @Override
    public ResultadoPaginado<Documento> pesquisar(FiltroPesquisaDocumentoVo filtro) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (filtro.getDe() != null) {
            parametros.add(new ParametroPesquisaDataMaiorOuIgual("dataInicio", UtilitarioCalculoData.obterPrimeiroInstanteDia(filtro.getDe())));
        }

        if (filtro.getAte() != null) {
            parametros.add(new ParametroPesquisaDataMenorOuIgual("dataInicio", UtilitarioCalculoData.obterUltimoInstanteDia(filtro.getAte())));
        }

        if(filtro.getTipo() != null && filtro.getTipo().getName() != null) {
            parametros.add(new ParametroPesquisaIgual( "tipo",  DocumentoTipo.valueOf(filtro.getTipo().getName()).getValor()));
        }

        if(filtro.getVersao() != null) {
            parametros.add(new ParametroPesquisaLike( "versaoDocumento", filtro.getVersao()));
        }

        if (filtro.getStatus() != null && filtro.getStatus().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("status", DocumentoStatus.valueOf(filtro.getStatus().getName()).getValor()));
        }

        if(filtro.getPaginacao()!=null && filtro.getPaginacao().getParametrosOrdenacaoColuna() != null && filtro.getPaginacao().getParametrosOrdenacaoColuna().size() == 0){
            filtro.getPaginacao().getParametrosOrdenacaoColuna().add(new ParametroOrdenacaoColuna("dataInicio", Ordenacao.DECRESCENTE));
        }

        if (filtro.getTipoPerfil() != null && filtro.getTipoPerfil().getName() != null) {
            parametros.add(new ParametroPesquisaIgual("tipoPerfil.id", TipoPerfilUsuario.obterPorNome(filtro.getTipoPerfil().getName()).getValue()));
        }

        return pesquisar(filtro.getPaginacao(), parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public Documento obterDocumento(DocumentoTipo tipo, String versao, Long idTipoPerfil) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaIgual( "tipo", tipo.getValor()));
        if(versao != null) {
            parametros.add(new ParametroPesquisaIgual("versaoDocumento", versao));
        }
        parametros.add(new ParametroPesquisaIgual( "tipoPerfil.id", idTipoPerfil));
        return pesquisarUnico(parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public ResultadoPaginado<Documento> obterDocumentos(DocumentoTipo tipo, DocumentoStatus status, Long idTipoPerfil) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if(tipo != null) {
            parametros.add(new ParametroPesquisaIgual("tipo", tipo.getValor()));
        }
        if(status != null) {
            parametros.add(new ParametroPesquisaIgual("status", status.getValor()));
        }
        if(idTipoPerfil != null) {
            parametros.add(new ParametroPesquisaIgual("tipoPerfil.id", idTipoPerfil));
        }

        return pesquisar((InformacaoPaginacao)null, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<Documento> obterListaDocumentos(DocumentoTipo tipo, Long idTipoPerfil , DocumentoStatus status) {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        if (tipo != null) {
            parametros.add(new ParametroPesquisaIgual("tipo", tipo.getValor()));
        }
        if (status != null) {
            parametros.add(new ParametroPesquisaIgual("status", status.getValor()));
        }
        if (idTipoPerfil != null) {
            parametros.add(new ParametroPesquisaIgual("tipoPerfil.id", idTipoPerfil));
        }

        ParametroOrdenacaoColuna parametroOrdenacaoColuna = new ParametroOrdenacaoColuna("versaoDocumento", Ordenacao.CRESCENTE);

        return pesquisar(parametroOrdenacaoColuna, parametros.toArray(new ParametroPesquisa[parametros.size()]));
    }

    @Override
    public List<Documento> obterDocumentosParaPublicacao() {
        List<ParametroPesquisa> parametros = new ArrayList<>();
        parametros.add(new ParametroPesquisaDataMenorOuIgual("dataInicio", UtilitarioCalculoData.obterPrimeiroInstanteDia(ambiente.buscarDataAmbiente())));
        parametros.add(new ParametroPesquisaIgual("status", DocumentoStatus.AGUARDANDO_PUBLICACAO.getValor()));
        parametros.add(new ParametroPesquisaFetch("tipoPerfil"));
        return pesquisar((InformacaoPaginacao)null, parametros.toArray(new ParametroPesquisa[parametros.size()])).getRegistros();
    }
}
