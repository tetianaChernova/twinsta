<#import "parts/common.ftl" as c>
<@c.page "/static/profile.css">
    <div class="profile-page" style="margin-top: 10rem !important;">
        <div class="main main-raised">
            <div class="profile-content">
                <div class="container">
                    <div class="row">
                        <div class="col-md-6 ml-auto mr-auto">
                            <div class="profile">
                                <div class="name">
                                    <#if recommendations?has_content>
                                        <h3 class="title">Recommendations for you</h3>
                                    <#else>
                                        <h3 class="title" style="margin-top: 7.5rem;">No recommendations available</h3>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-deck" style="margin-top: 30px; margin-bottom: 30px;">
                        <#list recommendations as recommendation>
                            <div class="col-sm-4 mb-5">
                                <div class="card js-card h-100">
                                    <#if recommendation.filename??>
                                        <a href="/user-messages/${recommendation.name}">
                                            <img src="/img/${recommendation.filename}"
                                                 alt="Circle Image"
                                                 class="img-raised rounded-circle img-fluid"
                                                 style="width:10vw; height:10vw; margin-left: 5vw; margin-top: 1vw;"></a>
                                    <#else>
                                        <a href="/user-messages/${recommendation.name}">
                                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRWKbLa_dJgtKiBItyiLId0m6ZKSbRwtCKCgf9dsgGED2uRcZXJ&usqp=CAU"
                                                 alt="Circle Image" class="img-raised rounded-circle img-fluid"
                                                 style="width:10vw; height:10vw; margin-left: 5vw; margin-top: 1vw;"></a>
                                    </#if>
                                    <div class="card-body">
                                        <h5 class="card-title">${recommendation.name}</h5>
                                        <p class="card-text">Some quick example text to build on the card title and
                                            make up
                                            the
                                            bulk of the
                                            card's content.</p>
                                        <a class="btn btn-info"
                                           href="/user/subscribe/${recommendation.name}">Subscribe</a>
                                    </div>
                                </div>
                            </div>
                        </#list>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <footer class="footer text-center ">
        <p>Made with Love by Twinsta Team</p>
    </footer>
</@c.page>