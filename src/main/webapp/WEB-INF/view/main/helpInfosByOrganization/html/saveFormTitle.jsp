<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="%{helpInfoByOrganization==null}">
    <s:text name="addHelpInfoByOrganization"/>
</s:if>
<s:else>
    <s:text name="editHelpInfoByOrganization"/>
</s:else>