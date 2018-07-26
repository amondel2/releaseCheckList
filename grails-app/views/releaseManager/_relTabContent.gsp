<g:each in="${releasePackageList}" var="item">
    <div>
        <g:if test="${isActive}">
            ${item.name} will take ${item.getDuration()} minutes and will be completed by ${item.getPredicatedEndTime()}
            <button rpid="${item.id}">Start </button>
        </g:if>
        <g:else>
            ${item.name} started on ${item.startTime} and was completed on ${item.completedTime}
        </g:else>
    </div>
</g:each>