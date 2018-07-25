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
        <div>${item.name} will take ${item.getDuration()} minutes and be completed by ${item.getPredicatedEndTime()}
            <button rpid="${item.id}">Start </button>
        </div>
    </g:each>
    <form id="mngfrm" action="manageRelease" method="post">
        <input type="hidden" id="id" name="id">
    </form>
</div>
    <script>
        $("button").on("click",function(){ $("#id").val($(this).attr('rpid')); $("#mngfrm").submit();});
    </script>
</body>
</html>