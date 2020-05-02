package ipp.aci.boleia.util.log;

import java.util.HashMap;

public class LogRequest {
    private String client;
    private String url;
    private String httpMethod;
    private HashMap<String, String> parameter;
    private HashMap<String, String> header;
    private Object body;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getHttpMethod() {
        return httpMethod;
    }
    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }
    public HashMap<String, String> getParameter() {
        return parameter;
    }
    public void setParameter(HashMap<String, String> parameter) {
        this.parameter = parameter;
    }
    public HashMap<String, String> getHeader() {
        return header;
    }
    public void setHeader(HashMap<String, String> header) {
        this.header = header;
    }
    public Object getBody() {
        return body;
    }
    public void setBody(Object body) {
        this.body = body;
    }
    public String getClient() {
        return client;
    }
    public void setClient(String client) {
        this.client = client;
    }
}

