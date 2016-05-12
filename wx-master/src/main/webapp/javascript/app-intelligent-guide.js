/**
 * Created by cxy on 2016/5/9.
 */

/**
 * check
 */
function check(){
     var age=$("#age").val();
    if(age<18 || age>100){
        alert("年龄必须满18周岁！");
    }else{
        // 1 男性 0 女性
        var sexValue=$("input[type='radio']:checked").val();
        window.location.href="api/wx-service/select-body?age="+age+"&sexValue="+sexValue;
    }

}
