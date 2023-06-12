$(document).ready(function(){
	var formInputs = $('input[type="text"],input[type="tel"],input[type="email"]');
	formInputs.focus(function() {
        $(this).parent().children('label.formLabel').addClass('formTop');
	});

	formInputs.focusout(function() {
		if ($.trim($(this).val()).length == 0){
		$(this).parent().children('label.formLabel').removeClass('formTop');
		}
	});

	$('label.formLabel').click(function(){
        $(this).parent().children('.form-style').focus();
	});
});