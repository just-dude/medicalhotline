<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<s:i18n name="controller.struts.action.common.pages.admin.package">
    <s:set  var="pageNumber" value="%{#parameters.pageNumber?#parameters.pageNumber:1}" />
    <s:set  var="totalPagesCount" value="totalPagesCount" />
    <s:if test="%{pageNumber>1}">
        <s:url var="prevPageUrl" value="%{#pagginationBaseUrl}">
            <s:param name="pageNumber" value="%{pageNumber-1}"/>
        </s:url>
    </s:if>
    <s:else>
        <s:url var="prevPageUrl" value="#"></s:url>
    </s:else>
    <s:if test="%{pageNumber<totalPagesCount}">
        <s:url var="nextPageUrl" value="%{#pagginationBaseUrl}">
            <s:param name="pageNumber" value="%{pageNumber+1}"/>
        </s:url>
    </s:if>
    <s:else>
        <s:url var="nextPageUrl" value="#"></s:url>
    </s:else>
    <s:if test="%{totalPagesCount>1 && pageNumber<=totalPagesCount}">
        <ul class="pagination">
            <li class="page-item"><s:a class="page-link" href="%{#prevPageUrl}"><s:text name="pagination.prevPageText"/></s:a></li>
            <s:if test="%{totalPagesCount>6}">
                <s:if test="%{pageNumber>0 && pageNumber<4}">                                
                    <s:set  var="endFirstNumber" value="%{pageNumber==3?4:3}" />
                    <s:set  var="firstLastNumber" value="%{pageNumber==3?totalPagesCount:totalPagesCount-1}" />
                    <s:iterator  begin="1" end="%{#endFirstNumber}" var="item">
                        <s:url var="curPageUrl" value="%{#pagginationBaseUrl}">
                            <s:param name="pageNumber" value="%{#item}"/>
                        </s:url>
                        <s:if test="%{#item==pageNumber}">
                            <li class="page-item active">
                                <s:a class="page-link" href="%{#curPageUrl}">
                                    <s:property value="top" />
                                </s:a>
                            </li>
                        </s:if>
                        <s:else>
                            <li class="page-item">
                                <s:a class="page-link" href="%{#curPageUrl}">
                                    <s:property value="top" />
                                </s:a>
                            </li>
                        </s:else>                                        
                    </s:iterator>
                    <li class="page-item"><a class="page-link" href="#">..</a></li>
                    <s:iterator  begin="%{#firstLastNumber}" end="%{#totalPagesCount}" var="item">
                        <s:url var="curPageUrl" value="%{#pagginationBaseUrl}">
                            <s:param name="pageNumber" value="%{#item}"/>
                        </s:url>
                        <li class="page-item">
                            <s:a class="page-link" href="%{#curPageUrl}">
                                <s:property value="top" />
                            </s:a>
                        </li>
                    </s:iterator>                                        
                </s:if>
                <s:elseif test="%{pageNumber>0 && pageNumber<totalPagesCount-2}">
                    <s:url var="firstPageUrl" value="%{#pagginationBaseUrl}">
                        <s:param name="pageNumber" value="1"/>
                    </s:url>
                    <s:url var="lastPageUrl" value="%{#pagginationBaseUrl}">
                        <s:param name="pageNumber" value="%{totalPagesCount}"/>
                    </s:url>
                    <li class="page-item">
                        <s:a class="page-link" href="%{#firstPageUrl}">1</s:a>
                    </li>
                    <li class="page-item"><a class="page-link" href="#">..</a></li>
                    <s:set  var="firstRangeNumber" value="%{pageNumber-1}" />
                    <s:set  var="lastRangeNumber" value="%{pageNumber+1}" />
                    <s:iterator  begin="%{#firstRangeNumber}" end="%{#lastRangeNumber}" var="item">
                        <s:url var="curPageUrl" value="%{#pagginationBaseUrl}">
                            <s:param name="pageNumber" value="%{#item}"/>
                        </s:url>
                        <s:if test="%{#item==pageNumber}">
                            <li class="page-item active">
                                <s:a class="page-link" href="%{#curPageUrl}">
                                    <s:property value="top" />
                                </s:a>
                            </li>
                        </s:if>
                        <s:else>
                            <li class="page-item">
                                <s:a class="page-link" href="%{#curPageUrl}">
                                    <s:property value="top" />
                                </s:a>
                            </li>
                        </s:else>                                        
                    </s:iterator>
                    <li class="page-item"><a class="page-link" href="#">..</a></li>
                    <li class="page-item">
                        <s:a class="page-link" href="%{#lastPageUrl}"><s:property value="%{#totalPagesCount}"/></s:a>
                    </li>
                </s:elseif>
                <s:elseif test="%{pageNumber>=totalPagesCount-2 && pageNumber<=totalPagesCount}">                                
                    <s:set  var="endFirstNumber" value="%{pageNumber==totalPagesCount-2?1:2}" />
                    <s:set  var="firstLastNumber" value="%{pageNumber==totalPagesCount-2?totalPagesCount-3:totalPagesCount-2}" />
                    <s:iterator  begin="1" end="%{#endFirstNumber}" var="item">
                        <s:url var="curPageUrl" value="%{#pagginationBaseUrl}">
                            <s:param name="pageNumber" value="%{#item}"/>
                        </s:url>
                        <li class="page-item">
                            <s:a class="page-link" href="%{#curPageUrl}">
                                <s:property value="top" />
                            </s:a>
                        </li>                                       
                    </s:iterator>
                    <li class="page-item"><a class="page-link" href="#">..</a></li>
                    <s:iterator  begin="%{#firstLastNumber}" end="%{#totalPagesCount}" var="item">
                        <s:url var="curPageUrl" value="%{#pagginationBaseUrl}">
                            <s:param name="pageNumber" value="%{#item}"/>
                        </s:url>
                        <s:if test="%{#item==pageNumber}">
                            <li class="page-item active">
                                <s:a class="page-link" href="%{#curPageUrl}">
                                    <s:property value="top" />
                                </s:a>
                            </li>
                        </s:if>
                        <s:else>
                            <li class="page-item">
                                <s:a class="page-link" href="%{#curPageUrl}">
                                    <s:property value="top" />
                                </s:a>
                            </li>
                        </s:else>    
                    </s:iterator>                                        
                </s:elseif>
            </s:if>
            <s:else>
                <s:iterator  begin="1" end="%{#totalPagesCount}" var="item">
                    <s:url var="curPageUrl" value="%{#pagginationBaseUrl}">
                        <s:param name="pageNumber" value="%{#item}"/>
                    </s:url>
                    <s:if test="%{#item==pageNumber}">
                        <li class="page-item active">
                            <s:a class="page-link" href="%{#curPageUrl}">
                                <s:property value="top" />
                            </s:a>
                        </li>
                    </s:if>
                    <s:else>
                        <li class="page-item">
                            <s:a class="page-link" href="%{#curPageUrl}">
                                <s:property value="top" />
                            </s:a>
                        </li>
                    </s:else>                                         
                </s:iterator>
            </s:else>
            <li class="page-item"><s:a class="page-link" href="%{#nextPageUrl}"><s:text name="pagination.nextPageText"/></s:a></li>
        </ul>
    </s:if>
</s:i18n> 
                       

