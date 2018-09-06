<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>
<ol class="breadcrumb">
    <s:url var="mainMainPageUrl" namespace="/" action="index">
        <s:param name="locale" value="%{#request.locale}"/>
    </s:url>
    <s:url var="issueTypesUrl" namespace="/" action="issuetypes-management">
        <s:param name="locale" value="%{#request.locale}"/>
        <s:param name="pageNumber" value="%{#parameters.pageNumber}"/>
        <s:param name="issueCategoryId" value="%{issueCategoryId}"/>
    </s:url>
    <s:i18n name="controller.struts.action.common.pages.main.package">
        <li class="breadcrumb-item"><s:a href="%{#mainMainPageUrl}"><s:text name="mainPage"/></s:a></li>
    </s:i18n>
    <li class="breadcrumb-item"><s:a href="%{#issueTypesUrl}"><s:text name="issueTypesManagementTitle"/></s:a></li>
    <li class="breadcrumb-item"><s:text name="editIssueType.title"/></li>
</ol>

