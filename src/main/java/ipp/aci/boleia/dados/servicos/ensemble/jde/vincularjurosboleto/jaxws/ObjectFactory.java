
package ipp.aci.boleia.dados.servicos.ensemble.jde.vincularjurosboleto.jaxws;

import javax.xml.bind.annotation.XmlRegistry;


@XmlRegistry
public class ObjectFactory {


    
    public ObjectFactory() {
    }

    
    public Vincular createVincular() {
        return new Vincular();
    }

    
    public Request createRequest() {
        return new Request();
    }

    
    public VincularResponse createVincularResponse() {
        return new VincularResponse();
    }

    
    public Response createResponse() {
        return new Response();
    }

    
    public EnsMessagebody createEnsMessagebody() {
        return new EnsMessagebody();
    }

    
    public EnsResponse createEnsResponse() {
        return new EnsResponse();
    }

    
    public EnsRequest createEnsRequest() {
        return new EnsRequest();
    }

    
    public E1Message createE1Message() {
        return new E1Message();
    }

    
    public E1MessageList createE1MessageList() {
        return new E1MessageList();
    }

    
    public ArrayOfE1Message createArrayOfE1Message() {
        return new ArrayOfE1Message();
    }

    
    public StatusIntegracao createStatusIntegracao() {
        return new StatusIntegracao();
    }

    
    public V3 createV3() {
        return new V3();
    }

}
