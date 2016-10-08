$(document).ready(function () {
    $("#submit").click(function () {

        $.ajax({
            dataType: "json",
            type: 'POST',
            url: "servlet/com.servlet.LoginServlet",
            data: {
                userName: $("#userName").val(),
                password: $("#password").val()
            },
            success: function (data) {
                if (data.result == true) {
                    window.location.href = "http://www.baidu.com";
                }
            },
        });
    });
});