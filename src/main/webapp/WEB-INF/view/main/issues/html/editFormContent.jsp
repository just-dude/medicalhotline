<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <s:url var="goBackToAppealUrl" namespace="/" action="appeal-management" escapeAmp="false">
                <s:param name="locale" value="%{#request.locale}"/>
                <s:param name="appealId" value="%{appealId}"/>
            </s:url>
            <div class="card-header">
                <strong><s:text name="editIssueTitle"/> <s:a href="%{#goBackToAppealUrl}" escapeAmp="false">№ <s:property value="appealId"/></s:a></strong>
                <span style="float: right"><strong><s:a href="%{#goBackToAppealUrl}" escapeAmp="false"><s:text name="goBackToAppealUrl"/></s:a></strong></span>
            </div>
            <div class="card-block">
                <s:form action="edit-issue" theme="bootstrap" name="editIssueForm" id="editIssueForm">
                    <!--/row-->
                    <div class="form-group row">
                        <div class="col-sm-6">
                            <s:textarea name="issue.text" id="issueText"
                                        key="issue.text" rows="15"  />
                        </div>
                        <div class="col-sm-6">
                            <script>
                                var getIssueTypesAndCategoriesList = function () {
                                    return '<s:property value="%{issueTypesAndCategoriesListAsJson}" escapeHtml="false"/>';
                                }
                            </script>
                            <div>
                                <s:select list="%{getAllIssueCategories()}" key="IssueCategory"
                                          name="issue.issueCategory.id" id="issueCategory"/>
                            </div>
                            <div>
                                <s:select list="%{getAllIssueTypes()}" key="issueTypes"
                                          name="issueTypes" id="issueTypes" required="true" multiple="true"/>
                            </div>
                            <div>
                                <s:select list="%{findAllOrganizations()}" key="issue.organizations"
                                          name="organizationsIds" id="organizations" multiple="true"/>
                            </div>
                        </div>
                    </div>
                    <s:hidden name="locale" value="%{#request.locale}"/>
                    <s:hidden name="issueId" value="%{issueId}"/>
                    <s:hidden name="issue.id" value="%{issueId}"/>
                    <s:hidden name="appealId" value="%{appealId}"/>

                    <s:submit value="%{sendButtonText}" class="btn btn-primary" style="float:right"/>
                </s:form>


                <div class="card-header mt-1">
                    <strong><s:text name="activityTitle"/></strong>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover table-condensed">
                        <thead>
                        <tr>
                            <th style="width: 50%;"><s:text name="actionShortcutText"/></th>
                            <th><s:text name="actionStatus"/></th>
                            <th><s:text name="issue.creator"/></th>
                            <th><s:text name="dateTime"/></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="issue.takenActions" var="item">
                            <tr class="table-row">
                                <td><s:property value="%{#item.text}"/></td>
                                <s:if test="%{#item.isFinal}">
                                    <td><span class="tag tag-success"><s:text name="finalActionStatus"/></span></td>
                                </s:if>
                                <s:else>
                                    <td><span class="tag tag-warning"><s:text name="notFinalActionStatus"/></span></td>
                                </s:else>
                                <td><s:property value="%{#item.creator.profile.fullname}"/></td>
                                <td><s:property value="%{@common.DateTimeUtils@toString(#item.createdDateTime)}"/></td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!--/col-->
</div>

