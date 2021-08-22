package radix.home.work.gps.tracker.gateway.dto;

import lombok.Data;

@Data
public class GpsPositionDto {
    private String device;
    private boolean notify;
    private String latitude;
    private String longitude;

    public void setNotify(String notify) {
        this.notify = notify.equals("1");
    }
}
