<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


    <div class="row" style="margin-top: 20px">
        <div class="col-sm-3 col-centered">
            <div id="authentication-container" class="card">

                <s:if test="hasActionErrors()">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="card card-inverse card-danger">
                                <div class="card-block pb-0">
                                    <p>
                                        <s:property value="getActionErrors().get(0)"/>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </s:if>
                <s:i18n name="controller.struts.action.main.package">
                    <shiro:notAuthenticated>
                        <div id="auth-block-container" class="card-block">
                            <div id="login-form-container" class="auth-block center-block">
                                <h4 style="text-align: center;"><s:text name="offerToLogin"/></h4>
                                <s:form action="login" theme="bootstrap" name="loginForm" id="loginForm">
                                    <s:textfield name="login" id="login" key="login"  />
                                    <s:password name="password" id="password" key="password"/>
                                    <s:hidden name="locale" value="%{#request.locale}"/>
                                    <div>
                                        <s:submit value="%{getText('enter')}" class="btn btn-sm btn-primary" style="float:right;"/>
                                    </div>
                                </s:form>
                            </div>
                        </div>
                    </shiro:notAuthenticated>
                    <shiro:authenticated>
                        <div id="success-login-container" class="card-block">
                            <h4 style="text-align: center;"><s:text name="successLoginMessage"/></h4>
                            <s:url var="logoutUrl" namespace="/" action="logout">
                                <s:param name="locale" value="%{#request.locale}"/>
                            </s:url>
                            <button id="logout-button" onclick="location.href='<s:property value="%{logoutUrl}"/>'" class="btn btn-sm btn-primary center-block" style="margin: 20px auto;display: block;">
                                <s:text name="logoutButtonText"/>
                            </button>
                        </div>
                    </shiro:authenticated>
                </s:i18n>
            </div>
        </div>
    </div>