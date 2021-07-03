package radix.home.work.gps.tracker.gateway.configuration;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.InfluxDBClientOptions;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class InfluxDBConfiguration {

    @Value("${spring.data.influxdb.url}")
    private String url;
    @Value("${spring.data.influxdb.token}")
    private String token;

    private InfluxDBClient influxDBClient;

    @Bean
    public InfluxDBClient getClient(@NotNull OkHttpClient.Builder client) {
        if (influxDBClient == null) {
            InfluxDBClientOptions opts = InfluxDBClientOptions.builder()
                    .url(url)
                    .authenticateToken(token.toCharArray())
                    // FIXME: allow to disable or not the SSL certificate validation
                    .okHttpClient(client)
                    .build();
            influxDBClient = InfluxDBClientFactory.create(opts);
        }
        return influxDBClient;
    }
}
