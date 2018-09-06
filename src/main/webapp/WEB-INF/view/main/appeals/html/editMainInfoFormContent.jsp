<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>
<s:text var="continueButtonText" name="continueButtonText"/>


<div class="row">
    <div class="col-sm-12">
            <div class="card">
                <div class="card-header">
                    <span><strong><s:text name="appealTitle"/> № <s:property value="appeal.id"/></strong></span>
                    <span style="float: right"><strong><s:text name="appeal.createdDateTimeFormattedString"/>  <s:property value="@common.DateTimeUtils@toString(createdDateTime)"/></strong></span>
                </div>
                <div class="card-block">
                    <s:form action="edit-appeal-main-info" theme="bootstrap" name="editAppealMainInfoForm" id="editAppealMainInfoForm">
                        <div class="row">
                            <div class="col-sm-6">
                                <s:textfield name="appeal.contactPhoneNumber" id="contactPhoneNumber"
                                             key="appeal.contactPhoneNumber"  />
                            </div>
                        </div>
                        <!--/row-->
                        <div class="form-group row">
                            <div class="col-sm-4">
                                <s:textfield name="appeal.citizen.surname" id="citizenSurname"
                                             key="appeal.citizen.surname" />
                            </div>
                            <div class="col-sm-4">
                                <s:textfield name="appeal.citizen.name" id="citizenName"
                                             key="appeal.citizen.name"/>
                            </div>
                            <div class="col-sm-4">
                                <s:textfield name="appeal.citizen.patronymic" id="citizenPatronymic"
                                             key="appeal.citizen.patronymic"/>
                            </div>
                        </div>
                        <!--/row-->
                        <div class="form-group row">
                            <div class="col-sm-3">
                                <s:select list="%{findAllSmo()}" key="appeal.citizen.smo"
                                          name="appeal.citizen.smo.id" id="smo" required="true" value="%{appeal.citizen.smo.id}"/>
                            </div>
                            <div class="col-sm-3">
                                <s:textfield name="appeal.citizen.omsPolicyNumber" id="citizenOmsPolicyNumber"
                                             key="appeal.citizen.omsPolicyNumber"/>
                            </div>
                            <div class="col-sm-6">
                                <s:textfield name="appeal.citizen.placeOfLiving" id="citizenPlaceOfLiving"
                                             key="appeal.citizen.placeOfLiving"/>
                            </div>
                        </div>
                        <s:hidden name="locale" value="%{#request.locale}"/>
                        <s:hidden name="appeal.id"/>
                        <s:hidden name="createdDateTime" value="%{createdDateTime}"/>
                        <s:url var="mangementAppealUrl" namespace="/" action="appeal-management" escapeAmp="false">
                            <s:param name="locale" value="%{#request.locale}"/>
                            <s:param name="appealId" value="%{appealId}"/>
                        </s:url>
                        <s:submit value="%{sendButtonText}" class="btn btn-primary" style="float:right;margin-left: 50px;"/>
                        <input type="button" value="<s:text name="cancel"/>" id="mangementAppeal" class="btn btn-warning" style="float:right;" onclick="location.href='<s:property value="#mangementAppealUrl"/>'"/>
                    </s:form>


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
                                <tr class="table-row pointer-cursor" data-issueid="<s:property value="%{#item.id}"/>">
                                    <td><s:property value="%{#item.text}"/></td>
                                    <td>
                                        <s:if test="%{#item.state.toString()=='PrimaryProcessing'}"><s:set var="statusClass" value="'tag-danger'"/></s:if>
                                        <s:elseif test="%{#item.state.toString()=='OnTheGo'}"><s:set var="statusClass" value="'tag-warning'"/></s:elseif>
                                        <s:elseif test="%{#item.state.toString()=='Resolved'}"><s:set var="statusClass" value="'tag-success'"/></s:elseif>
                                        <s:elseif test="%{#item.state.toString()=='NotRelevant'}"><s:set var="statusClass" value="'tag-default'"/></s:elseif>
                                        <s:elseif test="%{#item.state.toString()=='ImpossibleToSolve'}"><s:set var="statusClass" value="'tag-very-danger'"/></s:elseif>
                                        <span class="tag <s:property value="#statusClass"/>">
                                            <s:property value="%{getText(#item.state)}"/>
                                            <s:if test="%{#item.causeOfTransitionToFinalState!=null}">
                                                (<s:text name="causeOfTransitionToFinalState"/>: <s:property value="%{getText(#item.causeOfTransitionToFinalState)}"/>)
                                            </s:if>
                                        </span>
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
                    <input type="button" value="<s:text name="addIssue"/>" id="add-issue" class="btn btn-primary" style="float:right; margin-right: 10px;" disabled/>
                </div>
            </div>
    </div>
    <!--/col-->
</div>

