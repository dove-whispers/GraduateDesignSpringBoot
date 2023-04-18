$(function () {
    $('#detail-body').on('change', '.detail_image', async function (e) {
        let fileStr = await changeFileIntoBase64($(this)[0].files[0])
        let ttd = $(this).closest('td').next()
        ttd.find("img").prop('src', fileStr)
        if (fileStr) {
            ttd.find("img").removeClass("hide")
            ttd.find("span").addClass("hide")
        } else {
            ttd.find("img").addClass("hide")
            ttd.find("span").removeClass("hide")
        }
        $(this).closest('ul').prev().click()
    }).on('click', '.pre-img', function () {
        const img = new Image()
        img.src = $(this).prop('src')
        const newWin = window.open('', '预览', 'top=10,left=200,width=1200,height=800')
        newWin.document.write(img.outerHTML)
        newWin.document.title = "预览图片"
        newWin.document.close()
    }).on('click', '.del', function () {
        let line = $(this).closest('tr')
        let count = line.find("th").text()
        if (2 === line.parent().children().length) {
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
                            if (index % 2 === 1) {
                                let th = $(element).find("th")
                                th.text(th.text() * 1 - 1)
                            }
                        })
                        line.next().remove()
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
    }).on('focus', '.ci,.cc,.cn,.ca', function () {
        $(this).removeClass("my-error")
    })

    $('#expense-cause').focus(function () {
        $(this).removeClass("my-error")
    })

    $('#add').click(function () {
        let target = $('#detail-body')
        let count = target.children(":last-child").prev().find("th").text() * 1 + 1
        let tr1 = $('<tr></tr>')
        let th = $('<th rowspan="2" scope="row">' + (count) + '</th>')
        let td1 = $('<td><input class="form-control detail_item ci" type="text" placeholder="费用项目"></td>')
        let td2 = $('<td><input class="form-control datepicker detail_time" type="text" placeholder="请选择日期"></td>')
        let td3 = $('<td><input class="form-control detail_type" type="text" placeholder="类别"></td>')
        let td4 = $('<td><input class="form-control detail_code cc" type="text" placeholder="发票代码"></td>')
        let td5 = $('<td><input class="form-control detail_num cn" type="text" placeholder="发票号码"></td>')
        let td6 = $('<td><input class="form-control detail_amount ca" type="text" placeholder="报销金额"></td>')
        let td7 = $('<td rowspan="2"><div class="btn-group"><a class="img-avatar img-avatar-48 bg-translucent del"><i class="mdi mdi-delete-forever fa-1-5x"></i></a></div></td>')
        tr1.append(th).append(td1).append(td2).append(td3).append(td4).append(td5).append(td6).append(td7)
        let tr2 = $('<tr></tr>')
        let td21 = $('<td colspan="3"><label class="col-xs-12">备注:</label><textarea class="col-xs-12" rows="4" style="resize:none"></textarea></td>')
        let td22 = $('<td>'
            + '<label class="col-xs-12">发票:</label>'
            + '<div class="btn-group">'
            + '    <button type="button" class="btn btn-primary btn-round dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">上传发票图片 <span class="caret"></span></button>'
            + '    <ul class="dropdown-menu">'
            + '        <li>'
            + '            <a class="file" href="javascript:void(0)">本地上传'
            + '                <input type="file" class="detail_image" accept="image/jpg,image/jpeg,image/png">'
            + '            </a>'
            + '        </li>'
            + '        <li role="separator" class="divider"></li>'
            + '        <li><a href="javascript:void(0)">拍照上传</a></li>'
            + '    </ul>'
            + '</div>'
            + '</td>')
        let td23 = $('<td colspan="2"><div><span>暂无图片</span><img class="hide pre-img" src="" alt="点击放大" style="max-height: 200px"/></div></td>')
        tr2.append(td21).append(td22).append(td23)
        target.append(tr1).append(tr2)
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
        let item = ''
        let time = ''
        let type = ''
        let code = ''
        let num = ''
        let amount = ''
        let comment = ''
        let image = ''
        $('#detail-body').children("tr").each(function (rowIndex, rowElement) {
            if (flag === 0) return
            if (rowIndex % 2 === 0) {
                item = ''
                time = ''
                type = ''
                code = ''
                num = ''
                amount = ''
                comment = ''
                image = ''
                $(rowElement).children("td").each(function (colIndex, colElement) {
                    if (flag === 0) return
                    switch (colIndex) {
                        case 0:
                            item = $(colElement).children().val()
                            if (!item) {
                                $.confirm({
                                    title: '错误提示',
                                    content: '第' + (Math.floor(rowIndex / 2) + 1) + '项报销项目不能为空!',
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
                            if (!code) {
                                $.confirm({
                                    title: '错误提示',
                                    content: '第' + (Math.floor(rowIndex / 2) + 1) + '项发票代码不能为空!',
                                    type: 'red',
                                    typeAnimated: true,
                                    buttons: {
                                        tryAgain: {
                                            text: '重试',
                                            btnClass: 'btn-red',
                                            action: function () {
                                                let detail_body = $('#detail-body')
                                                let ctr1 = $(detail_body).children("tr")[rowIndex]
                                                $(ctr1).find(".cc").addClass("my-error")
                                            }
                                        },
                                    }
                                });
                                flag = 0
                                return
                            }
                            break
                        case 4:
                            num = $(colElement).children().val()
                            if (!num) {
                                $.confirm({
                                    title: '错误提示',
                                    content: '第' + (Math.floor(rowIndex / 2) + 1) + '项发票号码不能为空!',
                                    type: 'red',
                                    typeAnimated: true,
                                    buttons: {
                                        tryAgain: {
                                            text: '重试',
                                            btnClass: 'btn-red',
                                            action: function () {
                                                let detail_body = $('#detail-body')
                                                let ctr1 = $(detail_body).children("tr")[rowIndex]
                                                $(ctr1).find(".cn").addClass("my-error")
                                            }
                                        },
                                    }
                                });
                                flag = 0
                                return
                            }
                            break
                        case 5:
                            amount = $(colElement).children().val()
                            if (!amount) {
                                $.confirm({
                                    title: '错误提示',
                                    content: '第' + (Math.floor(rowIndex / 2) + 1) + '项报销金额不能为空!',
                                    type: 'red',
                                    typeAnimated: true,
                                    buttons: {
                                        tryAgain: {
                                            text: '重试',
                                            btnClass: 'btn-red',
                                            action: function () {
                                                let detail_body = $('#detail-body')
                                                let ctr1 = $(detail_body).children("tr")[rowIndex]
                                                $(ctr1).find(".ca").addClass("my-error")
                                            }
                                        },
                                    }
                                });
                                flag = 0
                                return
                            }
                            break
                        default:
                    }
                })
            } else {
                $(rowElement).children("td").each(function (colIndex, colElement) {
                    if (colIndex === 0) {
                        comment = $(colElement).find('textarea').val()
                    } else if (colIndex === 2) {
                        image = $(colElement).find("img").prop('src')
                        image = image.length > 100 ? image.substring(image.indexOf(",") + 1) : ''
                    }
                })
            }
            if (rowIndex % 2 === 1) {
                let report_detail = createReportDetail(item, time, type, code, num, amount, comment, image)
                for (let i = 0; i < report_details.length; i++) {
                    let temp_code = report_details[i].code
                    let temp_num = report_details[i].num
                    if (temp_code === code && temp_num === num) {
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
                                        let ctr1 = $(detail_body).children("tr")[i * 2]
                                        $(ctr1).find(".cc").addClass("my-error")
                                        $(ctr1).find(".cn").addClass("my-error")
                                        let ctr2 = $(detail_body).children("tr")[(report_details.length - 1) * 2]
                                        $(ctr2).find(".cc").addClass("my-error")
                                        $(ctr2).find(".cn").addClass("my-error")
                                    }
                                },
                            }
                        });
                    }
                }
                report_details.push(report_detail)
            }
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
                        // lightyear.notify('新增报销单成功~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center', '/main')
                        lightyear.notify('新增报销单成功~', 'success', 2000, 'mdi mdi-emoticon-happy', 'top', 'center')
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
                                        let ctr = $('#detail-body').children("tr")[(data.errCount - 1) * 2]
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

    function createReportDetail(item, time, type, code, num, amount, comment, image) {
        let detail = {};
        detail.item = item;
        detail.time = time;
        detail.type = type;
        detail.code = code;
        detail.num = num;
        detail.amount = amount;
        detail.comment = comment;
        detail.image = image;
        return detail;
    }

    function changeFileIntoBase64(file) {
        return new Promise((resolve, reject) => {
            const fr = new FileReader()
            fr.readAsDataURL(file)
            fr.onload = () => {
                const base64Str = fr.result
                resolve(base64Str)
            }
        })
    }
})