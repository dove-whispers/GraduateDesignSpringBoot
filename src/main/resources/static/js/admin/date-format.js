/**
 * 使用说明:
 * 1.  let time1 = new Date().format("yyyy-MM-dd hh:mm:ss");
 *     console.log(time1); // 2022-01-18 15:20:39
 * 2.  let time2 = new Date().format("yyyy-MM-dd");
 *     console.log(time2);  // 2022-01-18
 * 3.  var oldTime = (new Date("2022/1/18 15:26:11")).getTime(); // 1642490771000
 *     var curTime = new Date(oldTime).format("yyyy-MM-dd");
 *     console.log(curTime); // 2022-01-18
 */
Date.prototype.format = function(fmt) {
    let o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
    for(let k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length===1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
    return fmt;
}