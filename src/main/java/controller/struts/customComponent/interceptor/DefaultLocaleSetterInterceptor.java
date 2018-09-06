
package controller.struts.customComponent.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;
import org.apache.struts2.dispatcher.Parameter;

import java.util.Locale;
import java.util.Map;


public class DefaultLocaleSetterInterceptor extends AbstractInterceptor {
    
    private static final Logger LOG = LogManager.getLogger(DefaultLocaleSetterInterceptor.class);
            
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        try{
            if(!ActionContext.getContext().getParameters().get("locale").isDefined()) {
                ActionContext.getContext().setLocale(new Locale("ru","RU"));
            }
        } catch(Exception e){
            LOG.error("On set default locale exception have occured",e);
        }
        return invocation.invoke();
    }
}
