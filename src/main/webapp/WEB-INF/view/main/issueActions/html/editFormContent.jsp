<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<div class="row">
    <div class="col-sm-12">
            <div class="card">
                <s:form action="edit-issueaction" theme="bootstrap" name="editIssueActionForm" id="editIssueActionForm">
                    <div class="card-header">
                        <strong><s:text name="editIssueAction"/></strong>
                    </div>
                    <div class="card-block">
                        <!--/row-->
                        <div class="row mb-2">
                            <div class="col-sm-12 py-1">
                                <s:text name="issueTitle"/>: <s:property value="issue.text"/>
                            </div>
                        </div>
                        <!--/row-->
                        <div class="form-group row">
                            <div class="col-sm-6">
                                <s:textarea name="issueAction.text" id="issueActionText"
                                             key="issueAction.text" rows="8"  />
                            </div>
                        </div>
                        <s:hidden name="locale" value="%{#request.locale}"/>
                        <s:hidden name="issueId" value="%{issueId}"/>
                        <s:hidden name="appealId" value="%{appealId}"/>
                        <s:hidden name="issueActionId" value="%{issueActionId}"/>
                        <s:hidden name="issueAction.id" value="%{issueActionId}"/>
                    </div>
                    <div class="card-footer">
                        <s:submit value="%{sendButtonText}" class="btn btn-primary" style="float:right;margin-left: 30px;"/>
                        <s:url var="mangementIssueActionUrl" namespace="/" action="issueaction-management" escapeAmp="false">
                            <s:param name="locale" value="%{#request.locale}"/>
                            <s:param name="issueActionId" value="%{issueActionId}"/>
                            <s:param name="issueId" value="%{issueId}"/>
                            <s:param name="appealId" value="%{appealId}"/>
                        </s:url>
                        <input type="button" value="<s:text name="cancel"/>" id="mangementIssueAction" class="btn btn-warning" style="float:right;" onclick="location.href='<s:property value="#mangementIssueUrl"/>'"/>
                    </div>
                </s:form>
            </div>
    </div>
    <!--/col-->
</div>

