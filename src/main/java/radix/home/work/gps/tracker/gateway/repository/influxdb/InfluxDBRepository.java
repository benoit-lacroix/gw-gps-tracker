package radix.home.work.gps.tracker.gateway.repository.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.domain.WritePrecision;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class InfluxDBRepository {

    @Value("${spring.data.influxdb.bucket}")
    private String bucket;
    @Value("${spring.data.influxdb.org}")
    private String org;

    private final InfluxDBClient client;

    public <M> void writeMeasurement(M measurement) {
        try (var writeApi = client.getWriteApi()) {
            writeApi.writeMeasurement(bucket, org, WritePrecision.NS, measurement);
        }
    }
}
