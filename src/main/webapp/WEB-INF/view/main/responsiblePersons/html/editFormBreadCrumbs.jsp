<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>
<ol class="breadcrumb">
    <s:url var="mainMainPageUrl" namespace="/" action="index">
        <s:param name="locale" value="%{#request.locale}"/>
    </s:url>
    <s:url var="organizationsUrl" namespace="/" action="organizations-management">
        <s:param name="locale" value="%{#request.locale}"/>
    </s:url>
    <s:url var="responsiblePersonsUrl" namespace="/" action="responsible-persons-management">
        <s:param name="locale" value="%{#request.locale}"/>
        <s:param name="organizationId" value="%{#parameters.organizationId}"/>
    </s:url>
    <s:i18n name="controller.struts.action.common.pages.main.package">
        <li class="breadcrumb-item"><s:a href="%{#mainMainPageUrl}"><s:text name="mainPage"/></s:a></li>
    </s:i18n>
    <li class="breadcrumb-item"><s:a href="%{#organizationsUrl}"><s:text name="organizations"/></s:a></li>
    <li class="breadcrumb-item"><s:a href="%{#responsiblePersonsUrl}"><s:text name="responsiblePersons"/></s:a></li>
    <li class="breadcrumb-item"><s:text name="editResponsiblePerson"/></li>
</ol>

