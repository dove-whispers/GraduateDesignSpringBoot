$(function () {
    $('#detail-body').on('change', '.detail_image', async function (e) {
        $(this).data('base64Str', await changeFileIntoBase64($(this)[0].files[0]))
    }).on('click', '.pre-view', function () {
        const img = new Image()
        img.src = $(this).prev().children().data('base64Str')
        const newWin = window.open('', '预览', 'top=10,left=300,width=1200,height=800')
        newWin.document.write(img.outerHTML)
        newWin.document.title = "预览图片"
        newWin.document.close()
    }).on('click', '.del', function () {
        let line = $(this).parent().parent().parent()
        let count = line.find("th").text()
        $.confirm({
            title: '确认删除第[' + count + ']项吗?',
            content: '此项操作无法追回',
            buttons: {
                cancel: {
                    text: '取消',
                },
                confirm: {
                    text: '确认',
                    btnClass: 'btn-blue',
                    action: function () {
                        line.nextAll().each(function (index, element) {
                            let th = $(element).find("th")
                            th.text(th.text() * 1 - 1)
                        })
                        line.remove()
                    }
                }
            }
        })
    }).on('change', '.datepicker', function () {
        $(this).data('time', $(this).val())
        console.log($(this).data('time'))
    }).on('input', '.detail_amount', function () {
        let total_amount = 0
        $('#detail-body').find(".detail_amount").each(function (index, element) {
            total_amount += $(this).val() * 1
        })
        $('#total_amount').val(total_amount)
    })

    $('#add').click(function () {
        let target = $('#detail-body')
        let count = target.children(":last-child").find("th").text() * 1 + 1
        let tr = $('<tr></tr>')
        let th = $('<th scope="row">' + (count) + '</th>')
        let td1 = $('<td><input class="form-control detail_item" type="text" placeholder="费用项目"></td>')
        let td2 = $('<td><input class="form-control datepicker detail_time" type="text" placeholder="请选择日期"></td>')
        let td3 = $('<td><input class="form-control detail_type" type="text" placeholder="类别"></td>')
        let td4 = $('<td><input class="form-control detail_code" type="text" placeholder="发票代码"></td>')
        let td5 = $('<td><input class="form-control detail_num" type="text" placeholder="发票号码"></td>')
        let td6 = $('<td><input class="form-control detail_amount" type="text" placeholder="报销金额"></td>')
        let td7 = $('<td><a href="javascript:void(0)" class="file">选择文件<input type="file" class="detail_image" accept="image/jpg,image/jpeg,image/png"><a class="btn btn-link pre-view" href="javascript:void(0)">预览</a></a></td>')
        let td8 = $('<td><div class="btn-group"><a class="btn btn-xs btn-default del"><i class="mdi mdi-window-close"></i></a></div></td>')
        tr.append(th).append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7).append(td8)
        target.append(tr)
        td2.datepicker({
            endDate: new Date(),
            format: "yyyy-mm-dd",
            autoclose: true,
            todayHighlight: true,
            language: 'zh-CN',
            orientation: 'button',
        })
    })

    $('.datepicker').datepicker({
        endDate: new Date(),
        format: "yyyy-mm-dd",
        autoclose: true,
        todayHighlight: true,
        language: 'zh-CN',
        orientation: 'button',
    })

    // $(".date-picker").datepicker("update", new Date(data.inputDate));

    $('#submit').click(function () {
        $('#detail-body').find("tr").each(function (rowIndex, rowElement) {
            let detail_data = []
            $(rowElement).find("td").each(function (colIndex, colElement) {
                console.log("行:"+rowIndex+",列:"+colIndex)
                console.log(colElement)
                // detail_data.push($(colElement).children())
            })
            let report_detail = createReportDetail()
        })
    })

    function createReportDetail(item, time, type, code, num, amount, image) {
        let detail = {};
        detail.item = item;
        detail.time = time;
        detail.type = type;
        detail.code = code;
        detail.num = num;
        detail.amount = amount;
        detail.image = image;
        return detail;
    }

    function changeFileIntoBase64(file) {
        return new Promise((resolve, reject) => {
            const fr = new FileReader();
            fr.readAsDataURL(file);
            fr.onload = (result) => {
                const base64Str = result.currentTarget.result;
                resolve(base64Str);
            };
        });
    }

    function getFileSize(size) {//把字节转换成正常文件大小
        if (!size) return "";
        let num = 1024.00; //byte
        if (size < num)
            return size + "B";
        if (size < Math.pow(num, 2))
            return (size / num).toFixed(2) + "KB"; //kb
        if (size < Math.pow(num, 3))
            return (size / Math.pow(num, 2)).toFixed(2) + "MB"; //M
        if (size < Math.pow(num, 4))
            return (size / Math.pow(num, 3)).toFixed(2) + "G"; //G
        return (size / Math.pow(num, 4)).toFixed(2) + "T"; //T
    }
})