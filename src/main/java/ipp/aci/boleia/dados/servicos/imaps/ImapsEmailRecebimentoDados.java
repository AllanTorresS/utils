package ipp.aci.boleia.dados.servicos.imaps;

import com.sun.mail.imap.IMAPStore;
import ipp.aci.boleia.dados.IEmailRecebimentoDados;
import ipp.aci.boleia.dominio.vo.ConfiguracaoLeitorEmailVo;
import ipp.aci.boleia.dominio.vo.EmailVo;
import ipp.aci.boleia.dominio.vo.ImportacaoNfeArmazemVo;
import ipp.aci.boleia.util.UtilitarioLambda;
import ipp.aci.boleia.util.UtilitarioStreams;
import ipp.aci.boleia.util.excecao.Erro;
import ipp.aci.boleia.util.excecao.ExcecaoBoleiaRuntime;
import ipp.aci.boleia.util.negocio.UtilitarioAmbiente;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

@Repository
public class ImapsEmailRecebimentoDados implements IEmailRecebimentoDados {

    private static final String NOME_PROTOCOLO = "imaps";

    private static final Logger LOGGER = LoggerFactory.getLogger(ImapsEmailRecebimentoDados.class);

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Override
    public ImportacaoNfeArmazemVo obterEmailsAPartirDe(ConfiguracaoLeitorEmailVo config, Date dataInicio) {
        Date dataInicioLeitura = ambiente.buscarDataAmbiente();
        ImportacaoNfeArmazemVo resultadoLeitura = new ImportacaoNfeArmazemVo();
        Folder diretorio = conectarEObterDiretorio(config);
        List<Message> mensagens = buscarMensagensRecebidasAPartirDe(diretorio, dataInicio);

        List<EmailVo> emails = new ArrayList<>();
        for (Message mensagem : mensagens) {
            if(possuiAnexos(mensagem)) {
                montarObjetoArmazem(config.getMimeTypeAnexo(), emails, mensagem);
            }
            else {
                continue;
            }
        }
        resultadoLeitura.setMensagens(emails);
        resultadoLeitura.setDataLeitura(dataInicioLeitura);

        return resultadoLeitura;
    }

    @Override
    public ImportacaoNfeArmazemVo obterTodosOsEmails(ConfiguracaoLeitorEmailVo config) {
        Date dataInicioLeitura = ambiente.buscarDataAmbiente();
        ImportacaoNfeArmazemVo resultadoLeitura = new ImportacaoNfeArmazemVo();
        Folder diretorio = conectarEObterDiretorio(config);
        List<Message> mensagens = buscarMensagens(diretorio);

        List<EmailVo> emails = new ArrayList<>();
        for (Message mensagem : mensagens) {
            if(possuiAnexos(mensagem)) {
                montarObjetoArmazem(config.getMimeTypeAnexo(), emails, mensagem);
            }
            else {
                continue;
            }
        }
        resultadoLeitura.setMensagens(emails);
        resultadoLeitura.setDataLeitura(dataInicioLeitura);

        return resultadoLeitura;
    }

    @Override
    public String getProtocolo() {
        return NOME_PROTOCOLO;
    }

    /**
     * Conecta a uma conta de e-mail via protocolo IMAPs e retorna o diretório escolhido pela configuração
     * @param config Objeto contendo as configurações para a sessão
     * @return O diretório escolhido da conta de e-mail
     */
    private Folder conectarEObterDiretorio(ConfiguracaoLeitorEmailVo config) {
        try {
            Properties propriedadesConexao = new Properties();

            propriedadesConexao.put("mail.store.protocol", this.getProtocolo());
            propriedadesConexao.put("mail.imaps.host", config.getHost());
            if (config.getPorta() != null) {
                propriedadesConexao.put("mail.imaps.port", config.getPorta());
            }

            Session sessao = Session.getInstance(propriedadesConexao, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(config.getUsuario(), config.getSenha());
                }
            });
            IMAPStore emailStore = (IMAPStore) sessao.getStore();
            emailStore.connect();

            Folder diretorio = emailStore.getFolder(config.getDiretorio());
            diretorio.open(Folder.READ_ONLY);

