/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.struts.customComponent.tlds.simpleJson;

import javax.servlet.jsp.tagext.SimpleTagSupport;
import javax.servlet.jsp.JspException;
import java.io.IOException;

import java.io.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 *
 * @author SuslovAI
 */
public class JsonObjectTag extends SimpleTagSupport {
    
    
    @Override
    public void doTag() throws JspException, IOException {
        StringWriter sw = new StringWriter();
        StringBuilder sb;
        String body;
        getJspBody().invoke(sw);
        body = sw.toString().trim();
        
        if(body.isEmpty()){
            return;
        }
        sb = new StringBuilder(body);
        
        int lastCommaIndex=sb.lastIndexOf(",");
        int penultimatRank=sb.length()-1;
        if(lastCommaIndex==penultimatRank){
            sb.deleteCharAt(lastCommaIndex);
        }
        sb.insert(0, '{');
        sb.append('}');      
       
        getJspContext().getOut().write(sb.toString());
    }
}
