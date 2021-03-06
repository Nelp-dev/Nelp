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

$("#update_payment_dialog").on('show.bs.modal', function (event) {
    var payment = $(event.relatedTarget);
    var payer = payment.attr("value");
    var paymentId = payment.attr("id");
    var paymentName = payment.attr("name");
    var paymentAmount = payment.attr("data");

    $("#update_payment_owner_name").val(payer);
    $("#update_payment_amount").val(paymentAmount);
    $("#update_payment_name").val(paymentName);

    var modal = $(this);
    var updateURL = $(location).attr('pathname') + '/payment/' + paymentId + '/update';
    modal.find('.modal-content').attr("action",updateURL);
});


$("#remove_payment_dialog").on('show.bs.modal', function (event) {
    var payment = $(event.relatedTarget);
    var payer = payment.attr("value");
    var paymentId = payment.attr("id");
    var paymentName = payment.attr("name");
    var paymentAmount = payment.attr("data");

    $("#remove_payment_owner_name").val(payer);
    $("#remove_payment_amount").val(paymentAmount);
    $("#remove_payment_name").val(paymentName);

    var modal = $(this);
    var updateURL = $(location).attr('pathname') + '/payment/' + paymentId + '/remove';
    modal.find('.modal-content').attr("action",updateURL);
});


