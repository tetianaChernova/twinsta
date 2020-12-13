<#include "security.ftl">
<#macro login path isRegisterForm>
    <div class="signup-form">
        <#if isRegisterForm>
            <div class="text-center"><h5>Add new user</h5></div>
        </#if>
        <form action="${path}" method="post" enctype="multipart/form-data">
            <div class="form-group">
                <input type="text"
                       class="form-control ${(usernameError??)?string('is-invalid', '')}"
                       name="username"
                       placeholder="Username"
                       value="<#if user??>${user.username}</#if>"
                       required="required">
                <#if usernameError??>
                    <div class="invalid-feedback">
                        ${usernameError}
                    </div>
                </#if>
            </div>
            <div class="form-group">
                <input type="password"
                       name="password"
                       class="form-control ${(passwordError??)?string('is-invalid', '')}"
                       placeholder="Password"
                       required="required">
                <#if passwordError??>
                    <div class="invalid-feedback">
                        ${passwordError}
                    </div>
                </#if>
            </div>
            <#if isRegisterForm>
                <div class="form-group">
                    <input type="password"
                           class="form-control ${(passwordConfirmError??)?string('is-invalid', '')}"
                           name="passwordConfirm"
                           placeholder="Password confirmation"
                           required="required">
                    <#if passwordConfirmError??>
                        <div class="invalid-feedback">
                            ${passwordConfirmError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="email"
                           class="form-control ${(emailError??)?string('is-invalid', '')}"
                           name="email"
                           value="<#if user??>${user.email}</#if>"
                           placeholder="unknown@unknown.com"
                           required="required">
                    <#if emailError??>
                        <div class="invalid-feedback">
                            ${emailError}
                        </div>
                    </#if>
                </div>
                <div class="custom-file form-group">
                    <input type="file"
                            <#if user??><#if user.filename??>
                                class="custom-file-input is-invalid"
                            <#else>
                                class="custom-file-input"
                            </#if></#if>
                           id="imgInput"
                           name="file">
                    <label class="custom-file-label form-control-lg d-inline-block"
                           for="imgInput"
                           style="color: #969fa4;">Upload photo</label>
                    <#if user??><#if user.filename??>
                        <div class="invalid-feedback">
                            Please upload file again
                        </div>
                    </#if></#if>
                </div>
            </#if>
            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
            <div class="form-group">
                <button type="submit"
                        class="btn btn-info btn-lg btn-block"><#if isRegisterForm>Create<#else>Login</#if></button>
            </div>
            <div class="text-center"><#if isRegisterForm>Already have an account?<#else>Don`t have an account?</#if></div>
            <#if !isRegisterForm>
                <div class="text-center"><a style="margin-right: 10px;" href="/registration">Create account</a></div>
            <#else>
                <div class="text-center"><a style="margin-right: 10px;" href="/login">Login</a></div>
            </#if>
        </form>
    </div>
</#macro>

<#macro logout>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button class="btn btn-info btn-primary" type="submit"><#if user??>Sign Out<#else>Log in</#if></button>
    </form>
</#macro>