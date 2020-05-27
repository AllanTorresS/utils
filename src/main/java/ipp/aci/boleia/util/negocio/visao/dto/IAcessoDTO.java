package ipp.aci.boleia.util.negocio.visao.dto;

/**
 * Representa interface para uma tentativa de acesso ao portal
 */
public interface IAcessoDTO {
    String getLogin();
    void setLogin(String login);
    boolean isInterno();
    void setInterno(boolean interno);
}
