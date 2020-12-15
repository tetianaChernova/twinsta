<#include "parts/security.ftl">
<#import "parts/common.ftl" as c>
<@c.page "/static/profile.css">
    <div class="profile-page" style="margin-top: 10rem !important;">
        <div class="main main-raised">
            <div class="profile-content">
                <div class="container">
                    <div class="row">
                        <div class="col-md-6 ml-auto mr-auto">
                            <div class="profile">
                                <div class="avatar">
                                    <#if usr??>
                                        <#if usr.filename??>
                                            <img src="/img/${usr.filename}" alt="Circle Image"
                                                 class="img-raised rounded-circle img-fluid"
                                                 style="width: 140px;height: 140px;">
                                        <#else>
                                            <img src="https://f0.pngfuel.com/png/980/886/male-portrait-avatar-png-clip-art.png"
                                                 alt="Circle Image" class="img-raised rounded-circle img-fluid">
                                        </#if>
                                    </#if>
                                </div>
                                <div class="name">
                                    <h3 class="title">${usr.name}</h3>
                                    <p><i class="far fa-envelope"></i> ${usr.email}</p>
                                    <p><i class="far fa-grin-beam"></i>
                                        <a href="/user/subscriptions/${userChannel}/list">${subscriptionsCount} subscriptions</a>
                                    </p>
                                    <p><i class="far fa-grin-beam"></i>
                                        <a href="/user/subscribers/${userChannel}/list">${subscribersCount} subscribers</a>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>

                    <#if !isCurrentUser>
                        <#if isSubscriber>
                            <a class="btn btn-info" href="/user/unsubscribe/${userChannel}">Unsubscribe</a>
                        <#else>
                            <a class="btn btn-info" href="/user/subscribe/${userChannel}">Subscribe</a>
                        </#if>
                    </#if>
                    <#include "parts/messageList.ftl" />
                </div>
            </div>
        </div>
        <footer class="footer text-center ">
            <p>Made with Love by Twinsta Team</p>
        </footer>
    </div>
</@c.page>