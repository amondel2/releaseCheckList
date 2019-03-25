<!doctype html>
<html>
<head>
    <meta name="layout" content="basic"/>
    <script>
        baseDir = '${request.contextPath}/${controllerName}/';
    </script>
    <title>Welcome to RM</title>
</head>
<body>
<sec:ifNotLoggedIn>
    Please <g:link controller='login' action='auth'>Login</g:link>
</sec:ifNotLoggedIn>
<sec:ifLoggedIn>
    <h2>Current Releases</h2>
    <div id="InProg"></div>
    <ul>
    <g:each var="rel" in="${inprog}">
        <li><a href="${request.contextPath}/releaseReport/index/${rel.id}">${rel.toString()}</a> started at ${rel.getPredicatedEndTime().format('MM/dd/yyyy hh:mm a')} </li>
    </g:each>
    </ul>
    <h2>UpComming Releases</h2>
    <div id="NotStarted"></div>
    <ul>
        <g:each var="rel" in="${uprelease}">
            <li><a href="${request.contextPath}/releaseReport/index/${rel.id}">${rel.toString()}</a> <g:if test="${rel.plannedStartTime}">plans to start at ${rel.plannedStartTime.format('MM/dd/yyyy hh:mm a')}.</g:if>  This release is planned to finish at ${rel.getPredicatedEndTime().format('MM/dd/yyyy hh:mm a')}  </li>
        </g:each>
    </ul>

    <h2>Completed Releases</h2>
    <div id="completed"></div>
    <ul>
        <g:each var="rel" in="${completerelease}">
            <li><a href="${request.contextPath}/releaseReport/index/${rel.id}">${rel.toString()}</a> finish at ${rel.endTime.format('MM/dd/yyyy hh:mm a')}  </li>
        </g:each>
    </ul>

</sec:ifLoggedIn>

</body>
</html>
