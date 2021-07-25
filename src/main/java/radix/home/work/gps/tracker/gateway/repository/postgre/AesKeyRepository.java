package radix.home.work.gps.tracker.gateway.repository.postgre;

import org.springframework.data.repository.CrudRepository;
import radix.home.work.gps.tracker.gateway.entity.postgres.AesKeyEntity;

public interface AesKeyRepository extends CrudRepository<AesKeyEntity, Integer> {

}
