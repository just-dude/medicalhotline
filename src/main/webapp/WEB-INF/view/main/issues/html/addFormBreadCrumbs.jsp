<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<ol class="breadcrumb">
<s:i18n name="controller.struts.action.main.package">
    <s:url var="mainMainPageUrl" namespace="/" action="index">
        <s:param name="locale" value="%{#request.locale}"/>
    </s:url>
    <s:url var="appealsPageUrl" namespace="/" action="appeals-page-input" escapeAmp="false">
        <s:param name="locale" value="%{#request.locale}"/>
    </s:url>
    <s:url var="appealManagementUrl" namespace="/" action="appeal-management" escapeAmp="false">
        <s:param name="locale" value="%{#request.locale}"/>
        <s:param name="appealId" value="%{appealId}"/>
    </s:url>
    <s:i18n name="controller.struts.action.common.pages.main.package">
        <li class="breadcrumb-item"><s:a href="%{#mainMainPageUrl}"><s:text name="mainPage"/></s:a></li>
    </s:i18n>
    <li class="breadcrumb-item"><s:a href="%{#appealsPageUrl}"><s:text name="appealsPageTitle"/></s:a></li>
    <li class="breadcrumb-item"><s:a href="%{#appealManagementUrl}"><s:text name="appealTitle"/>  № <s:property value="appealId"/></s:a></li>
    <li class="breadcrumb-item"><s:text name="addIssueTitle"/></li>
</s:i18n>
</ol>
