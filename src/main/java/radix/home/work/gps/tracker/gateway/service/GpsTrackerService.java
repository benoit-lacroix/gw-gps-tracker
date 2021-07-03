package radix.home.work.gps.tracker.gateway.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import radix.home.work.gps.tracker.gateway.bean.XmlMailTemplate;
import radix.home.work.gps.tracker.gateway.dto.GpsPositionDto;
import radix.home.work.gps.tracker.gateway.enums.EnumMailTemplate;
import radix.home.work.gps.tracker.gateway.exception.InternalGatewayException;
import radix.home.work.gps.tracker.gateway.mapper.GpsPositionMapper;
import radix.home.work.gps.tracker.gateway.repository.influxdb.InfluxDBRepository;

import javax.mail.MessagingException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class GpsTrackerService {

    private final InfluxDBRepository repository;
    private final GpsPositionMapper gpsPositionMapper;
    private final JavaMailSender mailSender;
    private final MailTemplatesService mailTemplates;

    @Value("${spring.mail.from}")
    private String from;
    @Value("${spring.mail.to}")
    private String to;

    public void notifyStartTracking(GpsPositionDto position) {
        try {
            Map<String, String> elements = new HashMap<>();
            elements.put("device", position.getDevice());
            elements.put("longitude", position.getLongitude());
            elements.put("latitude", position.getLatitude());
            XmlMailTemplate template = mailTemplates.loadAndFillXmlTemplate(EnumMailTemplate.START_TRACKING, elements);
            var message = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to.split(","));
            helper.setSubject(template.getSubject());
            helper.setText(template.getBody(), true);
            mailSender.send(message);
        } catch (MessagingException me) {
            log.error("Error while sending 'Start tracking' mail", me);
            throw new InternalGatewayException();
        }
    }

    public void savePosition(GpsPositionDto position) {
        repository.writeMeasurement(gpsPositionMapper.toMeas(position));
    }
}
