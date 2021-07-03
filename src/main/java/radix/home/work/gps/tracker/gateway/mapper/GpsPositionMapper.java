package radix.home.work.gps.tracker.gateway.mapper;

import org.mapstruct.Mapper;
import radix.home.work.gps.tracker.gateway.dto.GpsPositionDto;
import radix.home.work.gps.tracker.gateway.entity.influxdb.GpsPositionMeas;

@Mapper(componentModel = "spring")
public interface GpsPositionMapper {

    GpsPositionMeas toMeas(GpsPositionDto dto);
}
