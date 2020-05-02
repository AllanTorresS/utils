package ipp.aci.boleia.util.pdf;

import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Classe responsável pela geração de documentos PDF.
 */
@Component
public class UtilitarioRelatorio {

    private static final String JASPER_FILE_FORMAT = "/%s.jasper";
    private static final String REPORT_LOCALE_KEY = "REPORT_LOCALE";
    private static final String IDIOMA = "pt";
    private static final String PAIS = "BR";


    /**
     * Método que realiza a geração de arquivos PDF utilizando JasperReports.
     *
     * @param dados Dados do relatório, serão passados como dados para o template.
     * @param template Template que será usado para geração deste PDF.
     * @param oStream Stream onde será gravado o relatório exportado.
     * @param <D> O tipo dos dados para a geracao do relatorio
     */
    public <D> void gerarPdf(D dados, String template, OutputStream oStream)  {
        List<D> list = new ArrayList<>();
        list.add(dados);
        gerarPdf(list, template, oStream);
    }

    /**
     * Método que realiza a geração de arquivos PDF utilizando JasperReports.
     *
     * @param dados Dados do relatório, serão passados como dados para o template.
     * @param template Template que será usado para geração deste PDF.
     * @param oStream Stream onde será gravado o relatório exportado.
     * @param <D> O tipo dos dados para a geracao do relatorio
     */
    public <D> void gerarPdf(Collection<D> dados, String template, OutputStream oStream)  {
        gerarPdf(dados, new HashMap<>(), template, oStream);
    }

    /**
     * Método que realiza a geração de arquivos PDF utilizando JasperReports.
     * 
     * @param dados Dados do relatório, serão passados como dados para o template.
     * @param params Parâmetros a serem enviados para o template.
     * @param template Template que será usado para geração deste PDF.
     * @param oStream Stream onde será gravado o relatório exportado.
     * @param <D> O tipo dos dados para a geracao do relatorio
     */
    public <D> void gerarPdf(Collection<D> dados, Map<String, Object> params, String template, OutputStream oStream)  {
        try (InputStream iStream = this.getClass().getResourceAsStream(String.format(JASPER_FILE_FORMAT, template))) {
            params.put(REPORT_LOCALE_KEY, new Locale(IDIOMA, PAIS));
            JasperPrint jasperPrint = JasperFillManager.fillReport(iStream, params, new JRBeanCollectionDataSource(dados));
            JasperExportManager.exportReportToPdfStream(jasperPrint, oStream);
        } catch (IOException | JRException e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_GENERICO, e);
        }
    }

    /**
     * Método que realiza a geração de arquivos PDF utilizando JasperReports.
     *
     * @param dados Dados do relatório, serão passados como dados para o template.
     * @param template Template que será usado para geração deste PDF.
     * @param <D> O tipo dos dados para a geracao do relatorio
     * @return retorna um InputStream do PDF
     */
    public <D> InputStream gerarPdf(D dados, String template)  {
        List<D> list = new ArrayList<>();
        list.add(dados);
        return gerarPdf(list, template);
    }

    /**
     * Método que realiza a geração de arquivos PDF utilizando JasperReports.
     *
     * @param dados Dados do relatório, serão passados como dados para o template.
     * @param template Template que será usado para geração deste PDF.
     * @param <D> O tipo dos dados para a geracao do relatorio
     * @return retorna um InputStream do PDF
     */
    public <D> InputStream gerarPdf(Collection<D> dados, String template)  {
        return gerarPdf(dados, new HashMap<>(), template);
    }

    /**
     * Método que realiza a geração de arquivos PDF utilizando JasperReports.
     *
     * @param dados Dados do relatório, serão passados como dados para o template.
     * @param params Parâmetros a serem enviados para o template.
     * @param template Template que será usado para geração deste PDF.
     * @param <D> O tipo dos dados para a geracao do relatorio
     * @return Retorna um InputStream do PDF
     */
    public <D> InputStream gerarPdf(Collection<D> dados, Map<String, Object> params, String template)  {
        try (InputStream iStream = this.getClass().getResourceAsStream(String.format(JASPER_FILE_FORMAT, template))) {
            params.put(REPORT_LOCALE_KEY, new Locale(IDIOMA, PAIS));
            JasperPrint jasperPrint = JasperFillManager.fillReport(iStream, params, new JRBeanCollectionDataSource(dados));
            byte[] relatorio = JasperExportManager.exportReportToPdf(jasperPrint);
            return new ByteArrayInputStream(relatorio);
        } catch (IOException | JRException e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_GENERICO, e);
        }
    }

    /**
     * Método que realiza a geração de arquivos Excel utilizando JasperReports.
     *
     * @param dados Dados do relatório, serão passados como dados para o template.
     * @param template Template que será usado para geração deste Excel.
     * @param oStream Stream onde será gravado o relatório exportado.
     * @param <D> O tipo dos dados para a geracao do relatorio
     */
    public <D> void gerarExcel(D dados, String template, OutputStream oStream)  {
        List<D> list = new ArrayList<>();
        list.add(dados);
        gerarExcel(list, template, oStream);
    }

    /**
     * Método que realiza a geração de arquivos Excel utilizando JasperReports.
     *
     * @param dados Dados do relatório, serão passados como dados para o template.
     * @param template Template que será usado para geração deste Excel.
     * @param oStream Stream onde será gravado o relatório exportado.
     * @param <D> O tipo dos dados para a geracao do relatorio
     */
    public <D> void gerarExcel(Collection<D> dados, String template, OutputStream oStream)  {
        gerarExcel(dados, new HashMap<>(), template, oStream);
    }

    /**
     * Método que realiza a geração de arquivos Excel utilizando JasperReports.
     *
     * @param dados Dados do relatório, serão passados como dados para o template.
     * @param params Parâmetros a serem enviados para o template.
     * @param template Template que será usado para geração deste Excel.
     * @param oStream Stream onde será gravado o relatório exportado.
     * @param <D> O tipo dos dados para a geracao do relatorio
     */
    public <D> void gerarExcel(Collection<D> dados, Map<String, Object> params, String template, OutputStream oStream)  {
        try (InputStream iStream = this.getClass().getResourceAsStream(String.format(JASPER_FILE_FORMAT, template))) {
            params.put(REPORT_LOCALE_KEY, new Locale(IDIOMA, PAIS));
            params.put(JRParameter.IS_IGNORE_PAGINATION, Boolean.TRUE);
            JasperPrint jasperPrint = JasperFillManager.fillReport(iStream, params, new JRBeanCollectionDataSource(dados));
            JRXlsExporter exporter = new JRXlsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(oStream));
            SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
            configuration.setOnePagePerSheet(false);
            exporter.setConfiguration(configuration);
            exporter.exportReport();
        } catch (IOException | JRException e) {
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_GENERICO, e);
        }
    }
}