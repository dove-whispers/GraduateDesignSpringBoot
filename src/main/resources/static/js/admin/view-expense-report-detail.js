$(function () {
    let expenseId = getQueryParam('expenseId')
    let body = $('#detail-body')
    const pre = 'data:image/jpeg;base64,'
    let result_div = $('#result')
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
                    queryLatestDealRecord()
                } else {
                    lightyear.notify('报销单细节查询失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })

        function queryLatestDealRecord() {
            $.ajax({
                url: '/dealRecord/queryLatestDealRecord',
                type: 'POST',
                async: false,
                cache: false,
                dataType: 'json',
                data: {expensiveId: expenseId},
                success: function (data) {
                    if (data.success) {
                        handleDeal(data.data)
                        if ('打回' === data.data.dealResult) {
                            $(':disabled').removeAttr("disabled");
                            $('#total_amount').attr('disabled', 'disabled')
                            $('#deal_comment').attr('disabled', 'disabled')
                            dealResult(true)
                        }
                        dealResult(false)
                    } else {
                        lightyear.notify('报销报销单最新操作失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                    }
                }
            })
        }
    }

    function dealResult(result) {
        let html = ''
        if (result) {
            html += '<label class="col-lg-12 m-b-15">处理结果:</label>'
                + '<button id="edit" class="btn btn-label btn-primary m-r-15"><label><i class="mdi mdi-checkbox-marked-circle-outline"></i></label>提交修改</button>'
        } else {
            html += '<label class="col-lg-12 m-b-15">处理结果:</label>'
                + '<button id="primary" class="btn btn-label btn-primary m-r-15" data-name="通过"><label><i class="mdi mdi-checkbox-marked-circle-outline"></i></label>确认通过</button>'
                + '<button id="warning" class="btn btn-label btn-warning m-r-15" data-name="打回"><label><i class="mdi mdi-arrow-left-bold-circle-outline"></i></label>打回修改</button>'
                + '<button id="danger" class="btn btn-label btn-danger" data-name="终止"><label><i class="mdi mdi-close-circle-outline"></i></label>终&nbsp;&nbsp;止</button>'
        }
        result_div.html(html)
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

    result_div.on('click', '#primary,#warning,#danger', function () {
        let name = $(this).data('name')
        let comment = $('#need_comment').val()
        $.confirm({
            title: '确认框',
            content: '确认' + name + '吗?',
            buttons: {
                confirm: {
                    text: '确认',
                    btnClass: 'btn-blue',
                    action: function () {
                        $.ajax({
                            url: '/dealRecord/addNewDeal',
                            type: 'POST',
                            async: false,
                            cache: false,
                            dataType: 'json',
                            contentType: 'application/json;charset=utf-8',
                            data: JSON.stringify({way: name, comment, expenseId}),
                            success: function (data) {
                                if (data.success) {
                                    lightyear.notify('报销单处理成功~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center', '/main')
                                } else {
                                    lightyear.notify('报销单处理失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                                }
                            }
                        })
                    }
                },
                cancel: {
                    text: '取消',
                }
            }
        })
    }).on('click', '#edit', function () {
        console.log('用户修改')
    })

    function handleDeal(record) {
        let record_comment = $('#record-comment')
        let table = $('<table class="col-xs-12 m-l-15 m-t-10"></table>')
        let trh = $('<tr><th>处理人</th><th>处理方式</th><th>处理结果</th><th style="padding-left: 25px;">处理时间</th><th>备注</th></tr>')
        let tr = $('<tr></tr>')
        let td_comment = $('<td><textarea id="deal_comment" class="col-xs-10" rows="4" style="resize:none" disabled placeholder="暂无备注">' + (record.comment ? record.comment : '') + '</textarea></td>')
        let td_em = $('<td>' + record.employee.name + '</td>')
        let td_way = $('<td>' + record.dealWay + '</td>')
        let td_result = $('<td>' + record.dealResult + '</td>')
        let td_time = $('<td>' + record.dealTime + '</td>')
        table.append(trh).append(tr.append(td_em).append(td_way).append(td_result).append(td_time).append(td_comment))
        record_comment.append(table)
    }

    function handleList(data) {
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

    function createStepDot(status, text) {
        return $('<li class="nav-step-item ' + status + '"><span>' + text + '</span><a href="javascript:void(0)"></a></li>')
    }

    function createStepAnchor(status, text1, text2) {
        return $('<li class="nav-step-item ' + status + '"><a href="javascript:void(0)"><h6>' + text1 + '</h6><p class="m-0">' + text2 + '</p></a></li>')
    }

    function createTip(type, role) {
        if ('warning' === type) {
            return $('<div class="alert alert-warning" role="alert">等待用户修改。</div>')
        } else if ('danger' === type) {
            return $('<div class="alert alert-danger" role="alert">已被' + role + '终止,该报销单已结束。</div>')
        } else {
            return $('<div class="alert alert-success" role="alert">已成功报销,该报销单已结束。</div>')
        }
    }
})