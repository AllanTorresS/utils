package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Classe abstrata para implementação de Média de Consumo
 *
 */
public class MediaConsumoVo implements IPertenceFrota {

    private Long idFrota;
    private BigDecimal media;
    private BigDecimal mediaHorHod;
    private BigDecimal mediaTotalLitrosAbastecimento;
    private Integer tipoConsumo;

    private String nomeMotorista;
    private Long cpfMotorista;
    private Long cnpjUnidadeMotorista;
    private String nomeUnidadeMotorista;
    private String codigoGrupoMotorista;
    private String nomeGrupoMotorista;
    private Integer classificacaoMotorista;
    private String razaoSocialEmpresaMotorista;

    private String placaVeiculo;
    private String nomeUnidadeVeiculo;
    private Long cnpjUnidadeVeiculo;
    private String codigoGrupoVeiculo;
    private String nomeGrupoVeiculo;
    private String subTipoVeiculo;
    private Integer classificacaoVeiculo;
    private String razaoSocialEmpresaVeiculo;
    private String razaoSocialFrota;
    private Long cnpjFrota;
    private Integer agregadoVeiculo;


    /**
     * Construtor
     */
    public MediaConsumoVo() {
        // construtor default
    }

    /**
     * Construtor
     *
     * @param idFrota O id da Frota
     * @param mediaHorHod A diferença de hodometro ou horimetro para o calculo da média de consumo
     * @param mediaTotalLitrosAbastecimento O somatorio de todos os abastecimentos(incluindo o ultimo) calculo da média de consumo
     * @param tipoConsumo O tipo de consumo
     * @param razaoSocialFrota  A razão social da Frota
     * @param cnpjFrota O CNPJ da Frota
     */
    protected MediaConsumoVo(Long idFrota, BigDecimal mediaHorHod, BigDecimal mediaTotalLitrosAbastecimento, Integer tipoConsumo, String razaoSocialFrota, Long cnpjFrota) {
        this.idFrota = idFrota;
        this.mediaHorHod = mediaHorHod;
        this.mediaTotalLitrosAbastecimento = mediaTotalLitrosAbastecimento;
        this.tipoConsumo = tipoConsumo;
        this.razaoSocialFrota = razaoSocialFrota;
        this.cnpjFrota = cnpjFrota;
    }

    /**
     * Construtor para consumo de Motorista
     *
     * @param nomeMotorista O nome do Motorista.
     * @param cpfMotorista O Cpf do Motorista
     * @param cnpjUnidadeMotorista O cnpj do Motorista
     * @param nomeUnidadeMotorista O nome da Unidade do Motorista
     * @param codigoGrupoMotorista O código de Grupo do Motorista
     * @param nomeGrupoMotorista O nome do Grupo do Motorista
     * @param classificacaoMotorista A classificao do motorista
     * @param razaoSocialEmpresaMotorista O cnpj da empresa agregada do motorista
     * @param idFrota  O id da Frota
     * @param mediaHorHod A diferença de hodometro ou horimetro para o calculo da média de consumo
     * @param mediaTotalLitrosAbastecimento O somatorio de todos os abastecimentos(incluindo o ultimo) calculo da média de consumo
     * @param tipoConsumo O tipo de consumo realizado
     * @param razaoSocialFrota A razão soscial da Frota
     * @param cnpjFrota O CNPJ da Frota
     */
    public MediaConsumoVo(
            String nomeMotorista, Long cpfMotorista, Long cnpjUnidadeMotorista, String nomeUnidadeMotorista,
            String codigoGrupoMotorista, String nomeGrupoMotorista, Integer classificacaoMotorista, String razaoSocialEmpresaMotorista, Long idFrota, BigDecimal mediaHorHod, BigDecimal mediaTotalLitrosAbastecimento, Integer tipoConsumo,
            String razaoSocialFrota, Long cnpjFrota) {
        this(idFrota, mediaHorHod, mediaTotalLitrosAbastecimento, tipoConsumo, razaoSocialFrota, cnpjFrota);
        this.nomeMotorista = nomeMotorista;
        this.cpfMotorista = cpfMotorista;
        this.cnpjUnidadeMotorista = cnpjUnidadeMotorista;
        this.nomeUnidadeMotorista = nomeUnidadeMotorista;
        this.codigoGrupoMotorista = codigoGrupoMotorista;
        this.nomeGrupoMotorista = nomeGrupoMotorista;
        this.classificacaoMotorista = classificacaoMotorista;
        this.razaoSocialEmpresaMotorista = razaoSocialEmpresaMotorista;
    }

