package radix.home.work.gps.tracker.gateway.controller.se;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import radix.home.work.gps.tracker.gateway.dto.ApiKeyDto;
import radix.home.work.gps.tracker.gateway.dto.RouteDto;
import radix.home.work.gps.tracker.gateway.service.ApiKeyService;

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
    public ApiKeyDto updateApiKey(@NotNull @PathVariable int id, @Valid @RequestBody ApiKeyDto dto) {
        return apiKeyService.saveApiKey(dto);
    }

    @DeleteMapping(path = "/{id}")
    public void deleteApiKey(@NotNull @PathVariable int id) {
        apiKeyService.deleteApiKey(id);
    }

    @PostMapping(path = "/generate-key")
    public void generateKey(@NotNull @Param("path") String path) {
        var apiKeyDto = new ApiKeyDto();
        apiKeyDto.setKey(UUID.randomUUID().toString());
        apiKeyDto.setFromDate(LocalDate.now());
        var route = new RouteDto();
        route.setPath(path);
        apiKeyDto.setRoutes(Collections.singletonList(route));
        apiKeyDto.setTimestamp(LocalDateTime.now());
        apiKeyService.saveApiKey(apiKeyDto);
    }
}
