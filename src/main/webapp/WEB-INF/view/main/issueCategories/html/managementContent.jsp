<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="acceptButtonText" name="acceptButtonText"/>
<s:url var="addIssueCategoryUrl" namespace="/" action="add-issuecategory-input" escapeAmp="false">
    <s:param name="locale" value="%{#request.locale}"/>
</s:url>

<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <span><strong><s:text name="issueCategoriesManagement"/>  (<s:property value="issueCategoriesPage.totalElements"/>)</strong></span>
                <input type="button" value="<s:text name="addIssueCategoryButton"/>" id="add-issue-issueCategory" class="btn btn-primary" style="float:right;"
                       onclick="location.href='<s:property value="#addIssueCategoryUrl"/>'" />
            </div>
            <div class="card-block">
                <div class="table-responsive">
                    <table class="table table-hover table-condensed">
                        <thead>
                        <tr>
                            <th width="80%"><s:text name="issueCategory.name"/></th>
                            <th></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="issueCategoriesPage.content" var="item">
                            <s:url var="issueTypesByCategoryManagementUrl" namespace="/" action="issuetypes-management" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="issueCategoryId" value="%{#item.id}"/>
                            </s:url>
                            <s:url var="editIssueCategoryUrl" namespace="/" action="edit-issuecategory-input" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="issueCategoryId" value="%{#item.id}"/>
                            </s:url>
                            <s:url var="removeIssueCategoryUrl" namespace="/" action="remove-issuecategory" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="issueCategoryId" value="%{#item.id}"/>
                                <s:param name="pageNumber" value="%{#parameters.pageNumber}"/>
                            </s:url>
                            <tr class="table-row">
                                <td><s:property value="%{#item.name}"/></td>
                                <td>
                                    <s:if test="%{!#item.isSoftDeleted}">
                                        <div class="control-group">
                                            <div class="controls">
                                                <button class="btn btn-primary"
                                                        onclick="location.href='${issueTypesByCategoryManagementUrl}'">
                                                    <s:text name="goToIssueTypesByIssueCategory"/>
                                                </button>
                                            </div>
                                        </div>
                                    </s:if>
                                </td>
                                <s:i18n name="controller.struts.action.common.pages.main.package">
                                    <td>
                                        <s:if test="%{!#item.isSoftDeleted}">
                                            <div class="control-group">
                                                <div class="controls">
                                                    <button class="btn btn-warning"
                                                            onclick="location.href='${editIssueCategoryUrl}'">
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
                                                            onclick="location.href='${removeIssueCategoryUrl}'">
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

                <s:url var="pagginationBaseUrl" namespace="/" action="issuecategories-management" escapeAmp="false" includeParams="get">
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

