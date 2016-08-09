<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page language="java" import="hjj.dcits.teller.entities.*"%>
<%	
	request.setCharacterEncoding("utf8");
	Employees emp = (Employees)session.getAttribute("current_user");
	String emp_name = emp.getUser_name();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎进入银行业务系统</title>
</head>
<body bgColor="#FF4081" >
	<center><h1>欢迎<%=emp_name%>进入银行业务系统 ^_^</h1></center>
</body>
</html>