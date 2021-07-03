package radix.home.work.gps.tracker.gateway.controller.ue;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import radix.home.work.gps.tracker.gateway.controller.AbstractController;
import radix.home.work.gps.tracker.gateway.dto.GpsPositionDto;
import radix.home.work.gps.tracker.gateway.service.GpsTrackerService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/${server.servlet.unsecure-context}/gps")
public class GpsTrackerController extends AbstractController {

    private final GpsTrackerService trackerService;

    @PostMapping("/start-tracking")
    public void startTracking(@RequestBody GpsPositionDto position) {
        log.debug("Starting tracking: {}", position);
        trackerService.notifyStartTracking(position);
    }

    @PostMapping("/position")
    public void receivePosition(@RequestBody GpsPositionDto position) {
        log.debug("Received position: {}", position);
        trackerService.savePosition(position);
    }
}
