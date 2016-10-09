$(document).ready(function () {
    $("#submit").click(function () {

        $.ajax({
            dataType: "json",
            type: 'GET',
            url: "servlet/com.servlet.LoginServlet?userName=" + $("#userName").val() + "&password=" + $("#password").val(),
            success: function (data) {
                if (data.result == true) {
                    window.location.href = "/servlet/com.servlet.WelcomeServlet";
                } else {
                    alert("小伙子，用户名和密码不对，请重试");
                }
            },
        });
    });
});