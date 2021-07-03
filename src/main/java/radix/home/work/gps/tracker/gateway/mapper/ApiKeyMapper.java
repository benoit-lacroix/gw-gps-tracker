package radix.home.work.gps.tracker.gateway.mapper;

import org.mapstruct.Mapper;
import radix.home.work.gps.tracker.gateway.dto.ApiKeyDto;
import radix.home.work.gps.tracker.gateway.entity.postgres.ApiKeyEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring", uses = RouteMapper.class)
public interface ApiKeyMapper {

    ApiKeyEntity toEntity(ApiKeyDto apiKey);

    ApiKeyDto fromEntity(ApiKeyEntity entity);

    default List<ApiKeyDto> fromEntity(Iterator<ApiKeyEntity> entities) {
        List<ApiKeyDto> out = new ArrayList<>();
        entities.forEachRemaining(item -> out.add(fromEntity(item)));
        return out;
    }
}
