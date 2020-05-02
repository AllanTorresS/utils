package ipp.aci.boleia.util.log;

import ipp.aci.boleia.dados.servicos.rest.ConsumidorHttp;
import org.apache.http.Header;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.util.HashMap;

/**
 * Classe responsavel por logar a entrada e a saida de todos os metodo da http do sistema.
 */
@Aspect
public class ClienteHttpDadosHandler {
    @Pointcut("execution(* ipp.aci.boleia.dados.IClienteHttpDados.*(..))")
    public void interfaceClienteHttpDados() {}
    @Pointcut("execution(public * *(..))")
    public void publicMethod() {}
    
    /**
     * Metodo que encapsula todos os metodos publicos dos controllers do sistema, com o objetivo de logar as chamadas HTTP.
     * @param point o ProceedingJoinPoint para execucao do metodo original
     * @return o retorno do metodo encapsulado
     */
    @Around("publicMethod() && interfaceClienteHttpDados()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        LogResult log;
        long start = System.currentTimeMillis();
        Object result = null;
        result = point.proceed();
        long end = System.currentTimeMillis();
        switch(MethodSignature.class.cast(point.getSignature()).getMethod().getName()){
            case "doGet":
                log = doGet(point);
                break;
            case "doPostJson":
                log = doPostJson(point);
                break;
            case "doPostFormEncoded":
                log = doPostFormEncoded(point);
                break;
            case "doPutJson":
                log = doPutJson(point);
                break;
            case "doDelete":
                log = doDelete(point);
                break;
            case "doPatchJson":
                log = doPatchJson(point);
                break;
            default:
                log = new LogResult();
        }
        log.setDuration(end - start);
        log.setResponse(result);
        return result;
    }

    /**
     * Monta o log dos metodos GET HTTP.
     * @param point o ProceedingJoinPoint para pegar os argumentos dos metodos
     * @return o log montado
     */
    private LogResult doGet(ProceedingJoinPoint point) {
        LogResult log = new LogResult();
        LogRequest request = new LogRequest();
        request.setHttpMethod("GET");
        Object[] args = point.getArgs();
        if(args.length == 2){
            String url = (String) args[0];
            ConsumidorHttp<Object> consumidorHttp = (ConsumidorHttp<Object>) args[1];
            request.setUrl(url);
        }else if(args.length == 3){
            String url = (String) args[0];
            Header[] headers = (Header[]) args[1];
            ConsumidorHttp<Object> consumidorHttp = (ConsumidorHttp<Object>) args[2];
            request.setUrl(url);
            request.setHeader(getHeader(headers));
        } else if(args.length == 5){
            String url= (String) args[0];
            String username= (String) args[1];
            String password= (String) args[2];
            Header[] headers= (Header[]) args[3];
            ConsumidorHttp<Object> consumidorHttp =(ConsumidorHttp<Object>) args[4];
            request.setUrl(url);
            request.setHeader(getHeader(headers));
        }
        log.setRequest(request);
        return log;
    }

    private HashMap<String, String> getHeader(Header[] headerList) {
        if(headerList == null){
            return null;
        }
        HashMap<String, String> headers = new HashMap<>();
        for(Header header:headerList){
            headers.put(header.getName(),header.getValue());
        }
        return headers;
    }

    /**
     * Monta o log dos metodos Post HTTP com body json.
     * @param point o ProceedingJoinPoint para pegar os argumentos dos metodos
     * @return o log montado
     */
    private LogResult doPostJson(ProceedingJoinPoint point){
        LogResult log = new LogResult();
        LogRequest request = new LogRequest();
        request.setHttpMethod("POST");
        Object[] args = point.getArgs();
        String url = (String) args[0];
        request.setUrl(url);
        Object body = null;
        Header[] headers = null;
        if(args.length == 4) {
            body = args[1];
            headers = (Header[])args[2];
        }
        if(args.length == 6) {
            body = args[3];
            headers = (Header[])args[4];
        }
        request.setBody(body);
        request.setHeader(getHeader(headers));
        log.setRequest(request);
        return log;
    }
    
    /**
     * Monta o log dos metodos POST HTTP com body Form.
     * @param point o ProceedingJoinPoint para pegar os argumentos dos metodos
     * @return o log montado
     */
    private LogResult doPostFormEncoded(ProceedingJoinPoint point) {
        LogResult log = new LogResult();
        LogRequest request = new LogRequest();
        request.setHttpMethod("POST");
        Object[] args = point.getArgs();
        String url = (String) args[0];
        Object body = args[1];
        request.setUrl(url);
        request.setBody(body);
        log.setRequest(request);
        return log;
    }

    /**
     * Monta o log dos metodos PUT HTTP com body json.
     * @param point o ProceedingJoinPoint para pegar os argumentos dos metodos
     * @return o log montado
     */
    private LogResult doPutJson(ProceedingJoinPoint point) {
        LogResult log = new LogResult();
        LogRequest request = new LogRequest();
        request.setHttpMethod("PUT");
        Object[] args = point.getArgs();
        String url = (String) args[0];
        Object body = null;
        Header[] headers = null;
        if(args.length == 4) {
            body = args[1];
            headers = (Header[])args[2];
        }
        if(args.length == 6) {
            body = args[3];
            headers = (Header[])args[4];
        }
        request.setUrl(url);
        request.setBody(body);
        request.setHeader(getHeader(headers));
        log.setRequest(request);
        return log;
    }

    /**
     * Monta o log dos metodos DELETE HTTP.
     * @param point o ProceedingJoinPoint para pegar os argumentos dos metodos
     * @return o log montado
     */
    private LogResult doDelete(ProceedingJoinPoint point){
        LogResult log = new LogResult();
        LogRequest request = new LogRequest();
        request.setHttpMethod("DELETE");
        Object[] args = point.getArgs();
        String url = (String) args[0];
        Header[] headers = null;
        if(args.length == 3) {
            headers= (Header[])args[1];
        }
        if(args.length == 5) {
            headers= (Header[])args[3];
        }
        request.setUrl(url);
        request.setHeader(getHeader(headers));
        log.setRequest(request);
        return log;
    }

    
    /**
     * Monta o log dos metodos PATCH HTTP com body json.
     * @param point o ProceedingJoinPoint para pegar os argumentos dos metodos
     * @return o log montado
     */
    private LogResult doPatchJson(ProceedingJoinPoint point){
        LogResult log = new LogResult();
        LogRequest request = new LogRequest();
        request.setHttpMethod("PATCH");
        Object[] args = point.getArgs();
        String url = (String) args[0];
        Object body = args[1];
        Header[] headers = (Header[]) args[2];
        request.setUrl(url);
        request.setBody(body);
        request.setHeader(getHeader(headers));
        log.setRequest(request);
        return log;
    }
}