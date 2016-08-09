<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//从菜单页面跳过来
	//获取交易号
	String power_id = request.getParameter("power_id");
	//获取柜员id
	String emp_id = request.getParameter("emp_id");
	//获取柜员姓名
	String emp_name = request.getParameter("emp_name");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户开户</title>
	<style>
	        html,body{text-align:center;margin:0px auto;}
			input{width:200px;height:40px;background:#f6f6f6;border:1px solid #999;border-radius:10px;box-shadow:inset 0 2px 5px #999;margin:10px 0;position:relative;}
			select{width:200px;height:40px;background:#f6f6f6;border:1px solid #999;border-radius:10px;box-shadow:inset 0 2px 5px #999;margin:10px 0;position:relative;}
	</style>
	<link rel="stylesheet" type="text/css" href="css/header.css">
	<script src="js/prefixfree.min.js"></script>
	<script src="js/index.js"></script>
	<script>
		function check()
		{ 
		       var password = document.getElementById("card_psw").value; 
		       var repsword = document.getElementById("rcard_psw").value;
		       //alert(password);
		       //alert(repsword);
		       if(password == repsword) {
		          return true;
		        }
		       
		       alert("两次密码不同，请重新输入");
		       return false;
		}
</script>
</head>
<body bgColor="#FF4081">
	  <nav>
	 	 <menu>
		    <li>
		      <a href="#"> <span>帐</span><span>*******</span></a>
		    </li>
	    	<li id="at">
	    		<a href="#"><span>户</span><span>*******</span></a>
	    	</li>
	    	<li>
		     	 <a href="#">
			       	 <span>开</span>
			       	 <span>*******</span>
		     	 </a>
	    	</li>
	    	
		    <li>
		      <a href="#">
		        <span>户</span>
		        <span>*********</span>
		      </a>
		    </li>
	  </menu>
	  
	  <div class="ribbon left"></div>
	  <div class="ribbon right"></div>
	</nav>

 	<br><br><br><br><br><br>
	 <form action="AccountOpenServlet" method="post" onsubmit="check()">
	 	
	    <input name="powerid" type="hidden" value="<%=power_id %>"/>
	 	<input name="empid" type="hidden" value="<%=emp_id %>"/>
	 	<input name="empname" type="hidden" value="<%=emp_name %>"/>
		账户编号:<input name="id" type="text"/>
		银行卡号:<input type="text" name="card_id" />
		<br>
		银行卡密码:<input type="password" id="card_psw" name="card_psw" />
		密码确认:<input type="password" id="rcard_psw" name="rcard_psw" />
		<br>
		账户类型：<select name="type">
			  <option value ="基本存款账户">基本存款账户</option>
			  <option value ="一般存款账户">一般存款账户</option>
			  <option value="临时存款账户">临时存款账户</option>
			  <option value="专用存款账户">专用存款账户</option>
		</select>	
		客户编号:<input type="text" name="cuerstom_id"/>
		柜员号:<input type="text" name="emp_id" value="<%=emp_id %>" />
		<br>
		<input type="submit" value="提交" />
	</form>
	
</body>

</html>