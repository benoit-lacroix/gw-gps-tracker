package radix.home.work.gps.tracker.gateway.dto;

import lombok.Data;

@Data
public class GpsPositionDto {
    private String device;
    private String latitude;
    private String longitude;
}
