$(function () {
    $('.search-bar .dropdown-menu a').click(function () {
        let field = $(this).data('field') || '';
        $('#search-field').val(field);
        $('#search-btn').html($(this).text() + ' <span class="caret"></span>');
    });

    getList()

    //获取部门列表
    function getList() {
        $.ajax({
            url: '/department/getList',
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            success: function (data) {
                if (data.success) {
                    //动态渲染列表数据
                    handleList(data.data)
                } else {
                    //TODO
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
                + '<a class="btn btn-xs btn-default" href="#!" title="编辑" data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>'
                + '<a class="btn btn-xs btn-default" href="#!" title="查看" data-toggle="tooltip"><i class="mdi mdi-eye"></i></a>'
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
                        console.log("修改状态成功")
                    } else {
                        console.log("修改状态失败")
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