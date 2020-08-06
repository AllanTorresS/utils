package ipp.aci.boleia.util.log;

/**
 * Log com as informações do resultado final de uma requisição.
 */
public class LogResult {
    private LogRequest request;
    private Object response;
    private Integer status;
    private Long duration;

    public LogRequest getRequest() {
        return request;
    }
    public void setRequest(LogRequest request) {
        this.request = request;
    }
    public Object getResponse() {
        return response;
    }
    public void setResponse(Object response) {
        this.response = response;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
    public Long getDuration() {
        return duration;
    }
    public void setDuration(Long duration) {
        this.duration = duration;
    }
}