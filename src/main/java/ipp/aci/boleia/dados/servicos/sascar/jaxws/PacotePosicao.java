
package ipp.aci.boleia.dados.servicos.sascar.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pacotePosicao", propOrder = {
    "anguloReferencia",
    "bloqueio",
    "ccid",
    "cidade",
    "codigoMacro",
    "conteudoMensagem",
    "dataPacote",
    "dataPosicao",
    "direcao",
    "distanciaReferencia",
    "embreagem",
    "entrada1",
    "entrada2",
    "entrada3",
    "entrada4",
    "entrada5",
    "entrada6",
    "entrada7",
    "entrada8",
    "estadoLimpadorParabrisa",
    "eventoFormatado",
    "eventoSeqFormatado",
    "eventoSequenciamento",
    "eventos",
    "eventosTelemetria",
    "freio",
    "gps",
    "horimetro",
    "idMotorista",
    "idPacote",
    "idReferencia",
    "idVeiculo",
    "ignicao",
    "integradoraId",
    "jamming",
    "latitude",
    "longitude",
    "memoria",
    "motorFuncionando",
    "nomeMensagem",
    "nomeMotorista",
    "odometro",
    "pontoEntrada",
    "pontoReferencia",
    "pontoSaida",
    "rf",
    "rpm",
    "rua",
    "saida1",
    "saida2",
    "saida3",
    "saida4",
    "saida5",
    "saida6",
    "saida7",
    "saida8",
    "satelite",
    "temperatura1",
    "temperatura2",
    "temperatura3",
    "tensao",
    "textoMensagem",
    "tipoTeclado",
    "uf",
    "velocidade"
})
public class PacotePosicao {

