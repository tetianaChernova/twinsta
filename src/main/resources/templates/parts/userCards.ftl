<#include "security.ftl">
<div class="card-deck" style="margin-top: 30px; margin-bottom: 30px;">
    <#list users as user>
        <div class="col-sm-4 mb-5">
            <div class="card js-card h-100">
                <#if user.filename??>
                    <a href="/user-messages/${user.name}">
                        <img src="/img/${user.filename}"
                             alt="Circle Image"
                             class="img-raised rounded-circle img-fluid"
                             style="width:10vw; height:10vw; margin-left: 5vw; margin-top: 1vw;"></a>
                <#else>
                    <a href="/user-messages/${user.name}">
                        <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRWKbLa_dJgtKiBItyiLId0m6ZKSbRwtCKCgf9dsgGED2uRcZXJ&usqp=CAU"
                             alt="Circle Image" class="img-raised rounded-circle img-fluid"
                             style="width:10vw; height:10vw; margin-left: 5vw; margin-top: 1vw;"></a>
                </#if>
                <div class="card-body">
                    <h5 class="card-title">${user.name}</h5>
                    <p class="card-text">Some quick example text to build on the card title and
                        make up the bulk of the card's content.</p>
                    <#if (currentUser.getSubscriptions()?map(us -> us.name)?seq_contains(user.name)) && currentUsername != user.name>
                        <a class="btn btn-info" href="/user/unsubscribe/${user.name}">Unsubscribe</a>
                    <#elseif currentUsername != user.name>
                        <a class="btn btn-info" href="/user/subscribe/${user.name}">Subscribe</a>
                    </#if>
                </div>
            </div>
        </div>
    </#list>
</div>