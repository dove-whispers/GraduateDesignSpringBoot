$(function () {
    //定义渲染列表时的条件
    let request_condition = {}
    $('.search-bar .dropdown-menu a').click(function () {
        let field = $(this).data('field') || '';
        $('#search-field').val(field);
        $('#search-btn').html($(this).text() + ' <span class="caret"></span>');
    });

    $('#search-input').keydown(function (e) {
        //13代表回车键
        if (13 === e.keyCode) {
            let type = $('#search-field').val()
            let keyword = $(this).val()
            if ('name' === type) {
                request_condition.name = keyword
                request_condition.address = null
            } else {
                request_condition.name = null
                request_condition.address = keyword
            }
            getList(request_condition)
        }
    })

    $('#status-switch').change(function () {
        if ($('#status-switch').is(':checked')) {
            request_condition.status = 1
            getList(request_condition)
        } else {
            request_condition.status = null
            getList(request_condition)
        }
    })

    getList(request_condition)

    //获取部门列表
    function getList(data) {
        $.ajax({
            url: '/department/getList',
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(data),
            success: function (data) {
                if (data.success) {
                    //动态渲染列表数据
                    if (0 === data.data.record.length) {
                        lightyear.notify('啥也没搜到~', 'info', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                    }
                    handleList(data.data.record)
                } else {
                    lightyear.notify(data.errMsg, 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })
    }

    //对列表数据进行渲染
    function handleList(data) {
        let i = 1
        let html = ''
        data.map(function (item, index) {
            html += '<tr>'
                + '<td>' + (i++) + '</td>'
                + '<td>' + item.name + '</td>'
                + '<td>' + item.address + '</td>'
                + departmentStatus(item.status)
                + '<td>'
                + '<div class="btn-group">'
                + '<a class="btn btn-xs btn-default" href="/department/goDepartmentEdit?edit=true&depId=' + (item.depId) + '" title="编辑" data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>'
                + '<a class="btn btn-xs btn-default" href="/department/goDepartment?depId=' + (item.depId) + '" title="查看" data-toggle="tooltip"><i class="mdi mdi-eye"></i></a>'
                + updateDepartmentStatus(item.depId, item.status)
                + '</div>'
                + '</td>'
                + '</tr>'
        })
        $('.department-wrap').html(html)
    }

    function updateDepartmentStatus(depId, status) {
        if (status === 1) {
            return '<a class="btn btn-xs btn-default department-status-btn" href="#!" title="修改状态" data-id=' + depId + ' data-status=' + status + ' data-toggle="tooltip"><i class="mdi mdi-toggle-switch"></i></a>'
        }
        return '<a class="btn btn-xs btn-default department-status-btn" href="#!" title="修改状态" data-id=' + depId + ' data-status=' + status + ' data-toggle="tooltip"><i class="mdi mdi-toggle-switch-off"></i></a>'
    }

    $('.department-wrap').on('click', 'a', function (e) {
        let target = e.currentTarget;
        if ($(this).hasClass('department-status-btn')) {
            let depId = target.dataset.id
            let status = target.dataset.status
            $.ajax({
                url: '/department/toggleDepartmentStatus',
                type: 'POST',
                async: true,
                cache: false,
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify({
                    depId, status
                }),
                success: function (data) {
                    if (data.success) {
                        lightyear.notify('修改状态成功~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center')
                        getList(request_condition)
                    } else {
                        lightyear.notify('修改状态失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                        getList(request_condition)
                    }
                }
            })

        }
    })

    function departmentStatus(status) {
        if (status === 1) {
            return '<td><font class="text-success">有效</font></td>'
        }
        return '<td><font class="text-danger">失效</font></td>'
    }
})