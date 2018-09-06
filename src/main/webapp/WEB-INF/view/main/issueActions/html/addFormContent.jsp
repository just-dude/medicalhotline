<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<div class="row">
    <div class="col-sm-12">
            <div class="card">
                <s:form action="add-issueaction" theme="bootstrap" name="addIssueActionForm" id="addIssueActionForm">
                    <div class="card-header">
                        <strong><s:text name="addIssueAction"/></strong>
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
                            <shiro:hasPermission name="issueAction:add:isFinal:*">
                                <div class="col-sm-4">
                                    <s:checkbox name="issueAction.isFinal" id="isFinalIssueAction" key="issueAction.isFinal" class="my-2"/>
                                </div>
                            </shiro:hasPermission>
                        </div>
                        <s:hidden name="locale" value="%{#request.locale}"/>
                        <s:hidden name="issueId" value="%{issueId}"/>
                        <s:hidden name="appealId" value="%{appealId}"/>
                    </div>
                    <div class="card-footer">
                        <s:submit value="%{sendButtonText}" class="btn btn-primary" style="float:right;margin-left: 30px;"/>
                        <s:url var="mangementIssueUrl" namespace="/" action="issue-management" escapeAmp="false">
                            <s:param name="locale" value="%{#request.locale}"/>
                            <s:param name="issueId" value="%{issueId}"/>
                        </s:url>
                        <input type="button" value="<s:text name="cancel"/>" id="mangementIssue" class="btn btn-warning" style="float:right;" onclick="location.href='<s:property value="#mangementIssueUrl"/>'"/>
                    </div>
                </s:form>
            </div>
    </div>
    <!--/col-->
</div>

