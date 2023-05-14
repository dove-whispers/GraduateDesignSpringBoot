$(function () {
    let expenseId = getQueryParam('expenseId')
    let body = $('#detail_body')
    const pre = 'data:image/jpeg;base64,'
    let result_div = $('#result')
    if (expenseId) {
        getData().then(r => r ? console.log('渲染成功') : console.log('渲染失败'))
    }

    async function getData() {
        try {
            const len = await getBadge()
            if (0 !== len) {
                $('.badge').text(len)
            }
            const step = await queryStep()
            handleProcedure(step)
            const deal_record = await queryLatestDealRecord()
            handleDeal(deal_record)
            const isSelf = await isReportBack()
            const list_data = await queryExpenseReportDetail()
            if ('打回' === deal_record.dealResult) {
                handleDealResult(true, isSelf)
                handleList(list_data, true)
            } else {
                handleDealResult(false, isSelf)
                handleList(list_data, false)
            }
            return true
        } catch (e) {
            console.error(e)
            lightyear.notify(e.message, 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
            return false
        }
    }

    function getBadge() {
        return new Promise((resolve, reject) => {
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
                        resolve(data.data.records.length)
                    } else {
                        reject(data.errMsg)
                    }
                }
            })
        })
    }

    function isReportBack() {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: '/expenseReport/isSelfReport',
                type: 'POST',
                async: false,
                cache: false,
                dataType: 'json',
                data: {expensiveId: expenseId},
                success: data => {
                    if (data.success) {
                        resolve(data.outcome)
                    } else {
                        reject(new Error('报销单查询失败!'))
                    }
                }
            })
        })
    }

    function queryExpenseReportDetail() {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: '/expenseReportDetail/queryExpenseReportDetail',
                type: 'POST',
                async: false,
                cache: false,
                dataType: 'json',
                data: {expensiveId: expenseId},
                success: data => {
                    if (data.success) {
                        resolve(data.data)
                    } else {
                        reject(new Error('报销单细节查询失败!'))
                    }
                }
            })
        })
    }

    function queryLatestDealRecord() {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: '/dealRecord/queryLatestDealRecord',
                type: 'POST',
                async: false,
                cache: false,
                dataType: 'json',
                data: {expensiveId: expenseId},
                success: data => {
                    if (data.success) {
                        resolve(data.data)
                    } else {
                        reject(new Error('报销报销单最新操作失败!'))
                    }
                }
            })
        })
    }

    function queryStep() {
        return new Promise((resolve, reject) => {
            $.ajax({
                url: '/dealRecord/queryExpenseReportStep',
                type: 'POST',
                async: false,
                cache: false,
                dataType: 'json',
                data: {expensiveId: expenseId},
                success: data => {
                    if (data.success) {
                        resolve(data.step)
                    } else {
                        reject(new Error('报销进度查询失败!'))
                    }
                }
            })
        })
    }

    function handleDealResult(result, isSelf) {
        let html = '<label class="col-lg-12 m-b-15">处理结果:</label>'
        if (result) {
            html += '<button id="edit" class="btn btn-label btn-primary m-r-15"><label><i class="mdi mdi-check-circle-outline"></i></label>提交修改</button>'
            if (isSelf) {
                html += '<button id="abandon" class="btn btn-label btn-danger"><label><i class="mdi mdi-stop-circle-outline"></i></label>放&nbsp;&nbsp;弃</button>'
            } else {
                html += '<button id="warning" class="btn btn-label btn-warning m-r-15" data-name="打回"><label><i class="mdi mdi-arrow-left-bold-circle-outline"></i></label>打回修改</button>'
                    + '<button id="danger" class="btn btn-label btn-danger" data-name="终止"><label><i class="mdi mdi-close-circle-outline"></i></label>终&nbsp;&nbsp;止</button>'
            }
        } else {
            html += '<button id="primary" class="btn btn-label btn-primary m-r-15" data-name="通过"><label><i class="mdi mdi-check-circle-outline"></i></label>确认通过</button>'
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
    }).on('click', '.detail_amount', function () {
        let total_amount = 0
        body.find(".detail_amount").each(function (index, element) {
            total_amount += $(this).val() * 1
        })
        $('#total_amount').val(total_amount)
    })

    result_div.on('click', '#primary,#warning,#danger', function () {
        let name = $(this).data('name')
        let comment = $('#need_comment').val()
        let color = ''
        let type = ''
        if ('通过' === name) {
            color = 'btn-green'
            type = 'green'
        } else if ('打回' === name) {
            color = 'btn-orange'
            type = 'orange'
        } else {
            color = 'btn-red'
            type = 'red'
        }
        $.confirm({
            title: '确认框',
            content: '确认' + name + '吗?',
            type: type,
            typeAnimated: true,
            buttons: {
                omg: {
                    text: '确认',
                    btnClass: color,
                    action: function () {
                        $.ajax({
                            url: '/dealRecord/addAuditDeal',
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
                close: {
                    text: '取消',
                }
            }
        })
    }).on('click', '#edit', function () {
        $.confirm({
            title: '确认框',
            content: '确认提交修改吗?',
            type: 'blue',
            typeAnimated: true,
            buttons: {
                omg: {
                    text: '确认',
                    btnClass: 'btn-blue',
                    action: () => getSubmit()
                },
                close: {
                    text: '取消',
                }
            }
        })
    }).on('click', '#abandon', function () {
        $.confirm({
            title: '确认框',
            content: '确认放弃该报销单吗?',
            type: 'red',
            typeAnimated: true,
            buttons: {
                omg: {
                    text: '确认',
                    btnClass: 'btn-red',
                    action: () => {
                        $.ajax({
                            url: '/expenseReport/abandonReport',
                            type: 'POST',
                            async: false,
                            cache: false,
                            dataType: 'json',
                            data: {
                                expensiveId: expenseId,
                                comment: $('#need_comment').val()
                            },
                            success: data => {
                                if (data.success) {
                                    lightyear.notify('报销单已成功结束~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center', '/main')
                                } else {
                                    lightyear.notify('报销单处理失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                                }
                            }
                        })
                    }
                },
                close: {
                    text: '取消',
                }
            }
        })
    })

    function getSubmit() {
        const cause = $('#expense_cause').val()
        const total_amount = $('#total_amount').val()
        const total_comment = $('#need_comment').val()
        let report_details = []
        let item = ''
        let time = ''
        let type = ''
        let code = ''
        let num = ''
        let amount = ''
        let comment = ''
        let image = ''
        body.children("tr").each(function (rowIndex, rowElement) {
            if (rowIndex % 2 === 0) {
                item = ''
                time = ''
                type = ''
                code = ''
                num = ''
                amount = ''
                comment = ''
                image = ''
                $(rowElement).children("td").each(function (colIndex, colElement) {
                    switch (colIndex) {
                        case 0:
                            item = $(colElement).children().val()
                            break
                        case 1:
                            time = $(colElement).children().val()
                            break
                        case 2:
                            type = $(colElement).children().val()
                            break
                        case 3:
                            code = $(colElement).children().val()
                            break
                        case 4:
                            num = $(colElement).children().val()
                            break
                        case 5:
                            amount = $(colElement).children().val()
                            break
                        default:
                    }
                })
            } else {
                $(rowElement).find('td.col-xs-5').each(function (colIndex, colElement) {
                    if (0 === colIndex) {
                        comment = $(colElement).find('textarea').val()
                    } else {
                        image = $(colElement).find("img").prop('src')
                        image = image && image.length > 100 ? image.substring(image.indexOf(",") + 1) : ''
                    }
                })
                const report_detail = createReportDetail(item, time, type, code, num, amount, comment, image)
                report_details.push(report_detail)
            }
        })
        $.ajax({
            url: '/expenseReport/updateExpenseReport',
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                expenseId,
                expenseReportDetails: report_details,
                cause,
                totalAmount: total_amount,
                comment: total_comment
            }),
            success: data => {
                if (data.success) {
                    lightyear.notify('修改报销单成功~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center', '/main')
                } else {
                    lightyear.notify('修改报销单失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })
    }

    function handleDeal(record) {
        let record_comment = $('#record_comment')
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

    function handleList(data, result) {
        let $expense_cause = $('#expense_cause')
        $expense_cause.val(data.cause)
        $('#total_amount').val(data.totalAmount)
        let html = ''
        if (result) {
            data.expenseReportDetails.map(function (item, index) {
                html += '<tr>'
                    + '<th scope="row">' + (index + 1) + '</th>'
                    + '<td><input class="form-control detail_item" type="text" value="' + item.item + '"></td>'
                    + '<td><input class="form-control datepicker detail_time detail_item" type="text" placeholder="请选择日期" value="' + item.time + '"></td>'
                    + '<td><input class="form-control detail_item" type="text" value="' + item.type + '"></td>'
                    + '<td><input class="form-control detail_item" type="text" value="' + item.code + '"></td>'
                    + '<td><input class="form-control detail_item" type="text" value="' + item.num + '"></td>'
                    + '<td><input class="form-control detail_item detail_amount" type="text" value="' + item.amount + '"></td>'
                    + '<td>'
                    + '<a class="btn btn-xs btn-default d-cell" href="#collapse' + (index + 1) + '" data-toggle="collapse" title="更多" data-toggle="tooltip"><i class="mdi mdi-dots-horizontal"></i></a>'
                    + '</td>'
                    + '</tr>'
                    + '<tr>'
                    + '<td colspan="8" class="collapse" id="collapse' + (index + 1) + '">'
                    + '<div class="well m-b-0">'
                    + '<table>'
                    + '<tr>'
                    + setComment(item.comment, result)
                    + '<td class="col-xs-2">'
                    + '<label class="col-xs-12">发票:</label>'
                    + '<div class="btn-group">'
                    + '<button type="button" class="btn btn-primary btn-round dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">上传发票图片<span class="caret"></span></button>'
                    + '<ul class="dropdown-menu">'
                    + '<li><a class="file" href="javascript:void(0)">本地上传<input type="file" class="detail_image" accept="image/jpg,image/jpeg,image/png"></a></li>'
                    + '<li role="separator" class="divider"></li>'
                    + '<li><a href="javascript:void(0)">拍照上传</a></li>'
                    + '</ul>'
                    + '</div>'
                    + '</td>'
                    + setImage(item.image, result)
                    + '</tr>'
                    + '</table>'
                    + '</div>'
                    + '</td>'
                    + '</tr>'
            })
        } else {
            $expense_cause.prop('disabled', true)
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
                    + setComment(item.comment, result)
                    + setImage(item.image, result)
                    + '</tr>'
                    + '</table>'
                    + '</div>'
                    + '</td>'
                    + '</tr>'
            })
        }
        body.html(html)
        if (result) {
            $('.datepicker').datepicker({
                endDate: new Date(),
                format: "yyyy-mm-dd",
                autoclose: true,
                todayHighlight: true,
                language: 'zh-CN',
                orientation: 'button',
            })
        }
    }

    body.on('change', '.detail_image', async function () {
        lightyear.loading('show');
        try {
            let fileStr = await changeFileIntoBase64($(this)[0].files[0])
            let ttd = $(this).closest('td').next()
            let tr = $(this).closest('tr').prev()
            if (fileStr) {
                if (1 === ttd.has('img').length) {
                    ttd.find("img").prop('src', fileStr)
                } else {
                    ttd.empty().append('<img class="col-xs-12 img-f" src="' + (fileStr) + '" alt="点击放大" style="cursor:pointer"/>')
                }
            } else {
                if (1 === ttd.has('img').length) {
                    ttd.empty().append('<span class="col-xs-12 text-truncate">暂无发票图片</span>')
                }
            }
            $(this).closest('ul').prev().click()
            //TODO:API
            // const info = await queryImgInfo(fileStr)
            // fillInInfo(tr, info)
        } catch (e) {
            console.error(e)
            lightyear.notify('图片信息识别失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
        } finally {
            lightyear.loading('hide');
        }
    })

    function setImage(image, result) {
        if (image) {
            if (result) {
                return '<td class="col-xs-5"><img class="col-xs-12 img-f" src="' + (pre + image) + '" alt="点击放大" style="cursor:pointer"/></td>'
            } else {
                return '<td><img class="col-xs-12 img-f" src="' + (pre + image) + '" alt="点击放大" style="cursor:pointer"/></td>'
            }
        } else {
            if (result) {
                return '<td class="col-xs-5"><span class="col-xs-12 text-truncate">暂无发票图片</span></td>'
            } else {
                return '<td><span class="col-xs-12 text-truncate">暂无发票图片</span></td>'
            }
        }
    }

    function setComment(comment, result) {
        if (comment) {
            if (result) {
                return '<td class="col-xs-5"><label class="col-xs-12">备注:</label><textarea class="col-xs-12" rows="6" style="resize:none">' + comment + '</textarea></td>'
            } else {
                return '<td><label class="col-xs-12">备注:</label><textarea class="col-xs-12" rows="6" disabled style="resize:none">' + comment + '</textarea></td>'
            }
        } else {
            if (result) {
                return '<td class="col-xs-5"><label class="col-xs-12">备注:</label><textarea class="col-xs-12" rows="6" placeholder="暂无备注" style="resize:none"></textarea></td>'
            } else {
                return '<td><label class="col-xs-12">备注:</label><textarea class="col-xs-12" rows="6" placeholder="暂无备注" disabled style="resize:none"></textarea></td>'
            }
        }
    }

    function handleProcedure(step) {
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
                dot1 = createStepDot('complete', '步骤一')
                dot2 = createStepDot('active', '步骤二')
                dot3 = createStepDot(null, '步骤三')
                dot4 = createStepDot(null, '步骤四')
                anchor1 = createStepAnchor('active', '步骤一', '填写报销单')
                anchor2 = createStepAnchor(null, '步骤二', '部长审核')
                anchor3 = createStepAnchor(null, '步骤三', '总经理审核')
                anchor4 = createStepAnchor(null, '步骤四', '财务打款')
                switch (step) {
                    case 5:
                        tip = createTip('warning', null)
                        break
                    case 8:
                        tip = createTip('danger', '部门经理')
                        break
                    default:
                }
                break
            case 2:
            case 6:
            case 9:
                dot1 = createStepDot('complete', '步骤一')
                dot2 = createStepDot('complete', '步骤二')
                dot3 = createStepDot('active', '步骤三')
                dot4 = createStepDot(null, '步骤四')
                anchor1 = createStepAnchor('active', '步骤一', '填写报销单')
                anchor2 = createStepAnchor('active', '步骤二', '部长审核')
                anchor3 = createStepAnchor(null, '步骤三', '总经理审核')
                anchor4 = createStepAnchor(null, '步骤四', '财务打款')
                switch (step) {
                    case 6:
                        tip = createTip('warning', null)
                        break
                    case 9:
                        tip = createTip('danger', '总经理')
                        break
                    default:
                }
                break
            case 3:
            case 7:
            case 10:
                dot1 = createStepDot('complete', '步骤一')
                dot2 = createStepDot('complete', '步骤二')
                dot3 = createStepDot('complete', '步骤三')
                dot4 = createStepDot('active', '步骤四')
                anchor1 = createStepAnchor('active', '步骤一', '填写报销单')
                anchor2 = createStepAnchor('active', '步骤二', '部长审核')
                anchor3 = createStepAnchor('active', '步骤三', '总经理审核')
                anchor4 = createStepAnchor(null, '步骤四', '财务打款')
                switch (step) {
                    case 7:
                        tip = createTip('warning', null)
                        break
                    case 10:
                        tip = createTip('danger', '财务')
                        break
                    default:
                }
                break
            case 4:
                dot1 = createStepDot('complete', '步骤一')
                dot2 = createStepDot('complete', '步骤二')
                dot3 = createStepDot('complete', '步骤三')
                dot4 = createStepDot('complete', '步骤四')
                anchor1 = createStepAnchor('active', '步骤一', '填写报销单')
                anchor2 = createStepAnchor('active', '步骤二', '部长审核')
                anchor3 = createStepAnchor('active', '步骤三', '总经理审核')
                anchor4 = createStepAnchor('active', '步骤四', '财务打款')
                tip = createTip(null, null)
                break
            case 11:
                dot1 = createStepDot('active', '步骤一')
                dot2 = createStepDot('null', '步骤二')
                dot3 = createStepDot(null, '步骤三')
                dot4 = createStepDot(null, '步骤四')
                anchor1 = createStepAnchor('active', '步骤一', '填写报销单')
                anchor2 = createStepAnchor(null, '步骤二', '部长审核')
                anchor3 = createStepAnchor(null, '步骤三', '总经理审核')
                anchor4 = createStepAnchor(null, '步骤四', '财务打款')
                tip = createTip('abandon', null)
                break
            default:
                console.error(new Error('进度非法!'))
        }
        step_dots.append(dot1).append(dot2).append(dot3).append(dot4)
        step_anchor.append(anchor1).append(anchor2).append(anchor3).append(anchor4)
        $('#procedure').append(step_dots).append(step_anchor).append(tip)
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
        } else if ('abandon' === type) {
            return $('<div class="alert alert-danger" role="alert">申请人已放弃。</div>')
        } else {
            return $('<div class="alert alert-success" role="alert">已成功报销,该报销单已结束。</div>')
        }
    }

    function createReportDetail(item, time, type, code, num, amount, comment, image) {
        let detail = {};
        detail.item = item;
        detail.time = time;
        detail.type = type;
        detail.code = code;
        detail.num = num;
        detail.amount = amount;
        detail.comment = comment;
        detail.image = image;
        return detail;
    }

    function changeFileIntoBase64(file) {
        return new Promise((resolve, reject) => {
            const fr = new FileReader()
            fr.readAsDataURL(file)
            fr.onload = () => {
                const base64Str = fr.result
                resolve(base64Str)
            }
        })
    }
})