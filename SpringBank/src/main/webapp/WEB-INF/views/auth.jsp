<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<a href="auth">사용자 인증</a>
<a href="callback?code=xYI6HjKgHdm6WR1yAiHyGkafU15jNP">토큰발급</a>
<a href="userinfo?access_token=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxMTAwNzcwNTI3Iiwic2NvcGUiOlsiaW5xdWlyeSIsImxvZ2luIiwidHJhbnNmZXIiXSwiaXNzIjoiaHR0cHM6Ly93d3cub3BlbmJhbmtpbmcub3Iua3IiLCJleHAiOjE2MjMxMzk5OTYsImp0aSI6ImQ1OWNhY2E0LTI3OWEtNGM3Ny1hMDQyLTRhM2Q3M2JlZjFjYiJ9.yNXtaky_Fm7vfDn7UBVAhfDpantQk7VtD-5jiru6iSo">사용자 정보</a>
<div>
    access_token:${access_token}
</div>
</body>
</html>