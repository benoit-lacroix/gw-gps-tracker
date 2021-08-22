package radix.home.work.gps.tracker.gateway.controller.se;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import radix.home.work.gps.tracker.gateway.dto.ApiKeyDto;
import radix.home.work.gps.tracker.gateway.dto.RouteDto;
import radix.home.work.gps.tracker.gateway.exception.CustomException;
import radix.home.work.gps.tracker.gateway.service.ApiKeyService;
import radix.home.work.gps.tracker.gateway.utils.AESUtil;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/${server.servlet.secure-context}/api-key")
public class ApiKeyController {

    private final ApiKeyService apiKeyService;

    @GetMapping(path = "/")
    public List<ApiKeyDto> getApiKeys() {
        return apiKeyService.getApiKeys();
    }

    @GetMapping(path = "/{id}")
    public ApiKeyDto getApiKey(@NotNull @PathVariable int id) {
        return apiKeyService.getApiKey(id);
    }

    @PostMapping(path = "/")
    public ApiKeyDto saveApiKey(@Valid @RequestBody ApiKeyDto dto) {
        return apiKeyService.saveApiKey(dto);
    }

    @PutMapping(path = "/{id}")
    public ApiKeyDto updateApiKey(@Valid @RequestBody ApiKeyDto dto) {
        return apiKeyService.saveApiKey(dto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteApiKey(@NotNull @PathVariable int id) {
        apiKeyService.deleteApiKey(id);
    }

    @PostMapping(path = "/generate-key")
    public void generateKey(@NotNull @RequestParam("path") String path, @RequestParam("aes-key") Boolean aesKey,
                            @RequestParam("aes-key-length") Integer aesKeyLength) {
        log.info("Generating new api-key (params: {}, {}, {})", path, aesKey, aesKeyLength);
        var apiKeyDto = new ApiKeyDto();
        apiKeyDto.setKey(UUID.randomUUID().toString());
        apiKeyDto.setFromDate(LocalDate.now());
        var route = new RouteDto();
        route.setPath(path);
        if (aesKey != null && aesKey && aesKeyLength != null) {
            log.info("Generating new aes-key");
            apiKeyDto.setAesKey(AESUtil.generateKey(aesKeyLength));
        } else if (aesKey != null && aesKey) {
            throw new CustomException(HttpStatus.BAD_REQUEST);
        }
        apiKeyDto.setRoutes(Collections.singletonList(route));
        apiKeyDto.setTimestamp(LocalDateTime.now());
        apiKeyService.saveApiKey(apiKeyDto);
    }
}
