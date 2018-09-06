<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>


<div class="row">
    <div class="col-sm-6">
        <s:form action="add-issuecategory" theme="bootstrap" name="addIssueCategoryForm" id="addIssueCategoryForm">
            <div class="card">
                <div class="card-header">
                    <strong><s:text name="addIssueCategory.title"/></strong>
                </div>
                <div class="card-block">

                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:textfield name="issueCategory.name" id="issueCategoryName"
                                         key="issueCategory.name"/>
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

