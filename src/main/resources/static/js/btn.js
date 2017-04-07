
$("#leave_meeting_btn").click(function(){
    alert("탈퇴하였습니다.");
});

$("#join_meeting_btn").click(function(){
    alert("참여하였습니다.");
});

$("#add_payment_btn").click(function () {
    $("#add_payment_dialog").style.display = "block";
});

$("#close_btn").click(function () {
    $("#add_payment_dialog").style.display = "none";
});

$(window).click(function (event) {
    if (event.target == $("#add_payment_dialog")) {
        $("#add_payment_dialog").style.display = "none";
    }
});