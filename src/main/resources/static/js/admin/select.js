$(function () {
    let queryActiveDepartmentListUrl = '/department/queryActiveDepartmentList'
    $.ajax({
        url: queryActiveDepartmentListUrl,
        type: 'POST',
        async: false,
        cache: false,
        dataType: 'json',
        success: function (data) {
            console.log(data)
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

    $('#department-select').change(function () {
        console.log($(this).val())
    })
})