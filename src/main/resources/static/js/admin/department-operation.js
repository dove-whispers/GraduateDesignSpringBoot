$(function () {
    // 新增
    let queryDepartmentUrl = '/department/queryDepartment'
    let operateDepartmentUrl = '/department/operateDepartment'
    let dep_id = getQueryParam('depId')
    let is_edit = getQueryParam('edit')
    //非空则说明用户在查看或编辑
    if (dep_id) {
        //从后端填充值
        $.ajax({
            url: queryDepartmentUrl,
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            data: {
                departmentId: dep_id,
            },
            success: function (data) {
                if (data.success) {
                    let department = data.data
                    $('#dep-name').val(department.name)
                    $('#dep-address').val(department.address)
                    if (1 === department.status) {
                        $('#status-on').attr('checked', true)
                        $('#status-off').removeAttr('checked')
                    } else {
                        $('#status-off').attr('checked', true)
                        $('#status-on').removeAttr('checked')
                    }
                } else {
                    lightyear.notify('查询失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })
    }
    $('#submit').click(function () {
        let name = $('#dep-name').val()
        let address = $('#dep-address').val()
        let status = $('input[name="status"][checked]').val()
        let depId = is_edit ? dep_id : ''
        $.ajax({
            url: operateDepartmentUrl,
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                depId, name, address, status
            }),
            success: function (data) {
                if (data.success) {
                    lightyear.notify(is_edit ? '修改部门成功~' : '新增部门成功~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center', '/department/toList')
                } else {
                    lightyear.notify(is_edit ? '修改部门失败!' : '新增部门失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                }
            }
        })
    })
    $('#status-on').click(function () {
        $('#status-on').attr('checked', true)
        $('#status-off').removeAttr('checked')
    })
    $('#status-off').click(function () {
        $('#status-off').attr('checked', true)
        $('#status-on').removeAttr('checked')
    })
})