/**
 * Created by yanfeng on 2016/4/20.
 */

//只允许输入中文
function CheckChinese(id,iconId){
    var target=$("#"+id).val();
    var regex=/^([\u4e00-\u9fa5]*)$/;
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}
//只允许输入10位以内中文
function CheckChinese2(id,iconId){
    var target=$("#"+id).val();
    var regex=/^[\u4e00-\u9fa5]{0,10}$/;
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}
//只允许输入数字
function CheckNumber(id,iconId){
    var target=$("#"+id).val();
    var regex=/^([0-9]*)$/;
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}
//只允许输入中文、英文、数字和.
function CheckCEN(id,iconId){
    var target=$("#"+id).val();
    var regex=/^(([a-zA-Z0-9\u4e00-\u9fa5]+)\.*)([\u4e00-\u9fa5]*)$/;
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}
//只允许输入n位中文、英文、数字
function CheckCEN3(id,iconId,n){
    var target=$("#"+id).val();
    var regex=new RegExp("^[a-zA-Z0-9\\u4e00-\\u9fa5、]{0,"+n+"}$");
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}
//只允许输入n位以内英文，-，数字
function CheckQueryId(id,iconId){
    var s=$("#sMethod").val();
    var target=$("#"+id).val();
    if(s==0){
        var regex=/^[a-zA-Z0-9-]{0,15}$/;
        if(regex.test(target)){
            // alert('!!!');
            //$("#search").attr('disabled','false');
            $("#"+iconId).css('display', 'none');
        }else{
            //$("#search").attr('disabled','true');
            $("#"+iconId).css('display', '');
        }
    }else{
        CheckCEN2(id,iconId);
    }

}
//只允许中文，英文，数字和.
function CheckCEN2(id,iconId){
    var target=$("#"+id).val();
    var regex=/^[a-zA-Z0-9\u4e00-\u9fa5]?[a-zA-Z0-9\u4e00-\u9fa5\.]{0,25}$/;
    if(regex.test(target)){
        //$("#search").attr('disabled','false');
        $("#"+iconId).css('display', 'none');
    }else{
        //alert('!!!');
        //$("#search").attr('disabled','true');
        $("#"+iconId).css('display', '');
    }
}
//只允许中文，英文，数字和逗号感叹号问号双引号
function CheckCEN4(id,iconId){
    var target=$("#"+id).val();
    var regex=/^[a-zA-Z0-9\u4e00-\u9fa5\.，。！？“”‘’]{0,26}$/;
    if(regex.test(target)){
        //$("#search").attr('disabled','false');
        $("#"+iconId).css('display', 'none');
    }else{
        //alert('!!!');
        //$("#search").attr('disabled','true');
        $("#"+iconId).css('display', '');
    }
}
//只控制字符串长度
function CheckLength(id,iconId,n){
    var target=$("#"+id).val();
    var regex=new RegExp("^[\\s\\S]{0,"+n+"}$");///^[\s\S]{0,n}$/
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}
//只控制字符串长度
function CheckAddress(id,iconId,n){
    var target=$("#"+id).val();
    var regex=/^[\S^%&$!?]{0,50}$/;///^[\s\S]{0,n}$/
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}


//控制时间输入格式
function CheckTime(id,iconId){
    var target=$("#"+id).val();
    var regex=/^(\d{4}-\d{2}-\d{2} +\d{2}:\d{2}:\d{2})?$/;
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}
//只出现数字
function CheckNum(id,iconId){
    var target=$("#"+id).val();
    var regex=/^\d{0,10}$/;
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}
//只是正整数
function CheckNumZ(id,iconId){

    var target=$("#"+id).val();
    var regex=/^[1-9]\d{0,9}$/;
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}
//中文和数字，长度10
function CheckCN(id,iconId){
    var target=$("#"+id).val();
    var regex=/^[0-9\u4e00-\u9fa5]{0,10}$/;
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}
//模糊匹配传入参数
function CheckX(x,y){

    var regex=new RegExp("^("+x+").*$");
    if(regex.test(y)){
        return true;
    }else{
        return false;
    }
}
//模糊匹配
function CheckX2(x,y){
    var regex=new RegExp("^.*?"+x+".*?$");
    if(regex.test(y)){
        return true;
    }else{
        return false;
    }
}

function CheckPic(id,iconId){
    var target=$("#"+id).val();
    var regex= /\.(jpg|gif|png)$/i;
    if(regex.test(target)){
        $("#"+iconId).css('display', 'none');
    }else{
        $("#"+iconId).css('display', '');
    }
}