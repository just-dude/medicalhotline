<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<div class="row">
    <div class="col-sm-12">
        <div class="card">
            <div class="card-header">
                <span><strong><s:text name="notifications"/>  (<s:property value="page.totalElements"/>)</strong></span>
            </div>
            <div class="card-block">
                <div class="table-responsive">
                    <table class="table table-hover table-condensed">
                        <thead>
                        <tr>
                            <th><s:text name="text"/></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <s:iterator value="page.content" var="item">
                            <tr class="table-row pointer-cursor" onclick="location.href='<s:property value="%{#item.notificationContent.link}"/>'">
                                <td><s:property value="%{#item.notificationContent.text}"/></td>
                                <td>
                                    <s:if test="%{#item.hasBeenRead}">
                                        <span class="tag tag-success"><s:text name="hasBeenRead"/></span>
                                    </s:if>
                                    <s:else>
                                        <span class="tag tag-warning"><s:text name="notHasBeenRead"/></span>
                                    </s:else>
                                </td>
                            </tr>
                        </s:iterator>
                        </tbody>
                    </table>
                </div>

                <s:url var="pagginationBaseUrl" namespace="/" action="notifications-management" escapeAmp="false" includeParams="get">
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

