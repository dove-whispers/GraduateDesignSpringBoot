$(function () {
    // 新增
    let queryEmployeeUrl = '/employee/queryEmployee'
    let operateEmployeeUrl = '/employee/operateEmployee'
    let em_id = getQueryParam('emId')
    let is_edit = getQueryParam('edit')
    //下拉列表初始化
    let queryActiveDepartmentListUrl = '/department/queryActiveDepartmentList'
    let queryActivePositionListUrl = '/position/queryActivePositionList'
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
    //非空则说明用户在查看或编辑
    if (em_id) {
        //从后端填充值
        $.ajax({
            url: queryEmployeeUrl,
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            data: {
                employeeId: em_id,
            },
            success: function (data) {
                if (data.success) {
                    let employee = data.data
                    $('#employee-name').val(employee.name)
                    $('#login-name').val(employee.loginName)
                    if (1 === employee.status) {
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
        let name = $('#employee-name').val()
        let loginName = $('#login-name').val()
        let status = $('input[name="status"][checked]').val()
        let positionId = $('#position-select').val()
        let depId = $('#department-select').val()
        let emId = is_edit ? em_id : ''
        $.ajax({
            url: operateEmployeeUrl,
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                emId, name, loginName, depId, positionId, status
            }),
            success: function (data) {
                if (data.success) {
                    lightyear.notify(is_edit ? '修改员工信息成功~' : '新增员工信息成功~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center', '/employee/toList')
                } else {
                    lightyear.notify(is_edit ? '修改员工信息失败!' : '新增员工信息失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
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