<#import "parts/common.ftl" as c>
<@c.page>
    <h5>Recommendations for you</h5>
    <#list recommendations as recommendation>
        <div class="card my-3">
<#--            <#if message.filename??>-->
                <img src="https://www.digitals.co.za/wp-content/uploads/2018/05/instagram-followers-1.png" class="card-img-top">
<#--            </#if>-->
<#--            <div class="m-2">-->
<#--                <span>${message.text}</span><br/>-->
<#--                <i>#${message.tag}</i>-->
<#--            </div>-->
            <div class="card-footer text-muted container">
                <div class="row">
                    <a class="col align-self-center">
<#--                       href="/user-messages/${message.author.username}">-->
                        ${recommendation.name}</a>
                    <a class="col align-self-center"
<#--                       href="/messages/${message.id}/like"-->
                    >
                    </a>
<#--                    <#if message.author.id == currentUserId>-->
<#--                        <a class="col btn btn-primary"-->
<#--                           href="/user-messages/${message.author.username}?message=${message.id}">Edit</a>-->
<#--                    </#if>-->
                </div>
            </div>
        </div>
    <#else>
        <h6 class="my-3">No recommendations are available for you now</h6>
    </#list>
</@c.page>