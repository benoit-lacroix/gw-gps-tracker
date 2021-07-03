package radix.home.work.gps.tracker.gateway.repository.postgre;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import radix.home.work.gps.tracker.gateway.entity.postgres.ApiKeyEntity;

import java.time.LocalDate;

public interface ApiKeyRepository extends CrudRepository<ApiKeyEntity, Integer> {

    /**
     * Repository for checking whether the given combo api-key/path is valid or not.
     *
     * @param key Api-key
     * @param path Path of the api called
     * @param refDate Reference date to validate authentication
     * @return {@code true} if the comb api-key/path is valid, {@code false} otherwise.
     */
    @Query(value = "select case when count(*) >= 1 then true else false end " +
            "from api_key ak " +
            "    join api_key_route akr on ak.id = akr.api_key_id " +
            "    join route r on akr.route_id = r.id " +
            "where ak.key = :key " +
            "    and (:refDate >= ak.from_date or ak.from_date is null) " +
            "    and (:refDate <= ak.until_date or ak.until_date is null) " +
            "    and :path like r.path", nativeQuery = true)
    boolean checkApiKey(String key, String path, LocalDate refDate);

    @Query("from ApiKeyEntity ak where ak.key = :key and :refDate between ak.fromDate and ak.untilDate")
    ApiKeyEntity findActiveByKey(String key, LocalDate refDate);
}
