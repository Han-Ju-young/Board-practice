<%@ page language="java" contentType="text/html; charset=EUC-KR" pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
<table width="500" cellpadding="0" cellspacing="0" border="1">
	<tr>
		<td>번호</td>
		<td>이름</td>
		<td>제목</td>
		<td>날짜</td>
		<td>히트</td>
	</tr>

	<c:forEach items="${bList}" var="dto">
	<tr>
		<td>${dto.bId}</td>
		<td>${dto.bName}</td>
		<td><a href="content_view.do?bId=${dto.bId}">${dto.bTitle}</a></td>
		<td><fmt:formatDate value="${dto.bDate}" pattern="yyyy-MM-dd" /></td>
		<td>${dto.bHit}</td>
	</tr>
	</c:forEach>

	<tr>
		<td colspan="5"> <a href="write_view.jsp">글작성</a></td>
	</tr>
</table>
</body>
</html>
