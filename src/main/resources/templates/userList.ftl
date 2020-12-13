<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>
<@c.page "/static/style.css">
    List of users
    <table>
        <thead>
        <tr>
            <th>Name</th>
            <th>Role</th>
            <th>Name</th>
        </tr>
        </thead>
        <tbody>
        <#list users as user>
            <#if !(user.username == "admin")>
                <tr>
                    <td>${user.username}</td>
                    <td><#list user.roles as role>${role}<#sep>, </#list></td>
                    <td><a href="/user/${user.id}">edit</a></td>
                </tr>
            </#if>
        </#list>
        </tbody>
    </table>
</@c.page>

