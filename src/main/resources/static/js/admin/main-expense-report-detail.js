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
                + '<td><input class="form-control detail_item" type="text" disabled value="'+item.item+'"></td>'
                + '<td><input class="form-control detail_item" type="text" disabled value="'+item.time+'"></td>'
                + '<td><input class="form-control detail_item" type="text" disabled value="'+item.type+'"></td>'
                + '<td><input class="form-control detail_item" type="text" disabled value="'+item.code+'"></td>'
                + '<td><input class="form-control detail_item" type="text" disabled value="'+item.num+'"></td>'
                + '<td><input class="form-control detail_item" type="text" disabled value="'+item.amount+'"></td>'
                + '<td>'
                + '<a class="btn btn-link pre-view hide" href="javascript:void(0)">预览</a>'
                + '<small class="none-tip text-truncate">未选择任何文件</small>'
                + '</td>'
                + '</tr>'
        })
        body.html(html)
    }
})