    protected Integer anguloReferencia;
    protected Integer bloqueio;
    protected String ccid;
    protected String cidade;
    protected Integer codigoMacro;
    protected String conteudoMensagem;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPacote;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataPosicao;
    protected Integer direcao;
    protected Integer distanciaReferencia;
    protected Integer embreagem;
    protected Integer entrada1;
    protected Integer entrada2;
    protected Integer entrada3;
    protected Integer entrada4;
    protected Integer entrada5;
    protected Integer entrada6;
    protected Integer entrada7;
    protected Integer entrada8;
    protected Integer estadoLimpadorParabrisa;
    protected String eventoFormatado;
    protected String eventoSeqFormatado;
    @XmlElement(nillable = true)
    protected List<EventoSequenciamento> eventoSequenciamento;
    @XmlElement(nillable = true)
    protected List<Evento> eventos;
    protected EventoTelemetriaContainer eventosTelemetria;
    protected Integer freio;
    protected Integer gps;
    protected Integer horimetro;
    protected Integer idMotorista;
    protected long idPacote;
    protected Integer idReferencia;
    protected Integer idVeiculo;
    protected Integer ignicao;
    protected Integer integradoraId;
    protected Integer jamming;
    protected Double latitude;
    protected Double longitude;
    protected Integer memoria;
    protected Integer motorFuncionando;
    protected String nomeMensagem;
    protected String nomeMotorista;
    protected Integer odometro;
    protected Integer pontoEntrada;
    protected String pontoReferencia;
    protected Integer pontoSaida;
    protected Long rf;
    protected Integer rpm;
    protected String rua;
    protected Integer saida1;
    protected Integer saida2;
    protected Integer saida3;
    protected Integer saida4;
    protected Integer saida5;
    protected Integer saida6;
    protected Integer saida7;
    protected Integer saida8;
    protected Integer satelite;
    protected Integer temperatura1;
    protected Integer temperatura2;
    protected Integer temperatura3;
    protected Integer tensao;
    protected String textoMensagem;
    protected Integer tipoTeclado;
    protected String uf;
    protected Integer velocidade;

    
    public Integer getAnguloReferencia() {
        return anguloReferencia;
    }

    
    public void setAnguloReferencia(Integer value) {
        this.anguloReferencia = value;
    }

    
    public Integer getBloqueio() {
        return bloqueio;
    }

    
    public void setBloqueio(Integer value) {
        this.bloqueio = value;
    }

    
    public String getCcid() {
        return ccid;
    }

    
    public void setCcid(String value) {
        this.ccid = value;
    }

    
    public String getCidade() {
        return cidade;
    }

    
    public void setCidade(String value) {
        this.cidade = value;
    }

    
    public Integer getCodigoMacro() {
        return codigoMacro;
    }

    
    public void setCodigoMacro(Integer value) {
        this.codigoMacro = value;
    }

    
    public String getConteudoMensagem() {
        return conteudoMensagem;
    }

    
    public void setConteudoMensagem(String value) {
        this.conteudoMensagem = value;
    }

    
    public XMLGregorianCalendar getDataPacote() {
        return dataPacote;
    }

    
    public void setDataPacote(XMLGregorianCalendar value) {
        this.dataPacote = value;
    }

    
    public XMLGregorianCalendar getDataPosicao() {
        return dataPosicao;
    }

    
    public void setDataPosicao(XMLGregorianCalendar value) {
        this.dataPosicao = value;
    }

    
    public Integer getDirecao() {
        return direcao;
    }

    
    public void setDirecao(Integer value) {
        this.direcao = value;
    }

    
    public Integer getDistanciaReferencia() {
        return distanciaReferencia;
    }

    
    public void setDistanciaReferencia(Integer value) {
        this.distanciaReferencia = value;
    }

    
    public Integer getEmbreagem() {
        return embreagem;
    }

    
    public void setEmbreagem(Integer value) {
        this.embreagem = value;
    }

    
    public Integer getEntrada1() {
        return entrada1;
    }

    
    public void setEntrada1(Integer value) {
        this.entrada1 = value;
    }

    
    public Integer getEntrada2() {
        return entrada2;
    }

    
    public void setEntrada2(Integer value) {
        this.entrada2 = value;
    }

    
    public Integer getEntrada3() {
        return entrada3;
    }

    
    public void setEntrada3(Integer value) {
        this.entrada3 = value;
    }

    
    public Integer getEntrada4() {
        return entrada4;
    }

    
    public void setEntrada4(Integer value) {
        this.entrada4 = value;
    }

    
    public Integer getEntrada5() {
        return entrada5;
    }

    
    public void setEntrada5(Integer value) {
        this.entrada5 = value;
    }

    
    public Integer getEntrada6() {
        return entrada6;
    }

    
    public void setEntrada6(Integer value) {
        this.entrada6 = value;
    }

    
    public Integer getEntrada7() {
        return entrada7;
    }

    
    public void setEntrada7(Integer value) {
        this.entrada7 = value;
    }

    
    public Integer getEntrada8() {
        return entrada8;
    }

    
    public void setEntrada8(Integer value) {
        this.entrada8 = value;
    }

    
    public Integer getEstadoLimpadorParabrisa() {
        return estadoLimpadorParabrisa;
    }

    
    public void setEstadoLimpadorParabrisa(Integer value) {
        this.estadoLimpadorParabrisa = value;
    }

    
    public String getEventoFormatado() {
        return eventoFormatado;
    }

    
    public void setEventoFormatado(String value) {
        this.eventoFormatado = value;
    }

    
    public String getEventoSeqFormatado() {
        return eventoSeqFormatado;
    }

    
    public void setEventoSeqFormatado(String value) {
        this.eventoSeqFormatado = value;
    }

    
    public List<EventoSequenciamento> getEventoSequenciamento() {
        if (eventoSequenciamento == null) {
            eventoSequenciamento = new ArrayList<EventoSequenciamento>();
        }
        return this.eventoSequenciamento;
    }

    
    public List<Evento> getEventos() {
        if (eventos == null) {
            eventos = new ArrayList<Evento>();
        }
        return this.eventos;
    }

    
    public EventoTelemetriaContainer getEventosTelemetria() {
        return eventosTelemetria;
    }

    
    public void setEventosTelemetria(EventoTelemetriaContainer value) {
        this.eventosTelemetria = value;
    }

    
    public Integer getFreio() {
        return freio;
    }

    
    public void setFreio(Integer value) {
        this.freio = value;
    }

    
    public Integer getGps() {
        return gps;
    }

    
    public void setGps(Integer value) {
        this.gps = value;
    }

    
    public Integer getHorimetro() {
        return horimetro;
    }

    
    public void setHorimetro(Integer value) {
        this.horimetro = value;
    }

    
    public Integer getIdMotorista() {
        return idMotorista;
    }

    
    public void setIdMotorista(Integer value) {
        this.idMotorista = value;
    }

    
    public long getIdPacote() {
        return idPacote;
    }

    
    public void setIdPacote(long value) {
        this.idPacote = value;
    }

    
    public Integer getIdReferencia() {
        return idReferencia;
    }

    
    public void setIdReferencia(Integer value) {
        this.idReferencia = value;
    }

    
    public Integer getIdVeiculo() {
        return idVeiculo;
    }

    
    public void setIdVeiculo(Integer value) {
        this.idVeiculo = value;
    }

    
    public Integer getIgnicao() {
        return ignicao;
    }

    
    public void setIgnicao(Integer value) {
        this.ignicao = value;
    }

    
    public Integer getIntegradoraId() {
        return integradoraId;
    }

    
    public void setIntegradoraId(Integer value) {
        this.integradoraId = value;
    }

    
    public Integer getJamming() {
        return jamming;
    }

    
    public void setJamming(Integer value) {
        this.jamming = value;
    }

    
    public Double getLatitude() {
        return latitude;
    }

    
    public void setLatitude(Double value) {
        this.latitude = value;
    }

    
    public Double getLongitude() {
        return longitude;
    }

    
    public void setLongitude(Double value) {
        this.longitude = value;
    }

    
    public Integer getMemoria() {
        return memoria;
    }

    
    public void setMemoria(Integer value) {
        this.memoria = value;
    }

    
    public Integer getMotorFuncionando() {
        return motorFuncionando;
    }

    
    public void setMotorFuncionando(Integer value) {
        this.motorFuncionando = value;
    }

    
    public String getNomeMensagem() {
        return nomeMensagem;
    }

    
    public void setNomeMensagem(String value) {
        this.nomeMensagem = value;
    }

    
    public String getNomeMotorista() {
        return nomeMotorista;
    }

    
    public void setNomeMotorista(String value) {
        this.nomeMotorista = value;
    }

    
    public Integer getOdometro() {
        return odometro;
    }

    
    public void setOdometro(Integer value) {
        this.odometro = value;
    }

    
    public Integer getPontoEntrada() {
        return pontoEntrada;
    }

    
    public void setPontoEntrada(Integer value) {
        this.pontoEntrada = value;
    }

    
    public String getPontoReferencia() {
        return pontoReferencia;
    }

    
    public void setPontoReferencia(String value) {
        this.pontoReferencia = value;
    }

    
    public Integer getPontoSaida() {
        return pontoSaida;
    }

    
    public void setPontoSaida(Integer value) {
        this.pontoSaida = value;
    }

    
    public Long getRf() {
        return rf;
    }

    
    public void setRf(Long value) {
        this.rf = value;
    }

    
    public Integer getRpm() {
        return rpm;
    }

    
    public void setRpm(Integer value) {
        this.rpm = value;
    }

    
    public String getRua() {
        return rua;
    }

    
    public void setRua(String value) {
        this.rua = value;
    }

    
    public Integer getSaida1() {
        return saida1;
    }

    
    public void setSaida1(Integer value) {
        this.saida1 = value;
    }

    
    public Integer getSaida2() {
        return saida2;
    }

    
    public void setSaida2(Integer value) {
        this.saida2 = value;
    }

    
    public Integer getSaida3() {
        return saida3;
    }

    
    public void setSaida3(Integer value) {
        this.saida3 = value;
    }

    
    public Integer getSaida4() {
        return saida4;
    }

    
    public void setSaida4(Integer value) {
        this.saida4 = value;
    }

    
    public Integer getSaida5() {
        return saida5;
    }

    
    public void setSaida5(Integer value) {
        this.saida5 = value;
    }

    
    public Integer getSaida6() {
        return saida6;
    }

    
    public void setSaida6(Integer value) {
        this.saida6 = value;
    }

    
    public Integer getSaida7() {
        return saida7;
    }

    
    public void setSaida7(Integer value) {
        this.saida7 = value;
    }

    
    public Integer getSaida8() {
        return saida8;
    }

    
    public void setSaida8(Integer value) {
        this.saida8 = value;
    }

    
    public Integer getSatelite() {
        return satelite;
    }

    
    public void setSatelite(Integer value) {
        this.satelite = value;
    }

    
    public Integer getTemperatura1() {
        return temperatura1;
    }

    
    public void setTemperatura1(Integer value) {
        this.temperatura1 = value;
    }

    
    public Integer getTemperatura2() {
        return temperatura2;
    }

    
    public void setTemperatura2(Integer value) {
        this.temperatura2 = value;
    }

    
    public Integer getTemperatura3() {
        return temperatura3;
    }

    
    public void setTemperatura3(Integer value) {
        this.temperatura3 = value;
    }

    
    public Integer getTensao() {
        return tensao;
    }

    
    public void setTensao(Integer value) {
        this.tensao = value;
    }

    
    public String getTextoMensagem() {
        return textoMensagem;
    }

    
    public void setTextoMensagem(String value) {
        this.textoMensagem = value;
    }

    
    public Integer getTipoTeclado() {
        return tipoTeclado;
    }

    
    public void setTipoTeclado(Integer value) {
        this.tipoTeclado = value;
    }

    
    public String getUf() {
        return uf;
    }

    
    public void setUf(String value) {
        this.uf = value;
    }

    
    public Integer getVelocidade() {
        return velocidade;
    }

    
    public void setVelocidade(Integer value) {
        this.velocidade = value;
    }

}
