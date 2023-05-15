$(function(){
    $.ajax({
        url: '/expenseReport/getViewList',
        type: 'POST',
        async: false,
        cache: false,
        dataType: 'json',
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify({}),
        success: function (data) {
            if (data.success) {
                let len = data.data.records.length
                if (0 !== len) {
                    $('.badge').text(len)
                }
            } else {
                lightyear.notify(data.errMsg, 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
            }
        }
    })
})