<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		request.setCharacterEncoding("UTF-8");
	%>
	<form action="KadaiLoginServlet" method="post">
		【メールアドレスとPWを入力してください。】<br>
		メール：<input type="text" name="mail"><br>
		PW：<input type="password" name="pass"><br>
		<input type="submit" value="ログイン">
	</form>
</body>
</html>