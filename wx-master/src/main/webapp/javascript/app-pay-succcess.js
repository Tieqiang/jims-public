/**
 * created by chenxy on 2016-04-17
 */
$(function () {
     $("#showTooltips").on('click',function(){});

 })
function getUrlParameter(name){
    name = name.replace(/[\[]/,"\\\[").replace(/[\]]/,"\\\]");
    var regexS = "[\\?&]"+name+"=([^&#]*)";
    var regex = new RegExp( regexS );
    var results = regex.exec(window.parent.location.href );
    if( results == null )    return "";  else {
        return results[1];
    }
}
var clinicForRegistId=getUrlParameter('clinicForRegistId');