    /**
     * Construtor para Consumo de Veículo
     *
     * @param placaVeiculo A placa do veículo
     * @param nomeUnidadeVeiculo O nome da Unidade do Veículo
     * @param cnpjUnidadeVeiculo O CNPJ da Unidade do Veículo
     * @param codigoGrupoVeiculo O Código do Grupo Operacional do Veículo
     * @param nomeGrupoVeiculo O nome do Grupo Operacional do Veículo
     * @param subTipoVeiculo O sub tipo do veículo
     * @param classificacaoVeiculo A classificao do veiculo
     * @param razaoSocialEmpresaVeiculo Nome da empresa agregada veiculo
     * @param idFrota O id da Frota do Veículo
     * @param mediaHorHod A diferença de hodometro ou horimetro para o calculo da média de consumo
     * @param mediaTotalLitrosAbastecimento O somatorio de todos os abastecimentos(incluindo o ultimo) calculo da média de consumo
     * @param tipoConsumo O tipo de consumo
     * @param razaoSocialFrota A razão Social da Frota
     * @param cnpjFrota O CNPJ da Frota
     */
    public MediaConsumoVo(
            String placaVeiculo, String nomeUnidadeVeiculo, Long cnpjUnidadeVeiculo, String codigoGrupoVeiculo,
            String nomeGrupoVeiculo, String subTipoVeiculo, Integer classificacaoVeiculo, String razaoSocialEmpresaVeiculo, Long idFrota, BigDecimal mediaHorHod, BigDecimal mediaTotalLitrosAbastecimento, Integer tipoConsumo,
            String razaoSocialFrota, Long cnpjFrota) {
        this(idFrota, mediaHorHod, mediaTotalLitrosAbastecimento, tipoConsumo, razaoSocialFrota, cnpjFrota);
        this.placaVeiculo = placaVeiculo;
        this.nomeUnidadeVeiculo = nomeUnidadeVeiculo;
        this.cnpjUnidadeVeiculo = cnpjUnidadeVeiculo;
        this.codigoGrupoVeiculo = codigoGrupoVeiculo;
        this.nomeGrupoVeiculo = nomeGrupoVeiculo;
        this.subTipoVeiculo = subTipoVeiculo;
        this.classificacaoVeiculo = classificacaoVeiculo;
        this.razaoSocialEmpresaVeiculo = razaoSocialEmpresaVeiculo;
    }

    /**
     * Construtor para média consumo Motorista x Veiculo
     *
     * @param nomeMotorista O nome do Motorista
     * @param cpfMotorista O cpf do Motorista
     * @param cnpjUnidadeMotorista O cnpj da Unidade do Motorista
     * @param nomeUnidadeMotorista O nome da Unidade do Motorista
     * @param codigoGrupoMotorista O código do Grupo do Motorista
     * @param nomeGrupoMotorista O nome do Grupo do Motorista
     * @param placaVeiculo A placa do Veículo
     * @param subTipoVeiculo O Subtipo do Veículo
     * @param classificaoMotorista A classificacao do motorista
     * @param razaoSocialEmpresaMotorista O nome da empresa agregada do motorista
     * @param classificaoVeiculo A classificacao do Veiculo
     * @param razaoSocialEmpresaVeiculo O nome da empresa agregada do Veiculo
     * @param idFrota O id da Frota
     * @param mediaHorHod A diferença de hodometro ou horimetro para o calculo da média de consumo
     * @param mediaTotalLitrosAbastecimento O somatorio de todos os abastecimentos(incluindo o ultimo) calculo da média de consumo
     * @param tipoConsumo O tipo de Consumo
     * @param razaoSocialFrota A razão social da Frota
     * @param cnpjFrota O Cnpj da Frota
     */
    public MediaConsumoVo(
            String nomeMotorista, Long cpfMotorista, Long cnpjUnidadeMotorista, String nomeUnidadeMotorista,
            String codigoGrupoMotorista, String nomeGrupoMotorista, String placaVeiculo, String subTipoVeiculo,
            Integer classificaoMotorista, String razaoSocialEmpresaMotorista,
            Integer classificaoVeiculo, String razaoSocialEmpresaVeiculo,
            Long idFrota, BigDecimal mediaHorHod, BigDecimal mediaTotalLitrosAbastecimento, Integer tipoConsumo,
            String razaoSocialFrota, Long cnpjFrota ) {
        this(nomeMotorista, cpfMotorista, cnpjUnidadeMotorista, nomeUnidadeMotorista, codigoGrupoMotorista,
                nomeGrupoMotorista, classificaoMotorista, razaoSocialEmpresaMotorista,  idFrota, mediaHorHod, mediaTotalLitrosAbastecimento, tipoConsumo, razaoSocialFrota, cnpjFrota);

        this.placaVeiculo = placaVeiculo;
        this.subTipoVeiculo = subTipoVeiculo;
        this.classificacaoVeiculo = classificaoVeiculo;
        this.razaoSocialEmpresaVeiculo = razaoSocialEmpresaVeiculo;
    }

