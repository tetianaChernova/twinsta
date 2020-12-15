<div class="card-columns mt-1">
    <#list messages as message>
        <div class="card my-3">
            <#if message.filename??>
                <img src="/img/${message.filename}" class="card-img-top">
            </#if>
            <div class="m-2 card-text">
                <p style=" max-height: 130px; overflow-y: scroll; margin-bottom: -1rem">${message.text}</p><br/>
                <i>#${message.tag}</i>
            </div>
            <div class="card-footer text-muted container">
                <div class="row">
                    <a class="col align-self-center"
                       href="/user-messages/${message.author.username}">${message.authorName}</a>
                    <a class="col align-self-center" href="/messages/${message.id}/like">
                        <#if message.meLiked>
                            <i class="fas fa-heart"></i>
                        <#else>
                            <i class="far fa-heart"></i>
                        </#if>
                        ${message.likes}
                    </a>
                    <#if message.author.id == currentUserId>
                        <a class="col btn btn-info btn-primary"
                           href="/user-messages/${message.author.username}/${message.id}/edit">Edit</a>
                    </#if>
                </div>
            </div>
        </div>
    <#else>
    </#list>
</div>