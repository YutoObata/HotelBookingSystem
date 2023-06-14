/* -------------------------------------
    詳細ボタンを押したとき
--------------------------------------*/
$(function() {
    $(".detail").click(function(event) {
        event.preventDefault(); // リンクのデフォルトの動作を無効化

        var imageUrl = $(this).attr('href');
        var roomDescription1 = $("#roomDescription1").text();
		var roomDescription2 = $("#roomDescription2").text();
		var roomDescription3 = $("#roomDescription3").text();
        var roomDescription4 = $("#roomDescription4").text();
		var roomDescription5 = $("#roomDescription5").text();
        var roomDescription6 = $("#roomDescription6").text();

        // モーダルウィンドウのHTMLを動的に生成し、画像と部屋の説明を表示
        var modalHtml = '<div id="modal">';
            modalHtml += '<div class="backGround"></div>';
            modalHtml += '<div class="photo">';
            modalHtml += '<img src="' + imageUrl + '" width="420" height="280">';
			modalHtml += '<h3>' + roomDescription1 + '</h3>';
			modalHtml += '<p>' + roomDescription2 + '</p>';
			modalHtml += '<p style="margin: 0px -28px; font-size: 14px;">' + roomDescription3 + '</p>';
			modalHtml += '<p style="margin: 0px -16px 10px; font-size: 12px;">' + roomDescription4 + '</p>';
			modalHtml += '<p style="margin: 0px -28px; font-size: 14px;">' + roomDescription5 + '</p>';
			modalHtml += '<p style="margin: 0px -16px; font-size: 12px;">' + roomDescription6 + '</p>';
			modalHtml += '</div>';
			modalHtml += '</div>';

        $("body").append(modalHtml); // モーダルウィンドウのHTMLを追加
		
		$("#modal").fadeIn();

        $(".backGround").click(function() {
            // モーダルウィンドウをフェードアウトして削除
            $("#modal").fadeOut(function() {
                $(this).remove();
            });
        });
    });
});



/* -------------------------------------
    入力フォームをクリックしたときに
	中の文字をずらす
--------------------------------------*/
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
    現地払いを選択したときに
	カード情報入力フォームを暗くする
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
