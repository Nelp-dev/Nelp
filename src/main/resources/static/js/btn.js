$("#add_payment_btn").click(function () {
    $("#add_payment_dialog").style.display = "block";
});

$("#add_payment_dialog_close_btn").click(function () {
    $("#add_payment_dialog").style.display = "none";
});

$(window).click(function (event) {
    if (event.target == $("#add_payment_dialog")) {
        $("#add_payment_dialog").style.display = "none";
    }
});

$("#url_copy_btn").click(function () {
    var textArea = document.createElement("textarea");
    textArea.value = document.getElementById('meeting_url').valueOf();
    document.body.appendChild(textArea);
    textArea.select();
    var msg;
    try {
        var successful = document.execCommand('copy');
        msg = successful ? 'success' : 'fail';
    }catch (err) {
        msg = 'fail';
    }
    document.body.removeChild(textArea);
    var popup = document.getElementById("url_copy_btn_popup");
    popup.innerHTML = msg;
    popup.classList.toggle("show");
    popup.style.opacity = 0;
});