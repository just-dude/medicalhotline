<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="acceptButtonText" name="acceptButtonText"/>
<s:url var="addIssueTypeUrl" namespace="/" action="add-issuetype-input" escapeAmp="false">
    <s:param name="locale" value="%{#request.locale}"/>
    <s:param name="issueCategoryId" value="issueCategoryId"/>
    <s:param name="pageNumber" value="%{#parameters.pageNumber}"/>
</s:url>

<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <strong style="display:inline-block; width: 20%; text-align:left"><s:text name="issueTypesManagement"/> (<s:property value="issueTypesPage.totalElements"/>)</strong>
                <strong style="display:inline-block; width: 55%; text-align:center"><s:text name="issuesСategory"/> "<s:property value="issueCategory.name"/>"</strong>
                <div style="display:inline-block; width: 20%; text-align:right">
                    <input type="button" value="<s:text name="addIssueTypeButton"/>" id="add-issue-type" class="btn btn-primary"
                           onclick="location.href='<s:property value="#addIssueTypeUrl"/>'" />
                </div>
            </div>
            <div class="card-block">
                <div class="table-responsive">
                    <table class="table table-hover table-condensed">
                        <thead>
                        <tr>
                            <th width="80%"><s:text name="issueType.name"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="issueTypesPage.content" var="item">
                            <s:url var="editIssueTypeUrl" namespace="/" action="edit-issuetype-input" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="issueTypeId" value="%{#item.id}"/>
                                <s:param name="issueCategoryId" value="issueCategoryId"/>
                                <s:param name="pageNumber" value="%{#parameters.pageNumber}"/>
                            </s:url>
                            <s:url var="removeIssueTypeUrl" namespace="/" action="remove-issuetype" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="issueTypeId" value="%{#item.id}"/>
                                <s:param name="issueCategoryId" value="issueCategoryId"/>
                                <s:param name="pageNumber" value="%{#parameters.pageNumber}"/>
                            </s:url>
                            <tr class="table-row">
                                <td><s:property value="%{#item.name}"/></td>
                                <s:i18n name="controller.struts.action.common.pages.main.package">
                                    <td>
                                        <s:if test="%{!#item.isSoftDeleted}">
                                            <div class="control-group">
                                                <div class="controls">
                                                    <button class="btn btn-warning"
                                                            onclick="location.href='${editIssueTypeUrl}'">
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
                                                            onclick="location.href='${removeIssueTypeUrl}'">
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

                <s:url var="pagginationBaseUrl" namespace="/" action="issuetypes-management" escapeAmp="false" includeParams="get">
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

