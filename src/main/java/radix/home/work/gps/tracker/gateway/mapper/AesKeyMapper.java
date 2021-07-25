package radix.home.work.gps.tracker.gateway.mapper;

import org.mapstruct.Mapper;
import radix.home.work.gps.tracker.gateway.dto.AesKeyDto;
import radix.home.work.gps.tracker.gateway.entity.postgres.AesKeyEntity;

@Mapper(componentModel = "spring")
public interface AesKeyMapper {

    AesKeyEntity toEntity(AesKeyDto apiKey);

    AesKeyDto fromEntity(AesKeyEntity entity);
}
