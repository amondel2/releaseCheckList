<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="basic" />
        <asset:stylesheet src="main.css"/>
        <g:set var="entityName" value="${message(code: 'releaseParallelItems.label', default: 'ReleaseParallelItems')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav" role="navigation">
            <ul>
                <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
                <li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
            </ul>
        </div>
        <div id="edit-releaseParallelItems" class="content scaffold-edit" role="main">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message" role="status">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${this.releaseParallelItems}">
            <ul class="errors" role="alert">
                <g:eachError bean="${this.releaseParallelItems}" var="error">
                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                </g:eachError>
            </ul>
            </g:hasErrors>
            <g:form resource="${this.releaseParallelItems}" method="PUT">
                <g:hiddenField name="version" value="${this.releaseParallelItems?.version}" />
                <fieldset class="form">
                    <f:all bean="releaseParallelItems"/>
                </fieldset>
                <fieldset class="buttons">
                    <input class="save" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                </fieldset>
            </g:form>
        </div>
    </body>
</html>