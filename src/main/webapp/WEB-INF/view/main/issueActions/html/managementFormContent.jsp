<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <strong><s:text name="managementIssueAction"/></strong>
            </div>
            <div class="card-block">
                <!--/row-->
                <div class="row mb-2">
                    <div class="col-sm-12 py-1">
                        <strong><s:text name="issueTitle"/>:</strong> <s:property value="issue.text"/>
                    </div>
                </div>
                <!--/row-->
                <div class="row">
                    <div class="col-sm-12">
                        <s:if test="issueAction.isFinal">
                            <strong><s:text name="issueAction.isFinal"/>: </strong> <s:property value="issueAction.text"/>
                        </s:if>
                        <s:else>
                            <strong><s:text name="issueAction.notIsFinal"/>: </strong> <s:property value="issueAction.text"/>
                        </s:else>
                    </div>
                </div>
                <shiro:hasPermission name="issueaction:edit:*:${issueActionId}">
                    <s:if test="%{issueAction.isAllowedToEdit()}">
                        <s:url var="editIssueActionUrl" namespace="/" action="edit-issueaction-input" escapeAmp="false">
                            <s:param name="locale" value="%{#request.locale}"/>
                            <s:param name="issueActionId" value="%{issueActionId}"/>
                            <s:param name="issueId" value="%{issueId}"/>
                            <s:param name="appealId" value="%{appealId}"/>
                        </s:url>
                        <div class="row">
                            <div class="col-sm-12">
                                <input type="button"  value="<s:text name="editButton"/>" id="edit" class="btn btn-warning" style="float:right; margin-right: 10px;" onclick="location.href='<s:property value="#editIssueActionUrl"/>'"/>
                            </div>
                        </div>
                    </s:if>
                </shiro:hasPermission>
            </div>
            <div class="card-footer">
            </div>
        </div>

        <shiro:hasPermission name="issueAction:view:changesHistory:*">
            <s:if test="%{hasAtLeastOneFieldPrevState()}">
                <div class="card">
                    <s:url var="showChangesHstoryUrl" namespace="/" action="issueaction-management" escapeAmp="false">
                        <s:param name="locale" value="%{#request.locale}"/>
                        <s:param name="issueActionId" value="%{issueActionId}"/>
                        <s:param name="issueId" value="%{issueId}"/>
                        <s:param name="appealId" value="%{appealId}"/>
                        <s:param name="showChangesHistory" value="true"/>
                    </s:url>
                    <s:url var="hideChangesHstoryUrl" namespace="/" action="issueaction-management" escapeAmp="false">
                        <s:param name="locale" value="%{#request.locale}"/>
                        <s:param name="issueActionId" value="%{issueActionId}"/>
                        <s:param name="issueId" value="%{issueId}"/>
                        <s:param name="appealId" value="%{appealId}"/>
                    </s:url>
                    <s:if test="%{#parameters['showChangesHistory']==null}">
                        <div class="card-header">
                            <s:a href="%{#showChangesHstoryUrl}">
                                <strong>
                                    <s:text name="showChangesHistory"/>
                                </strong>
                            </s:a>
                        </div>
                    </s:if>
                    <s:else>
                        <div class="card-header">
                            <strong>
                                <s:text name="changesHistory"/>:
                            </strong>
                            <s:a  style="float: right" href="%{#hideChangesHstoryUrl}">
                                <strong>
                                    <s:text name="hideChangesHistory"/>
                                </strong>
                            </s:a>
                        </div>
                        <div class="card-block">
                            <div class="table-responsive">
                                <table class="table table-hover table-condensed">
                                    <thead>
                                    <tr>
                                        <th><s:text name="fieldName"/></th>
                                        <th><s:text name="prevFieldValue"/></th>
                                        <th><s:text name="nextFieldValue"/></th>
                                        <th><s:text name="changingDateTime"/></th>
                                        <th><s:text name="changer"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <s:iterator value="%{findFieldPrevStatesWithNextFieldValue()}" var="entry">
                                        <tr class="table-row">
                                            <td><s:property value="%{getText(#entry.key.id.fieldName)}"/></td>
                                            <td><s:property value="%{#entry.key.fieldValue}"/></td>
                                            <td>
                                                <s:if test="%{#entry.value!='currentValue'}">
                                                    <s:property value="%{#entry.value}"/>
                                                </s:if>
                                                <s:else>
                                                    <s:text name="currentValue"/>
                                                </s:else>
                                            </td>
                                            <td><s:property value="%{@common.DateTimeUtils@toString(#entry.key.id.changingDateTime)}"/></td>
                                            <td><s:property value="%{#entry.key.changer.profile.fullname}"/></td>
                                        </tr>
                                    </s:iterator>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="card-footer">
                        </div>
                    </s:else>
                </div>
            </s:if>
        </shiro:hasPermission>
    </div>
    <!--/col-->
</div>

