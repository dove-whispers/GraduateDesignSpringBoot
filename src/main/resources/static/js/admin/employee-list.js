$(function () {
    //列表
    let wrap = $('.employee-wrap')
    //定义渲染列表时的条件
    let request_condition = {}
    //是否初始化分页插件
    let flag = true
    //下拉列表初始化
    let queryActiveDepartmentListUrl = '/department/queryActiveDepartmentList'
    let queryActivePositionListUrl = '/position/queryActivePositionList'

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

    //获取下拉列表数据
    $.ajax({
        url: queryActivePositionListUrl,
        type: 'POST',
        async: false,
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                let activeList = data.data
                let target = $('#position-select')
                target.append($('<option value="">全部职位</option>'))
                activeList.map(function (item, index) {
                    let option = $('<option></option>')
                    option.prop('value', item.positionId)
                    option.text(item.positionName)
                    target.append(option)
                })
                target.selectpicker('refresh');
                target.selectpicker('render');
            }
        }
    })

    $.ajax({
        url: queryActiveDepartmentListUrl,
        type: 'POST',
        async: false,
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                let activeList = data.data
                let target = $('#department-select')
                target.append($('<option value="">全部部门</option>'))
                activeList.map(function (item, index) {
                    let option = $('<option></option>')
                    option.prop('value', item.depId)
                    option.text(item.name)
                    target.append(option)
                })
                target.selectpicker('refresh');
                target.selectpicker('render');
            }
        }
    })

    $('#department-select').change(function () {
        request_condition.depId = $(this).val()
        flag = true
        request_condition.current = 1
        getList(request_condition)
    })

    $('#position-select').change(function () {
        request_condition.positionId = $(this).val()
        flag = true
        request_condition.current = 1
        getList(request_condition)
    })

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

    function handleList(data) {
        let html = ''
        data.map(function (item, index) {
            html += '<tr>'
                + '<td>' + (index+1) + '</td>'
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
        wrap.html(html)
    }

    function updateEmployeeStatus(emId, status) {
        if (status === 1) {
            return '<a class="btn btn-xs btn-default employee-status-btn" href="#!" title="修改状态" data-id=' + emId + ' data-status=' + status + ' data-toggle="tooltip"><i class="mdi mdi-toggle-switch"></i></a>'
        }
        return '<a class="btn btn-xs btn-default employee-status-btn" href="#!" title="修改状态" data-id=' + emId + ' data-status=' + status + ' data-toggle="tooltip"><i class="mdi mdi-toggle-switch-off"></i></a>'
    }

    wrap.on('click', 'a', function (e) {
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