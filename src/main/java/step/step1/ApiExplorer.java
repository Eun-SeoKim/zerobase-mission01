package step.step1;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.IOException;
import com.google.gson.*;

import zerobase.wifi.dto.WifiInfoDto;

public class ApiExplorer {
	public static void main(String[] args) throws IOException {
		
		StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
		urlBuilder.append("/" +  URLEncoder.encode("76426353427165743132336475527165","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
		urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
		urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
		urlBuilder.append("/" + URLEncoder.encode("1","UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
		urlBuilder.append("/" + URLEncoder.encode("5","UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
		// 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
		
		// 서비스별 추가 요청 인자이며 자세한 내용은 각 서비스별 '요청인자'부분에 자세히 나와 있습니다.
		//urlBuilder.append("/" + URLEncoder.encode("20220301","UTF-8")); /* 서비스별 추가 요청인자들*/
		
		URL url = new URL(urlBuilder.toString());
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-type", "application/json");
		System.out.println("Response code: " + conn.getResponseCode()); /* 연결 자체에 대한 확인이 필요하므로 추가합니다.*/
		BufferedReader rd;
		

		// 서비스코드가 정상이면 200~300사이의 숫자가 나옵니다.
		if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
				rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		} else {
				rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
		}
		StringBuilder sb = new StringBuilder();
		String line;
		while ((line = rd.readLine()) != null) {
				sb.append(line);
		}

		rd.close();
		conn.disconnect();
		//System.out.println(sb.toString());
		
		String str = sb.toString();
		
		System.out.println(str);
		

        // json
		JsonParser Parser = new JsonParser();
		JsonObject jsonObj = (JsonObject) Parser.parse(sb.toString());
		JsonObject TbPublicWifiInfo = (JsonObject) jsonObj.get("TbPublicWifiInfo");
		System.out.println(TbPublicWifiInfo);
	
		JsonArray array = (JsonArray) TbPublicWifiInfo.get("row");

		for (int i = 0; i < array.size(); i++) {
			
			JsonObject object = (JsonObject) array.get(i);
			
			String X_SWIFI_MGR_NO = object.get("X_SWIFI_MGR_NO").getAsString();
			String X_SWIFI_WRDOFC = object.get("X_SWIFI_WRDOFC").getAsString();
			String X_SWIFI_MAIN_NM = object.get("X_SWIFI_MAIN_NM").getAsString();
			String X_SWIFI_ADRES1 = object.get("X_SWIFI_ADRES1").getAsString();
			String X_SWIFI_ADRES2 = object.get("X_SWIFI_ADRES2").getAsString();
			String X_SWIFI_INSTL_FLOOR = object.get("X_SWIFI_INSTL_FLOOR").getAsString();
			String X_SWIFI_INSTL_TY = object.get("X_SWIFI_INSTL_TY").getAsString();
			String X_SWIFI_INSTL_MBY = object.get("X_SWIFI_INSTL_MBY").getAsString();
			String X_SWIFI_SVC_SE = object.get("X_SWIFI_SVC_SE").getAsString();
			String X_SWIFI_CMCWR = object.get("X_SWIFI_CMCWR").getAsString();
			String X_SWIFI_CNSTC_YEAR = object.get("X_SWIFI_CNSTC_YEAR").getAsString();
			String X_SWIFI_INOUT_DOOR = object.get("X_SWIFI_INOUT_DOOR").getAsString();
			String X_SWIFI_REMARS3 = object.get("X_SWIFI_REMARS3").getAsString();
			String LAT = object.get("LAT").getAsString();
			String LNT = object.get("LNT").getAsString();
			String WORK_DTTM = object.get("WORK_DTTM").getAsString();

			System.out.println(X_SWIFI_MGR_NO);
			System.out.println(X_SWIFI_WRDOFC);
		}
		
	}
}
