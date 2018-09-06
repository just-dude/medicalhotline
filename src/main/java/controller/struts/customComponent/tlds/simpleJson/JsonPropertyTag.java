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
/**
 *
 * @author SuslovAI
 */
public class JsonPropertyTag extends SimpleTagSupport {
    
    private boolean encloseInQuotes=true;
    
    private String name;
    
    @Override
    public void doTag() throws JspException, IOException {
        StringWriter sw = new StringWriter();
        StringBuilder sb = new StringBuilder();
        String formatString = "\"%s\"";
        
        getJspBody().invoke(sw);
        
        sb.append(String.format(formatString, name));
        sb.append(":");
        if(encloseInQuotes){
            sb.append(String.format(formatString, sw.toString().trim()));
        }else{
            sb.append(sw.toString().trim());
        }        
        sb.append(",");
        getJspContext().getOut().write(sb.toString());
    }
    
    public void setEncloseInQuotes(boolean encloseInQuotes) {
        this.encloseInQuotes = encloseInQuotes;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    
}
