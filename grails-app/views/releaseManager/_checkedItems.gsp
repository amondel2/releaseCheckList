<h3>${obj.get(0)?.releasePackage.toString()}</h3>
<div>It will take <span id="${obj.get(0)?.releasePackage.id}_time">${obj.get(0)?.releasePackage.getDuration()}</span> minutes to complete all tasks</div>
<ul sectionId="${obj.get(0)?.releasePackage.id}">
<g:each in="${obj}" var="myobj">
    <li style="list-style-type:none;">
        <input type="checkbox" <g:if test="${myobj.isComplete}">checked</g:if> <g:if test="${myobj.user && myobj.user.id != loginUser }">disabled="disabled"</g:if> name="${myobj.id}" id="${myobj.id}" />
        <label for="${myobj.id}">${myobj.releaseItem?.toString()} <g:if test="${myobj.user}"> owned by ${myobj.user.username}</g:if></label>
    </li>
</g:each>
</ul>