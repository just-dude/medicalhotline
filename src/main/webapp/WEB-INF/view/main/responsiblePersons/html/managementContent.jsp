<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="acceptButtonText" name="acceptButtonText"/>
<s:url var="addUrl" namespace="/" action="add-responsible-person-input" escapeAmp="false">
    <s:param name="locale" value="%{#request.locale}"/>
    <s:param name="organizationId" value="%{organizationId}"/>
</s:url>

<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <strong style="display:inline-block; width: 25%; text-align:left"><s:text name="responsiblePersonsManagement"/>  (<s:property value="page.totalElements"/>)</strong>
                <strong style="display:inline-block; width: 55%; text-align:center"><s:text name="organization"/> "<s:property value="organization.name"/>"</strong>
                <div style="display:inline-block; width: 18%; text-align:right">
                    <input type="button" value="<s:text name="addButton"/>" id="add-responsible-person" class="btn btn-primary"
                           onclick="location.href='<s:property value="#addUrl"/>'" />
                </div>
            </div>
            <div class="card-block">
                <div class="table-responsive">
                    <table class="table table-hover table-condensed">
                        <thead>
                        <tr>
                            <th><s:text name="surname"/></th>
                            <th><s:text name="personName"/></th>
                            <th><s:text name="patronymic"/></th>
                            <th><s:text name="position"/></th>
                            <th><s:text name="contactInfo"/></th>
                            <th><s:text name="email"/></th>
                            <th><s:text name="priority"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="page.content" var="item">
                            <s:url var="editUrl" namespace="/" action="edit-responsible-person-input" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="organizationId" value="%{organizationId}"/>
                                <s:param name="responsiblePersonId" value="%{#item.id}"/>
                            </s:url>
                            <s:url var="removeUrl" namespace="/" action="remove-responsible-person" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="id" value="%{#item.id}"/>
                                <s:param name="pageNumber" value="%{#parameters.pageNumber}"/>
                                <s:param name="organizationId" value="%{organizationId}"/>
                            </s:url>
                            <tr class="table-row">
                                <td><s:property value="%{#item.surname}"/></td>
                                <td><s:property value="%{#item.name}"/></td>
                                <td><s:property value="%{#item.patronymic}"/></td>
                                <td><s:property value="%{#item.position}"/></td>
                                <td><s:property value="%{#item.contactInfo}"/></td>
                                <td><s:property value="%{#item.email}"/></td>
                                <td><s:property value="%{#item.priority}"/></td>
                                <s:i18n name="controller.struts.action.common.pages.main.package">
                                    <td>
                                        <s:if test="%{!#item.isSoftDeleted}">
                                            <div class="control-group">
                                                <div class="controls">
                                                    <button class="btn btn-warning"
                                                            onclick="location.href='${editUrl}'">
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
                                                            onclick="location.href='${removeUrl}'">
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

                <s:url var="pagginationBaseUrl" namespace="/" action="responsible-persons-management" escapeAmp="false" includeParams="get">
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

