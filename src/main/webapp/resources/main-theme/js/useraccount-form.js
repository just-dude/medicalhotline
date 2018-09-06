$( document ).ready(function() {
    console.log( "ready!" );
    var organizationsWithResponsiblePersonsList=JSON.parse(getOrganizationsWithResponsiblePersonsList());
    var findOrganizationByResponsiblePersonId=function(responsiblePersonId){
        list=organizationsWithResponsiblePersonsList;
        for(i=0;i<list.length;i++){
            for(j=0;j<list[i].responsiblePeople.length;j++){
                if(list[i].responsiblePeople[j].id===responsiblePersonId){
                    return list[i];
                }
            }
        }
    }
    var getResponsiblePersonsListByOrganizationId=function(organizationId){
        list=organizationsWithResponsiblePersonsList;
        for(i=0;i<list.length;i++){
            if(list[i].id===organizationId){
                return list[i].responsiblePeople;
            }
        }
    }

    $('#organizationId').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите организацию'
    });
    $('#responsiblePersonId').select2({
        "language": {
            "noResults": function(){ return "Совпадения отсутствуют"; }
        },
        placeholder: 'Выберите ответственное лицо'
    });

    if($('#userGroup').val()==="ResponsiblePerson"){
        $("#organizaationAndResponsiblePersonSelectWrapper").show();
    } else{
        $("#organizaationAndResponsiblePersonSelectWrapper").hide();
    }

    $('#userGroup').on('change', function (e) {
        value = $(e.target).val();
        if(value==="ResponsiblePerson"){
            $("#organizaationAndResponsiblePersonSelectWrapper").show();
        } else{
            $("#organizaationAndResponsiblePersonSelectWrapper").hide();
        }
    });

    $('#responsiblePersonId').on('change', function (e) {
        values = $(e.target).val();
        if(!(values instanceof Array)){
            values = [values];
        }
        if (values.length>0 && $('#organizationId').val()==='') {
            responsiblePersonId = Number(values[0]);
            organization = findOrganizationByResponsiblePersonId(responsiblePersonId);
            $('#organizationId').val(organization.id).trigger('change');
        }
    });

    $('#organizationId').on('change', function (e) {
        organizationId = Number($(e.target).val());
        responsiblePersonsList = getResponsiblePersonsListByOrganizationId(organizationId);
        if($("#organizationId option[value='-1']").length>0){
            $("#organizationId option[value='-1']")[0].remove();
            $("#organizationId option[value='-1']").trigger('change');
        }
        prevSelectedResponsiblePersons =$('#responsiblePersonId').val();
        $("#responsiblePersonId option").each(function () {
            $(this).remove();
        });
        for (h = 0; h < responsiblePersonsList.length; h++) {
            isSelected=false;
            if(prevSelectedResponsiblePersons.indexOf(responsiblePersonsList[h].id.toString()) != -1){
                isSelected=true;
            }
            $('#responsiblePersonId').append(new Option(utils.unescapeHtml(responsiblePersonsList[h].name), responsiblePersonsList[h].id, false, isSelected));
        }
        $('#responsiblePersonId').trigger('change');
    });

    if($('#responsiblePersonId').val().length!==0){
        $('#responsiblePersonId').trigger('change');
    }

});