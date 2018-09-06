$.validator.addMethod( "customEmail", function( value, element ) {
	return this.optional( element ) || /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/.test(value);
}, $.validator.messages.customEmail );
