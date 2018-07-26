<h3>${obj.get(0)?.releaseSection.description}</h3>
<div>It will take <span id="${obj.get(0)?.releaseSection.id}_time">${obj.get(0)?.releaseSection.getDuration()}</span> minutes to complete all tasks</div>
<ul sectionId="${obj.get(0)?.releaseSection.id}">
<g:each in="${obj}" var="myobj">
    <li style="list-style-type:none;">
        <input type="checkbox" <g:if test="${myobj.endTime}">checked</g:if> <g:if test="${myobj?.owenedUser && myobj?.owenedUserId != currUserId }">disabled="disabled" readonly="readonly"</g:if> name="${myobj.id}" id="${myobj.id}" />
        <label for="${myobj.id}">${myobj.name}<g:if test="${myobj?.owenedUser}">Owned By ${myobj?.owenedUser}</g:if></label><g:if test="${myobj?.owenedUser && myobj?.owenedUserId != currUserId }"><span class="error">* Only Owned User can check this item</span></g:if>
    </li>
</g:each>
</ul>