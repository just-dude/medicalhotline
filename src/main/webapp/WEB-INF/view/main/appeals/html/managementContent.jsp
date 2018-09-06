<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <span><strong><s:text name="appealTitle"/> № <s:property value="appeal.id"/></strong></span>
                <span style="float: right"><strong><s:property value="@common.DateTimeUtils@toString(appeal.createdDateTime)"/></strong></span>
            </div>
            <div class="card-block">

                <div class="row">
                    <div class="col-sm-3 py-1">
                        <strong><s:text name="appeal.contactPhoneNumber"/>: </strong><s:property value="appeal.contactPhoneNumber"/>
                    </div>
                    <div class="col-sm-3 py-1">
                        <strong><s:text name="fio"/>: </strong><s:property value="appeal.citizen.surname"/> <s:property value="appeal.citizen.name"/> <s:property value="appeal.citizen.patronymic"/>
                    </div>
                    <div class="col-sm-3 py-1">
                        <s:if test="%{appeal.state.toString()=='NotFilled'}"><s:set var="statusClass" value="'tag-danger'"/></s:if>
                        <s:elseif test="%{appeal.state.toString()=='Filling' || appeal.state.toString()=='OnTheGo'}">
                            <s:set var="statusClass" value="'tag-warning'"/>
                        </s:elseif>
                        <s:elseif test="%{appeal.state.toString()=='Resolved' || appeal.state.toString()=='PartiallyResolved'}">
                            <s:set var="statusClass" value="'tag-success'"/>
                        </s:elseif>
                        <s:elseif test="%{appeal.state.toString()=='ImpossibleToSolve'}"><s:set var="statusClass" value="'tag-very-danger'"/></s:elseif>
                        <s:elseif test="%{appeal.state.toString()=='Canceled'}"><s:set var="statusClass" value="'tag-default'"/></s:elseif>
                        <strong><s:text name="appeal.state"/>:&nbsp;</strong>
                        <span class="tag <s:property value="#statusClass"/>">
                            <s:property value="%{getText(appeal.state)}"/>
                        </span>
                        <s:if test="appeal.causeOfTransitionToFinalState!=null">
                            <div>
                                <strong><s:text name="appeal.causeOfTransitionToFinalState"/>:&nbsp;</strong>
                                <s:property value="%{getText(appeal.causeOfTransitionToFinalState)}"/>
                            </div>
                        </s:if>
                    </div>
                    <div class="col-sm-3 py-1">
                        <strong><s:text name="appeal.creator"/>: </strong><s:property value="appeal.creator.profile.fullname"/>
                    </div>
                </div>
                <!--/row-->
                <s:url var="editAppealMainInfo" namespace="/" action="edit-appeal-main-info-input" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                    <s:param name="appealId" value="%{appealId}"/>
                </s:url>
                <div class="row">
                    <div class="col-sm-3 py-1">
                        <s:if test="appeal.citizen.omsPolicyNumber!=null">
                            <strong><s:text name="appeal.citizen.smo"/>: </strong><br/><s:property value="appeal.citizen.smo.name"/>
                        </s:if>
                    </div>
                    <div class="col-sm-3 py-1">
                        <s:if test="appeal.citizen.omsPolicyNumber!=null">
                            <strong><s:text name="appeal.citizen.omsPolicyNumber"/>: </strong><s:property value="appeal.citizen.omsPolicyNumber"/>
                        </s:if>
                    </div>
                    <div class="col-sm-4 py-1">
                        <s:if test="appeal.citizen.placeOfLiving!=null">
                            <strong><s:text name="appeal.citizen.placeOfLiving"/>: </strong><s:property value="appeal.citizen.placeOfLiving"/>
                        </s:if>
                    </div>
                    <s:if test="%{appeal.isAllowedToEdit()}">
                        <div class="col-sm-2">
                            <input type="button" value="<s:text name="editAppeal"/>" id="edit-appeal" class="btn btn-warning" style="float:right;margin-top:20px;" onclick="location.href='<s:property value="%{#editAppealMainInfo}"/>'" />
                        </div>
                    </s:if>
                </div>


                <div class="card-header" style="margin-top:20px;">
                    <strong><s:text name="issuesTitle"/></strong>
                </div>
                <div class="table-responsive">
                    <table class="table table-hover table-condensed">
                        <thead>
                        <tr>
                            <th style="width: 50%;"><s:text name="issueShortcutText"/></th>
                            <th><s:text name="issueState"/></th>
                            <th><s:text name="issue.issueCategory"/></th>
                            <th><s:text name="issueTypes"/></th>
                            <th><s:text name="issue.organizations"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="appeal.issues" var="item">
                            <s:url var="issueManagementUrl" namespace="/" action="issue-management" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="issueId" value="%{#item.id}"/>
                            </s:url>
                            <tr class="table-row pointer-cursor" onclick="location.href='<s:property value="%{#issueManagementUrl}"/>'" >
                                <td><s:property value="%{#item.text}"/></td>
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
                                        <s:text name="causeOfTransitionToFinalState"/>: <s:property value="%{getText(#item.causeOfTransitionToFinalState)}"/>
                                    </s:if>
                                </td>
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
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>
                <s:if test="%{appeal.isAllowedToAddIssues()}">
                    <s:url var="addIssuelUrl" namespace="/" action="add-issue-input" escapeAmp="false">
                        <s:param name="locale" value="%{#request.locale}"/>
                        <s:param name="appealId" value="%{appealId}"/>
                    </s:url>
                    <input type="button" value="<s:text name="addIssue"/>" id="add-issue" class="btn btn-primary" style="float:right;" onclick="location.href='<s:property value="#addIssuelUrl"/>'"/>
                </s:if>


            </div>
            <div class="card-footer">
                <s:if test="%{appeal.isAllowedToGoToTheOnTheGoState()}">
                    <s:url var="goToOnTheGoStateUrl" namespace="/" action="go-appeal-to-onthego-state" escapeAmp="false">
                        <s:param name="locale" value="%{#request.locale}"/>
                        <s:param name="appealId" value="%{appealId}"/>
                    </s:url>
                    <input type="button" value="<s:text name="goToOnTheGoState"/>" id="go-appeal-to-onthego-state" class="btn btn-primary" style="float:right;margin-left: 50px;" onclick="location.href='<s:property value="#goToOnTheGoStateUrl"/>'"/>
                </s:if>

                <s:if test="%{appeal.isAllowedToGoToTheCanceledState()}">
                    <s:form action="go-appeal-to-canceled-state" theme="bootstrap" name="go-to-canceled-state-form" id="go-to-canceled-state-form" style="float:right; ">
                        <div id="causeOfTransitionToFinalStateWrapper" style="display: none; margin-bottom: 20px;" >
                            <h6><s:text name="goToCanceledStateTitle"/></h6>
                            <s:select list="%{findAllDefaultCausesOfTransition()}" key="defaultCauseOfTransitionToCanceledState"
                                      name="defaultCauseOfTransitionToCanceledState" id="defaultCauseOfTransitionToCanceledState" required="true"/>
                            <s:textarea key="myselfCauseOfTransition" id="myselfCauseOfTransition" name="myselfCauseOfTransition"  rows="5" cols="50" />
                        </div>
                        <s:hidden name="locale" value="%{#request.locale}"/>
                        <s:hidden name="appealId" value="%{appealId}"/>
                        <div>
                            <s:submit key="goToCanceledState" id="go-appeal-to-canceled-state" class="btn btn-danger" style="float: right;"/>
                        </div>
                    </s:form>
                </s:if>
            </div>
        </div>
        <s:if test="%{isPermittedToViewChangesHistory() && hasAtLeastOneFieldPrevState()}">
            <div class="card">
                <s:url var="showChangesHstoryUrl" namespace="/" action="appeal-management" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                    <s:param name="appealId" value="%{appealId}"/>
                    <s:param name="showChangesHistory" value="true"/>
                </s:url>
                <s:url var="hideChangesHstoryUrl" namespace="/" action="appeal-management" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
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
                            <s:text name="changesHistory"/>
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

    </div>
    <!--/col-->
</div>

