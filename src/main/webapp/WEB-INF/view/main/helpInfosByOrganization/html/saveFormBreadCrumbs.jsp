<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>
<ol class="breadcrumb">
    <s:url var="mainMainPageUrl" namespace="/" action="index">
        <s:param name="locale" value="%{#request.locale}"/>
    </s:url>
    <s:url var="helpInfosByOrganizationPageUrl" namespace="/" action="helpinfos-by-organization-page">
        <s:param name="locale" value="%{#request.locale}"/>
    </s:url>
    <s:url var="helpInfoByOrganizationManagementUrl" namespace="/" action="helpinfo-by-organization-management">
        <s:param name="locale" value="%{#request.locale}"/>
        <s:param name="issueTypeId" value="%{issueTypeId}"/>
        <s:param name="organizationId" value="%{organizationId}"/>
    </s:url>
    <s:i18n name="controller.struts.action.common.pages.main.package">
        <li class="breadcrumb-item"><s:a href="%{#mainMainPageUrl}"><s:text name="mainPage"/></s:a></li>
    </s:i18n>
    <li class="breadcrumb-item"><s:a href="%{#helpInfosByOrganizationPageUrl}"><s:text name="helpInfosByOrganization"/></s:a></li>
    <s:if test="%{helpInfoByOrganization==null}">
        <li class="breadcrumb-item">
            <s:text name="addHelpInfoByOrganization"/>
        </li>
    </s:if>
    <s:else>
        <li class="breadcrumb-item"><s:a href="%{#helpInfoByOrganizationManagementUrl}"><s:text name="helpInfoByOneOrganization"/></s:a></li>
        <li class="breadcrumb-item"><s:text name="editHelpInfoByOrganization"/></li>
    </s:else>

</ol>

