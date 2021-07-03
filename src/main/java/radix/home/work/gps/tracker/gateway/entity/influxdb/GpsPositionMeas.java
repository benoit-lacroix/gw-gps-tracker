package radix.home.work.gps.tracker.gateway.entity.influxdb;

import com.influxdb.annotations.Column;
import com.influxdb.annotations.Measurement;
import lombok.Data;

@Data
@Measurement(name = "gps-position")
public class GpsPositionMeas {

    @Column(tag = true)
    private String device;

    @Column
    private String longitude;

    @Column
    private String latitude;
}
