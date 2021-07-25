package radix.home.work.gps.tracker.gateway.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import radix.home.work.gps.tracker.gateway.bean.XmlMailTemplate;
import radix.home.work.gps.tracker.gateway.bean.XmlMailTemplates;
import radix.home.work.gps.tracker.gateway.enums.EnumMailTemplate;
import radix.home.work.gps.tracker.gateway.exception.TemplateNotFoundException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.util.Map;

@Slf4j
@Component
public class MailTemplatesService {

    private XmlMailTemplates xmlMailTemplates;

    /**
     * Method for loading and filling a mail template
     *
     * @param template {@link EnumMailTemplate} corresponding to the template to load and fill
     * @param elements {@link Map} of the placeholders to replace in the template
     * @return The template loaded and filled
     */
    public XmlMailTemplate loadAndFillXmlTemplate(EnumMailTemplate template, Map<String, String> elements) {
        XmlMailTemplate out = loadXmlTemplate(template);
        out.addElements(elements);
        out.fillTemplate();
        return out;
    }

    /**
     * Method for loading a single mail template
     *
     * @param mailTemplate {@link EnumMailTemplate} corresponding to the template to load
     * @return The template loaded
     */
    public XmlMailTemplate loadXmlTemplate(EnumMailTemplate mailTemplate) {
        if (xmlMailTemplates == null || xmlMailTemplates.getMailTemplates() == null) {
            try {
                var file = new File("/templates/mail-templates.xml");
                var context = JAXBContext.newInstance(XmlMailTemplates.class);
                var unmarshaller = context.createUnmarshaller();
                xmlMailTemplates = (XmlMailTemplates) unmarshaller.unmarshal(file);
            } catch (JAXBException | NullPointerException e) {
                log.error("Error while loading templates", e);
                throw new TemplateNotFoundException("Unable to load mail templates configuration file.");
            }
        }
        for (XmlMailTemplate template : xmlMailTemplates.getMailTemplates()) {
            if (mailTemplate.name().equals(template.getCode())) {
                try {
                    return template.clone();
                } catch (CloneNotSupportedException cnse) {
                    throw new TemplateNotFoundException("Error while cloning XmlMailTemplate {}" + mailTemplate);
                }
            }
        }
        throw new TemplateNotFoundException("Template not found: " + mailTemplate);
    }
}
