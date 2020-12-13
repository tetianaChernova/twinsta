<#import "parts/common.ftl" as c>
<@c.page "/static/style.css">
    <div class="container">
        <h3>Edit <#if user??>${user.username}'s </#if>profile</h3>
        <hr>
        <div class="row">
            <div class="col-md-3">
                <div class="text-center">
                    <#if user.filename??>
                        <img src="/img/${user.filename}"
                             id="imgSrc"
                             alt="Circle Image"
                             class="img-raised rounded-circle img-fluid"
                             style="width: 125px;height: 125px;">
                    <#else>
                        <img src="https://f0.pngfuel.com/png/980/886/male-portrait-avatar-png-clip-art.png"
                             alt="Circle Image"
                             id="imgSrcNoPhoto"
                             class="img-raised rounded-circle img-fluid"
                             style="width: 125px;height: 125px;">
                    </#if>
                    <h6>Upload a different photo...</h6>
                    <input id="imgInput"
                           type="file"
                           name="file"
                           form="editForm"
                           class="form-control">
                </div>
            </div>
            <div class="col-md-9 personal-info">
                <form method="post" id="editForm" enctype="multipart/form-data" class="form-horizontal">
                    <#if user.email??>
                        <div class="form-group">
                            <label class="col-lg-3 control-label"> Email: </label>
                            <div class="col-lg-8">
                                <input type="email"
                                       name="email"
                                       class="form-control"
                                       placeholder="unknown@unknown.com"
                                       value="${user.email}">
                            </div>
                        </div>
                    </#if>
                    <div class="form-group ">
                        <label class="col-lg-3 control-label"> Password: </label>
                        <div class="col-lg-8">
                            <input type="password"
                                   name="password"
                                   class="form-control"
                                   placeholder="Password"/>
                        </div>
                    </div>
                    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                    <div class="form-group">
                        <label class="col-md-3 control-label"></label>
                        <div class="col-md-8">
                            <input type="submit" class="btn btn-info btn-primary" value="Save Changes"/>
                            <span></span>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <style>
        .row:before, .row:after {
            display: none !important;
        }
    </style>
    <script>
        function updateTextInput(value) {
            console.log("value is: ", value);
            document.getElementById('rangeValueText').innerHTML = value;
        }


        function readURL(input) {
            if (input.files && input.files[0]) {
                let reader = new FileReader();

                reader.onload = function (e) {
                    $('#imgSrc').attr('src', e.target.result);
                    $('#imgSrcNoPhoto').attr('src', e.target.result);
                };

                reader.readAsDataURL(input.files[0]); // convert to base64 string
            }
        }

        $("#imgInput").change(function () {
            readURL(this);
        });
    </script>
</@c.page>