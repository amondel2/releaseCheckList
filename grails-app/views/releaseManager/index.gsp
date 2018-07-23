<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="basic" />
    <title><g:message code="default.list.label" args="Home Page" /></title>
</head>
<body>
<div>
    <h2>Releases</h2>
    <g:each in="${releasePackageList}" var="item">
        <div>${item.name} will take ${item.getDuration()} minutes and be completed by ${item.getPredicatedEndTime()}</div>
    </g:each>
</div>
</body>
</html>