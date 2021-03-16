<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>board</title>
</head>
<body>
번호:${board.seq}<br/>
제목:${board.title}<br/>
글쓴이:${board.writer}<br/>
내용:${board.content}<br/>
첨부파일 단건:<a href="fileDown?seq=${board.seq}">${board.fileName}</a><br/>
첨부파일 다건:<br/>
<a href="">일괄다운받기(zip)</a><br/>
<c:forTokens items="${board.fileName}" delims="," var="file">
<a href="fileNameDown?fileName=${file}">${file}</a><br/>
</c:forTokens>
</body>
</html>

