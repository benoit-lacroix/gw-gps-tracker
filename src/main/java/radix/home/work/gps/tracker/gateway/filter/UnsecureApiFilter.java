package radix.home.work.gps.tracker.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import radix.home.work.gps.tracker.gateway.bean.CustomHttpServletRequestWrapper;
import radix.home.work.gps.tracker.gateway.service.HttpRequestService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Base64;

@Slf4j
@Order(2)
@Component
@RequiredArgsConstructor
public class UnsecureApiFilter implements Filter {

    private final HttpRequestService httpRequestService;

    @Value("${server.servlet.context-path}")
    private String baseContext;

    @Value("${server.servlet.unsecure-context}")
    private String unsecureContext;

    /**
     * Filter for decrypting bodies of unsecure endpoints.
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            var requestWrapper = new CustomHttpServletRequestWrapper((HttpServletRequest) request);

            if (requestWrapper.getRequestURI().startsWith(baseContext + "/" + unsecureContext)) {
                log.debug("Body to be decrypted before being processed");
                String apiKey = httpRequestService.getApiKey(requestWrapper);
                // Received body is base64 encoded, AES encrypted, base64 encoded content...
                byte[] decodedBody = Base64.getDecoder().decode(requestWrapper.getBody());
                byte[] decryptedBody = Base64.getMimeDecoder().decode(httpRequestService.decryptBody(decodedBody, apiKey));
                log.info("Decrypted body: {}", new String(decryptedBody));
                requestWrapper.setBody(decryptedBody);
                chain.doFilter(requestWrapper, response);
            } else {
                log.debug("No decryption do be done");
                chain.doFilter(request, response);
            }
        }
    }

}

