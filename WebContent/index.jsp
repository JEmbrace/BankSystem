<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@ page language="java" import="hjj.dcits.teller.entities.*,java.util.*" %>

<%	
	request.setCharacterEncoding("utf8");
	Employees emp = (Employees)session.getAttribute("current_user");
	String emp_name = emp.getUser_name();
	int emp_id   = emp.getUser_id();
	List<Menus> enums = (List<Menus>)session.getAttribute("dynamic_enums");
	System.out.println("emp_name----"+emp_name);
	System.out.println("index.jsp----"+enums.size());
	
%>
<!doctype html>
<html>
	<head>
		<meta http-equiv="Content-Type" Content="text/html;charset=utf8"/>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>欢迎进入银行综合业务系统</title>
		<!--必要样式-->
		<link rel="stylesheet" href="css/style.css">
		<link rel="stylesheet" href="css/tips.css">
		<script type="text/javascript" src="js/jquery-2.1.4.js"></script>
		<script type="text/javascript" src="js/script.js"></script>
		</head>
	<body>		
		<ul class="mainmenu">	
			<%
			for (Menus menu:enums) {
				  int temp_id = 0;
				  if(menu.getPower_pid() == 0){
					  temp_id  = menu.getPower_id();
			%>
			<li class="lii">
				<img src="images/envelope.png" alt="Envelope icon" class="icon">
				<span><%=menu.getPower_name() %></span>
			</li>
			
			
			<ul class="submenu">
				<%
				for (Menus menu2:enums) {
						if(menu2.getPower_pid() == temp_id){
							int pid  	 = menu2.getPower_id();
							String pname = menu2.getPower_name().trim();
							String url   = menu2.getMenu_url().toLowerCase();
						
				%>
				<li><span><a class="color" target="up" href="<%=url%>?power_id=<%=pid%>&emp_id=<%=emp_id%>&emp_name=<%=emp_name%>"><%=menu2.getPower_name()%></a></span></li>
				<%
						}
				}
				%>
			</ul>
			
			<%
				  }
			
			}
			%>
		</ul>
		<a href="LoginOutServlet" target="main">退出</a>
		
	</body>
</html>