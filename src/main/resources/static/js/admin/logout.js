$(function () {
    $('#log-out').click(function () {
        $.ajax({
            url: '/logout',
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            success: function (data) {
                if (data.success) {
                    window.location.href = '/login'
                }
            },
            error: function (data, error) {
                alert(error)
            }
        })
    })
})