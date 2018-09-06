
package controller.struts.action.common;


import org.apache.commons.lang3.StringEscapeUtils;


public abstract class PrevActionMsgHandlingExceptionsAction extends HandlingExceptionsBaseAction {

    protected String prevActionMessage;
    protected String prevActionError;

    public String getPrevActionMessage() {
        return prevActionMessage;
    }

    public String getPrevActionError() {
        return prevActionError;
    }

    public void setPrevActionError(String prevActionError) {
        this.prevActionError =filterString(prevActionError);
    }

    public void setPrevActionMessage(String prevActionMessage) {
        this.prevActionMessage =filterString(prevActionMessage);
    }

    protected void addPrevActionMsgAndErrorToAction(){
        if(prevActionMessage!=null && !prevActionMessage.equals("")){
            addActionMessage(prevActionMessage);
        }
        if(prevActionError!=null && !prevActionError.equals("")){
            addActionError(prevActionError);
        }
    }

    protected String filterString(String str){
//        try {
//            str=new String(str.getBytes("ISO-8859-1"), "UTF-8");
//        } catch (UnsupportedEncodingException e){
//            getLogger().warn("UnsupportedEncodingException has occured: ",e);
//        } finally {
            //return StringEscapeUtils.escapeHtml4(str);
        return str;
        //}
    }


}
