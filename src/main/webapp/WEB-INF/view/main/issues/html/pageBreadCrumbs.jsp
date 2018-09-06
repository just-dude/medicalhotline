<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<s:i18n name="controller.struts.action.main.package">
    <ol class="breadcrumb">
    <s:url var="mainMainPageUrl" namespace="/" action="index">
        <s:param name="locale" value="%{#request.locale}"/>
    </s:url>
    <s:url var="appealManagementUrl" namespace="/" action="appeal-management" escapeAmp="false">
        <s:param name="locale" value="%{#request.locale}"/>
        <s:param name="appealId" value="%{issue.appeal.id}"/>
    </s:url>
    <s:i18n name="controller.struts.action.common.pages.main.package">
        <li class="breadcrumb-item"><s:a href="%{#mainMainPageUrl}"><s:text name="mainPage"/></s:a></li>
    </s:i18n>
    <shiro:hasPermission name="appeal:view:*:*">
        <li class="breadcrumb-item"><s:a href="%{#appealsPageUrl}"><s:text name="appealsPageTitle"/></s:a></li>
    </shiro:hasPermission>
    <li class="breadcrumb-item"><s:text name="issuesPageTitle"/></li>
</s:i18n>
</ol>
