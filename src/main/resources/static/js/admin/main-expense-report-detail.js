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
    }

    function handleProcedure(step) {
        // 1创建 2部门经理审核通过 3总经理审核通过 4财务打款
        // 5部门经理打回 6总经理打回 7财务打回
        // 8部门经理终止 9总经理终止 10财务终止
        let html = '<div class="divider text-uppercase">报销进度</div>'
        html += '<ul class="nav-step step-dots">'
            + '      <li class="nav-step-item complete">'
            + '          <span>步骤一</span>'
            + '          <a href="javascript:void(0)"></a>'
            + '       </li>'
            + '       <li class="nav-step-item active">'
            + '           <span>步骤二</span>'
            + '           <a href="javascript:void(0)"></a>'
            + '       </li>'
            + '       <li class="nav-step-item">'
            + '           <span>步骤三</span>'
            + '           <a href="javascript:void(0)"></a>'
            + '       </li>'
            + '       <li class="nav-step-item">'
            + '           <span>步骤四</span>'
            + '           <a href="javascript:void(0)"></a>'
            + '       </li>'
            + '   </ul>'
            + '   <ul class="nav-step step-anchor m-l-15">'
            + '       <li class="nav-step-item active">'
            + '           <a href="javascript:void(0)">'
            + '           <h6>步骤一</h6>'
            + '           <p class="m-0">填写报销单</p>'
            + '           </a>'
            + '       </li>'
            + '       <li class="nav-step-item">'
            + '           <a href="javascript:void(0)">'
            + '           <h6>步骤二</h6>'
            + '           <p class="m-0">部长审核</p>'
            + '           </a>'
            + '       </li>'
            + '       <li class="nav-step-item">'
            + '           <a href="javascript:void(0)">'
            + '           <h6>步骤三</h6>'
            + '           <p class="m-0">总经理审核</p>'
            + '           </a>'
            + '       </li>'
            + '       <li class="nav-step-item">'
            + '           <a href="javascript:void(0)">'
            + '           <h6>步骤四</h6>'
            + '           <p class="m-0">财务打款</p>'
            + '           </a>'
            + '       </li>'
            + '   </ul>'
        $('#procedure').html(html)
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