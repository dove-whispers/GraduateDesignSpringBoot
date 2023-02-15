$(function () {
    // 新增
    let addDepartmentUrl = '/department/insertDepartment'
    $('#submit').click(function () {
        let name = $('#dep-name').val()
        let address = $('#dep-address').val()
        let status = $('input[name="status"][checked]').val()
        //一种全新的传数据方式
        // let formData = new FormData();
        // formData.append("name", name)
        // formData.append("address", address)
        // formData.append("status", status)
        $.ajax({
            url: addDepartmentUrl,
            type: 'POST',
            async: false,
            cache: false,
            dataType: 'json',
            // contentType: false,
            //processData表示会不会序列化data里面的数据
            // processData: false,
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify({
                name, address, status
            }),
            success: function (data) {
                if (data.success){
                    console.log("新增部门成功")
                }else {
                    console.log("新增部门失败")
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