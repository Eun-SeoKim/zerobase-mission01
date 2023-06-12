<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ page import="zerobase.wifi.dto.*"%>
<%@ page import="java.util.List" %>
<%@ page import="zerobase.wifi.service.WifiService"%>
<% 	
	request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
	<title>와이파이 정보 구하기</title>
	<link rel="stylesheet" href="res/css/main.css"/>
</head>
<body>
	<h1>와이파이 정보 구하기</h1>
	<jsp:include page="inc_menu.jsp"/>
	<jsp:include page="search.jsp"/>
	
	<table class="table-list">
		<tr>
			<th>거리(Km)</th>
			<th>관리번호</th>
			<th>자치구</th>			
			<th>와이파이명</th>			
			<th>도로명주소</th>			
			<th>상세주소</th>			
			<th>설치위치(층)</th>			
			<th>설치유형</th>	
			<th>설치기관</th>
			<th>서비스구분</th>
			<th>망종류</th>
			<th>설치년도</th>
			<th>실내외구분</th>
			<th>WIFI접속환경</th>
			<th>X좌표</th>
			<th>Y좌표</th>
			<th>작업일자</th>			
		</tr>
<% 
	String x = request.getParameter("lat");
	String y = request.getParameter("lnt");

	if(x != null && y != null){
	double lat = Double.valueOf(x);
	double lnt = Double.valueOf(y);
	
	System.out.println(lat);
	System.out.println(lnt);
	
	PosHistoryDto historyDto = new PosHistoryDto();
	WifiService wfs = new WifiService();
	historyDto.setLAT(lat);
	historyDto.setLNT(lnt);
	
	//검색 기록 저장 메소드
	WifiService.addHistory(historyDto);
	
	//거리 계산, 조회 메소드
	List<WifiInfoDto> disList = wfs.getDisList(lat, lnt);
	
	//out.write(historyList.toString());
	
	for(WifiInfoDto list : disList) {

%>
		<tr>
			<td><%=list.getDistance() %></td>
			<td><%=list.getX_SWIFI_MGR_NO() %></td>
			<td><%=list.getX_SWIFI_WRDOFC() %></td>
			<td><%=list.getX_SWIFI_MAIN_NM() %></td>
			<td><%=list.getX_SWIFI_ADRES1() %></td>
			<td><%=list.getX_SWIFI_ADRES2() %></td>
			<td><%=list.getX_SWIFI_INSTL_FLOOR() %></td>
			<td><%=list.getX_SWIFI_INSTL_TY() %></td>
			<td><%=list.getX_SWIFI_INSTL_MBY() %></td>
			<td><%=list.getX_SWIFI_SVC_SE() %></td>
			<td><%=list.getX_SWIFI_CMCWR() %></td>
			<td><%=list.getX_SWIFI_CNSTC_YEAR() %></td>
			<td><%=list.getX_SWIFI_INOUT_DOOR() %></td>
			<td><%=list.getX_SWIFI_REMARS3() %></td>
			<td><%=list.getLAT() %></td>
			<td><%=list.getLNT() %></td>
			<td><%=list.getWORK_DTTM() %></td>
		</tr>
<%
	}
		}else{%>
		<tr>
			<td colspan='17'>
				<p class = "p-center">위치 정보를 입력한 후에 조회해주세요.</p>
			</td>
		</tr>
<%} %>		
	</table>
	
</body>
</html>