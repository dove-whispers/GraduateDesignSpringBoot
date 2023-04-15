$(function () {
    let expenseId = getQueryParam('expenseId')
    let body = $('#detail-body')
    if (expenseId) {
        $.ajax({
            url: '/expenseReportDetail/queryExpenseReportDetail',
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            data: {expensiveId: expenseId},
            success: function (data) {
                if (data.success) {
                    handleList(data.data)
                } else {
                    lightyear.notify('查询失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })
    }

    function handleList(data) {
        let html = ''
        data.map(function (item, index) {
            html += '<tr>'
                + '<th scope="row">' + (index + 1) + '</th>'
                + '<td><input class="form-control detail_item" type="text" disabled value="' + item.item + '"></td>'
                + '<td><input class="form-control detail_item" type="text" disabled value="' + item.time + '"></td>'
                + '<td><input class="form-control detail_item" type="text" disabled value="' + item.type + '"></td>'
                + '<td><input class="form-control detail_item" type="text" disabled value="' + item.code + '"></td>'
                + '<td><input class="form-control detail_item" type="text" disabled value="' + item.num + '"></td>'
                + '<td><input class="form-control detail_item" type="text" disabled value="' + item.amount + '"></td>'
                + '<td>'
                + '<a class="btn btn-xs btn-default d-cell" href="#collapse' + (index + 1) + '" data-toggle="collapse" title="更多" data-toggle="tooltip"><i class="mdi mdi-dots-horizontal"></i></a>'
                + '</td>'
                + '</tr>'
                + '<tr>'
                + '<td colspan="8" class="collapse" id="collapse' + (index + 1) + '">'
                + '<div class="well m-b-0">'
                + '笑谈渴饮匈奴血，壮志饥餐胡虏肉。'
                + '</div>'
                + '</td>'
                + '</tr>'
        })
        body.html(html)
    }

    body.on('click', 'a.d-cell', function () {
        let ntd = $(this).parent().parent().next().children()
        if (ntd.hasClass("d-table-cell")) {
            ntd.removeClass("d-table-cell")
        } else {
            $('.collapse').each(function (index, element) {
                $(element).removeClass("d-table-cell")
                $(element).removeClass("in")
            })
            ntd.addClass("d-table-cell")
        }
    })
})