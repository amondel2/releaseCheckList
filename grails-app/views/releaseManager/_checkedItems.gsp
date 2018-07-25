<h3>${obj.get(0)?.releaseSection.description}</h3>
<div>It will take <span id="${obj.get(0)?.releaseSection.id}_time">${obj.get(0)?.releaseSection.getDuration()}</span> minutes to complete all tasks</div>
<ul sectionId="${obj.get(0)?.releaseSection.id}">
<g:each in="${obj}" var="myobj">
    <li style="list-style-type:none;">     <input type="checkbox" <g:if test="${myobj.endTime}">checked</g:if> name="${myobj.id}" id="${myobj.id}" />
        <label for="${myobj.id}">${myobj.name}</label>
    </li>
</g:each>
</ul>