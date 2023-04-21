$(function () {
    let wrap = $('.main-wrap')
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

    $('#status-switch').change(function () {
        flag = true
        request_condition.current = 1
        if ($('#status-switch').is(':checked')) {
            request_condition.status = 1
        } else {
            request_condition.status = null
        }
        getList(request_condition)
    })

    getList(request_condition)

    function getList(data) {
        $.ajax({
            url: '/expenseReport/getMainList',
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(data),
            success: function (data) {
                if (data.success) {
                    if (0 === data.data.records.length) {
                        lightyear.notify('啥也没搜到~', 'info', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                    }
                    if (flag) {
                        getPageInfo(data.data)
                        flag = false
                    }
                    handleList(data.data.records)
                } else {
                    lightyear.notify(data.errMsg, 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })
    }

    function handleList(data) {
        let html = ''
        data.map(function (item, index) {
            html += '<tr>'
                + '<td>' + (index + 1) + '</td>'
                + '<td>' + item.createEmployee.name + '</td>'
                + '<td>' + item.createEmployee.department.name + '</td>'
                + '<td>' + item.cause + '</td>'
                + '<td>' + item.createTime + '</td>'
                // + '<td>' + item.nextDealEmployee.name + '</td>'
                + expenseReportNextDeal(item.nextDealEmployee)
                + expenseReportStatus(item.status)
                + '<td>'
                + '<div class="progress progress-striped progress-sm">'
                + expenseReportProgress(item.status)
                + '</div>'
                + '</td>'
                + '<td>'
                + '<div class="btn-group">'
                + '<a class="btn btn-xs btn-default" href="/expenseReportDetail/goMainExpenseReportDetail?expenseId=' + (item.expenseId) + '" title="查看" data-toggle="tooltip"><i class="mdi mdi-eye"></i></a>'
                + '</div>'
                + '</td>'
                + '</tr>'
        })
        wrap.html(html)
    }

    function expenseReportNextDeal(employee) {
        if (employee) {
            return '<td>' + employee.name + '</td>'
        }
        return '<td>-</td>'
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
        } else {
            return '<div class="progress-bar progress-bar-warning active" style="width: 50%;"></div>'
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
