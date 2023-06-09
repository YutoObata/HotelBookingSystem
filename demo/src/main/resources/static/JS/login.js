$(document).ready(function(){
	var formInputs = $('input[type="text"],input[type="password"]');
	formInputs.focus(function() {
        $(this).parent().children('p.formLabel').addClass('formTop');
	});

	formInputs.focusout(function() {
		if ($.trim($(this).val()).length == 0){
		$(this).parent().children('p.formLabel').removeClass('formTop');
		}
	});

	$('p.formLabel').click(function(){
        $(this).parent().children('.form-style').focus();
	});
});