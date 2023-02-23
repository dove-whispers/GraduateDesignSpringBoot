$(function () {
    //定义渲染列表时的条件
    let request_condition = {}
    //是否初始化分页插件
    let flag = true

    $('#search-input').keydown(function (e) {
        //初始化分页插件
        flag = true
        request_condition.current = 1
        //13代表回车键
        if (13 === e.keyCode) {
            request_condition.name = $(this).val()
            getList(request_condition)
        }
    })

    $('#status-switch').change(function () {
        //初始化分页插件
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

    //获取部门列表
    function getList(data) {
        $.ajax({
            url: '/employee/getList',
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(data),
            success: function (data) {
                if (data.success) {
                    //动态渲染列表数据
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

    //对列表数据进行渲染
    function handleList(data) {
        console.log(data)
        let i = 1
        let html = ''
        data.map(function (item, index) {
            html += '<tr>'
                + '<td>' + (i++) + '</td>'
                + '<td data-toggle="tooltip" title="' + item.name + '">' + item.name + '</td>'
                + '<td data-toggle="tooltip" title="' + item.loginName + '">' + item.loginName + '</td>'
                + '<td data-toggle="tooltip" title="' + item.department.name + '">' + item.department.name + '</td>'
                + '<td data-toggle="tooltip" title="' + item.position.positionName + '">' + item.position.positionName + '</td>'
                + employeeStatus(item.status)
                + '<td>'
                + '<div class="btn-group">'
                + '<a class="btn btn-xs btn-default" href="/employee/goEmployeeEdit?edit=true&emId=' + (item.emId) + '" title="编辑" data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>'
                + '<a class="btn btn-xs btn-default" href="/employee/goEmployee?emId=' + (item.emId) + '" title="查看" data-toggle="tooltip"><i class="mdi mdi-eye"></i></a>'
                + updateEmployeeStatus(item.emId, item.status)
                + '</div>'
                + '</td>'
                + '</tr>'
        })
        $('.employee-wrap').html(html)
    }

    function updateEmployeeStatus(emId, status) {
        if (status === 1) {
            return '<a class="btn btn-xs btn-default employee-status-btn" href="#!" title="修改状态" data-id=' + emId + ' data-status=' + status + ' data-toggle="tooltip"><i class="mdi mdi-toggle-switch"></i></a>'
        }
        return '<a class="btn btn-xs btn-default employee-status-btn" href="#!" title="修改状态" data-id=' + emId + ' data-status=' + status + ' data-toggle="tooltip"><i class="mdi mdi-toggle-switch-off"></i></a>'
    }

    $('.employee-wrap').on('click', 'a', function (e) {
        let target = e.currentTarget;
        if ($(this).hasClass('employee-status-btn')) {
            let emId = target.dataset.id
            let status = target.dataset.status
            $.ajax({
                url: '/employee/toggleEmployeeStatus',
                type: 'POST',
                async: false,
                cache: false,
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify({
                    emId, status
                }),
                success: function (data) {
                    getList(request_condition)
                    if (data.success) {
                        lightyear.notify('修改状态成功~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center')
                    } else {
                        lightyear.notify('修改状态失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                    }
                }
            })

        }
    })

    function employeeStatus(status) {
        if (status === 1) {
            return '<td><font class="text-success">在职</font></td>'
        }
        return '<td><font class="text-danger">离职</font></td>'
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