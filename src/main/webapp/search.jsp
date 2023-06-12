<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<style>
.search {
	padding: 0 0 10px 0;
}
</style>
<script src="res/js/index.js"></script>
<div class="search">
	<form accept-charset ="utf-8" action="index.jsp" id="form" name = "my_location"  method ="get">
	LAT: <input type = "text" id = "inputLat" name = "lat" value = "0.0" required/> ,
	LNT: <input type = "text" id = "inputLnt" name = "lnt" value = "0.0" required/>
	
	<input type = "button" id = "getPositionButton" value = "내 위치 가져오기">
	<input type = "button" id = "getWifiButton" value = "근처 WIFI 정보 보기" onclick="document.getElementById('form').submit();">
	</form>
</div>


