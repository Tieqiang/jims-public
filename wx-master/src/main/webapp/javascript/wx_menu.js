/**
 * Created by lgx on 2016/4/25.
 */

var datas   //当前保存数据，json对象 {'button':[{...},{...}...]}
var current_menu_index=0  //当前选择的菜单，从0开始
var current_botton_index=-1  // 当前选择的子菜单，从0开始
/**
 * 保存
 */
function save(){
    if(!check_data(datas))return
    var menu = JSON.stringify(datas)
    $.post('/api/wx-menu/createMenu?menu='+encodeURIComponent(menu) ,function(res){
        if(res && res.success){
            alert('保存成功！')
            location.reload()
        } else {
            alert('保存失败！')
        }
    })
}
/**
 * 校验
 * @param data
 * @returns {boolean}
 */
function check_data(data){
    if(data && data.button){
        var buttons = data.button
        for(var i=0;i<buttons.length;i++){
            var menu = buttons[i]
            //校验菜单名称
            if(menu.name == undefined || menu.name == ''){
                add_menu_click($('#menu li .menu_li:eq('+i+')'))
                $('#sub_name_text').css('border','1px solid red')
                return false
            }
            if(menu.sub_button && menu.sub_button.length > 0){
                for(var j=0;j<menu.sub_button.length;j++){
                    var button = menu.sub_button[j]
                    if(button.name == undefined || button.name == ''){
                        add_menu_click($('#menu li .menu_li:eq('+i+')'))
                        add_click_button($('#menu li:eq('+i+') span a:eq('+j+')'))
                        $('#sub_name_text').css('border','1px solid red')
                        return false
                    }
                    if(button.url == undefined || button.url == ''){
                        $(':radio[value="2"]').prop('checked','checked')
                        set_content(2)
                        add_menu_click($('#menu li .menu_li:eq('+i+')'))
                        add_click_button($('#menu li:eq('+i+') span a:eq('+j+')'))
                        $('#url_id').css('border','1px solid red')
                        return false
                    }
                }
            } else {
                if(menu.url == undefined || menu.url == ''){
                    $(':radio[value="2"]').prop('checked','checked')
                    set_content(2)
                    add_menu_click($('#menu li .menu_li:eq('+i+')'))
                    $('#url_id').css('border','1px solid red')
                    return false
                }
            }
        }
    }
    return true
}
$('#sub_name_text').focus(function(){
    $('#sub_name_text').css('border','1px solid #A9A9A9')
})
$('#url_id').focus(function(){
    $('#url_id').css('border','1px solid #A9A9A9')
})
/**
 * 删除
 */
function delete_all(){
    $.get('/api/wx-menu/deleteMenu' ,function(res){
        if(res && res.success){
            alert('删除成功！')
            location.reload()
        } else {
            alert('删除失败！')
        }
    })
}
/**
 * 初始化菜单内容
 * @param data
 */
function addLi(data){
    if(data){
        var size = data.length
        for(var i=0;i<size;i++){
            add_menu(data[i])
            var sub_buttons = data[i].sub_button
            var len = sub_buttons ? sub_buttons.length : 0
            for(var j=0;j<len;j++){
                add_button(i,sub_buttons[j])
            }
        }
    }
    change_width()
}
/**
 * 修改菜单宽度
 */
function change_width(){
    var len = $('#menu ul li').length
    $('#menu ul li').css('width',100/len+'%')
    $('.menu_li').css('width',262/len)
}
/**
 * 内容
 * @param type
 */
function set_content(type){
    if(type == 1){
        $('#to_url').css('display','none')
        $('#add_content').css('display','inline')
    } else {
        $('#add_content').css('display','none')
        $('#to_url').css('display','inline')
    }
}
/**
 * 添加菜单
 * @param data
 */
