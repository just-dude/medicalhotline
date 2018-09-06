$( document ).ready(function() {
    console.log( "ready!" );
    $("#go-to-canceled-state-form").submit(
        function($e){
            console.log("in submit handler");
            if($("#causeOfTransitionToFinalStateWrapper").css("display")==="none"){
                $e.preventDefault();
            }
            console.log("not submitted");
            $("#causeOfTransitionToFinalStateWrapper").css("display", "inline-block");
        }
    );
});