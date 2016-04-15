/**
 * Created by heren on 2015/9/11.
 */

//base 64 加密开始
;
(function ($) {

    var b64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/",
        a256 = '',
        r64 = [256],
        r256 = [256],
        i = 0;

    var UTF8 = {

        /**
         * Encode multi-byte Unicode string into utf-8 multiple single-byte characters
         * (BMP / basic multilingual plane only)
         *
         * Chars in range U+0080 - U+07FF are encoded in 2 chars, U+0800 - U+FFFF in 3 chars
         *
         * @param {String} strUni Unicode string to be encoded as UTF-8
         * @returns {String} encoded string
         */
        encode: function (strUni) {
            // use regular expressions & String.replace callback function for better efficiency
            // than procedural approaches
            var strUtf = strUni.replace(/[\u0080-\u07ff]/g, // U+0080 - U+07FF => 2 bytes 110yyyyy, 10zzzzzz
                function (c) {
                    var cc = c.charCodeAt(0);
                    return String.fromCharCode(0xc0 | cc >> 6, 0x80 | cc & 0x3f);
                })
                .replace(/[\u0800-\uffff]/g, // U+0800 - U+FFFF => 3 bytes 1110xxxx, 10yyyyyy, 10zzzzzz
                function (c) {
                    var cc = c.charCodeAt(0);
                    return String.fromCharCode(0xe0 | cc >> 12, 0x80 | cc >> 6 & 0x3F, 0x80 | cc & 0x3f);
                });
            return strUtf;
        },

        /**
         * Decode utf-8 encoded string back into multi-byte Unicode characters
         *
         * @param {String} strUtf UTF-8 string to be decoded back to Unicode
         * @returns {String} decoded string
         */
        decode: function (strUtf) {
            // note: decode 3-byte chars first as decoded 2-byte strings could appear to be 3-byte char!
            var strUni = strUtf.replace(/[\u00e0-\u00ef][\u0080-\u00bf][\u0080-\u00bf]/g, // 3-byte chars
                function (c) { // (note parentheses for precence)
                    var cc = ((c.charCodeAt(0) & 0x0f) << 12) | ((c.charCodeAt(1) & 0x3f) << 6) | (c.charCodeAt(2) & 0x3f);
                    return String.fromCharCode(cc);
                })
                .replace(/[\u00c0-\u00df][\u0080-\u00bf]/g, // 2-byte chars
                function (c) { // (note parentheses for precence)
                    var cc = (c.charCodeAt(0) & 0x1f) << 6 | c.charCodeAt(1) & 0x3f;
                    return String.fromCharCode(cc);
                });
            return strUni;
        }
    };

    while (i < 256) {
        var c = String.fromCharCode(i);
        a256 += c;
        r256[i] = i;
        r64[i] = b64.indexOf(c);
        ++i;
    }

    function code(s, discard, alpha, beta, w1, w2) {
        s = String(s);
        var buffer = 0,
            i = 0,
            length = s.length,
            result = '',
            bitsInBuffer = 0;

        while (i < length) {
            var c = s.charCodeAt(i);
            c = c < 256 ? alpha[c] : -1;

            buffer = (buffer << w1) + c;
            bitsInBuffer += w1;

            while (bitsInBuffer >= w2) {
                bitsInBuffer -= w2;
                var tmp = buffer >> bitsInBuffer;
                result += beta.charAt(tmp);
                buffer ^= tmp << bitsInBuffer;
            }
            ++i;
        }
        if (!discard && bitsInBuffer > 0) result += beta.charAt(buffer << (w2 - bitsInBuffer));
        return result;
    }

    var Plugin = $.base64 = function (dir, input, encode) {
        return input ? Plugin[dir](input, encode) : dir ? null : this;
    };

    Plugin.btoa = Plugin.encode = function (plain, utf8encode) {
        plain = Plugin.raw === false || Plugin.utf8encode || utf8encode ? UTF8.encode(plain) : plain;
        plain = code(plain, false, r256, b64, 8, 6);
        return plain + '===='.slice((plain.length % 4) || 4);
    };

    Plugin.atob = Plugin.decode = function (coded, utf8decode) {
        coded = String(coded).split('=');
        var i = coded.length;
        do {
            --i;
            coded[i] = code(coded[i], true, r64, a256, 6, 8);
        } while (i > 0);
        coded = coded.join('');
        return Plugin.raw === false || Plugin.utf8decode || utf8decode ? UTF8.decode(coded) : coded;
    };
}(jQuery));
//base 64 加密结束

