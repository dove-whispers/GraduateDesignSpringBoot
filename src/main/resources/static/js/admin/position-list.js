$(function () {
    //列表
    let wrap = $('.position-wrap')
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
            request_condition.positionName = $(this).val()
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

    //获取职位列表
    function getList(data) {
        $.ajax({
            url: '/position/getList',
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
                + '<td data-toggle="tooltip" title="' + item.positionName + '">' + item.positionName + '</td>'
                + '<td data-toggle="tooltip" title="' + item.createTime + '">' + item.createTime + '</td>'
                + positionStatus(item.status)
                + '<td>'
                + '<div class="btn-group">'
                + '<a class="btn btn-xs btn-default" href="/position/goPositionEdit?edit=true&positionId=' + (item.positionId) + '" title="编辑" data-toggle="tooltip"><i class="mdi mdi-pencil"></i></a>'
                + '<a class="btn btn-xs btn-default" href="/position/goPosition?positionId=' + (item.positionId) + '" title="查看" data-toggle="tooltip"><i class="mdi mdi-eye"></i></a>'
                + updatePositionStatus(item.positionId, item.status)
                + '</div>'
                + '</td>'
                + '</tr>'
        })
        wrap.html(html)
    }

    function updatePositionStatus(positionId, status) {
        if (status === 1) {
            return '<a class="btn btn-xs btn-default position-status-btn" href="#!" title="修改状态" data-id=' + positionId + ' data-status=' + status + ' data-toggle="tooltip"><i class="mdi mdi-toggle-switch"></i></a>'
        }
        return '<a class="btn btn-xs btn-default position-status-btn" href="#!" title="修改状态" data-id=' + positionId + ' data-status=' + status + ' data-toggle="tooltip"><i class="mdi mdi-toggle-switch-off"></i></a>'
    }

    wrap.on('click', 'a', function (e) {
        let target = e.currentTarget;
        if ($(this).hasClass('position-status-btn')) {
            let positionId = target.dataset.id
            let status = target.dataset.status
            $.ajax({
                url: '/position/togglePositionStatus',
                type: 'POST',
                async: false,
                cache: false,
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify({
                    positionId, status
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

    function positionStatus(status) {
        if (status === 1) {
            return '<td><font class="text-success">有效</font></td>'
        }
        return '<td><font class="text-danger">失效</font></td>'
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