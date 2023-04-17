$(function () {
    $('#detail-body').on('change', '.detail_image', async function (e) {
        let fileStr = await changeFileIntoBase64($(this)[0].files[0])
        console.log(fileStr)
        $(this).data('base64Str', fileStr)
        let ttd = $(this).parent().parent().parent().next()
        if (fileStr) {
            ttd.find("a").removeClass("hide")
            ttd.find("small").addClass("hide")
        } else {
            ttd.find("a").addClass("hide")
            ttd.find("small").removeClass("hide")
        }
    }).on('click', '.pre-view', function () {
        const img = new Image()
        img.src = $(this).parent().parent().prev().find("input").data('base64Str')
        const newWin = window.open('', '预览', 'top=10,left=200,width=1200,height=800')
        newWin.document.write(img.outerHTML)
        newWin.document.title = "预览图片"
        newWin.document.close()
    }).on('click', '.del', function () {
        let line = $(this).parent().parent().parent()
        let count = line.find("th").text()
        if (1 === line.parent().children().length) {
            $.alert('必须至少有一个报销项!')
            return
        }
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
    }).on('input', '.detail_amount', function () {
        let total_amount = 0
        $('#detail-body').find(".detail_amount").each(function (index, element) {
            total_amount += $(this).val() * 1
        })
        $('#total_amount').val(total_amount)
    }).on('focus', '.ci,.cc,.cn', function () {
        $(this).removeClass("my-error")
    })

    $('#expense-cause').focus(function () {
        $(this).removeClass("my-error")
    })

    $('#add').click(function () {
        let target = $('#detail-body')
        let count = target.children(":last-child").find("th").text() * 1 + 1
        let tr = $('<tr></tr>')
        let th = $('<th scope="row">' + (count) + '</th>')
        let td1 = $('<td><input class="form-control detail_item ci" type="text" placeholder="费用项目"></td>')
        let td2 = $('<td><input class="form-control datepicker detail_time" type="text" placeholder="请选择日期"></td>')
        let td3 = $('<td><input class="form-control detail_type" type="text" placeholder="类别"></td>')
        let td4 = $('<td><input class="form-control detail_code cc" type="text" placeholder="发票代码"></td>')
        let td5 = $('<td><input class="form-control detail_num cn" type="text" placeholder="发票号码"></td>')
        let td6 = $('<td><input class="form-control detail_amount" type="text" placeholder="报销金额"></td>')
        let td7 = $('<td>\n' +
            '                                                    <table>\n' +
            '                                                        <tr>\n' +
            '                                                            <td>\n' +
            '                                                                <a href="javascript:void(0)" class="file text-truncate">选择文件\n' +
            '                                                                    <input type="file" class="detail_image"\n' +
            '                                                                           accept="image/jpg,image/jpeg,image/png">\n' +
            '                                                                </a>\n' +
            '                                                            </td>\n' +
            '                                                        </tr>\n' +
            '                                                        <tr>\n' +
            '                                                            <td>\n' +
            '                                                                <a class="btn btn-link pre-view hide"\n' +
            '                                                                   href="javascript:void(0)">预览</a>\n' +
            '                                                                <small class="none-tip text-truncate">未选择任何文件</small>\n' +
            '                                                            </td>\n' +
            '                                                        </tr>\n' +
            '                                                    </table>\n' +
            '                                                </td>')
        let td8 = $('<td><div class="btn-group"><a class="img-avatar img-avatar-48 bg-translucent del"><i class="mdi mdi-delete-forever fa-1-5x"></i></a></div></td>')
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
        let cause = $('#expense-cause').val()
        let total_amount = $('#total_amount').val()
        if (!cause) {
            $.confirm({
                title: '错误提示',
                content: '报销原因不能为空!',
                type: 'red',
                typeAnimated: true,
                buttons: {
                    tryAgain: {
                        text: '重试',
                        btnClass: 'btn-red',
                        action: function () {
                            $('#expense-cause').addClass("my-error")
                        }
                    },
                }
            });
            return
        }
        let flag = 1
        let report_details = []
        $('#detail-body').children("tr").each(function (rowIndex, rowElement) {
            if (flag === 0) return
            let item = ''
            let time = ''
            let type = ''
            let code = ''
            let num = ''
            let amount = ''
            let image = ''
            $(rowElement).children("td").each(function (colIndex, colElement) {
                if (flag === 0) return
                switch (colIndex) {
                    case 0:
                        item = $(colElement).children().val()
                        if (!item) {
                            $.confirm({
                                title: '错误提示',
                                content: '第' + (rowIndex + 1) + '项报销项目不能为空!',
                                type: 'red',
                                typeAnimated: true,
                                buttons: {
                                    tryAgain: {
                                        text: '重试',
                                        btnClass: 'btn-red',
                                        action: function () {
                                            let detail_body = $('#detail-body')
                                            let ctr1 = $(detail_body).children("tr")[rowIndex]
                                            $(ctr1).find(".ci").addClass("my-error")
                                        }
                                    },
                                }
                            });
                            flag = 0
                            return
                        }
                        break
                    case 1:
                        time = $(colElement).children().val()
                        break
                    case 2:
                        type = $(colElement).children().val()
                        break
                    case 3:
                        code = $(colElement).children().val()
                        break
                    case 4:
                        num = $(colElement).children().val()
                        break
                    case 5:
                        amount = $(colElement).children().val()
                        if (!amount) {
                            $.alert('报销金额不能为空!')
                            flag = 0
                            return
                        }
                        break
                    case 6:
                        image = $(colElement).find("input").data("base64Str")
                        image = image ? image.substring(image.indexOf(",") + 1) : ''
                        break
                    default:
                }
            })
            let report_detail = createReportDetail(item, time, type, code, num, amount, image)
            for (let i = 0; i < report_details.length; i++) {
                let temp_num = report_details[i].num
                let temp_code = report_details[i].code
                if (temp_num === num && temp_code === code) {
                    flag = 0
                    $.confirm({
                        title: '错误提示',
                        content: '第' + (i + 1) + '项报销单和第' + (report_details.length + 1) + '项报销单不能相同!',
                        type: 'red',
                        typeAnimated: true,
                        buttons: {
                            tryAgain: {
                                text: '重试',
                                btnClass: 'btn-red',
                                action: function () {
                                    let detail_body = $('#detail-body')
                                    let ctr1 = $(detail_body).children("tr")[i]
                                    $(ctr1).find(".cc").addClass("my-error")
                                    $(ctr1).find(".cn").addClass("my-error")
                                    let ctr2 = $(detail_body).children("tr")[report_details.length - 1]
                                    $(ctr2).find(".cc").addClass("my-error")
                                    $(ctr2).find(".cn").addClass("my-error")
                                }
                            },
                        }
                    });
                }
            }
            report_details.push(report_detail)
        })

        if (flag === 1) {
            $.ajax({
                url: '/expenseReport/addExpenseReport',
                type: 'POST',
                async: false,
                cache: false,
                dataType: 'json',
                contentType: 'application/json;charset=utf-8',
                data: JSON.stringify({
                    expenseReportDetails: report_details,
                    cause,
                    totalAmount: total_amount
                }),
                success: function (data) {
                    if (data.success) {
                        lightyear.notify('新增报销单成功~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center', '/main')
                    } else {
                        $.confirm({
                            title: '错误提示',
                            content: '第' + data.errCount + '项报销单已存在!',
                            type: 'red',
                            typeAnimated: true,
                            buttons: {
                                tryAgain: {
                                    text: '重试',
                                    btnClass: 'btn-red',
                                    action: function () {
                                        let ctr = $('#detail-body').children("tr")[data.errCount - 1]
                                        $(ctr).find(".cc").addClass("my-error")
                                        $(ctr).find(".cn").addClass("my-error")
                                    }
                                },
                            }
                        });
                    }
                }
            })
        } else {
            console.log('新增报销单失败')
        }
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
})