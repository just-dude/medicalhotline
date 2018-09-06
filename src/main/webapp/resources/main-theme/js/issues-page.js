$( document ).ready(function() {
    console.log( "ready!" );
    isCollapsed=false;
    if(!$(".collapse").hasClass("in")){
        isCollapsed=true;
        $(".collapse").addClass("in");
    }
    $('#organizations').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите организацию'
    });
    if(isCollapsed){
        $(".collapse").removeClass("in");
    }
    $('#issuesStates').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите состояние'
    });
    $('#issueCategories').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите категорию'
    });
    $('#issueTypes').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите типы проблем'
    });
    $.datetimepicker.setLocale('ru');
    $("#startPeriodDateTime, #endPeriodDateTime").datetimepicker({
        format:'d.m.Y H:i:s'
    });
});