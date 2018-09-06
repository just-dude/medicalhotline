$( document ).ready(function() {
    console.log( "ready!" );
    var issueTypesAndCategoriesList=JSON.parse(getIssueTypesAndCategoriesList());
    var findCategoryByLabelId=function(issueTypeId){
        list=issueTypesAndCategoriesList;
        for(i=0;i<list.length;i++){
            for(j=0;j<list[i].issueTypes.length;j++){
                if(list[i].issueTypes[j].id===issueTypeId){
                    return list[i];
                }
            }
        }
    }
    var getLabelsListByCategoryId=function(categoryId){
        list=issueTypesAndCategoriesList;
        for(i=0;i<list.length;i++){
            if(list[i].id===categoryId){
                return list[i].issueTypes;
            }
        }
    }

    $('#issueTypes').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите метку'
    });
    $('#issueCategory').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите категорию'
    });
    $('#organizations').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите организацию'
    });



    $('#issueTypes').on('change', function (e) {
        values = $(e.target).val();
        if (values.length>0 && $('#issueCategory').val()==='-1') {
            issueTypeId = Number(values[0]);
            category = findCategoryByLabelId(issueTypeId);
            $('#issueCategory').val(category.id).trigger('change');
        }
    });

    $('#issueCategory').on('change', function (e) {
        categoryId = Number($(e.target).val());
        issueTypesList = getLabelsListByCategoryId(categoryId);
        if($("#issueCategory option[value='-1']").length>0){
            $("#issueCategory option[value='-1']")[0].remove();
            $("#issueCategory option[value='-1']").trigger('change');
        }
        prevSelectedIssueTypes =$('#issueTypes').val();
        $("#issueTypes option").each(function () {
            $(this).remove();
        });
        for (h = 0; h < issueTypesList.length; h++) {
            isSelected=false;
            if(prevSelectedIssueTypes.indexOf(issueTypesList[h].id.toString()) != -1){
                isSelected=true;
            }
            $('#issueTypes').append(new Option(utils.unescapeHtml(issueTypesList[h].name), issueTypesList[h].id, false, isSelected));
        }
        $('#issueTypes').trigger('change');
    });

    if($('#issueTypes').val().length!==0){
        $('#issueTypes').trigger('change');
    }

});