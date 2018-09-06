<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>
<div class="row">
    <div class="col-sm-6">
        <s:form action="edit-responsible-person" theme="bootstrap" name="editForm" id="editForm">
            <div class="card">
                <div class="card-header">
                    <strong><s:text name="editResponsiblePerson"/></strong>
                    <div class="mt-1">
                        <s:text name="organization"/> "<s:property value="organization.name" />"
                    </div>
                </div>
                <div class="card-block">
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:textfield name="responsiblePerson.name" id="name"
                                         key="personName"/>
                        </div>
                    </div>
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:textfield name="responsiblePerson.surname" id="surname"
                                         key="surname"/>
                        </div>
                    </div>
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:textfield name="responsiblePerson.patronymic" id="patronymic"
                                         key="patronymic"/>
                        </div>
                    </div>
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:textfield name="responsiblePerson.position" id="position"
                                         key="position"/>
                        </div>
                    </div>
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:textfield name="responsiblePerson.contactInfo" id="contactInfo"
                                         key="contactInfo"/>
                        </div>
                    </div>
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:textfield name="responsiblePerson.email" id="email"
                                         key="email"/>
                        </div>
                    </div>
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:textfield name="responsiblePerson.priority" id="priority"
                                         key="priority"/>
                        </div>
                    </div>
                    <s:hidden name="organizationId" value="%{#parameters.organizationId}"/>
                    <s:hidden name="responsiblePerson.organization.id" value="%{#parameters.organizationId}"/>
                    <s:hidden name="responsiblePerson.id" id="id"/>
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
