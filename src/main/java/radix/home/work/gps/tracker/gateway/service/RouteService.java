package radix.home.work.gps.tracker.gateway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import radix.home.work.gps.tracker.gateway.dto.RouteDto;
import radix.home.work.gps.tracker.gateway.entity.postgres.RouteEntity;
import radix.home.work.gps.tracker.gateway.repository.postgre.RouteRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    public RouteEntity saveRoute(RouteEntity inEntity) {
        Optional<RouteEntity> optRoute = routeRepository.findByPath(inEntity.getPath());
        return optRoute.orElseGet(() -> routeRepository.save(inEntity));
    }

    public List<RouteEntity> saveRoutes(List<RouteEntity> inEntities) {
        List<RouteEntity> outEntities = new ArrayList<>();
        inEntities.forEach(inEntity -> outEntities.add(saveRoute(inEntity)));
        return outEntities;
    }
}
