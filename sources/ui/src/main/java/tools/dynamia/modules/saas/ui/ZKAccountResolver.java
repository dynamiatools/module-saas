
package tools.dynamia.modules.saas.ui;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;

/**
 *
 * @author Mario Serrano Leones
 */
@Component
public class ZKAccountResolver extends HttpAccountResolver {

    @Override
    protected HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) Executions.getCurrent().getNativeRequest();
    }

}
