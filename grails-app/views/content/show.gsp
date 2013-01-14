<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="bootstrap">
<title>${content.title}</title>
<r:require modules="exercise" />
</head>
<body>
<div class="row">
    <div class="span3">
        <!--課程單元列表-->
        <g:render template="/course/menu" />
    </div>
    <div class="span9 justfont">
        <g:if test="${authoring&&params.editor}">
            <!--切換編輯介面-->
            <g:render template="content_editor"/>
        </g:if>
        <g:else>
            <g:render template="content"/>
        </g:else>
    </div>
</div>

<!--練習模式變數設定-->
<g:hiddenField name="__ajax_save_record_url" value="${createLink(controller:'content', action:'ajaxSaveRecord',id:content?.id,absolute:true)}" />
<g:hiddenField name="__ajax_loader_image_src" value="${resource(dir: '/images', file: 'ajax-loader.gif')}" />
<g:hiddenField name="__ajax_client_port" value="${clientPort}" />

</body>
</html>
