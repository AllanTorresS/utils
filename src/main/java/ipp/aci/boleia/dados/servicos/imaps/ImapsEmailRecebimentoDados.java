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
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
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

/**
 * Implementação do repositório de recebimento de emails.
 */
@Repository
public class ImapsEmailRecebimentoDados implements IEmailRecebimentoDados {

    private static final String NOME_PROTOCOLO = "imaps";
    private static final String EXTENSAO_XML = ".xml";

    private static final Logger LOGGER = LoggerFactory.getLogger(ImapsEmailRecebimentoDados.class);

    @Autowired
    private UtilitarioAmbiente ambiente;

    @Override
    public ImportacaoNfeArmazemVo obterEmailsEntre(ConfiguracaoLeitorEmailVo config, Date dataInicio, Date dataFinal) {
        Date dataInicioLeitura = ambiente.buscarDataAmbiente();
        ImportacaoNfeArmazemVo resultadoLeitura = new ImportacaoNfeArmazemVo();
        IMAPStore emailStore = conectarEmail(config);

        List<Message> mensagens = new ArrayList<>();
        for (String nomeDiretorio: config.getDiretorios()) {

            Folder diretorio = obterDiretorio(emailStore, nomeDiretorio );
            if (diretorio != null) {
                if (dataInicio != null) {
                    if (dataFinal != null) {
                        mensagens.addAll(buscarMensagensRecebidasEmIntervalo(diretorio, dataInicio, dataFinal));
                    } else {
                        mensagens.addAll(buscarMensagensRecebidasAPartirDe(diretorio, dataInicio));
                    }
                } else {
                    mensagens.addAll(buscarMensagens(diretorio));
                }
            }
        }

        List<EmailVo> emails = new ArrayList<>();
        List<Message> mensagensProcessadas = new ArrayList<>();
        for (Message mensagem : mensagens) {

            try {
                if (((dataInicio == null) || dataInicio.before(mensagem.getReceivedDate())) &&
                        ((dataFinal == null) || (mensagem.getReceivedDate().before(dataFinal)))) {
                    mensagensProcessadas.add(mensagem);

                    if (possuiAnexos(mensagem)) {
                        montarObjetoArmazem(config.getMimeTypeAnexo(), emails, mensagem);
                    }
                }
            } catch(MessagingException | RuntimeException e) {
                LOGGER.error(e.getMessage(), e);
            }
        }
        resultadoLeitura.setMensagens(emails);
        resultadoLeitura.setDataLeitura(dataInicioLeitura);
        arquivarMensagens(config, emailStore, mensagensProcessadas);

        try {
            emailStore.close();
        } catch(MessagingException | ExcecaoBoleiaRuntime e) {
            LOGGER.error(e.getMessage(), e);
        }
        return resultadoLeitura;
    }

    /**
     * Copia as mensagens para a pasta de arquivo morto do email e apaga da pasta de origem
     *
     * @param config Objeto contendo as configurações para a sessão
     * @param emailStore conexão IMAP com a conta de e-mail
     * @param mensagens Lista de mensagens a arquivar
     */
    private void arquivarMensagens(ConfiguracaoLeitorEmailVo config, IMAPStore emailStore, List<Message> mensagens) {
        try{
            if (mensagens.isEmpty()) {
                return;
            }
            Folder diretorioArquivamento = emailStore.getFolder(config.getDiretorioArquivamento());
            if (!diretorioArquivamento.exists())
                diretorioArquivamento.create(Folder.HOLDS_MESSAGES);

            for (String nomeDiretorio: config.getDiretorios()) {
                Folder diretorioOrigem = obterDiretorio(emailStore, nomeDiretorio );

                List<Message> mensagensOrigem = new ArrayList<>();
                for (Message mensagem : mensagens) {
                    if (mensagem.getFolder().getName().equals(diretorioOrigem.getName()))
                        mensagensOrigem.add(mensagem);
                }

                Message[] listaMensagens = new Message[mensagensOrigem.size()];
                mensagens.toArray(listaMensagens);
                diretorioOrigem.copyMessages(listaMensagens, diretorioArquivamento);
                diretorioOrigem.setFlags(listaMensagens, new Flags(Flags.Flag.DELETED), true);
                diretorioOrigem.close(true);
            }

        } catch(Exception me) {
            LOGGER.error(me.getMessage(), me);
        }
    }

