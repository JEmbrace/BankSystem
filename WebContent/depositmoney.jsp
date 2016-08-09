<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String power_id = request.getParameter("power_id");
	String emp_id = request.getParameter("emp_id");
	String emp_name = request.getParameter("emp_name");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户存款</title>
	<style>
	        html,body{text-align:center;margin:0px auto;}
			input{width:200px;height:40px;background:#f6f6f6;border:1px solid #999;border-radius:10px;box-shadow:inset 0 2px 5px #999;margin:10px 0;position:relative;}
	</style>
	<link rel="stylesheet" type="text/css" href="css/header.css">
	<script src="js/prefixfree.min.js"></script>
	<script src="js/index.js"></script>
</head>
<body bgColor="#FF4081">
	  <nav>
	 	 <menu>
		    <li>
		      <a href="#"> <span>客</span><span>*******</span></a>
		    </li>
	    	<li id="at">
	    		<a href="#"><span>户</span><span>*******</span></a>
	    	</li>
	    	<li>
		     	 <a href="#">
			       	 <span>存</span>
			       	 <span>*******</span>
		     	 </a>
	    	</li>
	    	
		    <li>
		      <a href="#">
		        <span>款</span>
		        <span>*********</span>
		      </a>
		    </li>
	  </menu>
	  
	  <div class="ribbon left"></div>
	  <div class="ribbon right"></div>
	</nav>
  
 	<br><br><br><br><br><br>
	 <form action="DepositMoneyServlet" method="post">
	    <input name="powerid" type="hidden" value="<%=power_id %>"/>
	 	<input name="empid" type="hidden" value="<%=emp_id %>"/>
		银行卡号:<input type="text" name="card_id" />
		<br>
		银行卡密码:<input type="password" name="card_psw" />
		<br>
		存款金额:<input type="text" name="money"/>		
		<br>
		<input type="submit" value="提交" />
	</form>
</body>
</html>