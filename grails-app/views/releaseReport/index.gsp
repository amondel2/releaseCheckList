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
<h3>
    Release <g:if test="${release.startTime}"> has started on ${release.startTime.format('MM/dd/yyyy hh:mm a')} </g:if>
    <g:elseif test="${release.plannedStartTime}"> is planned to start on ${release.plannedStartTime.format('MM/dd/yyyy hh:mm a')}</g:elseif>
    <g:else> has no planned start time</g:else>
    <g:if test="${release.endTime}">.  Release has finished on ${release.endTime.format('MM/dd/yyyy hh:mm a')} </g:if>
    </h3>
    Predicted Time: ${release.getDuration(true)} minutes.
    <g:if test="${release.endTime}">Actual Time: <ps:timeDiff endTime="${release.endTime}" startTime="${release.startTime}" /> minutes </g:if>
    <table class="table table-bordered table-striped">
    <tr><th>Item Number</th><th>Item</th><th>Predicted Time</th><th>Actual Time</th><th>Assigned User</th><th>Completed User</th></tr>

        <g:each var="pack" in="${packages}" status="i" >
            <tr>
                <td>${i+1}</td>
                <td <g:if test="${pack.isComplete}">style="text-decoration: line-through;"</g:if>>${pack}</td>
                <td>${pack.getDuration(true)}</td>
                <td><g:if test="${pack.completedTime}"><ps:timeDiff endTime="${pack.completedTime}" startTime="${pack.startTime}" /></g:if></td>
                <td>N/A</td>
                <td>N/A</td>
            </tr>
                <g:each var="pis" in="${pack.releasePackageItems}" status="j">
                   <tr>
                       <td class="center_text">${i+1}.${(char)(65 + j)}</td>
                       <td <g:if test="${pis.isComplete}">style="text-decoration: line-through;"</g:if>>${pis.releaseItem.toString()}</td>
                        <td>${pis.getDuration()}</td>
                        <td><g:if test="${pis.endTime}"><ps:timeDiff endTime="${pis.endTime}" startTime="${pis.startTime}" /></g:if></td>
                        <td>${pis.user}</td>
                        <td>${pis.completedUser}</td>
                    </tr>

                </g:each>


        </g:each>
    </table>
</body>
</html>
