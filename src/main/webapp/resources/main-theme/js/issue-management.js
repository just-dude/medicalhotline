$( document ).ready(function() {
    console.log( "ready!" );
    $("#go-issue-to-notrelevant-state-form").submit(
        function($e){
            if($("#causeOfTransitionToNotRelevantStateWrapper").css("display")==="none"){
                $e.preventDefault();
            }
            $("#causeOfTransitionToNotRelevantStateWrapper").css("display", "inline-block");
        }
    );

    $("#go-issue-to-impossibletosolve-state-form").submit(
        function($e){
            if($("#causeOfTransitionToImpossibleToSolveWrapper").css("display")==="none"){
                $e.preventDefault();
            }
            $("#causeOfTransitionToImpossibleToSolveWrapper").css("display", "inline-block");
        }
    );
});