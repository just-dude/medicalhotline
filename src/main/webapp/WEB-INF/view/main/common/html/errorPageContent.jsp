<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:i18n name="controller.struts.action.common.message.package">
    <s:if test="hasActionErrors()">
    <div class="container d-table">
        <div class="d-100vh-va-middle">
            <div class="row">
                <div class="col-md-6 offset-md-5">
                    <div class="clearfix">
                        <h4 class="pt-1"><s:text name="defaultError.bodyTitle"/></h4>
                        <p><s:property value="getActionErrors().get(0)"/></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </s:if>

</s:i18n>
