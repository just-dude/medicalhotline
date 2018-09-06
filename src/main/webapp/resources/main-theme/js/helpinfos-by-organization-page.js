$( document ).ready(function() {
    console.log( "ready!" );
    $('#organization').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите организацию'
    });

    $('#issueCategory').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите категорию'
    });
    $('#issueType').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите типы проблем'
    });

    $('#issueType').on('change', function (e) {
        value = $(e.target).val();
        if (value!=='All') {
            $('#issueCategory').val("All").trigger('change');
        }
    });

    $('#issueCategory').on('change', function (e) {
        value = $(e.target).val();
        if (value!=='All') {
            $('#issueType').val("All").trigger('change');
        }
    });
});