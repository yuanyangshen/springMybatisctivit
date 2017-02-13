<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
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
	<a href="publish.jsp">Deploy</a>
	
	<hr />
	<table border="1" width="100%">
		<legend>process definition</legend>
		<thead>
			<tr>
				<th>key</th>
				<th>name</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${processList}" var="pro" varStatus="vs">
				<tr>
					<td>${pro.key}</td>
					<td>${pro.name }</td>
					<td>
						<a href="<%=basePath%>start?id=${pro.id}">start</a>
						<a href="<%=basePath%>graph?processDefinitionId=${pro.id }&type=start">graph</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
<hr />
<table border="1" width="100%">
		<legend>process instance</legend>
		<thead>
			<tr>
				<th>id</th>
				<th>process definition</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${processInstance}" var="insatnce" varStatus="vs">
				<tr>
					<td>${insatnce.id}</td>
					<td>${insatnce.processDefinitionId }</td>
					<td>
						<a href="<%=basePath%>graph?processDefinitionId=${insatnce.id }&type=instance">graph</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
<hr>
<table border="1" width="100%">
		<legend>task</legend>
		<thead>
			<tr>
				<th>id</th>
				<th>name</th>
				<th>assignee</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${taskService}" var="task" varStatus="vs">
				<tr>
					<td>${task.id}</td>
					<td>${task.name }</td>
					<td>${task.assignee }</td>
					<td>
						<a href="<%=basePath%>complete?processDefinitionId=${task.id }">Complete</a>
						<a href="<%=basePath%>graph?processDefinitionId=${task.id }&type=complete">graph</a>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</body>
<hr>
</html>