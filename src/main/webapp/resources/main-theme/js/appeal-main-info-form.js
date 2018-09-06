$( document ).ready(function() {
    console.log( "ready!" );
    $("#contactPhoneNumber").inputmask("+7(999)999-99-99");
    $("#showSimilarAppeals").on("click",function(){
        $("#similarAppealsModal").css("display","block");
        $("#similarAppealsModal").modal('show');
    });
    $(".substitutePersonalInfo").on("click",function(e){
        console.log(e);
        row=$(e.target).parent().parent();
        console.log(row);

        surname=row.children(".surname").first().text();
        name=row.children(".name").first().text();
        patronymic=row.children(".patronymic").first().text();
        smoId=row.children(".smoId").first().text();
        omsPolicyNumber=row.children(".omsPolicyNumber").first().text();
        placeOfLiving=row.children(".placeOfLiving").first().text();

        console.log(surname);
        console.log(name);
        console.log(patronymic);
        console.log($("#citizenSurname").val());

        if($("#citizenSurname").val().trim()===""){
            $("#citizenSurname").val(surname);
        }
        if($("#citizenName").val().trim()===""){
            $("#citizenName").val(name);
        }
        if($("#citizenPatronymic").val().trim()==="" && patronymic.trim()!==""){
            $("#citizenPatronymic").val(patronymic);
        }
        if(smoId.trim()!=="") {
            $("#smo").val(smoId);
        }
        if($("#citizenOmsPolicyNumber").val().trim()==="" && omsPolicyNumber.trim()!==""){
            $("#citizenOmsPolicyNumber").val(omsPolicyNumber);
        }
        if($("#citizenPlaceOfLiving").val().trim()==="" && placeOfLiving.trim()!==""){
            $("#citizenPlaceOfLiving").val(placeOfLiving);
        }


        $("#similarAppealsModal").modal('hide');

    });


});