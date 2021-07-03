package radix.home.work.gps.tracker.gateway.entity.postgres;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "api_key")
@SequenceGenerator(name = "api_key_seq", sequenceName = "api_key_id_seq", allocationSize = 1, initialValue = 1)
public class ApiKeyEntity {

    @Id
    @GeneratedValue(generator = "api_key_seq")
    private int id;
    private String key;

    @OneToOne(fetch = FetchType.EAGER)
    private AesKeyEntity aesKey;

    private LocalDate fromDate;
    private LocalDate untilDate;
    private LocalDateTime timestamp;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "api_key_route",
            joinColumns = @JoinColumn(name = "api_key_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "route_id", referencedColumnName = "id"))
    private List<RouteEntity> routes;
}
