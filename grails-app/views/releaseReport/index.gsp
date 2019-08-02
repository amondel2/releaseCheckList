<!doctype html>
<html>
<head>
    <meta name="layout" content="basic"/>
    <script>
        baseDir = '${request.contextPath}/${controllerName}/';
    </script>
    <title>${release.toString()} Report</title>
</head>
<body>
<h2>${release.toString()} Report</h2>
<button id="wordId" rlId="${release.id}">Export to Word (BETA)</button>
<h3>
    Release <g:if test="${release.startTime}"> has started on ${release.startTime.format('MM/dd/yyyy hh:mm a')} </g:if>
    <g:elseif test="${release.plannedStartTime}"> is planned to start on ${release.plannedStartTime.format('MM/dd/yyyy hh:mm a')}</g:elseif>
    <g:else> has no planned start time</g:else>
    <g:if test="${release.endTime}">.  Release has finished on ${release.endTime.format('MM/dd/yyyy hh:mm a')} </g:if>
    </h3>
    Predicted Time: ${release.getDuration(true)} minutes.
    <g:if test="${release.endTime}">Actual Time: <ps:timeDiff endTime="${release.endTime}" startTime="${release.startTime}" /> minutes </g:if>
    <g:render contextPath="/common" template="relReportTable" model="['packages':packages]"/>
    <g:javascript>
        $('document').ready(function () {
            $("#wordId").bind('click', function () {
                var url=baseDir + "getWord/" + $("#wordId").attr('rlId')
                window.open(url);
            });
        });

        setTimeout(function(){ window.document.location.reload(); }, 30000);

    </g:javascript>
</body>
</html>
