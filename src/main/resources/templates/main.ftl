<#import "parts/common.ftl" as c>
<#import "parts/messageEdit.ftl" as e>
<#include "parts/security.ftl">
<@c.page "/static/style.css">
    <div class="form-row">
        <div class="form-group col-md-6">
            <form method="get" action="main" class="form-inline">
                <input type="text" name="filter" class="form-control" value="${filter!}" placeholder="Search by tag"/>
                <button type="submit" class="btn btn-primary ml-2">Search</button>
            </form>
        </div>
    </div>
    <@e.editor false></@e.editor>
    <#include "parts/messageList.ftl" />
</@c.page>