package radix.home.work.gps.tracker.gateway.entity.postgres;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "aes_key")
@SequenceGenerator(name = "aes_key_seq", sequenceName = "aes_key_id_seq", allocationSize = 1)
public class AesKeyEntity {

    @Id
    @GeneratedValue(generator = "aes_key_seq")
    private long id;
    private long length;
    private byte[] key;
    private byte[] iv;
    private String cipher;
}
