package radix.home.work.gps.tracker.gateway.bean;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@Data
@XmlRootElement(name = "mails")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMailTemplates {

    @XmlElement(name = "mail")
    private List<XmlMailTemplate> mailTemplates = new ArrayList<>();
}
