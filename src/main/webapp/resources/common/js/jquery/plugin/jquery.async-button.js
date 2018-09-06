(function( $ ){
    
    $.fn.asyncButton = function(options) {
        
        var button = this;
        var dialog = null;
        var defaultSettinsUtils = {
            
            responseHandle : {
                
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
            
            createDialogIfNotExist: function(){
                var dialogId =button.attr('id')+"requestProcessingResultDialog"
                dialog = $("#"+dialogId);
                if(dialog.length==0){
                    requestProcessingResultDialogHTML = "<div id=\""+dialogId+"\" title=\"\"></div>";
                    button.after(requestProcessingResultDialogHTML);
                    dialog = $("#"+dialogId);
                    dialog.dialog({
                        autoOpen: false,
                        modal: true,
                        draggable: false,
                        resizable: false,
                        buttons: [{
                            text: "ОК",
                            click: function() {
                              $( this ).dialog( "close" );
                            }
                        }]                           
                    });
                    console.log("dialog created");                    
                }               
                return dialog;
            }           
            
        };
        
        
        var defaultSettings = {
            
            url: null,
            
            data: null,
            
            blockButton: function(){
                console.log("blockButton");
                button.attr("disabled", true);
            },
            
            unblockButton: function(){
                console.log("unblockButton");
                button.attr("disabled", false);
                button.css
            },

            viewStartOfSendingButton : function(){
                console.log("viewStartSendingButton");
                button.data("text",button.val(""));
                button.val("");
                button.addClass("loadingProcessButton");
            },

            viewEndOfSendingButton : function(){
                console.log("viewEndOfSendingButton");
                button.val(button.data("text"));
                button.removeClass("loadingProcessButton");                
            },   

            viewRequestErrorNotice : function(){
                console.log("viewRequestErrorNotice");
            },
                
            handleSendingError : function(){
                console.log("handleSendingError");
                message="Sending request is fail or server given't back response";
                this.viewProcessedRequestResultMessage("Error",message,function(){});
            },
            
            viewProcessedRequestResultMessage: function(status, message,afterViewProcessedRequestResultMessage){
                dialog.one( "dialogclose", function( event, ui ) { afterViewProcessedRequestResultMessage();} );
                dialog.html(message);
                dialog.dialog("open");
            },

            handleButtonProcessingResponse : function(response,afterProcessCalback){
                console.log(response);
                var afterViewProcessedRequestResultMessage = function(){};
                if(defaultSettinsUtils.responseHandle.isValidResponse(response)){                    
                    if(response.head.status==="Success"){
                        console.log("Success");
                        message=response.body.message;
                        afterViewProcessedRequestResultMessage=this.afterSuccessResponse;
                    } else{
                        message=("message" in response.body)?response.body.message:"The error have occured when request processing. Try again";
                    }
                } else {
                    console.log("NotValidJson or response");
                    message="The error have occured when request processing. Try again";
                }
                this.viewProcessedRequestResultMessage(response.head.status,message,afterViewProcessedRequestResultMessage);
            },
            
            afterSuccessResponse:function(){
                console.log("default afterSuccessResponse");
            }
            
            
        };
        

        var settings = $.extend( defaultSettings, options);   
        dialog = defaultSettinsUtils.createDialogIfNotExist();
        button.bind("click", 
            function( event ) {
                console.log("ajax is sttart");
                $.ajax({
                    type: "POST",
                    url: settings.url,
                    data: settings.data,
                    beforeSend: function(){
                        settings.blockButton();
                        settings.viewStartOfSendingButton();
                    },
                    uploadProgress: settings.viewSendingButtonProgress,
                    success: function(response){
                      settings.viewEndOfSendingButton();
                      settings.unblockButton();
                      settings.handleButtonProcessingResponse(response);
                    },
                    error: function(param){
                        console.log(param);
                        settings.viewEndOfSendingButton();
                        settings.unblockButton();
                        settings.handleSendingError();
                    }
                });
            }
        );
    };
})( jQuery );


