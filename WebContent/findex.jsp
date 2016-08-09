<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript">
	window.parent.frames["up"].location.reload(); 
</script>
</head>
	<frameset cols="20%,80%" bordercolor="#FF4081" border="1">
		<frame noresize="noresize" src="index.jsp" id="main" name="mainframe">
		<frameset bordercolor="#FF4081" >
			<frame noresize="noresize" id="up" name="up" src="welcome.jsp">	
			
		</frameset>
	</frameset>
</html>