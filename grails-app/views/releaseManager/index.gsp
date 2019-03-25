<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="basic" />
    <title><g:message code="default.list.label" args="Home Page" /></title>
</head>
<body>
<div>
    <h2>Releases</h2>
    <g:each in="${releaseNameList}" var="item">
        <div>${item.name} will take ${item.getDuration()} minutes and be completed by ${item.getPredicatedEndTime()}
            <button rpid="${item.id}">
            <g:if test="${item.startTime}">
                Continue
            </g:if>
            <g:else>
                Start
            </g:else>
            </button>
        </div>
    </g:each>
    <form id="mngfrm" action="${request.contextPath}/${controllerName}/manageRelease" method="post">
        <input type="hidden" id="id" name="id">
    </form>
</div>
    <script>
        $("button").on("click",function(){ $("#id").val($(this).attr('rpid')); $("#mngfrm").submit();});
    </script>
</body>
</html>