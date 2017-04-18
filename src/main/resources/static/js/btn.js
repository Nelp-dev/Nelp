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

$("#update_payment_dialog").on('show.bs.modal', function (event) {
    var payment = $(event.relatedTarget);
    var name = payment.attr("name");
    var amount = payment.attr("data");
    var modal = $(this);
    modal.find('.modal-body input').eq(0).val(amount);
    modal.find('.modal-body input').eq(1).val(name);
});
