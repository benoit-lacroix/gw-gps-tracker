package radix.home.work.gps.tracker.gateway.bean;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.Map;

@Data
@XmlRootElement(name = "mail")
@XmlAccessorType(XmlAccessType.FIELD)
public class XmlMailTemplate {
    @XmlElement
    private String code;
    @XmlElement
    private String subject;
    @XmlElement
    private String body;

    @Setter(AccessLevel.NONE)
    private Map<String, String> elements = new HashMap<>();

    public void addElement(String key, String value) {
        elements.put(String.format("\\{\\{%s\\}\\}", key), value);
    }

    public void addElements(Map<String, String> elements) {
        elements.forEach(this::addElement);
    }

    public void fillTemplate() {
        elements.forEach((key, value) -> {
            this.setSubject(this.getSubject().replaceAll(key, value));
            this.setBody(this.getBody().replaceAll(key, value));
        });
    }
}
