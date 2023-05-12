$(function () {
    let $username = $('#username')
    let $password = $('#password')
    let $captcha = $('#captcha')
    let $j_captcha = $('#j_captcha')
    let $submit = $('#submit')
    $captcha.click(function () {
        $(this).prop('src', '/getKaptchaImg?p=' + Math.ceil(Math.random() * 100))
    })
    $captcha.click()
    $('#username,#password,#j_captcha').keydown(function (e) {
        if (13 === e.keyCode) {
            $submit.click()
        }
    })
    $submit.click(function () {
        let username = $username.val()
        let password = $password.val()
        let captchaCode = $j_captcha.val()
        let isSave = $('#is_save').is(':checked')
        $.ajax({
            url: '/checkLogin',
            type: 'POST',
            async: false,
            cache: false,
            datatype: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                userName: username,
                password,
                isSave,
                verifyCodeActual: captchaCode
            }),
            success: function (data) {
                if (data.success) {
                    lightyear.notify('登陆成功,欢迎' + data.username + "~", 'success', 2000, 'mdi mdi-emoticon-tongue', 'top', 'center', "/main")
                } else {
                    lightyear.notify(data.errorMsg, 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })
    })
})