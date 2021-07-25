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
public class XmlMailTemplate implements Cloneable {
    @XmlElement
    private String code;
    @XmlElement
    private String subject;
    @XmlElement
    private String body;

    @Setter(AccessLevel.NONE)
    private Map<String, String> elements = new HashMap<>();

    /**
     * Add an placeholder key and the replacing value
     *
     * @param key The placeholder key
     * @param value The value replacing the placeholder in the template
     */
    public void addElement(String key, String value) {
        // The key is replaced by the placeholder pattern of the key: {{key}}
        elements.put(String.format("\\{\\{%s\\}\\}", key), value);
    }

    /**
     * Add a collection of placeholders keys and there replacing value
     *
     * @param elements A {@link Map} of the placeholders keys and values to replace
     */
    public void addElements(Map<String, String> elements) {
        elements.forEach(this::addElement);
    }

    /**
     * Method for filling the instance of the mail template. The placeholders are replaced by the values from the
     * {@link Map} of elements.
     *
     * @see XmlMailTemplate#addElement(String, String)
     * @see XmlMailTemplate#addElements(Map)
     */
    public void fillTemplate() {
        elements.forEach((key, value) -> {
            this.setSubject(this.getSubject().replaceAll(key, value));
            this.setBody(this.getBody().replaceAll(key, value));
        });
    }

    @Override
    public XmlMailTemplate clone() throws CloneNotSupportedException {
        XmlMailTemplate clone = (XmlMailTemplate) super.clone();
        clone.setCode(this.code);
        clone.setSubject(this.subject);
        clone.setBody(this.body);
        return clone;
    }
}
