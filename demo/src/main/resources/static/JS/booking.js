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

/* -------------------------------------
    現地払いを選択したときに暗くする
--------------------------------------*/
function handlePaymentOption() {
	var localPaymentRadio = document.getElementById("howToPay");
	var cardNumberInput = document.querySelector(".form-style-number");
	var cardExpirationMonthSelect = document.querySelectorAll(".form-item-card")[0];
	var cardExpirationYearSelect = document.querySelectorAll(".form-item-card")[1];
	var securityCodeInput = document.querySelector(".security-code");

	if (localPaymentRadio.checked) {
		// 現地払いが選択された場合、入力フィールドをグレーにして無効化する
		cardNumberInput.style.backgroundColor = "#ccc";
		cardNumberInput.disabled = true;
		cardExpirationMonthSelect.style.backgroundColor = "#ccc";
		cardExpirationMonthSelect.disabled = true;
		cardExpirationYearSelect.style.backgroundColor = "#ccc";
		cardExpirationYearSelect.disabled = true;
		securityCodeInput.style.backgroundColor = "#ccc";
		securityCodeInput.disabled = true;
	} else {
		// 現地払い以外が選択された場合、入力フィールドのスタイルと有効性をリセットする
		cardNumberInput.style.backgroundColor = "";
		cardNumberInput.disabled = false;
		cardExpirationMonthSelect.style.backgroundColor = "";
		cardExpirationMonthSelect.disabled = false;
		cardExpirationYearSelect.style.backgroundColor = "";
		cardExpirationYearSelect.disabled = false;
		securityCodeInput.style.backgroundColor = "";
		securityCodeInput.disabled = false;
	}
}
