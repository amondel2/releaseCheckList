<table class="table table-bordered table-striped">
    <tr><th>Item Number</th><th>Item</th><th>Predicted Time</th><th>Actual Time</th><th>Assigned User</th><th>Completed User</th></tr>
    <g:each var="pack" in="${packages}" status="i" >
        <g:render contextPath="/common" template="relPackage" model="['i':i,'pack':pack]" />
    </g:each>
</table>