    @Override
    public ImportacaoNfeArmazemVo obterEmailsAPartirDe(ConfiguracaoLeitorEmailVo config, Date dataInicio) {

        return obterEmailsEntre(config, dataInicio, null);
    }

    @Override
    public ImportacaoNfeArmazemVo obterTodosOsEmails(ConfiguracaoLeitorEmailVo config) {

        return obterEmailsAPartirDe(config, null);
    }

    @Override
    public String getProtocolo() {
        return NOME_PROTOCOLO;
    }

    /**
     * Conecta a uma conta de e-mail via protocolo IMAPs
     * @param config Objeto contendo as configurações para a sessão
     * @return A conexão com a conta IMAP
     */
    private IMAPStore conectarEmail(ConfiguracaoLeitorEmailVo config) {
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

            return emailStore;

        } catch(Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_CONEXAO_LEITOR_EMAIL);
        }
    }

    /**
     * Retorna o diretório escolhido pela configuração, a partir da conexão passada
     * @param emailStore conexão IMAP com a conta de e-mail
     * @param nomeDiretorio  nome do diretório a ser aberto para leitura dos e-mails
     * @return O diretório escolhido da conta de e-mail
     */
    private Folder obterDiretorio(IMAPStore emailStore, String nomeDiretorio) {
        try {
            Folder diretorio = emailStore.getFolder(nomeDiretorio);
            diretorio.open(Folder.READ_WRITE);
            return diretorio;

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return null;
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
        } catch(MessagingException e) {
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
        } catch(MessagingException e) {
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
            Message mensagemInterna = mensagem;
            if (mensagem.getContent()==null) {
                mensagemInterna = new MimeMessage((MimeMessage) mensagem);
            }

            Object conteudo = mensagemInterna.getContent();
            if (conteudo instanceof String){
                return null;
            }

            if (conteudo instanceof Multipart) {
                Multipart multipart = (Multipart) conteudo;
                List<InputStream> resultado = new ArrayList<>();

                for (int i = 0; i < multipart.getCount(); i++) {
                    try {
                        resultado.addAll(obterAnexos(multipart.getBodyPart(i), mimeType));
                    } catch (MessagingException | IOException | RuntimeException e) {
                        LOGGER.error(e.getMessage(), e);
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
    private List<InputStream> obterAnexos(BodyPart parte, String mimeType) throws IOException, MessagingException {
        List<InputStream> resultado = new ArrayList<>();
        Object conteudo = parte.getInputStream();
        if (conteudo instanceof InputStream || conteudo instanceof String) {
            if (Part.ATTACHMENT.equalsIgnoreCase(parte.getDisposition()) &&
                    StringUtils.isNotBlank(parte.getFileName()) &&
                        parte.getFileName().toLowerCase().endsWith(EXTENSAO_XML)
            ) {
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

    /**
     * Verifica se o e-mail possui algum anexo
     * @param mensagem O e-mail a ser verificado
     * @return true se o e-mail possui anexo, false caso contrário
     */
    private Boolean possuiAnexos (Message mensagem) {
        try {
            String mimeTypeMultipart = "multipart/mixed";

            if (!mensagem.getFolder().isOpen()) {
                mensagem.getFolder().open(Folder.READ_WRITE);
            }

            Message mensagemInterna = mensagem;
            if (mensagem.getContent()==null) {
                mensagemInterna = new MimeMessage((MimeMessage) mensagem);
            }
            if (mensagemInterna.isMimeType(mimeTypeMultipart)) {
                Multipart mp = (Multipart) mensagemInterna.getContent();
                if (mp.getCount() > 1) {
                    return true;
                }
            }
            return false;
        } catch(MessagingException | IOException e) {
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

        } catch(MessagingException e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcecaoBoleiaRuntime(Erro.ERRO_BUSCA_MENSAGENS_LEITOR_EMAIL);
        }
    }
}
