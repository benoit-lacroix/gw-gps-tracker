package radix.home.work.gps.tracker.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import radix.home.work.gps.tracker.gateway.repository.postgre.ApiKeyRepository;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final ApiKeyRepository apiKeyRepository;

    @Value("${admin.api-key:null}")
    private String adminApiKey;

    /**
     * Service for validating an api-key/path combo.
     *
     * @param key Api-key
     * @param path Path of the api called
     * @param refDate Reference date to validate authentication
     * @return {@code true} if the comb api-key/path is valid, {@code false} otherwise.
     */
    public boolean checkApiKey(String key, String path, LocalDate refDate) {
        if (!adminApiKey.equals("null") && key.equals(adminApiKey)) {
            log.info("Admin api-key checked successfully");
            return true;
        }

        boolean isApiKeyValid = apiKeyRepository.checkApiKey(key, path, refDate);
        if (isApiKeyValid) {
            log.info("Api-key checked successfully: {'{}', '{}'}", key, path);
            return true;
        } else {
            log.warn("Invalid apiKey: {'{}', '{}'}", key, path);
            return false;
        }
    }
}
