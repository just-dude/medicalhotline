<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<s:i18n name="controller.struts.action.common.pages.admin.package">
    <header class="navbar">
        <div class="container-fluid">
            <s:url var="adminMainPageUrl" namespace="/admin" action="index">
                <s:param name="locale" value="%{#request.locale}"/>
            </s:url>
            <s:a href="%{#adminMainPageUrl}" class="navbar-brand"></s:a>
            <ul class="nav navbar-nav hidden-md-down">
                <li class="nav-item pr-1">
                    <s:a class="nav-link" href="%{adminMainPageUrl}">
                        <h4 style="line-height: 2;color:#8ec8dc"><s:text name="systemName"/></h4>
                    </s:a>
                </li>
                <s:url var="siteIndexPageURL" namespace="" action="index">
                    <s:param name="locale" value="%{#request.locale}"/>
                </s:url>
                <li class="nav-item px-1">
                    <s:a class="nav-link" href="%{siteIndexPageURL}"><s:text name="goToWebsiteLinkText"/></s:a>
                </li>   
            </ul>
            <ul class="nav navbar-nav float-xs-right hidden-md-down">
                <!--<li class="nav-item">
                    <a class="nav-link" href="#"><i class="icon-bell"></i><span class="tag tag-pill tag-danger">5</span></a>
                </li>-->
                <shiro:authenticated>
                    <li class="nav-item dropdown" style="margin-right: 20px !important">
                        <a class="nav-link dropdown-toggle nav-link" data-toggle="dropdown" href="#" role="button" aria-haspopup="true" aria-expanded="false">
                            <span class="hidden-md-down">
                                <s:property value="%{@domain.security.SecuritySubjectUtils@getCurrentUserAccount().profile.fullName}"/>
                            </span>
                        </a>
                        <s:url var="logoutUrl" namespace="/" action="logout">
                            <s:param name="locale" value="%{#request.locale}"/>
                        </s:url>
                        <div class="dropdown-menu dropdown-menu-right" style="left: auto !important ">
                            <s:a class="dropdown-item" href="%{logoutUrl}"><i class="fa fa-user"></i><s:text name="exit"/></s:a>
                        </div>
                    </li>
                </shiro:authenticated>
                <shiro:notAuthenticated>
                    <ul class="nav navbar-nav float-xs-right hidden-md-down">
                        <s:url var="loginUrl" namespace="/" action="login-input">
                            <s:param name="locale" value="%{#request.locale}"/>
                        </s:url>
                        <li class="nav-item" style="margin-right: 20px !important">
                            <button type="button" class="btn btn-primary" onclick="location.href='${loginUrl}'">
                                <s:text name="offerToLogin"/>
                            </button>
                        </li>
                    </ul>
                </shiro:notAuthenticated>

            </ul>
        </div>
    </header>
</s:i18n>
