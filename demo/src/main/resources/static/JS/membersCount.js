/* -------------------------------------
    大人，子供の人数の + - ボタンの動き
--------------------------------------*/
function increaseAdultCount() {
    var countElement = document.getElementById("adultCount");
    var count = parseInt(countElement.innerText);
    if (count < 9) {
        count++;
        countElement.innerText = count;
        document.getElementById("adult").value = count; // 隠しフィールドの値を更新
    }
}

function decreaseAdultCount() {
    var countElement = document.getElementById("adultCount");
    var count = parseInt(countElement.innerText);
    if (count > 0) {
        count--;
        countElement.innerText = count;
        document.getElementById("adult").value = count; // 隠しフィールドの値を更新
    }
}

function increaseChildCount() {
    var countElement = document.getElementById("childCount");
    var count = parseInt(countElement.innerText);
    if (count < 9) {
        count++;
        countElement.innerText = count;
        document.getElementById("child").value = count; // 隠しフィールドの値を更新
    }
}

function decreaseChildCount() {
    var countElement = document.getElementById("childCount");
    var count = parseInt(countElement.innerText);
    if (count > 0) {
        count--;
        countElement.innerText = count;
        document.getElementById("child").value = count; // 隠しフィールドの値を更新
    }
}

/* -------------------------------------
    宿泊人数が0,0のときのエラーメッセージ
--------------------------------------*/
function validateMemberCount() {
    var adultCount = parseInt(document.getElementById("adultCount").innerText);
    var childCount = parseInt(document.getElementById("childCount").innerText);
    var errorMessage = document.getElementById("errorMessage");

    if (adultCount === 0 && childCount === 0) {
        errorMessage.innerText = "宿泊人数を入力してください。";
        event.preventDefault(); // フォームの送信を中止
    } else {
        errorMessage.innerText = ""; // エラーメッセージをクリア
    }
}