    /**
     * Construtor para Consumo de Veículo
     *
     * @param placaVeiculo A placa do veículo
     * @param nomeUnidadeVeiculo O nome da Unidade do Veículo
     * @param cnpjUnidadeVeiculo O CNPJ da Unidade do Veículo
     * @param codigoGrupoVeiculo O Código do Grupo Operacional do Veículo
     * @param nomeGrupoVeiculo O nome do Grupo Operacional do Veículo
     * @param subTipoVeiculo O sub tipo do veículo
     * @param agregadoVeiculo O id do veiculo agregado
     * @param razaoSocialEmpresaVeiculo Nome da empresa agregada veiculo
     * @param idFrota O id da Frota do Veículo
     * @param mediaHorHod media do horimetro ou hodometro
     * @param tipoConsumo O tipo de consumo
     * @param razaoSocialFrota A razão Social da Frota
     * @param cnpjFrota O CNPJ da Frota
     */
    public MediaConsumoVo(
            String placaVeiculo, String nomeUnidadeVeiculo, Long cnpjUnidadeVeiculo, String codigoGrupoVeiculo,
            String nomeGrupoVeiculo, String subTipoVeiculo, Integer agregadoVeiculo, String razaoSocialEmpresaVeiculo, Long idFrota, BigDecimal mediaHorHod, Integer tipoConsumo,
            String razaoSocialFrota, Long cnpjFrota) {
        this.placaVeiculo = placaVeiculo;
        this.nomeUnidadeVeiculo = nomeUnidadeVeiculo;
        this.cnpjUnidadeVeiculo = cnpjUnidadeVeiculo;
        this.codigoGrupoVeiculo = codigoGrupoVeiculo;
        this.nomeGrupoVeiculo = nomeGrupoVeiculo;
        this.subTipoVeiculo = subTipoVeiculo;
        this.agregadoVeiculo = agregadoVeiculo;
        this.razaoSocialEmpresaVeiculo = razaoSocialEmpresaVeiculo;
        this.idFrota = idFrota;
        this.media = mediaHorHod == null ? BigDecimal.ZERO : mediaHorHod;
        this.tipoConsumo = tipoConsumo;
        this.razaoSocialFrota = razaoSocialFrota;
        this.cnpjFrota = cnpjFrota;
    }



    public String getRazaoSocialFrota() {
        return razaoSocialFrota;
    }

    public void setRazaoSocialFrota(String razaoSocialFrota) {
        this.razaoSocialFrota = razaoSocialFrota;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }


    public BigDecimal getMedia() {
        return media;
    }

    public void setMedia(BigDecimal media) {
        this.media = media;
    }

    public BigDecimal getMediaHorHod() {
        return mediaHorHod;
    }

    public void setMediaHorHod(BigDecimal mediaHorHod) {
        this.mediaHorHod = mediaHorHod;
    }

    public BigDecimal getMediaTotalLitrosAbastecimento() {
        return mediaTotalLitrosAbastecimento;
    }

    public void setMediaTotalLitrosAbastecimento(BigDecimal mediaTotalLitrosAbastecimento) {
        this.mediaTotalLitrosAbastecimento = mediaTotalLitrosAbastecimento;
    }

