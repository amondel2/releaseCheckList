<div class="fieldcontain${required ? ' required' : ''}">
<label for="${property}">${label}<g:if test="${required}"> <span class="required-indicator">*</span></g:if></label>
 <g:datePicker name="${property}" noSelection="['':' ']" value="${value}" precision="minute" />
</div>