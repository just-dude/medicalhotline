<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<s:i18n name="controller.struts.action.main.package">
    <ol class="breadcrumb">
    <s:url var="mainMainPageUrl" namespace="/" action="index">
        <s:param name="locale" value="%{#request.locale}"/>
    </s:url>
    <s:url var="helpInfosPageUrl" namespace="/" action="helpinfos-page" escapeAmp="false">
        <s:param name="locale" value="%{#request.locale}"/>
    </s:url>
    <s:i18n name="controller.struts.action.common.pages.main.package">
        <li class="breadcrumb-item"><s:a href="%{#mainMainPageUrl}"><s:text name="mainPage"/></s:a></li>
    </s:i18n>
    <li class="breadcrumb-item"><s:a href="%{#helpInfosPageUrl}"><s:text name="helpInfos"/></s:a></li>
    <li class="breadcrumb-item"><s:text name="helpInfo"/></li>
</s:i18n>
</ol>