function add_menu(data){
    var c
    if(!data){
        data = {
            'name' : '菜单名称'
        }
        c = true
    }
    var size = $('#menu ul li').length
    var html = '<li><div class="menu_li" onclick="add_menu_click(this)">'
        +'<img src="/assert/images/menus/coin.png" width="5">&nbsp;<div style="display: inline-block">' + data.name + '</div></div>'
        + '<img class="line" src="/assert/images/menus/line.png" width="1" height="43">'

    html += ' <span ><div><a href="#" onclick="add_button_and_data('+(size-1)+')">+</a></div></span></li>'
    $('#menu ul li:last-child').before(html)
    var current_li = $('#menu ul li:eq('+(size-1)+')')
    $('span',current_li).animate({bottom:35},100);
    $('span',$('#menu ul li:eq('+(size-1)+')')).css("display","none");
    if(c)
        $($('.menu_li',current_li)).click()
    if(size > 2)
        $('#menu ul li:gt(2)').remove()
}
/**
 * 添加功能按钮
 * @param data
 * @param index 菜单位置，从0开始
 */
function add_button(index,data){
    var c
    if(!data){
        data = {
            'name' : '子菜单名称'
        }
        c = true
    }
    var sp = $('#menu ul li:eq('+index+') span div')
    var size = $('a',sp).length
    var html = '<a href="#" onclick="add_click_button(this)">' + (strlen(data.name)>10 ? limit_str(data.name,8)+'...' : data.name) + '</a>'
    $('a:last-child',sp).before(html)
    if(c)
        $($('a:eq('+(size-1)+')',sp)).click()
    if(size == 5)
        $('a:last-child',sp).remove()
}
/**
 * 菜单点击事件
 * @param obj
 */
function add_menu_click(obj){
    $('#menu ul li span').css("display","none");
    $('span',$(obj).parent('li')).css("display","block");
    $('#menu ul li .menu_li').css("color","#fff");
    $(obj).css('color','green')

    var parent = $(obj).parent('li')
    $('span a',parent).css('border-bottom','0')
    $('span a',parent).css('border-top','1px solid #cfcfcf')
    $('span a',parent).css('border-left','1px solid #cfcfcf')
    $('span a',parent).css('border-right','1px solid #cfcfcf')
    $('span a:last-child',parent).css('border-bottom','1px solid #cfcfcf')
    current_menu_index = $('#menu ul li .menu_li').index(obj)
    current_botton_index = -1
    change_table(0)
    $('.info-bg').show()
}
/**
 * 按钮点击事件
 * @param obj
 */
function add_click_button(obj){
    var parent = $(obj).parent('div')
    $('a',parent).css('border-bottom','0')
    $('a',parent).css('border-top','1px solid #cfcfcf')
    $('a',parent).css('border-left','1px solid #cfcfcf')
    $('a',parent).css('border-right','1px solid #cfcfcf')
    $('a:last-child',parent).css('border-bottom','1px solid #cfcfcf')
    $(obj).css('border','1px solid green')
    $('#menu ul li .menu_li').css("color","#fff");
    current_botton_index = $('#menu ul li:eq('+current_menu_index+') span a').index(obj)
    change_table(1)
    $('.info-bg').show()
}
/**
 * 添加菜单，并添加数据到datas
 */
function add_menu_and_data(){
    var menu_data = {'type':'view','name':'菜单名称'}
    datas.button.push(menu_data)
    add_menu()
    change_width()
}
/**
 * 添加子菜单，并添加数据到datas
 * @param index 需添加子菜单的菜单所在位置，从0开始
 */
function add_button_and_data(index){
    var button_data = {'type':'view','name':'子菜单名称'}
    delete datas.button[index].type
    if(datas.button[index].sub_button){
        datas.button[index].sub_button.push(button_data)
    }else {
        datas.button[index].sub_button = [button_data]
    }
    add_button(index)
}
/**
 * 删除菜单
 */
$('#delete_title a').click(function(){
    if(current_botton_index != -1){
        $('#menu ul li span:eq('+current_menu_index+') a:eq('+current_botton_index+')').remove()
        datas.button[current_menu_index].sub_button.splice(current_botton_index,1)
        if(datas.button[current_menu_index].sub_button.length == 0){
            delete datas.button[current_menu_index].sub_button
            datas.button[current_menu_index].type = 'view'
            datas.button[current_menu_index].url = 'https://weixin.qq.com/'
        }
        if($('#menu ul li:eq('+current_menu_index+') span a:last-child').html() != '+'){
            add_addbutton()
        }
    } else{
        if($('#menu ul li:last-child .menu_li').html() != '+'){
            add_addmenu()
        }
        $('#menu ul li:eq('+current_menu_index+')').remove()
        datas.button.splice(current_menu_index,1)
        change_width()
    }
    $('.info-bg').hide()
})
/**
 * 修改菜单修改界面
 * @param isSub 是否为子菜单修改界面
 */
