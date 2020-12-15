<#import "parts/common.ftl" as c>
<#import "parts/messageEdit.ftl" as e>
<#include "parts/security.ftl">
<@c.page "/static/profile.css">
    <div class="profile-page" style="margin-top: 10rem !important;">
        <div class="main main-raised">
            <div class="profile-content">
                <div class="container">
                    <div class="row" style="margin-bottom: -15px;">
                        <div class="col-md-6 ml-auto mr-auto">
                            <div class="form-group mt-3">
                                <form method="get" action="main">
                                    <div class="row">
                                        <input type="text" name="filter" class="form-control" value="${filter!}"
                                               placeholder="Search by tag"/>
                                        <button type="submit" style="visibility: hidden;"
                                                class="btn btn-info btn-primary">Search
                                        </button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                    <@e.editor false></@e.editor>
                    <#include "parts/messageList.ftl" />
                </div>
            </div>
        </div>
        <footer class="footer text-center ">
            <p>Made with Love by Twinsta Team</p>
        </footer>
    </div>
</@c.page>