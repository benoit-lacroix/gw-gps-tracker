package radix.home.work.gps.tracker.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import radix.home.work.gps.tracker.gateway.exception.InvalidApiKeyException;
import radix.home.work.gps.tracker.gateway.service.AuthenticationService;
import radix.home.work.gps.tracker.gateway.service.HttpRequestService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.time.LocalDate;

@Slf4j
@Order(1)
@Component
@RequiredArgsConstructor
public class AuthenticationFilter implements Filter {

    private final AuthenticationService authenticationService;
    private final HttpRequestService httpRequestService;

    @Value("${server.servlet.context-path}")
    private String baseContext;

    /**
     * Filter for checking api-key authorization.
     *
     * @throws InvalidApiKeyException when the key is invalid or not found
     */
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest && ((HttpServletRequest) request).getContextPath().equals(baseContext)) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String apiKey = httpRequestService.getApiKey(httpRequest);
            log.debug("Checking api-key with: {'{}', '{}'}", apiKey, httpRequest.getRequestURI());
            if (!authenticationService.checkApiKey(apiKey, httpRequest.getRequestURI(), LocalDate.now())) {
                throw new InvalidApiKeyException();
            }
        }
        chain.doFilter(request, response);
    }

}
