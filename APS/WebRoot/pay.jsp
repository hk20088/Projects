<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>


<!doctype html>
<html>
<head>
<title>支付成功界面</title>
<style>
*{margin:0; padding:0;}
body{background:url(img/beijing.png) no-repeat center center; background-size:100%;}
.zhifu{width:50%; margin:18% auto; text-align:center;}
.clear{clear:both;}
.chengong{margin:0 auto;}
table{margin:50px auto;}
.jixu{margin-top:10%;}
.jixu input{background:#199900;color:#fff; border-radius:5px; text-align:center;width:200px;line-height:60px; font-size:24px;}
.chengong{width:70%;}
.failure{width:70%;margin:0 auto;}
.cancel{width:70%;margin:0 auto;}
.chengong img{width:100%;}
.failure img{width:100%;}
.cancel img{width:100%;}
.quxiao{width:100%; margin:0 auto; text-align:center;}
.cancel input{background:#199900;color:#fff; border-radius:5px; text-align:center;width:200px;line-height:60px; font-size:24px;}
#failure p{margin:50px auto;}
input{border:5px solid #199900; background:url(ddd.png)}
input:focus{ border: 5px solid #ccc;} 
#success,#failure,#cancel{display:none;}
</style>
<Script language="javascript">  
   window.onload=function(){ 
//提取定义action变量
		function getvl(name) {
		var reg = new RegExp("(^|\\?|&)"+ name +"=([^&]*)(\\s|&|$)", "i");
		if (reg.test(location.href)) return unescape(RegExp.$2.replace(/\+/g, " "));
		return "";
		};
      var canshu=getvl('action');//调用传入需要获取的参数
//判断action的值
      var oSucc=document.getElementById('success');
      var oFai=document.getElementById('failure');
      var oCanc=document.getElementById('cancel');
    
	  if(canshu=='success'){
		  oSucc.style.display='block';
		  oFai.style.display='none';
		  oCanc.style.display='none';
		  }
	  else if(canshu=='failure'){
		  oSucc.style.display='none';
		  oFai.style.display='block';
		  oCanc.style.display='none';
		  }
	  else if(canshu=='cancel'){		  
		  oSucc.style.display='none';
		  oFai.style.display='none';
		  oCanc.style.display='block';
		  }
      else {
		  oSucc.style.display='none';
		  oFai.style.display='none';
		  oCanc.style.display='none';
		  }
}

//聚焦
	$(document).ready(function(){
    $("input[@type='image'], input[@type='text'], input[@type='button'], textarea").focus(function(){$(this).addClass("focus")}).blur(function(){$(this).removeClass("focus")});
     }) 
//关闭当前页面
function shut(){  
    window.opener=null;  
    window.open('','_self');  
    window.close();  
} 
</script>

</head>

<body>

<div class="zhifu">
  <div id="success">
   <div class="chengong"><img src="img/xcasd.png"></div>   
</div>
  <div id="failure">
   <div class="failure"><img src="img/hghg.png"></div>   
  </div>
  <div id="cancel">
   <div class="cancel"><img src="img/xzczx.png"></div>   
   </div>
   <div class="jixu">
  <input type="button" value="返回" onClick="shut()" autofocus="autofocus" >
  </div>
</div>
</body>
</html>
