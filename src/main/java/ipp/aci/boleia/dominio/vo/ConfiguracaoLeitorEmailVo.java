package ipp.aci.boleia.dominio.vo;


/**
 * Representa um objeto de configuração para conexão com o leitor de e-mail
 */
public class ConfiguracaoLeitorEmailVo {

    private String host;
    private String porta;
    private String usuario;
    private String senha;
    private String[] diretorios;
    private String mimeTypeAnexo;
    private String diretorioArquivamento;

    /**
     * Construtor padrão
     */
    public ConfiguracaoLeitorEmailVo() {
    }

    /**
     * Instancia o objeto
     *
     * @param host O host do e-mail
     * @param porta A porta utiilizada
     * @param usuario O usuário (endereço de e-mail)
     * @param senha A senha
     * @param diretorios O diretório do e-mail
     * @param mimeTypeAnexo Mime Type do anexo
     * @param diretorioArquivamento Diretório onde os e-mails serão arquivados
     */
    public ConfiguracaoLeitorEmailVo(String host, String porta, String usuario, String senha, String diretorios, String mimeTypeAnexo, String diretorioArquivamento) {
        this.host = host;
        this.porta = porta;
        this.usuario = usuario;
        this.senha = senha;
        this.diretorios = diretorios.replaceAll("\"","").split(",");
        this.mimeTypeAnexo = mimeTypeAnexo;
        this.diretorioArquivamento = diretorioArquivamento;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPorta() {
        return porta;
    }

    public void setPorta(String porta) {
        this.porta = porta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String[] getDiretorios() {
        return diretorios;
    }

    public void setDiretorios(String[] diretorios) {
        this.diretorios = diretorios;
    }

    public String getMimeTypeAnexo() {
        return mimeTypeAnexo;
    }

    public void setMimeTypeAnexo(String mimeTypeAnexo) {
        this.mimeTypeAnexo = mimeTypeAnexo;
    }

    public String getDiretorioArquivamento() { return diretorioArquivamento; }

    public void setDiretorioArquivamento(String diretorioArquivamento) { this.diretorioArquivamento = diretorioArquivamento; }
}
