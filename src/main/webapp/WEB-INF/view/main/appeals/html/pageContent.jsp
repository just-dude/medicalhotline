<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="acceptButtonText" name="acceptButtonText"/>
<s:url var="addAppealUrl" namespace="/" action="add-appeal-main-info-input" escapeAmp="false">
    <s:param name="locale" value="%{#request.locale}"/>
</s:url>


<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <span><strong><s:text name="appealListTitle"/> (<s:property value="appealsPage.totalElements"/>)</strong></span>
                    <input type="button" value="<s:text name="addAppealAction"/>" id="add-appeal" class="btn btn-primary" style="float:right;"
                           onclick="location.href='<s:property value="#addAppealUrl"/>'" />
            </div>
            <div class="card-block">
                <div class="card-header mt-1">
                    <s:form action="appeals-page" theme="bootstrap" name="appealsListParamtersForm" id="appealsListParamtersForm" method="GET">
                        <div class="row">
                            <div class="col-sm-3">
                                <s:select list="%{findAllAppealStates()}" key="appeal.state"
                                          name="appealsStates" id="appealsStates" multiple="true"/>
                            </div>
                            <div class="col-sm-2">
                                <s:textfield key="appealId" name="appealId"/>
                            </div>
                            <div class="col-sm-2">
                                <s:textfield key="appeal.contactPhoneNumber" name="contactInfo" id="contactInfo"/>
                            </div>
                            <div class="col-sm-2">
                                <s:textfield key="citizenSurname" name="citizenSurname"/>
                            </div>
                            <div class="col-sm-1" style="margin-top: 6px;">
                                <s:if test="%{onlyMyAppealsFlag}">
                                    <input type="checkbox" name="onlyMyAppealsFlag" value="true" checked id="onlyMyAppealsFlag" class="mt-2">
                                </s:if>
                                <s:else>
                                    <input type="checkbox" name="onlyMyAppealsFlag" value="true"  id="onlyMyAppealsFlag" class="mt-2">
                                </s:else>
                                <s:text name="onlyMyAppealsFlag"/>
                            </div>
                            <div class="col-sm-1" >
                                <s:select list="%{{'15','30','45'}}" key="entitiesCountPerPage"
                                          name="entitiesPerPage" id="entitiesPerPage"/>
                            </div>
                        </div>
                        <s:if test="%{(issueCategoriesIds!=null && issueCategoriesIds.size()>0)
                            || startPeriodDateTime!=null || endPeriodDateTime!=null
                            || (citizenName!=null && !citizenName.equals('')) || (citizenPatronymic!=null && !citizenPatronymic.equals(''))
                            || appealCreatorId!=null
                            }">
                            <s:set var="additionalFilterOptionsClass" value="'in'"/>
                        </s:if>
                        <s:else>
                            <s:set var="additionalFilterOptionsClass" value=""/>
                        </s:else>
                        <div class="row collapse <s:property value="%{#additionalFilterOptionsClass}"/>" id="additionalFilterOptions">
                            <div class="col-sm-3">
                                <s:select list="%{findAllIssueCategories()}" key="issue.categories"
                                          name="issueCategoriesIds" id="issueCategories" multiple="true"/>
                            </div>
                            <div class="col-sm-2">
                                <s:textfield key="startPeriodDateTime" name="startPeriodDateTime" id="startPeriodDateTime"/>
                            </div>
                            <div class="col-sm-2">
                                <s:textfield key="endPeriodDateTime" name="endPeriodDateTime" id="endPeriodDateTime"/>
                            </div>
                            <div class="col-sm-2">
                                <s:textfield key="citizenName" name="citizenName"/>
                            </div>
                            <div class="col-sm-2">
                                <s:textfield key="citizenPatronymic" name="citizenPatronymic"/>
                            </div>
                            <div class="col-sm-3">
                                <s:select list="%{findAllAppealCreators()}" key="appeal.creator"
                                          name="appealCreatorId" id="appealCreator" emptyOption="true"/>
                            </div>
                            <s:hidden name="locale" value="%{#request.locale}"/>
                        </div>
                        <div class="row">
                            <button class="btn btn-link" type="button" data-toggle="collapse" data-target="#additionalFilterOptions" aria-expanded="false" aria-controls="additionalFilterOptions">
                                <s:text name="showAdditionalFilterOptions"/>
                            </button>
                            <div style="float: right">
                                <s:submit value="%{acceptButtonText}" class="btn btn-primary" style="float:right;"/>
                            </div>
                        </div>
                    </s:form>

                </div>
                <div class="table-responsive">
                    <table class="table table-hover table-condensed">
                        <thead>
                        <tr>
                            <th>№</th>
                            <th><s:text name="appeal.createdDateTimeFormattedString"/></th>
                            <th><s:text name="appeal.contactPhoneNumber"/></th>
                            <th><s:text name="citizen"/></th>
                            <th><s:text name="issue.categories"/></th>
                            <th><s:text name="appeal.creator.fullName"/></th>
                            <th><s:text name="appeal.state"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="appealsPage" var="item">
                            <s:url var="appealManagementUrl" namespace="/" action="appeal-management" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="appealId" value="%{#item.id}"/>
                            </s:url>
                            <tr class="table-row pointer-cursor" onclick="location.href='<s:property value="%{#appealManagementUrl}"/>'" >
                                <td><s:property value="%{#item.id}"/></td>
                                <td><s:property value="%{@common.DateTimeUtils@toString(#item.createdDateTime)}"/></td>
                                <td><s:property value="%{#item.contactPhoneNumber}"/></td>
                                <td>
                                    <s:property value="%{#item.citizen.surname}"/>
                                    <s:property value="%{#item.citizen.name}"/>
                                    <s:property value="%{#item.citizen.patronymic}"/>
                                </td>
                                <td>
                                    <ul>
                                        <s:iterator value="issues" var="issueItem">
                                            <li><s:property value="%{#issueItem.issueCategory.name}"/></li>
                                        </s:iterator>
                                    </ul>
                                </td>
                                <td><s:property value="%{#item.creator.profile.fullName}"/></td>
                                <td>
                                    <s:if test="%{#item.state.toString()=='NotFilled'}"><s:set var="statusClass" value="'tag-danger'"/></s:if>
                                    <s:elseif test="%{#item.state.toString()=='Filling' || #item.state.toString()=='OnTheGo'}">
                                        <s:set var="statusClass" value="'tag-warning'"/>
                                    </s:elseif>
                                    <s:elseif test="%{#item.state.toString()=='Resolved' || #item.state.toString()=='PartiallyResolved'}">
                                        <s:set var="statusClass" value="'tag-success'"/>
                                    </s:elseif>
                                    <s:elseif test="%{#item.state.toString()=='ImpossibleToSolve'}"><s:set var="statusClass" value="'tag-very-danger'"/></s:elseif>
                                    <s:elseif test="%{#item.state.toString()=='Canceled'}"><s:set var="statusClass" value="'tag-default'"/></s:elseif>
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
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>

                <s:url var="pagginationBaseUrl" namespace="/" action="appeals-page" escapeAmp="false" includeParams="get">
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

