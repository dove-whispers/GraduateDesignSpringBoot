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
                    lightyear.notify('报销单细节查询失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })
        $.ajax({
            url: '/dealRecord/queryExpenseReportStep',
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            data: {expensiveId: expenseId},
            success: function (data) {
                if (data.success) {
                    handleProcedure(data.step)
                } else {
                    lightyear.notify('报销进度查询失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })
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
                } else {
                    lightyear.notify('报销报销单最新操作失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })
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

    function handleDeal(record) {
        if('已创建'===record.dealResult) return
        let record_comment = $('#record-comment')
        let table  = $('<table class="col-xs-12 m-l-15 m-t-10"></table>')
        let trh = $('<tr><th>处理人</th><th>处理方式</th><th>处理结果</th><th style="padding-left: 25px;">处理时间</th><th>备注</th></tr>')
        let tr = $('<tr></tr>')
        let td_comment = $('<td><textarea id="deal_comment" class="col-xs-10" rows="4" style="resize:none" disabled placeholder="暂无备注">'+(record.comment?record.comment:'')+'</textarea></td>')
        let td_em = $('<td>'+record.employee.name+'</td>')
        let td_way = $('<td>'+record.dealWay+'</td>')
        let td_result = $('<td>'+record.dealResult+'</td>')
        let td_time = $('<td>'+record.dealTime+'</td>')
        table.append(trh).append(tr.append(td_em).append(td_way).append(td_result).append(td_time).append(td_comment))
        record_comment.append(table)
    }

    function handleProcedure(step) {
        if (0===step) return
        let step_dots = $('<ul class="nav-step step-dots"></ul>')
        let step_anchor = $('<ul class="nav-step step-anchor m-l-15">')
        let dot1 = ''
        let dot2 = ''
        let dot3 = ''
        let dot4 = ''
        let anchor1 = ''
        let anchor2 = ''
        let anchor3 = ''
        let anchor4 = ''
        let tip = ''
        switch (step) {
            case 1:
            case 5:
            case 8:
                dot1 = createStepDot('complete','步骤一')
                dot2 = createStepDot('active','步骤二')
                dot3 = createStepDot(null,'步骤三')
                dot4 = createStepDot(null,'步骤四')
                anchor1 = createStepAnchor('active','步骤一','填写报销单')
                anchor2 = createStepAnchor(null,'步骤二','部长审核')
                anchor3 = createStepAnchor(null,'步骤三','总经理审核')
                anchor4 = createStepAnchor(null,'步骤四','财务打款')
                switch (step) {
                    case 5:
                        tip = createTip('warning',null)
                        break
                    case 8:
                        tip = createTip('danger','部门经理')
                        break
                    default:
                }
                break
            case 2:
            case 6:
            case 9:
                dot1 = createStepDot('complete','步骤一')
                dot2 = createStepDot('complete','步骤二')
                dot3 = createStepDot('active','步骤三')
                dot4 = createStepDot(null,'步骤四')
                anchor1 = createStepAnchor('active','步骤一','填写报销单')
                anchor2 = createStepAnchor('active','步骤二','部长审核')
                anchor3 = createStepAnchor(null,'步骤三','总经理审核')
                anchor4 = createStepAnchor(null,'步骤四','财务打款')
                switch (step) {
                    case 6:
                        tip = createTip('warning',null)
                        break
                    case 9:
                        tip = createTip('danger','总经理')
                        break
                    default:
                }
                break
            case 3:
            case 7:
            case 10:
                dot1 = createStepDot('complete','步骤一')
                dot2 = createStepDot('complete','步骤二')
                dot3 = createStepDot('complete','步骤三')
                dot4 = createStepDot('active','步骤四')
                anchor1 = createStepAnchor('active','步骤一','填写报销单')
                anchor2 = createStepAnchor('active','步骤二','部长审核')
                anchor3 = createStepAnchor('active','步骤三','总经理审核')
                anchor4 = createStepAnchor(null,'步骤四','财务打款')
                switch (step) {
                    case 7:
                        tip = createTip('warning',null)
                        break
                    case 10:
                        tip = createTip('danger','财务')
                        break
                    default:
                }
                break
            case 4:
                dot1 = createStepDot('complete','步骤一')
                dot2 = createStepDot('complete','步骤二')
                dot3 = createStepDot('complete','步骤三')
                dot4 = createStepDot('complete','步骤四')
                anchor1 = createStepAnchor('active','步骤一','填写报销单')
                anchor2 = createStepAnchor('active','步骤二','部长审核')
                anchor3 = createStepAnchor('active','步骤三','总经理审核')
                anchor4 = createStepAnchor('active','步骤四','财务打款')
                tip = createTip(null,null)
                break
            default:
                console.log('进度非法!')
        }
        step_dots.append(dot1).append(dot2).append(dot3).append(dot4)
        step_anchor.append(anchor1).append(anchor2).append(anchor3).append(anchor4)
        $('#procedure').append(step_dots).append(step_anchor).append(tip)
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

    function createStepDot(status,text) {
        return $('<li class="nav-step-item '+status+'"><span>'+text+'</span><a href="javascript:void(0)"></a></li>')
    }

    function createStepAnchor(status,text1,text2) {
        return $('<li class="nav-step-item '+status+'"><a href="javascript:void(0)"><h6>'+text1+'</h6><p class="m-0">'+text2+'</p></a></li>')
    }

    function createTip(type,role) {
        if('warning'===type) {
            return $('<div class="alert alert-warning" role="alert">等待用户修改。</div>')
        }else if ('danger'===type){
            return $('<div class="alert alert-danger" role="alert">已被'+role+'终止,该报销单已结束。</div>')
        }else {
            return $('<div class="alert alert-success" role="alert">已成功报销,该报销单已结束。</div>')
        }
    }
})