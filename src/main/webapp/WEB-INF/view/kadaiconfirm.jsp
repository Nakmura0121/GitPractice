<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="dto.Kadai15" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<p>下記の内容で登録します。よろしいですか？</p>
	<%
		Kadai15 kadai = (Kadai15)session.getAttribute("input_data");
	%>
	名前：<%=kadai.getName() %><br>
	年齢：<%=kadai.getAge() %><br>
	性別：<%=kadai.getGender() %><br>
	電話番号：<%=kadai.getTel() %><br>
	メール：<%=kadai.getMail() %><br>
	パスワード：********<br>
	<a href="KadaiResultServlet">OK</a><br>
	<a href="RegisterFormServlet">戻る</a>
</body>
</html>