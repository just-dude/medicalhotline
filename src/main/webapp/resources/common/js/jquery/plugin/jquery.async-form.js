(function( $ ){
    
    $.fn.asyncForm = function(options) {
        
        var form = this;
        var dialog = null;
        var defaultSettinsUtils = {
            
            formProcessingResponseHandle : {
                
                isValidResponse : function(response){
                    try{
                        if(("head" in response) && ("status" in response.head) && ("body" in response)){
                            return true;
                        } else{
                            return false;
                        }
                    } catch(e){
                        return false;
                    }
                }
            },
            
            createDialogIfNotExist: function(afterCloseCalback){
                if(form.find("#formProcessingResultDialog").length==0){
                    formProcessingResultDialogHTML = "<div id=\"formProcessingResultDialog\" title=\"\"></div>";
                    form.append(formProcessingResultDialogHTML);
                    $("#formProcessingResultDialog").dialog({
                        autoOpen: false,
                        modal: true,
                        draggable: false,
                        resizable: false,
                        appendTo: form,
                        buttons: [{
                            text: "ОК",
                            click: function() {
                              $( this ).dialog( "close" );
                            }
                        }],
                        close: function( event, ui ) {
                            afterCloseCalback();
                        }                            
                    });
                    console.log("dialog created");                    
                }               
                return $("#formProcessingResultDialog");
            }           
            
        };
        
        
        var defaultSettings = {

            validateForm : function(){
                if(form.find("input.invalidServerSideValidation").length>0 || !this.customValidateForm()){
                    console.log("not valid");
                    return false;
                } else{
                    console.log("valid");
                    return true;
                }                
            },
            
            customValidateForm: function(){
                console.log("default custom validate form");
                return true;
            },

            prepareForm: function(){
                console.log("prepare");
            },
            
            blockForm: function(){
                console.log("blockForm");
                form.find("input[type=submit]").attr("disabled", true);
                form.find("input[type=text]").attr("disabled", true);
                form.find("textarea").attr("disabled", true);
            },
            
            unblockForm: function(){
                console.log("unblockForm");
                form.find("input[type=submit]").attr("disabled", false);
                form.find("input[type=text]").attr("disabled", false);
                form.find("textarea").attr("disabled", false);
            },

            viewStartOfSendingForm : function(){
                console.log("viewStartSendingForm");
                form.find("input[type=submit]").val("");
                form.find("input[type=submit]").addClass("loadingProcessButton");
            },

            viewEndOfSendingForm : function(){
                console.log("viewEndOfSendingForm");
                form.find("input[type=submit]").val("Отправить");
                form.find("input[type=submit]").removeClass("loadingProcessButton");                
            },   

            viewRequestErrorNotice : function(){
                console.log("viewRequestErrorNotice");
            },
            
            viewProcessedRequestResultMessage: function(status, message,afterViewProcessedRequestResultMessage){
                dialog.one( "dialogclose", function( event, ui ) { afterViewProcessedRequestResultMessage();} );
                $("#formProcessingResultDialog").html(message);
                $("#formProcessingResultDialog").dialog("open");
            },
            
            handleSendingError : function(){ // сделать default, создать функцию с таким же именем и вызывать в ней default функцию
                console.log("handleSendingError");
                message="Sending request is fail or server given't back response";
                this.viewProcessedRequestResultMessage("Error",message,function(){});                
            },

            handleFormProcessingResponse : function(response){
                console.log(response);
                var afterViewProcessedRequestResultMessage = function(){};
                if(defaultSettinsUtils.formProcessingResponseHandle.isValidResponse(response)){                    
                    if(response.head.status==="Success"){
                        console.log("Success");
                        message=("message" in response.body)?response.body.message:"The request success executed";
                        context = this;
                        afterViewProcessedRequestResultMessage=context.afterSuccessResponse;                        
                    } else if(response.head.status==="InvalidParameters"){
                        this.handleInvalidParametersResponse(response);
                        message=("message" in response.body)?response.body.message:"The error have occured when request processing. Try again";
                    } else{
                        message=("message" in response.body)?response.body.message:"The error have occured when request processing. Try again";
                    }
                } else {
                    console.log("NotValidJson or response");
                    message="The error have occured when request processing. Try again";
                }
                this.viewProcessedRequestResultMessage(response.head.status,message,afterViewProcessedRequestResultMessage);
            },
            
            handleInvalidParametersResponse: function(response){
                this.handleFieldErrors(response);
                this.handleActionErrors(response);
            },
            
                       
            handleFieldErrors : function(response){
                if(!("fieldErrors" in response.body) || response.body.fieldErrors.length == 0){
                    return;
                }
                var viewInputValidationErrors = function(input,inputName,validationErrorMessage){
                    validationErrorLableId=inputName+"-serverSideValidationError";
                    var s=validationErrorLableId.replace(/\./g,"\\.");
                    validationErrorLabel=$("#"+s);
                    if(validationErrorLabel.length===0){
                        validationErrorLabel="<label id=\""+validationErrorLableId+"\" class=\"serverSideValidationError\" for=\""+inputName+"\">"+validationErrorMessage+"</label>";
                        input.after(validationErrorLabel);
                    } else{
                        validationErrorLabel.css("display","block");
                        validationErrorLabel.text(validationErrorMessage);
                    }
                    input.addClass("serverSideInvalidFieldValue");
                    input.one("change", function(){
                        $(this).removeClass("serverSideInvalidFieldValue");
                        labelId = ($(this).attr('name')+"-serverSideValidationError").replace(/\./g,"\\.");
                        $("#"+labelId).text("");
                        $("#"+labelId).css("display","none");
                    });
                };
                var constraintViolations = response.body.fieldErrors;
                var msg="";
                for(var key in constraintViolations){
                    msg+=key+" = "+constraintViolations[key]+",<br/>";
                    inputList = form.find("input[name=\""+key+"\"]");
                    if(inputList.length===0){
                        inputList = form.find("textarea[name=\""+key+"\"]");
                    }
                    if(inputList.length!==0){
                        viewInputValidationErrors(inputList,key,constraintViolations[key]);
                    } else{
                        this.handleCustomValidationErrors(key,constraintViolations[key]);
                    }
                }
                console.log(msg);
                
            },
            
            handleActionErrors: function(response){
                if(!("actionErrors" in response.body) || response.body.actionErrors.length == 0){
                    return;
                }
                var actionErrors = $("#actionErrors");
                if(actionErrors.length==0){
                   form.find(":submit").before("<div id=\"actionErrorsContainer\"><ul id=\"actionErrors\" class=\"errors\"><ul></div>"); 
                   actionErrors = $("#actionErrors");
                }else{
                    actionErrors.html("");
                }
                var constraintViolations = response.body.actionErrors;
                var msg="";
                for(var key in constraintViolations){
                    actionErrors.append("<li>"+constraintViolations[key]+"</li>");
                }
                
            },
            
            handleCustomValidationErrors:function(validationKey,validationErrorMessage){
                console.log("default caustom validation");
            },
            
            afterSuccessResponse:function(){
                console.log("default afterSuccessResponse");
            }
            
            
        };
        

        var settings = $.extend( defaultSettings, options);   
        dialog = defaultSettinsUtils.createDialogIfNotExist(settings.unblockForm);
        this.ajaxForm({
            beforeSend: function(){
                if(!settings.validateForm()){
                    return false;
                }
                settings.blockForm();
                settings.prepareForm();
                settings.viewStartOfSendingForm();
            },
            uploadProgress: settings.viewSendingFormProgress,
            success:  function(responseText){
                settings.viewEndOfSendingForm();
                settings.unblockForm();
                settings.handleFormProcessingResponse(responseText); // выделить в отдельный объект!
            },
            error: function(param){
                console.log(param);
                settings.viewEndOfSendingForm();
                settings.unblockForm();
                settings.handleSendingError();
            },
            resetForm: false
        });
    };
})( jQuery );


