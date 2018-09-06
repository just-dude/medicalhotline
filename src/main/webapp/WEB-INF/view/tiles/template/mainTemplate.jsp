<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<!DOCTYPE html>
<html>
<head>
    <title>
        <tiles:insertAttribute name="title"/>
    </title>
    <tiles:insertAttribute name="commonAssets"/>
    <tiles:insertAttribute name="customAssets"/>
</head>

<body class="navbar-fixed sidebar-nav fixed-nav">
<div id="header">
    <tiles:insertAttribute name="header"/>
</div>
<div id="menu">
    <tiles:insertAttribute name="menu"/>
</div>
<div id="content">
    <main class="main">
        <tiles:insertAttribute name="breadCrumbs"/>
        <div class="container-fluid">
            <div class="animated">
                <tiles:insertAttribute name="prevActionStatusMessage"/>
                <tiles:insertAttribute name="content"/>
            </div>
        </div>
    </main>
</div>
<div id="footer">
    <tiles:insertAttribute name="footer"/>
</div>
</body>
</html>

