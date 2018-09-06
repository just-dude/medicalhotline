<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:i18n name="controller.struts.action.common.message.package">
    <s:if test="(hasActionMessages() || hasActionErrors())">
        <div class="row">
            <div class="col-sm-12">
                <s:if test="hasActionMessages()">
                    <div class="card card-inverse card-primary">
                        <div class="card-block pb-0">
                            <p>
                                <s:property value="getActionMessages().get(0)"/>
                            </p>
                        </div>
                    </div>
                </s:if>
                <s:if test="hasActionErrors()">
                    <div class="card card-inverse card-danger">
                        <div class="card-block pb-0">
                            <p>
                                <s:property value="getActionErrors().get(0)"/>
                            </p>
                        </div>
                    </div>
                </s:if>
            </div>
        </div>
    </s:if>

</s:i18n>