function change_table(isSub){
    var data
    if(isSub){
        data = datas.button[current_menu_index].sub_button[current_botton_index]
        $('#delete_title a').html('删除子菜单')
        $('#sub_name').html('子菜单名称')
        $('.sub_alter:eq(1)').html('字数不超过8个汉字或16个字母')
        $('#hiddens').css('display','block')
        $('#menu_text').html('子菜单内容')
    } else {
        data = datas.button[current_menu_index]
        $('#delete_title a').html('删除菜单')
        $('#sub_name').html('菜单名称')
        $('.sub_alter:eq(1)').html('字数不超过4个汉字或8个字母')
        var size = $('#menu li span:eq('+current_menu_index+') a').length
        if(size == 1){
            $('.sub_alter:eq(0)').html('&nbsp;')
            $('#hiddens').css('display','block')
            $('#menu_text').html('菜单内容')
        } else {
            $('#hiddens').css('display','none')
            $('.sub_alter:eq(0)').html('已添加子菜单，仅可设置菜单名称。')
        }
    }
    $('#sub_name_text').val(data.name)
    if(data.name){
        $('#sub_name_text').css('border','1px solid #A9A9A9')
    }
    $('#sub_title').html(data.name)
    if($('input[name="sub_text"]:checked').val() == 2){
        $('#url_id').val(data.url)
        if(data.url){
            $('#url_id').css('border','1px solid #A9A9A9')
        }
    }
}
/**
 * 菜单（子菜单）名称框事件
 */
$('#sub_name_text').blur(function(){
    var val = $(this).val()
    if(current_botton_index != -1){
        val = limit_str(val,16)
        datas.button[current_menu_index].sub_button[current_botton_index].name = val
        $('#menu li:eq('+current_menu_index+') span a:eq('+current_botton_index+')').html(
            strlen(val)>10 ? (limit_str(val,8)+'...') : val)
    } else {
        val = limit_str(val,8)
        $('.menu_li:eq('+current_menu_index+') div').html(val)
        datas.button[current_menu_index].name = val
    }
    $('#sub_name_text').val(val)
    $('#sub_title').html(val)
})
/**
 * 菜单(子菜单)url
 */
$('#url_id').blur(function(){
    var val = $(this).val()
    if(current_botton_index != -1){
        datas.button[current_menu_index].sub_button[current_botton_index].url = val
    } else {
        datas.button[current_menu_index].url = val
    }
})
/**
 * 添加菜单添加按钮
 */
function add_addmenu(){
    var html = '<li><div class="menu_li" onclick="add_menu_and_data()">+</div></li>'
    $('#add_menu').append(html)
}
/**
 * 添加子菜单添加按钮
 */
function add_addbutton(){
    var html = '<a href="#" onclick="add_button_and_data('+current_menu_index+')">+</a>'
    $('#menu li:eq('+current_menu_index+') span div').append(html)
}
/**
 * 字符串长度，汉字长度2
 * @param str
 * @returns {number}
 */
function strlen(str){
    var len = 0;
    for (var i=0; i<str.length; i++) {
        var c = str.charCodeAt(i);
        //单字节加1
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
            len++;
        }
        else {
            len+=2;
        }
    }
    return len;
}
/**
 * 限制字符串长度，汉字长度为2
 * @param str
 * @param l 限制最大长度
 * @returns {*}
 */
function limit_str(str,l){
    var len = 0;
    for (var i=0; i<str.length; i++) {
        var c = str.charCodeAt(i);
        //单字节加1
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
            len++;
        }
        else {
            len+=2;
        }
        if(len == l){
            return str.substr(0,i+1)
        }
    }
    return str;
}