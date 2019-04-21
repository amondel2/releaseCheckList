<tr>
    <td>${i+1}</td>
    <td <g:if test="${pack.isComplete}">class="strike_text"</g:if>>${pack}</td>
    <td>${pack.getDuration(true)}</td>
    <td><g:if test="${pack.completedTime}"><ps:timeDiff endTime="${pack.completedTime}" startTime="${pack.startTime}" /></g:if></td>
    <td>N/A</td>
    <td>N/A</td>
</tr>
    <g:each var="pis" in="${pack.releasePackageItems}" status="j">
        <g:render contextPath="/common" template="relItem" model="['pis':pis,'j':j,'i':i,'pack':pack]" />
    </g:each>
