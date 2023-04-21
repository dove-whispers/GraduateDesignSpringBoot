$(function () {
    // 新增
    let queryPositionUrl = '/position/queryPosition'
    let operatePositionUrl = '/position/operatePosition'
    let position_id = getQueryParam('positionId')
    let is_edit = getQueryParam('edit')
    // 非空则说明用户在查看或编辑
    if (position_id) {
        //从后端填充值
        $.ajax({
            url: queryPositionUrl,
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            data: {
                positionId: position_id,
            },
            success: function (data) {
                if (data.success) {
                    let position = data.data
                    $('#position-name').val(position.positionName)
                    if (1 === position.status) {
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
        let positionName = $('#position-name').val()
        let status = $('input[name="status"][checked]').val()
        let positionId = is_edit ? position_id : ''
        $.ajax({
            url: operatePositionUrl,
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                positionId, positionName, status
            }),
            success: function (data) {
                if (data.success) {
                    lightyear.notify(is_edit ? '修改职位成功~' : '新增职位成功~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center', '/position/toList')
                } else {
                    lightyear.notify(is_edit ? '修改职位失败!' : '新增职位失败!', 'danger', 2000, 'mdi mdi-emoticon-sad', 'top', 'center')
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