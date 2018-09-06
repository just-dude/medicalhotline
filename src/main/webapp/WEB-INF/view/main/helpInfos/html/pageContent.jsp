<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <span><strong><s:text name="helpInfosManagement"/>  (<s:property value="page.totalElements"/>)</strong></span>
                <div class="card-header mt-1">
                    <s:form action="helpinfos-page" theme="bootstrap" name="helpInfosPageParamtersForm" id="helpInfosPageParamtersForm" method="GET">
                        <div class="row">
                            <div class="col-sm-5">
                                <s:select list="%{findAllIssueCategories()}" key="issue.categories"
                                          name="issueCategoryId" id="issueCategory" multiple="false"/>
                            </div>
                            <div class="col-sm-5">
                                <s:select list="%{findAllIssueTypes()}" key="issueTypes"
                                          name="issueTypeId" id="issueType" multiple="false"/>
                            </div>
                            <div class="col-sm-2" >
                                <s:submit key="acceptButtonText" class="btn btn-primary mt-2" style="float:right;"/>
                            </div>
                        </div>
                    </s:form>
                </div>
            </div>
            <div class="card-block">
                <div class="table-responsive">
                    <table class="table table-hover table-condensed">
                        <thead>
                        <tr>
                            <th><s:text name="issue.issueCategory"/></th>
                            <th><s:text name="issueType"/></th>
                            <th width="50%"><s:text name="text"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="page.content" var="item">
                            <s:url var="helpInfoManagementUrl" namespace="/" action="helpinfo-management" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="issueTypeId" value="%{#item.id}"/>
                            </s:url>
                            <s:url var="saveUrl" namespace="/" action="save-helpinfo-input" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="issueTypeId" value="%{#item.id}"/>
                            </s:url>
                            <s:url var="removeUrl" namespace="/" action="remove-helpinfo" escapeAmp="false">
                                <s:param name="locale" value="%{#request.locale}"/>
                                <s:param name="id" value="%{#item.id}"/>
                                <s:param name="pageNumber" value="%{#parameters.pageNumber}"/>
                            </s:url>
                            <s:if test="%{#item.text==null}">
                                <tr class="table-row">
                            </s:if>
                            <s:else>
                                <tr class="table-row pointer-cursor"  onclick="location.href='<s:property value="%{#helpInfoManagementUrl}"/>';">
                            </s:else>
                                <td><s:property value="%{#item.issueType.issueCategory.name}"/></td>
                                <td><s:property value="%{#item.issueType.name}"/></td>
                                <td>
                                    <s:if test="%{#item.text==null}">
                                        <div class="my-1"><s:text name="helpInfoDataNotAvailable"/></div>
                                        <div class="control-group my-1">
                                            <div class="controls">
                                                <button class="btn btn-primary"
                                                        onclick="event.stopPropagation();location.href='${saveUrl}';">
                                                    <s:text name="addButton"/>
                                                </button>
                                            </div>
                                        </div>
                                    </s:if>
                                    <s:else>
                                        <div>
                                            <s:property value="%{@org.apache.commons.lang3.StringUtils@substring(@org.jsoup.Jsoup@parse(#item.text).text(),0,500)}" escapeHtml="false"/>
                                        </div>
                                    </s:else>
                                </td>
                                <td>
                                    <s:if test="%{#item.text!=null}">
                                        <div class="control-group">
                                            <div class="controls">
                                                <button class="btn btn-danger"
                                                        onclick="event.stopPropagation();location.href='${removeUrl}';">
                                                    <s:i18n name="controller.struts.action.common.pages.main.package">
                                                        <s:text name="button.remove"/>
                                                    </s:i18n>
                                                </button>
                                            </div>
                                        </div>
                                    </s:if>
                                </td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>

                <s:url var="pagginationBaseUrl" namespace="/" action="helpinfos-page" escapeAmp="false" includeParams="get">
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

