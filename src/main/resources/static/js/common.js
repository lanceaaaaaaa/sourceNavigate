
/** 表单序列化成json字符串的方法  */
function form2JsonString(formId) {
    var paramArray = $('#' + formId).serializeArray();
    /*请求参数转json对象*/
    var jsonObj={};
    $(paramArray).each(function(){
        jsonObj[this.name]=this.value;
    });
    // json对象再转换成json字符串
    return JSON.stringify(jsonObj);
}


function makeTippy(node, text, contentEleId){
    var ref = node.popperRef();

    // unfortunately, a dummy element must be passed
    // as tippy only accepts a dom element as the target
    // https://github.com/atomiks/tippyjs/issues/661
    var dummyDomEle = document.createElement('div');

    var tip = tippy( dummyDomEle, {
        onCreate: function(instance){ // mandatory
            // patch the tippy's popper reference so positioning works
            // https://atomiks.github.io/tippyjs/misc/#custom-position
            instance.popperInstance.reference = ref;
        },
        lazy: false, // mandatory
        trigger: 'manual', // mandatory

        // dom element inside the tippy:
        content: function(){ // function can be better for performance
            var div = document.createElement('div');
            div.innerHTML = text;
            div.id = contentEleId;
            return div;
        },

        // your own preferences:
        arrow: true,
        placement: 'bottom',
        hideOnClick: false,
        multiple: false,
        sticky: true
    } );

    return tip;
};

function getDialog() {
    try {
        var dialog = top.dialog.get(window);
    } catch (e) {
        $('body').append(
            '<p><strong>Error:</strong> 跨域无法无法操作 iframe 对象</p>' +
            '<p>chrome 浏览器本地会认为跨域，请使用 http 方式访问当前页面</p>'
        );
        return;
    }
    return dialog;
}

function getStrAppear(str,flagStr) {
    var newStr=str.replace(new RegExp(flagStr,"g"),"");
    return (str.length-newStr.length)/flagStr.length;
}

