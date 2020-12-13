<#import "parts/common.ftl" as c>
<#include "parts/security.ftl">
<@c.page "/static/style.css">
    <h5>Hello, <#if user??>${name}<#else>guest</#if></h5>
    <div>This is main page os simple twitter&insta</div>
</@c.page>