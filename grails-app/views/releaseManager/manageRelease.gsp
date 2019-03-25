<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="basic" />
    <title><g:message code="default.list.label" args="Home Page" /></title>
</head>
<body>
<div>
    <h2>${this.releaseName?.toString()}</h2>
    <div class="hidden" id="releaseId" relId="${params?.id}"></div>
    <div class="row">
        <div id="prevContainer" class="col-sm-4" style="border:1px solid black;">
            <legend>Prev</legend>
            <div></div>
        </div>
        <div id="currContainer" class="col-sm-4" style="border:1px solid black;">
            <legend>Current</legend>
            <div></div>
        </div>
        <div id="nextContainer" class="col-sm-4" style="border:1px solid black;">
            <legend>Next</legend>
            <div></div>
        </div>
    </div>
</div>
<asset:javascript src="releaseManage.js" />
</body>
</html>