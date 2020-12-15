<#import "parts/common.ftl" as c>
<@c.page "/static/style.css">
    <h3>${userChannel} 's ${type}</h3>
    <ul class="list-group">
        <#list users as user>
            <li class="list-group-item">
                <a href="/user-messages/${user.getName()}">${user.getName()}</a>
            </li>
        </#list>
    </ul>
</@c.page>