    public Integer getTipoConsumo() {
        return tipoConsumo;
    }

    public void setTipoConsumo(Integer tipoConsumo) {
        this.tipoConsumo = tipoConsumo;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public Long getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(Long cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }

    public Long getCnpjUnidadeMotorista() {
        return cnpjUnidadeMotorista;
    }

    public void setCnpjUnidadeMotorista(Long cnpjUnidadeMotorista) {
        this.cnpjUnidadeMotorista = cnpjUnidadeMotorista;
    }

    public String getNomeUnidadeMotorista() {
        return nomeUnidadeMotorista;
    }

    public void setNomeUnidadeMotorista(String nomeUnidadeMotorista) {
        this.nomeUnidadeMotorista = nomeUnidadeMotorista;
    }

    public String getCodigoGrupoMotorista() {
        return codigoGrupoMotorista;
    }

    public void setCodigoGrupoMotorista(String codigoGrupoMotorista) {
        this.codigoGrupoMotorista = codigoGrupoMotorista;
    }

    public String getNomeGrupoMotorista() {
        return nomeGrupoMotorista;
    }

    public void setNomeGrupoMotorista(String nomeGrupoMotorista) {
        this.nomeGrupoMotorista = nomeGrupoMotorista;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getNomeUnidadeVeiculo() {
        return nomeUnidadeVeiculo;
    }

    public void setNomeUnidadeVeiculo(String nomeUnidadeVeiculo) {
        this.nomeUnidadeVeiculo = nomeUnidadeVeiculo;
    }

    public Long getCnpjUnidadeVeiculo() {
        return cnpjUnidadeVeiculo;
    }

    public void setCnpjUnidadeVeiculo(Long cnpjUnidadeVeiculo) {
        this.cnpjUnidadeVeiculo = cnpjUnidadeVeiculo;
    }

    public String getCodigoGrupoVeiculo() {
        return codigoGrupoVeiculo;
    }

    public void setCodigoGrupoVeiculo(String codigoGrupoVeiculo) {
        this.codigoGrupoVeiculo = codigoGrupoVeiculo;
    }

    public String getNomeGrupoVeiculo() {
        return nomeGrupoVeiculo;
    }

    public void setNomeGrupoVeiculo(String nomeGrupoVeiculo) {
        this.nomeGrupoVeiculo = nomeGrupoVeiculo;
    }

    public String getSubTipoVeiculo() {
        return subTipoVeiculo;
    }

    public void setSubTipoVeiculo(String subTipoVeiculo) {
        this.subTipoVeiculo = subTipoVeiculo;
    }

    public Long getCnpjFrota() {
        return cnpjFrota;
    }

    public void setCnpjFrota(Long cnpjFrota) {
        this.cnpjFrota = cnpjFrota;
    }

    public Integer getClassificacaoMotorista() {
        return classificacaoMotorista;
    }

    public void setClassificacaoMotorista(Integer classificacaoMotorista) {
        this.classificacaoMotorista = classificacaoMotorista;
    }

    public Integer getClassificacaoVeiculo() {
        return classificacaoVeiculo;
    }

    public void setClassificacaoVeiculo(Integer classificacaoVeiculo) {
        this.classificacaoVeiculo = classificacaoVeiculo;
    }

    public String getRazaoSocialEmpresaMotorista() {
        return razaoSocialEmpresaMotorista;
    }

    public void setRazaoSocialEmpresaMotorista(String razaoSocialEmpresaMotorista) {
        this.razaoSocialEmpresaMotorista = razaoSocialEmpresaMotorista;
    }

    public String getRazaoSocialEmpresaVeiculo() {
        return razaoSocialEmpresaVeiculo;
    }

    public void setRazaoSocialEmpresaVeiculo(String razaoSocialEmpresaVeiculo) {
        this.razaoSocialEmpresaVeiculo = razaoSocialEmpresaVeiculo;
    }

    public Integer getAgregadoVeiculo() {
        return agregadoVeiculo;
    }

    public void setAgregadoVeiculo(Integer agregadoVeiculo) {
        this.agregadoVeiculo = agregadoVeiculo;
    }

    @Override
    public List<Frota> getFrotas() {
        Frota frota = new Frota();
        frota.setId(idFrota);
        return Collections.singletonList(frota);
    }
}
