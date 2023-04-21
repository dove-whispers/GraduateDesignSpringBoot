$(function () {
    $('#submit').click(function () {
        let oldPassword = $('#old-password').val()
        let newPassword = $('#new-password').val()
        let confirmPassword = $('#confirm-password').val()
        if (confirmPassword !== newPassword) {
            $.confirm({
                title: '错误提示',
                content: '两次输入的密码不一致!',
                type: 'red',
                typeAnimated: true,
                buttons: {
                    tryAgain: {
                        text: '重试',
                        btnClass: 'btn-red',
                    }
                }
            })
            return
        }
        $.ajax({
            url: '/checkPassword',
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({oldPassword, newPassword}),
            success: function (data) {
                if (data.success) {
                    lightyear.notify('修改密码成功~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center', '/main')
                } else {
                    $.confirm({
                        title: '错误提示',
                        content: data.errMsg,
                        type: 'red',
                        typeAnimated: true,
                        buttons: {
                            tryAgain: {
                                text: '重试',
                                btnClass: 'btn-red',
                            }
                        }
                    })
                }
            }
        })
    })
})