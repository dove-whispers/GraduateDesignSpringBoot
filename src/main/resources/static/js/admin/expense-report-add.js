$(function () {
    let base64Str = ''
    $('#file-input').change(async function () {
        base64Str = await changeFileIntoBase64($(this)[0].files[0]);
    })

    $('#pre-view').click(function () {
        const img = new Image();
        img.src = base64Str;
        const newWin = window.open('', '预览', 'top=10,left=400,width=1000,height=900');
        newWin.document.write(img.outerHTML);
        newWin.document.title = "预览图片";
        newWin.document.close();
    })

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