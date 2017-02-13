<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <% String path = request.getContextPath(); 
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/"; %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<base href="<%=basePath%>">
<style type="text/css">
   .a{
   	
   }
</style>
</head>
<body>
	<form action="deploy" method="post">
		<input type="submit" value="Deploy" /><br><br>
		<textarea name="xml" rows="20" cols="120"></textarea>
	</form>
</body>
</html>