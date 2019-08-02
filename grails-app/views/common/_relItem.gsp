<tr>
    <td class="center_text">${i+1}.${(char)(65 + j)}</td>
    <td><g:if test="${pis.isComplete}"><s></g:if>${pis.releaseItem.toString()}<g:if test="${pis.isComplete}"></s></g:if>$</td>
    <td>${pis.getDuration()}</td>
    <td><g:if test="${pis.endTime}"><ps:timeDiff endTime="${pis.endTime}" startTime="${pis.startTime}" /></g:if></td>
    <td>${pis.user}</td>
    <td>${pis.completedUser}</td>
</tr>