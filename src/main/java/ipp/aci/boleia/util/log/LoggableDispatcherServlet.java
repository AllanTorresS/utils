package ipp.aci.boleia.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

/**
 * Classe responsavel por controlar o fluxo de todas as requisições do sistema para serem logadas.
 */
public class LoggableDispatcherServlet extends DispatcherServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggableDispatcherServlet.class);
    
    private static final List<String> CONTENT_TYPE_NAO_LOGAVEIS = Arrays.asList("image/jpeg","application/pdf","application/vnd.ms-excel");

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        HandlerExecutionChain handler = getHandler(request);
        try {
            super.doDispatch(request, response);
        } finally {
            if(handler.getHandler() instanceof HandlerMethod) {
                HandlerMethod handlerMethod = (HandlerMethod) handler.getHandler();
                if (handlerMethod.getResolvedFromHandlerMethod()!=null
                        && handlerMethod.getResolvedFromHandlerMethod().getBeanType()!=null
                        && handlerMethod.getResolvedFromHandlerMethod().getBeanType().getName().contains("ipp.aci.boleia.visao.")){
                    log(request, response);
                }
            }
            updateResponse(response);
        }
    }
    
    /**
     * Loga o request e o response e o response de uma requisicao.
     * @param requestToCache o request
     * @param responseToCache o response
     */
    private void log(HttpServletRequest requestToCache, HttpServletResponse responseToCache) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(requestToCache.getRemoteAddr())
                .append(" (")
                .append(requestToCache.getMethod())
                .append(") ")
                .append(requestToCache.getRequestURI());
        String params = getParameters(requestToCache);
        if(params != null) {
            stringBuilder
                    .append(" Params<")
                    .append(params)
                    .append(">");
        }
        String body = getRequestBody(requestToCache);
        if(body != null) {
            stringBuilder
                    .append(" Body<")
                    .append(body)
                    .append(">");
        }
        String header = getHeadersInfo(requestToCache);
        if(LOGGER.isDebugEnabled() && header != null){
            stringBuilder.append(" Headers<")
                    .append(header)
                    .append(">");
        }
        stringBuilder
                .append(" [")
                .append(responseToCache.getStatus())
                .append("] : ")
                .append(getResponsePayload(responseToCache));
        LOGGER.info(stringBuilder.toString());

    }
    
    /**
     * Obtem a response como texto
     * @param response o response
     * @return o texto do response
     */
    private String getResponsePayload(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            if (wrapper.getContentType() != null && CONTENT_TYPE_NAO_LOGAVEIS.contains(wrapper.getContentType().toLowerCase())){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder
                        .append("[")
                        .append(wrapper.getContentType())
                        .append("]");
                return stringBuilder.toString();
            } else {
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length > 0) {
                    int length = Math.min(buf.length, 5120);
                    try {
                        return new String(buf, 0, length, wrapper.getCharacterEncoding());
                    } catch (UnsupportedEncodingException ex) {
                    }
                }
            }            
        }
        return "[unknown]";
    }
    
    /**
     * Retorna o response para o estado original 
     * @param response o response
     * @throws IOException caso nao consiga ler o response 
     */
    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper =
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        responseWrapper.copyBodyToResponse();
    }

    /**
     * Obtém os parametros de uma requisição em formato de query string
     * @param request o http request
     * @return os parametros em formato de query string
     */
    private String getParameters(HttpServletRequest request){
        return request.getQueryString();
    }

    /**
     * Obtém todos os headers de uma requisição
     * @param request o http request
     * @return os readers concatenados e separados em uma string
     */
    private String getHeadersInfo(HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        if(!headerNames.hasMoreElements()) {
            return null;
        }

        StringBuilder header = new StringBuilder();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key);
            header.append(key).append(": ").append(value).append("; ");
        }
        return header.toString();
    }

    /**
     * Obtem o body de um http request em formato de string.
     * @param request o http request
     * @return o body em formato string
     */
    private String getRequestBody(final HttpServletRequest request) {
        String payload = null;
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            if (wrapper.getContentType() != null && CONTENT_TYPE_NAO_LOGAVEIS.contains(wrapper.getContentType().toLowerCase())){
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder
                        .append("[")
                        .append(wrapper.getContentType())
                        .append("]");
                return stringBuilder.toString();
            } else {
                byte[] buf = wrapper.getContentAsByteArray();
                if (buf.length > 0) {
                    try {
                        payload = new String(buf, 0, buf.length, wrapper.getCharacterEncoding());
                    } catch (UnsupportedEncodingException e) {
                        payload = null;
                    }
                }
            }
        }
        return payload;
    }
}