<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:text var="sendButtonText" name="sendButtonText"/>
<div class="row">
    <div class="col-sm-12">
        <s:form action="save-helpinfo" theme="bootstrap" name="saveForm" id="saveForm">
            <div class="card">
                <div class="card-header">
                    <strong>
                        <s:if test="%{helpInfo==null}">
                            <s:text name="addHelpInfo"/>
                        </s:if>
                        <s:else>
                            <s:text name="editHelpInfo"/>
                        </s:else>
                    </strong>
                </div>
                <div class="card-block">
                    <!--/row-->
                    <div class="row mb-2">
                        <div class="col-sm-6">
                            <strong><s:text name="issue.issueCategory"/>:</strong> <s:property value="issueType.issueCategory.name"/>
                        </div>
                        <div class="col-sm-6">
                            <strong><s:text name="issueType"/>:</strong> <s:property value="issueType.name"/>
                        </div>
                    </div>
                    <!--/row-->
                    <div class="row">
                        <div class="col-sm-12">
                            <s:textarea name="helpInfo.text" id="text"
                                         key="helpInfoText"/>
                        </div>
                    </div>
                    <s:hidden name="helpInfo.id" value="%{issueTypeId}"/>
                    <s:hidden name="issueTypeId"/>
                    <s:hidden name="locale" value="%{#request.locale}"/>
                </div>
                <div class="card-footer">
                    <s:submit value="%{sendButtonText}" class="btn btn-sm btn-primary" style="float:right;"/>
                </div>
            </div>
        </s:form>
    </div>
    <!--/col-->
</div>
