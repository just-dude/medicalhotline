$( document ).ready(function() {
    console.log( "ready!" );
    isCollapsed=false;
    if(!$(".collapse").hasClass("in")){
        isCollapsed=true;
        $(".collapse").addClass("in");
    }
    $('#issueCategories').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите категории'
    });
    if(isCollapsed){
        $(".collapse").removeClass("in");
    }
    $('#appealsStates').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите состояния'
    });

    $("#phoneNumber").inputmask("+7(999)999-99-99");
    $.datetimepicker.setLocale('ru');
    $("#startPeriodDateTime, #endPeriodDateTime").datetimepicker({
        format:'d.m.Y H:i:s'
    });
});