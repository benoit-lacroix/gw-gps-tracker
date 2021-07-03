package radix.home.work.gps.tracker.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import radix.home.work.gps.tracker.gateway.dto.ApiKeyDto;
import radix.home.work.gps.tracker.gateway.exception.CustomException;
import radix.home.work.gps.tracker.gateway.exception.InvalidApiKeyException;
import radix.home.work.gps.tracker.gateway.utils.AESUtil;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Slf4j
@Service
@RequiredArgsConstructor
public class HttpRequestService {

    private static final String API_KEY_HEADER = "X-API-KEY";
    private static final String API_KEY_PARAMETER = "api-key";
    private static final String API_KEY_ATTRIBUTE = "apiKey";
    private final ApiKeyService apiKeyService;

    /**
     * Internal method for retrieving api-key from request header or from request parameters.
     *
     * @param request The {@link HttpServletRequest} to analyse
     * @return The value of the api-key
     * @throws InvalidApiKeyException if the api-key value cannot be found
     */
    public String getApiKey(HttpServletRequest request) {
        String apiKey = request.getHeader(API_KEY_HEADER);
        if (apiKey == null) {
            log.debug("Header '{}' not found, trying with request parameters", API_KEY_HEADER);
            apiKey = request.getParameter(API_KEY_PARAMETER);
        }
        if (apiKey == null) {
            log.debug("Request parameter '{}' not found, trying with request attribute", API_KEY_PARAMETER);
            apiKey = (String) request.getAttribute(API_KEY_ATTRIBUTE);
        }
        if (apiKey != null) {
            log.debug("Api-key found: '{}'", apiKey);
            return apiKey;
        }
        log.debug("Request attribute '{}' not found, no api-key found", API_KEY_ATTRIBUTE);
        throw new InvalidApiKeyException();
    }

    /**
     * Method for decrypting body with the aes-key associated with the given api-key.
     *
     * @param body The body to be decrypted
     * @param apiKey The api-key used to find the aes-key to decrypt body
     * @return The body decrypted
     */
    public byte[] decryptBody(byte[] body, String apiKey) {
        try {
            ApiKeyDto key = apiKeyService.getApiKey(apiKey);
            if (key.getAesKey() != null) {
                return AESUtil.decrypt(body, key.getAesKey().getKey(),
                        key.getAesKey().getIv(),
                        key.getAesKey().getCipher());

            } else {
                log.warn("No aes-key found for api-key {}", apiKey);
                return body;
            }
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                InvalidAlgorithmParameterException | IllegalBlockSizeException |
                BadPaddingException e) {
            log.error("Error while decrypting request body", e);
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
