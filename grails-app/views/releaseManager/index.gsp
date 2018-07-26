<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="basic" />
    <title><g:message code="default.list.label" args="Home Page" /></title>
</head>
<body>
<div>
    <h2>Releases</h2>
    <ul class="nav nav-tabs" id="myTab">
        <li class="active"><a data-toggle="tab" dtar="activeRels" isactive="1"  href="#activeRels">Active Release</a></li>
        <li><a data-toggle="tab" isactive="0"  dtar="prevRels" href="#prevRels">Prev Release</a></li>
    </ul>
    <div class="tab-content" id="myTabContent">
        <div id="activeRels" class="tab-pane fade in active">
            <h3>Active Releases</h3>
            <div>
                <g:render template="relTabContent" model="${[releasePackageList:releasePackageList,isActive:true]}"></g:render>
            </div>
        </div>
        <div id="prevRels" class="tab-pane fade">
            <h3>Completed Releases</h3>
            <div></div>
        </div>
    </div>

    <form id="mngfrm" action="/releaseManager/manageRelease" method="post">
        <input type="hidden" id="id" name="id">
    </form>
</div>
<script type="text/javascript">
    $(document).ready(function(){
        $("button").on("click",function(){ $("#id").val($(this).attr('rpid')); $("#mngfrm").submit();});
        $('a[data-toggle="tab"]').on('show.bs.tab', function(e){
            var activeTab = $(e.target);
            $.ajax({
                url:  baseDir + "/releaseManager/getReleasePackages",
                type: "post",
                dataType: 'text',
                cache: false,
                data: {isactive: $(activeTab).attr('isactive')}
            }).done(function (mydata) {
                $("#" + $(activeTab).attr('dtar') + " div").html(mydata);
            });


        });
    });
</script>
</body>
</html>