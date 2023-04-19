$(function () {
    let loginCount = 0
    $('#submit').click(function () {
        let username = $('#username').val()
        let password = $('#password').val()
        let captchaCode = $('#j_captcha').val()
        let needVerify = false
        if (loginCount >= 3) {
            if (!captchaCode) {
                lightyear.notify('请输入验证码', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                return
            }
            needVerify = true
        }

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
                needVerify,
                verifyCodeActual: captchaCode
            }),
            success: function (data) {
                if (data.success) {
                    lightyear.notify('登陆成功,欢迎' + data.username + "~", 'success', 2000, 'mdi mdi-emoticon-tongue', 'top', 'center', "/main")
                } else {
                    lightyear.notify(data.errorMsg, 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                    loginCount++
                    if (loginCount >= 3) {
                        if (loginCount === 3) lightyear.notify('尝试次数过多,请输入验证码', 'warning', 2000, 'mdi mdi-emoticon-dead', 'top', 'center')
                        $('#verifyPart').show()
                        $('#captcha').click()
                    }
                }
            }
        })
    })
})()

function changeVerifyCode(img) {
    img.src = "/getKaptchaImg?p=" + Math.ceil(Math.random() * 100)
}