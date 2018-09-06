$.validator.addMethod( "dateRU", function( value, element ) {
	return this.optional( element ) || utils.isValidDate(value);
}, $.validator.messages.date );

$.validator.addMethod( "minDateRU", function( value, element,param ){ 
    if(!utils.isValidDate(value)){
        return false;
    }
    var dateMas = value.split('.');
    var date = new Date(parseInt(dateMas[2], 10),parseInt(dateMas[1], 10),parseInt(dateMas[0], 10));
    return this.optional( element ) || date>=param;
}, $.validator.messages.min );

$.validator.addMethod( "maxDateRU", function( value, element,param ) {
    if(!utils.isValidDate(value)){
        return false;
    }
    var dateMas = value.split('.');
    var date = new Date(parseInt(dateMas[2], 10),parseInt(dateMas[1], 10)-1,parseInt(dateMas[0], 10));
    return this.optional( element ) || date<=param;
}, $.validator.messages.max );