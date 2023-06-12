<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="zerobase.wifi.service.WifiService"%>
<%
	WifiService wsf = new WifiService();
	String total = wsf.saveWifiInfo();
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>와이파이 정보 구하기</title>
	<link href="res/css/main.css" rel="stylesheet"/>
</head>
<body>
	<h1 class="h1-center">
		<%=total %>개의 와이파이 정보를 정상적으로 저장하였습니다.
	</h1>
	<div class="a-center">
		<a href="index.jsp">홈으로 가기</a>
	</div>

</body>
</html>