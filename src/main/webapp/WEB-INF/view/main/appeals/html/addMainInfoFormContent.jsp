<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>
<s:text var="continueButtonText" name="continueButtonText"/>

<div class="row">
    <div class="col-sm-12">
            <div class="card">
                <div class="card-header">
                    <span>
                        <strong><s:text name="appealTitle"/>
                            <s:if test="%{appeal.id!=null}">
                                № <s:property value="appeal.id"/>
                            </s:if>
                        </strong>
                    </span>
                    <s:if test="%{appeal.createdDateTime!=null}">
                        <span style="float: right"><strong><s:text name="appeal.createdDateTimeFormattedString"/>  <s:property value="%{@common.DateTimeUtils@toString(appeal.createdDateTime)}"/></strong></span>
                    </s:if>
                </div>
                <div class="card-block">
                    <s:form action="add-appeal-main-info" theme="bootstrap" name="addAppealMainInfoForm" id="addAppealMainInfoForm" >
                        <div class="row">
                            <div class="col-sm-6">
                                <s:textfield name="appeal.contactPhoneNumber" id="contactPhoneNumber"
                                             key="appeal.contactPhoneNumber" />
                            </div>
                            <div class="col-sm-2">
                                <s:if test="%{!isContactPhoneNumberSavedFlag}">
                                    <s:submit value="%{continueButtonText}" class="btn btn-primary" style="margin: 28px;"/>
                                </s:if>
                                <s:elseif test="%{similarAppeals.size>0}">
                                    <input type="button" class="btn btn-primary" value="<s:text name="showSimilarAppeals"/>" id="showSimilarAppeals" style="margin: 28px;">
                                </s:elseif>
                            </div>
                        </div>
                        <!--/row-->
                        <div class="form-group row">
                            <div class="col-sm-4">
                                    <s:textfield name="appeal.citizen.surname" id="citizenSurname"
                                             key="appeal.citizen.surname" disabled="%{!isContactPhoneNumberSavedFlag}"/>
                            </div>
                            <div class="col-sm-4">
                                <s:textfield name="appeal.citizen.name" id="citizenName"
                                             key="appeal.citizen.name" disabled="%{!isContactPhoneNumberSavedFlag}"/>
                            </div>
                            <div class="col-sm-4">
                                <s:textfield name="appeal.citizen.patronymic" id="citizenPatronymic"
                                             key="appeal.citizen.patronymic" disabled="%{!isContactPhoneNumberSavedFlag}"/>
                            </div>
                        </div>
                        <!--/row-->
                        <div class="form-group row">
                            <div class="col-sm-3">
                                <s:select list="%{findAllSmo()}" key="appeal.citizen.smo"
                                          name="appeal.citizen.smo.id" id="smo" required="true" disabled="%{!isContactPhoneNumberSavedFlag}"/>
                            </div>
                            <div class="col-sm-3">
                                <s:textfield name="appeal.citizen.omsPolicyNumber" id="citizenOmsPolicyNumber"
                                             key="appeal.citizen.omsPolicyNumber" disabled="%{!isContactPhoneNumberSavedFlag}"/>
                            </div>
                            <div class="col-sm-6">
                                <s:textfield name="appeal.citizen.placeOfLiving" id="citizenPlaceOfLiving"
                                             key="appeal.citizen.placeOfLiving" disabled="%{!isContactPhoneNumberSavedFlag}" />
                            </div>
                        </div>
                        <s:hidden name="locale" value="%{#request.locale}"/>
                        <s:hidden name="appeal.id" value="%{appeal.id}"/>
                        <s:hidden name="createdDateTime" value="%{createdDateTime}"/>
                        <s:if test="%{isContactPhoneNumberSavedFlag}">
                            <s:hidden name="isContactPhoneNumberSavedFlag" value="true"/>
                            <s:submit value="%{sendButtonText}" class="btn btn-primary" style="float:right"/>
                        </s:if>
                    </s:form>


                    <div class="card-header" style="margin-top:20px;">
                        <strong><s:text name="issuesTitle"/></strong>
                    </div>
                    <div class="table-responsive">
                        <table class="table table-hover table-condensed">
                            <thead>
                            <tr>
                                <th><s:text name="issueShortcutText"/></th>
                                <th><s:text name="issueState"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            </tbody>
                        </table>
                    </div>
                    <input type="button" value="<s:text name="addIssue"/>" id="add-issue" class="btn btn-primary" style="float:right; margin-right: 10px;" disabled/>

                </div>
            </div>
    </div>
    <!--/col-->
</div>

<s:if test="%{isContactPhoneNumberSavedFlag}">
    <div id="similarAppealsModal" class="modal fade" tabindex="-1" role="dialog" style="display: none">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title" style="text-align: center"><s:text name="similarAppeals"/></h4>
                </div>
                <div class="modal-body">
                    <div class="table-responsive" id="similarAppeals">
                        <table class="table table-hover table-condensed">
                            <thead>
                            <tr>
                                <th>№</th>
                                <th><s:text name="appeal.createdDateTimeFormattedString"/></th>
                                <th><s:text name="appeal.citizen.surname"/></th>
                                <th><s:text name="appeal.citizen.name"/></th>
                                <th><s:text name="appeal.citizen.patronymic"/></th>
                                <th><s:text name="appeal.state"/></th>
                                <th></th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <s:iterator value="similarAppeals" var="item">
                                <s:url var="appealManagementUrl" namespace="/" action="appeal-management" escapeAmp="false">
                                    <s:param name="locale" value="%{#request.locale}"/>
                                    <s:param name="appealId" value="%{#item.id}"/>
                                </s:url>
                                <tr class="table-row">
                                    <td><s:property value="%{#item.id}"/></td>
                                    <td><s:property value="%{@common.DateTimeUtils@toString(#item.createdDateTime)}"/></td>
                                    <td class="surname"><s:property value="%{#item.citizen.surname}"/></td>
                                    <td class="name"><s:property value="%{#item.citizen.name}"/></td>
                                    <td class="patronymic"><s:property value="%{#item.citizen.patronymic}"/></td>

                                    <td class="smoId" style="display: none"><s:property value="%{#item.citizen.smo.id}"/></td>
                                    <td class="omsPolicyNumber" style="display: none"><s:property value="%{#item.citizen.omsPolicyNumber}"/></td>
                                    <td class="placeOfLiving" style="display: none"><s:property value="%{#item.citizen.placeOfLiving}"/></td>

                                        <%--<td><s:property value="%{#item.creator.profile.fullName}"/></td>--%>
                                    <td>
                                        <s:property value="%{getText(#item.state)}"/>
                                    </td>
                                    <td>
                                        <input type="button" value="<s:text name="substitutePersonalInfo"/>" class="btn btn-primary substitutePersonalInfo" style="float:right;margin-left: 50px;"/>
                                    </td>
                                    <td>
                                        <input type="button" value="<s:text name="goTo"/>" id="go-to" class="btn btn-primary" style="float:right;margin-left: 50px;" onclick="location.href='<s:property value="#appealManagementUrl"/>'"/>
                                    </td>
                                </tr>
                            </s:iterator>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                </div>
            </div>
        </div>
    </div>
</s:if>

