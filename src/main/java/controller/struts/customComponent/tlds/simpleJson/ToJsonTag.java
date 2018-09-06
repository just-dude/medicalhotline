/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.struts.customComponent.tlds.simpleJson;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspException;
import java.io.IOException;

import controller.struts.customComponent.util.JsonUtils;
/**
 *
 * @author SuslovAI
 */
public class ToJsonTag extends SimpleTagSupport {
    
    private Object param;
    
    @Override
    public void doTag() throws JspException, IOException {
        String jsonResult=JsonUtils.toJson(param);
        getJspContext().getOut().write(jsonResult.trim());
    }
    
    public void setParam(Object param) {
        this.param = param;
    }
}