$.fn.datebox.defaults.formatter = function(date){
    var y = date.getFullYear();
    var m = date.getMonth()+1;
    var d = date.getDate();
    if(m<10){
        return y+"-0"+m+"-"+d
    }
    return y+'-'+m+'-'+d;
}


$.extend({
    postJSON: function (url, data, callback, error) {
        return jQuery.ajax({
            'type': 'POST',
            'url': url,
            'contentType': 'application/json',
            'data': JSON.stringify(data),
            'dataType': 'json',
            'success': callback,
            'error': error
        });
    },
    putJSON: function (url, data, callback, error) {
        return jQuery.ajax({
            'type': 'PUT',
            'url': url,
            'contentType': 'application/json',
            'data': JSON.stringify(data),
            'dataType': 'json',
            'success': callback,
            'error': error
        });
    },
    delete: function (url, callback) {
        return jQuery.ajax({
            'type': 'DELETE',
            'url': url,
            'success': callback
        });
    },
    getJSON: function (url, data, callback, error) {
        return jQuery.ajax({
            'type': 'GET',
            'url': url,
            'contentType': 'application/json',
            'data': JSON.stringify(data),
            'dataType': 'json',
            'success': callback
        });
    },

    startWith: function (str, patten) {

        var pLenth = patten.length;
        var strLength = str.length;
        if (strLength <= 0 || !str || !patten || pLenth <= 0) {
            return false;
        }

        if (str.substr(0, pLenth) == patten) {
            return true;
        } else {
            return false;
        }
    },

    endWith: function (str, patten) {
        var pLenth = patten.length;
        var strLength = str.length;
        if (strLength <= 0 || !str || !patten || pLenth <= 0) {
            return false;
        }

        if (str.substr(strLength - pLenth) == patten) {
            return true;
        } else {
            return false;
        }
    }


})


