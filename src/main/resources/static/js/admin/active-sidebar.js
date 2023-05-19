$(function () {
    const $drawer = $('ul.nav-drawer')
    $drawer.children('li').removeClass('active');
    let pathname = $(location).attr('pathname')
    let result = pathname.split('/')
    let name = result[1]
    let pname = ''
    if ('main' === name) {
        $drawer.children('li').eq(0).addClass('active')
    } else if ('department' === name) {
        $drawer.children('li').eq(3).addClass('active')
    } else if ('position' === name) {
        $drawer.children('li').eq(4).addClass('active')
    } else if ('employee' === name) {
        $drawer.children('li').eq(5).addClass('active')
    } else if ('authority' === name) {
        $drawer.children('li').eq(6).addClass('active')
    } else if ('expenseReportDetail' === name) {
        pname = result[2]
        if (-1 !== pname.indexOf('Main')) {
            $drawer.children('li').eq(0).addClass('active')
        } else if (-1 !== pname.indexOf('View')) {
            $drawer.children('li').eq(1).addClass('active')
        }
    } else if ('expenseReport' === name) {
        pname = result[2]
        if ('toViewExpenseReport' === pname) {
            $drawer.children('li').eq(1).addClass('active')
        } else if ('toAddExpenseReport' === pname) {
            $drawer.children('li').eq(2).addClass('active')
        }
    }
})