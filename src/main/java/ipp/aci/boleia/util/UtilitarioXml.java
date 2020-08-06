package ipp.aci.boleia.util;

import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Ferramentas para manipulacao de arquivos XML
 */
public final class UtilitarioXml {

    /**
     * Previne instanciacao e heranca
     */
    private UtilitarioXml() {
        // previne instanciacao e heranca
    }

    /**
     * Obtem uma nova instancia de XPath
     *
     * @return a instancia criada
     */
    private static final XPath getXPath() {
        return XPathFactory.newInstance().newXPath();
    }

    /**
     * Dado o conteudo de um arquivo XML, representado como vetor de bytes,
     * constroi e retorna um {@link Document}
     * @param xml O conteudo do arquivo XML
     * @return Um XML Document
     */
    public static Document lerXml(byte[] xml) {
        try {
            DocumentBuilderFactory builderFactory = criarDocumentBuilderFactory();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            return builder.parse(new ByteArrayInputStream(xml));
        } catch (SAXException | ParserConfigurationException | IOException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Converte um objeto em uma string no formato xml.
     *
     * @param o O objeto a ser convertido.
     * @return o resultado em formato xml.
     */
    public static String toXml(Object o){
        try{
            StringWriter xmlOutput = new StringWriter();
            JAXBContext jaxbContext = JAXBContext.newInstance(o.getClass());
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.marshal(o, xmlOutput);
            return xmlOutput.toString();
        } catch (JAXBException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Converte uma string formatada em xml para um objeto de acordo com a classe especificada,
     *
     * @param xml A string correspondente ao xml formatado
     * @param classe A classe que será retornada ao realizar a conversão
     * @param <T> O tipo da classe mapeada
     * @return O objeto convertido de acordo com a classe especificada
     */
    public static <T> T toObject(String xml, Class<T> classe) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(classe);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return classe.cast(jaxbUnmarshaller.unmarshal(new StringReader(xml)));
        } catch (JAXBException e){
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Cria um DocumentBuilderFactory com as protecoes necessarias para evitar ataques de XXE
     * (https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Prevention_Cheat_Sheet#Java)
     *
     * @return Um DocumentBuilderFactory seguro
     */
    private static DocumentBuilderFactory criarDocumentBuilderFactory() {
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(false);
            builderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            builderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            builderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            builderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
            builderFactory.setXIncludeAware(false);
            builderFactory.setExpandEntityReferences(false);
            return builderFactory;
        } catch (ParserConfigurationException e) {
            throw new ExcecaoBoleiaRuntime(e);
        }
    }

    /**
     * Obtem a lista de itens da nota
     *
     * @param documento documento que representa o Xml da nota parseada
     * @return O valor obtido
     */
    public static NodeList getItens(Document documento) {
        try {
            return (NodeList) getXPath().compile(ConstantesNotaFiscalParser.CAMINHO_NF).evaluate(documento, XPathConstants.NODESET);
        } catch (XPathExpressionException e) {
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
    public static String getString(Document documento, String caminho) {
        return getString(documento, caminho, null);
    }

    /**
     * Obtem um campo string da nota a partir do caminho informado e um nó.
     * Caso o nó nãoo exista recupera da raíz próprio documento.
     *
     * @param documento documento que representa o Xml da nota parseada
     * @param caminho O caminho de nos no XML da nota
     * @param node O nó onde o caminho deve ser pesquisado, se nulo pesquisa na
     * raíz do documento
     * @return O valor obtido
     */
    public static String getString(Document documento, String caminho, Node node) {
        try {
            return getXPath().compile(caminho).evaluate((node != null) ? node : documento);
        } catch (XPathExpressionException e) {
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
    public static Long getLong(Document documento, String caminho) {
        if (caminho != null) {
            try {
                return Long.parseLong(getString(documento, caminho).trim(), 10);
            } catch (NumberFormatException e) {
                return null;
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
    public static BigDecimal getBigDecimal(Document documento, String caminho) {
        String vlr = getString(documento, caminho);
        if (vlr != null) {
            try {
                return new BigDecimal(vlr.trim());
            } catch (NumberFormatException e) {
                return null;
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
    public static Date getDate(Document documento, String caminho) {
        String vlr = getString(documento, caminho);
        if (vlr != null) {
            return UtilitarioFormatacaoData.lerDataIso8601(vlr);
        }
        return null;
    }
}
