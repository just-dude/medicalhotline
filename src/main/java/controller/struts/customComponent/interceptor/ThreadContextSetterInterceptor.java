
package controller.struts.customComponent.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import domain.security.SecuritySubjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.dispatcher.HttpParameters;


public class ThreadContextSetterInterceptor extends AbstractInterceptor {
    
    private static final Logger LOG = LogManager.getLogger(ThreadContextSetterInterceptor.class);
            
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String result;
        try{                   
            HttpParameters parameters = ActionContext.getContext().getParameters();
            ServletActionContext.getRequest().getRemoteAddr();
            LOG.debug("ThreadContextSetterInterceptor parameters: "+parameters);
            ThreadContext.put("ipAddress", ServletActionContext.getRequest().getRemoteAddr().toString());
            try{
                if(SecuritySubjectUtils.getCurrentUserAccount()!=null) {
                    ThreadContext.put("userAccount", SecuritySubjectUtils.getCurrentUserAccount().toString());
                } else{
                    ThreadContext.put("userAccount", "not authenticated");
                }
            } catch(Exception e){
                LOG.error("ThreadContextSetterInterceptor userAccount error",e);
            }
        } catch(Exception e){
            LOG.error("On set thread context exception have occured",e);
        }              
        result=invocation.invoke();       
        ThreadContext.clearAll();       
        return result;
    }
}
