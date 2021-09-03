package ipp.aci.boleia.util.negocio.visao.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Representa a interface para uma nota de compra exibida no aplicativo do motorista
 */
public interface INotaMotoristaDTO {

    Long getId();
    void setId(Long id);
    Date getData();
    void setData(Date data);
    Long getPostoId();
    String getPostoNome();
    String getPostoEndereco();
    IItemNotaMotoristaDTO getAbastecimento();
    void setAbastecimento(IItemNotaMotoristaDTO abastecimento);
    List<IItemNotaMotoristaDTO> getProdServ();
    BigDecimal getTotal();
    void setTotal(BigDecimal total);
    String getPlaca();
    void setPlaca(String placa);
    BigDecimal getKilometragem();
    String getKilometragemTipo();
    Boolean getAvaliacaoPendente();
    Integer getPontosAcumuladosKmv();
}
