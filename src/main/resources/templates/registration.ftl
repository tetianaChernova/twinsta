<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>
<@c.page>
    Add new user
    <#if errorMessage??>
        ${errorMessage}
    </#if>
    <@l.login "/registration" />
</@c.page>