            return diretorio;
        }
        catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_CONEXAO_LEITOR_EMAIL);
        }
    }

    /**
     * Busca mensagens recebidas dentro de um intervalo estabelecido
     * @param diretorioEmail Diretório onde as mensagens serão buscadas
     * @param dataInicio Data de início da busca
     * @return Lista de mensagens dentro do intervalo de recebimento estabelecido
     */
    private List<Message> buscarMensagensRecebidasAPartirDe(Folder diretorioEmail, Date dataInicio) {
        return buscarMensagensRecebidasEmIntervalo(diretorioEmail, dataInicio, null);
    }

    /**
     * Busca todas as mensagens
     * @param diretorioEmail Diretório onde as mensagens serão buscadas
     * @return Lista de mensagens dentro do intervalo de recebimento estabelecido
     */
    private List<Message> buscarMensagens(Folder diretorioEmail) {
        try {
            return Arrays.asList(diretorioEmail.getMessages());
        }
        catch(MessagingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_BUSCA_MENSAGENS_LEITOR_EMAIL);
        }
    }

    /**
     * Busca mensagens recebidas dentro de um intervalo estabelecido
     * @param diretorioEmail Diretório onde as mensagens serão buscadas
     * @param dataInicio Data de início da busca
     * @param dataFim Data de fim da busca
     * @return Lista de mensagens dentro do intervalo de recebimento estabelecido
     */
    private List<Message> buscarMensagensRecebidasEmIntervalo(Folder diretorioEmail, Date dataInicio, Date dataFim) {
        try {
            ReceivedDateTerm termoDataInicio = new ReceivedDateTerm(ComparisonTerm.GE, dataInicio);
            SearchTerm termoBusca = termoDataInicio;

            if (dataFim != null) {
                ReceivedDateTerm termoDataFim = new ReceivedDateTerm(ComparisonTerm.LE, dataFim);
                termoBusca = new AndTerm(termoBusca, termoDataFim);
            }

            return Arrays.asList(diretorioEmail.search(termoBusca));
        }
        catch(MessagingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_BUSCA_MENSAGENS_LEITOR_EMAIL);
        }
    }

    /**
     * Dado uma mensagem, obtém seus anexos
     * @param mensagem A mensagem aonde os anexos serão buscados
     * @param mimeType O Mime Type a ser buscado
     * @return Lista de anexos em formato de InputStream
     */
    private List<InputStream> obterAnexos(Message mensagem, String mimeType) {
        try {
            Object conteudo = mensagem.getContent();
            if (conteudo instanceof String)
                return null;

            if (conteudo instanceof Multipart) {
                Multipart multipart = (Multipart) conteudo;
                List<InputStream> resultado = new ArrayList<>();

                for (int i = 0; i < multipart.getCount(); i++) {
                    try {
                        resultado.addAll(obterAnexos(multipart.getBodyPart(i), mimeType));
                    }
                    catch(ExcecaoBoleiaRuntime e) {
                        continue;
                    }
                }
                return resultado;

            }
            return null;
        }
        catch(MessagingException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_OBTER_ANEXOS_LEITOR_EMAIL);
        }
    }

    /**
     * Dado uma parte da mensagem, busca anexos de um determinado mime type
     * @param parte A parte em questão
     * @param mimeType O Mime Type sendo buscado
     * @return Lista de anexos em formato de InputStream
     */
    private List<InputStream> obterAnexos(BodyPart parte, String mimeType) {
        try {
            List<InputStream> resultado = new ArrayList<>();
            Object conteudo = parte.getInputStream();
            if (conteudo instanceof InputStream || conteudo instanceof String) {
                if ((Part.ATTACHMENT.equalsIgnoreCase(parte.getDisposition()) || StringUtils.isNotBlank(parte.getFileName())) && parte.isMimeType(mimeType)) {
                    resultado.add(parte.getInputStream());
                    return resultado;
                } else {
                    return new ArrayList<>();
                }
            }
            if (conteudo instanceof Multipart) {
                Multipart multipart = (Multipart) conteudo;
                for (int i = 0; i < multipart.getCount(); i++) {
                    BodyPart bodyPart = multipart.getBodyPart(i);
                    resultado.addAll(obterAnexos(bodyPart, mimeType));
                }
            }
            return resultado;
        }
        catch (MessagingException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_OBTER_ANEXOS_LEITOR_EMAIL);
        }
    }

    /**
     * Verifica se o e-mail possui algum anexo
     * @param mensagem O e-mail a ser verificado
     * @return true se o e-mail possui anexo, false caso contrário
     */
    private Boolean possuiAnexos (Message mensagem) {
        try {
            String mimeTypeMultipart = "multipart/mixed";
            if (mensagem.isMimeType(mimeTypeMultipart)) {
                Multipart mp = (Multipart) mensagem.getContent();
                if (mp.getCount() > 1) {
                    return true;
                }
            }
            return false;
        }
        catch(MessagingException | IOException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_OBTER_ANEXOS_LEITOR_EMAIL);
        }
    }

    /**
     * Monta um objeto EmailVo a partir de um objeto Message
     * @param mimeTypeAnexo Mime Type a ser buscado no anexo do e-mail
     * @param emails Lista de e-mails aonde os objetos serão adicionados
     * @param mensagem Objeto original representando um e-mail
     */
    private void montarObjetoArmazem(String mimeTypeAnexo, List<EmailVo> emails, Message mensagem) {
        try {
            Address[] remetentes = mensagem.getFrom();
            String emailRemetente = remetentes != null ? ((InternetAddress) UtilitarioLambda.obterPrimeiroObjetoDaLista(Arrays.asList(remetentes))).getAddress() : null;
            EmailVo emailVo = new EmailVo();
            emailVo.setRemetente(emailRemetente);
            emailVo.setDataEnvio(mensagem.getSentDate());
            emailVo.setDataRecebimento(mensagem.getReceivedDate());
            List<String> anexosBase64 = obterAnexos(mensagem, mimeTypeAnexo).stream().map(anexo -> Base64.encodeBase64String((UtilitarioStreams.carregarEmMemoria(anexo)))).collect(Collectors.toList());
            emailVo.setAnexos(anexosBase64);
            emails.add(emailVo);
        }
        catch(MessagingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_BUSCA_MENSAGENS_LEITOR_EMAIL);
        }
    }
}
