package controller.struts.customComponent.viewSettings;

import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

public class ViewSettingsFactory {

    private static final ViewSettings DEFAULT_VIEW_SETTINGS = ViewSettings.FULL_PAGE_HTML;
    private static final String MODE_PARAMETER_NAME = "viewSettingsMode";

    public static ViewSettings getViewSettingsByRequestOrDefaultSettings(HttpServletRequest request) throws IllegalArgumentException{
        String uri = request.getRequestURI();
        String uriExtension;
        uriExtension=getURIExtensionOrEmpty(uri);
        if(!uriExtension.equals("")){
            Optional<ViewSettings> vs=getViewSettingsByExtension(uriExtension,request);
            return vs.orElse(getDeafultViewSettings());
        } else{
            return getDeafultViewSettings();
        }
    }

    private static ViewSettings getDeafultViewSettings(){
        return DEFAULT_VIEW_SETTINGS;
    }

    private static Optional<ViewSettings> getViewSettingsByExtension(String extension,HttpServletRequest request) throws IllegalArgumentException{
        String mode=getModeByRequestOrEmpty(request);
        for(ViewSettings vs:ViewSettings.values()){
            if(vs.getExtension().equals(extension)){
                if(!mode.equals("")){
                    if(vs.getMode().equals(mode)){
                        return Optional.of(vs);
                    }
                } else{
                    mode=vs.getDefaultMode();
                    if(vs.getMode().equals(mode)){
                        return Optional.of(vs);
                    }
                }                
            }
        }
        return Optional.empty();
    }

    private static String getModeByRequestOrEmpty(HttpServletRequest request){
        String[] htmlModeParameterValues = request.getParameterMap().get(MODE_PARAMETER_NAME);
        if(htmlModeParameterValues!=null && htmlModeParameterValues.length>0){
            return htmlModeParameterValues[0];
        } else{
            return "";
        }
    }
    
    private static String getURIExtensionOrEmpty(String uri) throws IllegalArgumentException{
        int uriLastIndexOfDot=uri.lastIndexOf('.')+1;
        if(uriLastIndexOfDot>-1){
            return uri.substring(uriLastIndexOfDot);
        }
        else{
            return "";
        }
    }
    
}
