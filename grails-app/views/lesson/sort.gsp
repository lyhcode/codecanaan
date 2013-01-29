<html>
<head>
<meta name="layout" content="bootstrap" />
<title>Sort</title>
</head>
<body>
<div class="container-fluid">
    <div class="row-fluid">
        <div class="span3">
            <div class="alert alert-info"><g:message code="default.ordering.description" /></div>
        </div>
        <div class="span9 clearlook-wrapper padding-around">
            <g:form controller="lesson" action="sortUpdate" id="${lesson.id}" method="post">
                <legend>${lesson.title}</legend>
                <ul id="sortable" class="nav nav-tabs nav-stacked">
                    <g:each in="${lesson.contents}" var="content" status="i">
                        <li><a href="#"><i class="icon icon-sort"></i> ${content.title}<g:hiddenField name="priority" value="${content.id}" /></a></li>
                    </g:each>
                </ul>

                <hr/>

                <g:submitButton name="save" value="${message(code: 'default.button.update.label', default: 'Update')}" class="btn btn-primary" />
                <g:link action="show" id="${lesson.id}" class="btn"><g:message code="default.button.cancel.label" default="Cancel" /></g:link>
            </g:form>
        </div>
    </div>
</div>
<r:script>
$(function() {
    $( "#sortable" ).sortable();
    $( "#sortable" ).disableSelection();
});
</r:script>
</body>
</html>
