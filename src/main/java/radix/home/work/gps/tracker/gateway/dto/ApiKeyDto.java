package radix.home.work.gps.tracker.gateway.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ApiKeyDto {

    private int id;

    private String key;

    private AesKeyDto aesKey;

    private LocalDate fromDate;

    private LocalDate untilDate;

    private LocalDateTime timestamp;

    private List<RouteDto> routes;
}
