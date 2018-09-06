<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="%{helpInfo==null}">
    <s:text name="addHelpInfo"/>
</s:if>
<s:else>
    <s:text name="editHelpInfo"/>
</s:else>