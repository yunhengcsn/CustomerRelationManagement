<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>客户列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	  <script type="text/javascript">
		  function _change() {
			  var select = document.getElementById("select");
			  location = "<c:url value='/CustomerServlet?${page.url}'/>&currPage=" + select.value;
		  }
	  </script>
  </head>
  
  <body>
<h3 align="center">客户列表</h3>
<table border="1" width="70%" align="center">
	<tr>
		<th>客户姓名</th>
		<th>性别</th>
		<th>生日</th>
		<th>手机</th>
		<th>邮箱</th>
		<th>描述</th>
		<th>操作</th>
	</tr>
	<c:forEach items="${page.data}" var="ctm">
		<tr>
			<td>${ctm.cname}</td>
			<td>${ctm.gender}</td>
			<td>${ctm.birthday}</td>
			<td>${ctm.cellphone}</td>
			<td>${ctm.email}</td>
			<td>${ctm.description}</td>
			<td>
				<a href="<c:url value='/CustomerServlet?method=preEdit&cid=${ctm.cid}'/>">编辑</a>
				<a href="<c:url value='/CustomerServlet?method=delete&cid=${ctm.cid}'/>">删除</a>
			</td>
		</tr>
	</c:forEach>
</table>
<br/>

<p style="text-align: center;">
	第${page.currPage}页/共${page.totalPage}页

	<a href="${page.url}&currPage=1">首页</a>
	
	<c:choose>
		<c:when test="${page.currPage eq 1}">上一页</c:when>
		<c:otherwise>
			<a href="${page.url}&currPage=${page.currPage-1}">上一页</a>
		</c:otherwise>
	</c:choose>
	
	<c:choose>
		<c:when test="${page.totalPage <= 10}">
			<c:set var="begin" value="1"/>
			<c:set var="end" value="${page.totalPage}"/>
		</c:when>
		<c:otherwise>
			<c:set var="begin" value="${page.currPage-4}"/>
			<c:set var="end" value="${page.currPage+5}"/>
			<c:choose>
				<c:when test="${begin < 1}">
					<c:set var="begin" value="1"/>
					<c:set var="end" value="10"/>
				</c:when>
				<c:when test="${end > page.totalPage}">
					<c:set var="end" value="${page.totalPage}"/>
					<c:set var="begin" value="${page.totalPage-9}"/>
				</c:when>
			</c:choose>
		</c:otherwise>
	</c:choose>

	<c:forEach begin="${begin}" end="${end}" var="i">
		<c:choose>
			<c:when test="${i == page.currPage}">${i}</c:when>
			<c:otherwise>
				<a href="${page.url}&currPage=${i}">${i}</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>

	<c:choose>
		<c:when test="${page.currPage eq page.totalPage}">下一页</c:when>
		<c:otherwise>
			<a href="${page.url}&currPage=${page.currPage+1}">下一页</a>
		</c:otherwise>
	</c:choose>

	<a href="${page.url}&currPage=${page.totalPage}">尾页</a>

	<select name="currPage" onchange="_change()" id="select">
		<c:forEach begin="1" end="${page.totalPage}" var="i">
			<option value="${i}" <c:if test="${i eq page.currPage }">selected="selected"</c:if> >
					${i}
			</option>
		</c:forEach>
	</select>
</p>
  </body>
</html>
