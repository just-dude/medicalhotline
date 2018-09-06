<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <strong><s:text name="helpInfoByOrganization"/></strong>
            </div>
            <div class="card-block">
                <div class="row">
                    <div class="col-sm-4">
                        <strong><s:text name="organization"/>:</strong> <s:property value="helpInfoByOrganization.organization.name"/>
                    </div>
                    <div class="col-sm-4">
                        <strong><s:text name="issue.issueCategory"/>:</strong> <s:property value="helpInfoByOrganization.issueType.issueCategory.name"/>
                    </div>
                    <div class="col-sm-4">
                        <strong><s:text name="issueType"/>:</strong> <s:property value="helpInfoByOrganization.issueType.name"/>
                    </div>
                </div>
                <div class="row mt-2">
                    <div class="col-sm-12 py-1">
                        <div><strong><s:text name="helpInfoText"/>:</strong></div>
                        <div class="mt-2"><s:property value="helpInfoByOrganization.text" escapeHtml="false"/></div>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <s:url var="saveUrl" namespace="/" action="save-helpinfo-by-organization-input" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                    <s:param name="issueTypeId" value="%{issueTypeId}"/>
                    <s:param name="organizationId" value="%{organizationId}"/>
                </s:url>
                <s:url var="removeUrl" namespace="/" action="remove-helpinfo-by-organization" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                    <s:param name="issueTypeId" value="%{issueTypeId}"/>
                    <s:param name="organizationId" value="%{organizationId}"/>
                    <s:param name="pageNumber" value="%{#parameters.pageNumber}"/>
                </s:url>
                <s:i18n name="controller.struts.action.common.pages.main.package">
                    <input type="button" value="<s:text name="button.remove"/>" class="btn btn-danger" style="float:right;margin-left: 50px;" onclick="location.href='<s:property value="#removeUrl"/>'"/>
                    <input type="button" value="<s:text name="button.edit"/>" class="btn btn-primary" style="float:right;margin-left: 50px;" onclick="location.href='<s:property value="#saveUrl"/>'"/>
                </s:i18n>

            </div>
        </div>
    </div>
    <!--/col-->
</div>

