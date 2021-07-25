package radix.home.work.gps.tracker.gateway.mapper;

import org.mapstruct.Mapper;
import radix.home.work.gps.tracker.gateway.dto.ApiKeyDto;
import radix.home.work.gps.tracker.gateway.entity.postgres.ApiKeyEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Mapper(componentModel = "spring", uses = {AesKeyMapper.class, RouteMapper.class})
public interface ApiKeyMapper {

    ApiKeyEntity toEntity(ApiKeyDto apiKey);

    ApiKeyDto fromEntity(ApiKeyEntity entity);

    default List<ApiKeyDto> fromEntities(Iterator<ApiKeyEntity> entities) {
        List<ApiKeyDto> dtoList = new ArrayList<>();
        entities.forEachRemaining(item -> dtoList.add(fromEntity(item)));
        return dtoList;
    }
}
