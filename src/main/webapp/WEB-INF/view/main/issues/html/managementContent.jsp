<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<div class="row">
    <div class="col-sm-12">

        <shiro:hasPermission name="issue:viewOne:appeal:?">
            <div class="card">
                <div class="card-header">
                    <span><strong><s:text name="appealTitle"/> № <s:property value="issue.appeal.id"/></strong></span>
                    <span style="float: right"><strong><s:property value="@common.DateTimeUtils@toString(issue.appeal.createdDateTime)"/></strong></span>
                </div>
                <div class="card-block">

                    <div class="row">
                        <div class="col-sm-3 py-1">
                            <strong><s:text name="appeal.contactPhoneNumber"/>: </strong><s:property value="issue.appeal.contactPhoneNumber"/>
                        </div>
                        <div class="col-sm-3 py-1">
                            <strong><s:text name="fio"/>: </strong><s:property value="issue.appeal.citizen.surname"/> <s:property value="issue.appeal.citizen.name"/> <s:property value="issue.appeal.citizen.patronymic"/>
                        </div>
                        <div class="col-sm-3 py-1">
                            <s:if test="%{issue.appeal.state.toString()=='NotFilled'}"><s:set var="statusClass" value="'tag-danger'"/></s:if>
                            <s:elseif test="%{issue.appeal.state.toString()=='Filling' || issue.appeal.state.toString()=='OnTheGo'}">
                                <s:set var="statusClass" value="'tag-warning'"/>
                            </s:elseif>
                            <s:elseif test="%{issue.appeal.state.toString()=='Resolved' || issue.appeal.state.toString()=='PartiallyResolved'}">
                                <s:set var="statusClass" value="'tag-success'"/>
                            </s:elseif>
                            <s:elseif test="%{issue.appeal.state.toString()=='ImpossibleToSolve'}"><s:set var="statusClass" value="'tag-very-danger'"/></s:elseif>
                            <s:elseif test="%{issue.appeal.state.toString()=='Canceled'}"><s:set var="statusClass" value="'tag-default'"/></s:elseif>
                            <strong><s:text name="appeal.state"/>:&nbsp;</strong>
                            <span class="tag <s:property value="#statusClass"/>">
                                        <s:property value="%{getText(issue.appeal.state)}"/>
                                    </span>
                            <s:if test="issue.appeal.causeOfTransitionToFinalState!=null">
                                <div>
                                    <strong><s:text name="appeal.causeOfTransitionToFinalState"/>:&nbsp;</strong>
                                    <s:property value="%{getText(issue.appeal.causeOfTransitionToFinalState)}"/>
                                </div>
                            </s:if>
                        </div>
                        <div class="col-sm-3 py-1">
                            <strong><s:text name="appeal.creator"/>: </strong><s:property value="issue.appeal.creator.profile.fullname"/>
                        </div>
                    </div>
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-3 py-1">
                            <s:if test="issue.appeal.citizen.omsPolicyNumber!=null">
                                <strong><s:text name="appeal.citizen.smo"/>: </strong><br/><s:property value="issue.appeal.citizen.smo.name"/>
                            </s:if>
                        </div>
                        <div class="col-sm-3 py-1">
                            <s:if test="issue.appeal.citizen.omsPolicyNumber!=null">
                                <strong><s:text name="appeal.citizen.omsPolicyNumber"/>: </strong><s:property value="issue.appeal.citizen.omsPolicyNumber"/>
                            </s:if>
                        </div>
                        <div class="col-sm-4 py-1">
                            <s:if test="issue.appeal.citizen.placeOfLiving!=null">
                                <strong><s:text name="appeal.citizen.placeOfLiving"/>: </strong><s:property value="issue.appeal.citizen.placeOfLiving"/>
                            </s:if>
                        </div>
                    </div>
                </div>
                <div class="card-footer">
                </div>
            </div>
        </shiro:hasPermission>


        <div class="card">
            <s:url var="goBackToAppealUrl" namespace="/" action="appeal-management" escapeAmp="false">
                <s:param name="locale" value="%{#request.locale}"/>
                <s:param name="appealId" value="%{issue.appeal.id}"/>
            </s:url>
            <div class="card-header">
                <strong>
                    <shiro:hasPermission name="appeal:view:*:*">
                        <s:text name="issueOfAppealTitle"/>
                        <s:a href="%{#goBackToAppealUrl}" escapeAmp="false"> № <s:property value="issue.appeal.id"/></s:a>
                    </shiro:hasPermission>
                    <shiro:lacksPermission name="appeal:view:*:*">
                        <s:text name="issue"/>
                    </shiro:lacksPermission>
                </strong>
            </div>

            <div class="card-block">

                <s:url var="editIssueUrl" namespace="/" action="edit-issue-input" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                    <s:param name="issueId" value="%{issueId}"/>
                    <s:param name="appealId" value="%{issue.appeal.id}"/>
                </s:url>
                <!--/row-->
                <div class="row mb-1">
                    <div class="col-sm-4 py-1">
                        <strong><s:text name="issue.text"/>:</strong>
                        <s:property value="issue.text"/>
                    </div>
                    <div class="col-sm-2 py-1">
                        <s:if test="%{issue.state.toString()=='PrimaryProcessing'}"><s:set var="statusClass" value="'tag-danger'"/></s:if>
                        <s:elseif test="%{issue.state.toString()=='OnTheGo'}"><s:set var="statusClass" value="'tag-warning'"/></s:elseif>
                        <s:elseif test="%{issue.state.toString()=='Resolved'}"><s:set var="statusClass" value="'tag-success'"/></s:elseif>
                        <s:elseif test="%{issue.state.toString()=='NotRelevant'}"><s:set var="statusClass" value="'tag-default'"/></s:elseif>
                        <s:elseif test="%{issue.state.toString()=='ImpossibleToSolve'}"><s:set var="statusClass" value="'tag-very-danger'"/></s:elseif>

                        <strong><s:text name="issue.state"/>:&nbsp;</strong>
                        <span class="tag <s:property value="#statusClass"/>">
                            <s:property value="%{getText(issue.state)}"/>
                        </span>
                        <s:if test="issue.causeOfTransitionToFinalState!=null">
                            <div>
                                <strong><s:text name="causeOfTransitionToFinalState"/>:&nbsp;</strong>
                                <s:property value="%{getText(issue.causeOfTransitionToFinalState)}"/>
                            </div>
                        </s:if>
                    </div>
                    <div class="col-sm-3 py-1">
                        <div class="mb-1">
                            <strong><s:text name="issue.issueCategory"/>:</strong>
                            <s:property value="issue.issueCategory.name"/>
                        </div>
                        <div>
                            <strong><s:text name="issueTypes"/>:</strong>
                            <ul style="padding-left: 20px;margin-top:10px;">
                                <s:iterator value="issue.issueTypes" var="item">
                                    <li><s:property value="%{#item.name}"/></li>
                                </s:iterator>
                            </ul>
                        </div>
                    </div>
                    <div class="col-sm-3 py-1">
                        <strong><s:text name="issue.organizations"/>:</strong>
                        <ul style="padding-left: 20px;margin-top:10px;">
                            <s:iterator value="issue.organizations" var="item">
                                <li><s:property value="%{#item.name}"/></li>
                            </s:iterator>
                        </ul>
                    </div>

                </div>
                <shiro:hasPermission name="issue:edit:*:*">
                    <div class="row">
                        <s:if test="%{issue.isAllowedToEdit()}">
                            <div class="col-sm-12">
                                <input type="button" value="<s:text name="editButton"/>" id="edit-issue" class="btn btn-warning" style="float:right;margin-top:20px;" onclick="location.href='<s:property value="%{#editIssueUrl}"/>'" />
                            </div>
                        </s:if>
                    </div>
                </shiro:hasPermission>


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
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="issue.takenActions" var="item">
                            <s:url var="issueActionManagementUrl" namespace="/" action="issueaction-management" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="issueActionId" value="%{#item.id}"/>
                                <s:param name="issueId" value="%{issueId}"/>
                                <s:param name="appealId" value="%{issue.appeal.id}"/>
                            </s:url>
                            <tr class="table-row pointer-cursor" onclick="location.href='<s:property value="%{#issueActionManagementUrl}"/>'">
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
                <shiro:hasPermission name="issueaction:add:text:${issueId}">
                    <s:if test="%{issue.isAllowedToAddActions()}">
                        <s:url var="addIssueActionUrl" namespace="/" action="add-issueaction-input" escapeAmp="false">
                            <s:param name="locale" value="%{#request.locale}"/>
                            <s:param name="issueId" value="%{issueId}"/>
                            <s:param name="appealId" value="%{issue.appeal.id}"/>
                        </s:url>
                        <input type="button"  value="<s:text name="addAction"/>" id="add-action" class="btn btn-primary" style="float:right; margin-right: 10px;" onclick="location.href='<s:property value="#addIssueActionUrl"/>'"/>
                    </s:if>
                </shiro:hasPermission>
            </div>
            <shiro:hasPermission name="issue:gotostate:*:*">
                <div class="card-footer">
                    <s:if test="%{issue.isAllowedToGoToImpossibleToSolveState()}">
                        <s:form action="go-issue-to-impossibletosolve-state" theme="bootstrap" name="go-issue-to-impossibletosolve-state-form" id="go-issue-to-impossibletosolve-state-form" style="float:right; margin-left:50px">
                            <div id="causeOfTransitionToImpossibleToSolveWrapper" style="display: none; margin-bottom: 20px;" >
                                <h6><s:text name="goToImpossibleToSolveStateTitle"/></h6>
                                <s:textarea key="causeOfTransition" name="causeOfTransition"  rows="5" cols="50" />
                            </div>
                            <s:hidden name="locale" value="%{#request.locale}"/>
                            <s:hidden name="issueId" value="%{issueId}"/>
                            <div>
                                <s:submit key="goToImpossibleToSolveState" id="go-issue-to-notrelevant-state" class="btn btn-danger" style="float: right;"/>
                            </div>
                        </s:form>
                    </s:if>
                    <s:if test="%{issue.isAllowedToGoToNotRelevantState()}">
                        <s:form action="go-issue-to-notrelevant-state" theme="bootstrap" name="go-issue-to-notrelevant-state-form" id="go-issue-to-notrelevant-state-form" style="float:right; ">
                            <div id="causeOfTransitionToNotRelevantStateWrapper" style="display: none; margin-bottom: 20px;" >
                                <h6><s:text name="goToNotRelevantStateTitle"/></h6>
                                <s:textarea key="causeOfTransition" name="causeOfTransition"  rows="5" cols="50" />
                            </div>
                            <s:hidden name="locale" value="%{#request.locale}"/>
                            <s:hidden name="issueId" value="%{issueId}"/>
                            <div>
                                <s:submit key="goToNotRelevantState" id="go-issue-to-notrelevant-state" class="btn btn-danger" style="float: right;"/>
                            </div>
                        </s:form>
                    </s:if>
                </div>
            </shiro:hasPermission>
        </div>
        <shiro:hasPermission name="issue:viewOne:changesHistory:*">
            <s:if test="%{hasAtLeastOneFieldPrevState()}">
                <div class="card">
                    <s:url var="showChangesHstoryUrl" namespace="/" action="issue-management" escapeAmp="false">
                        <s:param name="locale" value="%{#request.locale}"/>
                        <s:param name="issueId" value="%{issueId}"/>
                        <s:param name="showChangesHistory" value="true"/>
                    </s:url>
                    <s:url var="hideChangesHstoryUrl" namespace="/" action="issue-management" escapeAmp="false">
                        <s:param name="locale" value="%{#request.locale}"/>
                        <s:param name="issueId" value="%{issueId}"/>
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
        </shiro:hasPermission>

        <shiro:hasPermission name="issue:viewOne:organizationsInfo:*">
            <s:iterator value="organizations" var="item">
                <div class="card">
                    <div class="card-header pointer-cursor" data-toggle="collapse" data-target="#organizationInfoBlock-<s:property value="%{#item.id}"/>" aria-expanded="false"
                         aria-controls="organizationInfoBlock-<s:property value="%{#item.id}"/>">
                        <strong>
                            <s:text name="organizationInfo"/> "<s:property value="%{#item.name}"/>"
                        </strong>
                    </div>
                    <div id="organizationInfoBlock-<s:property value="%{#item.id}"/>" class="collapse">
                        <div class="card-block">
                                <div class="row my-1">
                                    <div class="col-sm-3">
                                        <strong><s:text name="name"/>:</strong> <s:property value="%{#item.name}"/>
                                    </div>
                                    <div class="col-sm-3">
                                        <strong><s:text name="organizationType"/>:</strong> <s:property value="%{getText(#item.type)}"/>
                                    </div>
                                    <div class="col-sm-6">
                                        <strong><s:text name="seniorOfficialFio"/>:</strong>
                                        <s:property value="%{#item.seniorOfficialSurname}"/> <s:property value="%{#item.seniorOfficialName}"/> <s:property value="%{#item.seniorOfficialPatronymic}"/>
                                    </div>
                                </div>
                                <!--/row-->
                                <div class="row">
                                    <div class="col-sm-6">
                                        <strong><s:text name="address"/>:</strong> <s:property value="%{#item.address}"/>
                                    </div>
                                    <div class="col-sm-6">
                                        <strong><s:text name="contactInfo"/>:</strong> <s:property value="%{#item.contactInfo}"/>
                                    </div>
                                </div>
                                <s:if test="%{#item.responsiblePeople.size()!=0}">
                                    <div class="card-header mt-1">
                                        <strong><s:text name="responsiblePersons"/></strong>
                                    </div>
                                    <div class="table-responsive">
                                        <table class="table table-hover table-condensed">
                                            <thead>
                                            <tr>
                                                <th><s:text name="surname"/></th>
                                                <th><s:text name="personName"/></th>
                                                <th><s:text name="patronymic"/></th>
                                                <th><s:text name="contactInfo"/></th>
                                                <th><s:text name="email"/></th>
                                                <th><s:text name="position"/></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <s:iterator value="%{#item.responsiblePeople}" var="rpItem">
                                                <tr class="table-row">
                                                    <td><s:property value="%{#rpItem.surname}"/></td>
                                                    <td><s:property value="%{#rpItem.name}"/></td>
                                                    <td><s:property value="%{#rpItem.patronymic}"/></td>
                                                    <td><s:property value="%{#rpItem.contactInfo}"/></td>
                                                    <td><s:property value="%{#rpItem.email}"/></td>
                                                    <td><s:property value="%{#rpItem.position}"/></td>
                                                </tr>
                                            </s:iterator>
                                            </tbody>
                                        </table>
                                    </div>
                                    <hr class="my-1" style="border-top: 3px solid rgba(0, 0, 0, 0.1);"/>
                                </s:if>
                                <s:else>
                                    <div class="mt-1" style="text-align: center">
                                        <strong><s:text name="responsiblePersonsIsNotAvailible"/></strong>
                                    </div>
                                </s:else>
                        </div>
                        <div class="card-footer">
                        </div>
                    </div>
                </div>
            </s:iterator>
        </shiro:hasPermission>
        <shiro:hasPermission name="issue:viewOne:helpInfo:*">
            <s:iterator value="helpInfos" var="item">
                <div class="card">
                    <div class="card-header pointer-cursor" data-toggle="collapse" data-target="#helpInfoBlock-<s:property value="%{#item.id}"/>" aria-expanded="false"
                         aria-controls="helpInfoBlock-<s:property value="%{#item.id}"/>">
                        <strong>
                            <strong style="display:inline-block; width: 49%; text-align:left"><s:text name="helpInfo"/></strong>
                            <strong style="display:inline-block; width: 50%; text-align:right"><s:text name="issueType"/> "<s:property value="%{#item.issueType.name}"/>"</strong>
                        </strong>
                    </div>
                    <div id="helpInfoBlock-<s:property value="%{#item.id}"/>" class="collapse">
                        <div class="card-block">
                            <div class="row mb-12">
                                <div class="col-sm-12">
                                    <s:property value="%{#item.text}" escapeHtml="false"/>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                        </div>
                    </div>
                </div>
            </s:iterator>
        </shiro:hasPermission>
        <shiro:hasPermission name="issue:viewOne:helpInfoByOrganization:*">
            <s:iterator value="helpInfosByOrganization" var="item">
                <div class="card">
                    <div class="card-header pointer-cursor" data-toggle="collapse" data-target="#helpInfoByOrganizationBlock-<s:property value="%{#item.id.issueTypeId}"/>-<s:property value="%{#item.id.organizationId}"/>"
                         aria-expanded="false"aria-controls="helpInfoByOrganizationBlock-<s:property value="%{#item.id.issueTypeId}"/>-<s:property value="%{#item.id.organizationId}"/>">
                        <strong style="display:inline-block; width: 29%; text-align:left"><s:text name="helpInfoByOrganization"/></strong>
                        <strong style="display:inline-block; width: 40%; text-align:center"><s:text name="organization"/> "<s:property value="%{#item.organization.name}"/>""</strong>
                        <strong style="display:inline-block; width: 30%; text-align:right"><s:text name="issueType"/> "<s:property value="%{#item.issueType.name}"/>"</strong>
                    </div>
                    <div id="helpInfoByOrganizationBlock-<s:property value="%{#item.id.issueTypeId}"/>-<s:property value="%{#item.id.organizationId}"/>" class="collapse">
                        <div class="card-block">
                            <div class="row mb-12">
                                <div class="col-sm-12">
                                    <s:property value="%{#item.text}" escapeHtml="false"/>
                                </div>
                            </div>
                        </div>
                        <div class="card-footer">
                        </div>
                    </div>
                </div>
            </s:iterator>
        </shiro:hasPermission>
    </div>
    <!--/col-->
</div>
