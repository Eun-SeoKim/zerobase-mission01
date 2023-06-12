package step.step1;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class OpenApi {

    /**
     * 오픈 API 샘플을 통한 JSON 문자열 리턴
     */
    public static String get(int startPage, int endPage) {

        String request = null;
        
        String start = Integer.toString(startPage);
        String end = Integer.toString(endPage);

        try {
        	StringBuilder urlBuilder = new StringBuilder("http://openapi.seoul.go.kr:8088"); /*URL*/
    		urlBuilder.append("/" +  URLEncoder.encode("76426353427165743132336475527165","UTF-8") ); /*인증키 (sample사용시에는 호출시 제한됩니다.)*/
    		urlBuilder.append("/" +  URLEncoder.encode("json","UTF-8") ); /*요청파일타입 (xml,xmlf,xls,json) */
    		urlBuilder.append("/" + URLEncoder.encode("TbPublicWifiInfo","UTF-8")); /*서비스명 (대소문자 구분 필수입니다.)*/
    		urlBuilder.append("/" + URLEncoder.encode(start,"UTF-8")); /*요청시작위치 (sample인증키 사용시 5이내 숫자)*/
    		urlBuilder.append("/" + URLEncoder.encode(end,"UTF-8")); /*요청종료위치(sample인증키 사용시 5이상 숫자 선택 안 됨)*/
    		// 상위 5개는 필수적으로 순서바꾸지 않고 호출해야 합니다.
    		
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
    		
    		request = sb.toString();
    		
        	} catch (Exception e) {
                System.out.println("openApi error" + e.getMessage());
            }

        return request;
    }
}
