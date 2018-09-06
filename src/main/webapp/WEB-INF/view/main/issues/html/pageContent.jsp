<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="acceptButtonText" name="acceptButtonText"/>
<s:url var="addIssueUrl" namespace="/" action="add-appeal-main-info-input" escapeAmp="false">
    <s:param name="locale" value="%{#request.locale}"/>
</s:url>


<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <span><strong><s:text name="issueListTitle"/>  (<s:property value="issuesPage.totalElements"/>)</strong></span>
            </div>
            <div class="card-block">
                <shiro:hasPermission name="issue:viewMany:*:*">
                    <div class="card-header mt-1">
                        <s:form action="issues-page" theme="bootstrap" name="issuesListParamtersForm" id="issuesListParamtersForm" method="GET">
                            <div class="row">
                                <div class="col-sm-3">
                                    <s:select list="%{findAllIssueStates()}" key="issue.state"
                                              name="issuesStates" id="issuesStates" multiple="true"/>
                                </div>
                                <div class="col-sm-3">
                                    <s:select list="%{findAllIssueCategories()}" key="issue.categories"
                                              name="issueCategoriesIds" id="issueCategories" multiple="true"/>
                                </div>
                                <div class="col-sm-4">
                                    <s:select list="%{findAllIssueTypes()}" key="issueTypes"
                                              name="issueTypes" id="issueTypes" multiple="true"/>
                                </div>

                                    <div class="col-sm-1" style="margin-top: 6px;">
                                        <s:if test="%{onlyMyIssuesFlag}">
                                            <input type="checkbox" name="onlyMyIssuesFlag" value="true" checked id="onlyMyIssuesFlag" class="mt-2"/>
                                        </s:if>
                                        <s:else>
                                            <input type="checkbox" name="onlyMyIssuesFlag" value="true"  id="onlyMyIssuesFlag" class="mt-2"/>
                                        </s:else>
                                        <s:text name="onlyMyIssuesFlag"/>
                                    </div>
                                <div class="col-sm-1" >
                                    <s:select list="%{{'15','30','45'}}" key="entitiesCountPerPage"
                                              name="entitiesPerPage" id="entitiesPerPage"/>
                                </div>
                            </div>
                            <s:if test="%{startPeriodDateTime!=null || endPeriodDateTime!=null || organizationsIds!=null}">
                                <s:set var="additionalFilterOptionsClass" value="'in'"/>
                            </s:if>
                            <s:else>
                                <s:set var="additionalFilterOptionsClass" value=""/>
                            </s:else>
                            <div class="row collapse <s:property value="%{#additionalFilterOptionsClass}"/>" id="additionalFilterOptions">
                                <div class="col-sm-3">
                                    <s:select list="%{findAllOrganizations()}" key="organizations"
                                              name="organizationsIds" id="organizations" multiple="true"/>
                                </div>
                                <div class="col-sm-2">
                                    <s:textfield key="startPeriodDateTime" name="startPeriodDateTime" id="startPeriodDateTime"/>
                                </div>
                                <div class="col-sm-2">
                                    <s:textfield key="endPeriodDateTime" name="endPeriodDateTime" id="endPeriodDateTime"/>
                                </div>
                            </div>
                            <div class="row">
                                <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#additionalFilterOptions" aria-expanded="false" aria-controls="additionalFilterOptions">
                                    <s:text name="showAdditionalFilterOptions"/>
                                </button>

                                <div class="col-sm-12" >
                                    <s:submit value="%{acceptButtonText}" class="btn btn-primary" style="float:right;"/>
                                </div>
                            </div>
                            <s:hidden name="locale" value="%{#request.locale}"/>
                        </s:form>
                    </div>
                </shiro:hasPermission>
                <div class="table-responsive">
                    <table class="table table-hover table-condensed">
                        <thead>
                        <tr>
                            <th width="50%"><s:text name="issue.text"/></th>
                            <th><s:text name="issue.issueCategory"/></th>
                            <th><s:text name="issueTypes"/></th>
                            <th><s:text name="issue.organizations"/></th>
                            <th><s:text name="issue.state"/></th>
                            <th><s:text name="appeal.creator.fullName"/></th>
                            <th><s:text name="appeal.createdDateTimeFormattedString"/></th>
                            <shiro:hasPermission name="issue:viewMany:*:*">
                                <th><s:text name="appealTitle"/></th>
                            </shiro:hasPermission>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="issuesPage" var="item">
                            <s:url var="issueManagementUrl" namespace="/" action="issue-management" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="issueId" value="%{#item.id}"/>
                            </s:url>
                            <s:url var="appealManagementUrl" namespace="/" action="appeal-management" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="appealId" value="%{#item.appeal.id}"/>
                            </s:url>
                            <tr class="table-row pointer-cursor" onclick="location.href='<s:property value="%{#issueManagementUrl}"/>'" >
                                <td><s:property value="%{#item.text}"/></td>
                                <td><s:property value="%{#item.issueCategory.name}"/></td>
                                <td>
                                    <ul>
                                    <s:iterator value="%{#item.issueTypes}" var="subitem">
                                        <li><s:property value="%{#subitem.name}"/></li>
                                    </s:iterator>
                                    </ul>
                                </td>
                                <td>
                                    <ul>
                                        <s:iterator value="%{#item.organizations}" var="subitem">
                                            <li><s:property value="%{#subitem.name}"/></li>
                                        </s:iterator>
                                    </ul>
                                </td>
                                <td>
                                    <s:if test="%{#item.state.toString()=='PrimaryProcessing'}"><s:set var="statusClass" value="'tag-danger'"/></s:if>
                                    <s:elseif test="%{#item.state.toString()=='OnTheGo'}"><s:set var="statusClass" value="'tag-warning'"/></s:elseif>
                                    <s:elseif test="%{#item.state.toString()=='Resolved'}"><s:set var="statusClass" value="'tag-success'"/></s:elseif>
                                    <s:elseif test="%{#item.state.toString()=='NotRelevant'}"><s:set var="statusClass" value="'tag-default'"/></s:elseif>
                                    <s:elseif test="%{#item.state.toString()=='ImpossibleToSolve'}"><s:set var="statusClass" value="'tag-very-danger'"/></s:elseif>
                                    <span class="tag <s:property value="#statusClass"/>">
                                        <s:property value="%{getText(#item.state)}"/>
                                    </span>
                                    <s:if test="%{#item.causeOfTransitionToFinalState!=null}">
                                        <div class="mt-1">
                                            <s:text name="causeOfTransitionToFinalState"/>:
                                            <s:property value="%{#item.causeOfTransitionToFinalState}"/>
                                        </div>
                                    </s:if>
                                </td>
                                <td><s:property value="%{#item.appeal.creator.profile.fullName}"/></td>
                                <td><s:property value="%{@common.DateTimeUtils@toString(#item.appeal.createdDateTime)}"/></td>
                                <shiro:hasPermission name="issue:viewMany:*:*">
                                    <td>
                                        <s:a href="%{#appealManagementUrl}">
                                            <s:text name="appealTitle"/> № <s:property value="%{#item.appeal.id}"/>
                                        </s:a>
                                    </td>
                                </shiro:hasPermission>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>

                <s:url var="pagginationBaseUrl" namespace="/" action="issues-page" escapeAmp="false" includeParams="get">
                    <s:param name="locale" value="%{#request.locale}"/>
                </s:url>
                <s:include value="/WEB-INF/view/common/html/paggination.jsp"/>
            </div>
            <div class="card-footer">
            </div>
        </div>
    </div>
    <!--/col-->
</div>

