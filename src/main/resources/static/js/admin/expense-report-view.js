$(function () {
    let wrap = $('.view-wrap')
    let request_condition = {}
    let flag = true

    $('.search-bar .dropdown-menu a').click(function () {
        let field = $(this).data('field') || '';
        $('#search-field').val(field);
        $('#search-btn').html($(this).text() + ' <span class="caret"></span>');
    });

    $('#search-input').keydown(function (e) {
        flag = true
        request_condition.current = 1
        if (13 === e.keyCode) {
            let type = $('#search-field').val()
            let keyword = $(this).val()
            if ('name' === type) {
                request_condition.name = keyword
                request_condition.cause = null
            } else {
                request_condition.name = null
                request_condition.cause = keyword
            }
            getList(request_condition)
        }
    })

    getList(request_condition)

    function getList(data) {
        $.ajax({
            url: '/expenseReport/getViewList',
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(data),
            success: function (data) {
                if (data.success) {
                    if (0 === data.data.records.length) {
                        let fillRow = $('<tr><td colspan="9" style="text-align:center;"></td></tr>')
                        if (!request_condition || !request_condition.name && !request_condition.cause) {
                            fillRow.children().text('不存在任何待处理的报销单')
                            lightyear.notify('暂无待处理的报销单~', 'info', 2000, 'mdi mdi-emoticon-excited', 'top', 'center')
                        } else {
                            fillRow.children().text('未找到相关报销单')
                            lightyear.notify('啥也没搜到~', 'info', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                        }
                        wrap.empty().append(fillRow)
                    } else {
                        handleList(data.data.records)
                    }
                    if (flag) {
                        getPageInfo(data.data)
                        flag = false
                    }
                } else {
                    lightyear.notify(data.errMsg, 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })
    }

    function handleList(data) {
        let maxLength = 0
        data.map(function (item, index) {
            maxLength = Math.max(maxLength, item.totalAmount.toString().length)
        })
        let html = ''
        data.map(function (item, index) {
            html += '<tr>'
                + '<td>' + (index + 1) + '</td>'
                + '<td><a class="my-link" href="/employee/goEmployee?emId=' + (item.createEmployee.emId) + '" title="查看" data-toggle="tooltip">' + item.createEmployee.name + '</a></td>'
                + '<td>' + item.createEmployee.department.name + '</td>'
                + '<td>' + item.cause + '</td>'
                + '<td>' + item.createTime + '</td>'
                + '<td style="padding-left: ' + (10 + (maxLength - item.totalAmount.toString().length) * 8) + 'px">' + item.totalAmount + '元' + '</td>'
                + expenseReportStatus(item.status)
                + '<td>'
                + '<div class="progress progress-striped progress-sm">'
                + expenseReportProgress(item.status)
                + '</div>'
                + '</td>'
                + '<td>'
                + '<div class="btn-group">'
                + '<a class="btn btn-xs btn-default" href="/expenseReportDetail/goViewExpenseReportDetail?expenseId=' + (item.expenseId) + '" title="查看" data-toggle="tooltip"><i class="mdi mdi-eye"></i></a>'
                + '</div>'
                + '</td>'
                + '</tr>'
        })
        wrap.html(html)
    }

    function expenseReportStatus(status) {
        if (status === '新创建') {
            return '<td><span class="label label-info">新创建</span></td>'
        } else if (status === '已打款') {
            return '<td><span class="label label-success">已打款</span></td>'
        } else if (status === '总经理审核') {
            return '<td><span class="label label-info">总经理审核</span></td>'
        } else if (status === '财务审核') {
            return '<td><span class="label label-info">财务审核</span></td>'
        } else if (status === '已终止') {
            return '<td><span class="label label-danger">已终止</span></td>'
        } else if (status === '已放弃') {
            return '<td><span class="label label-danger">已放弃</span></td>'
        } else if (status === '已修改') {
            return '<td><span class="label label-info">已修改</span></td>'
        } else {
            return '<td><span class="label label-warning">待修改</span></td>'
        }
    }

    function expenseReportProgress(status) {
        if (status === '新创建') {
            return '<div class="progress-bar progress-bar-info active" style="width: 25%;"></div>'
        } else if (status === '已打款') {
            return '<div class="progress-bar progress-bar-success active" style="width: 100%;"></div>'
        } else if (status === '总经理审核') {
            return '<div class="progress-bar progress-bar-info active" style="width: 50%;"></div>'
        } else if (status === '财务审核') {
            return '<div class="progress-bar progress-bar-info active" style="width: 75%;"></div>'
        } else if (status === '已终止') {
            return '<div class="progress-bar progress-bar-danger active" style="width: 100%;"></div>'
        } else if (status === '已放弃') {
            return '<div class="progress-bar progress-bar-danger active" style="width: 100%;"></div>'
        } else if (status === '已修改') {
            return '<div class="progress-bar progress-bar-info active" style="width: 60%;"></div>'
        } else {
            return '<div class="progress-bar progress-bar-warning active" style="width: 40%;"></div>'
        }
    }

    function getPageInfo(data) {
        $('#jq-page').pagination({
            pageCount: data.pages,
            coping: true,
            callback: function (e) {
                request_condition.current = e.getCurrent()
                getList(request_condition)
            }
        })
    }
})
