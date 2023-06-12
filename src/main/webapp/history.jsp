<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="zerobase.wifi.service.WifiService"%>
<%@ page import="zerobase.wifi.dto.*"%>
<%@ page import="java.util.List" %>
<% 	
	request.setCharacterEncoding("utf-8");
	String id = request.getParameter("id");
	
	WifiService wfs = new WifiService();
	
	wfs.delHistory(id);

	
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" href="res/css/main.css"/>
</head>
<body>
	<h1>위치 히스토리 목록</h1>
	<jsp:include page="inc_menu.jsp"/>
	<jsp:include page="search.jsp"/>
	
	<table class="table-list">
		<tr>
			<th>ID</th>
			<th>X좌표</th>
			<th>Y좌표</th>			
			<th>조회일자</th>			
			<th>비고</th>
		</tr>
		<%
			List<PosHistoryDto> historyList = wfs.getHistoryList();
			
			//out.write(historyList.toString());
			
			for(PosHistoryDto list : historyList) {
		%>
		<form id = "del">
			<tr>			
				<td>
					<%=list.getID() %>
					
					<input type = "text" action = "history.jsp" method ="get"
					class="hidden" name = "id" value ="<%=list.getID() %>"/>
									
				</td>
				<td><%=list.getLAT() %></td>
				<td><%=list.getLNT() %></td>
				<td><%=list.getWORK_DTTM() %></td>
				<td>
					<div style="text-align: center;">
						<input type = "button" style=" display :inline-block;" id = "delButton" 
						value = "삭제" onclick="document.getElementById('del').submit();">
					</div>
				</td>	
			</tr>
		</form>
		<%
			}		
		%>
		
	</table>
	
</body>
</html>