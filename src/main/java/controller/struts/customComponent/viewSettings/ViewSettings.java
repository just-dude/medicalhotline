package controller.struts.customComponent.viewSettings;



public class ViewSettings { 
    
    public static final ViewSettings FULL_PAGE_HTML = new ViewSettings("html","full-page","full-page");
    public static final ViewSettings MAIN_CONTENT_HTML= new ViewSettings("html","main-content","full-page");
    public static final ViewSettings JSON= new ViewSettings("json");
    
    public static ViewSettings[] values(){
        return new ViewSettings[]{FULL_PAGE_HTML,MAIN_CONTENT_HTML,JSON};
    }
    
    private String extension;
    private String mode;
    private String defaultMode;

    private ViewSettings(String extension, String mode,String defaultMode) {
        this.extension = extension;
        this.mode = mode;
        this.defaultMode=defaultMode;
    }
    
    private ViewSettings(String extension) {
        this.extension = extension;
        this.mode = "";
        this.defaultMode="";
    }

    public String getExtension() {
        return extension;
    }

    public String getMode() {
        return mode;
    }

    public String getDefaultMode() {
        return defaultMode;
    }

    @Override
    public String toString() {
        String modePart = (!mode.equals(""))?"-"+mode:"";
        return extension+modePart;
    }

}
