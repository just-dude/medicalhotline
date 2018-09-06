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
                    <strong><s:text name="addIssueTitle"/> <s:a href="%{#goBackToAppealUrl}" escapeAmp="false">№ <s:property value="appealId"/></s:a></strong>
                    <span style="float: right"><strong><s:a href="%{#goBackToAppealUrl}" escapeAmp="false"><s:text name="goBackToAppealUrl"/></s:a></strong></span>
                </div>
                <div class="card-block">
                    <s:form action="add-issue" theme="bootstrap" name="addIssueForm" id="addIssueForm">
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
                                <th><s:text name="actionShortcutText"/></th>
                                <th><s:text name="dateTime"/></th>
                                <th><s:text name="actionStatus"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <input type="button" value="<s:text name="addAction"/>" id="add-action" class="btn btn-primary" style="float:right; margin-right: 10px;" disabled="true"/>
                </div>
            </div>
    </div>
    <!--/col-->
</div>

