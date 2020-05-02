
package ipp.aci.boleia.dados.servicos.ensemble.jde.contabancaria.jaxws;

import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {


    
    public ObjectFactory() {
    }

    
    public DadosContaBancariaResponse createDadosContaBancariaResponse() {
        return new DadosContaBancariaResponse();
    }

    
    public DadosContaBancariaResp createDadosContaBancariaResp() {
        return new DadosContaBancariaResp();
    }

    
    public DadosContaBancaria createDadosContaBancaria() {
        return new DadosContaBancaria();
    }

    
    public DadosContaBancariaReq createDadosContaBancariaReq() {
        return new DadosContaBancariaReq();
    }

    
    public StatusIntegracao createStatusIntegracao() {
        return new StatusIntegracao();
    }

    
    public EnsMessagebody createEnsMessagebody() {
        return new EnsMessagebody();
    }

    
    public V2 createV2() {
        return new V2();
    }

    
    public EnsResponse createEnsResponse() {
        return new EnsResponse();
    }

    
    public EnsRequest createEnsRequest() {
        return new EnsRequest();
    }

}