$(function () {
    function pagerFilter(data) {
        if ($.isArray(data)) {    // is array
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var state = dg.data('datagrid');
        var opts = dg.datagrid('options');
        if (!state.allRows) {
            state.allRows = (data.rows);
        }
        var start = (opts.pageNumber - 1) * parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = $.extend(true, [], state.allRows.slice(start, end));
        return data;
    }

    var loadDataMethod = $.fn.datagrid.methods.loadData;
    $.extend($.fn.datagrid.methods, {
        clientPaging: function (jq) {
            return jq.each(function () {
                var dg = $(this);
                var state = dg.data('datagrid');
                var opts = state.options;
                opts.loadFilter = pagerFilter;
                var onBeforeLoad = opts.onBeforeLoad;
                opts.onBeforeLoad = function (param) {
                    state.allRows = null;
                    return onBeforeLoad.call(this, param);
                }
                dg.datagrid('getPager').pagination({
                    onSelectPage: function (pageNum, pageSize) {
                        opts.pageNumber = pageNum;
                        opts.pageSize = pageSize;
                        $(this).pagination('refresh', {
                            pageNumber: pageNum,
                            pageSize: pageSize
                        });
                        dg.datagrid('loadData', state.allRows);
                    }
                });
                $(this).datagrid('loadData', state.data);
                if (opts.url) {
                    $(this).datagrid('reload');
                }
            });
        },
        loadData: function (jq, data) {
            jq.each(function () {
                $(this).data('datagrid').allRows = null;
            });
            return loadDataMethod.call($.fn.datagrid.methods, jq, data);
        },
        getAllRows: function (jq) {
            return jq.data('datagrid').allRows;
        }
    })

    /**
     * linkbutton方法扩展
     * @param {Object} jq
     */
    $.extend($.fn.linkbutton.methods, {
        /**
         * 激活选项（覆盖重写）
         * @param {Object} jq
         */
        enable: function(jq){
            return jq.each(function(){
                var state = $.data(this, 'linkbutton');
                if ($(this).hasClass('l-btn-disabled')) {
                    var itemData = state._eventsStore;
                    //恢复超链接
                    if (itemData.href) {
                        $(this).attr("href", itemData.href);
                    }
                    //回复点击事件
                    if (itemData.onclicks) {
                        for (var j = 0; j < itemData.onclicks.length; j++) {
                            $(this).bind('click', itemData.onclicks[j]);
                        }
                    }
                    //设置target为null，清空存储的事件处理程序
                    itemData.target = null;
                    itemData.onclicks = [];
                    $(this).removeClass('l-btn-disabled');
                }
            });
        },
        /**
         * 禁用选项（覆盖重写）
         * @param {Object} jq
         */
        disable: function(jq){
            return jq.each(function(){
                var state = $.data(this, 'linkbutton');
                if (!state._eventsStore)
                    state._eventsStore = {};
                if (!$(this).hasClass('l-btn-disabled')) {
                    var eventsStore = {};
                    eventsStore.target = this;
                    eventsStore.onclicks = [];
                    //处理超链接
                    var strHref = $(this).attr("href");
                    if (strHref) {
                        eventsStore.href = strHref;
                        $(this).attr("href", "javascript:void(0)");
                    }
                    //处理直接耦合绑定到onclick属性上的事件
                    var onclickStr = $(this).attr("onclick");
                    if (onclickStr && onclickStr != "") {
                        eventsStore.onclicks[eventsStore.onclicks.length] = new Function(onclickStr);
                        $(this).attr("onclick", "");
                    }
                    //处理使用jquery绑定的事件
                    var eventDatas = $(this).data("events") || $._data(this, 'events');
                    if (eventDatas["click"]) {
                        var eventData = eventDatas["click"];
                        for (var i = 0; i < eventData.length; i++) {
                            if (eventData[i].namespace != "menu") {
                                eventsStore.onclicks[eventsStore.onclicks.length] = eventData[i]["handler"];
                                $(this).unbind('click', eventData[i]["handler"]);
                                i--;
                            }
                        }
                    }
                    state._eventsStore = eventsStore;
                    $(this).addClass('l-btn-disabled');
                }
            });
        }
    });
    /**
     * 监控input值的改变
     * @param callback
     * @returns {*}
     */
    $.fn.watch = function (callback) {
        return this.each(function () {
            //缓存以前的值
            $.data(this, 'originVal', $(this).val());

            //event
            $(this).on('keyup paste', function () {
                var originVal = $(this, 'originVal');
                var currentVal = $(this).val();

                if (originVal !== currentVal) {
                    $.data(this, 'originVal', $(this).val());
                    callback(currentVal);
                }
            });
        });
    }
});

var common = function () {
    this.hospitalId = "";
    this.hospitalName = "";
    this.storage = "";
    this.storageCode = "";
    this.defaultReportPath = "";
}

var config = new common();
//
config.authentication = 'YWRtaW46YWRtaW4=';
config.defaultSupplier = "供应室";
config.appId = "appId123456";
config.hospitalId = "4028862d4fcf2590014fcf9aef480016";
config.hospitalName = "双滦区人民医院";
config.storage = "五金库";
config.storageCode = '1503';
config.loginName = '123';
config.loginId = '11'
config.moduleId = '402886f350a6bd4f0150a6c0c47c0000';
config.moduleName = "消耗品管理系统"
config.exportClass = "'发放出库','批量出库','退账入库'";
config.reportServerIp = '192.168.6.68';
config.reportServerPort = '8080';
config.reportServerName = 'webReport';
config.reportServerResousePath = 'ReportServer?reportlet=';
config.defaultReportPath = "http://" + config.reportServerIp + ":" + config.reportServerPort + "/" + config.reportServerName + "/" + config.reportServerResousePath;
//写cookies
function setCookie(name, value) {
    var Days = 1;
    var exp = new Date();
    exp.setTime(exp.getTime() + Days * 24 * 60 * 60 * 1000);
    document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
//自定义过期时间 (s20是代表20秒
//h是指小时，如12小时则是：h12
//d是天数，30天则：d30)
function getSec(str) {
    alert(str);
    var str1 = str.substring(1, str.length) * 1;
    var str2 = str.substring(0, 1);
    if (str2 == "s") {
        return str1 * 1000;
    }
    else if (str2 == "h") {
        return str1 * 60 * 60 * 1000;
    }
    else if (str2 == "d") {
        return str1 * 24 * 60 * 60 * 1000;
    }
}
//读取cookies
function getCookie(name) {
    var arr, reg = new RegExp("(^| )" + name + "=([^;]*)(;|$)");

    if (arr = document.cookie.match(reg))

        return unescape(arr[2]);
    else
        return null;
}

//删除cookies
function delCookie(name) {
    var exp = new Date();
    exp.setTime(exp.getTime() - 1);
    var cVal = getCookie(name);
    if (cVal != null)
        document.cookie = name + "=" + cVal + ";expires=" + exp.toGMTString();
}


//--------权限认证
$.base64.utf8encode = true;

$.ajaxSetup({
    beforeSend: function (req) {
        //var result=$.base64.btoa('000LRF:1') ;//加密
        //req.setRequestHeader('Authorization', config.authentication);
        //result = $.base64.atob(config.authentication)
    }
});


/**
 * Created by heren on 2015/10/29.
 */
function cjkEncode(text) {
    if (text == null) {
        return "";
    }
    var newText = "";
    for (var i = 0; i < text.length; i++) {
        var code = text.charCodeAt(i);
        if (code >= 128 || code == 91 || code == 93) {  //91 is "[", 93 is "]".
            newText += "[" + code.toString(16) + "]";
        } else {
            newText += text.charAt(i);
        }
    }
    return newText;
}


//多线程
function Queue(n) {
    n = parseInt(n || 1, 10);
    return (n && n > 0) ? new Queue.prototype.init(n) : null;
}

Queue.prototype = {
    init: function (n) {
        this.threads = [];
        this.taskList = [];

        while (n--) {
            this.threads.push(new this.Thread)
        }
    },

    /**
     * @callback {Fucntion} promise对象done时的回调函数，它的返回值必须是一个promise对象
     */
    push: function (callback) {
        if (typeof callback !== 'function') return;

        var index = this.indexOfIdle();

        if (index != -1) {
            this.threads[index].idle(callback)
            try {
                console.log('Thread-' + (index + 1) + ' accept the task!')
            } catch (e) {
            }
        }
        else {
            this.taskList.push(callback);

            for (var i = 0, l = this.threads.length; i < l; i++) {

                (function (thread, self, id) {
                    thread.idle(function () {
                        if (self.taskList.length > 0) {
                            try {
                                console.log('Thread-' + (id + 1) + ' accept the task!')
                            } catch (e) {
                            }

                            var promise = self.taskList.pop()();    // 正确的返回值应该是一个promise对象
                            return promise.promise ? promise : thread.promise;
                        } else {
                            return thread.promise
                        }
                    })
                })(this.threads[i], this, i);

            }
        }
    },
    indexOfIdle: function () {
        var threads = this.threads,
            thread = null,
            index = -1;

        for (var i = 0, l = threads.length; i < l; i++) {
            thread = threads[i];

            if (thread.promise.state() === 'resolved') {
                index = i;
                break;
            }
        }

        return index;
    },
    Thread: function () {
        this.promise = $.Deferred().resolve().promise();

        this.idle = function (callback) {
            this.promise = this.promise.then(callback)
        }
    }
};

Queue.prototype.init.prototype = Queue.prototype;

$.messager.defaults.ok = "确定"
$.messager.defaults.cancel = "取消"

//扩展验证输入框
$.extend($.fn.validatebox.defaults.rules, {
    idcard: {// 验证身份证
        validator: function (value) {
            return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
        },
        message: '身份证号码格式不正确'
    },
    minLength: {
        validator: function (value, param) {
            return value.length >= param[0];
        },
        message: '请输入至少（2）个字符.'
    },
    length: {
        validator: function (value, param) {
            var len = $.trim(value).length;
            return len >= param[0] && len <= param[1];
        },
        message: "输入内容长度必须介于{0}和{1}之间."
    },
    phone: {// 验证电话号码
        validator: function (value) {
            return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '格式不正确,请使用下面格式:020-88888888'
    },
    mobile: {// 验证手机号码
        validator: function (value) {
            return /^(13|15|18)\d{9}$/i.test(value);
        },
        message: '手机号码格式不正确'
    },
    intOrFloat: {// 验证整数或小数
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '请输入数字，并确保格式正确'
    },
    currency: {// 验证货币
        validator: function (value) {
            return /^\d+(\.\d+)?$/i.test(value);
        },
        message: '货币格式不正确'
    },
    qq: {// 验证QQ,从10000开始
        validator: function (value) {
            return /^[1-9]\d{4,9}$/i.test(value);
        },
        message: 'QQ号码格式不正确'
    },
    integer: {// 验证整数 可正负数
        validator: function (value) {
            //return /^[+]?[1-9]+\d*$/i.test(value);

            return /^([+]?[0-9])|([-]?[0-9])+\d*$/i.test(value);
        },
        message: '请输入整数'
    },
    age: {// 验证年龄
        validator: function (value) {
            return /^(?:[1-9][0-9]?|1[01][0-9]|120)$/i.test(value);
        },
        message: '年龄必须是0到120之间的整数'
    },

    chinese: {// 验证中文
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value);
        },
        message: '请输入中文'
    },
    english: {// 验证英语
        validator: function (value) {
            return /^[A-Za-z]+$/i.test(value);
        },
        message: '请输入英文'
    },
    unnormal: {// 验证是否包含空格和非法字符
        validator: function (value) {
            return /.+/i.test(value);
        },
        message: '输入值不能为空和包含其他非法字符'
    },
    username: {// 验证用户名
        validator: function (value) {
            return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
        },
        message: '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
    },
    faxno: {// 验证传真
        validator: function (value) {
            //            return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/i.test(value);
            return /^((\d2,3)|(\d{3}\-))?(0\d2,3|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i.test(value);
        },
        message: '传真号码不正确'
    },
    zip: {// 验证邮政编码
        validator: function (value) {
            return /^[1-9]\d{5}$/i.test(value);
        },
        message: '邮政编码格式不正确'
    },
    ip: {// 验证IP地址
        validator: function (value) {
            return /d+.d+.d+.d+/i.test(value);
        },
        message: 'IP地址格式不正确'
    },
    name: {// 验证姓名，可以是中文或英文
        validator: function (value) {
            return /^[\Α-\￥]+$/i.test(value) | /^\w+[\w\s]+\w+$/i.test(value);
        },
        message: '请输入姓名'
    },
    date: {// 验证姓名，可以是中文或英文
        validator: function (value) {
            //格式yyyy-MM-dd或yyyy-M-d
            return /^(?:(?!0000)[0-9]{4}([-]?)(?:(?:0?[1-9]|1[0-2])\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\1(?:29|30)|(?:0?[13578]|1[02])\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-]?)0?2\2(?:29))$/i.test(value);
        },
        message: '清输入合适的日期格式'
    },
    msn: {
        validator: function (value) {
            return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/.test(value);
        },
        message: '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
    },
    same: {
        validator: function (value, param) {
            if ($("#" + param[0]).val() != "" && value != "") {
                return $("#" + param[0]).val() == value;
            } else {
                return true;
            }
        },
        message: '两次输入的密码不一致！'
    }
});