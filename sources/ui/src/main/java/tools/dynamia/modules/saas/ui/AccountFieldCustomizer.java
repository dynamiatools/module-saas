
package tools.dynamia.modules.saas.ui;

import org.springframework.stereotype.Component;
import tools.dynamia.viewers.Field;
import tools.dynamia.viewers.FieldCustomizer;
import tools.dynamia.viewers.FieldGroup;

/**
 * @author Mario Serrano Leones
 */
@Component
public class AccountFieldCustomizer implements FieldCustomizer {

    @Override
    public void customize(String viewTypeName, Field field) {
        if (field.getName().equals("accountId")) {
            if (field.getParams().get("forceVisible") == Boolean.TRUE) {
                return;
            }
            field.getViewDescriptor().removeField(field.getName());
            FieldGroup group = field.getGroup();
            if (group != null) {
                group.removeField(field);
            }
        }
    }

}
