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