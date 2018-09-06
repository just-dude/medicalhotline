<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>
<div class="row">
    <div class="col-sm-12">
        <s:form action="edit-user" theme="bootstrap" name="editUserForm" id="editUserForm">
            <div class="card">
                <div class="card-header">
                    <strong><s:text name="editUser.title"/></strong>
                </div>
                <div class="card-block">
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-4">
                            <s:textfield name="userAccount.profile.surname" id="surname"
                                         key="userAccount.profile.surname"/>
                        </div>
                        <div class="col-sm-4">
                            <s:textfield name="userAccount.profile.name" id="name"
                                         key="userAccount.profile.name"/>
                        </div>
                        <div class="col-sm-4">
                            <s:textfield name="userAccount.profile.patronymic" id="patronymic"
                                         key="userAccount.profile.patronymic"/>
                        </div>
                    </div>
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-3">
                            <s:textfield name="userAccount.profile.email" id="email"
                                         key="userAccount.profile.email"
                                         class="form-control"/>
                        </div>
                        <div class="col-sm-3">
                            <s:select list="%{findAllUserGroups()}" key="chooseUserGroupLabel"
                                      name="userAccount.userGroup" id="userGroup" required="true"/>
                        </div>
                        <script>
                            var getOrganizationsWithResponsiblePersonsList = function () {
                                return '<s:property value="%{organizationsWithResponsiblePersonsListAsJson}" escapeHtml="false"/>';
                            }
                        </script>
                        <div id="organizaationAndResponsiblePersonSelectWrapper">
                            <div class="col-sm-3">
                                <s:select list="%{findAllOrganizations()}" key="organization"
                                          name="organizationId" id="organizationId"/>
                            </div>
                            <div class="col-sm-3">
                                <s:select list="%{findAllResponsiblePersons()}" key="responsiblePerson"
                                          name="responsiblePersonId" id="responsiblePersonId"/>
                            </div>
                        </div>
                    </div>

                    <s:hidden name="userAccount.id" id="id"/>
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
