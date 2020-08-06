package ipp.aci.boleia.dominio.servico;

import ipp.aci.boleia.util.ConstantesNotaFiscalParser;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;
import ipp.aci.boleia.util.i18n.Mensagens;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Oferece funcionalidades para realizar o parser de uma Nota Fiscal.
 */
@Component
public class NotaFiscalParserSd {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotaFiscalParserSd.class);

    private final XPath xPath = XPathFactory.newInstance().newXPath();

    @Autowired
    private Mensagens mensagens;


    /**
     * Verifica se uma nota fiscal eh uma versao de homologacao
     *
     * @param documento documento que representa o Xml da nota parseada
     * @return true caso seja de homologacao
     */
    public boolean isHomologacao(Document documento) {
        String razaoSocial = getString(documento, ConstantesNotaFiscalParser.DEST_RAZAO_SOCIAL);
        String inscrEstad = getString(documento, ConstantesNotaFiscalParser.DEST_INSC_ESTADUAL);
        String cnpj = getString(documento, ConstantesNotaFiscalParser.DEST_CNPJ);
        return ConstantesNotaFiscalParser.RAZAO_SOCIAL_NOTA_HOMOLOG.equalsIgnoreCase(razaoSocial)
                && ConstantesNotaFiscalParser.INSCR_ESTADU_NOTA_HOMOLOG.equals(inscrEstad)
                && ConstantesNotaFiscalParser.CNPJ_NOTA_HOMOLOG.equals(cnpj);
    }

    /**
     * Obtem a lista de itens da nota
     *
     * @param documento documento que representa o Xml da nota parseada
     * @return O valor obtido
     */
    public NodeList getItens(Document documento) {
        try {
            return (NodeList) xPath.compile(ConstantesNotaFiscalParser.CAMINHO_NF).evaluate(documento, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
            LOGGER.info(mensagens.obterMensagem("notaFiscal.erro.recuperarListaDeNos"), e);
            return null;
        }
    }

    /**
     * Obtem um campo string da nota a partir do caminho informado
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param caminho O caminho de nos no XML da nota
     * @return O valor obtido
     */
    public String getString(Document documento, String caminho) {
        return getString(documento, caminho, null);
    }

    /**
     * Obtem um campo string da nota a partir do caminho informado e um nó.
     * Caso o nó nãoo exista recupera da raíz próprio documento.
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param caminho O caminho de nos no XML da nota
     * @param node O nó onde o caminho deve ser pesquisado, se nulo pesquisa na
     *             raíz do documento
     * @return O valor obtido
     */
    public String getString(Document documento, String caminho, Node node) {
        try {
            return xPath.compile(caminho).evaluate((node != null) ? node : documento);
        } catch (XPathExpressionException e) {
            LOGGER.info(mensagens.obterMensagem("notaFiscal.erro.expressaoXpathInvalida"), e);
            return null;
        }
    }

    /**
     * Obtem um campo long de uma nota a partir do caminho informado
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param caminho O caminho de nos no XML da nota
     * @return O valor obtido
     */
    public Long getLong(Document documento,String caminho) {
        if(caminho != null) {
            try {
                return Long.parseLong(getString(documento, caminho).trim(), 10);
            } catch (NumberFormatException e) {
                LOGGER.info(mensagens.obterMensagem("notaFiscal.erro.parsearLong"), e);
            }
        }
        return null;
    }

    /**
     * Obtem um campo big decimal da nota a partir do caminho informado
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param caminho O caminho de nos no XML da nota
     * @return O valor obtido
     */
    public BigDecimal getBigDecimal(Document documento, String caminho) {
        String vlr = getString(documento, caminho);
        if(vlr != null) {
            try {
                vlr = vlr.trim();
                return new BigDecimal(vlr);
            } catch (NumberFormatException e) {
                LOGGER.info(mensagens.obterMensagem("notaFiscal.erro.parsearBigdecimal"), e);
            }
        }
        return null;
    }

    /**
     * Obtem um campo date da nota a partir do caminho informado
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param caminho O caminho de nos no XML da nota
     * @return O valor obtido
     */
    public Date getDate(Document documento, String caminho) {
        String vlr = getString(documento, caminho);
        if(vlr != null) {
            return UtilitarioFormatacaoData.lerDataIso8601(vlr);
        }
        return null;
    }
}
