$(function () {
    // 新增
    let queryEmployeeUrl = '/employee/queryEmployee'
    let operateEmployeeUrl = '/employee/operateEmployee'
    let em_id = getQueryParam('emId')
    let is_edit = getQueryParam('edit')
    //下拉列表初始化
    let queryActiveDepartmentListUrl = '/department/queryActiveDepartmentList'
    let queryActivePositionListUrl = '/position/queryActivePositionList'
    //有效下拉信息
    let activeDepartmentList = {}
    let activePositionList = {}

    $.ajax({
        url: queryActiveDepartmentListUrl,
        type: 'POST',
        async: false,
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                activeDepartmentList = data.data
            }
        }
    })

    $.ajax({
        url: queryActivePositionListUrl,
        type: 'POST',
        async: false,
        cache: false,
        dataType: 'json',
        success: function (data) {
            if (data.success) {
                activePositionList = data.data
                process()
            }
        }
    })

    //非空则说明用户在查看或编辑
    function process() {
        if (em_id) {
            let employee = {}
            let department_select = $('#department-select')
            let position_select = $('#position-select')
            $.ajax({
                url: queryEmployeeUrl,
                type: 'POST',
                async: false,
                cache: false,
                dataType: 'json',
                data: {
                    emId: em_id,
                },
                success: function (data) {
                    if (data.success) {
                        employee = data.data
                        $('#employee-name').val(employee.name)
                        $('#login-name').val(employee.loginName)
                        if (1 === employee.status) {
                            $('#status-on').attr('checked', true)
                            $('#status-off').removeAttr('checked')
                        } else {
                            $('#status-off').attr('checked', true)
                            $('#status-on').removeAttr('checked')
                        }
                        if (is_edit) {
                            activeDepartmentList.map(function (item, index) {
                                let department_option = $('<option></option>')
                                department_option.prop('value', item.depId)
                                department_option.text(item.name)
                                department_select.append(department_option)
                            })
                            department_select.selectpicker('refresh')
                            department_select.selectpicker('val', employee.depId)
                            activePositionList.map(function (item, index) {
                                let position_option = $('<option></option>')
                                position_option.prop('value', item.positionId)
                                position_option.text(item.positionName)
                                position_select.append(position_option)
                            })
                            position_select.selectpicker('refresh')
                            position_select.selectpicker('val', employee.positionId)
                        } else {
                            activeDepartmentList.map(function (item, index) {
                                if (employee.depId === item.depId) {
                                    let department_option = $('<option></option>')
                                    department_option.text(item.name)
                                    department_select.append(department_option)
                                    department_select.selectpicker('refresh')
                                    department_select.selectpicker('render')
                                }
                            })
                            activePositionList.map(function (item, index) {
                                if (employee.positionId === item.positionId) {
                                    let position_option = $('<option></option>')
                                    position_option.text(item.positionName)
                                    position_select.append(position_option)
                                    position_select.selectpicker('refresh')
                                    position_select.selectpicker('render')
                                }
                            })
                        }
                    } else {
                        lightyear.notify('查询失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
                    }
                }
            })
        } else {
            let department_target = $('#department-select')
            activeDepartmentList.map(function (item, index) {
                let department_option = $('<option></option>')
                department_option.prop('value', item.depId)
                department_option.text(item.name)
                department_target.append(department_option)
            })
            department_target.selectpicker('refresh')
            department_target.selectpicker('render')
            let position_target = $('#position-select')
            activePositionList.map(function (item, index) {
                let position_option = $('<option></option>')
                position_option.prop('value', item.positionId)
                position_option.text(item.positionName)
                position_target.append(position_option)
            })
            position_target.selectpicker('refresh')
            position_target.selectpicker('render')
        }
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