package radix.home.work.gps.tracker.gateway.mapper;

import org.mapstruct.Mapper;
import radix.home.work.gps.tracker.gateway.dto.RouteDto;
import radix.home.work.gps.tracker.gateway.entity.postgres.RouteEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RouteMapper {

    RouteEntity toEntity(RouteDto dto);

    List<RouteEntity> toEntity(List<RouteDto> dtos);

    RouteDto fromEntity(RouteEntity entity);

    List<RouteDto> fromEntity(List<RouteEntity> entities);
}
