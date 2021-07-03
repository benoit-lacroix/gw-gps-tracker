package radix.home.work.gps.tracker.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import radix.home.work.gps.tracker.gateway.dto.ApiKeyDto;
import radix.home.work.gps.tracker.gateway.entity.postgres.ApiKeyEntity;
import radix.home.work.gps.tracker.gateway.entity.postgres.RouteEntity;
import radix.home.work.gps.tracker.gateway.mapper.ApiKeyMapper;
import radix.home.work.gps.tracker.gateway.repository.postgre.ApiKeyRepository;
import radix.home.work.gps.tracker.gateway.repository.postgre.RouteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiKeyService {
    private final ApiKeyRepository apiKeyRepository;
    private final RouteRepository routeRepository;
    private final ApiKeyMapper apiKeyMapper;

    public List<ApiKeyDto> getApiKeys() {
        return apiKeyMapper.fromEntity(apiKeyRepository.findAll().iterator());
    }

    public ApiKeyDto getApiKey(int id) {
        Optional<ApiKeyEntity> opt = apiKeyRepository.findById(id);
        if (opt.isPresent()) {
            return apiKeyMapper.fromEntity(opt.get());
        }
        return new ApiKeyDto();
    }

    public ApiKeyDto getApiKey(String apiKey) {
        ApiKeyEntity entity = apiKeyRepository.findActiveByKey(apiKey, LocalDate.now());
        if (entity != null) {
            return apiKeyMapper.fromEntity(entity);
        }
        return new ApiKeyDto();
    }

    public ApiKeyDto saveApiKey(ApiKeyDto dto) {
        ApiKeyEntity apiKey = apiKeyMapper.toEntity(dto);
        List<RouteEntity> routes = (List<RouteEntity>) routeRepository.saveAll(apiKey.getRoutes());
        apiKey.setRoutes(routes);
        return apiKeyMapper.fromEntity(apiKeyRepository.save(apiKey));
    }

    public void deleteApiKey(int id) {
        apiKeyRepository.deleteById(id);
    }

}
