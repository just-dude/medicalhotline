<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<div class="row">
    <div class="col-sm-6">
        <s:form action="add-issuetype" theme="bootstrap" name="addIssueTypeForm" id="addIssueTypeForm">
            <div class="card">
                <div class="card-header">
                    <strong><s:text name="addIssueType.title"/></strong>
                    <div class="mt-1">
                        <s:text name="issuesСategory"/> "<s:property value="issueCategory.name" />"
                    </div>
                </div>
                <div class="card-block">

                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:textfield name="issueType.name" id="issueTypeName"
                                         key="issueType.name"/>
                        </div>
                    </div>

                    <s:hidden name="locale" value="%{#request.locale}"/>
                    <s:hidden name="issueCategoryId" value="%{issueCategoryId}"/>
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

