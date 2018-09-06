<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <i class="fa fa-align-justify"></i> <s:text name="usersManagement"/>

                <s:i18n name="controller.struts.action.common.pages.admin.package">
                    <s:url var="addUserUrl" namespace="/admin/users" action="add-user-input">
                        <s:param name="locale" value="%{#request.locale}"/>
                    </s:url>
                    <button class="btn btn-primary" onclick="location.href='${addUserUrl}'" style="float:right;"><s:text name="button.add"/></button>
                </s:i18n>

            </div>
            <div class="card-block">
                <div class="table-responsive">
                    <table class="table table-hover table-condensed">
                        <thead>
                        <tr>
                            <th><s:text name="userAccount.accountInfo.login"/></th>
                            <th><s:text name="userAccount.profile.surname"/></th>
                            <th><s:text name="userAccount.profile.name"/></th>
                            <th><s:text name="userAccount.profile.patronymic"/></th>
                            <th><s:text name="userAccount.profile.email"/></th>
                            <th><s:text name="userAccount.group"/></th>
                            <th><s:text name="organization"/></th>
                            <th><s:text name="responsiblePerson"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="userAccountsPage.content" var="item">
                            <s:url var="editUserUrl" namespace="/admin/users" action="edit-user-input">
                                <s:param name="userAccountId" value="%{#item.id}"/>
                                <s:param name="locale" value="%{#request.locale}"/>
                            </s:url>
                            <s:url var="removeUserUrl" namespace="/admin/users" action="remove-user">
                                <s:param name="userAccountId" value="%{#item.id}"/>
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="pageNumber" value="%{#request.pageNumber}"/>
                            </s:url>

                            <tr>
                                <td><s:property value="%{#item.accountInfo.login}"/></td>
                                <td><s:property value="%{#item.profile.surname}"/></td>
                                <td><s:property value="%{#item.profile.name}"/></td>
                                <td><s:property value="%{#item.profile.patronymic}"/></td>
                                <td><s:property value="%{#item.profile.email}"/></td>
                                <td><s:property value="%{getText(#item.userGroup)}"/></td>
                                <s:if test="%{#item.userGroup.toString()=='ResponsiblePerson'}">
                                    <td><s:property value="%{#item.organization.name}"/></td>
                                    <td><s:property value="%{#item.responsiblePerson.fullName}"/></td>
                                </s:if>
                                <s:else>
                                    <td></td>
                                    <td></td>
                                </s:else>
                                <s:i18n name="controller.struts.action.common.pages.admin.package">
                                    <td>
                                        <s:if test="%{!#item.isSoftDeleted}">
                                            <div class="control-group">
                                                <div class="controls">
                                                    <button class="btn btn-warning"
                                                            onclick="location.href='${editUserUrl}'">
                                                        <s:text name="button.edit"/>
                                                    </button>
                                                </div>
                                            </div>
                                        </s:if>
                                        <s:else>
                                            <div style="font-size:12px;">
                                                <s:text name="softDeletedRecord"/>
                                            </div>
                                        </s:else>
                                    </td>


                                    <td>
                                        <s:if test="%{!#item.isSoftDeleted}">
                                            <div class="control-group">
                                                <div class="controls">

                                                        <button class="btn btn-danger"
                                                                onclick="location.href='${removeUserUrl}'">
                                                            <s:text name="button.remove"/>
                                                        </button>

                                                </div>
                                            </div>
                                        </s:if>
                                        <s:else>
                                            <div style="margin-top: 5px;">
                                                <i class="icon-trash icons font-2xl"></i>
                                            </div>
                                        </s:else>
                                    </td>

                                </s:i18n>

                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>

                <s:url var="pagginationBaseUrl" namespace="/admin/users" action="users-management">
                    <s:param name="locale" value="%{#request.locale}"/>
                </s:url>
                <s:include value="/WEB-INF/view/common/html/paggination.jsp"/>

            </div>
        </div>
        <div>

        </div>
    </div>
    <!--/col-->
</div>

                       

