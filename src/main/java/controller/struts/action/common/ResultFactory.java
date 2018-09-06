/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.struts.action.common;

import javax.servlet.http.HttpServletRequest;
import controller.struts.customComponent.viewSettings.ViewSettings;
import controller.struts.customComponent.viewSettings.ViewSettingsFactory;

/**
 *
 * @author SuslovAI
 */
public class ResultFactory {
    
    public static String getDecoratedByViewSettingsResultByRequest(String result,HttpServletRequest request){
        ViewSettings vs = ViewSettingsFactory.getViewSettingsByRequestOrDefaultSettings(request);
        return result+"-"+vs.toString();
    }
    
    public static String getDecoratedByViewSettingsResult(String result,ViewSettings vs){
        return result+"-"+vs.toString();
    }
}
