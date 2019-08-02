//验证值是否为空
function verify(value){
	if(value.replace(/\s+/g,"").length==0){
		return true;
	}else{
		return false;
	}
}
//验证值是否只包含字母与数字，注册时账号验证使用
function checknum(value) {
    var Regx = /^[A-Za-z0-9]*$/;
    if (Regx.test(value)) {
        return true;
    }else {
        return false;
    }
}



function getTime(){
	$.ajax({
	   type: "POST",
	   url: "../account/getTime",
	   success: function(msg){
		   $("#getTime").text("当前日期 : " + msg);
	   }
	});
}

$(function() {
	var int=self.setInterval("getTime()",10000);
});