<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<div class="row">
    <div class="col-sm-12">
        <s:form action="add-organization" theme="bootstrap" name="addForm" id="addForm">
            <div class="card">
                <div class="card-header">
                    <strong><s:text name="addOrganization"/></strong>
                </div>
                <div class="card-block">

                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-3">
                            <s:textfield name="organization.name" id="name"
                                         key="name"/>
                        </div>
                        <div class="col-sm-3">
                            <s:textfield name="organization.seniorOfficialName" id="seniorOfficialName"
                                        key="seniorOfficialName"/>
                        </div>
                        <div class="col-sm-3">
                            <s:textfield name="organization.seniorOfficialSurname" id="seniorOfficialSurname"
                                         key="seniorOfficialSurname"/>
                        </div>
                        <div class="col-sm-3">
                            <s:textfield name="organization.seniorOfficialPatronymic" id="seniorOfficialPatronymic"
                                         key="seniorOfficialPatronymic"/>
                        </div>
                    </div>
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-3">
                            <s:textarea name="organization.address" id="address"
                                        key="address" rows="10"/>
                        </div>
                        <div class="col-sm-3">
                            <s:textarea name="organization.contactInfo" id="contactInfo"
                                        key="contactInfo" rows="10"/>
                        </div>
                        <div class="col-sm-3">
                            <s:select list="%{findAllOrganizationTypes()}" key="organizationType"
                                      name="organization.type" id="type" required="true"/>
                        </div>
                    </div>

                    <s:hidden name="locale" value="%{#request.locale}"/>
                    <!--/row-->
                </div>
                <div class="card-footer">
                    <s:submit value="%{sendButtonText}" class="btn btn-sm btn-primary" style="float:right;"/>
                </div>
            </div>
        </s:form>
    </div>
    <!--/col-->
</div>

