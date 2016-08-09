<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!doctype html>
<html lang="zh">
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"> 
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>银行综合业务</title>
		<link rel="stylesheet" type="text/css" href="css/default.css">
		<link rel="stylesheet" type="text/css" href="css/styles.css">
		<script src="js/jquery-2.1.4.js" type="text/javascript"></script>
	</head>
<body>
	<form action="LoginServlet" method="post">
		<div class="panel-lite">
		
			  <div class="thumbur">
				<div class="icon-lock"></div>
			  </div>
		  
		  	 <h4>登录</h4>
		  
			  <div class="form-group">
				<input name="username" required="required" type="text" class="form-control"/>
				<label class="form-label">登录名</label>
			  </div>
		  
			  <div class="form-group">
				<input name="password" required="required" type="password" class="form-control"/>
				<label class="form-label">登录密码</label>
			  </div>
		 
			  <div class="form-group">
				<input name="orgnumber" required="required" ="text"  class="form-control"/>
				<label class="form-label">机构号</label>
			  </div>
		  
			   <div class="form-group">
				<input name="netnumber" required="required" type="text" class="form-control"/>
				<label class="form-label">网点号</label>
			   </div>
			   <input type="submit" class="floating-btn" value="提交" />
		</div>
	</form>
	
</body>
</html>