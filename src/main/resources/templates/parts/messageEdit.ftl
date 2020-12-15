<#macro editor isEditorForm>
    <a class="btn btn-info btn-primary" data-toggle="collapse" href="#collapseExample" role="button"
       aria-expanded="false"
       aria-controls="collapseExample">
        <#if isEditorForm>Post editor<#else>Post creator</#if>
    </a>
    <div class="collapse <#if message??>show</#if>" id="collapseExample">
        <div class="form-group mt-3">
            <form method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <textarea rows="3" name="text"
                              class="form-control rounded-0 ${(textError??)?string('is-invalid', '')}"
                              placeholder="Enter the message" required><#if message??>${message.text}</#if></textarea>
                    <#if textError??>
                        <div class="invalid-feedback">
                            ${textError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <input type="text" name="tag" class="form-control ${(tagError??)?string('is-invalid', '')}"
                           value="<#if message??>${message.tag}</#if>"
                           placeholder="Tag" required/>
                    <#if tagError??>
                        <div class="invalid-feedback">
                            ${tagError}
                        </div>
                    </#if>
                </div>
                <div class="form-group">
                    <div class="custom-file">
                        <input type="file" name="file" id="customFile"
                               value="<#if message??>${message.filename}</#if>"
                               style="width: 100%; height: 2.4rem;">
                        <label class="custom-file-label" for="customFile">Choose file</label>
                    </div>
                </div>
                <input type="hidden" name="_csrf" value="${_csrf.token}"/>
                <#if isEditorForm>
                    <input type="hidden" name="id" value="<#if message??>${message.id}</#if>"/>
                </#if>
                <div class="form-group">
                    <button type="submit"
                            class="btn btn-info btn-primary"><#if isEditorForm>Edit post<#else>Save post</#if></button>
                </div>
            </form>
        </div>
    </div>
</#macro>