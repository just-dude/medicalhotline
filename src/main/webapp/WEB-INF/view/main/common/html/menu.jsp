<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<s:i18n name="controller.struts.action.common.pages.main.package">
    <div class="sidebar">

        <nav class="sidebar-nav">
            <ul class="nav mt-1">
                <s:url var="appealsPageUrl" namespace="/" action="appeals-page-input" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                </s:url>
                <s:url var="issuesPageUrl" namespace="/" action="issues-page-input" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                </s:url>
                <s:url var="addAppealUrl" namespace="/" action="add-appeal-main-info-input" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                </s:url>
                <s:url var="issueCategoriesManagementUrl" namespace="/" action="issuecategories-management" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                </s:url>
                <s:url var="organizationsManagementUrl" namespace="/" action="organizations-management" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                </s:url>
                <s:url var="helpInfosPageUrl" namespace="/" action="helpinfos-page" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                </s:url>
                <s:url var="helpInfosByOrganizationPageUrl" namespace="/" action="helpinfos-by-organization-page" escapeAmp="false">
                    <s:param name="locale" value="%{#request.locale}"/>
                </s:url>
                <s:i18n name="controller.struts.action.main.package">
                    <shiro:hasPermission name="appeal:add:*:*">
                        <li class="nav-item">
                            <s:a class="nav-link"  href="%{#addAppealUrl}"><s:text name="addAppealAction"/></s:a>
                        </li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="appeal:view:*:*">
                        <li class="nav-item">
                            <s:a class="nav-link"  href="%{#appealsPageUrl}"><s:text name="appealListTitle"/></s:a>
                        </li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="issue:viewMany*:*">
                        <li class="nav-item">
                            <s:a class="nav-link"  href="%{#issuesPageUrl}"><s:text name="issueListTitle"/></s:a>
                        </li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="issue:viewMany:byorganization:?">
                        <li class="nav-item">
                            <s:a class="nav-link"  href="%{#issuesPageUrl}"><s:text name="issueListTitle"/></s:a>
                        </li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="issuecategory:view:*:*">
                        <li class="nav-item">
                            <s:a class="nav-link"  href="%{#issueCategoriesManagementUrl}"><s:text name="issueCategoriesManagementTitle"/></s:a>
                        </li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="organization:view:*:*">
                        <li class="nav-item">
                            <s:a class="nav-link"  href="%{#organizationsManagementUrl}"><s:text name="organizations"/></s:a>
                        </li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="helpinfo:view:*:*">
                        <li class="nav-item">
                            <s:a class="nav-link"  href="%{#helpInfosPageUrl}"><s:text name="helpInfos"/></s:a>
                        </li>
                    </shiro:hasPermission>
                    <shiro:hasPermission name="helpinfo-by-organization:view:*:*">
                        <li class="nav-item">
                            <s:a class="nav-link"  href="%{#helpInfosByOrganizationPageUrl}"><s:text name="helpInfosByOrganization"/></s:a>
                        </li>
                    </shiro:hasPermission>
                </s:i18n>
            </ul>
        </nav>
    </div>
</s:i18n>