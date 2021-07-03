package radix.home.work.gps.tracker.gateway.entity.postgres;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "route")
@SequenceGenerator(name = "route_seq", sequenceName = "route_id_seq", allocationSize = 1, initialValue = 1)
public class RouteEntity {

    @Id
    @GeneratedValue(generator = "route_seq")
    private int id;
    private String path;
}
