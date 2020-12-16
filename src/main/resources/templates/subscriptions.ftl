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
                                    <#if users?has_content>
                                        <h3 class="title">${userChannel} 's ${type}</h3>
                                    <#else>
                                        <h3 class="title" style="margin-top: 7.5rem;">No subscriptions are
                                            available</h3>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </div>
                    <#include "parts/userCards.ftl"/>
                </div>
            </div>
        </div>
    </div>
    <footer class="footer text-center ">
        <p>Made with Love by Twinsta Team</p>
    </footer>
</@c.page>