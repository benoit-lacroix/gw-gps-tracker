package radix.home.work.gps.tracker.gateway.dto;

import lombok.Data;

@Data
public class AesKeyDto {

    private long id;

    private long length;

    private byte[] key;

    private byte[] iv;

    private String cipher;
}
