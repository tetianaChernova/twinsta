<#import "parts/common.ftl" as c>
<@c.page "/static/style.css">
    <#if recommendations?has_content><h5>Recommendations for you</h5></#if>
    <#list recommendations as recommendation>
        <div class="card my-3">
            <div class="avatar">
                <#if recommendation.filename??>
                    <img src="/img/${recommendation.filename}"
                         alt="Circle Image" class="card-img-top img-raised rounded-circle img-fluid">
                <#else>
                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRWKbLa_dJgtKiBItyiLId0m6ZKSbRwtCKCgf9dsgGED2uRcZXJ&usqp=CAU"
                         alt="Circle Image" class="card-img-top img-raised rounded-circle img-fluid">
                </#if>
            </div>
            <div class="card-footer text-muted container">
                <div class="row">
                    <a class="col align-self-center">${recommendation.name}</a>
                </div>
            </div>
        </div>
    <#else>
        <h5 class="my-3">No recommendations are available for you now</h5>
    </#list>
</@c.page>