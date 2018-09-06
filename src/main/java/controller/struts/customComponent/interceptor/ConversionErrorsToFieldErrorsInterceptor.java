
package controller.struts.customComponent.interceptor;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.opensymphony.xwork2.ActionSupport;
import java.lang.reflect.*;
import org.apache.commons.lang3.StringUtils;


public class ConversionErrorsToFieldErrorsInterceptor extends AbstractInterceptor {
    
    private static final Logger LOG = LogManager.getLogger(ConversionErrorsToFieldErrorsInterceptor.class);
    
    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionSupport action = (ActionSupport)invocation.getAction();
        Map<String,Object> convErrors=(Map<String,Object>)ActionContext.getContext().getConversionErrors();
        Iterator<Map.Entry<String, Object>> itr = convErrors.entrySet().iterator();
        String resourceBundleKey;
        if(itr.hasNext()){
            LOG.debug("conversation errors:");
        }
        
        
        while (itr.hasNext()){
            Map.Entry<String, Object> curEntry=itr.next();
            String propertyType=getPropertyTypeByPath(action,curEntry.getKey());
            LOG.debug(curEntry.getKey());
            LOG.debug(Arrays.toString((String[])curEntry.getValue()));
            LOG.debug("propertyType: "+propertyType);
            resourceBundleKey="validation.conversionError."+propertyType;
            action.addFieldError(curEntry.getKey(),resourceBundleKey);
        }       
       return invocation.invoke();
    }
    
    private static String getPropertyTypeByPath(Object root,String path){
        String result=null; 
        try{
            String[] pathParts=StringUtils.split(path, '.');           
            
            
            Class c = root.getClass();
            
            for(String pathPart: pathParts){
                LOG.debug("pathPart: "+pathPart);
                Method method = c.getMethod("get"+StringUtils.capitalize(pathPart),new Class[0]);
                LOG.debug("TypeName: " + method.getReturnType().getTypeName()); 
                c=method.getReturnType();
            }
            result=c.getTypeName();
            LOG.debug("Type: " + c.getTypeName()); 
        }
        catch(Exception e){
            LOG.error("Exception: ",e );
        }
        return result;
    }
}
