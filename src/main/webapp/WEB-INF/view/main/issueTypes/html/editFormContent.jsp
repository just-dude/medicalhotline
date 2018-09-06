<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>
<div class="row">
    <div class="col-sm-6">
        <s:form action="edit-issuetype" theme="bootstrap" name="editIssueTypeForm" id="editIssueTypeForm">
            <div class="card">
                <div class="card-header">
                    <div>
                        <strong><s:text name="editIssueType.title"/></strong>
                    </div>
                    <div class="mt-1">
                        <s:text name="issuesСategory"/> "<s:property value="issueCategory.name"/>"
                    </div>
                </div>
                <div class="card-block">
                    <!--/row-->

                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:hidden name="issueType.id" id="id"/>
                        </div>
                    </div>
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:textfield name="issueType.name" id="issueTypeName"
                                         key="issueType.name" />
                        </div>
                    </div>
                    <s:hidden name="issueCategoryId"/>
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
