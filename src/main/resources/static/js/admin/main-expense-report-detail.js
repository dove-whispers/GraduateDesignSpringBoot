$(function () {
    let expenseId = getQueryParam('expenseId')
    let body = $('#detail-body')
    const pre = 'data:image/jpeg;base64,'
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
        console.log(data)
        $('#expense-cause').val(data.cause)
        $('#total_amount').val(data.totalAmount)
        let html = ''
        data.expenseReportDetails.map(function (item, index) {
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
                + '<table style="table-layout:fixed;width:100%;">'
                + '<tr>'
                + setComment(item.comment)
                + setImage(item.image)
                + '</tr>'
                + '</table>'
                + '</div>'
                + '</td>'
                + '</tr>'
        })
        body.html(html)
    }

    function setComment(comment) {
        if (comment) {
            return '<td><label class="col-xs-12">备注:</label><textarea class="col-xs-12" rows="6" disabled style="resize:none">' + comment + '</textarea></td>'
        } else {
            return '<td><label class="col-xs-12">备注:</label><textarea class="col-xs-12" rows="6" placeholder="暂无备注" disabled style="resize:none"></textarea></td>'
        }
    }

    function setImage(image) {
        if (image) {
            return '<td><label class="col-xs-12">发票:</label><img class="col-xs-12 img-f" src="' + (pre + image) + '" alt="点击放大" style="cursor:pointer"/></td>'
        } else {
            return '<td><label class="col-xs-12">发票:</label><span class="col-xs-12 text-truncate">暂无发票图片</span></td>'
        }
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
    }).on('click', 'img.img-f', function () {
        const img = new Image()
        img.src = $(this).prop('src')
        const newWin = window.open('', '预览', 'top=10,left=200,width=1200,height=800')
        newWin.document.write(img.outerHTML)
        newWin.document.title = "预览图片"
        newWin.document.close()
    })
})